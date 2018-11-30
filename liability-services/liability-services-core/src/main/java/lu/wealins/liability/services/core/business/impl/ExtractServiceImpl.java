package lu.wealins.liability.services.core.business.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.tempuri.wssftrupd.Exception_Exception;
import org.tempuri.wssftrupd.WSSFTRUPD;
import org.tempuri.wssftrupd.WssftrupdExport;
import org.tempuri.wssftrupd.WssftrupdImport;
import org.tempuri.wssftrupd.WssftrupdImport.ImpGrpFtr;
import org.tempuri.wssftrupd.WssftrupdImport.ImpGrpFtr.Row;
import org.tempuri.wssftrupd.WssftrupdImport.ImpGrpFtr.Row.ImpItmFtrFundTransactions;
import org.tempuri.wssftrupd.WssftrupdImport.ImpValidationUsers;

import lu.wealins.common.dto.OrderDTO;
import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.liability.services.ExtractOrderResponse;
import lu.wealins.common.dto.liability.services.UpdateOrderRequest;
import lu.wealins.common.dto.liability.services.UpdateOrderResponse;
import lu.wealins.common.dto.liability.services.ValorizedTransactionSearchResponse;
import lu.wealins.common.dto.webia.services.OrderFIDDTO;
import lu.wealins.liability.services.core.business.ExtractService;
import lu.wealins.liability.services.core.mapper.OrderMapper;
import lu.wealins.liability.services.core.persistence.entity.EstimatedOrderNoEntity;
import lu.wealins.liability.services.core.persistence.entity.FundEntity;
import lu.wealins.liability.services.core.persistence.entity.FundTransactionEntity;
import lu.wealins.liability.services.core.persistence.entity.ValorizedOrderForFIDEntity;
import lu.wealins.liability.services.core.persistence.entity.ValorizedOrderNoEntity;
import lu.wealins.liability.services.core.persistence.repository.FundRepository;
import lu.wealins.liability.services.core.persistence.repository.FundTransactionRepository;
import lu.wealins.liability.services.core.utils.constantes.Constantes;
import lu.wealins.liability.services.ws.rest.exception.WssftrupdExportException;

/**
 * ExtractServiceImpl class
 * 
 * @author xqv60
 *
 */
@Service
@Transactional
public class ExtractServiceImpl implements ExtractService {

	/**
	 * Constant MAX SIZE of the list of order to update into webservice lissia
	 */
	private static final int MAX_SIZE = 70;

	/**
	 * Constant CANCELLED_VALORIZED_FLAG
	 */
	private static final short CANCELLED_VALORIZED_FLAG = 2;

	/**
	 * Constant VALORIZED_FLAG
	 */
	private static final short VALORIZED_FLAG = 1;

	/**
	 * /** Constant CANCELLED_ESTIMATED_FLAG
	 */
	private static final short CANCELLED_ESTIMATED_FLAG = 2;
	/**
	 * Constant ESTIMATED_FLAG
	 */
	private static final short ESTIMATED_FLAG = 1;

	/**
	 * Constant ESTIMATED_TYPE
	 */
	private static final String ESTIMATED_TYPE = "ESTIMATED";

	/**
	 * Constant VALORIZED_TYPE
	 */
	private static final String VALORIZED_TYPE = "VALORIZED";

	/**
	 * Constant OUT
	 */
	private static final String OUT = "SORTIE";

	/**
	 * Constant BUY
	 */
	private static final String BUY = "ACHAT";

	/**
	 * Constant SELL
	 */
	private static final String SELL = "VENTE";

	/**
	 * Constant FINISHED
	 */
	private static final String FINISHED = "TERMINEE";

