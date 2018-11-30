package lu.wealins.liability.services.ws.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.GetProductSpecificitiesRequest;
import lu.wealins.common.dto.liability.services.ProductDTO;
import lu.wealins.common.dto.liability.services.ProductLightDTO;

/**
 * ProductRESTService is a REST service responsible to manipulate Product objects.
 *
 */
@Path("product")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface ProductRESTService {

	/**
	 * Get the active light products.
	 * 
	 * @param context The security context.
	 * @return The active light products.
	 */
	@GET
	@Path("allLights")
	List<ProductLightDTO> getActiveProductLights(@Context SecurityContext context);

	/**
	 * Get the active light products.
	 * 
	 * @param context The security context.
	 * @return The active light products.
	 */
	@GET
	@Path("{id}")
	ProductDTO getProduct(@Context SecurityContext context, @PathParam("id") String id);

	@GET
	@Path("countryCode")
	String getCountryCode(@Context SecurityContext context, @QueryParam("productId") String productId);

	@POST
	@Path("productSpecificities")
	String getProductSpecificities(@Context SecurityContext context, GetProductSpecificitiesRequest request);

}
