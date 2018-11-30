package lu.wealins.liability.services.core.business;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.liability.services.AccountTransactionDTO;
import lu.wealins.common.dto.liability.services.FundTransactionDTO;
import lu.wealins.common.dto.liability.services.FundTransactionRequest;
import lu.wealins.common.dto.liability.services.FundTransactionResponse;
import lu.wealins.common.dto.liability.services.FundTransactionsValidationRequest;
import lu.wealins.common.dto.liability.services.FundTransactionsValuationRequest;
import lu.wealins.common.dto.liability.services.PstPostingSetsDTO;
import lu.wealins.common.dto.liability.services.ReportingComDTO;
import lu.wealins.common.dto.liability.services.ResponsePostingSetIdsDTO;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.common.dto.liability.services.TransactionGroupedByFundDTO;
import lu.wealins.common.dto.liability.services.TransactionsForMathematicalReserveForPolicyRequest;
import lu.wealins.common.dto.liability.services.enums.FundTransactionStatus;
import lu.wealins.common.dto.liability.services.enums.TransactionCode;

public interface FundTransactionService {

	/**
	 * Find the number of non cancelled FUND_TRANSACTIONS of the specified fund.
	 * 
	 * @param fdsId
	 * @return
	 */
	int countTransactions(String fdsId);
	
	/**
	 * Find non exported transactions with closed posting set
	 */
	List<AccountTransactionDTO> getAllNoExportedTransactionsWithClosedPostingSet();
	
	/**
	 * Find non exported transactions with closed posting set
	 * 
	 * @param current page
	 * @param size of the page
	 * @return
	 */
	PageResult<AccountTransactionDTO> getAllNoExportedTransactionsWithClosedPostingSet(int page, int size);
	

	/**
	 * Find non exported transactions with closed posting set
	 * 
	 * @param page
	 * @param size
	 * @param lastMaxId
	 * @param currentPst
	 * @return
	 */
	PageResult<AccountTransactionDTO> getAllNoExportedTransactionsWithClosedPostingSetForSapAccounting(int page, int size, long lastMaxId, long currentPst);

	/**
	 * Find non exported SAP transactions for commission to pay
	 * 
	 * @param current page
	 * @param size of the page
	 * @param lastId 
	 * @param currentPst 
	 * @param codeBil 
	 * @return
	 */
	PageResult<AccountTransactionDTO> getAllNoExportedSapTransactionsCommissionToPay(int page, int size, Long lastId, Long currentPst, String codeBil);

	/**
	 * Find non exported SAP transactions for reporting com
	 *
	 * @param current page
	 * @param size of the page
	 * @param lastId 
	 * @param codeBilApplicationParam
	 * @param currentPst 
	 * 
	 * @return
	 */
	PageResult<AccountTransactionDTO> getAllNoExportedSapTransactionsReportingCom(int page, int size, Long lastId, String codeBilApplicationParam, Long currentPst);
	
	/**
	 * Find non exported external funds 132 for reporting com
	 * 
	 * @param policy
	 * @param comDate 
	 * 
	 * @return
	 */
	Collection<ReportingComDTO> findAllExtrenalFundsReportingCom132(String policy, Date comDate);
	
	/**
	 * Find non exported external funds 133 for reporting com
	 *
	 * @param policy
	 * @param comDate 
	 * 
	 * @return
	 */
	Collection<ReportingComDTO> findAllExtrenalFundsReportingCom133(String policy, Date comDate);

	/**
	 * Update posting_sets status
	 * 
	 * @param pstPostingSetsDtos
	 * @param usrId
	 * @return
	 */
	Long updateTransactionPostingSetStatus(Collection<PstPostingSetsDTO> pstPostingSetsDtos, String usrId);
	
	/**
	 * Find postings_sets available for reporting_com
	 * 
	 * @return List id of posting set available
	 */
	ResponsePostingSetIdsDTO getAllPostingSetDinstictByPostingIdForSapAccounting();
	
	/**
	 * Find postings_sets available for reporting_com
	 * 
	 * @return List id of posting set available
	 */
	List<Long> getAllPostingSetDinstictByPostingIdForCommissionToPay();
	
	/**
	 * Find postings_sets available for reporting_com
	 * 
	 * @param codeBilApplicationParam
	 * @return List id of posting set available
	 */
	List<Long> getAllPostingSetDinstictByPostingIdForReportingCom(String codeBilApplicationParam);
	
	SearchResult<TransactionGroupedByFundDTO> getTransactionsForMathematicalReserveFunctionOfPolicyAndDate(TransactionsForMathematicalReserveForPolicyRequest request);
	

	/**
	 * Return the fund transaction of a subscription or a addition
	 * 
	 * @return the fund transactions of a subscription or a addition
	 */
	FundTransactionResponse getSubcriptionOrAdditionFundTransactions(FundTransactionRequest fundTransactionRequest);

	/**
	 * Execute the fund transaction valuation.
	 * 
	 * @param fundTransactionsValuationRequest
	 * @return
	 */
	Response fundTransactionsValuation(FundTransactionsValuationRequest fundTransactionsValuationRequest);

	/**
	 * Execute the sql POST_FPC (previously it was a stored procedure).
	 * 
	 * @return
	 */
	Response executePOST_FPC();

	/**
	 * Execute the sql PRE_FPC (previously it was a stored procedure).
	 * 
	 * @return
	 */
	Response executePRE_FPC();

	/**
	 * Get fund transactions according the following parameters.
	 * 
	 * @param policyId The policy id.
	 * @param fundId The fund id.
	 * @param eventType The event type.
	 * @param status The status.
	 * @return The fund transactions.
	 */
	Collection<FundTransactionDTO> getFundTransactions(String policyId, String fundId, TransactionCode eventType, FundTransactionStatus status);

	Collection<FundTransactionDTO> getFundTransactions(String policyId, Collection<String> fundIds, TransactionCode eventType, FundTransactionStatus status);

	Collection<String> validateFundTransactions(FundTransactionsValidationRequest request);

	Collection<String> validatePosted(String policyId, Integer coverage);

	Collection<String> validatePostedForWithdrawal(String policyId, Date effectiveDate);

	Collection<String> validatePostedForSurrender(String policyId, Date effectiveDate);

	/***
	 * Get fund transaction order .
	 * 
	 * @param policyId
	 *            the police Id
	 * @param TransactionTaxEventType
	 *            the vent type
	 * @param effectiveDate
	 *            the effective date of transaction
	 * @return
	 */
	BigDecimal getFundTransactionOrder(String policyId, String TransactionTaxEventType, String effectiveDate);

	Collection<String> validateAwaitingFundTransactions(String policyId, Date effectiveDate);

	void postSurrenderAwaitingFundTransactions(String policyId);

}
