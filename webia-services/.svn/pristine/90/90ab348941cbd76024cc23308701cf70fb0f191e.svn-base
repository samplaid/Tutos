package lu.wealins.webia.services.ws.rest;

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

@Path("checkStep")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface CheckStepRESTService {

	/**
	 * Get comments history having an answer not null.
	 * 
	 * @param context The security context.
	 * @param workflowItemId The workflow itemid.
	 * @return The comments history.
	 */
	@GET
	@Path("commentsHistory")
	Collection<CheckStepDTO> getCommentsHistory(@Context SecurityContext context, @QueryParam("workflowItemId") Integer workflowItemId, @QueryParam("workflowItemTypeId") Integer workflowItemTypeId);

}
