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
import lu.wealins.webia.services.core.service.validations.additionalpremium.AdditionalPremiumValidationStepService;
import lu.wealins.webia.services.core.service.validations.appform.InvestmentFundValidationService;
import lu.wealins.webia.services.core.service.validations.appform.PaymentValidationService;

/**
 * @author LUR
 *
 */
@Service(value = "PremiumInputAndNavValidationStepService")
public class PremiumInputAndNavValidationStepServiceImpl extends AdditionalPremiumValidationStepService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = new HashSet<>();

	@Autowired
	private InvestmentFundValidationService investmentFundValidationService;

	@Autowired
	private PaymentValidationService paymentValidationService;

	static {
		ENABLE_STEPS.add(StepTypeDTO.PREMIUM_INPUT_AND_NAV);
	}

	@Override
	public List<String> validateBeforeSave(StepDTO step) {
		List<String> errors = new ArrayList<>();

		AppFormDTO form = getFormData(step);

		validateFundPartRules(form, errors);
		validatePayment(form, errors);
		
		return errors;
	}

	/**
	 * Perform validation on the fund parts
	 * 
	 * @param form the form model
	 * @param errors the errors list to contribute to
	 * @return a list of error message completed with the errors regarding the fund parts
	 */
	private void validateFundPartRules(AppFormDTO form, List<String> errors) {
		investmentFundValidationService.validateFundNotNull(form, errors);
		investmentFundValidationService.validateFundPartNotNull(form, errors);
		investmentFundValidationService.validateFundPartEq100(form, errors);
		// investmentFundValidationService.validateFidAndFasHaveAmoutValuation(form, errors);
	}

	/**
	 * Perform the validation on the payment part of the form
	 * 
	 * @param form the form model
	 * @param errors the errors list to contribute to
	 * @return a list of error message completed with the errors regarding the payment
	 */
	private void validatePayment(AppFormDTO form, List<String> errors) {
		paymentValidationService.validateEffectDate(form, errors);
		paymentValidationService.validatePremiumCountry(form, errors);
		paymentValidationService.validatePremiumAmount(form, errors);
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