	/**
	 * Constant CANCELLED
	 */
	private static final String CANCELED = "ANNULEE";

	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ExtractServiceImpl.class);

	@Autowired
	private FundTransactionRepository fundTransactionRepository;

	@Autowired
	private FundRepository fundRepository;

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private WSSFTRUPD wssftrupd;

	@Value("${liability.ws.credential.login}")
	private String wsLoginCredential;

	@Value("${liability.ws.credential.password}")
	private String wsPasswordCredential;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ExtractService#extractOrder(int, int, java.lang.Long, java.lang.Long)
	 */
	@Override
	public ExtractOrderResponse extractOrder(int page, int size, Long valorizedId, Long estimatedId)
			throws WssftrupdExportException {
		ExtractOrderResponse extractOrderResponse = new ExtractOrderResponse();
		// Page result from pagination
		PageResult<OrderDTO> valorizedResult = new PageResult<>();
		PageResult<OrderDTO> estimatedResult = new PageResult<>();
		Pageable pageable = new PageRequest(page, size);

		logger.info("Extract order is processing {page = " + page + ", size = " + size + "}");
		// Get valorized orders
		Page<ValorizedOrderNoEntity> valorized = fundTransactionRepository.findValorizedOrder(valorizedId, pageable);
		if (valorized != null && !CollectionUtils.isEmpty(valorized.getContent())) {
			for (ValorizedOrderNoEntity orderNoEntity : valorized.getContent()) {

				// If the valorized order had no estimated status, we create a estimated order associated at the same time
				if (orderNoEntity.getEstimatedFlag() == null || orderNoEntity.getEstimatedFlag() != ESTIMATED_FLAG) {
					OrderDTO additionalEstimatedOrderDto = mapAdditionalEstimatedOrder(orderNoEntity);
					estimatedResult.getContent().add(additionalEstimatedOrderDto);
				}
				OrderDTO orderDto = mapValorizedOrder(orderNoEntity);
				valorizedResult.getContent().add(orderDto);
			}
		}

		// Get cancelled valorized orders
		Page<ValorizedOrderNoEntity> cancelledValorized = fundTransactionRepository.findCancelledValorizedOrder(valorizedId, pageable);
		if (cancelledValorized != null && !CollectionUtils.isEmpty(cancelledValorized.getContent())) {
			for (ValorizedOrderNoEntity orderNoEntity : cancelledValorized.getContent()) {
				// If the valorized order had no estimated status, we create a estimated order associated at the same time
				if (orderNoEntity.getEstimatedFlag() == null || orderNoEntity.getEstimatedFlag() != CANCELLED_ESTIMATED_FLAG) {
					OrderDTO additionalEstimatedOrderDto = mapAdditionalEstimatedOrder(orderNoEntity);
					estimatedResult.getContent().add(additionalEstimatedOrderDto);
				}
				OrderDTO orderDto = mapValorizedOrder(orderNoEntity);
				valorizedResult.getContent().add(orderDto);
			}
		}

		// Get estimated orders
		Page<EstimatedOrderNoEntity> estimated = fundTransactionRepository.findEstimatedOrder(estimatedId, pageable);
		if (estimated != null && !CollectionUtils.isEmpty(estimated.getContent())) {
			for (EstimatedOrderNoEntity orderNoEntity : estimated.getContent()) {
				if (orderNoEntity.getEventType() == 38 && orderNoEntity.getStatus() == 6) {
					if (orderNoEntity.getUnitsNb() != null && orderNoEntity.getUnitsNb().compareTo(new BigDecimal(0)) != 0) {
						OrderDTO orderDto = mapEstimatedOrder(orderNoEntity);
						estimatedResult.getContent().add(orderDto);
					}
				} else {
					OrderDTO orderDto = mapEstimatedOrder(orderNoEntity);
					estimatedResult.getContent().add(orderDto);
				}

			}
		}

		// Get cancelled estimated orders
		Page<EstimatedOrderNoEntity> cancelledEstimated = fundTransactionRepository.findCancelledEstimatedOrder(estimatedId, pageable);
		if (cancelledEstimated != null && !CollectionUtils.isEmpty(cancelledEstimated.getContent())) {
			for (EstimatedOrderNoEntity orderNoEntity : cancelledEstimated.getContent()) {
				OrderDTO orderDto = mapEstimatedOrder(orderNoEntity);
				estimatedResult.getContent().add(orderDto);

			}
		}

		// Set the final response
		// If no data found, we set the old id else we set the new id
		initPageResult(valorized, valorizedResult, size,
				CollectionUtils.isEmpty(valorizedResult.getContent()) ? valorizedId : Long.parseLong(valorizedResult.getContent().get(valorizedResult.getContent().size() - 1).getGroupeId()));
		initPageResult(estimated, estimatedResult, size,
				CollectionUtils.isEmpty(estimatedResult.getContent()) ? estimatedId : Long.parseLong(estimatedResult.getContent().get(estimatedResult.getContent().size() - 1).getGroupeId()));
		extractOrderResponse.setValorizedResult(valorizedResult);
		extractOrderResponse.setEstimatedResult(estimatedResult);

		logger.info("Extract order is done {page = " + page + ", size = " + size + "} : result = valorized found (" + valorizedResult.getContent().size() + "), estimated found ("
				+ estimatedResult.getContent().size() + ")");

		return extractOrderResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ExtractService#updateOrder(lu.wealins.common.dto.liability.services.UpdateOrderRequest)
	 */
	@Override
	public UpdateOrderResponse updateOrder(UpdateOrderRequest updateOrderRequest) throws WssftrupdExportException {
		UpdateOrderResponse response = new UpdateOrderResponse();
		WssftrupdImport wssRequest = buildWssftrupdImportRequest();

		logger.info("Update estimated order is processing (estimated to update = " + updateOrderRequest.getEstimatedOrders().size() + "," + "valorized to update = "
				+ updateOrderRequest.getValorizedOrders().size() + ")");

		if (!updateOrderRequest.getEstimatedOrders().isEmpty()) {
			int cpt = 0;
			List<OrderDTO> subList = new ArrayList<>();
			// We need to split the webservice call to LISSIA. Because, LISSIA is so AWESOME, it is not able to
			// support more than 70 object to update
			for (OrderDTO estimatedOrder : updateOrderRequest.getEstimatedOrders()) {
				subList.add(estimatedOrder);
				if (cpt == MAX_SIZE) {
					updateOrderRest(subList, wssRequest, ESTIMATED_TYPE);
					subList.clear();
					cpt = 0;
				}
				cpt++;
			}
			// Last call
			if (!subList.isEmpty()) {
				updateOrderRest(subList, wssRequest, ESTIMATED_TYPE);
			}
		}

		if (!updateOrderRequest.getValorizedOrders().isEmpty()) {

			int cpt = 0;
			List<OrderDTO> subList = new ArrayList<>();
			// We need to split the webservice call to LISSIA. Because, LISSIA is so AWESOME, it is not able to
			// support more than 70 object to update
			for (OrderDTO valorizedOrder : updateOrderRequest.getValorizedOrders()) {
				subList.add(valorizedOrder);
				if (cpt == MAX_SIZE) {
					updateOrderRest(subList, wssRequest, VALORIZED_TYPE);
					subList.clear();
					cpt = 0;
				}
				cpt++;
			}
			// Last call
			if (!subList.isEmpty()) {
				updateOrderRest(subList, wssRequest, VALORIZED_TYPE);
			}
		}

		response.setSuccess(Boolean.TRUE);

		return response;

	}

	/**
	 * Initialize the page result with the pagination
	 * 
	 * @param pagination the pagination
	 * @param pageResult the page result
	 * @param size the size by page
	 */
	private <T> void initPageResult(Page<T> pagination, PageResult<OrderDTO> pageResult, int size, Long lastId) {
		if (size != 0) {
			pageResult.setSize(size);
		}
		pageResult.setTotalPages(pagination.getTotalPages());
		pageResult.setTotalRecordCount((int) pagination.getTotalElements());
		pageResult.setCurrentPage(pagination.getNumber());
		pageResult.setLastId(lastId);
	}

	/**
	 * CAll rest service for update lissia order
	 * 
	 * @param orders the orders to update
	 * @param wssRequest the REST request
	 * @param orderType the type of order
	 */
	private void updateOrderRest(List<OrderDTO> orders, WssftrupdImport wssRequest, String orderType) {
		// Start by clear the rows
		wssRequest.getImpGrpFtr().getRows().clear();

		for (OrderDTO order : orders) {
			wssRequest.getImpGrpFtr().getRows().add(prepareDataForUpdateLissia(order, orderType));
		}
		logger.info("Call lissia WS WSSFTRUPD to update (type = " + orderType + ") " + wssRequest.getImpGrpFtr().getRows().size() + " rows");
		// Lissa web service call
		try {
			WssftrupdExport wssftrupdResponse = wssftrupd.wssftrupdcall(wssRequest);
		} catch (Exception_Exception e) {
			logger.error("Error during the update flag order : " + e.getMessage());
			throw new WssftrupdExportException(e);
		}

	}

	/**
	 * Build a WssftrupdImport request object
	 * 
	 * @return the WssftrupdImport object
	 */
	private WssftrupdImport buildWssftrupdImportRequest() {
		WssftrupdImport wssRequest = new WssftrupdImport();
		ImpGrpFtr grpFtr = new ImpGrpFtr();
		wssRequest.setImpValidationUsers(buildValidationUser());
		wssRequest.setImpGrpFtr(grpFtr);
		return wssRequest;
	}

	/**
	 * Map a estimated order
	 * 
	 * @param orderNoEntity the order from lissia DB
	 * @return the order mapped
	 */
	private OrderDTO mapEstimatedOrder(EstimatedOrderNoEntity orderNoEntity) {
		OrderDTO orderDto = orderMapper.asEstimatedOrderDto(orderNoEntity);

		if (StringUtils.isNotEmpty(orderNoEntity.getIsinCode())) {
			orderDto.setSecurityId(orderNoEntity.getIsinCode().trim() + "+" + orderNoEntity.getFundCurrency());
		} else {
			orderDto.setSecurityId("+" + orderNoEntity.getFundCurrency());
		}
		orderDto.setValuationDate(new Date());
		orderDto.setQuantity(orderNoEntity.getUnitsNb());
		// Update transaction type
		orderDto.setTransactionType(getTransactionType(orderDto));

		orderDto.setQuantity(orderNoEntity.getUnitsNb().abs());

		// Set line id
		orderDto.setLineId(orderNoEntity.getId().toString() + OrderMapper.ESTIMATED_ORDER);

		// Cancelled order
		if (orderNoEntity.getStatus() == 2 || orderNoEntity.getStatus() == 5 || orderNoEntity.getStatus() == 7) {
			// Set line id
			orderDto.setLineId(orderNoEntity.getId().toString() + OrderMapper.ESTIMATED_ORDER + OrderMapper.CANCELLED_ORDER_SUFFIX);
			if (StringUtils.equals(orderDto.getTransactionType(), OrderMapper.ACHAT)) {
				orderDto.setTransactionType(OrderMapper.VENTE);
			} else {
				orderDto.setTransactionType(OrderMapper.ACHAT);
			}
		}

		orderDto.setValueFundCurrency(orderNoEntity.getAmountInvestedOrDesinvested());
		return orderDto;
	}

	/**
	 * Map additional estimated order. In the case where a valorized order does not come from a estimated status
	 * 
	 * @param orderNoEntity the order from lissia DB
	 * @return the order mapped
	 */
	private OrderDTO mapAdditionalEstimatedOrder(ValorizedOrderNoEntity orderNoEntity) {
		OrderDTO orderDto = orderMapper.asAdditionalEstimatedOrderDto(orderNoEntity);

		if (StringUtils.isNotEmpty(orderNoEntity.getIsinCode())) {
			orderDto.setSecurityId(orderNoEntity.getIsinCode().trim() + "+" + orderNoEntity.getFundCurrency());
		} else {
			orderDto.setSecurityId("+" + orderNoEntity.getFundCurrency());
		}
		orderDto.setValuationDate(new Date());
		orderDto.setQuantity(orderNoEntity.getUnitsNb());
		// Update transaction type
		orderDto.setTransactionType(getTransactionType(orderDto));

		orderDto.setQuantity(orderNoEntity.getUnitsNb().abs());
		// Set line id
		if (orderNoEntity.getStatus().intValue() == 5) {
			orderDto.setLineId(orderNoEntity.getId().toString() + OrderMapper.ESTIMATED_ORDER + OrderMapper.CANCELLED_ORDER_SUFFIX);
			if (orderDto.getTransactionType().equals(OrderMapper.ACHAT))
				orderDto.setTransactionType(OrderMapper.VENTE);
			else
				orderDto.setTransactionType(OrderMapper.ACHAT);
		} else {
			orderDto.setLineId(orderNoEntity.getId().toString() + OrderMapper.ESTIMATED_ORDER);
		}
		orderDto.setValueFundCurrency(orderNoEntity.getAmountInvestedOrDesinvested());
		return orderDto;
	}

	/**
	 * Map a valorized order
	 * 
	 * @param orderNoEntity the order from lissia DB
	 * @return the order mapped
	 */
	private OrderDTO mapValorizedOrder(ValorizedOrderNoEntity orderNoEntity) {
		OrderDTO orderDto = orderMapper.asValorizedOrderDto(orderNoEntity);

		if (StringUtils.isNotEmpty(orderNoEntity.getIsinCode())) {
			orderDto.setSecurityId(orderNoEntity.getIsinCode().trim() + "+" + orderNoEntity.getFundCurrency());
		} else {
			orderDto.setSecurityId("+" + orderNoEntity.getFundCurrency());
		}
		orderDto.setValuationDate(new Date());
		orderDto.setAmount(orderNoEntity.getAmountInvestedOrDesinvested().abs());
		orderDto.setQuantity(orderNoEntity.getUnitsNb());
		// Update transaction type
		orderDto.setTransactionType(getTransactionType(orderDto));

		orderDto.setQuantity(orderNoEntity.getUnitsNb().abs());
		// Set line id
		orderDto.setLineId(orderNoEntity.getId().toString() + OrderMapper.VALORIZED_ORDER);

		// Cancelled order
		if (orderNoEntity.getStatus() == 5) {
			// Set line id
			orderDto.setLineId(orderNoEntity.getId().toString() + OrderMapper.VALORIZED_ORDER + OrderMapper.CANCELLED_ORDER_SUFFIX);
			if (StringUtils.equals(orderDto.getTransactionType(), OrderMapper.ACHAT)) {
				orderDto.setTransactionType(OrderMapper.VENTE);
			} else {
				orderDto.setTransactionType(OrderMapper.ACHAT);
			}
		}
		orderDto.setValueFundCurrency(orderNoEntity.getAmountInvestedOrDesinvested());
		return orderDto;
	}

	/**
	 * Update flag ESTIMATED_TRANS_SENT or ft.VALUED_TRANS_SENT into lissia database
	 * 
	 * @param order the order to update
	 * @param typeOrder : ESTIMATED or VALORIZED
	 */
	private Row prepareDataForUpdateLissia(OrderDTO order, String typeOrder) {
		// create web service request
		WssftrupdImport wssRequest = new WssftrupdImport();
		wssRequest.setImpValidationUsers(buildValidationUser());
		Row row = new Row();
		ImpItmFtrFundTransactions fundTransaction = new ImpItmFtrFundTransactions();
		// HACK : we set the frt id by group id, because the group id is equal to the line id
		fundTransaction.setFtrId(Long.valueOf(order.getGroupeId()));

		if (StringUtils.equals(typeOrder, VALORIZED_TYPE)) {
			// valorized cancelled order
			if (order.getStatus() == 5) {
				fundTransaction.setValuedTransSent(CANCELLED_VALORIZED_FLAG);
			} else {
				fundTransaction.setValuedTransSent(VALORIZED_FLAG);
			}
		} else if (StringUtils.equals(typeOrder, ESTIMATED_TYPE)) {
			// estimated cancelled order
			if (order.getStatus() == 2 || order.getStatus() == 5 || order.getStatus() == 7) {
				fundTransaction.setEstimatedTransSent(CANCELLED_ESTIMATED_FLAG);
			} else {
				fundTransaction.setEstimatedTransSent(ESTIMATED_FLAG);
			}
		}

		row.setImpItmFtrFundTransactions(fundTransaction);

		return row;

	}

	/**
	 * Build validation user for web service lissa call
	 * 
	 * @return the user
	 */
	private ImpValidationUsers buildValidationUser() {
		ImpValidationUsers users = new ImpValidationUsers();
		users.setLoginId(wsLoginCredential);
		users.setPassword(wsPasswordCredential);
		return users;
	}

	/**
	 * Get the right transaction type in function of the event
	 * 
	 * @param order the order
	 * @return the right transaction type
	 */
	private String getTransactionType(OrderDTO order) {
		if (StringUtils.equals(order.getEventType(), "71") || StringUtils.equals(order.getEventType(), "15") || StringUtils.equals(order.getEventType(), "3")
				|| StringUtils.equals(order.getEventType(), "13") || StringUtils.equals(order.getEventType(), "17") || StringUtils.equals(order.getEventType(), "37")
				|| StringUtils.equals(order.getEventType(), "4") || StringUtils.equals(order.getEventType(), "12") || StringUtils.equals(order.getEventType(), "21")) {
			return OrderMapper.VENTE;
		} else if (StringUtils.equals(order.getEventType(), "38") || StringUtils.equals(order.getEventType(), "8")) {
			return OrderMapper.ACHAT;
		} else if (StringUtils.equals(order.getEventType(), "44")) {
			if (order.getQuantity().compareTo(new BigDecimal(0)) >= 0)
				return OrderMapper.ACHAT;
			else
				return OrderMapper.VENTE;
		}
		return null;
	}

	@Override
	public ValorizedTransactionSearchResponse findValorizedOrdersForFIDNotTransmitted(int page, int size) {
		Pageable pageable = new PageRequest(page, size);
		Page<FundTransactionEntity> results = fundTransactionRepository.findValorizedOrdersForFIDNotTransmitted(pageable);
		List<OrderFIDDTO> ordersMapped = new ArrayList<OrderFIDDTO>();
		ValorizedTransactionSearchResponse response = new ValorizedTransactionSearchResponse();
		for (FundTransactionEntity fundTransaction : results) {

			// if(Constantes.eventTypes.containsKey(fundTransaction.getEventType())){

			FundEntity fund = fundRepository.getOne(fundTransaction.getFund());

			ValorizedOrderForFIDEntity orderMapped = new ValorizedOrderForFIDEntity();
			orderMapped.setCurrency(fundTransaction.getFundCurrency());
			orderMapped.setEventType(fundTransaction.getEventType());
			orderMapped.setFund(fundTransaction.getFund());
			orderMapped.setInjectionReference(fundTransaction.getFtrId());
			orderMapped.setOperationDate(fundTransaction.getDate0());
			orderMapped.setPrice(fundTransaction.getPrice().abs());
			orderMapped.setQuantity(fundTransaction.getUnits().abs());
			orderMapped.setLineId(String.valueOf(fundTransaction.getFtrId()));
			orderMapped.setStatus(fundTransaction.getStatus().shortValue());

			if (ArrayUtils.contains(Constantes.BUY_EVENT_TYPE_LIST, orderMapped.getEventType().intValue()))
				orderMapped.setOrderType(BUY);

			if (ArrayUtils.contains(Constantes.SELL_EVENT_TYPE_LIST, orderMapped.getEventType().intValue()))
				orderMapped.setOrderType(SELL);

			if (ArrayUtils.contains(Constantes.OUT_EVENT_TYPE_LIST, orderMapped.getEventType().intValue()))
				orderMapped.setOrderType(OUT);

			if (fundTransaction.getStatus() == 1)
				orderMapped.setTransactionStatus(FINISHED);

			if (fundTransaction.getStatus() == 5)
				orderMapped.setTransactionStatus(CANCELED);

			orderMapped.setEventLabel(Constantes.eventTypes.get(fundTransaction.getEventType()));

			if (fund != null && fund.getAccountRoot() != null && fund.getCurrency() != null) {
				orderMapped.setAccountRoot("FD_" + fund.getAccountRoot().trim() + "_" + fund.getCurrency());
				orderMapped.setFundCurrency(fund.getCurrency());
			}
			OrderFIDDTO order = orderMapper.asValorizedOrderForFIDDto(orderMapped);
			order.setMirrorPortfolioCode("MIR " + order.getPortfolioCode());
			ordersMapped.add(order);
		}

		// }
		response.setOrders(ordersMapped);
		return response;
	}

}
