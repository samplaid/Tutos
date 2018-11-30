package lu.wealins.webia.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.BatchInjectionControlResponse;
import lu.wealins.common.dto.webia.services.LissiaFilesInjectionControlRequest;

@Path("batch/lissiaFilesInjection")
@Produces(MediaType.APPLICATION_JSON)
public interface LissiaFilesInjectionRESTService {

	@POST
	@Path("check")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed("rest-user")
	BatchInjectionControlResponse checkLissiaFilesInjection(@Context SecurityContext context, LissiaFilesInjectionControlRequest request);

}