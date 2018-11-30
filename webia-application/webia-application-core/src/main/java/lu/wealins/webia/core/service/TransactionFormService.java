package lu.wealins.webia.core.service;

import lu.wealins.common.dto.webia.services.SurrenderTransactionDetailsDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;

public interface TransactionFormService {

	TransactionFormDTO initTransactionForm(String policyId, Integer workflowItemId);

	SurrenderTransactionDetailsDTO getSurrenderDetails(Integer workflowItemId);
}
