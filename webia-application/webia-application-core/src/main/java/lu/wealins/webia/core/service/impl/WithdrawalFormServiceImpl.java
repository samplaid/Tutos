package lu.wealins.webia.core.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.TransactionType;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;

@Service(value = "WithdrawalFormService")
public class WithdrawalFormServiceImpl extends MoneyOutFormServiceImpl {

	@Override
	public TransactionFormDTO initTransactionForm(String policyId, Integer workflowItemId) {
		TransactionFormDTO transactionForm = super.initTransactionForm(policyId, workflowItemId);
		transactionForm.setTransactionType(TransactionType.RAP);
		if (CollectionUtils.isNotEmpty(transactionForm.getFundTransactionForms())) {
			transactionForm.getFundTransactionForms().stream().forEach(fundTransactionForm -> fundTransactionForm.setClosure(false));
		}
		return transactionForm;
	}

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.WITHDRAWAL;
	}
}
