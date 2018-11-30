package lu.wealins.liability.services.ws.rest;

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
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.FundPriceDTO;
import lu.wealins.common.dto.liability.services.FundPriceSearchRequest;
import lu.wealins.common.dto.liability.services.SearchResult;

/**
 * FundPriceRESTService is a REST service responsible to manipulate fund price objects.
 *
 */
@Path("fundPrice")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface FundPriceRESTService {

	/**
	 * Fund price search service. The fund id is mandatory.
	 * 
	 * The search request and returned page are one base indexed, the first page's number is one.
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	SearchResult<FundPriceDTO> search(@Context SecurityContext context, FundPriceSearchRequest request);

	@GET
	@Path("/{id}/count")
	int countFundPrice(@Context SecurityContext context, @PathParam("id") String fundId);

	@GET
	@Path("/exists")
	Boolean existsForFund(@Context SecurityContext context, @QueryParam("fundId") String fundId);

	/**
	 * Get fund prices according its fund id and the price date.
	 * 
	 * @param context The security context.
	 * @param fundId The fund id.
	 * @param priceDate The price date.
	 * @return The fund prices.
	 */
	@GET
	@Path("/fundPrices")
	List<FundPriceDTO> getFundPrices(@Context SecurityContext context, @QueryParam("fundId") String fundId, @QueryParam("priceDate") String priceDate);

	@GET
	@Path("/minFundPrice/{fundId}")
	FundPriceDTO getMinFundPrice(@Context SecurityContext context, @PathParam("fundId") String fundId);

	@GET
	@Path("/lastFundPriceBefore")
	FundPriceDTO getLastFundPricesBefore(@Context SecurityContext context, @QueryParam("fundId") String fundId, @QueryParam("valuationDate") String valuationDate, @QueryParam("priceType") int type);
}
