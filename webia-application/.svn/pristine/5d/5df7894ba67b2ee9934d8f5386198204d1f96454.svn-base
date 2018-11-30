package lu.wealins.webia.core.service.impl;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.webia.core.service.WebiaTransactionFormService;
import lu.wealins.webia.core.utils.RestClientUtils;


@Service
public class WebiaTransactionFormServiceImpl implements WebiaTransactionFormService {

	private static final String WORKFLOW_ITEM_ID_CANT_BE_NULL = "the workflow item id can't be null";
	private static final String WEBIA_TRANSACTION_FORM = "webia/transactionForm";
	private static final String WEBIA_TRANSACTION_FORM_GET_FORM_DATA = "/getFormData";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public TransactionFormDTO getFormData(Integer workflowItemId) {
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANT_BE_NULL);

		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.putSingle("workflowItemId", workflowItemId);

		return restClientUtils.get(WEBIA_TRANSACTION_FORM, WEBIA_TRANSACTION_FORM_GET_FORM_DATA, queryParams, TransactionFormDTO.class);
	}

	
}
