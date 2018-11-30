package lu.wealins.webia.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.AgentContactDTO;

@Path("/agentContact")
@RolesAllowed("rest-user")
@Produces(MediaType.APPLICATION_JSON)
public interface LiabilityAgentContactRESTService {

	/**
	 * Save or update an agent contact. It is composed by an agent owner and a contact type of agent. In order that the process executes successfully, the id of these two agents and the contact
	 * function must be provided and The record that is returned by agent owner id, contact id and the contact function must be unique.
	 * 
	 * @param agentContact the agent contact to update or create.
	 * @return an agent contact.
	 */
	@POST
	@Path("save")
	AgentContactDTO save(@Context SecurityContext context, AgentContactDTO agentContact);

	/**
	 * Find the agent contact having the provided id.
	 * 
	 * @param agentContact the agent contact
	 * @return Null if the agent contact is not found.
	 */
	@GET
	@Path("find")
	AgentContactDTO find(@Context SecurityContext context, @QueryParam("id") Integer agentContactId);
}
