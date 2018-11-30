package lu.wealins.liability.services.ws.rest;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.AdditionalPremiumDTO;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.UnitByCoverageDTO;
import lu.wealins.common.dto.liability.services.UnitByFundAndCoverageDTO;


@RolesAllowed("rest-user")
@Path("/coverage")
@Produces(MediaType.APPLICATION_JSON)
public interface PolicyCoverageRESTService {
	/**
	 * Create the additional preium
	 * 
	 * @param request The policy request.
	 * @return The policy.
	 */
	@GET
	@Path("/")
	PolicyCoverageDTO loadPolicyCoverage(@Context SecurityContext ctx, @QueryParam("policyId") String policyId, @QueryParam("coverage") Integer coverage);

	/**
	 * Create the additional preium
	 * 
	 * @param request The policy request.
	 * @return The policy.
	 */
	@POST
	@Path("/additional")
	PolicyCoverageDTO createAdditionalPremium(@Context SecurityContext ctx, AdditionalPremiumDTO request);

	@GET
	@Path("/last")
	PolicyCoverageDTO getLastPolicyCoverage(@Context SecurityContext ctx, @QueryParam("policyId") String policyId);

	@GET
	@Path("/first")
	PolicyCoverageDTO getFirstPolicyCoverage(@Context SecurityContext ctx, @QueryParam("policyId") String policyId);

	@GET
	@Path("/validate")
	List<String> validateNewCoverageCreation(@Context SecurityContext ctx, @QueryParam("policyId") String policyId);

	/**
	 * Get coverages according to the policy id and the fund ids.
	 * 
	 * @param context The security context.
	 * @param polId The policy id.
	 * @param fdsIds The fund ids.
	 */
	@GET
	@Path("/funds")
	Map<String, Collection<Integer>> getCoverages(@Context SecurityContext context, @QueryParam("polId") String polId, @QueryParam("fdsIds") List<String> fdsIds);

	@GET
	@Path("/unitByCoverages")
	Collection<UnitByCoverageDTO> getUnitByCoverages(@Context SecurityContext context, @QueryParam("polId") String polId);

	@GET
	@Path("/unitByFundsAndCoverages")
	Collection<UnitByFundAndCoverageDTO> getUnitByFundsAndCoverages(@Context SecurityContext context, @QueryParam("polId") String polId);

}
