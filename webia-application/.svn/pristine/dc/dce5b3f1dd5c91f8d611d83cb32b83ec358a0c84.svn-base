package lu.wealins.webia.core.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import lu.wealins.common.dto.liability.services.PolicyClausesDTO;
import lu.wealins.common.dto.liability.services.PolicyClientRoleViewRequest;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.common.dto.liability.services.PolicySurrenderDTO;
import lu.wealins.common.dto.liability.services.RolesByPoliciesDTO;
import lu.wealins.common.dto.liability.services.WithdrawalInputDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;

public interface LiabilityPolicyService {

	/**
	 * Get the policy according its id.
	 * 
	 * @param policyId The policy id.
	 * @return The policy.
	 */
	PolicyDTO getPolicy(String policyId);

	Collection<String> getPoliciesByFund(String fundId);

	PolicyLightDTO getPolicyLight(String policyId);

	PolicyLightDTO getPolicyLight(Integer workflowItemId);

	PolicyDTO getPolicy(PolicyClientRoleViewRequest request);

	/**
	 * Get policy according to the workflow item id. The only way is to retrieve the 'Policy' metadata in order to have the policy number.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @return The policy.
	 */
	PolicyDTO getPolicy(Long workflowItemId);

	/**
	 * Check if the policy exist.
	 * 
	 * @param policyId The policy id.
	 * @return True, if successful.
	 */
	Boolean isExist(String policyId);

	Boolean isExistByWorkflowItemId(String workflowItemId);

	/**
	 * Create a policy from webia to Lissia
	 * 
	 * @param appForm the data from webia
	 * @return the created policy
	 */
	PolicyDTO createPolicy(AppFormDTO appForm);

	/**
	 * activate a policy in Lissia (status to "InForce")
	 * 
	 * @param id the policy Id
	 * @param effectiveDate The effective date.
	 * @return the activated policy
	 */
	PolicyDTO activatePolicy(String id, Date effectiveDate);

	/**
	 * activate a policy in Lissia (status to "InForce")
	 * 
	 * @param id the policy Id
	 * @param effectiveDate The effective date.
	 * @return the activated policy
	 */
	PolicyDTO updatePolicyAdditionalPremium(String id, Date effectiveDate);

	/**
	 * Get policy incomplete details (same functionaly as Lissia 'incomplete details').
	 * 
	 * @param id The policy id.
	 * @return collection of policy incomplete details.
	 */
	Collection<String> getPolicyIncompleteDetails(String id);

	/**
	 * create the first cash transaction into Lissia for a new policy
	 * 
	 * @param appForm The application form
	 */
	Boolean createPolicyCashSuspense(AppFormDTO appForm);

	/**
	 * Get roles by policies according to a client.
	 * 
	 * @param clientId The client id.
	 * @return roles by policies.
	 */
	RolesByPoliciesDTO getRolesByPolicies(Integer clientId);

	/**
	 * get the life and death clauses for a given policy
	 * 
	 * @param polId - the policy id
	 * @return the life and death clauses
	 */
	PolicyClausesDTO getClauses(String polId);

	PolicyClausesDTO getClauses(String polId, String productCd, String lang);

	/**
	 * Save the policy score
	 * 
	 * @param policyId the policy
	 * @param scoreValue the score to save.
	 * @return True if the score has been successfully saved. Otherwise false
	 */
	Boolean saveScoreNewBusiness(String policyId, Integer scoreValue);

	/**
	 * Save the policy score
	 * 
	 * @param policyId the policy
	 * @param scoreValue the score to save.
	 * @return True if the score has been successfully saved. Otherwise false
	 */
	Boolean saveScoreLastTrans(String policyId, Integer scoreValue);

	/**
	 * Check if the policy is active.
	 * 
	 * @param policy The policy.
	 * @return True, if successful.
	 */
	boolean isActive(PolicyDTO policy);

	void abortCoverage(String policyId, Integer coverage);

	void surrenderPolicy(PolicySurrenderDTO surrenderDTO);

	/**
	 * get the life and death translated clauses for a given policy view
	 * @param clauses the life and death to translate
	 * @param productCd the policy product code
	 * @param lang the expected language if found (else another language)
	 * @return
	 */
	PolicyClausesDTO getTranslatedClauses(PolicyClausesDTO clauses, String productCd, String lang);

	
	/**
	 * Get policy IDs according to the Agent id and its category (BK, DB, AM).
	 * 
	 * @param agtId The agent id.
	 * @param category the agent category [BK|DB|AM]
	 * @return The policy ids
	 */
	List<String> getPoliciesByAgent(String partnerId, String partnerCategory);

	/**
	 * inserts a new withdrawal
	 * 
	 * @param policy id
	 * @return true if OK
	 */
	Boolean createWithdrawal(WithdrawalInputDTO input);

	/**
	 * inserts a new withdrawal
	 * 
	 * @param policy id
	 * @return true if OK
	 */
	Boolean createWithdrawal(List<WithdrawalInputDTO> inputs);

}
