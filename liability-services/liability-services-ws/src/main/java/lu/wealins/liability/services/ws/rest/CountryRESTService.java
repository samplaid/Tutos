package lu.wealins.liability.services.ws.rest;

import java.util.Collection;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.CountryDTO;

/**
 * CountryRESTService is a REST service responsible to manipulate Country objects.
 *
 */
@Path("country")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface CountryRESTService {

	/**
	 * Get the active countries.
	 * 
	 * @param context The security context.
	 * @return The active countries.
	 */
	@GET
	@Path("all")
	List<CountryDTO> getActiveCountries(@Context SecurityContext context);
	
	
	/**
	 * Get a countr by the code.
	 * 
	 * @param context The security context.
	 * @param code
	 * @return The active countries.
	 */
	@GET
	@Path("/{ctyId}")
	CountryDTO getCountry(@Context SecurityContext context, @PathParam("ctyId") String ctyId);

	/**
	 * Get a countries oh policy holders.
	 * 
	 * @param context
	 *            The security context.
	 * @param policyId
	 *            the Id of policy
	 * @return The isoCode2 countries.
	 */
	@GET
	@Path("/PolicyCountry")
	Collection<String> getPolicyCountry(@Context SecurityContext context, @QueryParam("policyId") String policyId);

}
