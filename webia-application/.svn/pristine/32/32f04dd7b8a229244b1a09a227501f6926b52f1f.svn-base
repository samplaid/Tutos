package lu.wealins.webia.core.service;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.BatchUploadResponse;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.SaveDocumentIntoFileNetRequest;

public interface DocumentGenerationCommissionsService {

	EditingRequest generateDocumentCommissions(SecurityContext context, EditingRequest request);

	/**
	 * Reserve a DocId in fileNet and update the editingRequest
	 * 
	 * @param editingRequest
	 * @return the updated editingRequest 
	 */
	EditingRequest reserveDocIdAndUpdateRequest(EditingRequest editingRequest);

	String uploadDocument(SaveDocumentIntoFileNetRequest uploadRequest);

	BatchUploadResponse batchUploadDocument(List<SaveDocumentIntoFileNetRequest> uploadRequests);


}
