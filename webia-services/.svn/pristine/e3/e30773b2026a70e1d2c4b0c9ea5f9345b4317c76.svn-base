package lu.wealins.webia.services.core.service.validations.additionalpremium.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.services.core.service.validations.additionalpremium.AdditionalPremiumValidationStepService;

@Service(value = "NewValidationStepService")
public class NewValidationStepServiceImpl extends AdditionalPremiumValidationStepService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = new HashSet<>();

	static {
		ENABLE_STEPS.add(StepTypeDTO.NEW);
	}

	@Override
	public List<String> validateBeforeSave(StepDTO step) {
		List<String> errors = new ArrayList<>();

		AppFormDTO stepNew = getFormData(step);
		validatePolicyPresence(stepNew, errors);
		
		return errors;
	}

	@Override
	public boolean needValidateBeforeComplete(StepDTO step) {
		return false;
	}

	@Override
	public boolean needValidateBeforeSave(StepDTO step) {
		return ENABLE_STEPS.contains(StepTypeDTO.getStepType(step.getStepWorkflow()));
	}

}
