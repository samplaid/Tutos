package lu.wealins.webia.core.service;

import javax.ws.rs.core.SecurityContext;

import lu.wealins.webia.ws.rest.request.EditingRequest;

public interface MandatGestionDocumentGenerationService {

	EditingRequest generateDocumentMandatGestion(SecurityContext context, EditingRequest request);
	
	EditingRequest generateDocumentMandatGestionEnd(SecurityContext context, EditingRequest request);

}
