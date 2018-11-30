package lu.wealins.liability.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.ClientInjectionRequest;
import lu.wealins.common.dto.liability.services.ClientInjectionResponse;

@Path("/clientInjection")
@Produces(MediaType.APPLICATION_JSON)
public interface ClientInjectionRESTService {

	@POST
	@Path("/inject")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed("rest-user")
	public ClientInjectionResponse runImport(@Context SecurityContext context, ClientInjectionRequest request);

}
