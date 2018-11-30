package lu.wealins.webia.core.service.validations.transactionform.impl;

import static lu.wealins.common.dto.liability.services.enums.Metadata.ENOUGH_CASH;
import static lu.wealins.common.dto.liability.services.enums.Metadata.SECURITIES_TRANSFER;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.core.service.MetadataService;
import lu.wealins.webia.core.service.validations.FundTransactionFormValidationService;
import lu.wealins.webia.core.service.validations.transactionform.TransactionFormValidationService;

@Service(value = "TransactionFormCompleteAnalysisValidationService")
public class CompleteAnalysisValidationServiceImpl extends TransactionFormValidationService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = new HashSet<>();

	static {
		ENABLE_STEPS.add(StepTypeDTO.COMPLETE_ANALYSIS);
	}

	@Autowired
	private MetadataService metadataService;

	@Autowired
	private FundTransactionFormValidationService fundTransactionFormValidationService;


	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		List<String> errors = new ArrayList<>();

		TransactionFormDTO formData = getFormData(step);
		validateValudationAmount(formData, errors);

		return errors;
	}

	private void validateValudationAmount(TransactionFormDTO formData, List<String> errors) {

		Integer workflowItemId = formData.getWorkflowItemId();

		if (!metadataService.isYes(workflowItemId, ENOUGH_CASH, formData.getUpdateUser())) {
			return;
		}

		if (metadataService.isNo(workflowItemId, SECURITIES_TRANSFER, formData.getUpdateUser())) {
			fundTransactionFormValidationService.validateValuationAmounts(formData.getFundTransactionForms(), errors);
		}
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

