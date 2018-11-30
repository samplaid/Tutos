package lu.wealins.liability.services.ws.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.OptionDetailDTO;
import lu.wealins.common.dto.liability.services.OptionDetails;

/**
 * UoptDetailRESTService is a REST service responsible to manipulate UoptDetail objects.
 *
 */
@Path("optionDetail")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface OptionDetailRESTService {

	/**
	 * Get the lives constant (option = 'NBVLVS')
	 * 
	 * @param context The security context
	 * @return The Live constants response.
	 */
	@GET
	@Path("lives")
	List<OptionDetailDTO> getLives(@Context SecurityContext context);

	/**
	 * Get the lives constant (option = 'NBVLVS') for a given product id
	 * 
	 * @param context The security context
	 * @return The Live constants response.
	 */
	@GET
	@Path("lives/{productId}")
	List<OptionDetailDTO> getLives(@Context SecurityContext context, @PathParam("productId") String productId);

	/**
	 * Get the cliPolRelationship roles (option = 'CPR_TYPE')
	 * 
	 * @param context The security context
	 * @return The cliPolRelationship roles response.
	 */
	@GET
	@Path("cpr-roles")
	OptionDetails getCPRRoles(@Context SecurityContext context);

	/**
	 * FK_OPTIONSOPT_ID = 'LANGUAGES'
	 * 
	 * @param ctx
	 * @return
	 */
	@GET
	@Path("languages")
	List<OptionDetailDTO> getLanguages(@Context SecurityContext ctx);

	/**
	 * FK_OPTIONSOPT_ID = 'CLI_MSTAT'
	 * 
	 * @param ctx
	 * @return
	 */
	@GET
	@Path("maritalStatus")
	List<OptionDetailDTO> getMaritalStatus(@Context SecurityContext ctx);

	/**
	 * Get the pricing frequency where FK_OPTIONSOPT_ID = 'PRICING_FREQUENCY'
	 * 
	 * @return set of pricing frequency
	 */
	@GET
	@Path("pricingFrequencies")
	List<OptionDetailDTO> getPricingFrequencies(@Context SecurityContext ctx);

	/**
	 * Get the payment modes where FK_OPTIONSOPT_ID = 'AGSPME'
	 * 
	 * @return set of pricing frequency
	 */
	@GET
	@Path("paymentModes")
	List<OptionDetailDTO> getPaymentModes(@Context SecurityContext ctx);
	
	/**
	 * Get the account statuses where FK_OPTIONSOPT_ID = 'STATUS_BKA'
	 * 
	 * @return set of pricing frequency
	 */
	@GET
	@Path("accountStatus")
	List<OptionDetailDTO> getAccountStatus(@Context SecurityContext ctx);
}
