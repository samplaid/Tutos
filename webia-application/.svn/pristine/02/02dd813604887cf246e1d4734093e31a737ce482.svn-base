package lu.wealins.webia.core.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDetailsDTO;
import lu.wealins.common.dto.liability.services.TransactionDTO;

public interface LiabilityTransactionService {

	TransactionDTO getTransactionByPolicyAndCoverage(String policy, Integer coverage, List<Integer> eventTypes);

	Collection<TransactionDTO> getActiveTransactionByPolicyAndEventType(String policy, Integer eventType);

	Collection<TransactionDTO> getAwaitingAdministrationFees(String policyId, Date effectiveDate);

	Collection<TransactionDTO> getActiveTransactionByPolicyAndEventTypeAndDate(String policy, Integer eventType, Date date);

	Collection<TransactionDTO> getActiveLinkedTransactions(Long transactionId);

	PolicyTransactionsHistoryDetailsDTO getTransactionDetails(String policyId, PolicyTransactionsHistoryDTO transaction);
}
