package lu.wealins.webia.core.service.validations.transactionform.impl;

import static lu.wealins.common.dto.liability.services.enums.Metadata.ENOUGH_CASH;
import static lu.wealins.common.dto.liability.services.enums.Metadata.SECURITIES_TRANSFER;
import static lu.wealins.common.dto.liability.services.enums.Metadata.STEP_ACCEPTANCE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.IBANValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.core.service.CheckStepService;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.LiabilityWithdrawalService;
import lu.wealins.webia.core.service.MetadataService;
import lu.wealins.webia.core.service.validations.FundTransactionFormValidationService;
import lu.wealins.webia.core.service.validations.transactionform.TransactionFormValidationService;

@Service(value = "TransactionFormAnalysisValidationService")
public class AnalysisValidationServiceImpl extends TransactionFormValidationService {
	private static final Set<StepTypeDTO> ENABLE_STEPS = Stream.of(StepTypeDTO.ANALYSIS, StepTypeDTO.COMPLETE_ANALYSIS, StepTypeDTO.AWAITING_CASH_TRANSFER).collect(Collectors.toSet());

	@Autowired
	private MetadataService metadataService;
	@Autowired
	private CheckStepService checkStepService;
	@Autowired
	@Qualifier(value = "LiabilityWithdrawalService")
	private LiabilityWithdrawalService withdrawalService;

	@Autowired
	private FundTransactionFormValidationService fundTransactionFormValidationService;

	@Autowired
	private LiabilityFundService fundService;

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		List<String> errors = new ArrayList<>();

		validateClosureAccount(step, errors);
		validateValuationAmount(step, errors);
		validateIbanformat(step, errors);

		return errors;
	}

	private void validateIbanformat(StepDTO step, List<String> errors) {
		TransactionFormDTO formData = getFormData(step);
		for(TransferDTO transferDTO : formData.getSecuritiesTransfer()) {
			if (!StringUtils.isEmpty(transferDTO.getIbanBenef()) && !IBANValidator.getInstance().isValid(transferDTO.getIbanBenef().replaceAll("\\s", ""))) {
				errors.add("The account " + transferDTO.getIbanBenef() + " does not respect the IBAN format");
			}
		}
	}

	private void validateClosureAccount(StepDTO step, List<String> errors) {

		TransactionFormDTO formData = getFormData(step);
		if (formData.getFundTransactionForms().stream().anyMatch(ftf -> fundService.isFIDorFAS(ftf.getFund()) && ftf.getClosure() == null)) {
			errors.add("Closure account must be set.");
		}
	}

	private void validateValuationAmount(StepDTO step, List<String> errors) {
		Collection<CheckStepDTO> checkSteps = step.getCheckSteps();
		StepTypeDTO stepType = StepTypeDTO.getStepType(step.getStepWorkflow());
		TransactionFormDTO formData = getFormData(step);

		// if External Funds are present, perform directly some validations
		if (formData.getFundTransactionForms().stream().anyMatch(ft -> FundSubType.FE.name().equals(ft.getFund().getFundSubType()))) {
			fundTransactionFormValidationService.validateValuationAmounts(formData.getFundTransactionForms(), errors);
			return;
		}

		boolean isStepAcceptanceIsNo = StepTypeDTO.ANALYSIS.equals(stepType) ? checkStepService.isNo(checkSteps, STEP_ACCEPTANCE)
				: metadataService.isNo(step.getWorkflowItemId(), STEP_ACCEPTANCE, formData.getUpdateUser());
		
		if (!isStepAcceptanceIsNo) {
			return;
		}
		
		boolean isEnoughCashIsYes = StepTypeDTO.ANALYSIS.equals(stepType) ? checkStepService.isYes(checkSteps, ENOUGH_CASH)
				: metadataService.isYes(step.getWorkflowItemId(), ENOUGH_CASH, formData.getUpdateUser());
		if (!isEnoughCashIsYes) {
			return;
		}

		if (metadataService.isNo(step.getWorkflowItemId(), SECURITIES_TRANSFER, formData.getUpdateUser())) {
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

