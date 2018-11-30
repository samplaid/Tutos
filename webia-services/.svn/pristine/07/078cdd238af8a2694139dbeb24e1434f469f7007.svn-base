/**
 * 
 */
package lu.wealins.webia.services.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.webia.services.core.service.ClientRoleActivationResolverService;
import lu.wealins.webia.services.ws.rest.ClientRoleActivationResolverRESTService;
import lu.wealins.common.dto.webia.services.ActivableRoleBasedCountry;

@Component
public class ClientRoleActivationResolverRESTServiceImpl implements ClientRoleActivationResolverRESTService {

	@Autowired
	private ClientRoleActivationResolverService clientRoleActivationResolverService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.ClientRoleActivationResolverRESTService#solveActivation(java.lang.String)
	 */
	@Override
	public List<? extends ActivableRoleBasedCountry> solveActivation(SecurityContext context, String countryCode) {
		return clientRoleActivationResolverService.solveClientRoleActivation(countryCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.ClientRoleActivationResolverRESTService#solvePolicyHolderRoleActivation(javax.ws.rs.core.SecurityContext, java.lang.String, boolean, boolean)
	 */
	@Override
	public List<? extends ActivableRoleBasedCountry> solvePolicyHolderRoleActivation(SecurityContext context, String countryCode, boolean productCapi, boolean yearTerm) {
		return clientRoleActivationResolverService.solvePolicyHolderRoleActivation(countryCode, productCapi, yearTerm);
	}

}
