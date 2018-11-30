package lu.wealins.webia.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.AgentContactDTO;
import lu.wealins.webia.core.service.LiabilityAgentContactService;
import lu.wealins.webia.ws.rest.LiabilityAgentContactRESTService;

@Component
public class LiabilityAgentContactRESTServiceImpl implements LiabilityAgentContactRESTService {

	@Autowired
	private LiabilityAgentContactService agentContactService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityAgentContactRESTService#save(javax.ws.rs.core.SecurityContext, lu.wealins.webia.ws.rest.dto.AgentContactDTO)
	 */
	@Override
	public AgentContactDTO save(SecurityContext context, AgentContactDTO agentContact) {
		return agentContactService.save(agentContact);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityAgentContactRESTService#find(javax.ws.rs.core.SecurityContext, java.lang.Integer)
	 */
	@Override
	public AgentContactDTO find(SecurityContext context, Integer agentContactId) {
		return agentContactService.find(agentContactId);
	}

}
