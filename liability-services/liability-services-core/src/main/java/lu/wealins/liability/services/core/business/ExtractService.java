package lu.wealins.liability.services.core.business;

import lu.wealins.common.dto.liability.services.ExtractOrderResponse;
import lu.wealins.common.dto.liability.services.UpdateOrderRequest;
import lu.wealins.common.dto.liability.services.UpdateOrderResponse;
import lu.wealins.common.dto.liability.services.ValorizedTransactionSearchResponse;
import lu.wealins.liability.services.ws.rest.exception.WssftrupdExportException;

/**
 * 
 * @author xqv60
 *
 */
public interface ExtractService {

	/**
	 * /** Extraction of order from LISSIA
	 * 
	 * @param size the size of the current page (pagination)
	 * @param page the page number retrieved (pagination)
	 * @param lastId retrieved (used to filter the orders into sql request)
	 * @param valorizedId the last valorized order id found
	 * @param estimatedId the last estimated id found
	 * @return the ExtractOrderResponse
	 */
	ExtractOrderResponse extractOrder(int page, int size, Long valorizedId, Long estimatedId);

	/**
	 * Update flag orders to know if they already are extracted
	 * 
	 * @param updateOrderRequest the update order request
	 * @return the update order response
	 * @throws WssftrupdExportException
	 */
	UpdateOrderResponse updateOrder(UpdateOrderRequest updateOrderRequest) throws WssftrupdExportException;
	
	
	/**
	 * Extract valorized orders for FID injection in SOLIAM
	 * @return
	 */
	ValorizedTransactionSearchResponse findValorizedOrdersForFIDNotTransmitted(int page, int size);

}
