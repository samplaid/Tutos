package lu.wealins.webia.core.service.validations.appform.impl;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.GENERATE_MANAGEMENT_MANDATE;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.GENERATE_MANDAT_DE_GESTION;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.PREMIUM_TRANSFER_REQUEST;

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
import lu.wealins.webia.core.service.validations.FundFormValidationService;
import lu.wealins.webia.core.service.validations.appform.AppFormValidationStepService;

@Service(value = "AppFormGenerationDocumentValidationService")
public class GenerationDocumentValidationServiceImpl extends AppFormValidationStepService {

	private static Set<WorkflowType> SUPPORTED_WORFLOW_TYPES = Stream.of(WorkflowType.APP_FORM, WorkflowType.ADDITIONAL_PREMIUM).collect(Collectors.toSet());
	private static final Set<StepTypeDTO> ENABLE_STEPS = Stream.of(PREMIUM_TRANSFER_REQUEST, GENERATE_MANDAT_DE_GESTION, GENERATE_MANAGEMENT_MANDATE).collect(Collectors.toSet());

	@Autowired
	private FundFormValidationService fundFormValidationService;

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		AppFormDTO formData = getFormData(step);
		List<String> errors = new ArrayList<>();

		fundFormValidationService.validateFIDorFASForDocumentation(formData.getFundForms(), errors);

		return errors;
	}

	@Override
	public boolean needValidateBeforeComplete(StepDTO step) {
		return ENABLE_STEPS.contains(StepTypeDTO.getStepType(step.getStepWorkflow()));
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
