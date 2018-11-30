package lu.wealins.liability.services.ws.rest;

import java.math.BigDecimal;
import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.FundTransactionDTO;
import lu.wealins.common.dto.liability.services.FundTransactionsValidationRequest;
import lu.wealins.common.dto.liability.services.FundTransactionsValuationRequest;
import lu.wealins.common.dto.liability.services.enums.FundTransactionStatus;
import lu.wealins.common.dto.liability.services.enums.TransactionCode;

@RolesAllowed("rest-user")
@Path("/fundTransaction")
@Produces(MediaType.APPLICATION_JSON)
public interface FundTransactionRESTService {

	@POST
	@Path("/fundTransactionValuation")
	@Consumes(MediaType.APPLICATION_JSON)
	Response fundTransactionValuation(
			@Context SecurityContext context, FundTransactionsValuationRequest fundTransactionsValuationRequest);

	@POST
	@Path("/executePOST_FPC")
	@Consumes(MediaType.APPLICATION_JSON)
	Response executePOST_FPC(@Context SecurityContext context);

	@POST
	@Path("/executePRE_FPC")
	@Consumes(MediaType.APPLICATION_JSON)
	Response executePRE_FPC(@Context SecurityContext context);

	@GET
	@Path("/fundTransactions")
	Collection<FundTransactionDTO> getFundTransactions(@Context SecurityContext context, @QueryParam("policyId") String policyId, @QueryParam("fundId") String fundId,
			@QueryParam("eventType") TransactionCode eventType,
			@QueryParam("status") FundTransactionStatus status);


	@POST
	@Path("/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	Collection<String> validate(FundTransactionsValidationRequest request);

	@GET
	@Path("/validatePosted")
	Collection<String> validatePosted(@QueryParam("policyId") String policyId, @QueryParam("coverage") Integer coverage);

	@GET
	@Path("/validatePostedForWithdrawal")
	Collection<String> validatePostedForWithdrawal(@QueryParam("policyId") String policyId, @QueryParam("effectiveDate") String effectiveDate);

	@GET
	@Path("/validatePostedForSurrender")
	Collection<String> validatePostedForSurrender(@QueryParam("policyId") String policyId, @QueryParam("effectiveDate") String effectiveDate);

	@GET
	@Path("/validateAwaitingStatus")
	Collection<String> validateAwaitingFundTransactions(@QueryParam("policyId") String policyId, @QueryParam("effectiveDate") String effectiveDate);

	@GET
	@Path("/fundTransactionAmount")
	BigDecimal getFundTransactionAmount(@Context SecurityContext context,
			@QueryParam("policyId") String policyId,
			@QueryParam("TransactionTaxEventType") String TransactionTaxEventType,
			@QueryParam("effectiveDate") String effectiveDate);

}
