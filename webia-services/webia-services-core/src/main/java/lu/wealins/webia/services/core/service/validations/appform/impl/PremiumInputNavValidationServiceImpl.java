package lu.wealins.webia.services.core.service.validations.appform.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.services.core.service.validations.appform.AppFormValidationStepService;
import lu.wealins.webia.services.core.service.validations.appform.OtherClientRelationshipValidationService;


@Service(value = "PremiumInputNavValidationService")
public class PremiumInputNavValidationServiceImpl extends AppFormValidationStepService {
	private static final String PAYMENT_AMOUNT_MANDATORY = "The payment amount is mandatory.";
	private static final String PAYMENT_DATE_EFFECT_MANDATORY = "The payment date effect is mandatory.";
	private static final String PREMIUM_INPUT_NAV_NULL = "The premium input NAV data must not be null.";
	private static final Set<StepTypeDTO> ENABLE_STEPS = new HashSet<>();

	static {
		ENABLE_STEPS.add(StepTypeDTO.PREMIUM_INPUT_AND_NAV);
		ENABLE_STEPS.add(StepTypeDTO.UPDATE_INPUT);
	}

	@Autowired
	private OtherClientRelationshipValidationService otherClientRelationshipValidation;

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		AppFormDTO premiumInputNav = getFormData(step);
		Assert.notNull(premiumInputNav, PREMIUM_INPUT_NAV_NULL);
		List<String> errors = new ArrayList<String>();
		
		otherClientRelationshipValidation.assertClientCessionPartsEq100(premiumInputNav.getOtherClients(), errors);

		if(premiumInputNav.getPaymentDt() == null){
			errors.add(PAYMENT_DATE_EFFECT_MANDATORY);
		}
		
		if(premiumInputNav.getPaymentAmt() == null){
			errors.add(PAYMENT_AMOUNT_MANDATORY);
		}

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
