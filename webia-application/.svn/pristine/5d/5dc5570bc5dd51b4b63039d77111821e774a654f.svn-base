package lu.wealins.webia.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.webia.services.CreateEditingRequest;
import lu.wealins.common.dto.webia.services.CreateEditingResponse;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.ws.rest.EditingRESTService;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Component
public class EditingRESTServiceImpl implements EditingRESTService {
	
	@Autowired
	private EditingService editingService ;

	@Override
	public CreateEditingResponse applyManagementMandateDocRequest(SecurityContext context, CreateEditingRequest editingRequest) {
		return editingService.applyManagementMandateDocRequest(editingRequest);
	}

	@Override
	public CreateEditingResponse applyManagementMandateEndDocRequest(SecurityContext context, CreateEditingRequest editingRequest) {
		return editingService.applyManagementMandateEndDocRequest(editingRequest);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CreateEditingResponse applyAcceptanceReportDocRequest(SecurityContext context, CreateEditingRequest editingRequest) {
		return editingService.applyAcceptanceReportDocRequest(editingRequest);
	}

	@Override
	public EditingRequest generateWorkflowDocument(SecurityContext context, Long workflowItemId, DocumentType documentType) {
		return editingService.createWorkflowDocumentRequest(workflowItemId, documentType, false, false);
	}
}
