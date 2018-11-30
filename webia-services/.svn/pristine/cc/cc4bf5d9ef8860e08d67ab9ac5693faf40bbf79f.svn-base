/**
 * 
 */
package lu.wealins.webia.services.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.webia.services.core.service.AcceptanceReportService;
import lu.wealins.webia.services.ws.rest.AcceptanceReportRESTService;
import lu.wealins.common.dto.webia.services.AcceptanceReportDTO;

/**
 * This implements the interface {@link AcceptanceReportRESTService} to realize the acceptance report.
 * 
 * @author oro
 *
 */
@Component
public class AcceptanceReportRESTServiceImpl implements AcceptanceReportRESTService {

	@Autowired
	private AcceptanceReportService acceptanceReportGeneratorService;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public List<AcceptanceReportDTO> retrieveAcceptanceReport(SecurityContext context, int workflowId) {
		return acceptanceReportGeneratorService.retrieveAcceptanceReport(workflowId);
	}


}
