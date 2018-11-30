package lu.wealins.liability.services.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.liability.services.core.business.AgentCategoryService;
import lu.wealins.liability.services.ws.rest.AgentCategoryRESTService;
import lu.wealins.common.dto.liability.services.AgentCategoryDTO;

@Component
public class AgentCategoryRESTServiceImpl implements AgentCategoryRESTService {
	@Autowired
	private AgentCategoryService agentCategoryService;
	
	@Override
	public List<AgentCategoryDTO> retrieveAll(SecurityContext context) {
		return agentCategoryService.retrieveAll();
	}

}
