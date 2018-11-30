package lu.wealins.webia.core.service;

import java.util.List;

import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemActionsDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemAllActionsDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;

public interface LiabilityWorkflowService {

	/**
	 * Get all actions for a workflow group id.
	 * 
	 * @param workflowGroupId The workflow group id.
	 * @param usrId The user id.
	 * @return The actions.
	 */
	WorkflowItemAllActionsDTO getWorkflowItemAllActions(Integer workflowGroupId, String usrId);

	/**
	 * Get workflow actions for a workitem id.
	 * 
	 * @param workitemId The workitem id.
	 * @param usrId The user id.
	 * @return The workflow actions.
	 */
	WorkflowItemActionsDTO getWorkflowItemActions(String workitemId, String usrId);

	/**
	 * Get the workflow general information.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param usrId The user id.
	 * @return The workflow general information.
	 */
	WorkflowGeneralInformationDTO getWorkflowGeneralInformation(String workflowItemId, String usrId);

	/**
	 * Get workflow item according the workflow item id.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param usrId The user id.
	 * @return The workflow item.
	 */
	WorkflowItemDataDTO getWorkflowItem(String workflowItemId, String usrId);

	/**
	 * Get workflow item according the workflow item id.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param dueDate The due Date to set in format MM.dd.yyyy
	 * @param usrId The user id.
	 * @return The workflow item.
	 */
	String updateDueDateWorkflowItem(String workflowItemId, String dueDate ,String usrId);
	
	/**
	 * Abort workflow.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param usrId The user id.
	 * @return The http response.
	 */
	String abort(Integer workflowItemId, String usrId);

	/**
	 * Recreate a policy.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param usrId The user id.
	 * @return The http response.
	 */
	String recreateWorkflow(Integer workflowItemId, String usrId);

	/**
	 * Launch 'Previous' action on the workflow.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param stepWorkflow The step workflow.
	 * @param usrId The user id.
	 * @return The http response.
	 */
	String previous(Integer workflowItemId, String stepWorkflow, String usrId);

	/**
	 * Launch 'Next' action on the workflow.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param stepWorkflow The step workflow.
	 * @param usrId The user id.
	 * @return The http response.
	 */
	String next(Integer workflowItemId, String stepWorkflow, String usrId);

	/**
	 * Save the metadata.
	 * 
	 * @param workflowItemDataDTO The workflow item data with metadata.
	 */
	void saveMetada(WorkflowItemDataDTO workflowItemDataDTO);

	void saveMetada(Long workflowItemId, String userId, List<MetadataDTO> metadata);

	void saveMetada(Long workflowItemId, String userId, MetadataDTO... metadata);

}
