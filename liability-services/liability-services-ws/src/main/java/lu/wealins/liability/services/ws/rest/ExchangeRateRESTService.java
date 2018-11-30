package lu.wealins.liability.services.ws.rest;

import java.math.BigDecimal;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.ExchangeRateDTO;

/**
 * ExchangeRateRESTService is a REST service responsible to manipulate ExchangeRate objects.
 *
 */
@Path("exchangeRate")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface ExchangeRateRESTService {

	/**
	 * Get the exchange rate.
	 * 
	 * @param context The security context.
	 * @return The active currencies.
	 */
	@GET
	ExchangeRateDTO getExchangeRate(@Context SecurityContext context, @QueryParam("fromCurrency") String fromCurrency, @QueryParam("toCurrency") String toCurrency, @QueryParam("date") String date);

	@GET
	@Path("/convert")
	BigDecimal convert(@Context SecurityContext context, @QueryParam("amount") BigDecimal amount, @QueryParam("fromCurrency") String fromCurrency, @QueryParam("toCurrency") String toCurrency,
			@QueryParam("date") String date);
}
