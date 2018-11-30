package lu.wealins.webia.services.core.service.validations.additionalpremium.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.services.core.service.validations.CpsValidationService;
import lu.wealins.webia.services.core.service.validations.additionalpremium.AdditionalPremiumValidationStepService;

@Service(value = "AdditionalPremiumWaitingDispatchValidationService")
public class WaitingDispatchValidationServiceImpl extends AdditionalPremiumValidationStepService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = new HashSet<>();

	@Autowired
	private CpsValidationService cpsValidationService;

	static {
		ENABLE_STEPS.add(StepTypeDTO.WAITING_DISPATCH);
	}

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		List<String> errors = new ArrayList<String>();
		AppFormDTO analysis = getFormData(step);
		cpsValidationService.validateCpsUsers(analysis.getFirstCpsUser(), analysis.getSecondCpsUser(), errors);

		return errors;
	}

	@Override
	public boolean needValidateBeforeComplete(StepDTO step) {
		return ENABLE_STEPS.contains(StepTypeDTO.getStepType(step.getStepWorkflow()));
	}

	@Override
	public boolean needValidateBeforeSave(StepDTO step) {
		return ENABLE_STEPS.contains(StepTypeDTO.getStepType(step.getStepWorkflow()));
	}
}