package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDetailsDTO;
import lu.wealins.common.dto.liability.services.enums.EventType;
import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;
import lu.wealins.common.dto.webia.services.SurrenderTransactionDetailsDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.AmountType;
import lu.wealins.common.security.SecurityContextHelper;
import lu.wealins.webia.core.service.LiabilityFundTransactionService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityTransactionService;
import lu.wealins.webia.core.service.TransactionFormService;
import lu.wealins.webia.core.service.WebiaFormDataService;
import lu.wealins.webia.core.service.impl.form.data.WorkflowItemSpecific;

public abstract class MoneyOutFormServiceImpl implements TransactionFormService, WorkflowItemSpecific {

	private static final String POLICY_ID_CANT_BE_NULL = "The policy id can't be null";
	private static final String WORKFLOW_ITEM_NOT_NULL = "Workflow Item ID can not be null.";

	@Autowired
	private LiabilityPolicyService policyService;

	@Autowired
	protected AbstractFundFormService<FundTransactionFormDTO> fundFormService;

	@Autowired
	private WebiaFormDataService formDataService;

	@Autowired
	private LiabilityFundTransactionService liabilityFundTransactionService;

	@Autowired
	private LiabilityTransactionService liabilityTransactionService;

	private final static Integer ACTIVE_STATUS = 1;

	@Override
	public TransactionFormDTO initTransactionForm(String policyId, Integer workflowItemId) {

		Assert.notNull(policyId, POLICY_ID_CANT_BE_NULL);
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_NOT_NULL);

		TransactionFormDTO transactionForm = formDataService.initFormData(workflowItemId, getSupportedWorkflowType().getValue(), TransactionFormDTO.class);
		
		PolicyLightDTO policy = policyService.getPolicyLight(policyId);
		
		transactionForm.setCurrency(policy.getCurrency());
		transactionForm.setAmountType(AmountType.NETT);
		transactionForm.setSpecificAmountByFund(Boolean.FALSE);
		transactionForm.setPolicyId(policyId);
		transactionForm.setFirstCpsUser(SecurityContextHelper.getPreferredUsername()); // The first cps user is the user connected
		transactionForm.setFundTransactionForms(fundFormService.getFundForms(policyId));
		transactionForm.setBrokerTransactionFees(BigDecimal.ZERO);
		transactionForm.setTransactionFees(BigDecimal.ZERO);

		return transactionForm;
	}

	@Override
	public SurrenderTransactionDetailsDTO getSurrenderDetails(Integer workflowItemId) {
		Assert.notNull(workflowItemId, "The workflow item id can't be null");

		PolicyLightDTO policy = policyService.getPolicyLight(workflowItemId);

		Collection<PolicyTransactionsHistoryDTO> transactions = liabilityFundTransactionService.getPolicyTransactionsHistory(policy.getPolId());
		
		// There is only one active surrender transaction per policy.
		PolicyTransactionsHistoryDTO surrenderTransaction = transactions.stream()
				.filter(transaction -> EventType.SURRENDER.getEvtId().equals(transaction.getEventType()) && ACTIVE_STATUS.equals(transaction.getStatusCode()))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("No surrender transaction found for workflow " + workflowItemId));

		PolicyTransactionsHistoryDetailsDTO transactionDetails = liabilityTransactionService.getTransactionDetails(policy.getPolId(), surrenderTransaction);

		SurrenderTransactionDetailsDTO result = new SurrenderTransactionDetailsDTO();
		result.setDetails(transactionDetails);
		result.setTransaction(surrenderTransaction);

		return result;
	}
}
