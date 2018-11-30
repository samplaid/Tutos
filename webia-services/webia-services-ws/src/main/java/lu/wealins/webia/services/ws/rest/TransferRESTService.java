package lu.wealins.webia.services.ws.rest;

import java.util.Collection;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.TransferExecutionRequest;
import lu.wealins.common.dto.webia.services.TransferRefuseDTO;

@Path("transfer")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface TransferRESTService {

	@GET
	@Path("compta")
	Collection<TransferDTO> getComptaPayments();

	@POST
	@Path("/{id}/accept")
	TransferDTO accept(@PathParam("id") Long transferId);

	@POST
	@Path("/{id}/refuse")
	@Consumes(value = MediaType.APPLICATION_JSON)
	TransferDTO refuse(@PathParam("id") Long transferId, TransferRefuseDTO refuseDTO);
	
	@POST
	@Path("/execute")
	@Consumes(value = MediaType.APPLICATION_JSON)
	Collection<TransferDTO> execute(TransferExecutionRequest transferExecutionRequest);

	@GET
	@Path("/{id}")
	TransferDTO getTransfer(@PathParam("id") Long transferId);
	
	@POST
	@Path("list")
	Collection<TransferDTO> getTransfers(List<Long> transferId);

	@POST
	@Path("update")
	Collection<TransferDTO> updateTransfers(Collection<TransferDTO> transferDTOs);

}
