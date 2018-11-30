package lu.wealins.liability.services.ws.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.BrokerProcessDTO;

/**
 * Allows filtering and processing data for commission to pay from Lissia
 * 
 * @author xqv99
 *
 */
@Path("payment-commission")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface PaymentCommissionRESTService {

	@POST
	@Path("/extract-agent-data-transfers/{code-wealins}")
	public Response extractAgentDataForTransfers(@Context SecurityContext context, @PathParam("code-wealins") String codeWealins, List<BrokerProcessDTO> brokerProcessDTO);
}
