package lu.wealins.webia.core.service.validations.withdrawal.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.FundTransactionInputType;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.core.service.LiabilityPolicyValuationService;
import lu.wealins.webia.core.service.LiabilityWithdrawalService;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.service.validations.withdrawal.WithdrawalValidationStepService;

@Service(value = "WithdrawalAnalysisValidationService")
public class AnalysisValidationServiceImpl extends WithdrawalValidationStepService {

	private static final BigDecimal HUNDRED = new BigDecimal(100);
	private static final String EUR = "EUR";
	private static final Set<StepTypeDTO> ENABLE_STEPS = Stream.of(StepTypeDTO.ANALYSIS, StepTypeDTO.COMPLETE_ANALYSIS, StepTypeDTO.AWAITING_CASH_TRANSFER).collect(Collectors.toSet());
	private static final String MAX_WITHDRAWAL_RATE = "MAX_WITHDRAWAL_RATE";

	@Autowired
	private LiabilityPolicyValuationService policyValuationService;
	@Autowired
	private WebiaApplicationParameterService applicationParameterService;
	@Autowired
	@Qualifier(value = "LiabilityWithdrawalService")
	private LiabilityWithdrawalService withdrawalService;

	private BigDecimal maxWithDrawalRate;
	private BigDecimal maxWithDrawalRateTimes100;

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		List<String> errors = new ArrayList<>();
		TransactionFormDTO formData = getFormData(step);

		Date effectiveDate = formData.getEffectiveDate() == null ? new Date() : formData.getEffectiveDate();
		PolicyValuationDTO policyValuation = policyValuationService.getPolicyValuation(formData.getPolicyId(), EUR, effectiveDate);

		validateWithdrawalAmount(errors, formData);
		validateSpecificAmountByFunds(formData, policyValuation, errors);
		validateMaximumAmount(formData, policyValuation, errors);

		return errors;
	}

	private void validateWithdrawalAmount(List<String> errors, TransactionFormDTO formData) {
		BigDecimal transactionFundAmount = withdrawalService.getTransactionAmount(formData, formData.getCurrency());
		if (transactionFundAmount == null || transactionFundAmount.compareTo(BigDecimal.ZERO) == 0) {
			errors.add("The withdrawal amount is mandatory.");
		}
	}

	private BigDecimal getMaxWithDrawalRate() {
		if (maxWithDrawalRate == null) {
			maxWithDrawalRate = applicationParameterService.getBigDecimalValue(MAX_WITHDRAWAL_RATE);
			Assert.notNull(maxWithDrawalRate, "Application parameter 'MAX_WITHDRAWAL_RATE' is not defined.");
			maxWithDrawalRateTimes100 = maxWithDrawalRate.multiply(HUNDRED);
		}
		return maxWithDrawalRate;
	}

	private void validateMaximumAmount(TransactionFormDTO formData, PolicyValuationDTO policyValuation, List<String> errors) {
		BigDecimal maxAmount = policyValuation.getTotalPolicyCurrency().multiply(getMaxWithDrawalRate());
		BigDecimal transactionAmount = withdrawalService.getTransactionAmount(formData, formData.getCurrency());

		if (transactionAmount.compareTo(maxAmount) > 0) {
			errors.add("The withdrawal amount cannot exceed to " + maxWithDrawalRateTimes100 + "% of the policy value.");
		}
	}

	private void validateSpecificAmountByFunds(TransactionFormDTO formData, PolicyValuationDTO policyValuation, List<String> errors) {
		if (BooleanUtils.isFalse(formData.getSpecificAmountByFund())) {
			return;
		}

		formData.getFundTransactionForms().forEach(x -> validateSpecificAmountByFund(x, policyValuation, errors));

	}

	private void validateSpecificAmountByFund(FundTransactionFormDTO fundTransactionForm, PolicyValuationDTO policyValuation, List<String> errors) {
		String fundId = fundTransactionForm.getFundId();
		PolicyValuationHoldingDTO policyValuationHolding = getPolicyValuationHolding(policyValuation, fundId);
		FundTransactionInputType inputType = fundTransactionForm.getInputType();

		if (inputType != null) {
			switch (inputType) {
			case GROSS_AMOUNT:
				if (fundTransactionForm.getAmount() != null && fundTransactionForm.getAmount().compareTo(policyValuationHolding.getHoldingValuePolicyCurrency()) > 0) {
					errors.add(fundTransactionForm.getFundId() + " - The gross amount cannot be higher than the fund value.");
				}
				break;
			case UNITS:
				if (fundTransactionForm.getUnits() != null && fundTransactionForm.getUnits().compareTo(policyValuationHolding.getUnits()) > 0) {
					errors.add(fundTransactionForm.getFundId() + " - The units cannot be higher than the total fund units.");
				}
				break;
			case PERCENTAGE:
				if (fundTransactionForm.getPercentage() != null && fundTransactionForm.getPercentage().compareTo(HUNDRED) > 0) {
					errors.add(fundTransactionForm.getFundId() + " - The percentage cannot be higher than 100%.");
				}
				break;
			case ALL_FUND:
				break;
			default:
				throw new UnsupportedOperationException("Unknown FundTransactionInputType.");
			}
		}

	}

	private PolicyValuationHoldingDTO getPolicyValuationHolding(PolicyValuationDTO policyValuation, String fdsId) {
		return policyValuation.getHoldings().stream().filter(x -> x.getFundId().equalsIgnoreCase(fdsId)).findFirst().orElse(null);
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
