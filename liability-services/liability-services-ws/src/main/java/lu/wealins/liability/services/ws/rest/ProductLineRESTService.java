package lu.wealins.liability.services.ws.rest;

import java.util.Collection;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.ProductLineDTO;

@Path("productLine")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface ProductLineRESTService {

	/**
	 * Get product lines according to the product id and the control types.
	 * 
	 * @param prdId The product id.
	 * @param controls The control types.
	 * @return The product lines.
	 */
	@GET
	public Collection<ProductLineDTO> getProductLines(@QueryParam("prdId") String prdId, @QueryParam("controls") List<String> controls);

	@GET
	@Path("/filter/")
	public Collection<ProductLineDTO> getFilteredProductLines(@QueryParam("prdId") String prdId, @QueryParam("controls") List<String> controls);

	/**
	 * Get product line according to the product line id.
	 * 
	 * @param context The security context.
	 * @param prlId The product line id.
	 * @return The product line
	 */
	@GET
	@Path("/get/{prlId}")
	ProductLineDTO getProductLine(@Context SecurityContext context, @PathParam("prlId") String prlId);

}
