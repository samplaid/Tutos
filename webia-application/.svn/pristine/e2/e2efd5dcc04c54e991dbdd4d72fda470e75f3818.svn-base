package lu.wealins.webia.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.BrokerChangeFormDTO;

@Path("brokerChangeForm")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface LiabilityBrokerChangeFormRESTService {

	@GET
	@Path("/init")
	BrokerChangeFormDTO initBrokerChangeForm(@Context SecurityContext context, @QueryParam("policyId") String policyId, @QueryParam("workflowItemId") Integer workflowItemId);

}
