package lu.wealins.liability.services.ws.rest.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
import lu.wealins.liability.services.core.business.FundTransactionService;
import lu.wealins.liability.services.core.business.TransactionService;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.ws.rest.TransactionRESTService;

@Component
public class TransactionRESTServiceImpl implements TransactionRESTService {

	private static final Logger logger = LoggerFactory.getLogger(TransactionRESTServiceImpl.class);

	@Autowired
	private FundTransactionService fundTransactionService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private CalendarUtils calendarUtils;

	@Override
	public TransactionDTO getTransactionById(SecurityContext context, long transactionId) {
		return transactionService.getTransactionById(transactionId);
	}

	@Override
	public List<AccountTransactionDTO> getNotExportedTransactions(SecurityContext context) {

		return fundTransactionService.getAllNoExportedTransactionsWithClosedPostingSet();
	}

	@Override
	public int countNotExportedTransactions(SecurityContext context) {
		return fundTransactionService.getAllNoExportedTransactionsWithClosedPostingSet().size();
	}

	@Override
	public PageResult<AccountTransactionDTO> getNotExportedTransactions(SecurityContext context, int page, int size, long lastMaxId, long currentPst) {
		PageResult<AccountTransactionDTO> response = null;
		logger.info("GetNotExportedTransactions Pagination :  page=" + page + " size=" + size + " lastId=" + lastMaxId + " currentPst=" + currentPst);
		try {
			response = fundTransactionService.getAllNoExportedTransactionsWithClosedPostingSetForSapAccounting(page, size, lastMaxId, currentPst);
		} catch (Exception e) {
			logger.error("Error getting transactions " + e);
		}
		return response;
	}

	@Override
	public PageResult<AccountTransactionDTO> getNotExportedTransactionsCommissionToPay(SecurityContext context, int page, int size, Long lastId, String codeBil, Long currentPst) {
		logger.info("GetNotExportedTransactionsCommissionToPay Pagination :  page=" + page + " size=" + size + " lastId=" + lastId + " codeBil=" + codeBil + " currentPst=" + currentPst);
		return fundTransactionService.getAllNoExportedSapTransactionsCommissionToPay(page, size, lastId, currentPst, codeBil);
	}

	@Override
	public PageResult<AccountTransactionDTO> getNotExportedTransactionsReportingCom(SecurityContext context, int page, int size, Long lastId, String codeBilApplicationParam, Long currentPst) {
		logger.info("GetNotExportedTransactionsReportingCom Pagination :  page=" + page + " size=" + size + " lastId=" + lastId + " codeBilApplicationParam=" + codeBilApplicationParam + " currentPst="
				+ currentPst);
		return fundTransactionService.getAllNoExportedSapTransactionsReportingCom(page, size, lastId, codeBilApplicationParam, currentPst);
	}

	@Override
	public Collection<ReportingComDTO> getExternalFundsReportingCom132(SecurityContext context, String policy, String comDate) {
		logger.info("Find all extrenal funds reportingCom 132 policy=" + policy + " comDate=" + comDate);
		Date dateParse = new Date();
		try {
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			dateParse = format.parse(comDate);
		} catch (Exception e) {
			logger.error("Error during external fund 132 {} ", e);
		}
		return fundTransactionService.findAllExtrenalFundsReportingCom132(policy, dateParse);
	}

	@Override
	public Collection<ReportingComDTO> getExternalFundsReportingCom133(SecurityContext context, String policy, String comDate) {
		logger.info("Find all extrenal funds reportingCom 133 policy=" + policy + " comDate=" + comDate);
		Date dateParse = new Date();
		try {
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			dateParse = format.parse(comDate);
		} catch (Exception e) {
			logger.error("Error during external fund 133 {} ", e);
		}
		return fundTransactionService.findAllExtrenalFundsReportingCom133(policy, dateParse);
	}

	@Override
	public ResponsePostingSetIdsDTO getPostingSetAvailableSapAccounting(SecurityContext context) {
		logger.info("GetPostingSetAvailableSapAccounting");
		return fundTransactionService.getAllPostingSetDinstictByPostingIdForSapAccounting();
	}

