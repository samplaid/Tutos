package lu.wealins.webia.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.CheckWorkflowDTO;

@Path("checkWorkflow")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface CheckWorkflowRESTService {

	/**
	 * Get the check workflow according to its check code.
	 * 
	 * @param context The security context.
	 * @param checkCode The check code.
	 * @return The check workflow.
	 */
	@GET
	@Path("load")
	CheckWorkflowDTO getCheckWorkflow(@Context SecurityContext context, @QueryParam("checkCode") String checkCode);

}
