package lu.wealins.webia.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.SepaDocumentRequest;
import lu.wealins.common.dto.webia.services.SurrenderTransferFormDataDTO;
import lu.wealins.common.dto.webia.services.TransferComptaDTO;
import lu.wealins.common.dto.webia.services.TransferExecutionRequest;
import lu.wealins.common.dto.webia.services.TransferRefuseDTO;
import lu.wealins.common.dto.webia.services.WithdrawalTransferFormDataDTO;

@Path("/transfer")
@RolesAllowed("rest-user")
public interface TransferRESTService {
	
	@GET
	@Path("/withdrawal/initFormData")
	WithdrawalTransferFormDataDTO initFormDataForWithdrawal(@QueryParam("workflowItemId") Integer workflowItemId);

	@GET
	@Path("/surrender/initFormData")
	SurrenderTransferFormDataDTO initFormDataForSurrender(@QueryParam("workflowItemId") Integer workflowItemId);

	@POST
	@Path("/executeFax")
	@Consumes(value = MediaType.APPLICATION_JSON)
	Collection<TransferComptaDTO> executeFax(@Context SecurityContext context, TransferExecutionRequest transferExecutionRequest);
	
	@POST
	@Path("/executeSepa")
	@Consumes(value = MediaType.APPLICATION_JSON)
	Collection<TransferComptaDTO> executeSepa(@Context SecurityContext context,
			SepaDocumentRequest sepaDocumentRequest);

	@POST
	@Path("/{id}/accept")
	Collection<TransferComptaDTO> accept(@PathParam("id") Long transferId);

	@POST
	@Path("/{id}/refuse")
	@Consumes(value = MediaType.APPLICATION_JSON)
	TransferComptaDTO refuse(@PathParam("id") Long transferId, TransferRefuseDTO refuseDTO);

	@GET
	@Path("compta")
	Collection<TransferComptaDTO> getComptaPayments();
	
	
	@POST
	@Path("/executeCsv")
	@Consumes(value = MediaType.APPLICATION_JSON)
	Collection<TransferComptaDTO> executeCsv(@Context SecurityContext context, TransferExecutionRequest transferExecutionRequest);

}
