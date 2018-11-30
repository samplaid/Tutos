package lu.wealins.webia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.webia.core.service.LiabilityFormDataService;
import lu.wealins.webia.core.service.builder.WorkflowBuilderService;
import lu.wealins.webia.core.service.impl.form.data.WorkflowFormService;

@Service
public class LiabilityFormDataServiceImpl implements LiabilityFormDataService {

	private static final String FORM_DATA_CANNOT_BE_NULL = "Form data cannot be null.";
	private static final String WORKFLOW_ITEM_ID_CANNOT_BE_NULL = "workflow item id cannot be null.";

	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier(value = "WorkflowFormBuilderService")
	private WorkflowBuilderService<WorkflowFormService> workflowFormBuilderService;

	@Override
	@SuppressWarnings("unchecked")
	public FormDataDTO updateFormData(FormDataDTO formData, Integer workflowItemTypeId, String stepWorkflow, String usrId) {
		Assert.notNull(formData, FORM_DATA_CANNOT_BE_NULL);

		return workflowFormBuilderService.getService(workflowItemTypeId).updateFormData(formData, stepWorkflow, usrId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public FormDataDTO completeFormData(FormDataDTO formData, Integer workflowItemTypeId, String stepWorkflow, String usrId) {
		Assert.notNull(formData, FORM_DATA_CANNOT_BE_NULL);

		return workflowFormBuilderService.getService(workflowItemTypeId).completeFormData(formData, stepWorkflow, usrId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public FormDataDTO preCompleteFormData(FormDataDTO formData, Integer workflowItemTypeId, String stepWorkflow, String userId) {
		Assert.notNull(formData, FORM_DATA_CANNOT_BE_NULL);

		return workflowFormBuilderService.getService(workflowItemTypeId).preCompleteFormData(formData, stepWorkflow, userId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public FormDataDTO previousFormData(FormDataDTO formData, Integer workflowItemTypeId, String stepWorkflow, String usrId) {
		Assert.notNull(formData, FORM_DATA_CANNOT_BE_NULL);

		return workflowFormBuilderService.getService(workflowItemTypeId).previousFormData(formData, stepWorkflow, usrId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void abortFormData(Integer workflowItemTypeId, FormDataDTO formData) {
		Assert.notNull(workflowItemTypeId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);
		workflowFormBuilderService.getService(workflowItemTypeId).abortFormData(formData);
	}

	@Override
	@SuppressWarnings("unchecked")
	public FormDataDTO enrichFormData(FormDataDTO formData, Integer workflowItemTypeId, String stepWorkflow, String usrId) {
		Assert.notNull(formData, FORM_DATA_CANNOT_BE_NULL);

		return workflowFormBuilderService.getService(workflowItemTypeId).enrichFormData(formData, stepWorkflow, usrId);
	}

}
