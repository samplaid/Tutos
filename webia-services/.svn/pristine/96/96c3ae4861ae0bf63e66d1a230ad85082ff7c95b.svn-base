package lu.wealins.webia.services.core.service.validations.appform.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.common.dto.webia.services.PolicyTransferFormDTO;
import lu.wealins.webia.services.core.service.ApplicationParameterService;
import lu.wealins.webia.services.core.service.validations.appform.PaymentValidationService;

@Service
public class PaymentValiationServiceImpl implements PaymentValidationService {

	private static final String A_VALUE_MUST_BE_SET_FOR_THE_RE_INVEST_FLAG = "A value must be set for the Re-Invest flag";
	private static final String A_VALUE_MUST_BE_SET_FOR_THE_TRANSFER_FLAG = "A value must be set for the Transfer flag";
	private static final String BROKER_ENTRY_FEES_CANNOT_BE_GREATER_THAN_THE_CONTRACT_ENTRY_FEES = "Broker entry fees cannot be greater than the contract entry fees";
	private static final String BROKER_ENTRY_FEES_IS_MANDATORY = "Broker entry fees is mandatory.";
	private static final String BROKER_IS_MANDATORY = "Broker is mandatory.";
	private static final String EXPECTED_PREMIUM_IS_MANDATORY = "Expected Premium is mandatory.";
	private static final String THE_PAYMENT_DATE_OF_EFFECT_SHOULD_NOT_OCCUR_IN_THE_FUTURE = "The payment date of effect should not occur in the future";
	private static final String THE_PAYMENT_DATE_OF_EFFECT_IS_MANDATORY = "The payment date of effect is mandatory";
	private static final String THE_PAYMENT_AMOUNT_IS_MANDATORY = "The payment amount is mandatory";
	private static final String CONTRACT_ENTRY = "contract entry";
	private static final String CONTRACT_ENTRY_FEES_IS_MANDATORY = "Contract entry fees is mandatory.";
	private static final String MAX_INIT_CHARGE = "MAX_INIT_CHARGE";
	private static final String POLICY_PAYMENT_TRANSFER = "Re-invest and transfer flags can not be set to yes simultaneously.";
	private static final String POLICY_TRANSFER_SHOUD_BE_DEFINED = "At least one policy transfer shoud be defined if the transfer flag is set to true";
	private static final String DATE_OF_EFFECT_OF_OLD_POLICY_IS_MISSING = "The date of effect of old policy is missing";
	private static final String POLICY_NUMBER_OF_THE_TRANSFER_IS_MISSING = "The policy number of the transfer is missing";
	private static final String A_POLICY_SHOULD_APPEAR_AT_MOST_ONCE_IN_THE_POLICY_TRANFERS = "A policy should appear at most once in the policy tranfers";
	private static final String PREMIUM_COUNTRY_REQUIRED = "The premium country is mandatory.";
	private static final String PAYMENT_TRANSFER_REQUIRED = "The Re-Invest flag in the premium part is mandatory.";
	private static final String MAX_FEES_PERCENTAGE = "MAX_FEES_PERCENTAGE";
	private static final String MIN_FEES_AMOUNT = "MIN_FEES_AMOUNT";

	@Autowired
	private ApplicationParameterService applicationParameterService;

	// Cache value
	private BigDecimal percentMaxValue;
	// Cache value
	private BigDecimal amountMinValue;

