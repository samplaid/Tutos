package lu.wealins.liability.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.PolicyAgentShareDTO;

/**
 * 
 * @author xqv99
 *
 */
@Path("policyagentshare")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface PolicyAgentShareRESTService {
	/**
	 * Get policy agent share with broker, type and coverage.
	 * @param context
	 * @param broker
	 * @param type
	 * @param coverage
	 * @return
	 */
	@GET
	@Path("/findby-agent-type-coverage")
	Response findByAgentAndTypeAndCoverage(@Context SecurityContext context, @QueryParam("broker") String broker, @QueryParam("type") Integer type, @QueryParam("coverage") Integer coverage);

	@GET
	Collection<PolicyAgentShareDTO> getPolicyAgentShares(@Context SecurityContext context, @QueryParam("polId") String polId, @QueryParam("type") Integer type,
			@QueryParam("category") String category, @QueryParam("coverage") Integer coverage);

	@GET
	@Path("/brokerEntryFees")
	PolicyAgentShareDTO getBrokerEntryFees(@Context SecurityContext context, @QueryParam("polId") String polId);

	@GET
	@Path("/lastBrokerEntryFees")
	PolicyAgentShareDTO getLastOperationBrokerEntryFees(@Context SecurityContext context, @QueryParam("polId") String polId);

	@GET
	@Path("/lastBrokerAdminFees")
	PolicyAgentShareDTO getLastOperationBrokerAdminFees(@Context SecurityContext context, @QueryParam("polId") String polId);

	@GET
	@Path("/previousBroker")
	PolicyAgentShareDTO getPreviousBroker(@Context SecurityContext context, @QueryParam("polId") String polId, @QueryParam("effectiveDate") String effectiveDate);
}
