package lu.wealins.liability.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.PolicyEntryFeesDto;
import lu.wealins.common.dto.liability.services.ProductValueDTO;
import lu.wealins.common.dto.liability.services.ProductValues;

/**
 * ProductValueRESTService is a REST service responsible to manipulate Product Value objects.
 *
 */
@Path("productValue")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface ProductValueRESTService {

	/**
	 * Get the death coverage available for a given product.
	 * 
	 * @param context The security context.
	 * @param prdId The product id.
	 * @return a collection of the death coverage available.
	 */
	@GET
	@Path("deathCoverage/{prdId}")
	ProductValues getDeathCoverage(@Context SecurityContext context, @PathParam("prdId") String prdId);

	/**
	 * Get the death coverage available for a given product.
	 * 
	 * @param context The security context.
	 * @param prdId The product id.
	 * @return a collection of the death coverage available.
	 */
	@GET
	@Path("fundManagementFee/{fundId}")
	ProductValueDTO getFundManagementFee(@Context SecurityContext context, @PathParam("fundId") String fundId);

	/**
	 * Get the death coverage available for a given product.
	 * 
	 * @param context The security context.
	 * @param prdId The product id.
	 * @return a collection of the death coverage available.
	 */
	@GET
	@Path("policyManagementFee/{productLineId}")
	ProductValueDTO getPolicyManagementFee(@Context SecurityContext context, @QueryParam("policyId") String policyId, @PathParam("productLineId") String productId,
			@QueryParam("coverage") Integer coverage);

	@GET
	@Path("policyC13RatFee/{productLineId}")
	ProductValueDTO getPolicyC13RatFee(@Context SecurityContext context, @QueryParam("policyId") String policyId, @PathParam("productLineId") String productId,
			@QueryParam("coverage") Integer coverage);

	/**
	 * Get the death coverage available for a given product.
	 * 
	 * @param context The security context.
	 * @param prdId The product id.
	 * @return a collection of the death coverage available.
	 */
	@GET
	@Path("policyFee/{productLineId}")
	ProductValueDTO getPolicyFee(@Context SecurityContext context, @QueryParam("policyId") String policyId, @PathParam("productLineId") String productLineId, @QueryParam("coverage") Integer coverage);

	/**
	 * Get the death coverage available for a given product.
	 * 
	 * @param context The security context.
	 * @param prdId The product id.
	 * @return a collection of the death coverage available.
	 */
	@GET
	@Path("feeBasis/{productLineId}")
	ProductValueDTO getFeeBasis(@Context SecurityContext context, @PathParam("productLineId") String productLineId);

	/**
	 * Get the death coverage available for a given product.
	 * 
	 * @param context The security context.
	 * @param prdId The product id.
	 * @return a collection of the death coverage available.
	 */
	@GET
	@Path("surrenderFee/{productLineId}")
	ProductValueDTO getSurrenderFee(@Context SecurityContext context, @QueryParam("policyId") String policyId, @PathParam("productLineId") String productId);

	/**
	 * Get the death coverage available for a given product.
	 * 
	 * @param context The security context.
	 * @param prdId The product id.
	 * @return a collection of the death coverage available.
	 */
	@GET
	@Path("switchFee/{productLineId}")
	ProductValueDTO getSwitchFee(@Context SecurityContext context, @QueryParam("policyId") String policyId, @PathParam("productLineId") String productId);

	/**
	 * Get the death coverage available for a given product.
	 * 
	 * @param context The security context.
	 * @param prdId The product id.
	 * @return a collection of the death coverage available.
	 */
	@GET
	@Path("withdrawalFee/{productLineId}")
	ProductValueDTO getWithdrawalFee(@Context SecurityContext context, @QueryParam("policyId") String policyId, @PathParam("productLineId") String productId);

	/**
	 * Get the value for a given product Line and a given control name.
	 * 
	 * @param context The security context.
	 * @param productLineId The product Line Id.
	 * @param control The control code.
	 * @return the corresponding value.
	 */
	@GET
	@Path("productLine/{productLineId}/{control}")
	ProductValueDTO getProductValueByProductLineAndControl(@Context SecurityContext context, @PathParam("productLineId") String productLineId, @PathParam("control") String control);

	/**
	 * Get the death coverage available for a given product.
	 * 
	 * @param context The security context.
	 * @param prdId The product id.
	 * @return a collection of the death coverage available.
	 */
	@GET
	@Path("deathCoverageAmountOrPercentage/{productLineId}/{controlType}")
	ProductValueDTO getDeathCoverageAmountOrPercentage(@Context SecurityContext context, @QueryParam("policyId") String policyId, @PathParam("productLineId") String productLineId,
			@PathParam("controlType") String controlType);

	/**
	 * Retrieve the entry fees.
	 * 
	 * @param policyId The policy id.
	 * @return the entry fees
	 */
	@GET
	@Path("/policyFee")
	PolicyEntryFeesDto getEntryFees(@Context SecurityContext ctx, @QueryParam("policyId") String policyId);

}
