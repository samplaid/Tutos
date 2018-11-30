package lu.wealins.webia.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.TaxBatchResponse;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsRequest;


/**
 * Rest service for managing transaction tax values.
 */
@Path("transactionTax")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface TaxBatchRESTService {
	/**
	 * Get the new transaction taxes.
	 * 
	 * @param context the security context
	 * @param transactionTaxDetails the request used to save the transaction tax details.
	 * @return a {@link Response}.
	 */
	@POST
	@Path("details")
	@Consumes(MediaType.APPLICATION_JSON)
	TaxBatchResponse createTransactionTaxDetails(@Context SecurityContext context);

	@POST
	@Path("createDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	TaxBatchResponse createTransactionTaxDetails(@Context SecurityContext context,
			TransactionTaxDetailsRequest request);


}
