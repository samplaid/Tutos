package lu.wealins.webia.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.WorkflowGroupDTO;

/**
 * WorkflowGroupRESTService is a REST service responsible to manipulate Workflow Group objects.
 *
 */
@Path("workflowGroup")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface WorkflowGroupRESTService {

	/**
	 * Get the workflow group according its id.
	 * 
	 * @param context The security context.
	 * @param id The workflow group id.
	 * @return The workflow group.
	 */
	@GET
	@Path("/{id}")
	WorkflowGroupDTO getWorkflowGroup(@Context SecurityContext context, @PathParam("id") Integer id);

	@GET
	@Path("/")
	Collection<WorkflowGroupDTO> getWorkflowGroupsByUser(@Context SecurityContext context, @QueryParam("userId") String userId);

}
