package lu.wealins.webia.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.CheckStepDTO;

@Path("/checkSteps")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface CheckStepRESTService {

	/**
	 * Load the checkSteps of a step with the specified workflow item id, and load the checks with the step (action) name. If the step name is not provided, the current workflow item action will be
	 * used instead.
	 * 
	 * @param context The security context.
	 * @param workflowItemId The workflow item id.
	 * @param stepWorkflow
	 * @return a collection of checkStep.
	 */
	@GET
	Collection<CheckStepDTO> getCheckSteps(@Context SecurityContext context, @QueryParam("workflowItemId") Integer workflowItemId, @QueryParam("stepWorkflow") String stepWorkflow);

}
