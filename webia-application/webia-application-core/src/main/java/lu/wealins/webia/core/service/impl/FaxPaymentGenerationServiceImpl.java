package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.AgentContactDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.webia.services.EditingRequestStatus;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.editing.common.webia.Account;
import lu.wealins.editing.common.webia.AmountType;
import lu.wealins.editing.common.webia.BankTransfer;
import lu.wealins.editing.common.webia.BankTransfer.Transfer;
import lu.wealins.editing.common.webia.BankTransfer.Transfer.DebitAccount;
import lu.wealins.editing.common.webia.CorrespondenceAddress;
import lu.wealins.editing.common.webia.CoverLetter;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.Documents;
import lu.wealins.editing.common.webia.Header;
import lu.wealins.editing.common.webia.PersonLight;
import lu.wealins.webia.core.mapper.PersonMapper;
import lu.wealins.webia.core.service.DocumentService;
import lu.wealins.webia.core.service.FaxPaymentGenerationService;
import lu.wealins.webia.core.service.LiabilityAgentContactService;
import lu.wealins.webia.core.service.LiabilityAgentService;
import lu.wealins.webia.core.service.TransferService;
import lu.wealins.webia.core.service.helper.FollowUpDocumentContentHelper;
import lu.wealins.webia.core.service.helper.IbanHelper;
import lu.wealins.webia.core.utils.Constantes;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.EditingUser;

