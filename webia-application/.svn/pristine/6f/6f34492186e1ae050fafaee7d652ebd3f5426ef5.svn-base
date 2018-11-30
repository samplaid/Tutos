package lu.wealins.webia.core.service.validations.appform.impl;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.VALIDATE_PREMIUM_AND_NAV;

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
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.validations.appform.AppFormValidationStepService;

@Service(value = "AppFormValidatePremiumInputAndNavValidationService")
public class ValidatePremiumInputAndNavValidationServiceImpl extends AppFormValidationStepService {

	private static Set<WorkflowType> SUPPORTED_WORFLOW_TYPES = Stream.of(WorkflowType.APP_FORM, WorkflowType.ADDITIONAL_PREMIUM).collect(Collectors.toSet());
	private static final Set<StepTypeDTO> ENABLE_STEPS = Stream.of(VALIDATE_PREMIUM_AND_NAV).collect(Collectors.toSet());

	@Autowired
	private LiabilityFundService fundService;
	@Autowired
	private LiabilityPolicyService policyService;

	static {
		ENABLE_STEPS.add(StepTypeDTO.VALIDATE_PREMIUM_AND_NAV);
	}

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		List<String> errors = new ArrayList<>();
		AppFormDTO appForm = getFormData(step);

		errors.addAll(fundService.validateActiveFunds(
				appForm.getFundForms().stream().map(x -> x.getFundId()).collect(Collectors.toList())));
		errors.addAll(policyService.getPolicyIncompleteDetails(appForm.getPolicyId()));

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
