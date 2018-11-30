package lu.wealins.webia.core.service;

import java.io.File;

import javax.ws.rs.core.SecurityContext;

import lu.wealins.webia.ws.rest.request.EditingRequest;

public interface SynchronousDocumentService {
	File createDocumentSynchronously(SecurityContext context, EditingRequest editingRequest);

	EditingRequest createEditingSynchronously(SecurityContext context, EditingRequest editingRequest);

	File getEditingFile(EditingRequest editingRequest);
}
