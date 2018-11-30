package lu.wealins.webia.core.service.validations.surrender.impl;

import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.AWAITING_VALUATION;

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
import lu.wealins.webia.core.service.LiabilityFundTransactionService;
import lu.wealins.webia.core.service.validations.surrender.SurrenderValidationStepService;

@Service(value = "SurrenderAwaitingValuationValidationService")
public class SurrenderAwaitingValuationValidationServiceImpl extends SurrenderValidationStepService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = Stream.of(AWAITING_VALUATION).collect(Collectors.toSet());

	@Autowired
	private LiabilityFundTransactionService fundTransactionService;

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		TransactionFormDTO formData = getFormData(step);

		List<String> errors = new ArrayList<>();

		errors.addAll(fundTransactionService.validatePostedForSurrender(formData.getPolicyId(), formData.getEffectiveDate()));

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
