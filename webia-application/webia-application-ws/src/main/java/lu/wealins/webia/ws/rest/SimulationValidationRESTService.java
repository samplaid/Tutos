package lu.wealins.webia.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.editing.services.enums.DocumentType;

@Path("simulationValidation")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface SimulationValidationRESTService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<String> validate(@Context SecurityContext context, @QueryParam("workflowItemId") Long workflowItemId, @QueryParam("documentType") DocumentType documentType);

}
