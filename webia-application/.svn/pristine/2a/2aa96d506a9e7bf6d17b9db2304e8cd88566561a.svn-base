package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.webia.core.service.DaliService;
import lu.wealins.webia.core.service.WebiaTransactionTaxService;
import lu.wealins.webia.core.service.helper.FrenchTaxHelper;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.PolicyEventUpdateServiceRequest;

@Service
public class DaliServiceImpl implements DaliService {

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	FrenchTaxHelper frenchTaxHelper;

	@Autowired
	private WebiaTransactionTaxService transactionTaxService;

	private static final String DALI_POLICY_EVENT_UPDATE = "daliPolicyEventUpdatePlusValues";

	@Override
	public String initEventPlusValueBeforeAndAfter(PolicyEventUpdateServiceRequest eventUpdate) {
		return restClientUtils.post(DALI_POLICY_EVENT_UPDATE, eventUpdate, String.class);
	}

	@Override
	public String initEventPlusValueBeforeAndAfter(Long transactionTaxId) {
		TransactionTaxDTO transaction = transactionTaxService.getTransactionTax(transactionTaxId);
		PolicyEventUpdateServiceRequest eventUpdate = new PolicyEventUpdateServiceRequest();
		Pair<BigDecimal, BigDecimal> plusValue = frenchTaxHelper.processPlusValue(transactionTaxId);

		if (transaction == null || transaction.getOriginId() == null) {
			return StringUtils.EMPTY;
		}

		eventUpdate.setEventId(transaction.getOriginId().longValue());
		eventUpdate.setPlusValueBefore(plusValue.getLeft());
		eventUpdate.setPlusValueAfter(plusValue.getRight());
		return initEventPlusValueBeforeAndAfter(eventUpdate);
	}

}
