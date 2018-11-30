package lu.wealins.liability.services.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.liability.services.core.business.AgentHierarchyService;
import lu.wealins.liability.services.ws.rest.AgentHierarchyRESTService;
import lu.wealins.common.dto.liability.services.AgentHierarchyDTO;
import lu.wealins.common.dto.liability.services.AgentHierarchyPageableRequest;
import lu.wealins.common.dto.liability.services.AgentHierarchyRequest;
import lu.wealins.common.dto.liability.services.SearchResult;

/**
 * @author XQV89
 *
 */
@Component
public class AgentHierarchyRESTServiceImpl implements AgentHierarchyRESTService {

	@Autowired
	AgentHierarchyService agentHierarchyService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * lu.wealins.liability.services.ws.rest.AgentHierarchyRESTService#search(
	 * javax.ws.rs.core.SecurityContext,
	 * lu.wealins.common.dto.liability.services.AgentHierarchyRequest)
	 */
	@Override
	public SearchResult<AgentHierarchyDTO> search(SecurityContext context, AgentHierarchyPageableRequest request) {
		return agentHierarchyService.search(request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.AgentHierarchyRESTService#
	 * findAgentHierarchies(lu.wealins.common.dto.liability.services.
	 * AgentHierarchyRequest)
	 */
	@Override
	public Collection<AgentHierarchyDTO> findAgentHierarchies(SecurityContext context, AgentHierarchyRequest criteria) {
		return agentHierarchyService.findAgentHierarchies(criteria);
	}

}
