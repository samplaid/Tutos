package lu.wealins.webia.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.WorkflowGroupDTO;

@Path("workflowGroup")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface LiabilityWorkflowGroupRESTService {

	/**
	 * Get the CPS workflow group.
	 * 
	 * @param context The security context.
	 * @return The CPS workflow group.
	 */
	@GET
	@Path("/cps")
	WorkflowGroupDTO getCPSWorkflowGroup(@Context SecurityContext context);

}
