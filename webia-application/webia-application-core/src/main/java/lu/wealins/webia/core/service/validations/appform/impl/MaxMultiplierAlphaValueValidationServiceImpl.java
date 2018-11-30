package lu.wealins.webia.core.service.validations.appform.impl;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.ANALYSIS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.service.validations.CoverageValidationService;
import lu.wealins.webia.core.service.validations.appform.AppFormValidationStepService;

@Service(value = "AppFormMaxMultiplierAlphaValueValidationService")
public class MaxMultiplierAlphaValueValidationServiceImpl extends AppFormValidationStepService {

	private static Set<WorkflowType> SUPPORTED_WORFLOW_TYPES = Stream.of(WorkflowType.APP_FORM, WorkflowType.ADDITIONAL_PREMIUM).collect(Collectors.toSet());

	@Autowired
	private CoverageValidationService coverageValidationService;

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		AppFormDTO formData = getFormData(step);
		List<String> errors = new ArrayList<>();

		coverageValidationService.validateMaxMultiplierAlphaValue(formData, errors);

		return errors;
	}

	@Override
	public boolean needValidateBeforeComplete(StepDTO step) {
		StepTypeDTO currentStep = StepTypeDTO.getStepType(step.getStepWorkflow());
		AppFormDTO formData = getFormData(step);

		return currentStep.isAfterOrEquals(ANALYSIS) && BooleanUtils.isFalse(formData.getDeathCoverageStd());
	}

	@Override
	public boolean needValidateBeforeSave(StepDTO step) {
		return false;
	}

	@Override
	public Set<WorkflowType> getSupportedWorkflowTypes() {
		return SUPPORTED_WORFLOW_TYPES;
	}

}
