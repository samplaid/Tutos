package lu.wealins.webia.services.core.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import lu.wealins.common.dto.OrderDTO;
import lu.wealins.common.dto.webia.services.ExtractOrderFIDResponse;
import lu.wealins.common.dto.webia.services.ExtractOrderResponse;
import lu.wealins.common.dto.webia.services.OrderFIDDTO;
import lu.wealins.common.dto.webia.services.PageResult;
import lu.wealins.common.dto.webia.services.ReportingComDTO;
import lu.wealins.common.dto.webia.services.SapAccountingDTO;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderFIDRequest;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderFIDResponse;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderRequest;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderResponse;
import lu.wealins.webia.services.core.mapper.ReportingComMapper;
import lu.wealins.webia.services.core.mapper.SapAccountingMapper;
import lu.wealins.webia.services.core.mapper.SasOrderMapper;
import lu.wealins.webia.services.core.persistence.entity.ReportingComEntity;
import lu.wealins.webia.services.core.persistence.entity.SapAccountingEntity;
import lu.wealins.webia.services.core.persistence.entity.SasMappingEntity;
import lu.wealins.webia.services.core.persistence.entity.SasOrderAggEntity;
import lu.wealins.webia.services.core.persistence.entity.SasOrderEntity;
import lu.wealins.webia.services.core.persistence.entity.SasOrderFIDEntity;
import lu.wealins.webia.services.core.persistence.repository.ReportingComRepository;
import lu.wealins.webia.services.core.persistence.repository.SapAccountingRepository;
import lu.wealins.webia.services.core.persistence.repository.SasMappingRepository;
import lu.wealins.webia.services.core.persistence.repository.SasOrderAggRepository;
import lu.wealins.webia.services.core.persistence.repository.SasOrderFIDRepository;
import lu.wealins.webia.services.core.persistence.repository.SasOrderRepository;
import lu.wealins.webia.services.core.service.ExtractService;
import lu.wealins.webia.services.ws.rest.impl.ExtractRESTServiceImpl;

@Service
@Transactional
public class ExtractServiceImpl implements ExtractService {

	private static final String IS_ESTIMATED = "E";

	private static final String EXPORT_FROM_DALI = "DALI";

	private static final String EXPORT_FROM_LISSIA = "LISSIA";

	private static final String CANCELED = "ANNULEE";
	private static final String CANCELED_SUFFIX = "_A";
	private static final String AGGREGATION_PREFIX = "_AG";
	private static final String AGGREGATION_CONSTANT = "AGG";

	private static final String MADMF = "MADMF";
	private static final String MMORT = "MMORT";

