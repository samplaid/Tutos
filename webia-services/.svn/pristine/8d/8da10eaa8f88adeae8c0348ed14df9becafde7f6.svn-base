package lu.wealins.webia.services.ws.rest;

import java.util.Collection;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("workflow")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface WorkflowUtilityServiceRESTService {

	/**
	 * Get workflow item Ids according to the workflow item type name and the
	 * workflow action.
	 * 
	 * @param context
	 *            The security context.
	 * @param workflowItemTypeName
	 *            The workflow item type name.
	 * @param workflowAction
	 *            The workflow action.
	 * @param excludedStatus
	 *            the excluded workflow item status
	 * @return The workflow item Ids.
	 */
	@GET
	@Path("workflowItemsIds")
	Collection<Long> getWorkflowItemIds(@Context SecurityContext context,
			@QueryParam("workflowItemType") Integer workflowItemType, @QueryParam("actionRequired") Integer actionRequired, @QueryParam("excludedStatus") List<Integer> excludedStatus);

	/**
	 * Retrieve the workflow item id by the policy.
	 * 
	 * @param context
	 *            the security context
	 * @param policyId
	 *            the policy id
	 * @return Null if it is not found
	 */
	@GET
	@Path("findByPolicy")
	Long getWorkFlowItemId(@Context SecurityContext context, @QueryParam("policyId") String policyId);

}
