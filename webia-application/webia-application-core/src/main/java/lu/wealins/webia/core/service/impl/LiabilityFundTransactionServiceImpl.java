package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.FrenchTaxPolicyTransactionDTO;
import lu.wealins.common.dto.liability.services.FundPriceDTO;
import lu.wealins.common.dto.liability.services.FundTransactionDTO;
import lu.wealins.common.dto.liability.services.FundTransactionsValidationRequest;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDTO;
import lu.wealins.common.dto.liability.services.TransactionDTO;
import lu.wealins.common.dto.liability.services.enums.FundTransactionStatus;
import lu.wealins.common.dto.liability.services.enums.TransactionCode;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.FundFormDTO;
import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.security.token.SecurityContextThreadLocal;
import lu.wealins.webia.core.service.LiabilityCountryService;
import lu.wealins.webia.core.service.LiabilityFundPriceService;
import lu.wealins.webia.core.service.LiabilityFundTransactionService;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.service.helper.FrenchTaxHelper;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.FundTransactionRequest;
import lu.wealins.webia.ws.rest.request.FundTransactionResponse;
import lu.wealins.webia.ws.rest.request.FundTransactionsValuationRequest;

@Service
public class LiabilityFundTransactionServiceImpl implements LiabilityFundTransactionService {


	private static final String FUND_TRANSACTIONS = "fundTransactions";
	private static final String FUND_TRANSACTION_VALUATION = "fundTransactionValuation";
	private static final String LIABILITY_TRANSACTION = "liability/transaction/";
	private static final String MORTALITY_CHARGE = "policyMortalityCharge";
	private static final String TRANSACTIONS_HISTORY = "policyTransactionsHistory";
	private static final String FRENCH_TAX_POLICY_TRANSACTIONS = "policyTransactionsForFrenchTax";
	private static final String LIABILITY_FUND_TRANSACTION = "liability/fundTransaction/";
	private static final String LIABILITY_FUND_TRANSACTION_VALIDATE_POSTED = "validatePosted";
	private static final String LIABILITY_FUND_TRANSACTION_VALIDATE_POSTED_FOR_WITHDRAWAL = "validatePostedForWithdrawal";
	private static final String LIABILITY_FUND_TRANSACTION_VALIDATE_POSTED_FOR_SURRENDER = "validatePostedForSurrender";
	private static final String LIABILITY_FUND_TRANSACTION_VALIDATE_AWAITING_STATUS = "validateAwaitingStatus";
	private static final String FUND_TRANSACTION_SUBCRIPTION_ADDITION = "fundtransactionforsubscriptionoraddition";
	private static final String FUND_TRANSACTION_AMOUNT = "fundTransactionAmount";
	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private LiabilityFundPriceService fundPriceService;

	@Autowired
	private LiabilityWorkflowService workflowService;

	@Autowired
	private WebiaApplicationParameterService appliParamService;

	@Autowired
	LiabilityCountryService countryService;

	@Autowired
	FrenchTaxHelper frenchTaxHelper;

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	private static final String APPLICATION_JSON = "application/json";
	private static final String VALIDATE = "validate";
	private static final String FUND_TRANSACTION_EVENT_TYPES = "FUND_TRANSACTION_EVENT_TYPES";
	private static final String FUND_TRANSACTION_EVENT_TYPES_MONEY_OUT = "FUND_TRANSACTION_EVENT_TYPES_MONEY_OUT";
	private static final String FUND_TRANSACTION_STATUS_VALORIZED = "FUND_TRANSACTION_STATUS_VALORIZED";
	private static final String FUND_TRANSACTION_STATUS_UNVALORIZED = "FUND_TRANSACTION_STATUS_UNVALORIZED";

