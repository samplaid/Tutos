package lu.wealins.liability.services.core.business;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import lu.wealins.common.dto.liability.services.FrenchTaxPolicyTransactionDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDetailsDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDetailsInputDTO;
import lu.wealins.common.dto.liability.services.TransactionDTO;


public interface TransactionService {

	TransactionDTO getTransactionById(long transactionId);

	TransactionDTO getTransaction(String polId, Integer coverage, List<Integer> eventTypes);

	Collection<TransactionDTO> getTransaction(String polId, BigDecimal value0, String curency, Date effectiveDate, List<Integer> eventTypes);

	/**
	 * Return the list of active {@link TransactionDTO} matching the provided policy id and event type
	 * 
	 * @param policyId the policy id
	 * @param eventTypethe event type
	 * @return a {@link Collection} of {@link TransactionDTO}
	 */
	Collection<TransactionDTO> getActiveTransactionByPolicyAndEventType(String policyId, Integer eventType);

	/**
	 * Return all the Mortality charge transactions gross amounts list for a policy
	 * in a specific date.
	 * 
	 * @param policyId
	 * @param paymentDate
	 *            The effect date of transaction
	 * @param eventTypes
	 *            Event type List corresponding to Mortality charge event.
	 * @return The collection of mortality charge transactions.
	 */
	Collection<BigDecimal> getPolicyMortalityCharge(String policyId, Date paymentDate, Integer eventTypes);

	/**
	 * Get the Transactions from a policy linked to a fund (excluding cash, journal,...) Data are group by date, transaction type, transaction/fund status and currency
	 * 
	 * @param polId a policy identifier
	 * @return a collection of PolicyTransactionsHistory
	 */
	List<PolicyTransactionsHistoryDTO> getPolicyTransactionsHistory(String polId);


	/**
	 * Get transactions for policy eligible for French taxation.
	 * 
	 * @param policyId
	 *            the policy Id
	 * @return all transactions for policy eligible for French taxation.
	 */
	List<FrenchTaxPolicyTransactionDTO> getFrenchTaxTransactionForPolicy(String policyId);


	Collection<TransactionDTO> getAwaitingAdministrationFees(String policyId, Date effectiveDate);

	/**
	 * Return the list of active {@link TransactionDTO} matching the provided policy id and event type
	 * 
	 * @param policyId the policy id
	 * @param eventTypethe event type
	 * @return a {@link Collection} of {@link TransactionDTO}
	 */
	Collection<TransactionDTO> getActiveTransactionByPolicyAndEventTypeAndDate(String policyId, Integer eventType, Date date);

	Collection<TransactionDTO> getActiveLinkedTransactions(Long transactionId);

	Collection<TransactionDTO> getActiveLinkedTransactions(Collection<Long> transactionIds);

	PolicyTransactionsHistoryDetailsDTO getPolicyTransactionsDetails(PolicyTransactionsHistoryDetailsInputDTO input);

}
