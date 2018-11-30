package lu.wealins.webia.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.BatchUploadRequest;
import lu.wealins.common.dto.webia.services.StatementRequest;
import lu.wealins.common.dto.webia.services.StatementResponse;

@Path("/documentCommission")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface DocumentCommissionRESTService {

	@POST
	@Path("/batchUploadCommissionPDF")
	Response batchUploadCommissionPDF(@Context SecurityContext context, BatchUploadRequest request);
	
	/**
	 * This method create the commission statement to generate the commission document
	 * 
	 * @param context the Security Context
	 * @param statementRequest the {@link StatementRequest} 
	 * @return response 200 or other
	 * @throws Exception 
	 */
	@Path("/generateStatementCom")
	@Consumes(MediaType.APPLICATION_JSON)
	@POST
	StatementResponse generateStatementCom(@Context SecurityContext context, StatementRequest statementRequest) throws Exception;  

}
