package lu.wealins.webia.ws.rest.impl;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.webia.core.service.SimulationService;
import lu.wealins.webia.ws.rest.WorkflowSimulationRESTService;

@Component
@RolesAllowed("rest-user")
@Path("/simulation")
public class WorkflowSimulationRESTServiceImpl implements WorkflowSimulationRESTService {

	@Autowired
	private SimulationService simulationService;

	@Override
	@GET
	@Path("workflowDocument")
	@Produces("application/pdf")
	public Response getNewDocument(@Context SecurityContext context, @QueryParam("workflowItemId") Long workflowItemId, @QueryParam("documentType") DocumentType documentType) {
		return simulationService.simulateWorkflowDocument(context, workflowItemId, documentType);
	}

	@Override
	@GET
	@Path("existingDocument")
	public Response getExistingDocument(@QueryParam("editingRequestId") Long editingRequestId) {
		return simulationService.getSimulationByEditingRequest(editingRequestId);
	}
}