	@Override
	public List<Long> getPostingSetAvailableCommissionToPay(SecurityContext context) {
		logger.info("GetPostingSetAvailableCommissionToPay");
		return fundTransactionService.getAllPostingSetDinstictByPostingIdForCommissionToPay();
	}

	@Override
	public List<Long> getPostingSetAvailableReportingCom(SecurityContext context, String codeBilApplicationParam) {
		logger.info("GetPostingSetAvailableReportingCom");
		return fundTransactionService.getAllPostingSetDinstictByPostingIdForReportingCom(codeBilApplicationParam);
	}

	@Override
	public SearchResult<TransactionGroupedByFundDTO> getTransactionsForMathematicalReserve(SecurityContext context, TransactionsForMathematicalReserveForPolicyRequest request) {
		return fundTransactionService.getTransactionsForMathematicalReserveFunctionOfPolicyAndDate(request);
	}

	@Override
	public FundTransactionResponse getSubcriptionOrAdditionFundTransactions(SecurityContext context, FundTransactionRequest fundTransactionRequest) {
		return fundTransactionService.getSubcriptionOrAdditionFundTransactions(fundTransactionRequest);
	}

	@Override
	public List<PolicyTransactionsHistoryDTO> getPolicyTransactionsHistory(SecurityContext context, String id) {
		logger.info("getPolicyTransactionsHistory for policy " + id);
		return transactionService.getPolicyTransactionsHistory(id);
	}

	@Override
	public Collection<BigDecimal> getPolicyMortalityCharge(SecurityContext context, String policyId, String paymentDate,
			Integer eventTypes) {

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date dateParse = new Date();
		try {
			dateParse = format.parse(paymentDate);
		} catch (ParseException e) {
			logger.error(" erreur lors du parsing de la date de paiement de la transaction {} ", paymentDate);
		}
		return transactionService.getPolicyMortalityCharge(policyId, dateParse, eventTypes);
	}


	@Override
	public TransactionDTO getTransaction(SecurityContext context, String policy, Integer coverage, List<Integer> eventTypes) {
		return transactionService.getTransaction(policy, coverage, eventTypes);
	}

	@Override
	public Collection<FrenchTaxPolicyTransactionDTO> getPolicyTransactionsForFrenchTax(SecurityContext context,
			String policyId) {
		return transactionService.getFrenchTaxTransactionForPolicy(policyId);
	}

	@Override
	public PolicyTransactionsHistoryDetailsDTO getPolicyTransactionsDetails(PolicyTransactionsHistoryDetailsInputDTO input) {
		return transactionService.getPolicyTransactionsDetails(input);
	}

	@Override
	public Collection<TransactionDTO> getAwaitingAdministrationFees(SecurityContext context, String policyId, String effectiveDate) {
		return transactionService.getAwaitingAdministrationFees(policyId, calendarUtils.createDate(effectiveDate));
	}

	@Override
	public Collection<TransactionDTO> getActiveTransactionByPolicyAndEventTypeAndDate(SecurityContext context, String policy, Integer eventType, String date) {
		Date dt = DateUtils.truncate(new Date(), Calendar.DATE);

		if (StringUtils.hasText(date)) {
			dt = calendarUtils.createDate(date);
		}

		return transactionService.getActiveTransactionByPolicyAndEventTypeAndDate(policy, eventType, dt);

	}

	@Override
	public Collection<TransactionDTO> getActiveLinkedTransactionLinked(SecurityContext context, Long transactionId) {
		return transactionService.getActiveLinkedTransactions(transactionId);
	}

	@Override
	public Collection<TransactionDTO> getActiveLinkedTransactionLinked(SecurityContext context, List<Long> transactionIds) {
		return transactionService.getActiveLinkedTransactions(transactionIds);
	}

	@Override
	public Collection<TransactionDTO> getActiveTransactionByPolicyAndEventType(SecurityContext context, String policy, Integer eventType) {
		return transactionService.getActiveTransactionByPolicyAndEventType(policy, eventType);
	}

}
