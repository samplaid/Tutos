package lu.wealins.webia.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemActionsDTO;

@Path("workflow")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface LiabilityWorkflowRESTService {

	/**
	 * Get workflow actions according to the workItem id.
	 * 
	 * @param context The security context.
	 * @param workItemId The workItem id.
	 * @return The workflow actions.
	 */
	@GET
	@Path("/{id}/actions")
	WorkflowItemActionsDTO getWorkItemActions(@Context SecurityContext context, @PathParam("id") String workItemId);

	/**
	 * Get general information.
	 * @param context
	 * @param workflowItemId
	 * @return
	 */
	@GET
	@Path("/generalInformation/{id}")
	WorkflowGeneralInformationDTO getGeneralInformation(@Context SecurityContext context, @PathParam("id") String workflowItemId);
}
