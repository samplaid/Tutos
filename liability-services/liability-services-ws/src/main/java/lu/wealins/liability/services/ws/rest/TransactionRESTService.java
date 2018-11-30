package lu.wealins.liability.services.ws.rest;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.liability.services.AccountTransactionDTO;
import lu.wealins.common.dto.liability.services.FrenchTaxPolicyTransactionDTO;
import lu.wealins.common.dto.liability.services.FundTransactionRequest;
import lu.wealins.common.dto.liability.services.FundTransactionResponse;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDetailsDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDetailsInputDTO;
import lu.wealins.common.dto.liability.services.ReportingComDTO;
import lu.wealins.common.dto.liability.services.ResponsePostingSetIdsDTO;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.common.dto.liability.services.TransactionDTO;
import lu.wealins.common.dto.liability.services.TransactionGroupedByFundDTO;
import lu.wealins.common.dto.liability.services.TransactionsForMathematicalReserveForPolicyRequest;

/**
 * TransactionRESTService is a REST service responsible to manipulate Transactions.
 *
 */
@Path("transaction")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface TransactionRESTService {

	/**
	 * fetch a transaction
	 * 
	 * @param context The security context.
	 * @return transactionDTO
	 */
	@GET
	@Path("id")
	TransactionDTO getTransactionById(@Context SecurityContext context, @QueryParam("transactionId") long transactionId);

	@GET
	@Path("policyCoverage")
	TransactionDTO getTransaction(@Context SecurityContext context, @QueryParam("policy") String policy, @QueryParam("coverage") Integer coverage, @QueryParam("eventTypes") List<Integer> eventTypes);

	@GET
	@Path("activeBypolicyAndEventType")
	Collection<TransactionDTO> getActiveTransactionByPolicyAndEventType(@Context SecurityContext context, @QueryParam("policy") String policy, @QueryParam("eventType") Integer eventType);

	/**
	 * Get not exported account transactions.
	 * 
	 * @param context The security context.
	 * @return not exported account transactions with closed posting set.
	 */
	@GET
	@Path("noexportedtransactions")
	List<AccountTransactionDTO> getNotExportedTransactions(@Context SecurityContext context);

	/**
	 * Get not exported account transactions.
	 * 
	 * @param context
	 * @param page
	 * @param size
	 * @param lastMaxId
	 * @param currentPst
	 * @return
	 */
	@GET
	@Path("noexportedtransactions/pagination")
	PageResult<AccountTransactionDTO> getNotExportedTransactions(@Context SecurityContext context, @QueryParam("page") int page, @QueryParam("size") int size, @QueryParam("lastId") long lastMaxId,
			@QueryParam("currentPst") long currentPst);

	/**
	 * Get not exported account transaction for commission_to_pay.
	 * 
	 * @param context
	 * @param page
	 * @param size
	 * @param lastId
	 * @param codeBil
	 * @param currentPst
	 * @return
	 */
	@GET
	@Path("noexportedtransactionscommissiontopay/pagination")
	PageResult<AccountTransactionDTO> getNotExportedTransactionsCommissionToPay(@Context SecurityContext context, @QueryParam("page") int page, @QueryParam("size") int size,
			@QueryParam("lastId") Long lastId, @QueryParam("codeBil") String codeBil, @QueryParam("currentPst") Long currentPst);

	/**
	 * Get not exported account transactions for reporting com.
	 * 
	 * @param context
	 * @param page
	 * @param size
	 * @param lastId
	 * @param codeBilApplicationParam
	 * @param currentPst
	 * @return
	 */
	@GET
	@Path("noexportedtransactionsreportingcom/pagination")
	PageResult<AccountTransactionDTO> getNotExportedTransactionsReportingCom(@Context SecurityContext context, @QueryParam("page") int page, @QueryParam("size") int size,
			@QueryParam("lastId") Long lastId, @QueryParam("codeBilApplicationParam") String codeBilApplicationParam, @QueryParam("currentPst") Long currentPst);

	/**
	 * Get not external funds reporting com 132.
	 * 
	 * @param context
	 * @param codeBilApplicationParam
	 * @return
	 */
	@GET
	@Path("get-external-funds-reportingcom-132")
	Collection<ReportingComDTO> getExternalFundsReportingCom132(@Context SecurityContext context, @QueryParam("policy") String policy, @QueryParam("comDate") String comDate);

	/**
	 * Get not external funds reporting com 133.
	 * 
	 * @param context
	 * @param codeBilApplicationParam
	 * @return
	 */
	@GET
	@Path("get-external-funds-reportingcom-133")
	Collection<ReportingComDTO> getExternalFundsReportingCom133(@Context SecurityContext context, @QueryParam("policy") String policy, @QueryParam("comDate") String comDate);

	/**
	 * Count not exported transaction.
	 * 
	 * @param context
	 * @return
	 */
	@GET
	@Path("noexportedtransactionscount")
	int countNotExportedTransactions(@Context SecurityContext context);

	/**
	 * Get not exported available account transactions for sap accounting.
	 * 
	 * @param context The security context.
	 * @return not exported account transactions with closed posting set.
	 */
	@GET
	@Path("postingsetavailable/sapaccounting")
	ResponsePostingSetIdsDTO getPostingSetAvailableSapAccounting(@Context SecurityContext context);

	/**
	 * Get not exported available account transactions for commission to pay.
	 * 
	 * @param context The security context.
	 * @return not exported account transactions with closed posting set.
	 */
	@GET
	@Path("postingsetavailable/commissiontopay")
	List<Long> getPostingSetAvailableCommissionToPay(@Context SecurityContext context);

	/**
	 * Get not exported available account transactions for Reporting Com.
	 * 
	 * @param context
	 * @param codeBilApplicationParam
	 * @return
	 */
	@GET
	@Path("postingsetavailable/reportingcom")
	List<Long> getPostingSetAvailableReportingCom(@Context SecurityContext context, @QueryParam("codeBilApplicationParam") String codeBilApplicationParam);

	/**
	 * Get the fund transactions for a subscription or an addition
	 * 
	 * @param context The security context.
	 * @param policyId
	 * @param eventDate
	 * @return the fund transactions for a subscription or an addition
	 */
	@POST
	@Path("fundtransactionforsubscriptionoraddition")
	FundTransactionResponse getSubcriptionOrAdditionFundTransactions(@Context SecurityContext context, FundTransactionRequest fundTransactionRequest);

	/**
	 * Get transactions for mathematical reserve function of a policy
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	@POST
	@Path("/transactionsForMathematicalReserve")
	SearchResult<TransactionGroupedByFundDTO> getTransactionsForMathematicalReserve(@Context SecurityContext context, TransactionsForMathematicalReserveForPolicyRequest request);

	/**
	 * Get the Transactions from a policy linked to a fund (excluding cash, journal,...) Data are group by date, transaction type, transaction/fund status and currency
	 * 
	 * @param context
	 * @param id a policy identifier (can contains slash)
	 * @return a collection of PolicyTransactionsHistory
	 */
	@GET
	@Path("/policyTransactionsHistory")
	List<PolicyTransactionsHistoryDTO> getPolicyTransactionsHistory(@Context SecurityContext context, @QueryParam("id") String id);

	/**
	 * Get the Transactions Data are group by date, transaction type,
	 * transaction/fund status and currency
	 * 
	 * @param context
	 * @param id
	 *            a policy identifier (can contains slash)
	 * @return a collection of PolicyTransactionsHistory
	 */
	@GET
	@Path("/policyMortalityCharge")
	Collection<BigDecimal> getPolicyMortalityCharge(@Context SecurityContext context,
			@QueryParam("policyId") String policyId, @QueryParam("paymentDate") String paymentDate,
			@QueryParam("eventTypes") Integer eventTypes);


	/**
	 * Get the transactions of policy eligible to French tax.
	 * 
	 * @param context
	 * @param policyId
	 *            a policy identifier (can contains slash)
	 * @return a collection of PolicyTransactionsHistory
	 */
	@GET
	@Path("/policyTransactionsForFrenchTax")
	Collection<FrenchTaxPolicyTransactionDTO> getPolicyTransactionsForFrenchTax(@Context SecurityContext context,
			@QueryParam("policyId") String policyId);

	/**
	 * Get the transactions grouped in one of them by effective Date and eventType.
	 * 
	 * @param context
	 * @param policyId
	 *            the policy Id.
	 * @param effectiveDate
	 *            the effective date of transaction
	 * @param EventType
	 *            the eventType of transactions
	 * @return the transactions Id of grouped transactions
	 */
	@POST
	@Path("/policyTransactionsDetails")
	PolicyTransactionsHistoryDetailsDTO getPolicyTransactionsDetails(PolicyTransactionsHistoryDetailsInputDTO input);

	@GET
	@Path("/awaitingAdministrationFees")
	Collection<TransactionDTO> getAwaitingAdministrationFees(@Context SecurityContext context, @QueryParam("policyId") String policyId, @QueryParam("effectiveDate") String effectiveDate);

	@GET
	@Path("activeBypolicyAndEventTypeAndDate")
	Collection<TransactionDTO> getActiveTransactionByPolicyAndEventTypeAndDate(@Context SecurityContext context, @QueryParam("policy") String policy, @QueryParam("eventType") Integer eventType,
			@QueryParam("date") String date);

	@GET
	@Path("activeLinkedTransanction")
	Collection<TransactionDTO> getActiveLinkedTransactionLinked(@Context SecurityContext context, @QueryParam("transactionId") Long transactionId);

	@GET
	@Path("activeLinkedTransanctions")
	Collection<TransactionDTO> getActiveLinkedTransactionLinked(@Context SecurityContext context, @QueryParam("transactionIds") List<Long> transactionIds);

}
