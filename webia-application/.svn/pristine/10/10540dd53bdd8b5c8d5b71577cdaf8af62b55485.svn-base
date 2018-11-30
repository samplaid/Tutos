package lu.wealins.webia.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentSearchRequest;
import lu.wealins.common.dto.liability.services.SearchResult;

@Path("/agent")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface LiabilityAgentRESTService {

	/**
	 * Agent search service. An agent could be
	 * 
	 * BK: Broker AM: Asset manager DB: Deposit bank FS: Sales person
	 * 
	 * The page is one base indexed, the first page's number is one.
	 * 
	 * @param context The security context.
	 * @param agentSearchRequest The agent search request.
	 * 
	 * @return The agents.
	 * 
	 */
	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	SearchResult<AgentDTO> search(
			@Context SecurityContext context, AgentSearchRequest agentSearchRequest);

	/**
	 * Get an agent according its id.
	 * 
	 * @param context The security context.
	 * @param id The agent id.
	 * @return The agent
	 */
	@GET
	@Path("/{id}")
	AgentDTO get(
			@Context SecurityContext context,
			@PathParam("id") String id);

	/**
	 * Get the Wealins broker.
	 * 
	 * @param context The security context.
	 * @return The Wealins broker
	 */
	@GET
	@Path("/wealinsBroker")
	AgentDTO getWealinsBroker(@Context SecurityContext context);
	
	/**
	 * Get the Wealins asset manager.
	 * 
	 * @param context The security context.
	 * @return The Wealins asset manager
	 */
	@GET
	@Path("/wealinsAssetManager")
	AgentDTO getWealinsAssetManager(@Context SecurityContext context);
	
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	AgentDTO update(@Context SecurityContext context, AgentDTO agentDTO);
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	AgentDTO create(@Context SecurityContext context, AgentDTO agentDTO);
}
