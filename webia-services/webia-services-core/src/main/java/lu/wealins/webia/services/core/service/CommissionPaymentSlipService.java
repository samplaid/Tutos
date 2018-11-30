/**
 * 
 */
package lu.wealins.webia.services.core.service;

import java.util.List;

import lu.wealins.common.dto.webia.services.TransferDTO;

/**
 * This interface aims to provide a functionality related the commission payment slip
 * 
 * @author ORO
 *
 */
public interface CommissionPaymentSlipService {

	/**
	 * Retrieves the payment slip related to the statement id
	 * 
	 * @param statementId the statement id
	 * @return a set of commission slips
	 */
	List<TransferDTO> retrievePaymentSlip(Long statementId);

}
