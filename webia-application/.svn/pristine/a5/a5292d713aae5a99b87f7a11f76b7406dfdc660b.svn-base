package lu.wealins.webia.core.service.validations.transactionform.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.core.service.validations.FundTransactionFormValidationService;
import lu.wealins.webia.core.service.validations.transactionform.TransactionFormValidationService;

@Service(value = "TransactionFormAwaitingCashTransferValidationService")
public class AwaitingCashTransferValidationServiceImpl extends TransactionFormValidationService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = new HashSet<>();

	static {
		ENABLE_STEPS.add(StepTypeDTO.AWAITING_CASH_TRANSFER);
	}

	@Autowired
	private FundTransactionFormValidationService fundTransactionFormValidationService;

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		List<String> errors = new ArrayList<>();

		TransactionFormDTO formData = getFormData(step);
		fundTransactionFormValidationService.validateValuationAmounts(formData.getFundTransactionForms(), errors);

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

