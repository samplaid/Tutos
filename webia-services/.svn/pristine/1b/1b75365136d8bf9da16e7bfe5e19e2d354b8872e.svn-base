package lu.wealins.webia.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

/**
 * SequenceRESTService is a REST service responsible to manipulate Sequence objects.
 *
 */
@Path("sequence")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface SequenceRESTService {

	@GET
	@Path("{target}")
	String generateNextId(@Context SecurityContext context, @PathParam("target") String target);

}
