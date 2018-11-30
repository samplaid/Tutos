package lu.wealins.webia.services.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.RejectDataDTO;

public interface RejectDataService {

	/**
	 * Get the reject data according the worflow item id
	 * 
	 * @param workflowItemId The worflow item id.
	 * @return a collection of reject data.
	 */
	Collection<RejectDataDTO> getRejectData(Integer workflowItemId);

	/**
	 * Get the check data according the worflow item id and the step id.
	 * 
	 * @param workflowItemId The worflow item id.
	 * @param stepId The step id.
	 * @return a collection of reject data.
	 */
	Collection<RejectDataDTO> getRejectData(Integer workflowItemId, Integer stepId);

	/**
	 * save the reject data according its DTO representation.
	 * 
	 * @param RejectData The reject data DTO
	 * @return The reject data.
	 */
	RejectDataDTO save(RejectDataDTO RejectData);

}
