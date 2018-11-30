package lu.wealins.webia.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.liability.services.ProductDTO;
import lu.wealins.common.dto.webia.services.ActivableRoleBasedCountry;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.ClientFormDTO;
import lu.wealins.common.dto.webia.services.enums.ClientRelationType;
import lu.wealins.webia.core.service.ClientRoleActivationResolverService;
import lu.wealins.webia.core.service.FundFormService;
import lu.wealins.webia.core.service.LiabilityProductService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Component
public abstract class AbstractStepEnricher {
	private static final String EXEMPT_CPR_TYPE = "EXEMPT_CPR_TYPE";

	@Autowired
	private LiabilityProductService productService;

	@Autowired
	private ClientRoleActivationResolverService clientRoleActivationResolverService;

	@Autowired
	private FundFormService fundFormService;

	@Autowired
	private RestClientUtils restClientUtils;

	public void enrichProduct(AppFormDTO appForm) {
		ProductDTO product = getProduct(appForm.getProductCd());

		if (product != null) {
			appForm.setProductCountryCd(product.getNlCountry());
		}
	}

	public void enrichFunds(AppFormDTO appForm) {
		fundFormService.enrichFunds(appForm.getFundForms(), appForm.getPaymentDt());
	}

	protected void enrichClients(AppFormDTO appForm) {
		filterOtherClientsBasedOnProduct(appForm);
	}

	protected void filterOtherClientsBasedOnProduct(AppFormDTO appForm) {
		if (CollectionUtils.isNotEmpty(appForm.getOtherClients()) && !StringUtils.isEmpty(appForm.getProductCd())) {
			List<Integer> excludedRoles = getExlucdedRoles();

			boolean yearTerm = appForm.getTerm() != null && appForm.getTerm().intValue() > 0;
			String countryCode = productService.getCountryCode(appForm.getProductCd());
			List<? extends ActivableRoleBasedCountry> activableBenefRoles = clientRoleActivationResolverService.solveClientRoleActivation(countryCode);
			List<? extends ActivableRoleBasedCountry> activablePolicyHolderRoles = clientRoleActivationResolverService.solvePolicyHolderRoleActivation(countryCode,
					productService.isCapiProduct(appForm.getProductCd()), yearTerm);

			List<ActivableRoleBasedCountry> activables = new ArrayList<>();
			activableBenefRoles.forEach(benefRole -> activables.add(benefRole));
			activablePolicyHolderRoles.forEach(policHolderRole -> activables.add(policHolderRole));

			appForm.setOtherClients(appForm.getOtherClients().stream().filter(filterCprRolePredicate(excludedRoles, activables)).collect(Collectors.toList()));
		}
	}

	private List<Integer> getExlucdedRoles() {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<String, Object>();
		queryParams.put("code", Arrays.asList(EXEMPT_CPR_TYPE));

		List<Integer> excludedRoles = restClientUtils.get("webia/applicationParameter/", "intValueList", queryParams, new GenericType<List<Integer>>() {
		});

		excludedRoles.addAll(ClientRelationType.CLIENTS_EXCLUDED_RELATIONS);
		return excludedRoles;
	}

	private Predicate<? super ClientFormDTO> filterCprRolePredicate(List<Integer> excludedRoles, List<? extends ActivableRoleBasedCountry> activableRoles) {
		return currentClient -> excludedRoles != null && currentClient.getClientRelationTp() != null && !excludedRoles.contains(currentClient.getClientRelationTp().intValue())
				&& activableRoles.stream().filter(activable -> activable.getRoleNumber() != null && activable.getRoleNumber().intValue() == currentClient.getClientRelationTp().intValue())
						.allMatch(activable -> !activable.isEnable());
	}


	protected ProductDTO getProduct(String ProductCd) {
		if (StringUtils.isEmpty(ProductCd)) {
			return null;
		}

		return productService.getProduct(ProductCd);
	}

}
