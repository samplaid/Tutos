package lu.wealins.webia.services.ws.rest;

import java.util.Collection;

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

import lu.wealins.common.dto.webia.services.PageResult;
import lu.wealins.common.dto.webia.services.ReportingComDTO;
import lu.wealins.common.dto.webia.services.SapAccountingDTO;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderFIDRequest;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderRequest;

/**
 * ExtractRESTService is a REST service responsible to extract objects or data.
 * 
 * @param <T>
 *
 */
@Path("extract")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface ExtractRESTService {

	/**
	 * Extract and create file of Sap Accounting
	 * 
	 * @param context The security context.
	 * @param origin of the datas.
	 * @return List of Sap Accounting.
	 */
	@GET
	@Path("/sap-accounting/{origin}/pagination")
	public PageResult<SapAccountingDTO> extractSapAccountingPagination(@Context SecurityContext context, @PathParam("origin") String origin, @QueryParam("page") int page, @QueryParam("size") int size, @QueryParam("lastId") Long lastId);
	
	/**
	 * Confirm list ids of SAP ACCOUNTING and update export date
	 * 
	 * @param context The security context.
	 * @param origin of the datas.
	 * @return List of Sap Accounting.
	 */
	@POST
	@Path("/sap-accounting/confirm")
	public Response confirmExtractSapAccounting(@Context SecurityContext context, Collection<Long> successIds);
	
	/**
	 * Extract and create file of Reporting Com
	 * 
	 * @param context The security context.
	 * @param origin of the datas.
	 * @return List of Reporting Com.
	 */
	@GET
	@Path("/reporting-com/pagination")
	public PageResult<ReportingComDTO> extractReportingComPagination(@Context SecurityContext context, @QueryParam("page") int page, @QueryParam("size") int size, @QueryParam("lastId") Long lastId, @QueryParam("reportId") Long reportId);
	
	/**
	 * Confirm list ids of Reporting Com and update export date
	 * 
	 * @param context The security context.
	 * @param origin of the datas.
	 * @return List of Reporting Com.
	 */
	@POST
	@Path("/reporting-com/confirm")
	public Response confirmExtractReportingCom(@Context SecurityContext context, Collection<Long> successIds);
	
	/**
	 * Extract the order from DALI and LISSIA
	 * 
	 * @param context the context
	 * @return the ExtractOrderResponse
	 */
	@GET
	@Path("/order")
	public Response extractOrder(@Context SecurityContext context);

	/**
	 * Save the order from LISSIA
	 * 
	 * @param context the context
	 * @param saveLissiaOrderRequest the save lissia order request
	 * @return the ExtractOrderResponse
	 */
	@POST
	@Path("/save")
	public Response saveLissiaOrder(@Context SecurityContext context, SaveLissiaOrderRequest saveLissiaOrderRequest);
	
	
	/**
	 * Extract the order FID from LISSIA
	 * 
	 * @param context the context
	 * @return the ExtractOrderFIDResponse
	 */
	@GET
	@Path("/orderFID")
	public Response extractOrderFID(@Context SecurityContext context);

	/**
	 * Save the order FID from LISSIA
	 * 
	 * @param context the context
	 * @param saveLissiaOrderFIDRequest the save lissia orderFID request
	 * @return the ExtractOrderFIDResponse
	 */
	@POST
	@Path("/saveFID")
	public Response saveLissiaOrderFID(@Context SecurityContext context, SaveLissiaOrderFIDRequest saveLissiaOrderFIDRequest);
	
}