	@Override
	public void validatePaymentTransferRule(AppFormDTO appForm, List<String> errors) {

		if (appForm.getPaymentTransfer() == null) {
			errors.add(A_VALUE_MUST_BE_SET_FOR_THE_RE_INVEST_FLAG);
		}

		if (appForm.getPolicyTransfer() == null) {
			errors.add(A_VALUE_MUST_BE_SET_FOR_THE_TRANSFER_FLAG);
		}

		if (BooleanUtils.isTrue(appForm.getPaymentTransfer()) && BooleanUtils.isTrue(appForm.getPolicyTransfer())) {
			errors.add(POLICY_PAYMENT_TRANSFER);
		}

		// Assert that there is at least one policy transfer defined, if policy transfer flag is checked
		if (BooleanUtils.isTrue(appForm.getPolicyTransfer()) && CollectionUtils.isEmpty(appForm.getPolicyTransferForms())) {
			errors.add(POLICY_TRANSFER_SHOUD_BE_DEFINED);
		}

		// Assert that the policy transfer forms are defined correctly
		for (PolicyTransferFormDTO policyTransferFormDTO : appForm.getPolicyTransferForms()) {
			if (policyTransferFormDTO.getFromPolicyEffectDt() == null && !errors.contains(DATE_OF_EFFECT_OF_OLD_POLICY_IS_MISSING)) {
				errors.add(DATE_OF_EFFECT_OF_OLD_POLICY_IS_MISSING);
			}
			if (policyTransferFormDTO.getFromPolicy().isEmpty() && !errors.contains(POLICY_NUMBER_OF_THE_TRANSFER_IS_MISSING)) {
				errors.add(POLICY_NUMBER_OF_THE_TRANSFER_IS_MISSING);
			}
		}

		// Assert that a policy does not appear more than once
		Collection<String> policies = appForm.getPolicyTransferForms().stream().map(x -> x.getFromPolicy()).filter(x -> x != null).collect(Collectors.toList());
		if (policies.stream().filter(i -> Collections.frequency(policies, i) > 1).count() > 0) {
			errors.add(A_POLICY_SHOULD_APPEAR_AT_MOST_ONCE_IN_THE_POLICY_TRANFERS);
		}
	}

	@Override
	public void validateSubscriptionMandatoryFields(AppFormDTO appForm, List<String> errors) {
		validateInMoneyMandatoryFields(appForm, errors);

		PartnerFormDTO broker = appForm.getBroker();
		if (broker == null || StringUtils.isEmpty(broker.getPartnerId())) {
			errors.add(BROKER_IS_MANDATORY);
		} else {
			validateMandatoryBrokerEntryFees(appForm, errors);
		}
	}

	private void validateMandatoryBrokerEntryFees(AppFormDTO appForm, List<String> errors) {
		PartnerFormDTO broker = appForm.getBroker();
		if (broker != null && broker.getEntryFeesPct() == null && broker.getEntryFeesAmt() == null) {
			errors.add(BROKER_ENTRY_FEES_IS_MANDATORY);
		}
	}

	@Override
	public void validateAnalysisMandatoryFields(AppFormDTO appForm, List<String> errors) {
		validateInMoneyMandatoryFields(appForm, errors);

		validateRegistrationMandatoryFields(appForm, errors);
	}

	@Override
	public void validateRegistrationMandatoryFields(AppFormDTO appForm, List<String> errors) {

		validateMandatoryExpectedPremium(appForm, errors);

		validateMandatoryBrokerEntryFees(appForm, errors);

		validateMandatoryEntryFees(appForm, errors);
	}

	private void validateInMoneyMandatoryFields(AppFormDTO appForm, List<String> errors) {
		validatePremiumCountry(appForm, errors);

		if (appForm.getPaymentTransfer() == null) {
			errors.add(PAYMENT_TRANSFER_REQUIRED);
		}
		}

	private void validateMandatoryEntryFees(AppFormDTO appForm, List<String> errors) {
		if (appForm.getEntryFeesPct() == null && appForm.getEntryFeesAmt() == null) {
			errors.add(CONTRACT_ENTRY_FEES_IS_MANDATORY);
		}
	}

	private void validateMandatoryExpectedPremium(AppFormDTO appForm, List<String> errors) {
		if (appForm.getExpectedPremium() == null) {
			errors.add(EXPECTED_PREMIUM_IS_MANDATORY);
		}
	}

