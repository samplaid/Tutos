package lu.wealins.webia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.DocumentIdReservationRequest;
import lu.wealins.common.dto.webia.services.DocumentIdReservationResponse;
import lu.wealins.webia.core.service.FileNetService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.SaveDocumentIntoFileNetRequest;
import lu.wealins.webia.ws.rest.request.SaveDocumentIntoFileNetResponse;

@Service
@Deprecated
public class FileNetServiceImpl implements FileNetService {

	private static final String EDM_RESERVE_DOCUMENT_ID = "reserveFileNetDocumentId";
	private static final String EDM_SAVE_DOCUMENT = "saveDocumentIntoFileNet";
	

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

}
