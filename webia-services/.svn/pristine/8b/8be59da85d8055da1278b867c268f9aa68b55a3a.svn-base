package lu.wealins.webia.services.core.service.validations.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.TransferSecurityDTO;
import lu.wealins.common.dto.webia.services.enums.FundTransactionInputType;
import lu.wealins.common.dto.webia.services.enums.PaymentType;
import lu.wealins.webia.services.core.service.FundFormService;
import lu.wealins.webia.services.core.service.validations.TransactionValidationService;

@Service
public class TransactionValidationServiceImpl implements TransactionValidationService {

	private static final String EFFECTIVE_DATE_MANDATORY = "The effective date is mandatory";
	private static final String EFFECTIVE_DATE_FUTURE = "The effective date can't be in the future";
	private static final String READY = "READY";
	private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

	@Autowired
	private FundFormService fundFormService;

	@Override
	public void validateMandatoryFields(TransactionFormDTO transaction, List<String> errors) {

		if (transaction.getSpecificAmountByFund() == null) {
			errors.add("The specific amount flag should be defined.");
		} else {
			if (BooleanUtils.isTrue(transaction.getSpecificAmountByFund())) {
				validateSpecificAmountFields(transaction, errors);
			} else {
				validateAmountFields(transaction, errors);
			}
		}
	}

