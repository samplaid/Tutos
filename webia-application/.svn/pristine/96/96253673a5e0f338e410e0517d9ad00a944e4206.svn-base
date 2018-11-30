package lu.wealins.webia.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Path("withdrawal")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface WithdrawalFormRESTService {

	@GET
	@Path("/init")
	TransactionFormDTO initWithdrawalForm(@Context SecurityContext context, @QueryParam("policyId") String policyId, @QueryParam("workflowItemId") Integer workflowItemId);

	@GET
	@Path("/createEnoughCashEditings")
	Collection<EditingRequest> createEnoughCashEditings(@Context SecurityContext context, @QueryParam("workflowItemId") Integer workflowItemId);

	@GET
	@Path("/generateEnoughCashDocument")
	@Produces("application/pdf")
	public Response generateEnoughCashDocument(@Context SecurityContext context, @QueryParam("editingRequestId") Long editingRequestId);

}
