package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.AdminFeesDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.liability.services.enums.FundStatus;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.webia.services.AmountDTO;
import lu.wealins.common.dto.webia.services.CreateEditingRequest;
import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.enums.FundTransactionInputType;
import lu.wealins.common.dto.webia.services.enums.PaymentType;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.mapper.WithdrawalMapper;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.service.FundFormTransactionService;
import lu.wealins.webia.core.service.LiabilityAccountTransactionService;
import lu.wealins.webia.core.service.LiabilityExchangeRateService;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityPolicyValuationService;
import lu.wealins.webia.core.service.LiabilityWithdrawalService;
import lu.wealins.webia.core.service.TransferService;
import lu.wealins.webia.core.service.helper.WithdrawalEnoughCashHelper;
import lu.wealins.webia.core.service.helper.WithdrawalServiceHelper;
import lu.wealins.webia.core.service.helper.metadata.CpsMetadataHelper;

public abstract class LiabilityMoneyOutService extends AbstractWorkflowFormService<TransactionFormDTO> implements LiabilityWithdrawalService {
	
	private static final int _250000 = 250000;

	private static final String EUR = "EUR";

	@Autowired
	private AbstractFundFormService<FundTransactionFormDTO> fundFormService;

	@Autowired
	private FundFormTransactionService fundFormTransactionService;

	@Autowired
	WithdrawalServiceHelper withdrawalServiceHelper;

	@Autowired
	LiabilityPolicyService liabilityPolicyService;

	@Autowired
	private LiabilityExchangeRateService exchangeRateService;

	@Autowired
	WithdrawalMapper withdrawalMapper;

	@Autowired
	private CpsMetadataHelper cpsMetadataHelper;

	@Autowired
	protected TransferService transferService;

	@Autowired
	private LiabilityPolicyValuationService policyValuationService;

	@Autowired
	private LiabilityAccountTransactionService accountTransactionService;

	@Autowired
	private WithdrawalEnoughCashHelper withdrawalEnoughCashHelper;

	@Autowired
	private EditingService editingService;

	@Autowired
	private LiabilityFundService liabilityFundService;

	@Override
	public TransactionFormDTO enrichFormData(TransactionFormDTO formData, String stepWorkflow, String userId) {
		fundFormService.enrichFunds(formData.getFundTransactionForms());
		return formData;
	}

