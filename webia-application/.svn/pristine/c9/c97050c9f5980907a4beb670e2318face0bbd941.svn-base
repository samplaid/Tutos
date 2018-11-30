/**
 * 
 */
package lu.wealins.webia.core.service;

import javax.ws.rs.core.SecurityContext;

import lu.wealins.webia.ws.rest.request.EditingRequest;

/**
 * This interface provides method to allow generate the acceptance report. <br>
 * It uses the check data and check step to retrieve the data based on the workflow id.
 * 
 * @author oro
 *
 */
public interface AcceptanceReportGeneratorService {

	/**
	 * Generates the acceptance report based on the request passed in paraneter
	 * 
	 * @param context the security context
	 * @param request the document generation request.
	 * @return The generation response. Must not be null
	 */
	EditingRequest generateDocumentAcceptanceReport(SecurityContext context, EditingRequest request);
}
