package lu.wealins.liability.services.ws.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.AgentCategoryDTO;

@RolesAllowed("rest-user")
@Path("/agentCategory")
@Produces(MediaType.APPLICATION_JSON)
public interface AgentCategoryRESTService {
	
	/**
	 * Retrieve all agent categories.
	 * @return a list of agent categories.
	 */
	@GET
	List<AgentCategoryDTO> retrieveAll(@Context SecurityContext context);
	
}
