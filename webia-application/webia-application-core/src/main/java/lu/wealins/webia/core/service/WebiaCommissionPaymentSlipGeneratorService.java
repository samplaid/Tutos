/**
 * 
 */
package lu.wealins.webia.core.service;

import javax.ws.rs.core.SecurityContext;

import lu.wealins.webia.core.exceptions.CsvFileIOException;
import lu.wealins.webia.ws.rest.request.EditingRequest;

/**
 * This interface aims to provide a functionality to generate the commission payment slip
 * 
 * @author ORO
 *
 */
public interface WebiaCommissionPaymentSlipGeneratorService {

	/**
	 * Generates the commission payment slip related to the statement id in parameter.
	 * 
	 * @param statementId the statement id
	 * @return the location of the generated file
	 * @throws CsvFileIOException if the file cannot be read or write in a directory.
	 * @see #generatePaymentSlip(EditingRequest)
	 */
	String generatePaymentSlip(Long statementId);

	/**
	 * Generates the commission payment slip if an editing request has been set with the statement id.<br>
	 * This method calls the {@link #generatePaymentSlip(Long)} method by passing the statement inside the editing request object.
	 * 
	 * @param editingRequest the editing request with the statement id
	 * @return a new updated request object
	 * @throws CsvFileIOException if the file cannot be read or write in a directory.
	 * @see #generatePaymentSlip(SecurityContext, Long)
	 */
	EditingRequest generatePaymentSlip(EditingRequest editingRequest);

}
