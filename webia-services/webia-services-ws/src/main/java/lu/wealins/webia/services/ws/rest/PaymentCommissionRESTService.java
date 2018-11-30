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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.AgentDataForTransfersResponse;
import lu.wealins.common.dto.liability.services.BrokerProcessDTO;
import lu.wealins.common.dto.webia.services.AgentIdListResponse;
import lu.wealins.common.dto.webia.services.CommissionToPayWrapperDTO;
import lu.wealins.common.dto.webia.services.PageResult;
import lu.wealins.common.dto.webia.services.StatementWrapperDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.TransferWrapperDTO;

/**
 * PaymentCommissionRESTService is a REST service to handle commission to pay services.
 * 
 * @param <T>
 *
 */
@Path("payment-commission")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface PaymentCommissionRESTService {
	
	/**
	 * create new statement and select available broker
	 * 
	 * @param context The security context.
	 * @param txpe of commission
	 * @return List of commission to pay.
	 */
	@POST
	@Path("/get-available-broker")
	public List<BrokerProcessDTO> getAvailableBrokerForCommission(@Context SecurityContext context, StatementWrapperDTO statementWrapperDTO);
	
	/**
	 * Select available broker for create transfers
	 * 
	 * @param context The security context.
	 * @param agentDataForTransfersResponse of commission
	 * @return List of transfer wrapper
	 */
	@POST
	@Path("/post-available-broker-for-create-transfers")
	public TransferWrapperDTO postAvailableBrokerForCreateTransfers(@Context SecurityContext context, AgentDataForTransfersResponse agentDataForTransfersResponse);

	
	/**
	 * extract not transfered commission to pay by type
	 * 
	 * @param context The security context.
	 * @param txpe of commission
	 * @return List of commission to pay.
	 */
	@GET
	@Path("/not-transfered-commission")
	PageResult<CommissionToPayWrapperDTO> extractNotTransferedCommissionsToPayByType(@Context SecurityContext context, @QueryParam("comType") String comType, @QueryParam("page") int page,
			@QueryParam("size") int size);

	/**
	 * Insert new transfer commission
	 * 
	 * @param context
	 * @param transferDTOs
	 * @return List of transfers with ID
	 */
	@POST
	@Path("/transfers")
	public Response insertTransfers(@Context SecurityContext context, Collection<TransferDTO> transferDTOs);
	
	/**
	 * Update commission to pay transfer_id field in webia DB
	 * 
	 * @param context
	 * @param List of Wrapper for commission to pay
	 * @return Response
	 */
	@POST
	@Path("/update-commission-transfer-id")
	public Response updateCommissionToPay(@Context SecurityContext context, Collection<CommissionToPayWrapperDTO> commissionDTOs);

	/**
	 * export commissions to pay ready for generate statement
	 * 
	 * @param context The security context.
	 * @param transfertId of commission
	 * @return List of commission to pay.
	 */
	@GET
	@Path("/export-commissions-to-pay-ready-for-generate-statement/{transfertId}/")
	CommissionToPayWrapperDTO exportCommissionsToPayReadyForGenerateStatement(@Context SecurityContext context, @PathParam("transfertId") Long transfertId);
	
	
	/**
	 * export transfer ready for generate statement
	 * 
	 * @param context The security context.
	 * @param agentId of commission
	 * @return List of transfer.
	 */
	@GET
	@Path("/export-transfers-ready-for-generate-statement/{statementId}/{agentId}/")
	TransferWrapperDTO exportTransferReadyForGenerateStatement(@Context SecurityContext context, @PathParam("statementId") Long statementId, @PathParam("agentId") String agentId);

	/**
	 * distinct transfers broker by statement
	 * 
	 * @param context The security context.
	 * @param statementId of commission
	 * @return List of transfer.
	 */
	@GET
	@Path("/distinct-transfers-broker-by-statement/{statementId}/")
	AgentIdListResponse getDistinctTransfersByStatement(@Context SecurityContext context, @PathParam("statementId") Long statementId);
	
}
