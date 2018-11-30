package lu.wealins.webia.core.service;

import lu.wealins.common.dto.webia.services.FormDataDTO;

public interface LiabilityFormDataService {

	/**
	 * Update the form data.
	 * 
	 * @param formData The form data.
	 * @param workflowItemTypeId workflow item type id.
	 * @param stepWorkflow The current step workflow.
	 * @param usrId The user id.
	 * @return The updated form data.
	 */
	FormDataDTO updateFormData(FormDataDTO formData, Integer workflowItemTypeId, String stepWorkflow, String usrId);

	/**
	 * Enrich the form data with liability data.
	 * 
	 * @param formData The form data.
	 * @param workflowItemTypeId workflow item type id.
	 * @param stepWorkflow The current step workflow.
	 * @param usrId The user id.
	 * @return The enriched form data.
	 */
	FormDataDTO enrichFormData(FormDataDTO formData, Integer workflowItemTypeId, String stepWorkflow, String usrId);

	/**
	 * Complete the form data with liability data
	 * 
	 * @param formData The form data.
	 * @param workflowItemTypeId workflow item type id.
	 * @param stepWorkflow The current step workflow.
	 * @param usrId The user id.
	 * @return The updated form data.
	 */
	FormDataDTO completeFormData(FormDataDTO formData, Integer workflowItemTypeId, String stepWorkflow, String usrId);

	/**
	 * Pre-complete the form data with liability data
	 * 
	 * @param formData The form data.
	 * @param workflowItemTypeId workflow item type id.
	 * @param stepWorkflow The current step workflow.
	 * @param usrId The user id.
	 * @return The updated form data.
	 */
	FormDataDTO preCompleteFormData(FormDataDTO formData, Integer workflowItemTypeId, String stepWorkflow, String userId);

	/**
	 * Go to previous the form data with liability data
	 * 
	 * @param formData The form data.
	 * @param workflowItemTypeId workflow item type id.
	 * @param stepWorkflow The current step workflow.
	 * @param usrId The user id.
	 * @return The updated form data.
	 */
	FormDataDTO previousFormData(FormDataDTO formData, Integer workflowItemTypeId, String stepWorkflow, String usrId);

	/**
	 * Abort the form data with liability data
	 * 
	 * @param formData The form data.
	 */
	void abortFormData(Integer workflowItemTypeId, FormDataDTO formData);
}
