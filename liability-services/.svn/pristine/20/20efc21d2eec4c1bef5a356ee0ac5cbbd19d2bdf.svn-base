package lu.wealins.liability.services.core.business;

import java.util.List;
import java.util.Set;

import lu.wealins.common.dto.liability.services.ActivatePolicyRequest;
import lu.wealins.common.dto.liability.services.CreatePolicyCashSuspenseRequest;
import lu.wealins.common.dto.liability.services.NewBusinessDTO;
import lu.wealins.common.dto.liability.services.PoliciesForMathematicalReserveRequest;
import lu.wealins.common.dto.liability.services.PolicyClientRoleViewRequest;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyIdsSearchDTO;
import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.common.dto.liability.services.PolicyRecreateResponse;
import lu.wealins.common.dto.liability.services.PolicySearchRequest;
import lu.wealins.common.dto.liability.services.PolicySurrenderDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.RolesByPoliciesDTO;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.common.dto.liability.services.UpdateInputRequest;
import lu.wealins.common.dto.liability.services.WithdrawalInputDTO;
import lu.wealins.common.dto.liability.services.enums.CliPolRelationshipType;
import lu.wealins.common.dto.liability.services.enums.PolicyActiveStatus;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;

public interface PolicyService {

	/**
	 * Create the policy into Lissia from Webia using web service WSSNBSET
	 * 
	 * @param request The policy request.
	 * @return The policy.
	 */
	PolicyDTO createPolicy(NewBusinessDTO request);

	/**
	 * Set policy status to "InForce" in Lissia using web service WSSINFPL
	 * 
	 * @param request The request.
	 */
	void activatePolicy(ActivatePolicyRequest request);

	/**
	 * Set policy status to "InForce" in Lissia using web service WSSINFPL
	 * 
	 * @param request The request.
	 */
	void updatePolicy(ActivatePolicyRequest request);

	/**
	 * Get policy incomplete details (same functionaly as Lissia 'incomplete details').
	 * 
	 * @param id The policy id.
	 * @return The list of incomplete details.
	 */
	List<String> getPolicyIncompleteDetails(String id);

	/**
	 * Create the first policy transaction in Lissia using web service WSSCHSUS
	 * 
	 * @request The policy cash suspense request.
	 * @return The transaction created.
	 */
	Boolean createPolicyCashSuspense(CreatePolicyCashSuspenseRequest request);


	/**
	 * Get the policy according its id.
	 * 
	 * @param id The policy id.
	 * @return The policy.
	 */
	PolicyDTO getPolicy(String id);

	PolicyDTO getPolicy(PolicyClientRoleViewRequest request);

	PolicyEntity getPolicyEntity(String id);

	/**
	 * Get policy according to the workflow item id. The only way is to retrieve the 'Policy' metadata in order to have the policy number.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param usrId The user id.
	 * @return The policy.
	 */
	PolicyDTO getPolicy(Long workflowItemId, String usrId);

	/**
	 * Return true if the policy does exist.
	 * 
	 * @param id The policy id.
	 * @return True, if successful.
	 */
	boolean isExist(String id);
	
	/**
	 * Return true if the policy does exist based on its workflow item id.
	 * @param workflowItemId the workflow item id
	 * @return True, if successful.
	 */
	boolean isExistByWorkflowItemId(Long workflowItemId, String usrId);

	/**
	 * Get roles by policies for a client.
	 * 
	 * @param clientId The client id.
	 * @param cliPolRelationshipTypes client policy relationship types.
	 * @return The roles by policies for a client.
	 */
	RolesByPoliciesDTO getRolesByPolicies(Integer clientId, List<CliPolRelationshipType> cliPolRelationshipTypes);

	/**
	 * Get policies for an agent.
	 * 
	 * @param agtId The agent id.
	 * @return The policies for an agent.
	 */
	SearchResult<PolicyLightDTO> getBrokerPolicies(PolicySearchRequest request);

	/**
	 * Get the policies linked to the fund id.
	 * 
	 * @param fdsId The fund id.
	 * @return The policies.
	 */
	int countPolicies(String fdsId);

	/**
	 * Get Policies linked to specific fund id.
	 * 
	 * @param fdsId
	 *            The fund id.
	 * @return The policies.
	 */
	List<String> getPoliciesByFund(String fdsId);

	String getBrokerRefContract(String polId);

	String getPolicyCurrency(String polId);

	/**
	 * Generate a new policy number
	 * 
	 * @param product
	 * @return
	 */
	String getNewNumber(String product);

	/**
	 * Returns one page of the policy's search results. The search can be done by the policy id or/and the policy application form or/and its broker's contract reference. At lease one criterion is
	 * expected.
	 * 
	 * The page parameter is zero indexed, the first page has the number 0. If none or all of the 3 boolean are true, all policies will be include in the result
	 * 
	 * @param request
	 * @return
	 */
	SearchResult<PolicyDTO> searchPolicy(PolicySearchRequest request);

	/**
	 * Get policies for the holder.
	 * 
	 * @param polycHolder The policy holder id.
	 * @return The policies.
	 */
	Set<PolicyDTO> getPoliciesForHolder(Integer polycHolder);

	PolicyActiveStatus getPolicyActiveStatus(Integer status, Integer subStatus);

	/**
	 * return the last total valuation of all the policies linked to a given broker
	 * 
	 * @param agtId - the broker Id
	 * @param currency - the currency of the valuation
	 * @return the Policies Valuation
	 */
	PolicyValuationDTO getBrokerValuation(String agtId, String currency);

	/**
	 * Get policy light according to its id.
	 * 
	 * @param id The policy light id.
	 * @return The policy light.
	 */
	PolicyLightDTO getPolicyLight(String id);
	
	/**
	 * Get policy light according to its id.
	 * 
	 * @param id The policy light id.
	 * @return The policy light.
	 */
	SearchResult<PolicyDTO> getPoliciesForMathematicalReserve(PoliciesForMathematicalReserveRequest request);

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
	 * Update the field of the policy related to the 'Update Input step'
	 * 
	 * @param request the update input request.
	 * @param user the user performing the update
	 */
	void updateForUpdateInputStep(UpdateInputRequest request, String user);

	PolicyEntity save(PolicyEntity policyEntity);

	/**
	 * Abort a policy.
	 * 
	 * @param policyId the policy id.
	 * @return True if the policy was aborted.
	 */
	Boolean abort(String policyId);

	/**
	 * Recreate a new version of a policy.
	 * 
	 * @param workflowItemId the workflow id.
	 * @param userId id of the user in the security context.
	 * @return the new id generated.
	 */
	PolicyRecreateResponse recreate(Long workflowItemId, String userId);

	Boolean abortCoverage(String policyId, Integer coverage);

	/**
	 * Get the policy Ids for a given agent directly linked to policy (Asset manager and bank dep are not included)
	 * @param agtId
	 * @return
	 */
	List<String> getAgentPolicies(String agtId);

	PolicyLightDTO getPolicyLight(Long workflowItemId, String userId);

	Boolean createWithdrawal(WithdrawalInputDTO withdrawalInput);

	Boolean createWithdrawal(List<WithdrawalInputDTO> withdrawalInputs);

	List<String> searchPolicyIds(PolicyIdsSearchDTO policyIdsSearchDTO);

	Boolean surrenderPolicy(PolicySurrenderDTO policySurrenderDTO);
}