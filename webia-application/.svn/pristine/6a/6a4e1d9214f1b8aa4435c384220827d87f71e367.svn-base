package lu.wealins.webia.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.TransactionTaxDetailsRequest;

@Path("transactionTax")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface TaxBatchRESTService {

	@POST
	@Path("details")
	@Consumes(MediaType.APPLICATION_JSON)
	Response createTransactionTaxDetails(@Context SecurityContext context);

	@POST
	@Path("createDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	Response createTransactionTaxDetails(@Context SecurityContext context, TransactionTaxDetailsRequest request);
}
