package lu.wealins.webia.core.service.impl;

import java.io.File;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.service.SimulationService;
import lu.wealins.webia.core.service.SynchronousDocumentService;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Service
public class SimulationServiceImpl implements SimulationService {

	private static final String EDITING_REQUEST_CANT_BE_NULL = "The editing request can't be null";
	private static final String WORKFLOW_ITEM_NOT_NULL = "The workflow item id can't be null";
	private static final String DOCUMENT_TYPE_NOT_NULL = "The document type can't be null";

	@Autowired
	private SynchronousDocumentService synchronousDocumentService;

	@Autowired
	private EditingService editingService;

	@Override
	public Response simulateWorkflowDocument(SecurityContext context, Long workflowItemId, DocumentType documentType) {
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_NOT_NULL);
		Assert.notNull(documentType, DOCUMENT_TYPE_NOT_NULL);

		File simulationFile = getSimulationFile(context, workflowItemId, documentType);

		return createResponse(simulationFile, documentType);
	}

	@Override
	public Response getSimulationByEditingRequest(Long editingRequestId) {
		Assert.notNull(editingRequestId, EDITING_REQUEST_CANT_BE_NULL);

		EditingRequest editingRequest = editingService.getEditingRequestById(editingRequestId);
		File editingFile = synchronousDocumentService.getEditingFile(editingRequest);

		return createResponse(editingFile, editingRequest.getDocumentType());
	}

	private File getSimulationFile(SecurityContext context, Long workflowItemId, DocumentType documentType) {
		EditingRequest editingRequestInput = editingService.createWorkflowDocumentRequest(workflowItemId, documentType, true, true);
		return synchronousDocumentService.createDocumentSynchronously(context, editingRequestInput);
	}

	private Response createResponse(File simulationFile, DocumentType documentType) {
		ResponseBuilder responseBuilder = Response.ok(simulationFile);
		responseBuilder.header("Content-Disposition", "attachment; filename=" + simulationFile.getName());
		responseBuilder.type(getMediaType(documentType));
		return responseBuilder.build();
	}

	private String getMediaType(DocumentType documentType) {
		switch (documentType) {
		case SEPA_PAYMENT:
			return MediaType.APPLICATION_XML_VALUE;
		default:
			return MediaType.APPLICATION_PDF_VALUE;
		}
	}
}
