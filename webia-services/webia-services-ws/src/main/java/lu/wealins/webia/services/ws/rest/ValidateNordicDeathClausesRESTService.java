package lu.wealins.webia.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.NordicValidationRequest;

@Path("validate")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface ValidateNordicDeathClausesRESTService {

	@POST
	@Path("nordic")
	Collection<String> validateDeathClauses(@Context SecurityContext context, NordicValidationRequest request);

}
