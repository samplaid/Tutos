/**
* 
*/
package lu.wealins.webia.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.ClientRoleActivationFlagDTO;

@Path("clientRoleActivation")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface ClientRoleActivationResolverRESTService {

	@GET
	@Path("solveActivation/countryCode")
	ClientRoleActivationFlagDTO solveBeneficiaryRoleActivation(@Context SecurityContext context, @QueryParam("countryCode") String countryCode);

	@GET
	@Path("phActivation/countryCode")
	ClientRoleActivationFlagDTO solvePolicyHolderRoleActivation(@Context SecurityContext context,
			@QueryParam("countryCode") String countryCode,
			@QueryParam("productCapi") boolean productCapi,
			@QueryParam("yearTerm") boolean yearTerm);
}
