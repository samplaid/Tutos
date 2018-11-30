package lu.wealins.liability.services.ws.rest.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.liability.services.AgentSearchByCreationRequest;
import lu.wealins.common.dto.liability.services.AgentSearchRequest;
import lu.wealins.common.dto.liability.services.PaymentMethodsDTO;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.liability.services.core.business.AgentService;
import lu.wealins.liability.services.ws.rest.AgentRESTService;
import lu.wealins.liability.services.ws.rest.exception.WssUpdateAgentException;

@Component
public class AgentRESTServiceImpl implements AgentRESTService {

	@Autowired
	private AgentService agentService;

	private static final Logger log = LoggerFactory.getLogger(AgentRESTService.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.AgentRESTService#search(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.AgentSearchRequest)
	 */
	@Override
	public SearchResult<AgentLightDTO> search(SecurityContext context, AgentSearchRequest request) {
		int pageNum = request.getPageNum() == null || request.getPageNum().intValue() < 1 ? 1 : request.getPageNum().intValue();
		int pageSize = request.getPageSize() == null || request.getPageSize().intValue() <= 1 || request.getPageSize().intValue() > SearchResult.MAX_PAGE_SIZE ? SearchResult.DEFAULT_PAGE_SIZE
				: request.getPageSize().intValue();

		log.debug("searching for agent %{}% of category {}.", request.getFilter(), request.getCategories());

		return agentService.getAgent(request.getFilter(), request.getCategories(), request.getStatus(), pageNum - 1, pageSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.AgentRESTService#get(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public AgentDTO getAgent(SecurityContext context, String id) {
		return agentService.getAgent(id);
	}

	@Override
	public Collection<AgentDTO> getAgents(SecurityContext context, List<String> ids) {
		return agentService.getAgents(ids);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.AgentRESTService#create(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.AgentDTO)
	 */
	@Override
	public AgentDTO create(SecurityContext context, AgentDTO agent) {
		return agentService.create(agent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.AgentRESTService#update(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.AgentDTO)
	 */
	@Override
	public AgentDTO update(SecurityContext context, AgentDTO agent) {
		if (StringUtils.isEmpty(agent.getAgtId())) {
			throw new WssUpdateAgentException("Agent id must not be empty.");
		}
		return agentService.update(agent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.AgentRESTService#getSubBroker(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public Collection<AgentLightDTO> getSubBroker(@Context SecurityContext context, @PathParam("agtId") String agtId) {
		return agentService.getSubBroker(agtId);
	}

	@Override
	public Collection<AgentLightDTO> getCommunicationAgents(SecurityContext context, String brokerId, String subBrokerId, String policyId) {
		return agentService.getCommunicationAgents(brokerId, subBrokerId, policyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.AgentRESTService#addAgentHierarchy(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.AgentDTO)
	 */
	@Override
	public AgentDTO addAgentHierarchy(SecurityContext context, AgentDTO agent) {
		return agentService.addAgentHierarchy(agent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.AgentRESTService#addAgentContact(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.AgentDTO)
	 */
	@Override
	public AgentDTO addAgentContact(SecurityContext context, AgentDTO agent) {
		return agentService.addAgentContact(agent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.AgentRESTService#addBankAccount(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.AgentDTO)
	 */
	@Override
	public AgentDTO addAgentBankAccount(SecurityContext context, AgentDTO agent) {
		return agentService.addBankAccount(agent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.AgentRESTService#findByCriteria(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.AgentSearchRequest)
	 */
	@Override
	public Collection<AgentLightDTO> findByCriteria(SecurityContext context, AgentSearchRequest agentSearchRequest) {
		if (agentSearchRequest == null) {
			return Collections.emptyList();
		}
		return agentService.getAgent(agentSearchRequest.getFilter(), agentSearchRequest.getCategories(), agentSearchRequest.getStatus());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.AgentRESTService#searchFinancialAdvisor(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.AgentSearchRequest)
	 */
	@Override
	public SearchResult<AgentLightDTO> searchFinancialAdvisor(SecurityContext context, AgentSearchRequest request) {
		int pageNum = request.getPageNum() == null || request.getPageNum().intValue() < 1 ? 1 : request.getPageNum().intValue();
		int pageSize = request.getPageSize() == null || request.getPageSize().intValue() <= 1 || request.getPageSize().intValue() > SearchResult.MAX_PAGE_SIZE ? SearchResult.DEFAULT_PAGE_SIZE
				: request.getPageSize().intValue();
		return agentService.searchFinancialAdvisor(request.getFilter(), request.getCategories(), request.getStatus(), pageNum - 1, pageSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.AgentRESTService#getAgentLite(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public AgentLightDTO getAgentLite(SecurityContext context, String agtId) {
		return agentService.getAgentLite(agtId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<AgentLightDTO> retrievePayableCommissionAgentOwner(SecurityContext context, List<String> agentIds) {
		return agentService.retrievePayableCommissionAgentOwner(agentIds);
	}

	@Override
	public SearchResult<AgentDTO> getAgentsCreatedSince(SecurityContext context, AgentSearchByCreationRequest request) {
		int pageNum = request.getPageNum() == null || request.getPageNum().intValue() < 1 ? 1 : request.getPageNum().intValue();
		int pageSize = request.getPageSize() == null || request.getPageSize().intValue() <= 1 || request.getPageSize().intValue() > SearchResult.MAX_PAGE_SIZE ? SearchResult.DEFAULT_PAGE_SIZE
				: request.getPageSize().intValue();

		return agentService.getAgentsCreatedSince(request.getCreationDate(), request.getCreatedBy(), pageNum - 1, pageSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.AgentRESTService#getAccountRootPattern(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public Collection<String> getAccountRootPatternExample(SecurityContext context, String depositBank) {
		if (StringUtils.isEmpty(depositBank)) {
			return Collections.<String>emptyList();
		}
		return agentService.getAccountRootPatternExample(depositBank);
	}

	@Override
	public Collection<PaymentMethodsDTO> getPaymentMethodsByFundIds(List<String> fundIds) {
		return agentService.getPaymentMethodsByFundIds(fundIds);
	}

}
