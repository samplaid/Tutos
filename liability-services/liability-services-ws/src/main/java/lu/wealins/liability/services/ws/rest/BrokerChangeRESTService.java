package lu.wealins.liability.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.AdminFeeChangeDTO;
import lu.wealins.common.dto.liability.services.AmendmentRequest;
import lu.wealins.common.dto.liability.services.BrokerChangeDTO;

@RolesAllowed("rest-user")
@Path("/brokerChange")
@Produces(MediaType.APPLICATION_JSON)
public interface BrokerChangeRESTService {

	@POST
	@Path("/initBrokerChange")
	BrokerChangeDTO initBrokerChange(@Context SecurityContext context, AmendmentRequest brokerChangeRequest);

	@POST
	@Path("/update")
	BrokerChangeDTO updateBrokerChange(@Context SecurityContext context, BrokerChangeDTO brokerChangeDTO);

	@POST
	@Path("/applyChange")
	BrokerChangeDTO applyChangeToPolicy(@Context SecurityContext context, BrokerChangeDTO brokerChangeDTO);

	@POST
	@Path("/applyChangeToAdminFees")
	BrokerChangeDTO applyChangeToAdminFees(@Context SecurityContext context, BrokerChangeDTO brokerChange);

	@POST
	@Path("/adminFeeChanges")
	Collection<AdminFeeChangeDTO> getAdminFeeChanges(@Context SecurityContext context, BrokerChangeDTO brokerChange);

	@GET
	@Path("/load")
	BrokerChangeDTO getBrokerChange(@Context SecurityContext context, @QueryParam("workflowItemId") Integer workflowItemId);

	@GET
	@Path("/loadBefore")
	BrokerChangeDTO getBrokerChangeBefore(@Context SecurityContext context, @QueryParam("workflowItemId") Integer workflowItemId);
	
	@POST
	@Path("/adminFeesChanged")
	Boolean hasAdminFeesChanged(@Context SecurityContext context, BrokerChangeDTO brokerChangeDTO);
}
