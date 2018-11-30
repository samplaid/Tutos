package lu.wealins.webia.core.service.validations.transactionform.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.CheckWorkflowDTO;
import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.core.service.impl.AbstractFundFormService;
import lu.wealins.webia.core.service.validations.transactionform.TransactionFormValidationService;


@Service
public class NewValidationServiceImpl extends TransactionFormValidationService {
	private static final Set<StepTypeDTO> ENABLE_STEPS = new HashSet<>();

	private static final String SECURITY_TRANSFER_NEEDS_FID_FAS = "The securities transfer can only be done if there is at least one FID or FAS.";
	private static final String SECURITY_TRANSFER_CHECK_CODE = "SEC_TRANSFER";
	private static final String YES = "YES";

	@Autowired
	private AbstractFundFormService<FundTransactionFormDTO> fundFormService;

	static {
		ENABLE_STEPS.add(StepTypeDTO.NEW);
	}

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		TransactionFormDTO formData = getFormData(step);
		List<String> errors = new ArrayList<>();
		boolean hasFidOrFas = fundFormService.hasFidOrFas(formData.getFundTransactionForms());
		boolean isSecurityTransfer = step.getCheckSteps()
				.stream()
				.map(CheckStepDTO::getCheck)
				.filter(check -> SECURITY_TRANSFER_CHECK_CODE.equals(check.getCheckCode()))
				.findFirst()
				.map(this::checkWorkflowBooleanMapper)
				.orElseThrow(() -> new IllegalStateException(String.format("The check step with code %s was not found in step NEW", SECURITY_TRANSFER_CHECK_CODE)));
		if (!hasFidOrFas && isSecurityTransfer) {
			errors.add(SECURITY_TRANSFER_NEEDS_FID_FAS);
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

	private boolean checkWorkflowBooleanMapper(CheckWorkflowDTO checkWorkflow) {
		// this logic already exists in webia-services. but the logic of has fidOrFas exists in webia which makes it hard to choose between webia-app and services for this validation.
		return checkWorkflow.getCheckData() != null && YES.equals(checkWorkflow.getCheckData().getDataValueYesNoNa());
	}

}
