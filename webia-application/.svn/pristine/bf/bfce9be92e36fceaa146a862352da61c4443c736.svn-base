package lu.wealins.webia.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.webia.services.CreateEditingRequest;
import lu.wealins.common.dto.webia.services.CreateEditingResponse;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Path("/wiaEditing")
@RolesAllowed("rest-user")
public interface EditingRESTService {
	
	/**
	 * Create a new document generation request.
	 * 
	 * @param appForm the application form.
	 * @return the request id and the status of the request.
	 */
	@POST
	@Path("mgtMandateRequest")
	@Consumes(MediaType.APPLICATION_JSON)
	CreateEditingResponse applyManagementMandateDocRequest(@Context SecurityContext context, CreateEditingRequest editingRequest);

	/**
	 * Create a new document generation request.
	 * 
	 * @param appForm the application form.
	 * @return the request id and the status of the request.
	 */
	@POST
	@Path("mgtMandateRequestEnd")
	@Consumes(MediaType.APPLICATION_JSON)
	CreateEditingResponse applyManagementMandateEndDocRequest(@Context SecurityContext context, CreateEditingRequest editingRequest);

	/**
	 * Create a new acceptance report document request.
	 * 
	 * @param editingRequest the editing request.
	 * @return the request id and the status of the request.
	 */
	@POST
	@Path("acceptanceReportRequest")
	@Consumes(MediaType.APPLICATION_JSON)
	CreateEditingResponse applyAcceptanceReportDocRequest(@Context SecurityContext context, CreateEditingRequest editingRequest);

	@POST
	@Path("workflowDocument")
	EditingRequest generateWorkflowDocument(@Context SecurityContext context, @QueryParam("workflowItemId") Long workflowItemId, @QueryParam("documentType") DocumentType documentType);
}
