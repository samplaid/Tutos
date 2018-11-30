package lu.wealins.webia.core.service.impl;

import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.TransactionType;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;

@Service(value = "SurrenderFormService")
public class SurrenderFormServiceImpl extends MoneyOutFormServiceImpl {

	@Override
	public TransactionFormDTO initTransactionForm(String policyId, Integer workflowItemId) {
		TransactionFormDTO transactionForm = super.initTransactionForm(policyId, workflowItemId);
		transactionForm.setTransactionType(TransactionType.RAT);
		return transactionForm;
	}

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.SURRENDER;
	}
}
