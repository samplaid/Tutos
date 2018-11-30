package lu.wealins.liability.services.ws.rest;

import java.text.ParseException;

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

import lu.wealins.common.dto.liability.services.UpdateOrderRequest;
import lu.wealins.common.dto.liability.services.ValorizedTransactionSearchRequest;

@Path("extract")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface ExtractOrderRESTService {

	/**
	 * 
	 * Extract the estimated and valorized orders
	 * 
	 * @param context the context
	 * @param page the page(used for the pagination)
	 * @param size size of element in the current page
	 * @param valorizedId the last valorized order id found
	 * @param estimatedId the last estimated order id found
	 * @return the ExtractOrderResponse
	 */
	@GET
	@Path("/order")
	public Response extractOrder(@Context SecurityContext context, @QueryParam("page") int page, @QueryParam("size") int size, @QueryParam("valorizedId") Long valorizedId,
			@QueryParam("estimatedId") Long estimatedId);

	/**
	 * Update flag of estimated and valorized orders
	 * 
	 * @param context the context
	 * @param updateOrderRequest the order to update
	 * @return the UpdateOrderResponse
	 */
	@POST
	@Path("/update")
	public Response updateOrder(@Context SecurityContext context, UpdateOrderRequest updateOrderRequest);
	
	/**
	 * 
	 * @param context
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/valorizedOrdersFID")
	public Response getValorizedTransactions(@Context SecurityContext context, ValorizedTransactionSearchRequest request) throws ParseException;


}
