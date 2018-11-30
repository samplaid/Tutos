package lu.wealins.webia.services.core.service.impl.form.data;

import lu.wealins.common.dto.webia.services.FormDataDTO;

public interface WorkflowFormService<T extends FormDataDTO> extends WorkflowItemSpecific {

	T getFormData(Integer workflowItemId);

	T initFormData(Integer workflowItemId);

	T updateFormData(T formData, String stepWorkflow);
	
	T abort(T formData);

	default T complete(T formData, String stepWorkflow, Integer workflowItemId) {
		return formData;
	}
}
