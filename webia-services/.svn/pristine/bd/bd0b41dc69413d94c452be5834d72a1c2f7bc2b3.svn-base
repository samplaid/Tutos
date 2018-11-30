package lu.wealins.webia.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.WorkflowUserDTO;

/**
 * WorkflowUserRESTService is a REST service responsible to manipulate Workflow Group objects.
 *
 */
@Path("workflowUser")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface WorkflowUserRESTService {

	/**
	 * Get the workflow user according its id.
	 * 
	 * @param context The security context.
	 * @param id The workflow user id.
	 * @return The workflow user.
	 */
	@GET
	@Path("/{id}")
	WorkflowUserDTO getWorkflowUser(@Context SecurityContext context, @PathParam("id") String usrId);

	/**
	 * Get the current workflow user.
	 * 
	 * @param context The security context.
	 * @return The workflow user.
	 */
	@GET
	WorkflowUserDTO getWorkflowUser(@Context SecurityContext context);

	/**
	 * Get the workflow user according its login.
	 * 
	 * @param context The security context.
	 * @param id The workflow user login.
	 * @return The workflow user.
	 */
	@GET
	@Path("/login/{login}")
	WorkflowUserDTO getWorkflowUserWithLogin(@Context SecurityContext context, @PathParam("login") String login);

	/**
	 * Get the workflow user according its token.
	 * 
	 * @param context The security context.
	 * @param token The token.
	 * @return The workflow user.
	 */
	@GET
	@Path("/token/{token}")
	WorkflowUserDTO getWorkflowUserWithToken(@Context SecurityContext context, @PathParam("token") String userToken);

	/**
	 * Get workflow users.
	 * 
	 * @param context The security context.
	 * @return The workflow users.
	 */
	@GET
	@Path("/all")
	Collection<WorkflowUserDTO> getWorkflowUsers(@Context SecurityContext context);
}
