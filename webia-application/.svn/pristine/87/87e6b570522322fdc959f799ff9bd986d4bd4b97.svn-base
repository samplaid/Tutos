package lu.wealins.webia.core.service.impl;

import java.util.Collection;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.SurrenderReportResultDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxSendingDTO;
import lu.wealins.webia.core.service.WebiaTransactionTaxService;
import lu.wealins.webia.core.utils.RestClientUtils;


@Service
public class WebiaTransactionTaxServiceImpl implements WebiaTransactionTaxService {

	private static final String TRANSACTION_TAX_ID_CANNOT_BE_NULL = "The Transaction tax id cannot be null.";
	private static final String TRANSACTION_TAX_POLICY_ID_CANNOT_BE_NULL = "The Transaction tax policy id cannot be null.";

	private static final String WEBIA_GET_TRANSACTION_TAX = "webia/transactiontax/";
	private static final String GET = "get";
	private static final String GET_TRANSACTION_TAX_DETAILS = "transactionTaxDetails";
	private static final String TRANSACTION_BY_POLICY = "transactionTaxByPolicy";
	private static final String TRANSACTION_BY_ORIGIN_ID = "originId";
	private static final String TRANSACTION_TAX_REPORT_DETAILS = "resultDetails";
	private static final String WEBIA_GET_TRANSACTION_TAX_SENDING = "TransactionTaxSending";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public TransactionTaxDTO getTransactionTax(Long transactionTaxId) {
		Assert.notNull(transactionTaxId, TRANSACTION_TAX_ID_CANNOT_BE_NULL);
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("id", transactionTaxId);
		return restClientUtils.get(WEBIA_GET_TRANSACTION_TAX, GET, params, TransactionTaxDTO.class);
	}

	@Override
	public Collection<TransactionTaxDetailsDTO> getTransactionTaxDetails(Long transactionTaxId) {
		Assert.notNull(transactionTaxId, TRANSACTION_TAX_ID_CANNOT_BE_NULL);
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("transactionTaxId", transactionTaxId);

		return restClientUtils.get(WEBIA_GET_TRANSACTION_TAX, GET_TRANSACTION_TAX_DETAILS, params, new GenericType<Collection<TransactionTaxDetailsDTO>>() {
		});

	}

	@Override
	public Collection<TransactionTaxDTO> getTransactionTaxByPolicy(String policyId) {
		Assert.notNull(policyId, TRANSACTION_TAX_POLICY_ID_CANNOT_BE_NULL);
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("policyId", policyId);

		return restClientUtils.get(WEBIA_GET_TRANSACTION_TAX, TRANSACTION_BY_POLICY, params,
				new GenericType<Collection<TransactionTaxDTO>>() {
				});

}

	@Override
	public TransactionTaxDTO getTransactionTaxByOriginId(Long originId) {
		Assert.notNull(originId, TRANSACTION_TAX_POLICY_ID_CANNOT_BE_NULL);
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("originId", originId);

		return restClientUtils.get(WEBIA_GET_TRANSACTION_TAX, TRANSACTION_BY_ORIGIN_ID, params,
				TransactionTaxDTO.class);

	}
	

	@Override
	public SurrenderReportResultDTO getTransactionTaxReportResult(Long transactionTaxId) {
		Assert.notNull(transactionTaxId, TRANSACTION_TAX_ID_CANNOT_BE_NULL);
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("transactionId", transactionTaxId);

		return restClientUtils.get(WEBIA_GET_TRANSACTION_TAX, TRANSACTION_TAX_REPORT_DETAILS, params,
				SurrenderReportResultDTO.class);

	}

	@Override
	public TransactionTaxSendingDTO getTransactionTaxSending(Long transactionTaxId) {

		Assert.notNull(transactionTaxId, TRANSACTION_TAX_ID_CANNOT_BE_NULL);
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("transactionTaxId", transactionTaxId);

		return restClientUtils.get(WEBIA_GET_TRANSACTION_TAX, WEBIA_GET_TRANSACTION_TAX_SENDING, params,
				TransactionTaxSendingDTO.class);
	}

}
