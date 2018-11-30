package lu.wealins.webia.ws.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.OptionDetailDTO;

@Path("/optionDetail")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface LiabilityOptionDetailRESTService {

	/**
	 * Client policy relationship roles allowed in the webia application 
	 * It search all the CliPolRelationship roles possible (CPR_TYPE) excepte thoses listed in webia APPLI_PARAM
	 * 
	 * @param context The security context.
	 * @return collection of CliPolRelationship roles.
	 * 
	 */
	@GET
	@Path("/cpr-roles")
	@Consumes(MediaType.APPLICATION_JSON)
	List<OptionDetailDTO> getCPRRoles(@Context SecurityContext context);

	@GET
	@Path("/cpr-roles/product")
	@Consumes(MediaType.APPLICATION_JSON)
	List<OptionDetailDTO> getCPRRoles(@Context SecurityContext context, @QueryParam("id") String productId,
			@QueryParam("productCapi") boolean productCapi, @QueryParam("yearTerm") boolean yearTerm);

	/**
	 * Get the pricing frequency where FK_OPTIONSOPT_ID = 'PRICING_FREQUENCY'
	 * 
	 * @return set of pricing frequency
	 */
	@GET
	@Path("ctxPricingFrequencies")
	List<OptionDetailDTO> getContextualizedPricingFrequencies(@Context SecurityContext ctx);
}
