/**
 * 
 */
package lu.wealins.webia.services.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.webia.services.core.service.CommissionPaymentSlipService;
import lu.wealins.webia.services.ws.rest.CommissionPaymentSlipRESTService;

/**
 * Default implementation of the {@link CommissionPaymentSlipRESTService}
 * 
 * @author ORO
 *
 */
@Component
public class CommissionPaymentSlipRESTServiceImpl implements CommissionPaymentSlipRESTService {

	@Autowired
	private CommissionPaymentSlipService commissionPaymentSlipService;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Response retrievePaymentSlip(SecurityContext context, Long statementId) {
		List<TransferDTO> paymentSlip = commissionPaymentSlipService.retrievePaymentSlip(statementId);
		return Response.ok(paymentSlip).build();
	}

	
}
