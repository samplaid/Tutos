package lu.wealins.webia.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.PolicyClausesDTO;
import lu.wealins.common.dto.liability.services.RolesByPoliciesDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDTO;


@Path("policy")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface LiabilityPolicyRESTService {

	@GET
	@Path("/clauses")
	PolicyClausesDTO getClauses(@Context SecurityContext context, @QueryParam("id") String polId, @QueryParam("productCd") String productCd, @QueryParam("lang") String lang);

	/**
	 * Get roles by policies for a client.
	 * 
	 * @param context The security context
	 * @param clientId The client id.
	 * @return The roles by policies.
	 */
	@GET
	@Path("/rolesByPolicies/{clientId}")
	RolesByPoliciesDTO getRolesByPolicies(@Context SecurityContext context, @PathParam("clientId") Integer clientId);

	/**
	 * Get policy transactions history.
	 * 
	 * @param context
	 *            The security context
	 * @param policyId
	 *            The policy id.
	 * @return transactions history of policy.
	 */
	@GET
	@Path("/transactions")
	Collection<PolicyTransactionsHistoryDTO> getPoliciesHistory(@Context SecurityContext context,
			@QueryParam("policyId") String policyId);

}