@Service
@Transactional(readOnly = true)
public class FaxPaymentGenerationServiceImpl implements FaxPaymentGenerationService {

	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FaxPaymentGenerationServiceImpl.class);

	
	private final String PARTIAL_SURRENDER = "Partial surrender";
	
	/**
	 * Services
	 */
	@Autowired
	private DocumentService documentGenerationService;

	@Autowired
	private TransferService transferService;
	
	@Autowired
	private LiabilityAgentContactService liabilityAgentContactService;
	
	@Autowired
	private LiabilityAgentService liabilityAgentService;
	
	@Autowired
	private PersonMapper personMapper;
	
	@Autowired
	private FollowUpDocumentContentHelper followUpDocumentContentHelper;
	
	// @Value("${fax.payment.path}")
	@Value("${editique.xml.path}")
	private String faxFileLocation;

	@Autowired
	private IbanHelper ibanHelper;

	@Override
	public EditingRequest generateFaxPayment(SecurityContext context, EditingRequest editingRequest) {

		try {
			Assert.notNull(editingRequest, "The edition request can not be null");

			editingRequest.setStatus(EditingRequestStatus.IN_PROGRESS);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);

			EditingUser creationUser = editingRequest.getCreationUser();

			Assert.notNull(creationUser, "The creation user of the document generation request cannot be null.");
			Assert.isTrue(StringUtils.isNotBlank(editingRequest.getTransferIds()), "The transfers list cannot be null.");

			LocalDateTime date = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constantes.YYYYMMDDHHMMSSSSS);
			String today = date.format(formatter);
			String documentPath = faxFileLocation + "/FP-" + editingRequest.getId().toString() + "-" + today + ".xml";
			String saveResponse = documentGenerationService.saveXmlDocuments(createDocument(editingRequest), documentPath);

			editingRequest.setInputStreamPath(saveResponse);
			editingRequest.setStatus(EditingRequestStatus.XML_GENERATED);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);

		} catch (Exception e) {
			editingRequest.setStatus(EditingRequestStatus.IN_ERROR);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);

			LOGGER.error("Error occurred during the Fax Payment generation for transferIds "+ editingRequest.getTransferIds() , e);
		}

		return editingRequest;
	}


	private Documents createDocument(EditingRequest editingRequest) {
		Documents documentParentNode = new Documents();
		List<Document> documents = new ArrayList<>();
		LOGGER.info("Creating fax payment related to the following list of Transfer Ids :" + editingRequest.getTransferIds());

		Document document = new Document();
		document.setDocumentType(DocumentType.FAX_PAYMENT.val());
		document.setHeader(createHeader(editingRequest));
		document.setCoverLetter(createCoverLetter(editingRequest));
		document.setBankTransfer(createBankTransfer(editingRequest));
		documents.add(document);
		documentParentNode.setDocuments(documents);
		return documentParentNode;
	}

	/**
	 * Create the acceptance report header
	 * 
	 * @return
	 */
	private Header createHeader(EditingRequest editingRequest) {
		return documentGenerationService.generateHeader(editingRequest.getCreationUser(), editingRequest.getDocumentType(), "FR", null, UUID.randomUUID());
	}

	/**
	 * @param step
	 * @param mappedItem
	 * @return
	 */
	private BankTransfer createBankTransfer(EditingRequest editingRequest) {
		BankTransfer bankTransfer = new BankTransfer();
		BigDecimal total = BigDecimal.ZERO;
		String currency = null;
		List<Transfer> transfers = new ArrayList<Transfer>();
		
		//Fax contains only one transfer object but with multi debitAccount to one creditAccount and sometime a transitAccount (temporary wealins account)
		Transfer transfer = new Transfer();
		Account creditAccount = new Account();
		Account transitAccount = new Account();
		List<DebitAccount> debitAccounts = new ArrayList<DebitAccount>();
		
		// retrieve the Transfers data related to the request (for a same bank/currency/beneficiary )
		String[] transferIdsString = editingRequest.getTransferIds().replaceAll(" ", "").split(",");
		List<Long> transferIds = new ArrayList<Long>();
		for (int i = 0; i < transferIdsString.length; i++) {
			transferIds.add(Long.valueOf(transferIdsString[i]));
		}
		Collection<TransferDTO> transferDTOs = transferService.getTransfers(transferIds);
		for (Iterator iterator = transferDTOs.iterator(); iterator.hasNext();) {
			TransferDTO transferDTO = (TransferDTO) iterator.next();
			total = total.add(transferDTO.getTrfMt());
			DebitAccount newDebitAccount = new DebitAccount();
			newDebitAccount.setAccountNumber(ibanHelper.formatIban(transferDTO.getIbanDonOrd()));
			newDebitAccount.setAmount(createAmountType(transferDTO.getTrfMt(), transferDTO.getTrfCurrency()));
			newDebitAccount.setCommunication(transferDTO.getTrfComm());
			debitAccounts.add(newDebitAccount);
			if ( ! iterator.hasNext()) { // all iterator should have the same currency and beneficiary
				currency = transferDTO.getTrfCurrency();
				creditAccount.setAccountNumber(ibanHelper.formatIban(transferDTO.getIbanBenef()));
				creditAccount.setBIC(transferDTO.getSwiftBenef());
				creditAccount.setHolder(transferDTO.getLibBenef());
				
				//TODO : there is no data related to Transit Account yet in webia...this need a config table ??
				//transitAccount.setAccountNumber(transferDTO.getIbanTransit());
				//transitAccount.setBIC(transferDTO.getSwiftTransit());
				//transitAccount.setHolder(transferDTO.getLibTransit());
			}
		}
		transfer.setDebitAccounts(debitAccounts);
		transfer.setCreditAccount(creditAccount);
		if (StringUtils.isNotBlank(transitAccount.getAccountNumber())){
			transfer.setTransitAccount(transitAccount);
		}
		transfer.setTransferRef("");
		transfer.setTotalAmount(createAmountType(total, currency));
		
		transfers.add(transfer);
		bankTransfer.setTransfers(transfers);
		bankTransfer.setSubject(PARTIAL_SURRENDER);
		return bankTransfer;
	}
	
	private AmountType createAmountType(BigDecimal value, String currency){
		AmountType amountType =  new AmountType();
		amountType.setValue(value);
		amountType.setCurrencyCode(currency);
		return amountType;
	}

	private CoverLetter createCoverLetter(EditingRequest editingRequest ) {
		CoverLetter coverLetter = new CoverLetter();
		
		AgentContactDTO agentContact = liabilityAgentContactService.find(Integer.valueOf(editingRequest.getAgent())); // the contact agent has been saved in agent value in the request
		AgentLightDTO contact = agentContact.getContact();
		
		AgentLightDTO depositBankAgent = liabilityAgentService.getAgentLite(agentContact.getAgentId()); // get the parent agentId
				
		CorrespondenceAddress correspondenceAddress = personMapper.asCorrespondenceAddress(contact);
		
		correspondenceAddress.setName(depositBankAgent.getName());
		if (StringUtils.isBlank(correspondenceAddress.getEmail())){
			correspondenceAddress.setEmail(depositBankAgent.getEmail());
		}
		if (StringUtils.isBlank(correspondenceAddress.getFax())){
			correspondenceAddress.setFax(depositBankAgent.getFax());
		}		
		// set the person in the correspondenceAddress
		PersonLight contactPerson = personMapper.asPersonLight(contact);
		correspondenceAddress.setPersons(Arrays.asList(contactPerson));
		if (contactPerson != null) {
			coverLetter.setTitleId(contactPerson.getTitleId());
		}
		
		String agentLanguage = followUpDocumentContentHelper.getAgentLanguage(contact);
		coverLetter.setLanguage(agentLanguage);
	
		coverLetter.setCorrespondenceAddress(correspondenceAddress);		

		return coverLetter;

	}
	
}
