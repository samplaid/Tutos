package lu.wealins.webia.services.ws.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.SurrenderReportResultDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxResponse;
import lu.wealins.common.dto.webia.services.TransactionTaxSendingDTO;

/**
 * StatementRESTService is a REST service to handle statement services.
 * 
 * @param <T>
 *
 */
@Path("transactiontax")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface TransactionTaxRESTService {

	@GET
	@Path("get")
	TransactionTaxDTO getTransactionTax(@Context SecurityContext context, @QueryParam("id") Long id);

	@POST
	@Path("insert")
	TransactionTaxResponse insertTransactionTax(@Context SecurityContext context, TransactionTaxResponse request);

	@POST
	@Path("cancel")
	TransactionTaxResponse cancelTransactionTax(@Context SecurityContext context, TransactionTaxResponse request);

	@GET
	@Path("transactionTaxDetails")
	List<TransactionTaxDetailsDTO> getTransactionTaxDetails(@Context SecurityContext context, @QueryParam("transactionTaxId") Long transactionTaxId);

	@GET
	@Path("/transactionTaxByPolicy")
	List<TransactionTaxDTO> getTransactionTaxByPolicy(@Context SecurityContext context,
			@QueryParam("policyId") String policyId);

	@GET
	@Path("/originId")
	TransactionTaxDTO getTransactionTaxByOriginId(@Context SecurityContext context, @QueryParam("originId") Long id);

	@GET
	@Path("/resultDetails")
	SurrenderReportResultDTO getTransactionTaxReportResultDetails(@Context SecurityContext context,
			@QueryParam("transactionId") Long id);

	@GET
	@Path("/TransactionTaxSending")
	TransactionTaxSendingDTO getTransactionTaxSending(@Context SecurityContext context,
			@QueryParam("transactionTaxId") Long id);

}
