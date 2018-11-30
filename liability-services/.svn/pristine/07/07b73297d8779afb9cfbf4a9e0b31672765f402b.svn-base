package lu.wealins.liability.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.UserDTO;

/**
 * {@link UserRESTService} is a REST service responsible to manipulate User objects.
 *
 */
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface UserRESTService {

	/**
	 * Get the UserDTO corresponding to the trigram provided.
	 * 
	 * @param context the security context
	 * @param trigram the trigram
	 * @return the UserDTO corresponding to the trigram provided, or null if not found
	 */
	@GET
	@Path("getByTrigram")
	UserDTO getBytrigram(@Context SecurityContext context, @QueryParam("trigram") String trigram);

}
