package lu.wealins.webia.ws.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.editing.services.enums.DocumentType;


public interface WorkflowSimulationRESTService {
	Response getNewDocument(SecurityContext context, Long workflowItemId, DocumentType documentType);

	Response getExistingDocument(Long editingRequestId);
}
