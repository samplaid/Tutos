package lu.wealins.webia.services.core.service.validations.appform.impl;

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
import lu.wealins.webia.services.core.service.validations.appform.AppFormValidationStepService;

@Service(value = "DispatchValidationService")
public class DispatchValidationServiceImpl extends AppFormValidationStepService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = new HashSet<>();

	@Autowired
	private CpsValidationService cpsValidationService;

	static {
		ENABLE_STEPS.add(StepTypeDTO.WAITING_DISPATCH);
		ENABLE_STEPS.add(StepTypeDTO.UPDATE_INPUT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.DispatchValidationService#validateBeforeComplete(lu.wealins.common.dto.webia.services.AppFormDTO)
	 */
	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		AppFormDTO dispatch = getFormData(step);
		List<String> errors = new ArrayList<>();

		String firstCpsUser = dispatch.getFirstCpsUser();
		String secondCpsUser = dispatch.getSecondCpsUser();

		cpsValidationService.validateCpsUsers(firstCpsUser, secondCpsUser, errors);

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

}
