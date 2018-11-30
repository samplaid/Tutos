package lu.wealins.webia.core.service.document.trait;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.webia.core.utils.RestClientUtils;

/**
 * Trait that provide common feature such as form retrieving for documents based on {@link TransactionFormDTO}
 *
 */
public interface TransactionFormBasedDocument {

	/** End point to retrieve the transactionForm object from Webia. */
	public static final String WEBIA_TRANSACTION_FORM_DATA = "webia/transactionForm/getFormData";

	/**
	 * Perform a WS call to webia in order to retrieve the {@link TransactionFormDTO} corresponding to the provided workflow item id.
	 * 
	 * @param restClientUtils the rest client utils
	 * @param workflowItemId the workflow item id.
	 * @return the {@link TransactionFormDTO} corresponding to the provided workflow item id.
	 */
	public default TransactionFormDTO retrieveTransactionForm(RestClientUtils restClientUtils, Long workflowItemId) {

		// Retrieve the transaction form
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.putSingle("workflowItemId", new Integer(workflowItemId.intValue()));

		return restClientUtils.get(WEBIA_TRANSACTION_FORM_DATA, "", queryParams, TransactionFormDTO.class);
	}
}
