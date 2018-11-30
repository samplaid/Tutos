package lu.wealins.liability.services.ws.rest;

import java.util.Collection;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.liability.services.AgentSearchByCreationRequest;
import lu.wealins.common.dto.liability.services.AgentSearchRequest;
import lu.wealins.common.dto.liability.services.PaymentMethodsDTO;
import lu.wealins.common.dto.liability.services.SearchResult;

@RolesAllowed("rest-user")
@Path("/agent")
@Produces(MediaType.APPLICATION_JSON)
public interface AgentRESTService {

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
	SearchResult<AgentLightDTO> search(
			@Context SecurityContext context, AgentSearchRequest agentSearchRequest);

	/**
	 * Search financial advisor agents and put at the top of the result all agents with categories <code><strong>PSI (Prestation Service Investment)</strong></code> and
	 * <code><strong>IFI (Independent Financial Intermediary)</strong></code>
	 * 
	 * @param context contains the security context
	 * @param request the query parameter.
	 * @return The agents. Not null.
	 */
	@POST
	@Path("/searchFinAdvisor")
	@Consumes(MediaType.APPLICATION_JSON)
	SearchResult<AgentLightDTO> searchFinancialAdvisor(@Context SecurityContext context, AgentSearchRequest request);

	/**
	 * Retrieves a set of agent by criteria. This method is no paginable
	 * 
	 * @param context The security context.
	 * @param agentSearchRequest The agent search request.
	 * 
	 * @return The agents.
	 * 
	 */
	@POST
	@Path("/findByCriteria")
	@Consumes(MediaType.APPLICATION_JSON)
	Collection<AgentLightDTO> findByCriteria(
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
	AgentDTO getAgent(
			@Context SecurityContext context,
			@PathParam("id") String id);

	/**
	 * Retrieves the agent lite identified by the id in parameter
	 * 
	 * @param agtId the agent id
	 * @return null if theagent is not found.
	 */
	@GET
	@Path("/lite/one/{id}")
	AgentLightDTO getAgentLite(@Context SecurityContext context, @PathParam("id") String agtId);

	/**
	 * Get an agent according its id.
	 * 
	 * @param context The security context.
	 * @param id The agent id.
	 * @return The agent
	 */
	@GET
	@Path("/")
	Collection<AgentDTO> getAgents(@Context SecurityContext context, @QueryParam("ids") List<String> ids);

	/**
	 * Create a new agent with the WSSUPDAGT soap service. The principal in the security context will be used as the update user of the agent.
	 * 
	 * @param context The security context.
	 * @param agent The agent.
	 * @return The created agent.
	 */
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	AgentDTO create(@Context SecurityContext context, AgentDTO agent);

	/**
	 * Update an existing agent with the WSSUPDAGT soap service. The principal in the security context will be used as the update user of the agent.
	 * 
	 * Agent to be updated must exist in the database, otherwise the method will throw an exception. All the values of the agent will be replaced with the data in the DTO parameter.
	 * 
	 * @param context The security context.
	 * @param agent The agent.
	 * @return The updated agent.
	 */
	@POST
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON)
	AgentDTO update(@Context SecurityContext context, AgentDTO agent);

	/**
	 * Create a hierarchy link to the agent
	 * 
	 * @param context The security context.
	 * @param agent The agent.
	 * @return The linked agent.
	 */
	@POST
	@Path("addAgh")
	@Consumes(MediaType.APPLICATION_JSON)
	AgentDTO addAgentHierarchy(@Context SecurityContext context, AgentDTO agent);

	/**
	 * Create a contact link to the agent
	 * 
	 * @param context The security context.
	 * @param agent The agent.
	 * @return The linked agent.
	 */
	@POST
	@Path("addAco")
	@Consumes(MediaType.APPLICATION_JSON)
	AgentDTO addAgentContact(@Context SecurityContext context, AgentDTO agent);

	/**
	 * Create a contact link to the agent
	 * 
	 * @param context The security context.
	 * @param agent The agent.
	 * @return The linked agent.
	 */
	@POST
	@Path("addAgb")
	@Consumes(MediaType.APPLICATION_JSON)
	AgentDTO addAgentBankAccount(@Context SecurityContext context, AgentDTO agent);

	/**
	 * Get the list of sub-broker of a given broker using the inverse hierarchies links
	 * 
	 * @param context The security context.
	 * @param agtId The broker Id.
	 * @return a collection of the agents light.
	 */
	@GET
	@Path("subBroker/{agtId}")
	@Consumes(MediaType.APPLICATION_JSON)
	Collection<AgentLightDTO> getSubBroker(@Context SecurityContext context, @PathParam("agtId") String agtId);

	/**
	 * Retrieves all agents whose commissions have already payed.
	 * 
	 * @param agentIds list of agent id.
	 * @return set of agent.
	 */
	@POST
	@Path("payableCommissionOwner")
	@Consumes(MediaType.APPLICATION_JSON)
	List<AgentLightDTO> retrievePayableCommissionAgentOwner(@Context SecurityContext context, List<String> agentIds);

	/**
	 * Get the list of account root pattern of a given deposit bank.
	 * 
	 * @param context The security context.
	 * @param depositBank The deposit bank.
	 * @return a collection of the account root patterns.
	 */
	@GET
	@Path("/accountRootPatternExample")
	@Consumes(MediaType.APPLICATION_JSON)
	Collection<String> getAccountRootPatternExample(@Context SecurityContext context, @QueryParam("depositBank") String depositBank);

	/**
	 * Get last agents created
	 * 
	 * @param context The security context.
	 * @param id The agent id.
	 * @return The agent
	 */
	@POST
	@Path("createdSince")
	@Consumes(MediaType.APPLICATION_JSON)
	SearchResult<AgentDTO> getAgentsCreatedSince(@Context SecurityContext context, AgentSearchByCreationRequest agentSearchByCreationRequest);
	/**
	 * Get the list of agents eligible for the communication settings (i.e. the broker, sub broker, and the AM and deposit bank of the funds of the policy
	 * 
	 * @param context the security context
	 * @param brokerId the broker id
	 * @param subBrokerId the sub broker id
	 * @param policyId the policy
	 * @return
	 */
	@GET
	@Path("/communicationAgents")
	Collection<AgentLightDTO> getCommunicationAgents(@Context SecurityContext context, @QueryParam("brokerId") String brokerId, @QueryParam("subBrokerId") String subBrokerId,
			@QueryParam("policyId") String policyId);

	@GET
	@Path("/paymentMethods")
	Collection<PaymentMethodsDTO> getPaymentMethodsByFundIds(@QueryParam("fundIds") List<String> fundIds);
}
