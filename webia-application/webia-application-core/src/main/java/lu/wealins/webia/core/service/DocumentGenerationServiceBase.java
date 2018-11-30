package lu.wealins.webia.core.service;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.CreateEditingRequest;
import lu.wealins.common.dto.webia.services.CreateEditingResponse;
import lu.wealins.common.dto.webia.services.EditingRequestStatus;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.editing.common.webia.SubscriptionFollowUp;
import lu.wealins.editing.common.webia.SubscriptionFollowUp.PaymentDetails;
import lu.wealins.webia.core.mapper.AppFormMapper;
import lu.wealins.webia.core.service.helper.FollowUpDocumentContentHelper;
import lu.wealins.webia.ws.rest.request.DocumentGenerationResponse;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Component
public abstract class DocumentGenerationServiceBase {

	@Autowired
	protected EditingService editingService;

	@Autowired
	protected FollowUpDocumentContentHelper followUpHelper;

	@Autowired
	protected DocumentService documentGenerationService;

	@Autowired
	protected FollowUpDocumentValidationService followUpValidationService;

	@Autowired
	@Qualifier("webiaStepEnricher")
	StepEnricher webiaStepEnricher;

	@Autowired
	@Qualifier("lissiaStepEnricher")
	StepEnricher lissiaStepEnricher;

	@Autowired
	private AppFormMapper appFormMapper;

	public EditingRequest generateDocument(SecurityContext context, EditingRequest request,
			AppFormDTO enrichedAppForm) {
		return generateDocument(context, request, enrichedAppForm, initLanguage(enrichedAppForm), true);
	}

	public abstract EditingRequest generateDocument(SecurityContext context, EditingRequest request, AppFormDTO appForm,
			String language, boolean isBroker);

	public abstract CreateEditingResponse registerEditingRequest(CreateEditingRequest createdEditingRequest);

	public CreateEditingRequest initializeCreateEditingRequestFromAppForm(AppFormDTO appForm) {
		CreateEditingRequest createEditingRequest = new CreateEditingRequest();
		appFormMapper.asAppFormDTOToCreateEditingRequest(appForm, createEditingRequest);
		return createEditingRequest;
	}

	public EditingRequest getRegisteredEditingRequest(CreateEditingResponse createEditingReponse) {
		return editingService.getEditingRequestById(createEditingReponse.getRequestId());
	}

	public EditingRequest updateEditingRequestStatus(EditingRequest editingRequest,
			EditingRequestStatus newRequestStatus) {
		editingRequest.setStatus(newRequestStatus);
		return documentGenerationService.updateEditingRequest(editingRequest);
	}


	public DocumentGenerationResponse generateDocumentFromAppForm(SecurityContext context, AppFormDTO appForm) {
		return generateDocumentFromAppForm(context, appForm, initLanguage(appForm), true);
	}

	public DocumentGenerationResponse generateDocumentFromAppForm(SecurityContext context, AppFormDTO appForm,
			String language, boolean isBroker) {

		boolean isMailCanBeSent = followUpValidationService.validateBeforeSent(appForm);
		if (!isMailCanBeSent) {
			return new DocumentGenerationResponse();
		}
		
		AppFormDTO enrichedAppForm = enrich(appForm);
		CreateEditingRequest createEditingRequest = initializeCreateEditingRequestFromAppForm(enrichedAppForm);
		CreateEditingResponse createEditingReponse = registerEditingRequest(createEditingRequest);
		EditingRequest editingRequest = getRegisteredEditingRequest(createEditingReponse);
		EditingRequest editingRequestAfterXmlGeneration = generateDocument(context, editingRequest, enrichedAppForm,
				language, isBroker);
		updateEditingRequestStatus(editingRequestAfterXmlGeneration, EditingRequestStatus.XML_GENERATED);
		DocumentGenerationResponse response = generateFile(context, editingRequestAfterXmlGeneration);
		updateEditingRequestStatus(editingRequestAfterXmlGeneration, EditingRequestStatus.GENERATED);
		return response;
	}

	public DocumentGenerationResponse generateFile(SecurityContext context, EditingRequest editingRequest) {
		return documentGenerationService.generateFile(context, editingRequest);
	}

	AppFormDTO enrich(AppFormDTO appForm) {
		StepTypeDTO currentStep = StepTypeDTO.getStepType(getStep(appForm));
		AppFormDTO enrichedAppFormDTO = webiaStepEnricher.enrichAppForm(appForm);
		switch (currentStep) {
		case GENERATE_DOCUMENTATION:
			enrichedAppFormDTO = lissiaStepEnricher.enrichAppForm(appForm);
			break;
		default:
			break;
		}
		return enrichedAppFormDTO;
	}

	protected String getStep(AppFormDTO appFormDTO) {
		return followUpHelper.getStep(appFormDTO);
	}

	public SubscriptionFollowUp initializeSubscriptionFollowUp(AppFormDTO enrichedAppForm, boolean isBroker) {
		SubscriptionFollowUp subscriptionFollowUp = appFormMapper.asSouscriptionFollow(enrichedAppForm);
		subscriptionFollowUp.setFunds(getFollowUpFunds(enrichedAppForm));
		subscriptionFollowUp.setEntryFees(getEntryFees(enrichedAppForm));
		subscriptionFollowUp.setAdminFees(getAdminFees(enrichedAppForm));
		subscriptionFollowUp.setPaymentDetails(getPaymentDetail(enrichedAppForm, isBroker));
		return subscriptionFollowUp;
	}

	public abstract List<SubscriptionFollowUp.Fund> getFollowUpFunds(AppFormDTO enrichedAppForm);

	public abstract SubscriptionFollowUp.EntryFees getEntryFees(AppFormDTO appForm);

	public abstract SubscriptionFollowUp.AdminFees getAdminFees(AppFormDTO appForm);

	public abstract PaymentDetails getPaymentDetail(AppFormDTO appForm, boolean isRiskFees);

	public abstract String initLanguage(AppFormDTO enrichedAppForm);

	public boolean isBrokerEwealins(AppFormDTO enrichedAppForm) {
		return followUpHelper.isBrokerWealins(enrichedAppForm);
	}

	public boolean isAgentWealinsAssetManager(String agentId) {
		return followUpHelper.isAgentWealinsAssetManager(agentId);
	}

}
