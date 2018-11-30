package lu.wealins.webia.services.core.service.validations.transactionform.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.services.core.service.FundFormService;
import lu.wealins.webia.services.core.service.validations.CpsValidationService;
import lu.wealins.webia.services.core.service.validations.TransactionValidationService;
import lu.wealins.webia.services.core.service.validations.transactionform.TransactionFormValidationService;

@Service(value = "TransactionFormAnalysisValidationService")
public class AnalysisValidationServiceImpl extends TransactionFormValidationService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = Stream.of(StepTypeDTO.ANALYSIS, StepTypeDTO.COMPLETE_ANALYSIS, StepTypeDTO.AWAITING_CASH_TRANSFER).collect(Collectors.toSet());

	@Autowired
	private CpsValidationService cpsValidationService;

	@Autowired
	private TransactionValidationService transactionValidationService;

	@Autowired
	private FundFormService fundFormService;

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		List<String> errors = new ArrayList<String>();
		TransactionFormDTO analysis = getFormData(step);

		cpsValidationService.validateCpsUsers(analysis.getFirstCpsUser(), analysis.getSecondCpsUser(), errors);

		StepTypeDTO stepType = StepTypeDTO.getStepType(step.getStepWorkflow());
		if (!StepTypeDTO.ANALYSIS.equals(stepType) || fundFormService.containsFeOrFic(analysis.getFundTransactionForms())) {
			transactionValidationService.validateEffectiveDate(analysis.getEffectiveDate(), errors);
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
