package lu.wealins.liability.services.ws.rest;

import java.util.Date;

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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemActionsDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemAllActionsDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.liability.services.WorkflowTriggerActionDTO;

@RolesAllowed("rest-user")
@Path("/workflow")
@Produces(MediaType.APPLICATION_JSON)
public interface WorkflowRESTService {

	/**
	 * Save metadata.
	 * 
	 * @param context The security context.
	 * @param workflowItemData The workflow item data
	 * @return The rest response.
	 */
	@POST
	@Path("/saveMetadata")
	@Consumes(MediaType.APPLICATION_JSON)
	Response saveMetadata(@Context SecurityContext context, WorkflowItemDataDTO workflowItemData);

	/**
	 * Submit the workflow action.
	 * 
	 * @param context The security context.
	 * @param workflowTriggerAction The workflow action.
	 * @return The rest response.
	 */
	@POST
	@Path("/submit")
	@Consumes(MediaType.APPLICATION_JSON)
	Response submit(@Context SecurityContext context, WorkflowTriggerActionDTO workflowTriggerAction);
	
	/**
	 * Get all workflow item actions.
	 * 
	 * @param context The security context.
	 * @param workItemId The workflow item id.
	 * @param usrId The user id.
	 * @return The workflow item actions.
	 */
	@GET
	@Path("/{id}/allActions")
	WorkflowItemAllActionsDTO getAllWorkItemActions(@Context SecurityContext context, @PathParam("id") Integer workItemId, @QueryParam("usrId") String usrId);

	/**
	 * Get workflow item actions.
	 * 
	 * @param context The security context.
	 * @param workItemId The workflow item id.
	 * @param usrId The user id.
	 * @return The workflow item actions.
	 */
	@GET
	@Path("/{id}/actions")
	WorkflowItemActionsDTO getWorkItemActions(@Context SecurityContext context, @PathParam("id") Integer workItemId, @QueryParam("usrId") String usrId);

	/**
	 * Get the workflow general information.
	 * 
	 * @param context The security context.
	 * @param workflowItemId The workflow item id.
	 * @param usrId The user id.
	 * @return The workflow general information.
	 */
	@GET
	@Path("/generalInformation/{id}")
	WorkflowGeneralInformationDTO getGeneralInformation(@Context SecurityContext context, @PathParam("id") Long workflowItemId, @QueryParam("usrId") String usrId);

	/**
	 * Get workflow item accord
	 * 
	 * @param workflowItemId
	 * @param usrId
	 * @return
	 */
	@GET
	@Path("/workflowItem/{id}")
	WorkflowItemDataDTO getWorkflowItem(@Context SecurityContext context, @PathParam("id") Long workflowItemId, @QueryParam("usrId") String usrId);

	/**
	 * 
	 * @param context
	 * @param workflowItemId The workflow item id.
	 * @param dueDate the due Date to set as string format MM.dd.yyyy
	 * @param usrId The user id.
	 * @return
	 */
	@GET
	@Path("/workflowItem/{id}/dueDate")
	Response updateDueDateWorkflowItem(@Context SecurityContext context, @PathParam("id") Long workflowItemId, @QueryParam("dueDate") String dueDate, @QueryParam("usrId") String usrId);
}
