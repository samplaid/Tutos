package lu.wealins.webia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.UpdateOperationStatusRequest;
import lu.wealins.common.dto.webia.services.enums.OperationStatus;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.service.ScoreUtilityService;
import lu.wealins.webia.core.service.WebiaStepServiceByOperation;

public abstract class OperationWebiaStepService<T extends FormDataDTO> implements WebiaStepServiceByOperation<T> {

	@Autowired
	private ScoreUtilityService scoreUtilityService;

	@Autowired
	private WebiaOperationStatusService operationStatusService;

	@Override
	public StepDTO update(StepDTO step) {
		saveScoreInLissia(step);

		return step;
	}

	@Override
	public StepDTO postComplete(StepDTO step) {

		updateStatus(step);

		return step;
	}

	public void updateStatus(StepDTO step) {
		OperationStatus operationStatus = getNextOperationStatus(step);

		if (operationStatus == null) {
			return;
		}

		FormDataDTO formData = getFormData(step);

		UpdateOperationStatusRequest request = new UpdateOperationStatusRequest();
		request.setWorkflowItemId(formData.getWorkflowItemId());
		request.setStatus(operationStatus);
		request.setWorkflowType(WorkflowType.getType(step.getWorkflowItemTypeId()));

		operationStatusService.updateStatus(request);

		formData.setStatusCd(operationStatus.name());
	}

	protected abstract OperationStatus getNextOperationStatus(StepDTO step);

	@Override
	public StepDTO preUpdate(StepDTO step) {
		WebiaStepServiceByOperation.super.preUpdate(step);

		return step;
	}

	private void saveScoreInLissia(StepDTO step) {
		boolean isAfterCheckDocumentation = StepTypeDTO.getStepType(step.getStepWorkflow()).isAfter(StepTypeDTO.CHECK_DOCUMENTATION);

		if (isAfterCheckDocumentation) {
			scoreUtilityService.saveScoreInLissia(step);
		}
	}

}
