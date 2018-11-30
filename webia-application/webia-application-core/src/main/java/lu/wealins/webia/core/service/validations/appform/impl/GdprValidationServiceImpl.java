package lu.wealins.webia.core.service.validations.appform.impl;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.ANALYSIS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.service.CheckStepService;
import lu.wealins.webia.core.service.validations.ClientFormValidationService;
import lu.wealins.webia.core.service.validations.appform.AppFormValidationStepService;

@Service(value = "AdditionalPremiumGdprValidationService")
public class GdprValidationServiceImpl extends AppFormValidationStepService {

	private static Set<WorkflowType> SUPPORTED_WORFLOW_TYPES = Stream.of(WorkflowType.APP_FORM, WorkflowType.ADDITIONAL_PREMIUM).collect(Collectors.toSet());

	@Override
	public Set<WorkflowType> getSupportedWorkflowTypes() {
		return SUPPORTED_WORFLOW_TYPES;
	}

	@Autowired
	private CheckStepService checkStepService;

	@Autowired
	private ClientFormValidationService clientFormValidationService;

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		List<String> errors = new ArrayList<>();
		AppFormDTO formData = getFormData(step);

		clientFormValidationService.validateGdpr(formData.getInsureds(), errors);

		return errors;
	}

	@Override
	public boolean needValidateBeforeComplete(StepDTO step) {
		return StepTypeDTO.getStepType(step.getStepWorkflow()).isAfterOrEquals(ANALYSIS)
				&& checkStepService.isMedicalQuestionsChecked(step.getCheckSteps());
	}

	@Override
	public boolean needValidateBeforeSave(StepDTO step) {
		return false;
	}

}
