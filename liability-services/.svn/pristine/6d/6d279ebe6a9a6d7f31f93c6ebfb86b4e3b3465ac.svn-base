package lu.wealins.liability.services.ws.rest;

import java.text.ParseException;
import java.util.Collection;
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

import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.liability.services.ClientLiteDTO;
import lu.wealins.common.dto.liability.services.ClientSearchRequest;
import lu.wealins.common.dto.liability.services.SearchResult;

@RolesAllowed("rest-user")
@Path("/client")
@Produces(MediaType.APPLICATION_JSON)
public interface ClientRESTService {

	/**
	 * Get the client according its id.
	 * 
	 * @param context The security context.
	 * @param id The client id.
	 * @return The client. Return a <code>ObjectNotFoundException</code> if the client does not exist.
	 */
	@GET
	@Path("{id}")
	ClientDTO getClient(@Context SecurityContext context, @PathParam("id") Integer id);

	@GET
	@Path("/")
	Collection<ClientDTO> getClients(@Context SecurityContext context, @QueryParam("ids") List<Integer> ids);

	/**
	 * Get the client according its id.
	 * 
	 * @param context The security context.
	 * @param id The client id.
	 * @return The client. Return a <code>ObjectNotFoundException</code> if the client does not exist.
	 */
	@GET
	@Path("/lite")
	ClientLiteDTO getClientLight(@Context SecurityContext context, @QueryParam("id") Integer id);

	/**
	 * Create a new client with the WSSUPERS soap service. The principal in the security context will be used as the update user of the client.
	 * 
	 * @param context The security context.
	 * @param client The client.
	 * @return The created client.
	 */
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	ClientDTO create(@Context SecurityContext context, ClientDTO client);

	/**
	 * Update an existing client with the WSSUPERS soap service. The principal in the security context will be used as the update user of the client.
	 * 
	 * Client to be updated must exist in the database, otherwise the method will throw an exception. All the values of the client will be replaced with the data in the DTO parameter.
	 * 
	 * @param context The security context.
	 * @param client The client.
	 * @return The updated client.
	 */
	@POST
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON)
	ClientDTO update(@Context SecurityContext context, ClientDTO client);

	/**
	 * Client search service : the filter can be part of client id, last name or first name. The results can be further filtered by the client's birth date, the birth date must be given in
	 * international ISO format : yyyy-MM-dd.
	 * 
	 * @param context
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	SearchResult<ClientLiteDTO> search(
			@Context SecurityContext context, ClientSearchRequest request) throws ParseException;

	/**
	 * Returns the clients who match the specified criteria. The birthday parameter must be given in international ISO format : yyyy-MM-dd otherwise a {@link ParseException} will be thrown. The name
	 * parameter must not be null.
	 * 
	 * @param ctx
	 * @param name
	 * @param birthday
	 * @param clientId
	 * @return
	 * @throws ParseException
	 */
	@GET
	@Path("/match")
	List<ClientLiteDTO> match(@Context SecurityContext ctx,
			@QueryParam("name") String lastName,
			@QueryParam("birthday") String birthday,
			@QueryParam("exclude") Long clientId) throws ParseException;

	/**
	 * Return the list of client who are fiduciaries
	 * 
	 * @param ctx
	 * @return
	 */
	@GET
	@Path("/fiduciaries")
	List<ClientLiteDTO> loadAllFiduciaries(@Context SecurityContext ctx);
	
	/**
	 * Return if the death can be notify for a given client
	 * 	-check if there is no police settlement to perform before 
	 * 
	 * @param context
	 * @param id - the client identifier
	 * @return a boolean
	 */	
	@GET
	@Path("/canClientDeathBeNotified")
	boolean canClientDeathBeNotified(@Context SecurityContext context, @QueryParam("id") Integer id);

	/**
	 * GDPR client consent is accepted when the consent start date is filled in and the consent end date is empty.
	 * 
	 * @param context security content
	 * @param clientId a set of client id.
	 * @return true if the condition is met.
	 */
	@GET
	@Path("/gdprConsentAccepted")
	boolean isClientGdprConsentAccepted(@Context SecurityContext context, @QueryParam("clientId") Integer clientId);
}
