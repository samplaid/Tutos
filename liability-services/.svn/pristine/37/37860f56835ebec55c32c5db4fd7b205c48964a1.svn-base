package lu.wealins.liability.services.ws.rest;

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
import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;

@RolesAllowed("rest-user")
@Path("/agentContact")
@Produces(MediaType.APPLICATION_JSON)
public interface AgentContactRESTService {

	/**
	 * Save or update an agent contact. It is composed by an agent owner and a contact type of agent. In order that the process executes successfully, the id of these two agents and the contact
	 * function must be provided and The record that is returned by agent owner id, contact id and the contact function must be unique.
	 * 
	 * @param agentContact the agent contact to update or create.
	 * @return an agent contact.
	 */
	@Path("save")
	@POST()
	AgentContactDTO save(@Context SecurityContext context, AgentContactDTO agentContact);

	/**
	 * Find the agent contact having the provided id.
	 * 
	 * @param agentContact the agent contact
	 * @return Null if the agent contact is not found.
	 */
	@Path("find")
	@GET
	AgentContactDTO find(@Context SecurityContext context, @QueryParam("id") Integer agentContactId);

	/**
	 * Find the {@link AgentContactDTO} corresponding to the agent and contact provided in parameter.
	 * 
	 * @param agentId the agent id
	 * @param contactId the contact id
	 * @return the {@link AgentContactDTO} corresponding to the agent and contact provided in parameter.
	 */
	@Path("findByAgentIdAndContactId")
	@GET
	AgentContactLiteDTO findByAgentIdAndContactId(@QueryParam("agentId") String agentId, @QueryParam("contactId") String contactId);
}
