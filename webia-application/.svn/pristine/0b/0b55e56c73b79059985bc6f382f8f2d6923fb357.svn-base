package lu.wealins.webia.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.webia.services.CreateEditingRequest;
import lu.wealins.common.dto.webia.services.TaxBatchResponse;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsRequest;
import lu.wealins.webia.core.service.DaliService;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.service.TaxBatchService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.PolicyOrigin;

@Service
public class TaxBatchServiceImpl implements TaxBatchService {

	private static final String WEBIA_TAX_TRANSACTIONS_DETAILS_CREATION = "webia/transactionTax/details";
	private static final String WEBIA_TRANSACTIONS_TAX_DETAILS_CREATION = "webia/transactionTax/createDetails";

	private static final Logger LOGGER = LoggerFactory.getLogger(TaxBatchServiceImpl.class);

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private EditingService editingService;

	@Autowired
	DaliService daliService;

	@Override
	@Transactional
	public void createTransactionTaxDetails() {
		TaxBatchResponse response = restClientUtils.post(WEBIA_TAX_TRANSACTIONS_DETAILS_CREATION, null, TaxBatchResponse.class);
		for (Long transactionsTaxId : response.getTransactionTaxIds()) {

			CreateEditingRequest request = new CreateEditingRequest();
			request.setPolicyOrigin(PolicyOrigin.DALI.name());
			request.setDocumentType(DocumentType.ANNEX_FISC);
			request.setTransactionTax(transactionsTaxId);
			String eventProduct = daliService.initEventPlusValueBeforeAndAfter(transactionsTaxId);
			request.setProduct(eventProduct);
			editingService.generateSurrenderDoc(request);
			LOGGER.info("{} edition requests for event with id ", transactionsTaxId);
		}
	}

	@Override
	public void createTransactionTaxDetails(TransactionTaxDetailsRequest request) {
		restClientUtils.post(WEBIA_TRANSACTIONS_TAX_DETAILS_CREATION, request, TaxBatchResponse.class);
	}

}
