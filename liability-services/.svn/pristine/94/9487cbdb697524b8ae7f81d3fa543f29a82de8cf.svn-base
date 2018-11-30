package lu.wealins.liability.services.ws.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.UoptDetailDTO;

/**
 * UoptDetailRESTService is a REST service responsible to manipulate UoptDetail objects.
 *
 */
@Path("uoptDetail")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface UoptDetailRESTService {

	@GET
	UoptDetailDTO getUoptDetail(@Context SecurityContext context, @QueryParam("id") String uddId);

	/**
	 * Get the circular letters constant (definitionUdfId = 29, active = 1)
	 * 
	 * @param context The security context
	 * @return The circular letters constant response.
	 */
	@GET
	@Path("circularLetters")
	List<UoptDetailDTO> getCircularLetters(@Context SecurityContext context);

	/**
	 * Get the fund classifications constant (definitionUdfId = 29, active = 1)
	 * 
	 * @param context The security context
	 * @return The fund classifications constant response.
	 */
	@GET
	@Path("fundClassifications")
	List<UoptDetailDTO> getFundClassifications(@Context SecurityContext context);

	/**
	 * Get the risk classes constant (definitionUdfId = 57, active = 1)
	 * 
	 * @param context The security context
	 * @return The risk classes constant response.
	 */
	@GET
	@Path("riskClasses")
	List<UoptDetailDTO> getRiskClasses(@Context SecurityContext context);

	/**
	 * Get the risk profiles constant (definitionUdfId = 37, active = 1)
	 * 
	 * @param context The security context
	 * @return The risk profiles constant response.
	 */
	@GET
	@Path("riskProfiles")
	List<UoptDetailDTO> getRiskProfiles(@Context SecurityContext context);

	/**
	 * Get the risk currencies constant (definitionUdfId = 38, active = 1)
	 * 
	 * @param context The security context
	 * @return The risk currencies constant response.
	 */
	@GET
	@Path("riskCurrencies")
	List<UoptDetailDTO> getRiskCurrencies(@Context SecurityContext context);

	/**
	 * Get the investment categories constant (definitionUdfId = 3, active = 1)
	 * 
	 * @param context The security context
	 * @return The investment categories constant response.
	 */
	@GET
	@Path("investmentCategories")
	List<UoptDetailDTO> getInvestmentCategories(@Context SecurityContext context);

	/**
	 * Get the alternative funds constant (definitionUdfId = 41, active = 1)
	 * 
	 * @param context The security context
	 * @return The alternative funds constant response.
	 */
	@GET
	@Path("alternativeFunds")
	List<UoptDetailDTO> getAlternativeFunds(@Context SecurityContext context);

	/**
	 * Get the type of POAs constant (definitionUdfId = 44, active = 1)
	 * 
	 * @param context The security context
	 * @return The type of POAs constant response.
	 */
	@GET
	@Path("typePOAs")
	List<UoptDetailDTO> getTypePOAs(@Context SecurityContext context);

	/**
	 * Get the type of sendingRules constant (definitionUdfId = 9, active = 1)
	 * 
	 * @param context The security context
	 * @return The type of sendingRules constant response.
	 */
	@GET
	@Path("sendingRules")
	List<UoptDetailDTO> getSendingRules(@Context SecurityContext context);

	/**
	 * Get the type of agent contact constants (definitionUdfId = 62, active = 1)
	 * 
	 * @param context The security context
	 * @return The type of agent contact constants response.
	 */
	@GET
	@Path("typeOfAgentContact")
	List<UoptDetailDTO> getTypeOfAgentContact(@Context SecurityContext context);

	/**
	 * Titles (ID = 59)
	 * 
	 * @param ctx
	 * @return
	 */
	@GET
	@Path("titles")
	List<UoptDetailDTO> getTitles(@Context SecurityContext ctx);

	/**
	 * Client profiles (ID = 62)
	 * 
	 * @param ctx
	 * @return
	 */
	@GET
	@Path("clientProfiles")
	List<UoptDetailDTO> getClientProfiles(@Context SecurityContext ctx);

	/**
	 * Client compliance risk (ID = 1)
	 * 
	 */
	@GET
	@Path("clientComplianceRisks")
	List<UoptDetailDTO> getClientComplianceRisks(@Context SecurityContext ctx);

	/**
	 * ID = 53
	 * 
	 * @param ctx
	 * @return
	 */
	@GET
	@Path("clientActivitySectors")
	List<UoptDetailDTO> getClientActivitySectors(@Context SecurityContext ctx);

	/**
	 * ID = 58
	 * 
	 * @param ctx
	 * @return
	 */
	@GET
	@Path("clientProfessions")
	List<UoptDetailDTO> getClientProfessions(@Context SecurityContext ctx);

	/**
	 * ID = 8
	 * 
	 * @param ctx
	 * @return
	 */
	@GET
	@Path("entityType")
	List<UoptDetailDTO> getEntityType(@Context SecurityContext ctx);

	/**
	 * ID = 66
	 * 
	 * @param ctx
	 * @return
	 */
	@GET
	@Path("crsStatus")
	List<UoptDetailDTO> getCrsStatus(@Context SecurityContext ctx);

	/**
	 * ID = 67
	 * 
	 * @param ctx
	 * @return
	 */
	@GET
	@Path("crsExactStatus")
	List<UoptDetailDTO> getCrsExactStatus(@Context SecurityContext ctx);

	/**
	 * ID = 61
	 * 
	 * @param ctx
	 * @return
	 */
	@GET
	@Path("typeOfControl")
	List<UoptDetailDTO> getTypeOfControl(@Context SecurityContext ctx);

	/**
	 * ID = 68
	 * 
	 * @param ctx
	 * @return
	 */
	@GET
	@Path("agentTitle")
	List<UoptDetailDTO> getAgentTitle(@Context SecurityContext ctx);

	/**
	 * Get the death causes list (definitionUdfId = 4, active = 1)
	 * 
	 * @param ctx
	 * @return a collection of death causes
	 */
	@GET
	@Path("deathCauses")
	List<UoptDetailDTO> getDeathCauses(@Context SecurityContext ctx);

	/**
	 * Get the uoptDetail for a keyValue
	 * 
	 * @param context
	 * @param keyValue
	 * @return
	 */
	@GET
	@Path("forKeyValue")
	UoptDetailDTO getUoptDetailForKeyValue(@Context SecurityContext context, @QueryParam("keyValue") String keyValue);
}
