package lu.wealins.webia.services.core.service.impl;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.enums.PaymentType;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.services.core.service.CheckDataService;
import lu.wealins.webia.services.core.service.TransferService;

@Service(value = "WithdrawalFormService")
public class WithdrawalFormServiceImpl extends TransactionFormServiceImpl {

	private static final String SECURITY_TRANSFER_CHECK_CODE = "SEC_TRANSFER";

	private static final String TRANSACTION_FORM_CANNOT_BE_NULL = "TransactionForm cannot be null.";

	@Autowired
	private CheckDataService checkDataService;

	@Autowired
	private TransferService transferService;

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.WITHDRAWAL;
	}

	@Override
	public TransactionFormDTO updateFormData(TransactionFormDTO transactionForm, String stepWorkflow) {
		Assert.notNull(transactionForm, TRANSACTION_FORM_CANNOT_BE_NULL);

		// The transfers are not related to the transaction form by cascade persist because it needs specific update rules.
		updateTransfers(transactionForm);

		return super.updateFormData(transactionForm, stepWorkflow);
	}

	@Override
	public TransactionFormDTO complete(TransactionFormDTO formData, String stepWorkflow, Integer workflowItemId) {
		formData = super.complete(formData, stepWorkflow, workflowItemId);

		StepTypeDTO stepType = StepTypeDTO.getStepType(stepWorkflow);
		switch (stepType) {
		case NEW:
			return updatePaymentType(formData, stepWorkflow, workflowItemId);
		case VALIDATE_ANALYSIS:
		case VALIDATE_BANK_INSTRUCTIONS:
		case VALIDATE_INPUT:
		case VALIDATE_DOCUMENTATION:
			return performCps2Acceptation(formData);
		default:
			return formData;
		}
	}

	private void updateTransfers(TransactionFormDTO transactionForm) {
		Collection<TransferDTO> payments = transferService.updateWithdrawalTransfers(transactionForm.getPayments());
		transactionForm.setPayments(payments);

		Collection<TransferDTO> securitiesTransfers = transactionForm.getSecuritiesTransfer();
		if (CollectionUtils.isNotEmpty(securitiesTransfers)) {
			transactionForm.setSecuritiesTransfer(transferService.updateWithdrawalTransfers(securitiesTransfers));
		}
	}

	private TransactionFormDTO performCps2Acceptation(TransactionFormDTO transactionForm) {
		transactionForm.setPayments(transferService.updateToComptaStatus(transactionForm.getPayments()));

		Collection<TransferDTO> securitiesTransfers = transactionForm.getSecuritiesTransfer();
		if (CollectionUtils.isNotEmpty(securitiesTransfers)) {
			transactionForm.setSecuritiesTransfer(transferService.acceptByCps2(securitiesTransfers));
		}

		return transactionForm;
	}

	protected TransactionFormDTO updatePaymentType(TransactionFormDTO formData, String stepWorkflow, Integer workflowItemId) {
		boolean hasSecurityTransfer = checkDataService.hasYesValue(workflowItemId, SECURITY_TRANSFER_CHECK_CODE);
		PaymentType paymentType = getPaymentType(hasSecurityTransfer);
		formData.setPaymentType(paymentType);

		return updateFormData(formData, stepWorkflow);
	}

	private PaymentType getPaymentType(boolean hasSecurityTransfer) {
		return hasSecurityTransfer ? PaymentType.SECURITY_TRANSFER : PaymentType.CASH_TRANSFER;
	}

}
