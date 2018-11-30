package lu.wealins.webia.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.StatementRequest;
import lu.wealins.common.dto.webia.services.StatementDTO;
import lu.wealins.common.dto.webia.services.StatementRequest;
import lu.wealins.common.dto.webia.services.StatementUpdateStatusRequest;


/**
 * StatementRESTService is a REST service to handle statement services.
 * 
 * @param <T>
 *
 */
@Path("statement")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface StatementRESTService {
	
	/**
	 * create new statement
	 * 
	 * @param context The security context.
	 * @param statementRequest
	 * @return the statement.
	 */
	@POST
	@Path("/new-statement")
	public Response createNewStatement(@Context SecurityContext context, StatementRequest statementRequest);
	
	/**
	 * update statement status
	 * 
	 * @param context The security context.
	 * @param StatementUpdateStatusRequest The StatementUpdateStatusRequest
	 * @return the statement.
	 */
	@POST
	@Path("/update-status")
	public Response updateStatementStatus(@Context SecurityContext context, StatementUpdateStatusRequest statementUpdateStatusRequest);
	
	
	/**
	 * create new statement
	 * 
	 * @param context The security context.
	 * @param statementRequest
	 * @return the statement.
	 */
	@GET
	@Path("/{statementId}/")
	public StatementDTO getStatement(@Context SecurityContext context, @PathParam("statementId") Long statementId);
}
