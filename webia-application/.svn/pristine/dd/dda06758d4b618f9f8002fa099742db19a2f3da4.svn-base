package lu.wealins.webia.core.service;

import java.util.Collection;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.CreateEditingRequest;
import lu.wealins.common.dto.webia.services.CreateEditingResponse;
import lu.wealins.webia.ws.rest.request.EditingRequest;

public interface EditingService {

	/**
	 * Generate documentation.
	 * 
	 * @param appForm The appForm.
	 */
	void generateDocumentation(AppFormDTO appForm);
	
	/**
	 * Create the editing request for a transaction tax.
	 * 
	 * @param id the transaction tax id.
	 * @param id the transaction tax id.
	 */
	CreateEditingResponse generateSurrenderDoc(Long id);

	/***
	 * Create the editing request for a transaction tax.
	 * 
	 * @param editingRequest
	 *            creating request
	 * @return editing response
	 */
	CreateEditingResponse generateSurrenderDoc(CreateEditingRequest editingRequest);

	/**
	 * Create a new document generation request.
	 * 
	 * @param editingRequest the request.
	 * @return the request id and the status of the request.
	 */
	CreateEditingResponse applyManagementMandateDocRequest(CreateEditingRequest editingRequest);

	/**
	 * Create a new document generation request.
	 * 
	 * @param editingRequest the request.
	 * @return the request id and the status of the request.
	 */
	CreateEditingResponse applyManagementMandateEndDocRequest(CreateEditingRequest editingRequest);
	
	/**
	 * Create a new acceptance report document request.
	 * 
	 * @param editingRequest the application form.
	 * @return the request id and the status of the request.
	 */
	CreateEditingResponse applyAcceptanceReportDocRequest(CreateEditingRequest editingRequest);


	/**
	 * Get the editing request by its id.
	 * 
	 * @param the id of the editing request.
	 * @return the editing request matched.
	 */
	EditingRequest getEditingRequestById(Long id);

	/**
	 * Create a follow up mail request.
	 * 
	 * @param request
	 *            the application form.
	 * @return the request id and the status of the request.
	 */
	CreateEditingResponse applyFollowUpRequest(CreateEditingRequest request);

	/**
	 * Create a new commission statement report document request.
	 * 
	 * @param statementId
	 * @param agentId
	 * @return
	 */
	CreateEditingResponse createCommissionRequest(String statementId, String agentId);

	CreateEditingResponse createCommissionPaymentSlipRequest(String statementId);

	EditingRequest createWorkflowDocumentRequest(Long workflowItemId, DocumentType documentType, boolean simulation, boolean synchronous);

	/**
	 * Create a new Fax payment document request.
	 * 
	 * @param transfers a string containing a list of transfer identifiers with comma separation
	 * @param agtId the contact agent ID of the deposit bank
	 * @return
	 */
	EditingRequest createFaxPaymentRequest(String transferIds, String agtId);

	Collection<EditingRequest> createEditingRequests(Collection<CreateEditingRequest> createEditingRequests);

	/**
	 * Create a new SEPA payment document request.
	 * 
	 * @param transfers a string containing a list of transfer identifiers with comma separation
	 * @return
	 */
	EditingRequest createSepaPaymentRequest(String transferIds);
	
	/**
	 * Create a new Swift payments document request.
	 * 
	 * @param transfers a string containing a list of transfer identifiers with comma separation
	 * @return
	 */
	public EditingRequest createSwiftPaymentRequest(String transferIds);

	EditingRequest createAccountingNoteRequest(Long workflowItemId, String transferIds);
}
