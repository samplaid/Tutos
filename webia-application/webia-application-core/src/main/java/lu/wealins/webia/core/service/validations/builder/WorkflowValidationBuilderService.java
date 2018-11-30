package lu.wealins.webia.core.service.validations.builder;

import java.util.List;

import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.validations.ValidationStepService;

public interface WorkflowValidationBuilderService {

	public List<ValidationStepService> getValidationStepServicesForBeforeComplete(StepDTO step);

	public List<ValidationStepService> getValidationStepServicesForBeforeSave(StepDTO step);
}