	private final Logger log = LoggerFactory.getLogger(LiabilityFundTransactionServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityFundService#getFund(java.lang.String)
	 */
	@Override
	public FundTransactionResponse getSubscriptionOrAdditionFundTransactions(FundTransactionRequest request) {
		return restClientUtils.post(LIABILITY_TRANSACTION + FUND_TRANSACTION_SUBCRIPTION_ADDITION, request, FundTransactionResponse.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityFundTransactionService#executeFundTransactionValuation(lu.wealins.webia.ws.rest.dto.AppFormDTO)
	 */
	@Override
	public void executeFundTransactionValuation(AppFormDTO appForm, String usrId) {
		for (FundFormDTO fund : appForm.getFundForms()) {
			String fundId = fund.getFundId();
			Date paymentDt = appForm.getPaymentDt();
			Collection<FundPriceDTO> fundPrices = fundPriceService.getFundPrices(fundId, paymentDt);
			if (CollectionUtils.isNotEmpty(fundPrices)) {
				runFundTransaction(appForm.getWorkflowItemId(), fundId, paymentDt, usrId);
			}
		}

	}

	private void runFundTransaction(Integer workflowItemId, String fundId, Date paymentDt, String usrId) {

		ResteasyClient client = new ResteasyClientBuilder().build();
		String uri = piaRootContextURL + LIABILITY_FUND_TRANSACTION + FUND_TRANSACTION_VALUATION;

		client.target(uri).request()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + SecurityContextThreadLocal.get().getKeycloakSecurityContext().getTokenString())
				.async().post(Entity.entity(createFundTransactionsValuationRequest(fundId, paymentDt), APPLICATION_JSON), new InvocationCallback<String>() {

					@Override
					public void completed(String response) {
						workflowService.next(workflowItemId, StepTypeDTO.AWAITING_VALUATION.getvalue(), usrId);
					}

					@Override
					public void failed(Throwable throwable) {
						log.error("Cannot run the fund transactions valuation webservice", throwable);
					}
				});

	}

	private FundTransactionsValuationRequest createFundTransactionsValuationRequest(String fundId, Date date) {
		FundTransactionsValuationRequest request = new FundTransactionsValuationRequest();

		request.setDate(date);
		request.setFundId(fundId);

		return request;
	}

	@Override
	public Collection<FundTransactionDTO> getFundTransactions(String policyId, String fundId, TransactionCode eventType, FundTransactionStatus status) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();

		params.add("policyId", policyId);
		params.add("fundId", fundId);
		params.add("eventType", eventType);
		params.add("status", status);

		return restClientUtils.get(LIABILITY_FUND_TRANSACTION, FUND_TRANSACTIONS, params, new GenericType<Collection<FundTransactionDTO>>() {
			// nothing to do.
		});
	}

	@Override
	public Collection<BigDecimal> getPolicyMortalityCharge(String policyId, Date paymentDate,
			Integer eventTypes) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();

		params.add("policyId", policyId);
		params.add("paymentDate", new SimpleDateFormat("yyyy-MM-dd").format(paymentDate));
		params.add("eventTypes", eventTypes);

		return restClientUtils.get(LIABILITY_TRANSACTION, MORTALITY_CHARGE, params,
				new GenericType<Collection<BigDecimal>>() {
				});

	}

	

	@Override
	public Collection<String> validateDates(String policyId, Date effectiveDate) {
		FundTransactionsValidationRequest request = createBasicValidationRequest(policyId, effectiveDate);
		
		request.setEventTypes(appliParamService.getIntegerValues(FUND_TRANSACTION_EVENT_TYPES));

		return restClientUtils.post(LIABILITY_FUND_TRANSACTION + VALIDATE, request, new GenericType<Collection<String>>() {
		});
	}
	
	@Override
	public Collection<String> validateDatesMoneyOut(String policyId, Date effectiveDate) {
		FundTransactionsValidationRequest request = createBasicValidationRequest(policyId, effectiveDate);

		request.setEventTypes(appliParamService.getIntegerValues(FUND_TRANSACTION_EVENT_TYPES_MONEY_OUT));

		return restClientUtils.post(LIABILITY_FUND_TRANSACTION + VALIDATE, request, new GenericType<Collection<String>>() {
		});
	}

