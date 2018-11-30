package lu.wealins.webia.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentSearchRequest;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.common.dto.webia.services.ApplicationParameterDTO;
import lu.wealins.webia.core.service.LiabilityAgentService;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.ws.rest.LiabilityAgentRESTService;

@Component
public class LiabilityAgentRESTServiceImpl implements LiabilityAgentRESTService {

	private static final String WEALINS_BROKER_ID = "WEALINS_BROKER_ID";
	private static final String WEALINS_ASSET_MANAGER_ID = "WEALINS_ASSET_MANAGER_ID";
	@Autowired
	private LiabilityAgentService agentService;
	@Autowired
	private WebiaApplicationParameterService applicationParameterService;
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityAgentRESTService#search(javax.ws.rs.core.SecurityContext, lu.wealins.webia.ws.rest.dto.AgentSearchRequest)
	 */
	@Override
	public SearchResult<AgentDTO> search(SecurityContext context, AgentSearchRequest agentSearchRequest) {
		return agentService.search(agentSearchRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityAgentRESTService#get(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public AgentDTO get(SecurityContext context, String id) {
		return agentService.getAgent(id);
	}

	@Override
	public AgentDTO getWealinsBroker(SecurityContext context) {
		return getApplicationParameterAgent(WEALINS_BROKER_ID, context);
	}

	@Override
	public AgentDTO getWealinsAssetManager(SecurityContext context) {
		return getApplicationParameterAgent(WEALINS_ASSET_MANAGER_ID, context);
	}

	private AgentDTO getApplicationParameterAgent(String applicationParameter, SecurityContext context) {
		ApplicationParameterDTO wealinsBrokerIdParameter = applicationParameterService.getApplicationParameter(applicationParameter);

		if (wealinsBrokerIdParameter == null) {
			throw new IllegalStateException("No application parameter " + applicationParameter + ".");
		}

		return get(context, wealinsBrokerIdParameter.getValue());
	}

	@Override
	public AgentDTO create(SecurityContext context, AgentDTO agentDTO) {
		return agentService.create(agentDTO);
	}

	@Override
	public AgentDTO update(SecurityContext context, AgentDTO agentDTO) {
		return agentService.update(agentDTO);
	}

}
