package lu.wealins.webia.services.core.service.validations.surrender.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.services.core.service.validations.TransactionValidationService;
import lu.wealins.webia.services.core.service.validations.surrender.SurrenderValidationStepService;

@Service(value = "SurrenderAwaitingCashWealinsValidationService")
public class AwaitingCashWealinsValidationServiceImpl extends SurrenderValidationStepService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = new HashSet<>();

	@Autowired
	private TransactionValidationService transactionValidationService;

	static {
		ENABLE_STEPS.add(StepTypeDTO.AWAITING_CASH_WEALINS_ACCOUNT_NAV_AND_DATE);
	}

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		List<String> errors = new ArrayList<String>();
		TransactionFormDTO form = getFormData(step);

		transactionValidationService.validateEffectiveDate(form.getEffectiveDate(), errors);
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
