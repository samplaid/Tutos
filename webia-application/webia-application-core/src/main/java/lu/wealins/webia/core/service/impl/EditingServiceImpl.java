package lu.wealins.webia.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.GenericType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.CreateEditingRequest;
import lu.wealins.common.dto.webia.services.CreateEditingResponse;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.service.validations.FundFormValidationService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.impl.exception.EditingException;
import lu.wealins.webia.ws.rest.impl.exception.ReportExceptionHelper;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Service
public class EditingServiceImpl implements EditingService {

	private static final String EDITING_CREATE_REQUEST = "editing/createRequest";
	private static final String EDITING_CREATE_REQUESTS = "editing/createRequests";
	private static final String EDITING_GET_REQUEST = "editing/";
	private static final String EDITING_CREATE_SYNC_REQUEST = "editing/createSyncRequest";
	private static final String TRANSACTION_TAX_ID_CANNOT_BE_NULL = "The transaction tax id can't be null";

	@Autowired
	private RestClientUtils restClientUtils;
	
	@Autowired
	private FundFormValidationService fundFormValidationService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.EditingService#generateDocumentation(lu.wealins.webia.ws.rest.dto.AppFormDTO)
	 */
	@Override
	public void generateDocumentation(AppFormDTO appForm) {
		CreateEditingRequest request = new CreateEditingRequest();
		request.setPolicy(appForm.getPolicyId());
		request.setDocumentType(DocumentType.POLICY_SCHEDULE);

		restClientUtils.post(EDITING_CREATE_REQUEST, request, String.class);

	}
		
	
	/**
	 * {@inheritDoc}. <br>
	 * Before the document creation request, the method will validate data like
	 * fund. In case of data is not valid, it throws an exception type of
	 * {@link EditingException} that contain a list of errors
	 * generated during the validation. Otherwise, the document creation request will be processed.
	 * @param editingRequest the request object.
	 * @throws EditingException if the data used for the document creation is not valid.
	 */
	@Override
	public CreateEditingResponse applyManagementMandateDocRequest(CreateEditingRequest editingRequest){		
		if(editingRequest == null){
			return null;
		}
		
		List<String> errors = new ArrayList<>();
		fundFormValidationService.validateFundForDocumentation(editingRequest.getFund(), errors);
		ReportExceptionHelper.throwIfErrorsIsNotEmpty(errors, EditingException.class);
				
		editingRequest.setDocumentType(DocumentType.MANAGEMENT_MANDATE);
		return restClientUtils.post(EDITING_CREATE_REQUEST, editingRequest, CreateEditingResponse.class);
	}

