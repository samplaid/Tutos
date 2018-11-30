package lu.wealins.webia.services.ws.rest.impl;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.webia.services.core.components.PstIdWrapper;
import lu.wealins.webia.services.core.service.TransactionExtractionCommissionService;
import lu.wealins.webia.services.core.service.TransactionExtractionService;
import lu.wealins.webia.services.ws.rest.LissiaExtractRESTService;
import lu.wealins.common.dto.webia.services.ReportingComDTO;
import lu.wealins.common.dto.webia.services.TransactionDTO;

@Component
public class LissiaExtractRESTServiceImpl implements LissiaExtractRESTService {
	@Autowired
	private TransactionExtractionService transactionExtractionService;

	@Autowired
	private TransactionExtractionCommissionService transactionExtractionCommissionService;

	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(LissiaExtractRESTServiceImpl.class);
	
	/* (non-Javadoc)
	 * @see lu.wealins.webia.services.ws.rest.LissiaExtractRESTService#insertIntoSapAccounting(javax.ws.rs.core.SecurityContext, java.util.Collection)
	 */
	@Override
	public Response insertIntoSapAccounting(SecurityContext context, Collection<TransactionDTO> transactionDTOs) {
		try {
			List<PstIdWrapper> response = transactionExtractionService.processAccountTransactionsIntoSAPAccounting(transactionDTOs);
			return Response.ok(response).build();
		} catch (Exception e) {
			logger.error("Error during insert into SapAccounting " + e);
			return Response.serverError().build();
		}
	}

	/* (non-Javadoc)
	 * @see lu.wealins.webia.services.ws.rest.LissiaExtractRESTService#insertCommissionToPay(javax.ws.rs.core.SecurityContext, java.util.Collection)
	 */
	@Override
	public Response insertCommissionToPay(SecurityContext context, Collection<TransactionDTO> transactionDTOs) {
		try {
			Collection<Long> response = transactionExtractionCommissionService.processTransactionsIntoCommissionToPay(transactionDTOs);
			return Response.ok(response).build();
		} catch (Exception e) {
			logger.error("Error during insert CommissionToPay " + e);
			return Response.serverError().build();
		}
	}

	/* (non-Javadoc)
	 * @see lu.wealins.webia.services.ws.rest.LissiaExtractRESTService#insertReportingCom(javax.ws.rs.core.SecurityContext, java.util.Collection, java.lang.Long)
	 */
	@Override
	public Response insertReportingCom(SecurityContext context, Collection<TransactionDTO> transactionDTOs, Long reportId) {
		try {
			Collection<Long> response = transactionExtractionCommissionService.processTransactionsIntoReportingCom(transactionDTOs, reportId);
			return Response.ok(response).build();
		} catch (Exception e) {
			logger.error("Error during insert ReportingCom " + e);
			return Response.serverError().build();
		}
	}
	
	/* (non-Javadoc)
	 * @see lu.wealins.webia.services.ws.rest.LissiaExtractRESTService#insertReportingComExternal132(javax.ws.rs.core.SecurityContext, java.util.Collection, java.lang.Long)
	 */
	@Override
	public Response insertReportingComExternal132(SecurityContext context, Collection<ReportingComDTO> reportingComDTO, Long reportId) {
		try {
			Collection<Long> response = transactionExtractionCommissionService.processTransactionsExternal132IntoReportingCom(reportingComDTO, reportId);
			return Response.ok(response).build();
		} catch (Exception e) {
			logger.error("Error during insert ReportingCom External 132 " + e);
			return Response.serverError().build();
		}
	}
	
	/* (non-Javadoc)
	 * @see lu.wealins.webia.services.ws.rest.LissiaExtractRESTService#insertReportingComExternal133(javax.ws.rs.core.SecurityContext, java.util.Collection, java.lang.Long)
	 */
	@Override
	public Response insertReportingComExternal133(SecurityContext context, Collection<ReportingComDTO> reportingComDTO, Long reportId) {
		try {
			Collection<Long> response = transactionExtractionCommissionService.processTransactionsExternal133IntoReportingCom(reportingComDTO, reportId);
			return Response.ok(response).build();
		} catch (Exception e) {
			logger.error("Error during insert ReportingCom External 133 " + e);
			return Response.serverError().build();
		}
	}

	/* (non-Javadoc)
	 * @see lu.wealins.webia.services.ws.rest.LissiaExtractRESTService#removeFromSapAccounting(javax.ws.rs.core.SecurityContext, java.util.List)
	 */
	@Override
	public Response removeFromSapAccounting(SecurityContext context, List<Long> sapAccIds) {
		try {
			Long response = transactionExtractionService.removeTransactionsFromSAPAccounting(sapAccIds);
			return Response.ok(response).build();
		} catch (Exception e) {
			logger.error("Error during remove from SapAccounting " + e);
			return Response.serverError().build();
		}
	}

}
