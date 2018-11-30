/**
 * 
 */
package lu.wealins.webia.services.ws.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.ActivableRoleBasedCountry;


@Path("clientRoleActivation")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface ClientRoleActivationResolverRESTService {

	@GET
	@Path("solveActivation/countryCode")
	List<? extends ActivableRoleBasedCountry> solveActivation(@Context SecurityContext context, @QueryParam("countryCode") String countryCode);

	@GET
	@Path("phActivation/countryCode")
	List<? extends ActivableRoleBasedCountry> solvePolicyHolderRoleActivation(@Context SecurityContext context, @QueryParam("countryCode") String countryCode,
			@QueryParam("productCapi") boolean productCapi, @QueryParam("yearTerm") boolean yearTerm);
}