	/**
	 * {@inheritDoc}. <br>
	 * @param editingRequest the request object.
	 * @throws EditingException if the data used for the document creation is not valid.
	 */
	@Override
	public CreateEditingResponse applyManagementMandateEndDocRequest(CreateEditingRequest editingRequest){		
		if(editingRequest == null){
			return null;
		}
				
		editingRequest.setDocumentType(DocumentType.MANAGEMENT_MANDATE_END);
		return restClientUtils.post(EDITING_CREATE_REQUEST, editingRequest, CreateEditingResponse.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CreateEditingResponse applyAcceptanceReportDocRequest(CreateEditingRequest editingRequest) {
		if (editingRequest == null) {
			return null;
		}

		Collection<String> errors = new ArrayList<String>();

		if (editingRequest.getWorkflowItemId() == null) {
			errors.add("Acceptance editing request: the workflow item id is mandatory");
		}

		if (StringUtils.isBlank(editingRequest.getProduct())) {
			errors.add("Acceptance editing request: product is mandatory");
		}

		if (StringUtils.isBlank(editingRequest.getPolicy())) {
			errors.add("Acceptance editing request: policy number is mandatory");
		}

		ReportExceptionHelper.throwIfErrorsIsNotEmpty(errors, EditingException.class);

		editingRequest.setDocumentType(DocumentType.ACCEPTANCE_REPORT);
		return restClientUtils.post(EDITING_CREATE_REQUEST, editingRequest, CreateEditingResponse.class);
	}
	@Override
	public CreateEditingResponse generateSurrenderDoc(Long id) {
		Assert.notNull(id, TRANSACTION_TAX_ID_CANNOT_BE_NULL);

		CreateEditingRequest request = new CreateEditingRequest();
		request.setDocumentType(DocumentType.SURRENDER);
		request.setTransactionTax(id);
		return restClientUtils.post(EDITING_CREATE_REQUEST, request, CreateEditingResponse.class);
	}

	@Override
	public EditingRequest getEditingRequestById(Long id) {
		Assert.notNull(id, "The editing request id can't be null");

		return restClientUtils.get(EDITING_GET_REQUEST, id.toString(), EditingRequest.class);
	}

	private CreateEditingRequest createWorkflowRequest(Long workflowItemId, boolean simulation, DocumentType documentType, boolean synchronous) {
		CreateEditingRequest editingRequest = new CreateEditingRequest();
		editingRequest.setWorkflowItemId(workflowItemId);
		editingRequest.setSimulation(Boolean.valueOf(simulation));
		editingRequest.setDocumentType(documentType);
		editingRequest.setSynchronous(synchronous);
		return editingRequest;
	}

	@Override
	public CreateEditingResponse applyFollowUpRequest(CreateEditingRequest editingRequest) {

		if (editingRequest == null) {
			return null;
		}

		Collection<String> errors = new ArrayList<String>();

		if (StringUtils.isBlank(editingRequest.getProduct())) {
			errors.add("Follow Up request: product is mandatory");
		}

		if (StringUtils.isBlank(editingRequest.getPolicy())) {
			errors.add("Follow Up request: policy number is mandatory");
		}

		ReportExceptionHelper.throwIfErrorsIsNotEmpty(errors, EditingException.class);

		editingRequest.setDocumentType(DocumentType.SOUSCRIPTION_FOLLOWUP);
		return restClientUtils.post(EDITING_CREATE_REQUEST, editingRequest, CreateEditingResponse.class);

	}
	
	@Override
	public CreateEditingResponse createCommissionRequest(String statementId, String agentId) {
		CreateEditingRequest request = new CreateEditingRequest();
		request.setStatement(statementId);
		request.setAgent(agentId);
		request.setDocumentType(DocumentType.COMMISSIONS);

		return restClientUtils.post(EDITING_CREATE_REQUEST, request, CreateEditingResponse.class);
	}

	@Override
	public CreateEditingResponse createCommissionPaymentSlipRequest(String statementId) {
		CreateEditingRequest request = new CreateEditingRequest();
		request.setStatement(statementId);
		request.setDocumentType(DocumentType.COMMISSION_PAYMENT_SLIP);
		return restClientUtils.post(EDITING_CREATE_REQUEST, request, CreateEditingResponse.class);
	}

	// TODO : the editing service creation operation should return the dto because it is unnecessary to do a second request to get.
	@Override
	public EditingRequest createWorkflowDocumentRequest(Long workflowItemId, DocumentType documentType, boolean simulation, boolean synchronous) {
		Assert.notNull(workflowItemId, "The workflow item id can't be null");
		Assert.notNull(documentType, "The document type can not be null");

		CreateEditingRequest simulationRequest = createWorkflowRequest(workflowItemId, simulation, documentType, synchronous);

		return restClientUtils.post(EDITING_CREATE_SYNC_REQUEST, simulationRequest, EditingRequest.class);
	}
	
	@Override
	public EditingRequest createAccountingNoteRequest(Long workflowItemId, String transferIds) {
		Assert.notNull(workflowItemId, "The workflow item id can't be null");
		Assert.hasText(transferIds, "The transfer ids can't be blank");

		CreateEditingRequest simulationRequest = createWorkflowRequest(workflowItemId, false, DocumentType.WITHDRAWAL_ACCOUNTING_NOTE, false);
		simulationRequest.setTransferIds(transferIds);

		return restClientUtils.post(EDITING_CREATE_SYNC_REQUEST, simulationRequest, EditingRequest.class);
	}

	// TODO : the editing service creation operation should return the dto because it is unnecessary to do a second request to get.
	@Override
	public EditingRequest createFaxPaymentRequest(String transferIds, String agtId) {
		Assert.isTrue(StringUtils.isNotBlank(transferIds), "The transfers list cannot be null");
		CreateEditingRequest request = new CreateEditingRequest();
		request.setTransferIds(transferIds);
		request.setAgent(agtId);
		request.setDocumentType(DocumentType.FAX_PAYMENT);
		request.setSynchronous(true);
		return restClientUtils.post(EDITING_CREATE_SYNC_REQUEST, request, EditingRequest.class);
	}

	@Override
	public CreateEditingResponse generateSurrenderDoc(CreateEditingRequest editingRequest) {
		return restClientUtils.post(EDITING_CREATE_REQUEST, editingRequest, CreateEditingResponse.class);
	}
	
	@Override
	public EditingRequest createSepaPaymentRequest(String transferIds) {
		Assert.isTrue(StringUtils.isNotBlank(transferIds), "The transfers list cannot be null");
		CreateEditingRequest request = new CreateEditingRequest();
		request.setTransferIds(transferIds);
		request.setDocumentType(DocumentType.SEPA_PAYMENT);
		request.setSynchronous(true);
		return restClientUtils.post(EDITING_CREATE_SYNC_REQUEST, request, EditingRequest.class);
	}
	
	@Override
	public EditingRequest createSwiftPaymentRequest(String transferIds) {
		Assert.isTrue(StringUtils.isNotBlank(transferIds), "The transfers list cannot be null");
		CreateEditingRequest request = new CreateEditingRequest();
		request.setTransferIds(transferIds);
		request.setDocumentType(DocumentType.SWIFT_PAYMENT);
		request.setSynchronous(true);
		return restClientUtils.post(EDITING_CREATE_SYNC_REQUEST, request, EditingRequest.class);
	}

	@Override
	public Collection<EditingRequest> createEditingRequests(Collection<CreateEditingRequest> createEditingRequests) {
		Assert.notEmpty(createEditingRequests, "The create editing requests can't be empty");

		return restClientUtils.post(EDITING_CREATE_REQUESTS, createEditingRequests, new GenericType<Collection<EditingRequest>>() {
		});
	}

}