	private FundTransactionsValidationRequest createBasicValidationRequest(String policyId, Date effectiveDate) {
		FundTransactionsValidationRequest request = new FundTransactionsValidationRequest();
		request.setEffectiveDate(effectiveDate);
		request.setPolicyId(policyId);
		request.setValorizedStatus(appliParamService.getIntegerValues(FUND_TRANSACTION_STATUS_VALORIZED));
		request.setUnValorizedStatus(appliParamService.getIntegerValues(FUND_TRANSACTION_STATUS_UNVALORIZED));
		return request;
	}

	@Override
	public Collection<PolicyTransactionsHistoryDTO> getPolicyTransactionsHistory(String policyId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();

		params.add("id", policyId);

		List<PolicyTransactionsHistoryDTO> transactions = (List<PolicyTransactionsHistoryDTO>) restClientUtils.get(LIABILITY_TRANSACTION,
				TRANSACTIONS_HISTORY, params,
				new GenericType<Collection<PolicyTransactionsHistoryDTO>>() {
					// nothing to do.
				});
		
		if (transactions == null || transactions.isEmpty()) {
			 return transactions;
		}
		Optional<String> isFrenchTaxable = frenchTaxHelper.isPolicyFrenchTaxable(policyId);

		List<PolicyTransactionsHistoryDTO> transactionsHistory = transactions.stream().map((transaction -> {
			PolicyTransactionsHistoryDTO policyTransaction = new PolicyTransactionsHistoryDTO();
			policyTransaction.setCoverage(transaction.getCoverage());
			policyTransaction.setCurrency(transaction.getCurrency());
			policyTransaction.setEffectiveDate(transaction.getEffectiveDate());
			policyTransaction.setEventName(transaction.getEventName());
			policyTransaction.setEventType(transaction.getEventType());
			policyTransaction.setFeeAmount(transaction.getFeeAmount());
			policyTransaction.setFrenchTaxable(isFrenchTaxable.isPresent());
			policyTransaction.setEventCanBeReported(frenchTaxHelper.isEventTypeCanBeReported(transaction.getEventType()));
			policyTransaction.setEventDateEligible(frenchTaxHelper.isDateAfterFrenchTaxBeginningDate(transaction.getEffectiveDate()));
			policyTransaction.setGrossAmount(transaction.getGrossAmount());
			policyTransaction.setLastTrnId(transaction.getLastTrnId());
			policyTransaction.setNetAmount(transaction.getNetAmount());
			policyTransaction.setPolicyClientCountry(isFrenchTaxable.orElse(StringUtils.EMPTY));
			policyTransaction.setStatus(transaction.getStatus());
			policyTransaction.setStatusCode(transaction.getStatusCode());
			policyTransaction.setTaxAmount(transaction.getTaxAmount());
			return policyTransaction;
		})).collect(Collectors.toList());

		return transactionsHistory;

	}

	@Override
	public Collection<String> validateStatus(String policyId, Integer coverage) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();

		params.add("policyId", policyId);
		params.add("coverage", coverage.toString());

