package lu.wealins.webia.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDetailsDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDetailsInputDTO;
import lu.wealins.common.dto.liability.services.TransactionDTO;
import lu.wealins.webia.core.service.LiabilityTransactionService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityTransactionServiceImpl implements LiabilityTransactionService {

	@Autowired
	private RestClientUtils restClientUtils;

	private static final String LIABILITY_TRANSACTION = "liability/transaction/";
	private static final String AWAITING_ADMINISTRATION_FEES = "awaitingAdministrationFees";
	private static final String POLICY_COVERAGE = "policyCoverage";
	private static final String ACTIVE_BY_POLICY_AND_EVENT_TYPE = "activeBypolicyAndEventType";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final String ACTIVE_BY_POLICY_AND_EVENT_TYPE_AND_DATE = "activeBypolicyAndEventTypeAndDate";
	private static final String ACTIVE_LINKED = "activeLinkedTransanction";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");

	@Override
	public TransactionDTO getTransactionByPolicyAndCoverage(String policy, Integer coverage, List<Integer> eventTypes) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.add("policy", policy);
		queryParams.add("coverage", coverage);
		eventTypes.forEach(x -> queryParams.add("eventTypes", x));
		return restClientUtils.get(LIABILITY_TRANSACTION, POLICY_COVERAGE, queryParams, TransactionDTO.class);
	}

	@Override
	public Collection<TransactionDTO> getActiveTransactionByPolicyAndEventType(String policy, Integer eventType) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.add("policy", policy);
		queryParams.add("eventType", eventType);
		return restClientUtils.get(LIABILITY_TRANSACTION, ACTIVE_BY_POLICY_AND_EVENT_TYPE, queryParams, new GenericType<Collection<TransactionDTO>>() {
		});
	}

	@Override
	public Collection<TransactionDTO> getAwaitingAdministrationFees(String policyId, Date effectiveDate) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.add("policyId", policyId);
		queryParams.add("effectiveDate", DATE_FORMAT.format(effectiveDate));

		return restClientUtils.get(LIABILITY_TRANSACTION, AWAITING_ADMINISTRATION_FEES, queryParams, new GenericType<Collection<TransactionDTO>>() {
		});
	}

	@Override
	public Collection<TransactionDTO> getActiveTransactionByPolicyAndEventTypeAndDate(String policy, Integer eventType, Date date) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.add("policy", policy);
		queryParams.add("eventType", eventType);
		queryParams.add("date", sdf.format(date));
		return restClientUtils.get(LIABILITY_TRANSACTION, ACTIVE_BY_POLICY_AND_EVENT_TYPE_AND_DATE, queryParams, new GenericType<Collection<TransactionDTO>>() {
		});
	}

	@Override
	public Collection<TransactionDTO> getActiveLinkedTransactions(Long transactionId) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.add("transactionId", transactionId);
		return restClientUtils.get(LIABILITY_TRANSACTION, ACTIVE_LINKED, queryParams, new GenericType<Collection<TransactionDTO>>() {
		});
	}

	@Override
	public PolicyTransactionsHistoryDetailsDTO getTransactionDetails(String policyId, PolicyTransactionsHistoryDTO transaction) {
		Assert.notNull(policyId);
		Assert.notNull(transaction);

		PolicyTransactionsHistoryDetailsInputDTO input = new PolicyTransactionsHistoryDetailsInputDTO();
		input.setPolicyId(policyId);
		input.setTransaction(transaction);

		return restClientUtils.post(LIABILITY_TRANSACTION + "policyTransactionsDetails", input, PolicyTransactionsHistoryDetailsDTO.class);
	}

}
