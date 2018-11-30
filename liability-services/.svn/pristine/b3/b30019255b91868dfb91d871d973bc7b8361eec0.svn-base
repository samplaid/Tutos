package lu.wealins.liability.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.ControlDTO;

@RolesAllowed("rest-user")
@Path("/control")
@Produces(MediaType.APPLICATION_JSON)
public interface ControlRESTService {

	/**
	 * Get an Control according its id.
	 * 
	 * @param context The security context.
	 * @param ctlId The Control id.
	 * @return The Control
	 */
	@GET
	@Path("/{ctlId}")
	ControlDTO get(@Context SecurityContext context, @PathParam("ctlId") String ctlId);

}
