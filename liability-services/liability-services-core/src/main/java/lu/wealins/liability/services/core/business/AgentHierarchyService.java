package lu.wealins.liability.services.core.business;

import java.util.Collection;

import lu.wealins.common.dto.liability.services.AgentHierarchyDTO;
import lu.wealins.common.dto.liability.services.AgentHierarchyPageableRequest;
import lu.wealins.common.dto.liability.services.AgentHierarchyRequest;
import lu.wealins.common.dto.liability.services.SearchResult;

public interface AgentHierarchyService {
	
	/**
	 * Search the agent hierarchies and return a pageable result
	 * @param criteria the request for the search
	 * @param pageSize the page size
	 * @param pageNumber the page number
	 * @return the result.
	 */
	SearchResult<AgentHierarchyDTO> search(AgentHierarchyPageableRequest criteria);
	
	/**
	 * Find agent hierarchies using the criteria in parameter. 
	 * @param criteria a criteria.
	 * @return Empty if no result
	 */
	Collection<AgentHierarchyDTO> findAgentHierarchies(AgentHierarchyRequest criteria);
	
}
