package lu.wealins.liability.services.ws.rest.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.FundTransactionDTO;
import lu.wealins.common.dto.liability.services.FundTransactionsValidationRequest;
import lu.wealins.common.dto.liability.services.FundTransactionsValuationRequest;
import lu.wealins.common.dto.liability.services.enums.FundTransactionStatus;
import lu.wealins.common.dto.liability.services.enums.TransactionCode;
import lu.wealins.liability.services.core.business.FundTransactionService;
import lu.wealins.liability.services.ws.rest.FundTransactionRESTService;

@Component
public class FundTransactionRESTServiceImpl implements FundTransactionRESTService {

	@Autowired
	private FundTransactionService fundTransactionService;

	private static final Logger logger = LoggerFactory.getLogger(FundTransactionRESTServiceImpl.class);

	@Override
	public Response fundTransactionValuation(SecurityContext context, FundTransactionsValuationRequest fundTransactionsValuationRequest) {
		return fundTransactionService.fundTransactionsValuation(fundTransactionsValuationRequest);
	}

	@Override
	public Response executePOST_FPC(SecurityContext context) {
		return fundTransactionService.executePOST_FPC();
	}

	@Override
	public Response executePRE_FPC(SecurityContext context) {
		return fundTransactionService.executePRE_FPC();
	}

	@Override
	public Collection<FundTransactionDTO> getFundTransactions(SecurityContext context, String policyId, String fundId, TransactionCode eventType, FundTransactionStatus status) {
		return fundTransactionService.getFundTransactions(policyId, fundId, eventType, status);
	}

	@Override
	public Collection<String> validate(FundTransactionsValidationRequest request) {
		return fundTransactionService.validateFundTransactions(request);
	}

	@Override
	public BigDecimal getFundTransactionAmount(SecurityContext context, String policyId, String TransactionTaxEventType,
			String effectiveDate) {
		return fundTransactionService.getFundTransactionOrder(policyId, TransactionTaxEventType, effectiveDate);

	}

	@Override
	public Collection<String> validatePosted(String policyId, Integer coverage) {
		return fundTransactionService.validatePosted(policyId, coverage);
	}

	@Override
	public Collection<String> validatePostedForWithdrawal(String policyId, String effectiveDateString) {
		return fundTransactionService.validatePostedForWithdrawal(policyId, parseDate(effectiveDateString));
	}

	@Override
	public Collection<String> validatePostedForSurrender(String policyId, String effectiveDateString) {
		return fundTransactionService.validatePostedForSurrender(policyId, parseDate(effectiveDateString));
	}

	@Override
	public Collection<String> validateAwaitingFundTransactions(String policyId, String effectiveDate) {
		return fundTransactionService.validateAwaitingFundTransactions(policyId, parseDate(effectiveDate));
	}

	private Date parseDate(String effectiveDateString) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date effectiveDate = null;
		try {
			effectiveDate = format.parse(effectiveDateString);
		} catch (ParseException parseException) {
			logger.error("Error parsing of effective Date external  {} ", parseException);
		}
		return effectiveDate;
	}
}

