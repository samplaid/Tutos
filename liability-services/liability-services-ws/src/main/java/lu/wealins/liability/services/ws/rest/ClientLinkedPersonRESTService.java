package lu.wealins.liability.services.ws.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.ClientLinkedPersonDTO;

@RolesAllowed("rest-user")
@Path("/clientLinkedPerson")
@Produces(MediaType.APPLICATION_JSON)
public interface ClientLinkedPersonRESTService {

	/**
	 * Get the client according its id.
	 * 
	 * @param context The security context.
	 * @param id The client id.
	 * @return The client. Return a <code>ObjectNotFoundException</code> if the client does not exist.
	 */
	@GET
	@Path("{id}")
	List<ClientLinkedPersonDTO> getClientLinkedPerson(@Context SecurityContext context, @PathParam("id") Long id);

}
