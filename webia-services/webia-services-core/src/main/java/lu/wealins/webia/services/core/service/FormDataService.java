package lu.wealins.webia.services.core.service;

import lu.wealins.common.dto.webia.services.FormDataDTO;

public interface FormDataService {

	/**
	 * Get the form data according its workflow item id and its step id.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param stepWorkflow The step id.
	 * @param workflowItemTypeId
	 * @return The form data
	 */
	FormDataDTO getFormData(Integer workflowItemId, Integer workflowItemTypeId);

	FormDataDTO initFormData(Integer workflowItemId, Integer workflowItemTypeId);

	/**
	 * Update the form data.
	 * 
	 * @param formData The form data.
	 * @return The updated form data.
	 */
	FormDataDTO updateFormData(FormDataDTO formData, String stepWorkflow, Integer workflowItemTypeId);

	
	/**
	 * Perform the abort.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param stepWorkflow The step id.
	 * @param workflowItemTypeId
	 * @return The form data
	 */
	FormDataDTO abort(FormDataDTO formData, String stepWorkflow, Integer workflowItemTypeId);

	FormDataDTO completeFormData(FormDataDTO formData, String stepWorkflow, Integer workflowItemTypeId, Integer workflowItemId);

}
