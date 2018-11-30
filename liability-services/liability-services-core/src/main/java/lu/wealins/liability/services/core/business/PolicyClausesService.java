package lu.wealins.liability.services.core.business;

import java.util.Collection;

import lu.wealins.liability.services.core.persistence.entity.PolicyBeneficiaryClauseEntity;
import lu.wealins.common.dto.liability.services.PolicyBeneficiaryClauseDTO;
import lu.wealins.common.dto.liability.services.PolicyClausesDTO;

public interface PolicyClausesService {

	PolicyClausesDTO getClauses(String policy);
	
	PolicyClausesDTO getClausesWithWorkflowItemId(String workflowItemId);

	Collection<PolicyBeneficiaryClauseDTO> createNominativeDeathClauses(String workflowItemId);

	Collection<PolicyBeneficiaryClauseDTO> createNominativeLifeClauses(String workflowItemId);

	void save(PolicyBeneficiaryClauseDTO policyBeneficiaryClause);

	void save(PolicyBeneficiaryClauseDTO policyBeneficiaryClause, String modifyProcess);

	PolicyBeneficiaryClauseEntity save(PolicyBeneficiaryClauseEntity policyBeneficiaryClause, String modifyProcess);

	void updatePolicyBeneficiaryClauses(Collection<PolicyBeneficiaryClauseDTO> deathPolicyBeneficiaryClauses, Collection<PolicyBeneficiaryClauseDTO> lifePolicyBeneficiaryClauses,
			String policyId);

	void updatePolicyBeneficiaryClauses(Collection<PolicyBeneficiaryClauseDTO> deathPolicyBeneficiaryClauses, Collection<PolicyBeneficiaryClauseDTO> lifePolicyBeneficiaryClauses,
			String policyId, String workflowItemId);
}
