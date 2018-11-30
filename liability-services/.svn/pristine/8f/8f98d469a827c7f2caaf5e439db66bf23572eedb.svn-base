package lu.wealins.liability.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.PolicyFundHoldingDTO;

@RolesAllowed("rest-user")
@Path("/policyfundholding")
@Produces(MediaType.APPLICATION_JSON)
public interface PolicyFundHoldingRESTService {

	/**
	 * Get the policy according its id.
	 * 
	 * @param context The security context.
	 * @param id The policy id.
	 */
	@GET
	@Path("/all")
	Collection<PolicyFundHoldingDTO> getHoldingsByPolicy(@Context SecurityContext context, @QueryParam("policy") String policy);


}
