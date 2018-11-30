package lu.wealins.webia.core.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.editing.services.enums.DocumentType;


public interface SimulationService {
	Response simulateWorkflowDocument(SecurityContext context, Long workflowItemId, DocumentType documentType);

	Response getSimulationByEditingRequest(Long editingRequestId);
}
