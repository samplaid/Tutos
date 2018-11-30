package lu.wealins.webia.core.service.impl;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.liability.services.AgentSearchRequest;
import lu.wealins.common.dto.liability.services.PaymentMethodsDTO;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.webia.core.service.AssetManagerStrategyService;
import lu.wealins.webia.core.service.LiabilityAgentService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityAgentServiceImpl implements LiabilityAgentService {
	private static final String EMPTY_STRING = "";
	private static final String LIABILITY_AGENT = "liability/agent/";
	private static final String LIABILITY_LOAD_AGENT_LITE = LIABILITY_AGENT + "lite/one/";
	private static final String LIABILITY_SEARCH_AGENT = LIABILITY_AGENT + "search";
	private static final String LIABILITY_CREATE_AGENT = LIABILITY_AGENT + "create";
	private static final String LIABILITY_UPDATE_AGENT = LIABILITY_AGENT + "update";
	private static final String LIABILITY_PAYABLE_COMMISSION_AGENT_OWNER = "liability/agent/payableCommissionOwner";
	private static final String LIABILITY_PAYMENT_METHODS = "paymentMethods";

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private AssetManagerStrategyService strategyService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityAgentService#search(lu.wealins.webia.ws.rest.dto.AgentSearchRequest)
	 */
	@Override
	public SearchResult<AgentDTO> search(AgentSearchRequest request) {
		return restClientUtils.post(LIABILITY_SEARCH_AGENT, request, SearchResult.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityAgentService#get(java.lang.String)
	 */
	@Override
	public AgentDTO getAgent(String id) {

		return restClientUtils.get(LIABILITY_AGENT, id, AgentDTO.class);
	}

	@Override
	public AgentLightDTO getAgentLite(String id) {

		return restClientUtils.get(LIABILITY_LOAD_AGENT_LITE, id, AgentDTO.class);
	}

	@Override
	public Collection<AgentDTO> getAgents(Collection<String> agentIds) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();

		agentIds.forEach(x -> queryParams.add("ids", x));

		return restClientUtils.get(LIABILITY_AGENT, EMPTY_STRING, queryParams, new GenericType<Collection<AgentDTO>>() {
		});
	}

	@Override
	public AgentDTO create(AgentDTO agentDTO) {
		setStrategyId(agentDTO);
		return restClientUtils.post(LIABILITY_CREATE_AGENT, agentDTO, AgentDTO.class);
	}
	
	@Override
	public AgentDTO update(AgentDTO agentDTO) {
		if (agentDTO == null) {
			return null;
		}

		setStrategyId(agentDTO);
		return restClientUtils.post(LIABILITY_UPDATE_AGENT, agentDTO, AgentDTO.class);
	}

	private void setStrategyId(AgentDTO agentDTO) {
		agentDTO.getAssetManagerStrategies().stream().filter(assetManager -> assetManager != null)
				.forEach(assetManager -> strategyService.setId(assetManager));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<AgentLightDTO> retrievePayableCommissionAgentOwner(List<String> agentIds) {
		Assert.notNull(agentIds, "List of agent id must not be null while retrieving the agent owner of the payable commissions.");
		return restClientUtils.post(LIABILITY_PAYABLE_COMMISSION_AGENT_OWNER, agentIds, new GenericType<List<AgentLightDTO>>() {
		});
	}

	@Override
	public Collection<PaymentMethodsDTO> getPaymentMethodsByFundIds(Collection<String> fundIds) {
		Assert.notNull(fundIds, "Fund ids can't be empty");

		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();

		fundIds.forEach(x -> queryParams.add("fundIds", x));

		return restClientUtils.get(LIABILITY_AGENT, LIABILITY_PAYMENT_METHODS, queryParams, new GenericType<Collection<PaymentMethodsDTO>>() {
		});
	}

}
