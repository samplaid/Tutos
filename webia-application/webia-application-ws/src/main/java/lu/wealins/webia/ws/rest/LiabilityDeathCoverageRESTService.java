package lu.wealins.webia.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.DeathCoverageClauseDTO;
import lu.wealins.common.dto.liability.services.DeathCoverageClausesDTO;

@Path("deathCoverage")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface LiabilityDeathCoverageRESTService {

	@GET
	@Path("/{productCd}")
	DeathCoverageClausesDTO getDeathCoverageClauses(@PathParam(value = "productCd") String productCd);

	/**
	 * Get the death coverage text for a given policy It search the death coverage subscribe (DTHCAL) and add the label from webia APPLI_PARAM
	 * 
	 * @param context The security context.
	 * @param polId The policy Id
	 * @return the death coverage.
	 */
	@GET
	@Path("policy")
	@Consumes(MediaType.APPLICATION_JSON)
	DeathCoverageClauseDTO getPolicyDeathCoverage(@Context SecurityContext context, @QueryParam("id") String polId);
}
