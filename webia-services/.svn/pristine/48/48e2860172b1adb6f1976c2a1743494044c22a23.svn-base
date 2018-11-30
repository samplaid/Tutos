package lu.wealins.webia.services.ws.rest;

import java.util.Collection;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.OperationDTO;

@Path("operation")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface OperationRESTService {

		/**
		 * Get the E-lissia opened operations for a given list of policy ID.
		 * 
		 * @param context The security context.
		 * @param policyIds The list of policy IDs
		 * @return The Operations.
		 */
		@POST
		@Path("/policies/opened")
		@Consumes(value = MediaType.APPLICATION_JSON)
		Collection<OperationDTO> getOpenedOperations(@Context SecurityContext context, List<String> policyIds);


}