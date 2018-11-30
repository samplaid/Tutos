/**
 * 
 */
package lu.wealins.webia.core.service;

import java.util.List;

import lu.wealins.common.dto.webia.services.ActivableRoleBasedCountry;

public interface ClientRoleActivationResolverService {

	List<? extends ActivableRoleBasedCountry> solveClientRoleActivation(String countryCode);

	List<? extends ActivableRoleBasedCountry> solvePolicyHolderRoleActivation(String countryCode, boolean productCapi, boolean yearTerm);
}
