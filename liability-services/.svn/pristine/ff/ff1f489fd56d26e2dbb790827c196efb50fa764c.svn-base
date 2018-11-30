package lu.wealins.liability.services.core.business;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.liability.services.PaymentMethodsDTO;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.liability.services.core.persistence.entity.AgentEntity;
public interface AgentService {

	/**
	 * Get an agent according its id.
	 * 
	 * @param agtId The agent id.
	 * @return The agent.
	 */
	AgentDTO getAgent(String agtId);

	/**
	 * Retrieves the agent lite identified by the id in parameter
	 * 
	 * @param agtId the agent id
	 * @return null if theagent is not found.
	 */
	AgentLightDTO getAgentLite(String agtId);

	Collection<AgentDTO> getAgents(Collection<String> agtIds);

	/**
	 * Create a new agent with the WSSUPDAGT soap service and the user name.
	 * 
	 * @param agent The agent.
	 * @param userName The user name.
	 * @return The created agent.
	 */
	AgentDTO create(AgentDTO agent);

	/**
	 * Update an existing agent with the WSSUPDAGT soap service and the user name.
	 * 
	 * Agent to be updated must exist in the database, otherwise the method will throw an exception. All the values of the client will be replaced with the data in the DTO parameter.
	 * 
	 * @param agent The agent.
	 * @param userName The user name.
	 * @return The updated agent.
	 */
	AgentDTO update(AgentDTO agent);

	/**
	 * Create a hierarchy link to the provided agent.
	 * 
	 * @param agent the agent that will be linked to an hierarchy
	 * @param userName the user id that trigger this service.
	 * @return The agent which has been linked to an hierarchy.
	 */
	AgentDTO addAgentHierarchy(AgentDTO agent);

	/**
	 * Create a contact link to the provided agent.
	 * 
	 * @param agent the agent that will be linked to an hierarchy
	 * @param userName the user id that trigger this service.
	 * @return The agent which has been linked to an hierarchy.
	 */
	AgentDTO addAgentContact(AgentDTO agent);

	/**
	 * Create a bank account link to the provided agent.
	 * 
	 * @param agent the agent that will be linked to an hierarchy
	 * @param userName the user id that trigger this service.
	 * @return The agent which has been linked to an hierarchy.
	 */
	AgentDTO addBankAccount(AgentDTO agent);

	/**
	 * Return a list of {@link AgentEntity} having the name or the id that matches the search filter. All the returned objects in the list have the same category, such as : Broker, Asset manager, etc
	 * ...
	 * 
	 * The page parameter is zero indexed, the first page will have the number 0.
	 * 
	 * @param search
	 * @param type
	 * @param page
	 * @param size
	 * @return
	 */
	SearchResult<AgentLightDTO> getAgent(String search, List<String> categories, Integer status, int page, int size);

	/**
	 * Search the financial advisors by the criteria passed in parameter and set them as paginable. The financial advisors with categories
	 * <code><strong>PSI (Prestation Service Investment)</strong></code> and <code><strong>IFI (Independent Financial Intermediary)</strong></code> will be put in top of the list.
	 * 
	 * @param search the search criteria
	 * @param categories a list of agent category
	 * @param status the status of the agent
	 * @param page the current page number.
	 * @param size the number of the element in a page.
	 * @return a paginable list of agents
	 */
	SearchResult<AgentLightDTO> searchFinancialAdvisor(String search, List<String> categories, Integer status, int page, int size);

	/**
	 * This method returns a collection of agent depending to the criteria. To retrieve a paginable agents, use {@link AgentService#getAgent(String, String, Integer, int, int) agent} method which has
	 * the following signature: <br>
	 * <br>
	 * <code>SearchResult<AgentLightDTO> getAgent(String search, String category, Integer status, int page, int size)</code>.
	 * 
	 * @param agentSearchRequest
	 * @return empty if no result found. Otherwise, it returns a collection of agent.
	 * @see AgentService#getAgent(String) agent service
	 */
	Collection<AgentLightDTO> getAgent(String search, List<String> categories, Integer status);

	/**
	 * Get Sales manager linked to a fund.
	 * 
	 * @param fdsId The fund id.
	 * @return The sales manager.
	 */
	AgentDTO getSalesManager(String fdsId);

	/**
	 * Get the list of sub-broker of a given broker using the inverse hierarchies links
	 * 
	 * @param masterBrokerId a master broker id.
	 * @return a collection of the agents light.
	 */
	Collection<AgentLightDTO> getSubBroker(String masterBrokerId);

	/**
	 * Retrieve an agent contact identified by the id in parameter.
	 * 
	 * @param agcId the agent contact id
	 * @return Null if the agent contact is not found.
	 */
	AgentContactLiteDTO getAgentContact(Integer agcId);

	void delete(String agentId);

	/**
	 * Retrieves all agents whose commissions have already payed.
	 * 
	 * @param agentIds list of agent id.
	 * @return set of agent.
	 */
	List<AgentLightDTO> retrievePayableCommissionAgentOwner(List<String> agentIds);

	/**
	 * Get the list of account root pattern examples of a given deposit bank.
	 * 
	 * @param depositBank The deposit bank.
	 * @return a collection of the account root patterns examples.
	 */
	Collection<String> getAccountRootPatternExample(String depositBank);

	/**
	 * Get the list of account root pattern of a given deposit bank.
	 * 
	 * @param depositBank The deposit bank.
	 * @return a collection of the account root patterns.
	 */
	Collection<String> getAccountRootPattern(String depositBank);
	/**
	 * Get the agents created by since a given day
	 * 
	 * @param date
	 * @param page
	 * @param size
	 * @return
	 */
	SearchResult<AgentDTO> getAgentsCreatedSince(Date date, int page, int size);

	/**
	 * Get the agents created by a specific user since a given day
	 * 
	 * @param date
	 * @param createdBy
	 * @param page
	 * @param size
	 * @return
	 */
	SearchResult<AgentDTO> getAgentsCreatedSince(Date date, String createdBy, int page, int size);

	/**
	 * get Agent with wealins partner ID equivalent in DRM
	 * 
	 * @param search
	 * @param categories
	 * @param status
	 * @param page
	 * @param size
	 * @return
	 */
	Collection<AgentLightDTO> getAgentWithPartnerID(String search);

	/**
	 * Get the list of agents eligible for the communication settings (i.e. the broker, sub broker, and the AM and deposit bank of the funds of the policy.
	 * 
	 * @param brokerId the broker id
	 * @param subBrokerId the sub broker id
	 * @param policyId the policy id
	 * @return
	 */
	Collection<AgentLightDTO> getCommunicationAgents(String brokerId, String subBrokerId, String policyId);

	Collection<PaymentMethodsDTO> getPaymentMethodsByFundIds(Collection<String> fundId);
}
