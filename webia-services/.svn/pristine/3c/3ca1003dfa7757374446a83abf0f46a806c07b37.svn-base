package lu.wealins.webia.services.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.ExtractOrderFIDResponse;
import lu.wealins.common.dto.webia.services.ExtractOrderResponse;
import lu.wealins.common.dto.webia.services.PageResult;
import lu.wealins.common.dto.webia.services.ReportingComDTO;
import lu.wealins.common.dto.webia.services.SapAccountingDTO;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderFIDRequest;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderFIDResponse;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderRequest;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderResponse;
import lu.wealins.webia.services.core.service.ExtractService;
import lu.wealins.webia.services.ws.rest.ExtractRESTService;

@Component
public class ExtractRESTServiceImpl implements ExtractRESTService {

	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ExtractRESTServiceImpl.class);

	/**
	 * The extract service
	 */
	@Autowired
	private ExtractService extractService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.ExtractRESTService#extractSapAccounting(javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public PageResult<SapAccountingDTO> extractSapAccountingPagination(SecurityContext context, String origin, int page, int size, Long lastId) {
		logger.info("extractSapAccounting Origin " + origin +  " page " + page + " size " + size);
		
		try {
			return extractService.extractSapAccounting(origin, page, size, lastId);
		} catch (Exception e) {
			logger.error("Error during extract sap accounting " + e);
			throw new IllegalArgumentException("Error during extract sap accounting " + e);
		}
		
	}

	@Override
	public Response confirmExtractSapAccounting(SecurityContext context, Collection<Long> successIds) {
		Boolean response = extractService.confirmExtractSapAccounting(successIds);
		return Response.ok(response).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.ExtractRESTService#extractOrder(javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public Response extractOrder(SecurityContext context) {
		ExtractOrderResponse response = extractService.extractOrder();
		return Response.ok(response).build();

	}

	@Override
	public Response saveLissiaOrder(SecurityContext context, SaveLissiaOrderRequest saveLissiaOrderRequest) {
		SaveLissiaOrderResponse saveLissiaOrderResponse = extractService.saveLissiaOrder(saveLissiaOrderRequest);
		return Response.ok(saveLissiaOrderResponse).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.ExtractRESTService#extractReportingCom(javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public PageResult<ReportingComDTO> extractReportingComPagination(SecurityContext context, int page, int size, Long lastId, Long reportId) {
		logger.info("extractReportingCom for create file ");
		return extractService.extractReportingCom(page, size, lastId, reportId);
	}

	@Override
	public Response confirmExtractReportingCom(SecurityContext context, Collection<Long> successIds) {
		Boolean response = extractService.confirmExtractReportingCom(successIds);
		return Response.ok(response).build();
	}

	@Override
	public Response extractOrderFID(SecurityContext context) {
		ExtractOrderFIDResponse response = extractService.extractOrderFID();
		return Response.ok(response).build();
	}

	@Override
	public Response saveLissiaOrderFID(SecurityContext context, SaveLissiaOrderFIDRequest saveLissiaOrderFIDRequest) {
		SaveLissiaOrderFIDResponse saveLissiaOrderFIDResponse = extractService.saveLissiaOrderFID(saveLissiaOrderFIDRequest);
		return Response.ok(saveLissiaOrderFIDResponse).build();
	}
}
