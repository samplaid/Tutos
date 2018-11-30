package lu.wealins.webia.services.core.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.services.core.service.FormDataService;
import lu.wealins.webia.services.core.service.impl.form.data.WorkflowFormService;
@Service
public class FormDataServiceImpl implements FormDataService {

	private static final String FORM_DATA_CANNOT_BE_NULL = "Form data cannot be null.";
	private static final String STEP_WORKFLOW_CANNOT_BE_NULL = "Step workflow cannot be null.";
	private static final String WORKFLOW_ITEM_TYPE_ID_CANNOT_BE_NULL = "The workflow item type id cannot be null.";

	@SuppressWarnings("rawtypes")
	private Map<WorkflowType, WorkflowFormService> servicesByOperation;

	@SuppressWarnings("rawtypes")
	@Autowired
	public void setServicesMap(List<WorkflowFormService> services) {
		servicesByOperation = services.stream()
				.collect(Collectors.toMap(WorkflowFormService::getSupportedWorkflowType, Function.identity()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * lu.wealins.webia.services.core.service.FormDataService#getFormData(java.
	 * lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public FormDataDTO getFormData(Integer workflowItemId, Integer workflowItemTypeId) {
		WorkflowFormService service = getService(workflowItemTypeId);
		FormDataDTO formData = service.getFormData(workflowItemId);

		if (formData == null) {
			formData = initFormData(workflowItemId, workflowItemTypeId);
		}

		return formData;
	}

	@Override
	public FormDataDTO initFormData(Integer workflowItemId, Integer workflowItemTypeId) {
		@SuppressWarnings("rawtypes")
		WorkflowFormService service = getService(workflowItemTypeId);

		return service.initFormData(workflowItemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * lu.wealins.webia.services.core.service.FormDataService#updateFormData(lu.
	 * wealins.webia.services.ws.rest.dto.FormDataDTO, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public FormDataDTO updateFormData(FormDataDTO form, String stepWorkflow, Integer workflowItemTypeId) {
		Assert.notNull(form, FORM_DATA_CANNOT_BE_NULL);

		return getService(workflowItemTypeId).updateFormData(form, stepWorkflow);
	}
	
	/* (non-Javadoc)
	 * @see lu.wealins.webia.services.core.service.FormDataService#abort(lu.wealins.common.dto.webia.services.FormDataDTO, java.lang.String, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public FormDataDTO abort(FormDataDTO formData, String stepWorkflow, Integer workflowItemTypeId) {
		Assert.notNull(formData, FORM_DATA_CANNOT_BE_NULL);
		Assert.notNull(stepWorkflow, STEP_WORKFLOW_CANNOT_BE_NULL);
		
		return getService(workflowItemTypeId).abort(formData);
	}

	@SuppressWarnings("rawtypes")
	private WorkflowFormService getService(Integer workflowType) {
		WorkflowType enumType = WorkflowType.getType(workflowType);
		WorkflowFormService workflowFormService = servicesByOperation.get(enumType);
		if (workflowFormService == null) {
			throw new IllegalArgumentException(String.format("No form service defined for type : %s", enumType));
		}
		return workflowFormService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FormDataDTO completeFormData(FormDataDTO formData, String stepWorkflow, Integer workflowItemTypeId, Integer workflowItemId) {
		Assert.notNull(formData, FORM_DATA_CANNOT_BE_NULL);
		Assert.notNull(stepWorkflow, STEP_WORKFLOW_CANNOT_BE_NULL);
		Assert.notNull(workflowItemTypeId, WORKFLOW_ITEM_TYPE_ID_CANNOT_BE_NULL);

		return getService(workflowItemTypeId).complete(formData, stepWorkflow, workflowItemId);
	}

}
