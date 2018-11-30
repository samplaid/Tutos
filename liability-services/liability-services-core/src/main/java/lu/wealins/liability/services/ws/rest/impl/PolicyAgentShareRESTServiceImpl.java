package lu.wealins.liability.services.ws.rest.impl;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.PolicyAgentShareDTO;
import lu.wealins.liability.services.core.business.PolicyAgentShareService;
import lu.wealins.liability.services.core.persistence.repository.PolicyAgentShareRepository;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.ws.rest.PolicyAgentShareRESTService;

@Component
public class PolicyAgentShareRESTServiceImpl implements PolicyAgentShareRESTService {
	private static final Logger logger = LoggerFactory.getLogger(PolicyAgentShareRESTServiceImpl.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * PolicyAgentShare repository
	 */
	@Autowired
	private PolicyAgentShareRepository policyAgentShareRepository;
	
	@Autowired
	private PolicyAgentShareService policyAgentShareService;

	@Autowired
	private CalendarUtils calendarUtils;

	/* (non-Javadoc)
	 * @see lu.wealins.liability.services.ws.rest.PolicyAgentShareRESTService#findByAgentAndTypeAndCoverage(javax.ws.rs.core.SecurityContext, java.lang.String, int, int)
	 */
	@Override
	public Response findByAgentAndTypeAndCoverage(SecurityContext context, String broker, Integer type, Integer coverage) { 
		
		try{
			logger.info("Find policy agent share with agent {}, type {}, coverage {}", broker, type, coverage);
			Collection<String> response = policyAgentShareRepository.findByAgentAndTypeAndCoverageExternalFunds(broker, type, coverage);
			logger.info("Found {} PolicyAgentShare.", response.size());
			return Response.ok(response).build();
		} catch (Exception e) {
			logger.error("Error during find policy agent share: {}", e);
			return Response.serverError().build();
		}
		
	}

	@Override
	public Collection<PolicyAgentShareDTO> getPolicyAgentShares(SecurityContext context, String polId, Integer type, String category, Integer coverage) {
		return policyAgentShareService.getPolicyAgentShares(polId, type, category, coverage, null);
	}
	
	@Override
	public PolicyAgentShareDTO getBrokerEntryFees(SecurityContext context, String polId) {
		Assert.notNull(polId);
		return policyAgentShareService.getBrokerEntryFees(polId);
	}

	@Override
	public PolicyAgentShareDTO getLastOperationBrokerEntryFees(SecurityContext context, String polId) {
		Assert.notNull(polId);
		return policyAgentShareService.getLastOperationBrokerEntryFees(polId);
	}

	@Override
	public PolicyAgentShareDTO getLastOperationBrokerAdminFees(SecurityContext context, String polId) {
		Assert.notNull(polId);
		return policyAgentShareService.getLastOperationBrokerAdminFees(polId);
	}

	@Override
	public PolicyAgentShareDTO getPreviousBroker(SecurityContext context, String polId, String effectiveDate) {

		Date date = calendarUtils.createDate(effectiveDate);
		return policyAgentShareService.getPreviousBroker(polId, date);
	}
}
