package lu.wealins.liability.services.ws.rest.impl;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.liability.services.core.business.FundTransactionService;
import lu.wealins.liability.services.core.business.PostingSetService;
import lu.wealins.liability.services.core.utils.SecurityContextUtils;
import lu.wealins.liability.services.ws.rest.PostingSetsRESTService;
import lu.wealins.common.dto.liability.services.PstPostingSetsDTO;

@Component
public class PostingSetsRESTServiceImpl implements PostingSetsRESTService {
	private static final Logger logger = LoggerFactory.getLogger(PostingSetsRESTServiceImpl.class);
	@Autowired
	private SecurityContextUtils securityContextUtils;
	@Autowired
	private FundTransactionService fundTransactionService;
	@Autowired
	private PostingSetService postingSetService;

	@Override
	public Response UpdateSapStatus(SecurityContext context, Collection<PstPostingSetsDTO> request) {
		String userName = securityContextUtils.getPreferredUsername(context);
		try {
			Long response = fundTransactionService.updateTransactionPostingSetStatus(request, userName);
			return Response.ok(response).build();
		} catch (Exception e) {
			logger.error("Error during update sap status " + e);
			return Response.serverError().build();
		}
	}
	
	@Override
	@Transactional
	public Response UpdateComStatus(SecurityContext context, Collection<PstPostingSetsDTO> request) {
		try {
			List<Long> response = postingSetService.updateComStatus(request);
			return Response.ok(response).build();
		} catch (Exception e) {
			logger.error("Error during update sap status " + e);
			return Response.serverError().build();
		}
	}
	
	@Override
	@Transactional
	public Response UpdateReportStatus(SecurityContext context, Collection<PstPostingSetsDTO> request) {
		try {
			List<Long> response = postingSetService.updateReportStatus(request);
			return Response.ok(response).build();
		} catch (Exception e) {
			logger.error("Error during update sap status " + e);
			return Response.serverError().build();
		}
	}

}
