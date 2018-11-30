package lu.wealins.webia.core.service;

import java.util.Collection;
import java.util.List;

import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.liability.services.AgentSearchRequest;
import lu.wealins.common.dto.liability.services.PaymentMethodsDTO;
import lu.wealins.common.dto.liability.services.SearchResult;

public interface LiabilityAgentService {

	/**
	 * Get an agent according its id.
	 * 
	 * @param id The agent id.
	 * @return The agent
	 */
	AgentDTO getAgent(String id);

	AgentLightDTO getAgentLite(String id);

	Collection<AgentDTO> getAgents(Collection<String> agentIds);

	/**
	 * Agent search service. An agent could be
	 * 
	 * BK: Broker AM: Asset manager DB: Deposit bank FS: Sales person
	 * 
	 * The page is one base indexed, the first page's number is one.
	 * 
	 * @param agentSearchRequest The agent search request.
	 * 
	 * @return The agents.
	 * 
	 */
	SearchResult<AgentDTO> search(AgentSearchRequest request);
	
	/**
	 * Create the agent
	 * @param agentDTO
	 * @return the new agent. Null if it is not the case.
	 */
	AgentDTO create(AgentDTO agentDTO);
	
	/**
	 * Update the agent
	 * @param agentDTO
	 * @return the updated agent. Null if it is not the case.
	 */
	AgentDTO update(AgentDTO agentDTO);

	/**
	 * Retrieves all agents whose commissions are payable.
	 * 
	 * @param agentIds list of agent id.
	 * @return set of agent.
	 */
	List<AgentLightDTO> retrievePayableCommissionAgentOwner(List<String> agentIds);

	Collection<PaymentMethodsDTO> getPaymentMethodsByFundIds(Collection<String> fundIds);
}
