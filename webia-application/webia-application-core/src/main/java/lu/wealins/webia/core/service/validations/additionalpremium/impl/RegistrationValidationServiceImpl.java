package lu.wealins.webia.core.service.validations.additionalpremium.impl;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.REGISTRATION;

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
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.validations.additionalpremium.AdditionalPremiumValidationStepService;

@Service(value = "AdditionalRegistrationValidationService")
public class RegistrationValidationServiceImpl extends AdditionalPremiumValidationStepService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = Stream.of(REGISTRATION).collect(Collectors.toSet());

	@Autowired
	private LiabilityFundService fundService;

	@Override
	public List<String> validateBeforeSave(StepDTO step) {
		AppFormDTO formData = getFormData(step);

		// This is just a refactoring, I am not responsible of this --> contact LUR instead of! :-D
		// prepare the fund to validate
		fundService.removeEmptyFunds(formData.getFundForms());
		fundService.initFundsCurrency(formData.getFundForms(), formData.getContractCurrency());

		return new ArrayList<>();
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
