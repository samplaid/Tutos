/**
 * 
 */
package lu.wealins.webia.services.core.service;

import java.util.List;

import lu.wealins.common.dto.webia.services.AcceptanceReportDTO;

/**
 * This interface provides method to allow generate the acceptance report. <br>
 * It uses the check data and check step to retrieve the data based on the workflow id.
 * 
 * @author oro
 *
 */
public interface AcceptanceReportService {

	/**
	 * Retrieve the acceptance report related to the workflow id.
	 * 
	 * @param workflowId the workflow id
	 * @return a list of acceptance report data.
	 */
	List<AcceptanceReportDTO> retrieveAcceptanceReport(int workflowId);
}
