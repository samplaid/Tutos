package lu.wealins.liability.services.core.business;

import java.util.Date;

import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemActionsDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemAllActionsDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;

public interface WorkflowItemService {

	/**
	 * Save metadata.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param usrId The user id.
	 * @param metadata The metadata
	 */
	void saveMetadata(Long workflowItemId, String usrId, MetadataDTO... metadata);

	/**
	 * Save metadata.
	 * 
	 * @param workflowItemData The workflow item data.
	 * @param metadata The metadata
	 */
	void saveMetadata(WorkflowItemDataDTO workflowItemData);

	/**
	 * Get general information linked to the workflow.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param usrId The user id.
	 * @return The general information linked to the workflow.
	 */
	WorkflowGeneralInformationDTO getGeneralInformation(Long workflowItemId, String usrId);

	/**
	 * Get all workflow actions.
	 * 
	 * @param workItemId The workflow item id.
	 * @param usrId The user id.
	 * @return The workflow actions.
	 */
	WorkflowItemAllActionsDTO getAllWorkItemActions(Integer workItemId, String usrId);

	/**
	 * Get workflow actions.
	 * 
	 * @param workItemId The workflow item id.
	 * @param usrId The user id.
	 * @return The workflow actions.
	 */
	WorkflowItemActionsDTO getWorkItemActions(Integer workItemId, String usrId);

	/**
	 * Check if the current step is the first step according to the workflow item id.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param usrId The user id.
	 * @return True, if successful.
	 */
	boolean isFirstStep(String workflowItemId, String usrId);

	/**
	 * Get workflow item response according workflow item id an the user id.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param usrId The user id.
	 * @return The workflow item response.
	 */
	WorkflowItemDataDTO getWorkflowItem(Long workflowItemId, String usrId);
	
	/**
	 * Update the Due Date of a workflow item according workflow item id an the user id.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param dueDate the due Date to set as string format MM.dd.yyyy
	 * @param usrId The user id.
	 * @return The general information linked to the workflow.
	 */
	void updateDueDateWorkflowItem(Long workflowItemId, String dueDate, String usrId);
}
