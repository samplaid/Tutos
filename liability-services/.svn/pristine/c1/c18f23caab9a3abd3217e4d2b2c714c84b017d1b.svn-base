package lu.wealins.liability.services.ws.rest;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.ActivatePolicyRequest;
import lu.wealins.common.dto.liability.services.CreatePolicyCashSuspenseRequest;
import lu.wealins.common.dto.liability.services.InsuredDTO;
import lu.wealins.common.dto.liability.services.NewBusinessDTO;
import lu.wealins.common.dto.liability.services.PoliciesForMathematicalReserveRequest;
import lu.wealins.common.dto.liability.services.PolicyClausesDTO;
import lu.wealins.common.dto.liability.services.PolicyClientRoleViewRequest;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderDTO;
import lu.wealins.common.dto.liability.services.PolicyIdsSearchDTO;
import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.common.dto.liability.services.PolicyRecreateResponse;
import lu.wealins.common.dto.liability.services.PolicyScoreRequest;
import lu.wealins.common.dto.liability.services.PolicySearchRequest;
import lu.wealins.common.dto.liability.services.PolicySurrenderDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.RolesByPoliciesDTO;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.common.dto.liability.services.WithdrawalInputDTO;


@RolesAllowed("rest-user")
@Path("/policy")
@Produces(MediaType.APPLICATION_JSON)
public interface PolicyRESTService {

	/**
	 * Get the policy according its id.
	 * 
	 * @param context The security context.
	 * @param id The policy id.
	 */
	@GET
	@Path("/")
	PolicyDTO getPolicy(@Context SecurityContext context, @QueryParam("id") String id);

	@GET
	@Path("/fund")
	List<String> getPoliciesByFund(@Context SecurityContext context, @QueryParam("fundId") String fundId);

	/**
	 * Get policy IDs according to the Agent id and its category (BK, DB, AM).
	 * 
	 * @param context The security context.
	 * @param agtId The agent id.
	 * @param category the agent category [BK|DB|AM]
	 * @return The policy ids
	 */
	@GET
	@Path("/agent")
	List<String> getPoliciesByAgent(@Context SecurityContext context, @QueryParam("agtId") String agtId, @QueryParam("category") String category ) throws Exception;
	
	@POST
	@Path("/clientRoleView")
	PolicyDTO getPolicy(@Context SecurityContext context, PolicyClientRoleViewRequest policyClientRoleViewRequest);

	/**
	 * Get policy according to the workflow item id. The only way is to retrieve the 'Policy' metadata in order to have the policy number.
	 * 
	 * @param context The security context.
	 * @param workflowItemId The workflow item id.
	 * @return The policy
	 */
	@GET
	@Path("/workflowItemId/{workflowItemId}")
	PolicyDTO getPolicy(@Context SecurityContext context, @PathParam("workflowItemId") Long workflowItemId);

	@GET
	@Path("/policyLight/workflowItemId/{workflowItemId}")
	PolicyLightDTO getPolicyLight(@Context SecurityContext context, @PathParam("workflowItemId") Long workflowItemId);

	/**
	 * Get the policy light according its id.
	 * 
	 * @param context The security context.
	 * @param id The policy light id.
	 * @return The policy light.
	 */
	@GET
	@Path("/policyLight")
	PolicyLightDTO getPolicyLight(@Context SecurityContext context, @QueryParam("id") String id);

	/**
	 * Policy valuation of the given date. The date is optional, if not given the current date will be used.
	 * 
	 * @param ctx
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	@GET
	@Path("valuation")
	PolicyValuationDTO getValuation(@Context SecurityContext ctx,
			@QueryParam("id") String id,
			@QueryParam("date") String date,
			@QueryParam("currency") String currency) throws ParseException;

	/**
	 * Get roles by policies for a client.
	 * 
	 * @param context The security context
	 * @param clientId The client id.
	 * @param cliPolRelationshipTypes client policy relationship types.
	 * @return The roles by policies.
	 */
	@GET
	@Path("/rolesByPolicies/{clientId}")
	RolesByPoliciesDTO getRolesByPolicies(@Context SecurityContext context, @PathParam("clientId") Integer clientId,
			@QueryParam("cliPolRelationshipTypes") List<Integer> cliPolRelationshipTypes);

