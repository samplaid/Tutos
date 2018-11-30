package lu.wealins.webia.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.WorkflowQueueDTO;

/**
 * WorkflowQueueRESTService is a REST service responsible to manipulate Workflow Queue objects.
 *
 */
@Path("workflowQueue")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface WorkflowQueueRESTService {

	/**
	 * Get workflow queue according its id.
	 * 
	 * @param context The security context.
	 * @param workflowQueueId The workflow queue id.
	 * @return The workflow queue.
	 */
	@GET
	@Path("/{workflowQueueId}")
	WorkflowQueueDTO getWorkflowQueue(@Context SecurityContext context, @PathParam("workflowQueueId") Integer workflowQueueId);

	/**
	 * Check if the user is assigned to the workflow queue.
	 * 
	 * @param context The security context.
	 * @param workflowQueueId The workflow queue id.
	 * @param userId The user id.
	 * @return True, if successful.
	 */
	@GET
	@Path("/isAssignTo/{workflowQueueId}")
	boolean isAssignTo(@Context SecurityContext context, @PathParam("workflowQueueId") Integer workflowQueueId, @QueryParam("userId") String userId);

	/**
	 * Get workflow queues according to the user id.
	 * 
	 * @param context The security context.
	 * @param usrId The user id.
	 * @return The workflow queues.
	 */
	@GET
	@Path("/hasPersonalQueue/{usrId}")
	boolean hasPersonalQueue(@Context SecurityContext context, @PathParam("usrId") String usrId);
}
