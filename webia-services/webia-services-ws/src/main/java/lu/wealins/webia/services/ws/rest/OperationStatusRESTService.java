package lu.wealins.webia.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.UpdateOperationStatusRequest;

@Path("operationStatus")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface OperationStatusRESTService {

	@POST
	@Path("/update")
	Boolean updateStatus(@Context SecurityContext context, UpdateOperationStatusRequest request);

}
