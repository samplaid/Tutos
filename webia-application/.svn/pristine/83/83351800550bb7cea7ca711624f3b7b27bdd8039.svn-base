package lu.wealins.webia.core.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import lu.wealins.common.dto.liability.services.FrenchTaxPolicyTransactionDTO;
import lu.wealins.common.dto.liability.services.FundTransactionDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDTO;
import lu.wealins.common.dto.liability.services.TransactionDTO;
import lu.wealins.common.dto.liability.services.enums.FundTransactionStatus;
import lu.wealins.common.dto.liability.services.enums.TransactionCode;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.webia.ws.rest.request.FundTransactionRequest;
import lu.wealins.webia.ws.rest.request.FundTransactionResponse;

public interface LiabilityFundTransactionService {

	/**
	 * Get the fund transactions of a subscription or addition for the given policy.
	 * 
	 * @param policyId The policy id.
	 * @param eventDate the date of the subscription or the addition
	 * @return The fund transactions.
	 */
	FundTransactionResponse getSubscriptionOrAdditionFundTransactions(FundTransactionRequest request);

	void executeFundTransactionValuation(AppFormDTO appForm, String usrId);

	Collection<FundTransactionDTO> getFundTransactions(String policyId, String fundId, TransactionCode eventType, FundTransactionStatus status);
	
	/**
	 * Get Policy Mortality Charge in a specific date.
	 * 
	 * @param policyId
	 *            The policy Id.
	 * @param paymentDate
	 *            the Payment date of charge.
	 * @param eventTypes
	 *            the event type list corresponding to Mortality charge.
	 * @return The charges amounts list.
	 */
	Collection<BigDecimal> getPolicyMortalityCharge(String policyId, Date paymentDate, Integer eventTypes);

	/**
	 * Get Policy transaction History.
	 * 
	 * @param policyId
	 *            The policy Id.
	 * @return The charges amounts list.
	 */
	Collection<PolicyTransactionsHistoryDTO> getPolicyTransactionsHistory(String policyId);


	Collection<String> validateDates(String policyId, Date effectiveDate);

	Collection<String> validateStatus(String policyId, Integer coverage);

	/**
	 * Make sure that there is no pending transactions on the funds (used for Withdrawal)
	 * 
	 * @param policyId the policy id
	 * @param effectiveDate the effective date
	 * @return a list of string containing the validation error messages if any.
	 */
	Collection<String> validatePostedForWithdrawal(String policyId, Date effectiveDate);

	Collection<String> validatePostedForSurrender(String policyId, Date effectiveDate);

	Collection<String> validateAwaitingStatus(String policyId, Date effectiveDate);

	Collection<FundTransactionDTO> filterNonPremiumFundTransactions(TransactionDTO transaction);


	/**
	 * Get Policy eligible for French taxation transaction .
	 * 
	 * @param policyId
	 *            The policy Id.
	 * @return The policy transaction.
	 */

	Collection<FrenchTaxPolicyTransactionDTO> getPolicyTransactionsForFrenchTax(String policyId);


	/**
	 * Get fund list of policy for specific event type and effective Date
	 * 
	 * @param policyId
	 *            the police id
	 * @param TransactionTaxEventType
	 *            the transaction tax event type (WITH,PRIM,MATU,SURR)
	 * @param effectiveDate
	 *            the date of fund transaction
	 * @return
	 */
	BigDecimal getFundTransactionAmount(String policyId, String TransactionTaxEventType, String effectiveDate);

	void executeFundTransactionValuation(TransactionFormDTO appForm, String usrId);

	Collection<String> validateDatesMoneyOut(String policyId, Date effectiveDate);

}