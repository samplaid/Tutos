package lu.wealins.webia.services.ws.rest;

import java.util.Collection;
import java.util.List;

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

import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.StepLightDTO;

/**
 * StepServiceRESTService is a REST service responsible to manipulate Step objects.
 * 
 * @param <T>
 *
 */
@Path("step")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface StepRESTService {

	/**
	 * Get a step according its the step id and the workflow item id.
	 * 
	 * @param context The security context.
	 * @param stepId The step id.
	 * @param workflowItemId The workflow item id.
	 * @param workflowItemTypeId The workflow item type id.
	 * @return The step.
	 */
	@GET
	@Path("/")
	StepDTO getStep(@Context SecurityContext context, @QueryParam("workItemId") Integer workItemId, @QueryParam("stepWorkflow") String stepWorkflow,
			@QueryParam("workflowItemTypeId") Integer workflowItemTypeId);

	/**
	 * Get the checkSteps of a step according its the step id and the workflow item id.
	 * 
	 * @param context The security context.
	 * @param stepId The step id.
	 * @param workflowItemId The workflow item id.
	 * @param workflowItemTypeId The workflow item type id.
	 * @return a collection of checkStep.
	 */
	@GET
	@Path("/checkSteps")
	Collection<CheckStepDTO> getCheckSteps(@Context SecurityContext context, @QueryParam("workItemId") Integer workItemId, @QueryParam("stepWorkflow") String stepWorkflow,
			@QueryParam("workflowItemTypeId") Integer workflowItemTypeId);
	
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
	 * @param stepDTO The step.
	 * @return The updated step.
	 */
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	StepDTO updateStep(@Context SecurityContext context, StepDTO stepDTO);

	@POST
	@Path("/validateBeforeSave")
	@Consumes(MediaType.APPLICATION_JSON)
	List<String> validateBeforeSave(@Context SecurityContext context, StepDTO stepDTO);

	/**
	 * Complete the step.
	 * 
	 * @param context The security context.
	 * @param stepDTO The step.
	 * @return The completed step.
	 */
	@POST
	@Path("/complete")
	@Consumes(MediaType.APPLICATION_JSON)
	StepDTO completeStep(@Context SecurityContext context, StepDTO stepDTO);
	
	/**
	 * Abort the step.
	 * 
	 * @param context The security context.
	 * @param stepDTO The step.
	 * @return The aborted step.
	 */
	@POST
	@Path("/abort")
	@Consumes(MediaType.APPLICATION_JSON)
	StepDTO abortStep(@Context SecurityContext context, StepDTO stepDTO);

	@POST
	@Path("/validateBeforeCompleteStep")
	@Consumes(MediaType.APPLICATION_JSON)
	List<String> validateBeforeCompleteStep(@Context SecurityContext context, StepDTO stepDTO);;
}
