package lu.wealins.webia.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import lu.wealins.common.dto.webia.services.TransactionFormDTO;

/**
 * TransactionFormRESTService is a REST service for exposing the transaction form data.
 *
 */
@Path("transactionForm")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface TransactionFormRESTService {

	@GET
	@Path("getFormData")
	TransactionFormDTO getFormData(@QueryParam("workflowItemId") Integer workflowItemId);

}
