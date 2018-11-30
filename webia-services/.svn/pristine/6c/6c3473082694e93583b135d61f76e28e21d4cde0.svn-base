package lu.wealins.webia.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.CheckSignaletiqueIsinRequest;
import lu.wealins.common.dto.webia.services.CheckSignaletiqueIsinResponse;
import lu.wealins.common.dto.webia.services.CheckSignaletiqueRequest;
import lu.wealins.common.dto.webia.services.CheckSignaletiqueResponse;
import lu.wealins.common.dto.webia.services.SaveSignaletiqueRequest;
import lu.wealins.common.dto.webia.services.SaveSignaletiqueResponse;

/**
 * Rest service for managing signaletique values.
 */
@Path("signaletique")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface SignaletiqueRESTService {

	/**
	 * Check the existence in the webia database of the provided Isin/currency pairs.
	 * 
	 * @param context the security context
	 * @param request the request
	 * @return a {@link CheckSignaletiqueResponse}.
	 */
	@POST
	@Path("isAvailableInBloomberg")
	@Consumes(MediaType.APPLICATION_JSON)
	CheckSignaletiqueResponse exist(@Context SecurityContext context, final CheckSignaletiqueRequest request);

	/**
	 * Check the existence in the webia database of the provided Isin/currency pairs.
	 * 
	 * @param context the security context
	 * @param request the request
	 * @return a {@link CheckSignaletiqueResponse}.
	 */
	@POST
	@Path("isin/exist")
	@Consumes(MediaType.APPLICATION_JSON)
	CheckSignaletiqueIsinResponse isinExist(@Context SecurityContext context, final CheckSignaletiqueIsinRequest request);

	/**
	 * Update or create in the webia database the list of the provided Isin/currency pairs.
	 * 
	 * @param context the security context
	 * @param request the request
	 * @return a {@link CheckSignaletiqueResponse}.
	 */
	@POST
	@Path("save")
	@Consumes(MediaType.APPLICATION_JSON)
	SaveSignaletiqueResponse save(@Context SecurityContext context, final SaveSignaletiqueRequest request);

	/**
	 * Same as save, but only persist in not already found in the database
	 * 
	 * @param context the security context
	 * @param request the request
	 * @return a {@link CheckSignaletiqueResponse}.
	 */
	@POST
	@Path("inject")
	@Consumes(MediaType.APPLICATION_JSON)
	SaveSignaletiqueResponse inject(@Context SecurityContext context, final SaveSignaletiqueRequest request);

}
