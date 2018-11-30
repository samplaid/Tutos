package lu.wealins.liability.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.UpdateInputRequest;
import lu.wealins.common.dto.liability.services.UpdateInputResponse;

@RolesAllowed("rest-user")
@Path("/updateInput")
@Produces(MediaType.APPLICATION_JSON)
public interface UpdateInputRESTService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	UpdateInputResponse updateInput(@Context SecurityContext context, UpdateInputRequest request);
}
