package lu.wealins.liability.services.core.business;

import java.util.Collection;
import java.util.Date;

import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.common.dto.liability.services.BeneficiaryDTO;
import lu.wealins.common.dto.liability.services.BeneficiaryLiteDTO;
import lu.wealins.common.dto.liability.services.ClientRoleActivationFlagDTO;

public interface BeneficiaryService {

	/**
	 * Get the beneficiaries of maturity benefit linked to a policy.
	 * 
	 * @param policy The policy.
	 * @return The beneficiaries of maturity benefit.
	 */
	Collection<BeneficiaryDTO> getLifeBeneficiaries(PolicyEntity policy);

	Collection<BeneficiaryLiteDTO> getLifeBeneficiaryLites(PolicyEntity policy);

	/**
	 * Get the beneficiaries of death benefit linked to a policy.
	 * 
	 * @param policy The policy.
	 * @return The beneficiaries of death benefit.
	 */
	Collection<BeneficiaryDTO> getDeathBeneficiaries(PolicyEntity policy);

	Collection<BeneficiaryLiteDTO> getDeathBeneficiaryLites(PolicyEntity policy);

	Collection<BeneficiaryDTO> getLifeBeneficiaries(PolicyEntity policy, ClientRoleActivationFlagDTO clientRoleActivationFlag);

	Collection<BeneficiaryDTO> getDeathBeneficiaries(PolicyEntity policy, ClientRoleActivationFlagDTO clientRoleActivationFlag);

	Collection<BeneficiaryDTO> getLifeBeneficiaries(String workflowItemId, ClientRoleActivationFlagDTO clientRoleActivationFlag);

	Collection<BeneficiaryDTO> getDeathBeneficiaries(String workflowItemId, ClientRoleActivationFlagDTO clientRoleActivationFlag);

	void updateDeathBeneficiaries(Collection<BeneficiaryDTO> beneficiaries, String policyId, Date activeDate);

	void updateLifeBeneficiaries(Collection<BeneficiaryDTO> beneficiaries, String policyId, Date activeDate);

	void updateDeathBeneficiaries(Collection<BeneficiaryDTO> beneficiaries, String policyId, Date activeDate, String workflowItemId);

	void updateLifeBeneficiaries(Collection<BeneficiaryDTO> beneficiaries, String policyId, Date activeDate, String workflowItemId);
}