	@Override
	public void validateEntryFees(AppFormDTO appForm, List<String> errors) {
		String percentMaxInitCharge = applicationParameterService.getStringValue(MAX_INIT_CHARGE);
		if (appForm.getEntryFeesAmt() != null && appForm.getExpectedPremium() != null && StringUtils.isNotEmpty(percentMaxInitCharge)) {
			BigDecimal maxAllowedAmount = appForm.getExpectedPremium().multiply(new BigDecimal(percentMaxInitCharge)).divide(new BigDecimal(100));
			if (appForm.getEntryFeesAmt().compareTo(maxAllowedAmount) > 0) {
				errors.add("The contract entry fees must not greater than the " + percentMaxInitCharge + "% of the expected amount.");
			}
		}

		if (appForm.getEntryFeesPct() != null
				&& StringUtils.isNotEmpty(percentMaxInitCharge)
				&& appForm.getEntryFeesPct().compareTo(new BigDecimal(percentMaxInitCharge)) > 0) {
			errors.add("The contract percent entry fees " + appForm.getEntryFeesPct() + "% must not be greater than the " + percentMaxInitCharge + "%");
		}

		validateMaxFeesPercentage(appForm.getEntryFeesPct(), CONTRACT_ENTRY, errors);
		validateMinFeesAmount(appForm.getEntryFeesAmt(), CONTRACT_ENTRY, errors);
	}

	@Override
	public void validateMaxFeesPercentage(BigDecimal value, String name, List<String> errors) {
		BigDecimal pctMaxValue = getPercentMaxValue();
		if (value != null && value.compareTo(pctMaxValue) > 0 && value.compareTo(BigDecimal.ZERO) != 0) {
			errors.add("The " + name + " fees must not be greater than " + pctMaxValue.toPlainString() + "%.");
		}
	}

	@Override
	public void validateMinFeesAmount(BigDecimal value, String name, List<String> errors) {
		BigDecimal minAmount = getAmountMinValue();
		if (value != null && value.compareTo(minAmount) < 0 && value.compareTo(BigDecimal.ZERO) != 0) {
			errors.add("The " + name + " fees must be greater than " + minAmount.toPlainString() + ".");
		}
	}

	@Override
	public void validateBrokerEntryFees(PartnerFormDTO broker, BigDecimal entryFeesPct, BigDecimal entryFeesAmt, List<String> errors) {
		if (broker != null) {
			validateMaxFeesPercentage(broker.getEntryFeesPct(), "broker entry", errors);
			validateMinFeesAmount(broker.getEntryFeesAmt(), "broker entry", errors);
			validateMaxFeesPercentage(broker.getMngtFeesPct(), "broker management", errors);
			validateBrokerEntryFees(broker.getEntryFeesPct(), entryFeesPct, errors);
			validateBrokerEntryFees(broker.getEntryFeesAmt(), entryFeesAmt, errors);
		}
	}

	private void validateBrokerEntryFees(BigDecimal brokerEntryFees, BigDecimal entryFees, List<String> errors) {

		if (brokerEntryFees != null && entryFees != null && brokerEntryFees.compareTo(entryFees) > 0) {
			errors.add(BROKER_ENTRY_FEES_CANNOT_BE_GREATER_THAN_THE_CONTRACT_ENTRY_FEES);
		}

	}

	private BigDecimal getPercentMaxValue() {

		if (percentMaxValue != null) {
			return percentMaxValue;
		}

		percentMaxValue = applicationParameterService.getBigDecimalValue(MAX_FEES_PERCENTAGE);

		return percentMaxValue;
	}

	private BigDecimal getAmountMinValue() {

		if (amountMinValue != null) {
			return amountMinValue;
		}

		amountMinValue = applicationParameterService.getBigDecimalValue(MIN_FEES_AMOUNT);

		return amountMinValue;
	}

	@Override
	public void validateEffectDate(AppFormDTO appForm, List<String> errors) {
		if (appForm.getPaymentDt() == null) {
			errors.add(THE_PAYMENT_DATE_OF_EFFECT_IS_MANDATORY);
		} else if (appForm.getPaymentDt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(LocalDate.now())) {
			errors.add(THE_PAYMENT_DATE_OF_EFFECT_SHOULD_NOT_OCCUR_IN_THE_FUTURE);
		}
	}

	@Override
	public void validatePremiumAmount(AppFormDTO appForm, List<String> errors) {
		if (appForm.getPaymentAmt() == null) {
			errors.add(THE_PAYMENT_AMOUNT_IS_MANDATORY);
		}
	}

	@Override
	public void validatePremiumCountry(AppFormDTO appForm, List<String> errors) {
		if (StringUtils.isBlank(appForm.getPremiumCountryCd())) {
			errors.add(PREMIUM_COUNTRY_REQUIRED);
		}
	}
}
