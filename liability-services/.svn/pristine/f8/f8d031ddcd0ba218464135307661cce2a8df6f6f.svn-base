package lu.wealins.liability.services.ws.rest.impl;

import java.text.ParseException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.liability.services.core.business.ExtractService;
import lu.wealins.liability.services.ws.rest.ExtractOrderRESTService;
import lu.wealins.common.dto.liability.services.ExtractOrderResponse;
import lu.wealins.common.dto.liability.services.UpdateOrderRequest;
import lu.wealins.common.dto.liability.services.UpdateOrderResponse;
import lu.wealins.common.dto.liability.services.ValorizedTransactionSearchRequest;
import lu.wealins.common.dto.liability.services.ValorizedTransactionSearchResponse;

/**
 * Extract order rest service implementation
 * 
 * Implement any exposed service to extract information about order into Lissia
 * 
 * @see ExtractOrderRESTService
 * @author xqv60
 *
 */
@Component
public class ExtractOrderRESTServiceImpl implements ExtractOrderRESTService {

	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ExtractOrderRESTServiceImpl.class);

	/**
	 * The extract service
	 */
	@Autowired
	private ExtractService extractService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.ExtractOrderRESTService#extractOrder(javax.ws.rs.core.SecurityContext, int, int, java.lang.Long, java.lang.Long)
	 */
	@Override
	public Response extractOrder(SecurityContext context, int page, int size, Long valorizedId, Long estimatedId) {
		logger.debug("Extract request : page = " + page + " and size = " + size);
		ExtractOrderResponse response = extractService.extractOrder(page, size, valorizedId, estimatedId);
		return Response.ok(response).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.ExtractOrderRESTService#updateOrder(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.UpdateOrderRequest)
	 */
	@Override
	public Response updateOrder(SecurityContext context, UpdateOrderRequest updateOrderRequest) {
		UpdateOrderResponse response = extractService.updateOrder(updateOrderRequest);
		return Response.ok(response).build();
	}

	@Override
	public Response getValorizedTransactions(SecurityContext context, ValorizedTransactionSearchRequest request) throws ParseException {
		logger.info("Pagination :  page=" + request.getPageNum() + " size=" + request.getPageSize());
		ValorizedTransactionSearchResponse response = extractService.findValorizedOrdersForFIDNotTransmitted(request.getPageNum(), request.getPageSize());
		return Response.ok(response).build();
	}

}
