package lu.wealins.webia.core.service.document;

import org.springframework.stereotype.Service;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.webia.core.service.impl.PolicyDocumentService;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Service("SurrenderDocumentService")
public class SurrenderDocumentServiceImpl extends PolicyDocumentService {

	@Override
	protected Document createSpecificDocument(EditingRequest editingRequest, PolicyDTO policyDTO, String productCountry,
			String language, String userId) {
		return null;
	}

	@Override
	protected DocumentType getDocumentType() {
		return DocumentType.SURRENDER;
	}

	@Override
	protected String getXmlPath(EditingRequest editingRequest, String policyId) {
		return null;
	}

}
