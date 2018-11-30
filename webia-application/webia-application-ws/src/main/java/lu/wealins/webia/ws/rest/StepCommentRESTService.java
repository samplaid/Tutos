package lu.wealins.webia.ws.rest;

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

@Path("stepComment")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface StepCommentRESTService {
	
	/**
	 * Load all the comments of a given workflow Item.  
	 * 
	 * @param context The security context.
	 * @param workflowItemId The workflow item id.
	 * @return The comments.
	 */
	@GET
	@Path("/workflowItemId/{id}")
	Collection<StepCommentDTO> getStepComments(@Context  SecurityContext context, @PathParam("id") Long workflowItemId);

	/**
	 * Add a comment on a given workflow Item.  
	 * 
	 * @param context The security context.
	 * @param StepCommentRequest The request with a workflow Item id, a comment and an optional due date.
	 * @return
	 * @throws Exception 
	 */
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	StepCommentDTO addStepComment(@Context  SecurityContext context, StepCommentRequest request) throws Exception;


}
