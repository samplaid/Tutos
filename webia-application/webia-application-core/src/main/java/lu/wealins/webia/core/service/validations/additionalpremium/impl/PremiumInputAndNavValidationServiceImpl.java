package lu.wealins.webia.core.service.validations.additionalpremium.impl;
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
import lu.wealins.webia.core.service.LiabilityFundTransactionService;
import lu.wealins.webia.core.service.LiabilityPolicyCoverageService;
import lu.wealins.webia.core.service.validations.EncashmentFundFormValidationService;
import lu.wealins.webia.core.service.validations.additionalpremium.AdditionalPremiumValidationStepService;

@Service(value = "AdditionalPremiumInputAndNavValidationService")
public class PremiumInputAndNavValidationServiceImpl extends AdditionalPremiumValidationStepService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = Stream.of(PREMIUM_INPUT_AND_NAV).collect(Collectors.toSet());

	@Autowired
	private EncashmentFundFormValidationService encashmentFundFormValidationService;
	@Autowired
	private LiabilityFundTransactionService fundTransactionService;
	@Autowired
	private LiabilityPolicyCoverageService policyCoverageService;


	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		AppFormDTO formData = getFormData(step);
		List<String> errors = new ArrayList<>();
		String policyId = formData.getPolicyId();

		encashmentFundFormValidationService.validateEncashmentFundForms(formData.getFundForms(), formData.getPaymentDt(), errors);
		errors.addAll(fundTransactionService.validateDates(policyId, formData.getPaymentDt()));
		errors.addAll(policyCoverageService.validateCreationNewCoverage(policyId));

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
