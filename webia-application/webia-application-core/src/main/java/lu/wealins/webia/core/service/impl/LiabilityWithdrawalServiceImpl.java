package lu.wealins.webia.core.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.WithdrawalInputDTO;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.dto.webia.services.enums.TransferStatus;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.service.LiabilityFundTransactionService;
import lu.wealins.webia.core.service.WebiaCheckDataService;
import lu.wealins.webia.core.service.helper.FaxSurrenderDepositBankHelper;
import lu.wealins.webia.core.service.helper.TransferIdsHelper;

@Service(value = "LiabilityWithdrawalService")
public class LiabilityWithdrawalServiceImpl extends LiabilityMoneyOutService {

	private static final String SECURITIES_TRANSFERT_BEFORE_DEBIT_OF_FINANCIAL_FEES = "SEC_TFT_BEFORE_FF";

	@Autowired
	private LiabilityFundTransactionService liabilityFundTransactionService;

	@Autowired
	private EditingService editingService;

	@Autowired
	private WebiaCheckDataService checkDataService;

	@Autowired
	private FaxSurrenderDepositBankHelper faxSurrenderDepositBankHelper;

	@Autowired
	private TransferIdsHelper transferIdsHelper;

	@Override
	public TransactionFormDTO completeFormData(TransactionFormDTO transactionForm, String stepWorkflow, String usrId) {

		super.completeFormData(transactionForm, stepWorkflow, usrId);

		Integer workflowItemId = transactionForm.getWorkflowItemId();

		switch (StepTypeDTO.getStepType(stepWorkflow)) {

		case VALIDATE_ANALYSIS:

			boolean hasFidOrFas = transactionForm.getFundTransactionForms().stream().anyMatch(ftf -> FundSubType.FID.name().equals(ftf.getFundTp()) || FundSubType.FAS.name().equals(ftf.getFundTp()));
			if (hasFidOrFas) {
				createAssetManagerDocument(workflowItemId);
			}

			CheckDataDTO secTftBeforeDebitOfFees = checkDataService.getCheckData(workflowItemId, SECURITIES_TRANSFERT_BEFORE_DEBIT_OF_FINANCIAL_FEES);
			if (secTftBeforeDebitOfFees != null && "Yes".equalsIgnoreCase(secTftBeforeDebitOfFees.getDataValueYesNoNa())) {
				createDepositBankDocuments(workflowItemId);
			}
			break;
		case AWAITING_PUT_IN_FORCE:
			createWithdrawal(transactionForm, usrId);
			break;
		case GENERATE_DOCUMENTATION:
			editingService.createWorkflowDocumentRequest(Long.valueOf(transactionForm.getWorkflowItemId()), DocumentType.WITHDRAWAL, false, false);
			break;
		case VALIDATE_INPUT:
			createAssetManagerDocument(workflowItemId);
			break;
		default:
			break;
		}

		return transactionForm;
	}

	private void createDepositBankDocuments(Integer workflowItemId) {
		faxSurrenderDepositBankHelper.createSurrenderDepositBankFaxEditings(workflowItemId, false);
	}

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.WITHDRAWAL;
	}

	@Override
	protected boolean isCps1Step(StepTypeDTO stepType) {
		return StepTypeDTO.WITHDRAWAL_CPS1_GROUP.contains(stepType);
	}

	@Override
	protected boolean isCps2Step(StepTypeDTO stepType) {
		return StepTypeDTO.WITHDRAWAL_CPS2_GROUP.contains(stepType);
	}

	@Override
	protected StepTypeDTO getFirstStep() {
		return StepTypeDTO.NEW;
	}

	private void createWithdrawal(TransactionFormDTO form, String usrId) {

		List<WithdrawalInputDTO> withdrawalByCoverages = (form.getSpecificAmountByFund() == false) ? withdrawalServiceHelper.prepareWithdrawalRequest(form)
				: withdrawalServiceHelper.prepareDefinedWithdrawalRequest(form);

		for (WithdrawalInputDTO withdrawalInputDTO : withdrawalByCoverages) {
			liabilityPolicyService.createWithdrawal(withdrawalInputDTO);
			liabilityFundTransactionService.executeFundTransactionValuation(form, usrId);
		}

	}

	@Override
	public TransactionFormDTO preCompleteFormData(TransactionFormDTO transactionForm, String stepWorkflow, String usrId) {

		TransactionFormDTO transactionFormTarget = super.preCompleteFormData(transactionForm, stepWorkflow, usrId);
		switch (StepTypeDTO.getStepType(stepWorkflow)) {

		case VALIDATE_ANALYSIS:
		case VALIDATE_BANK_INSTRUCTIONS:
		case VALIDATE_INPUT:
		case VALIDATE_DOCUMENTATION:
			// The accounting editing requests should be created here because when we complete webia-services the statuses move from ready to compta.
			// the other solution (better one) could be to bring the webia-services complete logic (status change event) here... but then again is it really worth it (lifeware....etc)?
			createAccountingNotesEditings(transactionFormTarget);
		default:
			break;
		}

		return transactionFormTarget;
	}

	private void createAccountingNotesEditings(TransactionFormDTO transactionFormTarget) {
		List<TransferDTO> readyPayments = transactionFormTarget.getPayments().stream()
				.filter(transfer -> TransferStatus.READY.name().equals(transfer.getTransferStatus()))
				.collect(Collectors.toList());

		if (!CollectionUtils.isEmpty(readyPayments)) {
			Long workflowItemId = Long.valueOf(transactionFormTarget.getWorkflowItemId());
			String transferIds = transferIdsHelper.mapDtosToString(readyPayments);
			editingService.createAccountingNoteRequest(workflowItemId, transferIds);
		}
	}
}
