package lu.wealins.liability.services.core.business;

import lu.wealins.common.dto.liability.services.AgentContactDTO;
import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;

public interface AgentContactService {

	/**
	 * Save or update an agent contact. It is composed by an agent owner and a contact type of agent. In order that the process executes successfully, the id of these two agents and the contact
	 * function must be provided and The record that is returned by agent owner id, contact id and the contact function must be unique.
	 * 
	 * @param agentContact the agent contact to update or create.
	 * @return an agent contact.
	 */
	AgentContactDTO save(AgentContactDTO agentContact);

	/**
	 * Find the agent contact having the provided id.
	 * 
	 * @param agentContact the agent contact
	 * @return Null if the agent contact is not found.
	 */
	AgentContactDTO find(Integer agentContactId);

	/**
	 * Find the {@link AgentContactLiteDTO} corresponding to the agent and contact provided in parameter
	 * 
	 * @param agentId the id of the agent
	 * @param contactId the id of the agent
	 * 
	 * @return the {@link AgentContactLiteDTO} corresponding to the agent and contact provided in parameter
	 */
	AgentContactLiteDTO findByAgentIdAndContactID(String agentId, String contactId);
}
