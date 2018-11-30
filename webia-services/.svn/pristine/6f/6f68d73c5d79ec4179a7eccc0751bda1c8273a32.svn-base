package lu.wealins.webia.services.core.service.validations.builder.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.common.validations.ValidationStepService;
import lu.wealins.webia.services.core.service.validations.builder.WorkflowValidationBuilderService;

@Service
public class WorkflowValidationBuilderServiceImpl implements WorkflowValidationBuilderService {

	private List<ValidationStepService> validationServicesByOperation;

	@Autowired
	public void setValidationServicesMap(List<ValidationStepService> validationServicesByOperation) {
		this.validationServicesByOperation = validationServicesByOperation;
	}

	@Override
	public List<ValidationStepService> getValidationStepServicesForBeforeComplete(StepDTO step) {
		Integer workflowItemTypeId = step.getWorkflowItemTypeId();
		WorkflowType workflowType = WorkflowType.getType(workflowItemTypeId);

		return validationServicesByOperation.stream().filter(x -> x.getSupportedWorkflowTypes().contains(workflowType) && x.needValidateBeforeComplete(step)).collect(Collectors.toList());
	}

	@Override
	public List<ValidationStepService> getValidationStepServicesForBeforeSave(StepDTO step) {
		Integer workflowItemTypeId = step.getWorkflowItemTypeId();
		WorkflowType workflowType = WorkflowType.getType(workflowItemTypeId);

		return validationServicesByOperation.stream().filter(x -> x.getSupportedWorkflowTypes().contains(workflowType) && x.needValidateBeforeSave(step)).collect(Collectors.toList());
	}
}
