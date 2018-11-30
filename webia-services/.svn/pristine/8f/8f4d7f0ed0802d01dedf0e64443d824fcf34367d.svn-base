package lu.wealins.webia.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.StepCommentDTO;
import lu.wealins.common.dto.webia.services.StepCommentRequest;

/**
 * StepServiceRESTService is a REST service responsible to manipulate Step objects.
 * 
 * @param <T>
 *
 */
@Path("stepComment")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface StepCommentRESTService {

	/**
	 * Get all comments of the workflow item id.
	 * 
	 * @param context The security context.
	 * @param workflowItemId The workflow Item id.
	 * @return The step.
	 */
	@GET
	@Path("/{workflowItemId}")
	Collection<StepCommentDTO> getStepComments(@Context SecurityContext context, @PathParam("workflowItemId") Long workflowItemId);


	/**
	 * Add a comment.
	 * 
	 * @param context The security context.
	 * @param StepCommentRequest The request.
	 * @return the comment added
	 */
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	StepCommentDTO addStepComment(@Context SecurityContext context, StepCommentRequest request);

	/**
	 * Update a comment.
	 * 
	 * @param context The security context.
	 * @param StepCommentRequest The request.
	 * @return the comment added
	 */
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	StepCommentDTO updateStepComment(@Context SecurityContext context, StepCommentRequest request);


	
}
