package lu.wealins.webia.core.service.validations.appform.impl;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.PREMIUM_INPUT_AND_NAV;

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
import lu.wealins.webia.core.service.validations.EncashmentFundFormValidationService;
import lu.wealins.webia.core.service.validations.PaymentValidationService;
import lu.wealins.webia.core.service.validations.appform.AppFormValidationStepService;

@Service(value = "AppFormPremiumInputAndNavValidationService")
public class PremiumInputAndNavValidationServiceImpl extends AppFormValidationStepService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = Stream.of(PREMIUM_INPUT_AND_NAV).collect(Collectors.toSet());

	@Autowired
	private EncashmentFundFormValidationService encashmentFundFormValidationService;
	@Autowired
	private PaymentValidationService paymentValidationService;

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		AppFormDTO formData = getFormData(step);
		List<String> errors = new ArrayList<>();

		encashmentFundFormValidationService.validateEncashmentFundForms(formData.getFundForms(), formData.getPaymentDt(), errors);
		paymentValidationService.validatePremiumRange(formData, errors);

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