	@Override
	public TransactionFormDTO completeFormData(TransactionFormDTO transactionForm, String stepWorkflow, String usrId) {

		switch (StepTypeDTO.getStepType(stepWorkflow)) {

		case AWAITING_VALUATION:
			String policyId = transactionForm.getPolicyId();
			Collection<AdminFeesDTO> adminFees = accountTransactionService.geteAdminFees(policyId, transactionForm.getEffectiveDate());

			List<TransferDTO> adminFeesPayments = adminFees.stream().map(x -> transferService.createAdminFeesTransfer(transactionForm.getWorkflowItemId(), policyId, x.getAmount(), x.getCurrency()))
					.collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(adminFeesPayments)) {
				transferService.updateTransfers(adminFeesPayments);
			}
			deactivateClosedFunds(transactionForm.getFundTransactionForms());
			break;
		case AWAITING_PUT_IN_FORCE:
			liabilityFundService.updateFidFasValuations(transactionForm.getFundTransactionForms(), transactionForm.getEffectiveDate());
			break;
		default:
			break;
		}
		return super.completeFormData(transactionForm, stepWorkflow, usrId);
	}

	/**
	 * Deactivate FID or FAS funds marked as closed
	 * 
	 * @param fundTransactionForms the fund transaction form
	 */
	private void deactivateClosedFunds(Collection<FundTransactionFormDTO> fundTransactionForms) {
		fundFormService.getFidOrFas(fundTransactionForms).stream().filter(fundTransactionForm -> fundTransactionForm.getClosure().booleanValue()).forEach(
				fundTransactionForm -> {
					FundDTO fundDTO = liabilityFundService.getFund(fundTransactionForm.getFundId());
					fundDTO.setStatus(FundStatus.INACTIVE.getValue());
					liabilityFundService.update(fundDTO);
				});
	}

	protected void createAssetManagerDocument(Integer workflowItemId) {
		Collection<CreateEditingRequest> createEditings = withdrawalEnoughCashHelper.createEnoughCashEditings(workflowItemId, false);
		if (!CollectionUtils.isEmpty(createEditings)) {
			editingService.createEditingRequests(createEditings);
		}
	}

	@Override
	public TransactionFormDTO preCompleteFormData(TransactionFormDTO transactionForm, String stepWorkflow, String usrId) {
		StepTypeDTO stepType = StepTypeDTO.getStepType(stepWorkflow);
		switch (stepType) {
		case VALIDATE_ANALYSIS:
		case COMPLETE_ANALYSIS:
		case CHECK_DOCUMENTATION:
			// This metadata must be save in the precomplete becauseu the paymment status are updated before the getCompleteMetada
			workflowService.saveMetada(createNewPayCompleteMetadata(usrId, transactionForm));
			break;
		default:
		}

		return transactionForm;
	}

	@Override
	public void abortFormData(TransactionFormDTO formData) {
	}

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.WITHDRAWAL;
	}

	@Override
	protected WorkflowItemDataDTO getCompleteMetadata(TransactionFormDTO formData, String usrId, StepTypeDTO stepType) {
		switch (stepType) {
		case ANALYSIS:
			return createAnalysisMetadata(usrId, formData);
		case AWAITING_VALUATION:
			return createAwaitingValuationMetadata(usrId, formData);
		case VALIDATE_ANALYSIS:
			return createValidateAnalysisMetadata(usrId, formData);
		default:
			return new WorkflowItemDataDTO();
		}
	}

	private WorkflowItemDataDTO createNewPayCompleteMetadata(String usrId, TransactionFormDTO formData) {
		String newPayCompleteString = Boolean.valueOf(transferService.hasReady(getTransfers(formData))).toString();
		MetadataDTO newPayCompleteMetadata = metadataService.createMetadata(Metadata.NEW_PAY_COMPLETE.getMetadata(), newPayCompleteString);

		return metadataService.createWorkflowItemData(formData.getWorkflowItemId().longValue(), usrId, newPayCompleteMetadata);
	}

	private Collection<TransferDTO> getTransfers(TransactionFormDTO formData) {
		if (formData.getPaymentType() == PaymentType.CASH_TRANSFER) {
			return formData.getPayments();
		}

		return formData.getSecuritiesTransfer();
	}

	private WorkflowItemDataDTO createValidateAnalysisMetadata(String usrId, TransactionFormDTO formData) {
		BigDecimal amount = getTransactionAmount(formData, EUR);
		String amountLess250K = Boolean.valueOf(amount != null && amount.compareTo(new BigDecimal(_250000)) < 0).toString();
		MetadataDTO amountLess250KMetadata = metadataService.createMetadata(Metadata.AMOUNT_LESS_250K.getMetadata(), amountLess250K);

		return metadataService.createWorkflowItemData(formData.getWorkflowItemId().longValue(), usrId, amountLess250KMetadata);
	}

	private WorkflowItemDataDTO createAnalysisMetadata(String usrId, TransactionFormDTO formData) {

		List<MetadataDTO> metadata = cpsMetadataHelper.createSecondCpsMetadata(formData.getSecondCpsUser());
		metadata.add(metadataService.createMetadata(Metadata.TRANSACTION_CREATED.getMetadata(), Boolean.FALSE.toString()));

		return metadataService.createWorkflowItemData(formData.getWorkflowItemId().longValue(), metadata, usrId);
	}

	private WorkflowItemDataDTO createAwaitingValuationMetadata(String usrId, TransactionFormDTO formData) {

		MetadataDTO transactionCreatedMetadata = metadataService.createMetadata(Metadata.TRANSACTION_CREATED.getMetadata(), Boolean.TRUE.toString());

		return metadataService.createWorkflowItemData(formData.getWorkflowItemId().longValue(), usrId, transactionCreatedMetadata);
	}

	@Override
	public BigDecimal getTransactionAmount(TransactionFormDTO formData, String currency) {
		Date effectiveDate = formData.getEffectiveDate() == null ? new Date() : formData.getEffectiveDate();
		BigDecimal formDataAmount = formData.getAmount();

		if (formDataAmount != null) {
			return exchangeRateService.convert(formDataAmount, formData.getCurrency(), currency, effectiveDate);
		}
		
		BigDecimal totalAmount = BigDecimal.ZERO;
		PolicyValuationDTO policyValuation = policyValuationService.getPolicyValuation(formData.getPolicyId(), currency, effectiveDate);

		if(BooleanUtils.isTrue(formData.getSpecificAmountByFund())) {
			for (FundTransactionFormDTO fundTransactionForm : formData.getFundTransactionForms()) {
				BigDecimal fundAmount = getFundAmount(formData, fundTransactionForm, policyValuation, currency, effectiveDate);
				if (fundAmount != null) {
					totalAmount = totalAmount.add(fundAmount);
				}
			}
		}

		return totalAmount;
	}

	private BigDecimal getFundAmount(TransactionFormDTO formData, FundTransactionFormDTO fundTransactionForm, PolicyValuationDTO policyValuation, String currency, Date effectiveDate) {
		String fdsId = fundTransactionForm.getFundId();
		PolicyValuationHoldingDTO policyValuationHolding = policyValuationService.getFundValuation(policyValuation, fdsId);
		FundTransactionInputType inputType = fundTransactionForm.getInputType();

		if (policyValuationHolding == null || inputType == null) {
			return null;
		}

		AmountDTO fundAmount = fundFormTransactionService.getTransactionFundAmount(formData, fundTransactionForm, policyValuationHolding);

		if (fundAmount == null) {
			return null;
		}

		return exchangeRateService.convert(fundAmount.getAmount(), fundAmount.getCurrency(), currency, effectiveDate);
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

	@Override
	public TransactionFormDTO updateFormData(TransactionFormDTO formData, String stepWorkflow, String userId) {

		StepTypeDTO stepTypeDTO = StepTypeDTO.getStepType(stepWorkflow);

		if (stepTypeDTO.equals(getFirstStep())) {
			// register connected user as CPS 1
			MetadataDTO firstCpsUserFirstNameMetaData = metadataService.createMetadata(Metadata.FIRST_CPS_USER_FIRSTNAME.getMetadata(), userId);

			String hasFEorFIC = Boolean.valueOf(fundFormService.hasFEorFIC(formData.getFundTransactionForms())).toString();
			MetadataDTO externalFundMetadata = metadataService.createMetadata(Metadata.FE_FIC.getMetadata(), hasFEorFIC);

			String hasFidOrFas = Boolean.valueOf(fundFormService.hasFidOrFas(formData.getFundTransactionForms())).toString();
			MetadataDTO fidOrFasMetadata = metadataService.createMetadata(Metadata.FID_FAS.getMetadata(), hasFidOrFas);

			workflowService.saveMetada(
					metadataService.createWorkflowItemData(Long.valueOf(formData.getWorkflowItemId().longValue()), userId, firstCpsUserFirstNameMetaData, externalFundMetadata, fidOrFasMetadata));

		}

		return super.updateFormData(formData, stepWorkflow, userId);
	}

}
