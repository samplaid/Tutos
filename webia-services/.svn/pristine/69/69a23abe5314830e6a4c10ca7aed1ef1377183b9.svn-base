package lu.wealins.webia.services.core.service;

import java.util.List;

import lu.wealins.common.dto.webia.services.WorkflowQueueDTO;

public interface WorkflowQueueService {

	/**
	 * Get workflow queue according its id.
	 * 
	 * @param id The workflow queue id.
	 * @return The workflow queue.
	 */
	WorkflowQueueDTO getWorkflowQueue(Integer id);

	/**
	 * Get the workflow user ids of
	 * 
	 * @return
	 */
	List<String> getWorkflowQueueUsrIds();

	/**
	 * Check if the userId is assigned to the workflow queue.
	 * 
	 * @param workflowQueueId The workflow queue id.
	 * @param userId The user id.
	 * @return True, if successful.
	 */
	boolean isAssignTo(Integer workflowQueueId, String userId);

	/**
	 * Check if the user has a personal queue.
	 * 
	 * @param usrId The user id.
	 * @return True, if successful.
	 */
	boolean hasPersonalQueue(String usrId);
}