	private static final String SELL = "VENTE";
	private static final String BUY = "ACHAT";

	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ExtractRESTServiceImpl.class);

	@Autowired
	private SapAccountingRepository sapAccountingRepository;

	@Autowired
	private ReportingComRepository reportingComRepository;

	@Autowired
	private SasOrderRepository sasOrderRepository;

	@Autowired
	private SasOrderAggRepository sasOrderAggRepository;

	@Autowired
	private SasOrderFIDRepository sasOrderFIDRepository;

	@Autowired
	private SasMappingRepository sasMappingRepository;

	@Autowired
	private SapAccountingMapper sapAccountingMapper;

	@Autowired
	private ReportingComMapper reportingComMapper;

	@Autowired
	private SasOrderMapper sasOrderMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.ExtractService#extractSapAccounting(java.lang.String)
	 */
	@Override
	public PageResult<SapAccountingDTO> extractSapAccounting(String origin, int page, int size, Long lastId) {

		Pageable pageable = new PageRequest(page, size);

		PageResult<SapAccountingDTO> result = new PageResult<SapAccountingDTO>();
		Page<SapAccountingEntity> pageResult = sapAccountingRepository.findByExportDateIsNull(origin, pageable);

		if (size != 0)
			result.setSize(size);
		result.setTotalPages(pageResult.getTotalPages());
		result.setTotalRecordCount(pageResult.getTotalElements());
		result.setCurrentPage(pageResult.getNumber());
		result.setContent(sapAccountingMapper.asSapAccountingDTOs(pageResult.getContent()).stream().collect(Collectors.toList()));
		// result.setContent(sapAccountingMapper.asSapAccountingDTOs(processPostMapping(pageResult.getContent())).stream().collect(Collectors.toList()));
		return result;
	}

	public void processPostMapping(Collection<SapAccountingEntity> sapAccs) {
		// SET EXPORT DATE
		if (sapAccs != null) {
			sapAccs.forEach((acc) -> acc.setExportDate(new Date()));
			sapAccountingRepository.save(sapAccs);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.ExtractService#extractOrder()
	 */
	@Override
	public ExtractOrderResponse extractOrder() {
		ExtractOrderResponse extractOrderResponse = new ExtractOrderResponse();

		List<SasOrderEntity> elements = sasOrderRepository.findOrderToExport();
		if (!CollectionUtils.isEmpty(elements)) {
			for (SasOrderEntity sasOrderEntity : elements) {
				// update send date
				sasOrderEntity.setSendDate(new Date());

				// Order from lissia
				if (StringUtils.equals(sasOrderEntity.getOrigin(), EXPORT_FROM_LISSIA)) {

					// IF is estimated order from Lissia
					if (StringUtils.equals(sasOrderEntity.getIsEstimated(), IS_ESTIMATED)) {
						extractOrderResponse.getLissiaEstimatedOrder().add(sasOrderMapper.asSasOrderDTO(sasOrderEntity));
					} else { // is valorized order from Lissia
						extractOrderResponse.getLissiaValorizedOrder().add(sasOrderMapper.asSasOrderDTO(sasOrderEntity));
					}

				} else if (StringUtils.equals(sasOrderEntity.getOrigin(), EXPORT_FROM_DALI)) {
					// IF is estimated order from Dali
					if (StringUtils.equals(sasOrderEntity.getIsEstimated(), IS_ESTIMATED)) {
						extractOrderResponse.getDaliEstimatedOrder().add(sasOrderMapper.asSasOrderDTO(sasOrderEntity));
					} else { // is valorized order from Dali
						extractOrderResponse.getDaliValorizedOrder().add(sasOrderMapper.asSasOrderDTO(sasOrderEntity));
					}
				} else { // Case of error : no orign found
					logger.error("Error during the order extraction : the order " + +sasOrderEntity.getId() + " has no origin value ");
				}
			}
		}

		// Save to update the send date
		sasOrderRepository.save(elements);
		logger.info("Extract order finished");
		return extractOrderResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.ExtractService#saveLissiaOrder(lu.wealins.webia.services.ws.rest.batch.SaveLissiaOrderRequest)
	 */
	@Override
	public SaveLissiaOrderResponse saveLissiaOrder(SaveLissiaOrderRequest saveLissiaOrderRequest) {
		SaveLissiaOrderResponse saveLissiaOrderResponse = new SaveLissiaOrderResponse();
		HashMap<String, List<SasOrderEntity>> valorizedAggregatedOrders = new HashMap<String, List<SasOrderEntity>>();
		HashMap<String, List<SasOrderEntity>> estimatedAggregatedOrders = new HashMap<String, List<SasOrderEntity>>();
		if (saveLissiaOrderRequest != null) {

			Map<String, String> eventMapping = eventMappingToMap(sasMappingRepository.findByType(SasMappingRepository.EVENT_TYPE));

			if (!eventMapping.isEmpty()) {
				// Case for the valorized order
				if (!CollectionUtils.isEmpty(saveLissiaOrderRequest.getLissiaValorizedOrder())) {
					List<OrderDTO> lissiaValorizedOrders = saveLissiaOrderRequest.getLissiaValorizedOrder();
					List<SasOrderEntity> sasOrderEntityList = new ArrayList<>();
					List<SasOrderEntity> sasOrderEntityListAggregated = new ArrayList<>();
					for (OrderDTO order : lissiaValorizedOrders) {

						if (StringUtils.isNotEmpty(order.getEventType())) {
							if (eventMapping.containsKey(order.getEventType())) {
								order.setEventType(eventMapping.get(order.getEventType()));
								// Check if the line id already exist
								if (CollectionUtils.isEmpty(sasOrderRepository.findByLineIdAndEstimatedFlagAndTransactionType(order.getLineId(), order.getIsEstimated(), order.getTransactionType()))) {
									SasOrderEntity sasOrder = sasOrderMapper.asSasOrderEntity(order);
									logger.debug("Treating ORDER " + order.getLineId() + " - POLICY " + order.getPolicyId());
									if (!checkIfHadToBeAggregated(valorizedAggregatedOrders, sasOrder))
										sasOrderEntityList.add(sasOrder);
								}
							} else {
								logger.error("Code event : " + order.getEventType() + " does not exist in the mapping event list");
							}
						} else {
							logger.error("The LISSIA order with the line id : " + order.getLineId() + " has no event type");
						}
					}
					List<List<SasOrderEntity>> aggregatedOrdersList = createAggregatedOrders(valorizedAggregatedOrders);
					sasOrderEntityList.addAll(aggregatedOrdersList.get(0));
					sasOrderRepository.save(sasOrderEntityList);
					sasOrderEntityListAggregated.addAll(aggregatedOrdersList.get(1));
					List<SasOrderAggEntity> aggregatedOrdersToSave = sasOrderMapper.asSasOrderEntityAggList(sasOrderEntityListAggregated);
					sasOrderAggRepository.save(aggregatedOrdersToSave);

				}

				// Case for the estimated order
				if (!CollectionUtils.isEmpty(saveLissiaOrderRequest.getLissiaEstimatedOrder())) {
					List<OrderDTO> lissiaEstimatedOrders = saveLissiaOrderRequest.getLissiaEstimatedOrder();
					List<SasOrderEntity> sasOrderEntityList = new ArrayList<>();
					List<SasOrderEntity> sasOrderEntityListAggregated = new ArrayList<>();
					for (OrderDTO order : lissiaEstimatedOrders) {

						if (StringUtils.isNotEmpty(order.getEventType())) {
							if (eventMapping.containsKey(order.getEventType())) {
								order.setEventType(eventMapping.get(order.getEventType()));
								// Check if the line id already exist
								if(CollectionUtils.isEmpty(sasOrderAggRepository.findByLineIdAndEstimatedFlagAndTransactionType(order.getLineId(), order.getIsEstimated(), order.getTransactionType()))) {
									if (CollectionUtils.isEmpty(sasOrderRepository.findByLineIdAndEstimatedFlagAndTransactionType(order.getLineId(), order.getIsEstimated(), order.getTransactionType()))) {
										SasOrderEntity sasOrder = sasOrderMapper.asSasOrderEntity(order);
										logger.debug("Treating ORDER " + order.getLineId() + " - POLICY " + order.getPolicyId());
										if (!checkIfHadToBeAggregated(estimatedAggregatedOrders, sasOrder))
											sasOrderEntityList.add(sasOrder);
									}
								}
							} else {
								logger.error("Code event : " + order.getEventType() + " does not exist in the mapping event list");
							}
						} else {
							logger.error("The LISSIA order with the line id : " + order.getLineId() + " has no event type");
						}
					}
					List<List<SasOrderEntity>> aggregatedOrdersList = createAggregatedOrders(estimatedAggregatedOrders);
					sasOrderEntityList.addAll(aggregatedOrdersList.get(0));
					sasOrderRepository.save(sasOrderEntityList);
					sasOrderEntityListAggregated.addAll(aggregatedOrdersList.get(1));
					List<SasOrderAggEntity> aggregatedOrdersToSave = sasOrderMapper.asSasOrderEntityAggList(sasOrderEntityListAggregated);
					sasOrderAggRepository.save(aggregatedOrdersToSave);
				}
				saveLissiaOrderResponse.setSuccess(Boolean.TRUE);
			} else {
				logger.error("Cannot save the lissia order beacause the mapping event list is empty");
			}
		}
		logger.info("Save lissia order finished");
		return saveLissiaOrderResponse;
	}

	/**
	 * Build a map for the event mapping. The input and output are used as key and value
	 * 
	 * @param eventMapping
	 * @return the map. (The map cannot be null, only empty)
	 */
	private Map<String, String> eventMappingToMap(List<SasMappingEntity> eventMapping) {
		Map<String, String> eventMap = new HashMap<>();

		if (!CollectionUtils.isEmpty(eventMapping)) {
			for (SasMappingEntity sasMappingEntity : eventMapping) {
				eventMap.put(sasMappingEntity.getInput(), sasMappingEntity.getOutput());
			}
		}

		return eventMap;
	}

	@Override
	public Boolean confirmExtractSapAccounting(Collection<Long> successIds) {

		try {
			for (Long id : successIds) {
				SapAccountingEntity res = sapAccountingRepository.findOne(id);
				res.setExportDate(new Date());
				sapAccountingRepository.save(res);
			}
			return true;
		} catch (Exception e) {
			logger.error("Error update export date " + e);
			return false;
		}

	}

	@Override
	public PageResult<ReportingComDTO> extractReportingCom(int page, int size, Long lastId, Long reportId) {

		Pageable pageable = new PageRequest(page, size);
		
		// Generate a date which represents the first day of the current month and year 
		Date comDt = Date.from(LocalDate.now().withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
		logger.info("Update reportId before extract for reportId [{}] with date [{}]", reportId, comDt);
		// Update of reportId to include/exclude records by the comDt
		reportingComRepository.updateReportIdByReportIdAndComDt(reportId, comDt);
		reportingComRepository.updateReportIdByComDt(reportId, comDt);

		PageResult<ReportingComDTO> result = new PageResult<ReportingComDTO>();
		Page<ReportingComEntity> pageResult = reportingComRepository.findByReportIdAndExportDateIsNull(lastId, reportId, pageable);

		if (size != 0)
			result.setSize(size);
		result.setTotalPages(pageResult.getTotalPages());
		result.setTotalRecordCount(pageResult.getTotalElements());
		result.setCurrentPage(pageResult.getNumber());
		result.setContent(reportingComMapper.asReportingComDTOs(pageResult.getContent()).stream().collect(Collectors.toList()));
		// result.setContent(sapAccountingMapper.asSapAccountingDTOs(processPostMapping(pageResult.getContent())).stream().collect(Collectors.toList()));
		return result;
	}

	@Override
	public Boolean confirmExtractReportingCom(Collection<Long> successIds) {

		try {
			for (Long id : successIds) {
				ReportingComEntity res = reportingComRepository.findOne(id);
				res.setExportDt(new Date());
				reportingComRepository.save(res);
			}
			return true;
		} catch (Exception e) {
			logger.error("Error update export date " + e);
			return false;
		}

	}

	@Override
	public SaveLissiaOrderFIDResponse saveLissiaOrderFID(SaveLissiaOrderFIDRequest saveLissiaOrderFIDRequest) {
		SaveLissiaOrderFIDResponse saveLissiaOrderResponse = new SaveLissiaOrderFIDResponse();

		if (saveLissiaOrderFIDRequest != null) {

			Map<String, String> eventMapping = eventMappingToMap(sasMappingRepository.findByType(SasMappingRepository.EVENT_TYPE));

			if (!eventMapping.isEmpty()) {
				// Case for the valorized order
				if (!CollectionUtils.isEmpty(saveLissiaOrderFIDRequest.getLissiaOrderFID())) {
					List<OrderFIDDTO> lissiaFID = saveLissiaOrderFIDRequest.getLissiaOrderFID();
					List<SasOrderFIDEntity> sasOrderFIDEntityList = new ArrayList<>();
					for (OrderFIDDTO order : lissiaFID) {

						if (StringUtils.isNotEmpty(order.getEventType())) {
							if (eventMapping.containsValue(order.getEventType())) {
								SasOrderFIDEntity sasOrder = sasOrderMapper.asSasOrderFIDEntity(order);

								if (order.getTransactionId() != null) {
									if (order.getStatus() != null && order.getStatus().equals(CANCELED))
										sasOrder.setTransactionId(order.getTransactionId().concat(CANCELED_SUFFIX));
								}

								sasOrder.setCreationDate(new Date());

								List<SasOrderFIDEntity> ordersLISSIA = sasOrderFIDRepository.findByTransactionId(sasOrder.getTransactionId());

								if (CollectionUtils.isEmpty(ordersLISSIA)) {
									logger.info("##### ORDER " + sasOrder.getTransactionId());
									sasOrderFIDEntityList.add(sasOrder);
								}

							} else {
								logger.error("Code event : " + order.getEventType() + " does not exist in the mapping event list");
							}
						} else {
							logger.error("The LISSIA order with the transaction ID : " + order.getTransactionId() + " has no event type");
						}
					}
					sasOrderFIDRepository.save(sasOrderFIDEntityList);

				}

				saveLissiaOrderResponse.setSuccess(Boolean.TRUE);
			} else {
				logger.error("Cannot save the lissia order beacause the mapping event list is empty");
			}
		}
		return saveLissiaOrderResponse;

	}

	@Override
	public ExtractOrderFIDResponse extractOrderFID() {
		ExtractOrderFIDResponse extractOrderFIDResponse = new ExtractOrderFIDResponse();

		List<SasOrderFIDEntity> elements = sasOrderFIDRepository.findOrderToExport();
		if (!CollectionUtils.isEmpty(elements)) {
			for (SasOrderFIDEntity sasOrderFIDEntity : elements) {
				// update send date
				sasOrderFIDEntity.setSendDate(new Date());

				// Order from lissia
				extractOrderFIDResponse.getLissiaFID().add(sasOrderMapper.asSasOrderFIDDTO(sasOrderFIDEntity));
			}
		} else { // Case of error : no orign found
			logger.error("Error during the order extraction");
		}

		// Save to update the send date
		sasOrderFIDRepository.save(elements);

		return extractOrderFIDResponse;
	}

	/**
	 * create aggregated orders
	 * 
	 * @param aggregatedOrders
	 * @return
	 */
	private List<List<SasOrderEntity>> createAggregatedOrders(HashMap<String, List<SasOrderEntity>> aggregatedOrders) {

		List<List<SasOrderEntity>> totalOrders = new ArrayList<List<SasOrderEntity>>();
		List<SasOrderEntity> aggregatedOrdersList = new ArrayList<SasOrderEntity>();
		List<SasOrderEntity> aggregatedOrdersDetailsList = new ArrayList<SasOrderEntity>();

		for (Entry<String, List<SasOrderEntity>> entry : aggregatedOrders.entrySet()) {

			List<SasOrderEntity> aggregatedOrdersDetailsListTemp = new ArrayList<SasOrderEntity>();
			SasOrderEntity order = entry.getValue().get(0);
			BigDecimal quantitySum = null;
			BigDecimal amountSum = null;
			for (SasOrderEntity o : entry.getValue()) {

				logger.debug("Treating ORDER (aggregation) " + order.getLineId() + " - POLICY " + order.getPolicyId());

				BigDecimal recalculateQuantity = new BigDecimal(0);

				if ((o.getQuantity() == null || o.getQuantity().compareTo(new BigDecimal(0)) == 0) && (o.getValueFundCurrency() != null && o.getNav() != null)) {
					if (quantitySum == null)
						quantitySum = new BigDecimal(0);
					recalculateQuantity = recalculateQuantity.add(o.getValueFundCurrency().divide(o.getNav(), 10, RoundingMode.HALF_EVEN));
					quantitySum = quantitySum.add(recalculateQuantity);
				} else {
					if (o.getQuantity() != null) {
						if (quantitySum == null)
							quantitySum = new BigDecimal(0);
						quantitySum = quantitySum.add(o.getQuantity());
					}

				}

				if (o.getAmount() != null) {
					if (amountSum == null)
						amountSum = new BigDecimal(0);
					amountSum = amountSum.add(o.getAmount());
				}

				SasOrderEntity copy = sasOrderMapper.asSasOrder(o);
				if (recalculateQuantity.compareTo(new BigDecimal(0)) != 0)
					copy.setQuantity(recalculateQuantity.abs());

				aggregatedOrdersDetailsListTemp.add(copy);
			}

			if (aggregatedOrdersDetailsListTemp.size() > 1) {
				Iterator<SasOrderEntity> it = aggregatedOrdersDetailsListTemp.iterator();
				while (it.hasNext()) {
					SasOrderEntity s = it.next();
					s.setGroupeId(order.getLineId() + AGGREGATION_PREFIX);
				}
				aggregatedOrdersDetailsList.addAll(aggregatedOrdersDetailsListTemp);
			}

			if (entry.getValue().size() > 1) {
				order.setAggregationFlag("Y");
				order.setLineId(order.getLineId() + AGGREGATION_PREFIX);
				order.setContractId(AGGREGATION_CONSTANT);
				order.setPolicyId(AGGREGATION_CONSTANT);
				order.setSct(null);
			}

			order.setQuantity(quantitySum);
			order.setAmount(amountSum);

			if (order.getIsEstimated().equals("E")) {
				if (order.getQuantity().compareTo(new BigDecimal(0)) != 0)
					order.setIsQantity("Q");
				else
					order.setIsQantity("M");
			}

			if (order.getQuantity() != null && order.getQuantity().compareTo(new BigDecimal(0)) < 0)
				order.setTransactionType(SELL);

			order.setQuantity(quantitySum.abs());

			aggregatedOrdersList.add(order);
		}

		totalOrders.add(aggregatedOrdersList);
		totalOrders.add(aggregatedOrdersDetailsList);
		return totalOrders;
	}

	/**
	 * if order needs to be aggregated with other orders
	 * 
	 * @param aggregatedOrders
	 * @param sasOrder
	 */
	private boolean checkIfHadToBeAggregated(HashMap<String, List<SasOrderEntity>> aggregatedOrders, SasOrderEntity sasOrder) {
		if (sasOrder.getEventType().equals(MADMF) || sasOrder.getEventType().equals(MMORT)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			String key = sasOrder.getSecurityId() + sasOrder.getPortfolio() + sasOrder.getTransactionType() + formatter.format(sasOrder.getValuationDate())
					+ sasOrder.getOrigin() + sasOrder.getEventType() + formatter.format(sasOrder.getNavDate()) + sasOrder.getNav();

			if (aggregatedOrders.containsKey(key)) {
				aggregatedOrders.get(key).add(sasOrder);
			} else {
				ArrayList<SasOrderEntity> list = new ArrayList<SasOrderEntity>();
				list.add(sasOrder);
				aggregatedOrders.put(key, list);
			}
			return true;
		} else {
			return false;
		}
	}

}