	private void validateAmountFields(TransactionFormDTO transaction, List<String> errors) {
		if (transaction.getAmountType() == null) {
			errors.add("The amount type is mandatory.");
		}

		if (transaction.getAmount() == null) {
			errors.add("The transaction amount is mandatory.");
		} else if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			errors.add("The transaction amount must be greater than 0.");
		}
	}

	@Override
	public void validateMandatorySpecificAmount(TransactionFormDTO transaction, List<String> errors) {
		Collection<FundTransactionFormDTO> fundTransactionForms = transaction.getFundTransactionForms();
		Collection<FundTransactionFormDTO> fidOrFas = fundFormService.getFidOrFas(fundTransactionForms);
		boolean isOnlyOneFidOrFas = fidOrFas.size() == 1 && fundTransactionForms.size() == 1;

		if (!isOnlyOneFidOrFas && BooleanUtils.isFalse(transaction.getSpecificAmountByFund()) && fidOrFas.size() > 0) {
			errors.add("Specific amount by fund is mandatory when there is more than one FID or FAS.");
		}
	}

	private void validateSpecificAmountFields(TransactionFormDTO transaction, List<String> errors) {
		transaction.getFundTransactionForms().forEach(x -> validateFundTransaction(x, errors));
	}

	private void validateFundTransaction(FundTransactionFormDTO fundTransactionForm, List<String> errors) {

		String fundId = fundTransactionForm.getFundId();
		FundTransactionInputType inputType = fundTransactionForm.getInputType();

		if (inputType == null) {
			return;
		}

		if (fundTransactionForm.getInputType() != null) {
			if (fundTransactionForm.getInputType() == FundTransactionInputType.GROSS_AMOUNT && fundTransactionForm.getAmount() == null) {
				errors.add(fundId + " - Gross amount is mandatory.");
			}
			if (fundTransactionForm.getInputType() == FundTransactionInputType.UNITS && fundTransactionForm.getUnits() == null) {
				errors.add(fundId + " - Units are mandatory.");
			}
			if (fundTransactionForm.getInputType() == FundTransactionInputType.PERCENTAGE && fundTransactionForm.getPercentage() == null) {
				errors.add(fundId + " - The percentage is mandatory.");
			}
			if (isFIDorFAS(fundTransactionForm.getFund()) && fundTransactionForm.getValuationAmt() == null) {
				errors.add(fundId + " - Valuation amount is mandatory.");
			}

		}

	}

	private boolean isFIDorFAS(FundLiteDTO fundDTO) {
		return FundSubType.FID.name().equals(fundDTO.getFundSubType()) || FundSubType.FAS.name().equals(fundDTO.getFundSubType());
	}

	@Override
	public void validateTransactionFees(BigDecimal transactionFees, BigDecimal brokerTransactionFees, String operation, List<String> errors) {
		if (transactionFees == null) {
			errors.add("The " + operation + " fees is mandatory.");
		}
		if (brokerTransactionFees == null) {
			errors.add("The broker " + operation + " fees is mandatory.");
		}
		validatePercentage(brokerTransactionFees, "The broker " + operation + " fees", errors);
		validatePercentage(transactionFees, "The " + operation + " fees", errors);
		if (transactionFees != null && brokerTransactionFees != null
				&& brokerTransactionFees.compareTo(transactionFees) > 0) {
			errors.add("The broker " + operation + " fees  (" + brokerTransactionFees + "%) should not be greater than the " + operation + " fees (" + transactionFees + "%).");
		}
	}

	private void validatePercentage(BigDecimal value, String valueLabel, List<String> errors) {
		if (value != null && (value.compareTo(HUNDRED) > 0 || value.compareTo(BigDecimal.ZERO) < 0)) {
			errors.add(valueLabel + " should be included in the range [0, 100]");
		}
	}

	@Override
	public void validateEffectiveDate(Date effectiveDate, List<String> errors) {

		if (effectiveDate == null) {
			errors.add(EFFECTIVE_DATE_MANDATORY);
		} else {

			if (effectiveDate.after(new Date())) {
				errors.add(EFFECTIVE_DATE_FUTURE);
			}
		}
	}

	@Override
	public void validatePayment(TransactionFormDTO transaction, List<String> errors) {
		if (transaction.getPaymentType() == null) {
			errors.add("At least one cash or securities transfer should be present.");
		} else {
			if (transaction.getPaymentType() == PaymentType.CASH_TRANSFER) {
				validateTransfer(transaction.getPayments(), errors);
			} else {
				validateSecuritiesTransfers(transaction.getSecuritiesTransfer(), errors);
			}
		}

	}

	private void validateSecuritiesTransfers(Collection<TransferDTO> securitiesTransfers, List<String> errors) {
		securitiesTransfers.forEach(x -> validateSecuritiesTransfer(x, errors));
	}

	private void validateSecuritiesTransfer(TransferDTO securitiesTransfer, List<String> errors) {
		if (securitiesTransfer == null) {
			errors.add("A securities transfer is mandatory.");
		} else {
			validateTransfer(securitiesTransfer, errors, "");
			ValidateSecurities(securitiesTransfer, errors);
		}

	}

	private void ValidateSecurities(TransferDTO securitiesTransfer, List<String> errors) {
		Collection<TransferSecurityDTO> transferSecurities = securitiesTransfer.getTransferSecurities();
		if (CollectionUtils.isEmpty(transferSecurities)) {
			errors.add("A transfer security is mandatory.");
		} else {
			if (StringUtils.isEmpty(securitiesTransfer.getTransferSecuritiesComment())) {
				transferSecurities.forEach(x -> validateSecurity(x, errors));
			}
		}

	}

	private void validateSecurity(TransferSecurityDTO transferSecurity, List<String> errors) {
		// if (StringUtils.isBlank(transferSecurity.get)) {
		if (StringUtils.isBlank(transferSecurity.getFundName())) {
			errors.add("The security name is mandatory.");
		}
		if (StringUtils.isBlank(transferSecurity.getIsin())) {
			errors.add("the security ISIN is mandatory.");
		}
		if (transferSecurity.getUnits() == null) {
			errors.add("the security quantity is mandatory.");
		}

	}

	private void validateTransfer(Collection<TransferDTO> payments, List<String> errors) {
		if (payments == null || payments.size() < 1) {
			errors.add("A cash transfer is mandatory.");
		} else {
			Integer paymentNr = 1;
			for (TransferDTO transfer : payments) {
				if (StringUtils.compareIgnoreCase(READY, transfer.getTransferStatus()) == 0) {
					String number = "nr";
					String paymentNumber = (payments.size()) > 1 ? number + paymentNr.toString() : "";
					validateTransfer(transfer, errors, paymentNumber);
					validateCashPaymentfields(transfer, errors, paymentNumber);
					paymentNr++;
				}
			}
		}

	}

	private void validateCashPaymentfields(TransferDTO transfer, List<String> errors, String paymentNumber) {
		if (transfer.getTrfMt() == null) {
			errors.add("The payment " + paymentNumber + " amount is mandatory.");
		}
		// TODO : validate transferStatus
	}

	private void validateTransfer(TransferDTO transfer, List<String> errors, String paymentNumber) {

		
		if (StringUtils.isBlank(transfer.getLibDonOrd())) {
			errors.add("The payment " + paymentNumber + " source is mandatory.");
		}

		if (StringUtils.isBlank(transfer.getIbanDonOrd())) {
			errors.add("The payment " + paymentNumber + " source account is mandatory.");
		}

		if (StringUtils.isBlank(transfer.getSwiftDonOrd())) {
			errors.add("The payment " + paymentNumber + "  BIC is mandatory.");
		}

		if (StringUtils.isBlank(transfer.getLibBenef())) {
			errors.add("The payment " + paymentNumber + "  beneficiary is mandatory.");
		}

		if (StringUtils.isBlank(transfer.getIbanBenef())) {
			errors.add("The payment " + paymentNumber + "  beneficiary account is mandatory.");
		}

		if (StringUtils.isBlank(transfer.getSwiftBenef())) {
			errors.add("The payment " + paymentNumber + " beneficiary BIC is mandatory.");
		}

		if (StringUtils.isBlank(transfer.getTrfComm())) {
			errors.add("The payment " + paymentNumber + " reference is mandatory.");
		}

	}

}
