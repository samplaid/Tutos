package lu.wealins.webia.core.service;

import lu.wealins.common.dto.webia.services.DocumentIdReservationRequest;
import lu.wealins.common.dto.webia.services.DocumentIdReservationResponse;
import lu.wealins.webia.ws.rest.request.SaveDocumentIntoFileNetRequest;
import lu.wealins.webia.ws.rest.request.SaveDocumentIntoFileNetResponse;

public interface CommissionStatementFilenetService {

	/**
	 * Reserve a document id on fileNet
	 * 
	 * @param request, the request with the document class
	 */
	DocumentIdReservationResponse reserveDocumentId(DocumentIdReservationRequest request);

	SaveDocumentIntoFileNetResponse saveDocumentIntoFileNet(SaveDocumentIntoFileNetRequest request);

	/**
	 * Method used to import the historic of commission statement To upload a file into filenet prefer the method
	 * 
	 * @see lu.wealins.webia.core.service.CommissionStatementFilenetService#saveDocumentIntoFileNet(SaveDocumentIntoFileNetRequest)
	 * 
	 */
	@Deprecated
	SaveDocumentIntoFileNetResponse uploadDocumentIntoFileNet(SaveDocumentIntoFileNetRequest request);

}
