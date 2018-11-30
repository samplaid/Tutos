package lu.wealins.webia.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.RelaunchStepRequest;
import lu.wealins.common.dto.webia.services.RelaunchStepResponse;

@Path("relaunchStep")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface RelaunchStepUtilityRESTService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	RelaunchStepResponse relaunchStep(@Context SecurityContext context, RelaunchStepRequest relaunchCompleteStepRequest);

}
