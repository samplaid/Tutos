package lu.wealins.liability.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.ClientClaimsDetailDTO;

@RolesAllowed("rest-user")
@Path("/clientClaimsDetail")
@Produces(MediaType.APPLICATION_JSON)
public interface ClientClaimsDetailRESTService {

	/**
	 * Get the client claims details according its id.
	 * 
	 * @param context The security context.
	 * @param id The client id.
	 * @return the list of client claims details.
	 */
	@GET
	@Path("{id}")
	Collection<ClientClaimsDetailDTO> getClientClaimsDetails(@Context SecurityContext context, @PathParam("id") Integer id);

	/**
	 * Save or update an client Claims Detail.
	 * 
	 * @param context The security context.
	 * @param clientClaimsDetail The client Claims Detail to save or update.
	 * @return the clientClaimsDetail saved.
	 */
	@POST
	@Path("save")
	ClientClaimsDetailDTO save(@Context SecurityContext context, ClientClaimsDetailDTO clientClaimsDetail);
	
	/**
	 * Save an client Claims Detail to notify the Death of the client.
	 * 
	 * @param context The security context.
	 * @param clientClaimsDetail The client Claims Detail to save.
	 * @return the clientClaimsDetail saved.
	 */
	@POST
	@Path("notifyDeath")
	ClientClaimsDetailDTO notifyDeath(@Context SecurityContext context, ClientClaimsDetailDTO clientClaimsDetail);
}
