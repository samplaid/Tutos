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
import lu.wealins.webia.services.core.service.validations.appform.InvestmentFundValidationService;
import lu.wealins.webia.services.core.service.validations.appform.PaymentValidationService;

@Service(value = "AdditionalPremiumAnalysisValidationService")
public class AnalysisValidationServiceImpl extends AdditionalPremiumValidationStepService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = new HashSet<>();

	@Autowired
	private InvestmentFundValidationService investmentFundValidationService;
	@Autowired
	private PaymentValidationService paymentValidationService;
	@Autowired
	private CpsValidationService cpsValidationService;

	static {
		ENABLE_STEPS.add(StepTypeDTO.ANALYSIS);
		ENABLE_STEPS.add(StepTypeDTO.PREMIUM_TRANSFER_REQUEST);
	}

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		List<String> errors = new ArrayList<String>();
		AppFormDTO analysis = getFormData(step);

		paymentValidationService.validateAnalysisMandatoryFields(analysis, errors);
		paymentValidationService.validateEntryFees(analysis, errors);
		paymentValidationService.validateBrokerEntryFees(analysis.getBroker(), analysis.getEntryFeesPct(), analysis.getEntryFeesAmt(), errors);
		cpsValidationService.validateCpsUsers(analysis.getFirstCpsUser(), analysis.getSecondCpsUser(), errors);

		validateFundPartRules(analysis, errors);

		return errors;
	}

	@Override
	public List<String> validateBeforeSave(StepDTO step) {
		List<String> errors = new ArrayList<>();
		AppFormDTO analysis = getFormData(step);

		paymentValidationService.validatePaymentTransferRule(analysis, errors);

		return errors;
	}

	/**
	 * Check if the sum of the fund parts is equal to 100%
	 * 
	 * @param analysis the analysis model
	 * @return a list of error message. Empty if all rules are verified
	 */
	private void validateFundPartRules(AppFormDTO analysis, List<String> errors) {
		investmentFundValidationService.validateFundNotNull(analysis, errors);
		investmentFundValidationService.validateFundPartNotNull(analysis, errors);
		investmentFundValidationService.validateFundPartEq100(analysis, errors);
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
