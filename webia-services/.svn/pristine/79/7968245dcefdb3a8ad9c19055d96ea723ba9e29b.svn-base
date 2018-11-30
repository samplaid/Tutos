package lu.wealins.webia.services.ws.rest;

import java.util.Collection;
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

import lu.wealins.common.dto.webia.services.ReportingComDTO;
import lu.wealins.common.dto.webia.services.TransactionDTO;

/**
 * LissiaExtractRESTService is a REST service responsible to extract objects or data originally from Lissia.
 * 
 * @param <T>
 *
 */
@Path("lissia-extract")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface LissiaExtractRESTService {

	/**
	 * Insert transactions into SAP Accounting and get pstIds
	 * @param context The security context.
	 * @param List of transactions.
	 * @return
	 */
	@POST
	@Path("/insert-sap-accounting")
	public Response insertIntoSapAccounting(@Context SecurityContext context, Collection<TransactionDTO> transactionDTOs);

	/**
	 * Delete transactions into SAP Accounting 
	 * @param context The security context.
	 * @param List of ids to be removed.
	 * @return
	 */
	@POST
	@Path("/delete-sap-accounting")
	public Response removeFromSapAccounting(@Context SecurityContext context, List<Long> sapAccIds);
	
	/**
	 * Insert transactions into Commission To Pay
	 * 
	 * @param context The security context.
	 * @return List of transactions.
	 */
	@POST
	@Path("/commission-to-pay")
	public Response insertCommissionToPay(@Context SecurityContext context, Collection<TransactionDTO> transactionDTOs);
	
	/**
	 * Insert transactions into Reporting Com
	 * 
	 * @param context The security context.
	 * @return List of transactions.
	 */
	@POST
	@Path("/reporting-com/{reportId}")
	public Response insertReportingCom(@Context SecurityContext context, Collection<TransactionDTO> transactionDTOs, @PathParam("reportId") Long reportId);
	
	/**
	 * Insert transactions external funds 132 into Reporting Com
	 * 
	 * @param context The security context.
	 * @return List of transactions.
	 */
	@POST
	@Path("/reporting-com-external-132/{reportId}")
	public Response insertReportingComExternal132(@Context SecurityContext context, Collection<ReportingComDTO> reportingComDTO, @PathParam("reportId") Long reportId);
	
	/**
	 * Insert transactions external funds 133 into Reporting Com
	 * 
	 * @param context The security context.
	 * @return List of transactions.
	 */
	@POST
	@Path("/reporting-com-external-133/{reportId}")
	public Response insertReportingComExternal133(@Context SecurityContext context, Collection<ReportingComDTO> reportingComDTO, @PathParam("reportId") Long reportId);

}
