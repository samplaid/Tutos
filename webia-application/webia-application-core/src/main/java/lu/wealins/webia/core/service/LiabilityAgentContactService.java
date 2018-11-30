package lu.wealins.webia.core.service;

import lu.wealins.common.dto.liability.services.AgentContactDTO;
import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;

public interface LiabilityAgentContactService {

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
	 * get the CPS of the deposit bank for a given fund Id
	 * @param fdsId
	 * @return
	 */
	AgentContactLiteDTO getFundDepositBankContactAgent(String fdsId);

	/**
	 * Get the {@link AgentContactLiteDTO} corresponding to the payment contact of the Fund id provided in parameter.
	 * 
	 * @param fdsId the fund id
	 * @return the {@link AgentContactLiteDTO} corresponding to the payment contact of the Fund id provided in parameter.
	 */
	AgentContactLiteDTO getFundPaymentContactAgent(String fdsId);
}
