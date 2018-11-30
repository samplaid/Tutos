package lu.wealins.webia.core.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxSendingDTO;
import lu.wealins.editing.common.webia.CoverLetter;
import lu.wealins.editing.common.webia.Endorsement;
import lu.wealins.editing.common.webia.Header;
import lu.wealins.editing.common.webia.MailingAddress;
import lu.wealins.editing.common.webia.Policy;
import lu.wealins.webia.core.service.DocumentService;
import lu.wealins.webia.core.service.WebiaTransactionTaxService;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.EditingUser;

@Service("SurrrenderDaliDocumentGenerationService")
@Transactional(readOnly = true)
public class SurrrenderDaliDocumentGenerationServiceImpl extends SurrenderDocumentGenerationServiceCommonImpl {

	private static final Logger log = LoggerFactory.getLogger(SurrrenderDaliDocumentGenerationServiceImpl.class);

	@Autowired
	private WebiaTransactionTaxService transactionService;

	@Autowired
	private DocumentService documentService;

	@Override
	protected Header generateHeader(EditingUser creationUser, String language) {
		Header header = documentService.generateHeader(creationUser, DocumentType.ANNEX_FISC, language, null, null);
		return header;
	}

	@Override
	protected CoverLetter generateCoverLetter(TransactionTaxDTO transactionTax) {
		CoverLetter coverLetter = new CoverLetter();
		TransactionTaxSendingDTO transactionTaxSending = transactionService
				.getTransactionTaxSending(transactionTax.getTransactionTaxId());
		if (transactionTaxSending != null && transactionTaxSending.getSendingTitle() != null) {
			coverLetter.setTitleId(StringUtils.strip(transactionTaxSending.getSendingTitle()));
		}

		return coverLetter;
	}

	@Override
	protected List<MailingAddress> generateMailingAddress(TransactionTaxDTO transactionTax) {
		return null;
	}

	@Override
	protected EditingUser updateCreationUserByEmail(EditingUser creationUser, TransactionTaxDTO transactionTax) {

		TransactionTaxSendingDTO transactionTaxSending = transactionService
				.getTransactionTaxSending(transactionTax.getTransactionTaxId());

		if (transactionTaxSending != null && !StringUtils.isBlank(transactionTaxSending.getSendingAddressLine4())) {
			log.info("Now using the user of the transactionTax with id " + transactionTax.getOriginId()
					+ " - email : " + transactionTaxSending.getSendingAddressLine4());

			creationUser = new EditingUser();
			creationUser.setEmail(StringUtils.strip(transactionTaxSending.getSendingAddressLine4()));
		}
		return creationUser;
	}

	@Override
	protected Endorsement updateEndorsementByPolicy(Endorsement endorsement, TransactionTaxDTO transactionTax,
			EditingRequest editingRequest) {
		Policy policy = new Policy();
		policy.setProduct(editingRequest.getProduct());
		policy.setPolicyId(transactionTax.getPolicy());
		endorsement.setPolicy(policy);
		return endorsement;
	}

	@Override
	protected CoverLetter updateCoverLetterByPolicy(CoverLetter coverLetter, TransactionTaxDTO transactionTax) {
		return coverLetter;
	}

}
