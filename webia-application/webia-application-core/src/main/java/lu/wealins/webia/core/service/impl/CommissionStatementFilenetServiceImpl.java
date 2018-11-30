package lu.wealins.webia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.DocumentIdReservationRequest;
import lu.wealins.common.dto.webia.services.DocumentIdReservationResponse;
import lu.wealins.webia.core.service.CommissionStatementFilenetService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.SaveDocumentIntoFileNetRequest;
import lu.wealins.webia.ws.rest.request.SaveDocumentIntoFileNetResponse;

@Service
public class CommissionStatementFilenetServiceImpl implements CommissionStatementFilenetService {

	private static final String EDM_RESERVE_DOCUMENT_ID = "reserveCommissionDocumentId";
	private static final String EDM_SAVE_DOCUMENT = "saveCommissionDocumentIntoFileNet";
	private static final String EDM_UPLOAD_DOCUMENT = "uploadDocumentIntoFileNet";
	

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public DocumentIdReservationResponse reserveDocumentId(DocumentIdReservationRequest request) {
		return restClientUtils.post(EDM_RESERVE_DOCUMENT_ID, request, DocumentIdReservationResponse.class);
	}

	@Override
	public SaveDocumentIntoFileNetResponse saveDocumentIntoFileNet(SaveDocumentIntoFileNetRequest request) {
		return restClientUtils.post(EDM_SAVE_DOCUMENT, request, SaveDocumentIntoFileNetResponse.class);
	}

	@Override
	public SaveDocumentIntoFileNetResponse uploadDocumentIntoFileNet(SaveDocumentIntoFileNetRequest request) {
		return restClientUtils.post(EDM_UPLOAD_DOCUMENT, request, SaveDocumentIntoFileNetResponse.class);
	}

}
