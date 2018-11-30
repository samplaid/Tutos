package lu.wealins.liability.services.ws.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.CurrencyDTO;


/**
 * CurrencyRESTService is a REST service responsible to manipulate Currency objects.
 *
 */
@Path("currency")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface CurrencyRESTService {

	/**
	 * Get the active currencies.
	 * 
	 * @param context The security context.
	 * @return The active currencies.
	 */
	@GET
	@Path("all")
	List<CurrencyDTO> getActiveCurrencies(@Context SecurityContext context);

}
