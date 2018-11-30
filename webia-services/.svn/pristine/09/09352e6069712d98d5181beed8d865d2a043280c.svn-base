package lu.wealins.webia.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.GetAccountingPeriodResponse;

/**
 * ExtractRESTService is a REST service responsible to extract objects or data.
 * 
 * @param <T>
 *
 */
@Path("accountingPeriod")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface AccountingPeriodRESTService {

	@GET
	@Path("/getActiveAccountingPeriod")
	public GetAccountingPeriodResponse getAccountingNavByFundAndDate(@Context SecurityContext context);

}