		return restClientUtils.get(LIABILITY_FUND_TRANSACTION, LIABILITY_FUND_TRANSACTION_VALIDATE_POSTED, params,
				new GenericType<Collection<String>>() {
				});
	}

	@Override
	public Collection<String> validatePostedForWithdrawal(String policyId, Date effectiveDate) {
		MultivaluedMap<String, Object> params = getPolicyDateParams(policyId, effectiveDate);

		return restClientUtils.get(LIABILITY_FUND_TRANSACTION, LIABILITY_FUND_TRANSACTION_VALIDATE_POSTED_FOR_WITHDRAWAL, params,
				new GenericType<Collection<String>>() {
				});
	}

	@Override
	public Collection<String> validatePostedForSurrender(String policyId, Date effectiveDate) {
		MultivaluedMap<String, Object> params = getPolicyDateParams(policyId, effectiveDate);

		return restClientUtils.get(LIABILITY_FUND_TRANSACTION, LIABILITY_FUND_TRANSACTION_VALIDATE_POSTED_FOR_SURRENDER, params,
				new GenericType<Collection<String>>() {
				});
	}

	@Override
	public Collection<FundTransactionDTO> filterNonPremiumFundTransactions(TransactionDTO transaction) {
		return transaction.getFundTransactions()
				.stream()
				.filter(x -> x.getEventType() == TransactionCode.PRE_ALLOC.getCode())
				.collect(Collectors.toList());
	}


	@Override
	public Collection<FrenchTaxPolicyTransactionDTO> getPolicyTransactionsForFrenchTax(String policyId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();

		params.add("policyId", policyId);

		return restClientUtils.get(LIABILITY_TRANSACTION, FRENCH_TAX_POLICY_TRANSACTIONS, params,
				new GenericType<Collection<FrenchTaxPolicyTransactionDTO>>() {
				});
	}



	@Override
	public BigDecimal getFundTransactionAmount(String policyId, String TransactionTaxEventType, String effectiveDate) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("policyId", policyId);
		params.add("TransactionTaxEventType", TransactionTaxEventType);
		params.add("effectiveDate", effectiveDate);
		return restClientUtils.get(LIABILITY_FUND_TRANSACTION, FUND_TRANSACTION_AMOUNT, params,
				BigDecimal.class);
	}

	@Override
	public void executeFundTransactionValuation(TransactionFormDTO appForm, String usrId) {
		for (FundTransactionFormDTO fund : appForm.getFundTransactionForms()) {
			String fundId = fund.getFundId();
			Date paymentDt = appForm.getEffectiveDate();
			Collection<FundPriceDTO> fundPrices = fundPriceService.getFundPrices(fundId, paymentDt);
			if (CollectionUtils.isNotEmpty(fundPrices)) {
				runTransactionFundTransaction(appForm.getWorkflowItemId(), fundId, paymentDt, usrId);
			}
		}

	}

	private void runTransactionFundTransaction(Integer workflowItemId, String fundId, Date paymentDt, String usrId) {

		ResteasyClient client = new ResteasyClientBuilder().build();
		String uri = piaRootContextURL + LIABILITY_FUND_TRANSACTION + FUND_TRANSACTION_VALUATION;

		client.target(uri).request()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + SecurityContextThreadLocal.get().getKeycloakSecurityContext().getTokenString())
				.async().post(Entity.entity(createFundTransactionsValuationRequest(fundId, paymentDt), APPLICATION_JSON), new InvocationCallback<String>() {

					@Override
					public void completed(String response) {
						// workflowService.next(workflowItemId, StepTypeDTO.AWAITING_VALUATION.getvalue(), usrId);
					}

					@Override
					public void failed(Throwable throwable) {
						log.error("Cannot run the fund transactions valuation webservice", throwable);
					}
				});

	}

	@Override
	public Collection<String> validateAwaitingStatus(String policyId, Date effectiveDate) {
		MultivaluedMap<String, Object> params = getPolicyDateParams(policyId, effectiveDate);

		return restClientUtils.get(LIABILITY_FUND_TRANSACTION, LIABILITY_FUND_TRANSACTION_VALIDATE_AWAITING_STATUS, params,
				new GenericType<Collection<String>>() {
				});
	}

	private MultivaluedMap<String, Object> getPolicyDateParams(String policyId, Date effectiveDate) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();

		params.add("policyId", policyId);
		params.add("effectiveDate", new SimpleDateFormat("yyyy-MM-dd").format(effectiveDate));
		return params;
	}

}

