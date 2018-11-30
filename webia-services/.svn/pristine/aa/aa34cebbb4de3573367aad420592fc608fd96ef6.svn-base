/**
 * 
 */
package lu.wealins.webia.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * This interface provides the functionalities related the payment slip
 * 
 * @author ORO
 *
 */
@Path("paymentCommissionSlip")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface CommissionPaymentSlipRESTService {

	/**
	 * Retrieves the commission payment slip related to the statement id given in parameter
	 * 
	 * @param statementId the statement id
	 */
	@GET
	@Path("findBy")
	Response retrievePaymentSlip(@Context SecurityContext context, @QueryParam("statementId") Long statementId);
}
