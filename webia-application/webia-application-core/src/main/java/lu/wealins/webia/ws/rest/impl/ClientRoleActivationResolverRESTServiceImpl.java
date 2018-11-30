/**
* 
*/
package lu.wealins.webia.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.ClientRoleActivationFlagDTO;
import lu.wealins.webia.core.service.ClientRoleActivationFlagService;
import lu.wealins.webia.ws.rest.ClientRoleActivationResolverRESTService;

@Component
public class ClientRoleActivationResolverRESTServiceImpl implements ClientRoleActivationResolverRESTService {

	@Autowired
	private ClientRoleActivationFlagService clientRoleActivationFlagService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.ClientRoleActivationResolverRESTService#solveActivation(java.lang.String)
	 */
	@Override
	public ClientRoleActivationFlagDTO solveBeneficiaryRoleActivation(SecurityContext context, String countryCode) {
		return clientRoleActivationFlagService.solveBeneficiaryRoleActivation(countryCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.ClientRoleActivationResolverRESTService#solvePolicyHolderRoleActivation(javax.ws.rs.core.SecurityContext, java.lang.String, boolean, boolean)
	 */
	@Override
	public ClientRoleActivationFlagDTO solvePolicyHolderRoleActivation(SecurityContext context, String countryCode, boolean productCapi, boolean yearTerm) {

		return clientRoleActivationFlagService.solvePolicyHolderRoleActivation(countryCode, productCapi, yearTerm);
	}

}
