package lu.wealins.webia.core.service.validations.additionalpremium.impl;

import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.VALIDATE_ADDITIONAL_PREMIUM;

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
import lu.wealins.webia.core.service.LiabilityPolicyCoverageService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.validations.additionalpremium.AdditionalPremiumValidationStepService;

@Service(value = "AdditionalPremiumValidateAdditionalPremiumValidationService")
public class ValidateAdditionalPremiumValidationServiceImpl extends AdditionalPremiumValidationStepService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = Stream.of(VALIDATE_ADDITIONAL_PREMIUM).collect(Collectors.toSet());

	@Autowired
	protected LiabilityFundService fundService;
	@Autowired
	protected LiabilityPolicyService policyService;
	@Autowired
	private LiabilityPolicyCoverageService policyCoverageService;

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		AppFormDTO appForm = getFormData(step);
		List<String> errors = new ArrayList<>();
		String policyId = appForm.getPolicyId();

		errors.addAll(fundService.validateActiveFunds(
				appForm.getFundForms().stream().map(x -> x.getFundId()).collect(Collectors.toList())));
		errors.addAll(policyService.getPolicyIncompleteDetails(policyId));
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
