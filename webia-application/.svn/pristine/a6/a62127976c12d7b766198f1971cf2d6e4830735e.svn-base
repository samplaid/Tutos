package lu.wealins.webia.core.service;

import lu.wealins.common.dto.liability.services.ClientRoleActivationFlagDTO;

public interface ClientRoleActivationFlagService {

	void setupActivationFlag(ClientRoleActivationFlagDTO activationFlag, Integer roleNumber, boolean isEnable);

	ClientRoleActivationFlagDTO solveBeneficiaryRoleActivation(String countryCode);

	ClientRoleActivationFlagDTO solvePolicyHolderRoleActivation(String countryCode, boolean productCapi, boolean yearTerm);

	ClientRoleActivationFlagDTO solveAllRoleActivations(String policyId);
}
