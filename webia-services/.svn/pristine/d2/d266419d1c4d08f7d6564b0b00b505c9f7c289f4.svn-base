package lu.wealins.webia.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import lu.wealins.common.dto.webia.services.AccountPaymentDTO;

@Path("accountPayment")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface AccountPaymentRESTService {

	@GET
	@Path("/primeAccount")
	AccountPaymentDTO getPrimeAccountPayment(@QueryParam("typeAccount") String typeAccount, @QueryParam("bic") String bic, @QueryParam("currency") String currency);

	@GET
	Collection<AccountPaymentDTO> getAccountPayments(@QueryParam("typeAccount") String typeAccount, @QueryParam("bic") String bic, @QueryParam("depositAccount") String depositAccount);
}
