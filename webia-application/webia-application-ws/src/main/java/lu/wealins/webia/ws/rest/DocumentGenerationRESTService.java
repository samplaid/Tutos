package lu.wealins.webia.ws.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.webia.ws.rest.request.DocumentGenerationRequest;
import lu.wealins.webia.ws.rest.request.DocumentGenerationResponse;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Path("/documentGeneration")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface DocumentGenerationRESTService {

	@POST
	@Path("/managementMandate")
	@Consumes(MediaType.APPLICATION_JSON)
	DocumentGenerationResponse generateManagementMandate(@Context SecurityContext context, DocumentGenerationRequest request);

	@POST
	@Path("/managementMandateEnd")
	@Consumes(MediaType.APPLICATION_JSON)
	DocumentGenerationResponse generateManagementMandateEnd(@Context SecurityContext context, DocumentGenerationRequest request);
	
	@POST
	@Path("/generate")
	@Consumes(MediaType.APPLICATION_JSON)
	DocumentGenerationResponse generateDocument(@Context SecurityContext context, DocumentGenerationRequest request);

	@POST
	@Path("/upload")
	@Consumes(MediaType.APPLICATION_JSON)
	DocumentGenerationResponse uploadDocument(@Context SecurityContext context, DocumentGenerationRequest request);

	@POST
	@Path("/generateSouscriptionFollowUp")
	@Consumes(MediaType.APPLICATION_JSON)
	DocumentGenerationResponse generatefollowUp(@Context SecurityContext context, AppFormDTO request);

	@POST
	@Path("/generateSurrender")
	@Consumes(MediaType.APPLICATION_JSON)
	DocumentGenerationResponse generateFrenchTaxAvenant(@Context SecurityContext context, EditingRequest editingRequest);
	
}