	/**
	 * Get policies for a broker.
	 * 
	 * @param context
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/brokerPolicies")
	SearchResult<PolicyLightDTO> getBrokerPolicies(@Context SecurityContext context, PolicySearchRequest request);

	/**
	 * Check if the policy exist.
	 * 
	 * @param context The security context.
	 * @param id The policy id.
	 */
	@GET
	@Path("/exist")
	Boolean isExist(@Context SecurityContext context, @QueryParam("id") String id);
	
	/**
	 * Check if the policy exist.
	 * 
	 * @param context The security context.
	 * @param id The policy id.
	 */
	@GET
	@Path("/existByWorkflowItemId")
	Boolean isExistByWorkflowItemId(@Context SecurityContext context, @QueryParam("workflowItemId") Long workflowItemId);

	/**
	 * Generate a new policy number for the specified product.
	 * 
	 * @param ctx
	 * @param product
	 * @return
	 */
	@GET
	@Path("/getNewNumber")
	String getNewNumber(@Context SecurityContext ctx, @QueryParam("product") String product);

	/**
	 * Policy search service : the filter can be part of policy id, Application Form, Broker's contract reference. It will search all the policies 'containing' those criteria Operator AND is used in
	 * case of more than one criterion is given
	 * 
	 * @param context
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	SearchResult<PolicyDTO> search(@Context SecurityContext context, PolicySearchRequest request);

	/**
	 * Return the policy's death and maturity beneficiary clauses.
	 * 
	 * @param ctx
	 * @param policy
	 * @return
	 */
	@GET
	@Path("/clauses")
	PolicyClausesDTO clauses(@Context SecurityContext ctx, @QueryParam("id") String policy);

	/**
	 * Create the policy into Lissia from Webia using web service WSSNBSET
	 * 
	 * @param request The policy request.
	 * @return The policy.
	 */
	@POST
	@Path("/create")
	PolicyDTO createPolicy(@Context SecurityContext ctx, NewBusinessDTO request);

	/**
	 * Set policy status to "InForce" in Lissia using web service WSSINFPL
	 * 
	 * @param request The request.
	 * @return the policy
	 */
	@POST
	@Path("/activate")
	PolicyDTO activatePolicy(@Context SecurityContext ctx, ActivatePolicyRequest request);

	/**
	 * Set policy status to "InForce" in Lissia using web service WSSINFPL
	 * 
	 * @param request The request.
	 * @return the policy
	 */
	@POST
	@Path("/updatePolicyAdditionalPremium")
	PolicyDTO updatePolicy(@Context SecurityContext ctx, ActivatePolicyRequest request);

	/**
	 * Get policy incomplete details (same functionaly as Lissia 'incomplete details').
	 * 
	 * @param ctx The security context.
	 * @param id The policy id.
	 * @return List of policy incomplete details.
	 */
	@GET
	@Path("/incompleteDetails")
	List<String> getPolicyIncompleteDetails(@Context SecurityContext ctx, @QueryParam("id") String id);

	/**
	 * Create the first policy transaction in Lissia using web service WSSCHSUS
	 * 
	 * @param id The policy id.
	 * @param amount The transaction amount.
	 * @param currency The transaction currency.
	 * @param reference The transaction reference.
	 * @param detail Any detail on the transaction.
	 * 
	 * @return the first transaction created
	 */
	@POST
	@Path("/createPolicyCashSuspense")
	Boolean createPolicyCashSuspense(@Context SecurityContext ctx, CreatePolicyCashSuspenseRequest request);
	
