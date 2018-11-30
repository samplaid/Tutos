/**
* 
*/
package lu.wealins.webia.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.webia.services.ActivableRoleBasedCountry;
import lu.wealins.common.dto.webia.services.ClientRoleActivationDTO;
import lu.wealins.webia.core.service.ClientRoleActivationResolverService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class ClientRoleActivationResolverServiceImpl implements ClientRoleActivationResolverService {
	private static final String WEBIA_CPR_ROLE = "webia/clientRoleActivation/";

	@Autowired
	private RestClientUtils restClientUtils;
	/* (non-Javadoc)
	 * @see lu.wealins.webia.core.service.ClientRoleActivationResolverService#solveClientRoleActivation(java.lang.String)
	 */
	@Override
	public List<? extends ClientRoleActivationDTO> solveClientRoleActivation(String countryCode) {
		List<? extends ClientRoleActivationDTO> results = new ArrayList<>();

		if (!StringUtils.isEmpty(countryCode)) {
			MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
			queryParams.put("countryCode", Arrays.asList(countryCode));
			results = restClientUtils.get(WEBIA_CPR_ROLE, "solveActivation/countryCode", queryParams,
					new GenericType<List<? extends ClientRoleActivationDTO>>() {
					});
		}

		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.ClientRoleActivationResolverService#solvePolicyHolderRoleActivation(java.lang.String, boolean, boolean)
	 */
	@Override
	public List<? extends ActivableRoleBasedCountry> solvePolicyHolderRoleActivation(String countryCode, boolean productCapi, boolean yearTerm) {
		List<? extends ClientRoleActivationDTO> results = new ArrayList<>();

		if (!StringUtils.isEmpty(countryCode)) {
			MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
			queryParams.putSingle("countryCode", countryCode);
			queryParams.putSingle("productCapi", productCapi);
			queryParams.putSingle("yearTerm", yearTerm);

			results = restClientUtils.get(WEBIA_CPR_ROLE, "phActivation/countryCode", queryParams,
					new GenericType<List<? extends ClientRoleActivationDTO>>() {
					});
		}

		return results;
	}
}
