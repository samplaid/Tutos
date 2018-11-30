package lu.wealins.webia.ws.rest.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.webia.core.service.DocumentGenerationService;
import lu.wealins.webia.core.service.DocumentGenerationServiceBase;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.service.TaxBatchService;
import lu.wealins.webia.core.service.TransferService;
import lu.wealins.webia.core.service.helper.FrenchTaxHelper;
import lu.wealins.webia.core.service.validations.SurrenderDocumentValidationService;
import lu.wealins.webia.ws.rest.DocumentGenerationRESTService;
import lu.wealins.webia.ws.rest.request.DocumentGenerationRequest;
import lu.wealins.webia.ws.rest.request.DocumentGenerationResponse;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Component
public class DocumentGenerationRESTServiceImpl implements DocumentGenerationRESTService {

	SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private DocumentGenerationService documentGenerationService;

	@Autowired
	private FrenchTaxHelper frenchTaxHelper;

	@Autowired
	TaxBatchService taxService;

	@Autowired
	SurrenderDocumentValidationService surrenderValidationService;
	
	@Autowired
	@Qualifier("FollowUpDocumentGeneratorService")
	DocumentGenerationServiceBase documentGenerationServiceBase;

	@Override
	public DocumentGenerationResponse generateManagementMandate(SecurityContext context, DocumentGenerationRequest request) {
		DocumentGenerationResponse response = new DocumentGenerationResponse();
		response.setRequest(documentGenerationService.generateManagementMandate(context, request.getRequest()));
		return response;
	}

	@Override
	public DocumentGenerationResponse generateManagementMandateEnd(SecurityContext context, DocumentGenerationRequest request) {
		DocumentGenerationResponse response = new DocumentGenerationResponse();
		response.setRequest(documentGenerationService.generateDocument(context, request.getRequest()));
		return response;
	}
	
	@Override
	public DocumentGenerationResponse generateDocument(SecurityContext context, DocumentGenerationRequest request) {
		DocumentGenerationResponse response = new DocumentGenerationResponse();
		response.setRequest(documentGenerationService.generateDocument(context, request.getRequest()));
		return response;
	}
	
	@Override
	public DocumentGenerationResponse uploadDocument(SecurityContext context, DocumentGenerationRequest request){
		DocumentGenerationResponse response = new DocumentGenerationResponse();
		response.setRequest(documentGenerationService.uploadDocument(request.getRequest()));
		return response;
	}

	@Override
	public DocumentGenerationResponse generatefollowUp(SecurityContext context, AppFormDTO request) {
		return documentGenerationServiceBase.generateDocumentFromAppForm(context, request);
	}

	@Override
	public DocumentGenerationResponse generateFrenchTaxAvenant(SecurityContext context,
			EditingRequest editingRequest) {

		Optional<String> validationResponse = surrenderValidationService.validate(editingRequest);
		if (validationResponse.isPresent()) {
			DocumentGenerationResponse response = new DocumentGenerationResponse();
			response.setErrorMessage(validationResponse.get());
			return response;
		}
		return frenchTaxHelper.processFrenchTaxDataAndGenerateEditingRequest(editingRequest.getPolicy().trim(),
					editingRequest.getEventDate(),editingRequest.getFrenchTaxable());
	}

}
