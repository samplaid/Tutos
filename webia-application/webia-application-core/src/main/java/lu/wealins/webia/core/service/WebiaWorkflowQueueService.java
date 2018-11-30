package lu.wealins.webia.core.service;

import lu.wealins.common.dto.webia.services.WorkflowQueueDTO;

public interface WebiaWorkflowQueueService {

	String getWorkflowQueueId(String workflowItemId, String usrId);

	/**
	 * Get workflow queue according its id.
	 * 
	 * @param workflowQueueId The workflow queue id.
	 * @return The workflow queue.
	 */
	WorkflowQueueDTO getWorkflowQueue(String workflowQueueId);

	/**
	 * Get the user name or the group name assigned to a workflow queue.
	 * 
	 * @param workflowQueueId The workflow queue id.
	 * @return The user name or the group name.
	 */
	String getUserNameOrUserGroupName(String workflowQueueId);

	/**
	 * Check if the user is assigned to the workflow queue.
	 * 
	 * @param workflowQueueId The workflow queue id.
	 * @param userId The user id.
	 * @return True, if successful.
	 */
	Boolean isAssignTo(String workflowQueueId, String userId);

	/**
	 * Check if the user has a personal queue.
	 * 
	 * @param userId The user id.
	 * @return True, if successful.
	 */
	Boolean hasPersonalQueue(String userId);
}
