package lu.wealins.webia.core.service.validations.appform.impl;

import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.AWAITING_VALUATION;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.FundTransactionDTO;
import lu.wealins.common.dto.liability.services.enums.FundTransactionStatus;
import lu.wealins.common.dto.liability.services.enums.TransactionCode;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.FundFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.core.service.LiabilityFundTransactionService;
import lu.wealins.webia.core.service.validations.RelaunchStepValidationService;
import lu.wealins.webia.core.service.validations.appform.AppFormValidationStepService;

@Service(value = "AppFormAwaitingValuationValidationService")
public class AwaitingValuationValidationServiceImpl extends AppFormValidationStepService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = Stream.of(AWAITING_VALUATION).collect(Collectors.toSet());

	@Autowired
	private LiabilityFundTransactionService fundTransactionService;
	@Autowired
	private RelaunchStepValidationService relaunchStepValidationService;

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		AppFormDTO formData = getFormData(step);
		List<String> errors = new ArrayList<>();
		Integer workflowItemId = step.getWorkflowItemId();

		String workflowAction = AWAITING_VALUATION.getvalue();
//		relaunchStepValidationService.validateInForceStatus(formData, workflowAction, errors);
		relaunchStepValidationService.validateEffectiveDate(formData, workflowAction, formData.getPaymentDt(), errors);

		for (FundFormDTO fundForm : formData.getFundForms()) {
			Collection<FundTransactionDTO> premiumFundTransactions = fundTransactionService.getFundTransactions(formData.getPolicyId(), fundForm.getFundId(), TransactionCode.PRE_ALLOC,
					FundTransactionStatus.POSTED);

			if (CollectionUtils.isEmpty(premiumFundTransactions)) {
				errors.add(
						"Cannot relaunch " + workflowAction + " action for the workflow item id = " + workflowItemId + " because there is no 'POSTED' fund transaction for the fund "
								+ fundForm.getFundId() + ".");
			}
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
