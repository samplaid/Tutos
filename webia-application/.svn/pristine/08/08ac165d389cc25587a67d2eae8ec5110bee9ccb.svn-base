package lu.wealins.webia.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.FundDTO;

@Path("fund")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface LiabilityFundRESTService {

	/**
	 * Get a fund according its id.
	 * 
	 * @param context The security context.
	 * @param id The fund id.
	 * @return The Fund. Return a <code>ObjectNotFoundException</code> if the fund does not exist.
	 */
	@GET
	@Path("one")
	FundDTO getFund(@Context SecurityContext context, @QueryParam("id") String id);

	/**
	 * Update an existing fund with the WSSUPDFDS soap service. The principal in the security context will be used as the update user of the fund.
	 * 
	 * Fund to be updated must exist in the database, otherwise the method will throw an exception. All the values of the fund will be replaced with the data in the DTO parameter.
	 * 
	 * @param context The security context.
	 * @param fund The fund.
	 * @return The updated fund.
	 */
	@POST
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON)
	FundDTO update(@Context SecurityContext context, FundDTO fund);

	/**
	 * Create a new fund with the WSSUPDFDS soap service. The principal in the security context will be used as the update user of the fund.
	 * 
	 * @param context The security context.
	 * @param fund The fund.
	 * @return The created fund.
	 */
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	FundDTO create(@Context SecurityContext context, FundDTO fund);

}