	/**
	 * Set policy status to "InForce" in Lissia using web service WSSINFPL
	 * 
	 * @param id The policy id.
	 * @param currency The valuation currency.
	 * @return the valuation
	 */
	@GET
	@Path("/brokerValuation")
	PolicyValuationDTO getBrokerValuation(@Context SecurityContext ctx, @QueryParam("id") String id, @QueryParam("currency") String currency);
	
	
	/**
	 * Get policies for mathematical reserve
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/policiesForMathematicalReserve")
	SearchResult<PolicyDTO> getPoliciesForMathematicalReserve(@Context SecurityContext context, PoliciesForMathematicalReserveRequest request);

	/**
	 * Only save the policy score.
	 * 
	 * @param ctx a security context
	 * @param policyId the policy in which the score will be saved
	 * @return True if the score has been saved. Otherwise false.
	 */
	@POST
	@Path("/saveScoreNewBusiness")
	Boolean saveScoreNewBusiness(@Context SecurityContext ctx, PolicyScoreRequest scoreRequest);

	/**
	 * Only save the policy score.
	 * 
	 * @param ctx a security context
	 * @param policyId the policy in which the score will be saved
	 * @return True if the score has been saved. Otherwise false.
	 */
	@POST
	@Path("/saveScoreLastTrans")
	Boolean saveScoreLastTrans(@Context SecurityContext ctx, PolicyScoreRequest scoreRequest);

	/**
	 * Abort a policy.
	 * 
	 * @param policyId the policy to be aborted.
	 * @return True if the policy was aborted.
	 */
	@GET
	@Path("/abort")
	Boolean abort(@Context SecurityContext ctx, @QueryParam("policyId") String policyId);

	@GET
	@Path("/recreate")
	PolicyRecreateResponse recreate(@Context SecurityContext context, @QueryParam("workflowItemId") Long workflowItemId);


	/**
	 * Get policy valuations after transaction.
	 * 
	 * @param transactionId
	 *            the transactionId after which valuations are finded.
	 * @return policy transactions.
	 */
	@GET
	@Path("/frenchTaxPolicyValuationAfterTransaction")
	Collection<lu.wealins.common.dto.liability.services.PolicyTransactionValuationDTO> getPolicyValuationAfterTransaction(
			@Context SecurityContext context,
			@QueryParam("transactionId") Long transactionId);

	@GET
	@Path("/abortCoverage")
	Boolean abortCoverage(@Context SecurityContext context, @QueryParam("policyId") String policyId, @QueryParam("coverage") Integer coverage);

	/**
	 * Retrieve the list of the deceased insured of the specified policy.
	 * 
	 * @param context the security context
	 * @param policyId the policy Id
	 * @return a {@link Collection} of {@link InsuredDTO}
	 */
	@GET
	@Path("/deceasedInsureds")
	Collection<InsuredDTO> getDeceasedInsureds(@Context SecurityContext context, @QueryParam("policyId") String policyId);

	/**
	 * Retrieve the list of the deceased policy Holders of the specified policy.
	 * 
	 * @param context the security context
	 * @param policyId the policy Id
	 * @return a {@link Collection} of {@link PolicyHolderDTO}
	 */
	@GET
	@Path("/deceasedHolders")
	Collection<PolicyHolderDTO> getDeceasedHolders(@Context SecurityContext context, @QueryParam("policyId") String policyId);

	@POST
	@Path("/createWithdrawal")
	Boolean createWithdrawal(@Context SecurityContext ctx, WithdrawalInputDTO input);

	@POST
	@Path("/createWithdrawals")
	Boolean createWithdrawals(@Context SecurityContext ctx, List<WithdrawalInputDTO> inputs);

	@POST
	@Path("/searchPolicyIds")
	List<String> searchPolicyIds(PolicyIdsSearchDTO policyIdsSearchDTO);

	@POST
	@Path("/surrender")
	Boolean surrenderPolicy(PolicySurrenderDTO policySurrenderDTO);
}
