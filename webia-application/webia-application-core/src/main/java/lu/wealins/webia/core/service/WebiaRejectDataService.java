package lu.wealins.webia.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.RejectDataDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.WorkflowUserDTO;

public interface WebiaRejectDataService {

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
	 * save the reject data according its step DTO representation and the user name
	 * 
	 * @param step The step DTO
	 * @param workflowUser The workflow User (name and login)
	 * @return The reject data.
	 */
	RejectDataDTO createRejectData(StepDTO step, WorkflowUserDTO workflowUser);
}
