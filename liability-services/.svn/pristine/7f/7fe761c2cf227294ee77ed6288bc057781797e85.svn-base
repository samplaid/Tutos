package lu.wealins.liability.services.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.AgentContactDTO;
import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;
import lu.wealins.liability.services.core.business.AgentContactService;
import lu.wealins.liability.services.ws.rest.AgentContactRESTService;

@Component
public class AgentContactRESTServiceImpl implements AgentContactRESTService {

	@Autowired
	private AgentContactService agentContactService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.AgentContactRESTService#save(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.AgentContactDTO)
	 */
	@Override
	public AgentContactDTO save(SecurityContext context, AgentContactDTO agentContact) {
		return agentContactService.save(agentContact);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.AgentContactRESTService#find(javax.ws.rs.core.SecurityContext, java.lang.Long)
	 */
	@Override
	public AgentContactDTO find(SecurityContext context, Integer agentContactId) {
		return agentContactService.find(agentContactId);
	}

	@Override
	public AgentContactLiteDTO findByAgentIdAndContactId(String agentId, String contactId) {
		return agentContactService.findByAgentIdAndContactID(agentId, contactId);
	}

}
