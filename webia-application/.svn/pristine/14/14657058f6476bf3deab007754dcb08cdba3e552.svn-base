package lu.wealins.webia.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.StepLightDTO;


@Path("step")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface WebiaStepRESTService {

	/**
	 * Load the screen form with the specified workflow item id, and load the checks with the step (action) name. 
	 * If the step name is not provided, the current workflow item action will be used instead.  
	 * 
	 * @param context The security context.
	 * @param workflowItemId The workflow item id.
	 * @param stepWorkflow
	 * @return The step.
	 */
	@GET
	@Path("/")
	StepDTO getStep(@Context SecurityContext context, @QueryParam("workflowItemId") Integer workflowItemId, @QueryParam("stepWorkflow") String stepWorkflow);
	
	/**
	 * Get the steps according to its workflow item type id.
	 * 
	 * @param context The security context.
	 * @param workflowItemTypeId The workflow item type id.
	 * @return a collection of steps.
	 */
	@GET
	@Path("/stepsByWorkflowItemTypeId")
	Collection<StepLightDTO> getStepsByWorkflowItemTypeId(@Context SecurityContext context, @QueryParam("workflowItemTypeId") Integer workflowItemTypeId);
	
	/**
	 * Update the step.
	 * 
	 * @param context The security context.
	 * @param step The step
	 * @return The updated step.
	 */
	@POST
	@Path("update")
	StepDTO update(@Context SecurityContext context, StepDTO step);

	@POST
	@Path("completeWorkflowItemId")
	StepDTO complete(@Context SecurityContext context, Integer workflowItemId);
	
	/**
	 * Complete the step.
	 * 
	 * @param context The security context.
	 * @param step The step
	 * @return The completed step or errors
	 */
	@POST
	@Path("complete")
	StepDTO complete(@Context SecurityContext context, StepDTO step);

	/**
	 * Go to previous step.
	 * 
	 * @param context The security context.
	 * @param step The step
	 * @return The completed step or errors
	 */
	@POST
	@Path("previous")
	StepDTO previous(@Context SecurityContext context, StepDTO step);

	/**
	 * Abort the step.
	 * 
	 * @param context The security context.
	 * @param step The step
	 * @return The aborted step or errors
	 */
	@POST
	@Path("abort")
	StepDTO abort(@Context SecurityContext context, StepDTO step);

	/**
	 * Determine if the acceptance document could be generated at the current step of the provided workflow.
	 * 
	 * @param context the security context.
	 * @param workflowItemId the workflow item ID.
	 * @return <code>true</code> if the acceptance document could be generated on the current step, <code>false</code> otherwise
	 */
	@GET
	@Path("canGenerateAcceptanceDocument")
	Boolean canGenerateAcceptanceDocument(@Context SecurityContext context, @QueryParam("workflowItemId") Integer workflowItemId);
}
