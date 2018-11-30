package lu.wealins.webia.core.service.impl.form.data;

import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.common.validations.WorkflowItemSpecific;


public interface WorkflowFormService<T extends FormDataDTO> extends WorkflowItemSpecific {

	T updateFormData(T formData, String stepWorkflow, String userId);

	T enrichFormData(T formData, String stepWorkflow, String userId);

	T completeFormData(T formData, String stepWorkflow, String usrId);

	T preCompleteFormData(T formData, String stepWorkflow, String usrId);

	T previousFormData(T formData, String stepWorkflow, String usrId);

	/**
	 * Abort form data
	 * 
	 * @param formData The formdata.
	 */
	void abortFormData(T formData);
}
