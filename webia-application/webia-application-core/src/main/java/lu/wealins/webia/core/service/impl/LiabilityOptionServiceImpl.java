package lu.wealins.webia.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.liability.services.OptionDetailDTO;
import lu.wealins.common.dto.liability.services.OptionDetails;
import lu.wealins.common.dto.webia.services.ActivableRoleBasedCountry;
import lu.wealins.common.dto.webia.services.ApplicationParameterDTO;
import lu.wealins.common.dto.webia.services.CodeLabelDTO;
import lu.wealins.common.dto.webia.services.enums.ClientRelationType;
import lu.wealins.webia.core.service.ClientRoleActivationResolverService;
import lu.wealins.webia.core.service.LiabilityOptionDetailService;
import lu.wealins.webia.core.service.LiabilityProductService;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.service.WebiaCodeLabelService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityOptionServiceImpl implements LiabilityOptionDetailService {
	private static final String CPR_ROLE = "cpr-roles";
	private static final String LIABILITY_GET_OPTION_DETAIL = "liability/optionDetail/";
	private static final String EXEMPT_CPR_TYPE = "EXEMPT_CPR_TYPE";
	private static final String CLIENT = "CLIENT";
	private static final String LIABILITY_COUNTRY_CODE = "liability/product/";
	private static final String EXEMPT_PRICING_FREQUENCY_FE = "EXEMPT_PRICING_FREQUENCY_FE";
	private static final String LIABILITY_OPTION_DETAIL = "liability/optionDetail/";

	@Autowired
	private WebiaApplicationParameterService applicationParameterService;
	@Autowired
	private RestClientUtils restClientUtils;
	@Autowired
	private WebiaCodeLabelService webiaCodeLabelService;
	@Autowired
	private ClientRoleActivationResolverService clientRoleActivationResolverService;

	@Autowired
	private LiabilityProductService productService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityOptionService#getCPRRoles()
	 */
	@Override
	public List<OptionDetailDTO> getCPRRoles() {
		ApplicationParameterDTO excludedRolesString = applicationParameterService.getApplicationParameter(EXEMPT_CPR_TYPE);
		final List<Integer> excludedRoles;
		if (excludedRolesString != null) {
			// transform "1,2,5,8,11" to [1,2,5,8,11]
			String[] tmp = excludedRolesString.getValue().split(",");
			excludedRoles = Arrays.stream(tmp)
					.map(s -> (s != null) ? Integer.valueOf(s.trim()) : null)
					.collect(Collectors.toList());

		} else {
			excludedRoles = new ArrayList<Integer>();
		}
		OptionDetails optionDetails = restClientUtils.get(LIABILITY_GET_OPTION_DETAIL, CPR_ROLE, OptionDetails.class);
		List<OptionDetailDTO> allRoles = optionDetails.getList();
		// filter allRoles to exclude unexpected roles
		List<OptionDetailDTO> filtredRoles = allRoles.stream().filter(role -> !excludedRoles.contains((Integer) role.getNumber())).collect(Collectors.toList());

		// translate some role description from "Lissia" to "Webia" names
		final Collection<CodeLabelDTO> webiaRoleNames = webiaCodeLabelService.getCodeLabels(CLIENT);
		if (CollectionUtils.isNotEmpty(webiaRoleNames)) {
			Map<Integer, String> map = new HashMap<>();
			webiaRoleNames.stream().forEach(role -> map.put(Integer.valueOf(role.getCode()), role.getLabel()));
			filtredRoles.stream().forEach(role -> {
				Integer key = role.getNumber();
				if (map.containsKey(key)) {
					role.setDescription(map.get(key));
				}
				;
			});
		}
		return filtredRoles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityOptionDetailService#getCPRRoles(java.lang.String)
	 */
	@Override
	public List<OptionDetailDTO> getCPRRoles(String productId, boolean productCapi, boolean yearTerm) {
		if (!StringUtils.isEmpty(productId)) {
			List<OptionDetailDTO> results = filterCprRole(productId, productCapi, yearTerm);

			// translate some role description from "Lissia" to "Webia" names
			final Collection<CodeLabelDTO> webiaRoleNames = webiaCodeLabelService.getCodeLabels(CLIENT);

			if (CollectionUtils.isNotEmpty(webiaRoleNames)) {
				Map<Integer, String> map = new HashMap<>();
				webiaRoleNames.stream().forEach(role -> map.put(Integer.valueOf(role.getCode()), role.getLabel()));

				results.stream().forEach(role -> {
					if (map.containsKey(role.getNumber())) {
						role.setDescription(map.get(role.getNumber()));
					}
				});

				return results;
			}
		}

		return new ArrayList<>();

	}

	private List<OptionDetailDTO> filterCprRole(String productId, boolean productCapi, boolean yearTerm) {
		List<OptionDetailDTO> results = new ArrayList<>();

		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<String, Object>();
		queryParams.put("code", Arrays.asList(EXEMPT_CPR_TYPE));
		List<Integer> excludedRoles = restClientUtils.get("webia/applicationParameter/", "intValueList", queryParams, new GenericType<List<Integer>>() {});
		excludedRoles.addAll(ClientRelationType.CLIENTS_EXCLUDED_RELATIONS);
		
		String countryCode = productService.getCountryCode(productId);
		List<? extends ActivableRoleBasedCountry> activableBenefRoles = clientRoleActivationResolverService.solveClientRoleActivation(countryCode);
		List<? extends ActivableRoleBasedCountry> activablePolicyHolderRoles = clientRoleActivationResolverService.solvePolicyHolderRoleActivation(countryCode, productCapi, yearTerm);

		List<ActivableRoleBasedCountry> activables = new ArrayList<>();
		activableBenefRoles.forEach(benefRole -> activables.add(benefRole));
		activablePolicyHolderRoles.forEach(policHolderRole -> activables.add(policHolderRole));
		
		OptionDetails optionDetails = restClientUtils.get(LIABILITY_GET_OPTION_DETAIL, CPR_ROLE, OptionDetails.class);
		results = optionDetails.getList().stream().filter(filterCprRolePredicate(excludedRoles, activables)).collect(Collectors.toList());
		return results;
	}

	private Predicate<? super OptionDetailDTO> filterCprRolePredicate(List<Integer> excludedRoles, List<? extends ActivableRoleBasedCountry> activableRoles) {
		return currentRole -> excludedRoles != null && !excludedRoles.contains((Integer) currentRole.getNumber())
				&& activableRoles.stream().filter(activable -> activable.getRoleNumber() != null && activable.getRoleNumber().intValue() == currentRole.getNumber())
						.allMatch(activable -> !activable.isEnable());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityOptionDetailService#getContextualizedPricingFrequencies(javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public List<OptionDetailDTO> getContextualizedPricingFrequencies() {
		List<Integer> exemptPricingFreq = applicationParameterService.getIntegerValues(EXEMPT_PRICING_FREQUENCY_FE);
		List<OptionDetailDTO> pricingFrequencies = restClientUtils.get(LIABILITY_OPTION_DETAIL, "pricingFrequencies", new MultivaluedHashMap<>(), new GenericType<List<OptionDetailDTO>>() {
		});
		return pricingFrequencies.stream().filter(pricingFreq -> !exemptPricingFreq.contains(pricingFreq.getNumber())).collect(Collectors.toList());
	}

}
