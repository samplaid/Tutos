package lu.wealins.webia.core.service.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.TransferSecurityDTO;
import lu.wealins.editing.common.webia.Account;
import lu.wealins.editing.common.webia.CorrespondenceAddress;
import lu.wealins.editing.common.webia.CoverLetter;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.PersonLight;
import lu.wealins.editing.common.webia.Policy;
import lu.wealins.editing.common.webia.SecuritiesTransfer;
import lu.wealins.editing.common.webia.SecuritiesTransfer.From;
import lu.wealins.editing.common.webia.SecuritiesTransfer.From.SecurityToTransfer;
import lu.wealins.webia.core.service.LiabilityCountryService;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.document.trait.PolicyBasedDocumentGenerationService;
import lu.wealins.webia.core.service.document.trait.TransactionFormBasedDocument;
import lu.wealins.webia.core.service.helper.IbanHelper;
import lu.wealins.webia.core.service.impl.PolicyDocumentService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Service
public class FaxSecuritiesTransferDocumentGenerationService extends PolicyDocumentService implements PolicyBasedDocumentGenerationService, TransactionFormBasedDocument {

	private static final String DOCUMENT_SUBJECT = "Partial Surrender";

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private LiabilityFundService fundService;

	@Autowired
	private LiabilityCountryService countryService;

	@Autowired
	private IbanHelper ibanHelper;

	@Override
	protected Document createSpecificDocument(EditingRequest editingRequest, PolicyDTO policyDTO, String productCountry, String language, String userId) {

		// At first, retrieve the transaction form DTO
		TransactionFormDTO transactionFormDTO = retrieveTransactionForm(restClientUtils, editingRequest.getWorkflowItemId());
		TransferDTO securitiesTransferForm = CollectionUtils.extractSingleton(transactionFormDTO.getSecuritiesTransfer());

		SecuritiesTransfer securitiesTransfer = generateSecuritiesTransfer(policyDTO, securitiesTransferForm);

		Policy policy = getXmlPolicy(policyDTO, new ArrayList<>()); // TODO decide if policy holders are required or not...

		Document document = createPolicyDocument(editingRequest.getWorkflowItemId(), policyDTO, productCountry, language, policy, userId);

		document.setCoverLetter(createSpecificCoverLetter(transactionFormDTO));
		document.setSecuritiesTransfer(securitiesTransfer);
		return document;
	}

	/**
	 * Build and return a {@link CoverLetter} specific to the Fax Securities Transfer document. This cover letter will contain the deposit bank. Actually we can't simply override generateCoverLetter
	 * method, since it wouldn't be possible to retrieve the TransactionFormDTO
	 * 
	 * @param transactionFormDTO the transaction form DTO
	 * @return a {@link CoverLetter} specific to the Securities Transfer document and displaying the deposit bank.
	 */
	private CoverLetter createSpecificCoverLetter(TransactionFormDTO transactionFormDTO) {
		
		// retrieve the deposit bank
		TransferDTO securitiesTransferForm = CollectionUtils.extractSingleton(transactionFormDTO.getSecuritiesTransfer());
		FundDTO selectedFund = fundService.getFund(securitiesTransferForm.getFdsId());
		AgentDTO depositBank = selectedFund.getDepositBankAgent();
		
		CoverLetter coverLetter = new CoverLetter();
		CorrespondenceAddress correspondenceAddress = new CorrespondenceAddress();
		correspondenceAddress.setPhone(depositBank.getTelephone());
		correspondenceAddress.setFax(depositBank.getFax());
		correspondenceAddress.setEmail(depositBank.getEmail());
		correspondenceAddress.setCity(depositBank.getTown());
		correspondenceAddress.setCountryCode(depositBank.getCountryCode());
		correspondenceAddress.setZipCode(depositBank.getPostcode());
		correspondenceAddress.setCountry(countryService.getCountry(depositBank.getCountry()).getName());
		correspondenceAddress.setStreet(Arrays.asList(depositBank.getAddressLine1(), depositBank.getAddressLine2(), depositBank.getAddressLine3(), depositBank.getAddressLine4())
				.stream()
				.filter(line -> !StringUtils.isEmpty(line))
				.filter(line -> !line.matches("[Cc]/[Oo]\\s+.*"))
				.collect(Collectors.joining("\n")));
		PersonLight person = new PersonLight();
		person.setFirstName(depositBank.getName());
		correspondenceAddress.setPersons(Arrays.asList(person));
		coverLetter.setCorrespondenceAddress(correspondenceAddress);
		
		return coverLetter;
	}

	private SecuritiesTransfer generateSecuritiesTransfer(PolicyDTO policyDTO, TransferDTO securitiesTransferForm) {
		SecuritiesTransfer securitiesTransfer = new SecuritiesTransfer();

		try {

			securitiesTransfer.setPolicyId(policyDTO.getPolId());
			securitiesTransfer.setComment(securitiesTransferForm.getTransferSecuritiesComment());
			securitiesTransfer.setSubject(DOCUMENT_SUBJECT);
			securitiesTransfer.setCommunication(StringUtils.defaultString(securitiesTransferForm.getTrfComm()) + " " + StringUtils.defaultString(securitiesTransferForm.getPolicyId()));

			// from
			From from = new From();
			from.setAccountNumber(ibanHelper.formatIban(securitiesTransferForm.getIbanDonOrd()));
			List<SecurityToTransfer> securitiesToTransfer = new ArrayList<>();
			for (TransferSecurityDTO transferSecurityDTO : securitiesTransferForm.getTransferSecurities()) {
				SecurityToTransfer securityToTransfer = new SecurityToTransfer();
				securityToTransfer.setFundName(transferSecurityDTO.getFundName());
				securityToTransfer.setISIN(transferSecurityDTO.getIsin());
				securityToTransfer.setNumberOfUnit(transferSecurityDTO.getUnits());
				securitiesToTransfer.add(securityToTransfer);
			}
			from.setSecurityToTransfers(securitiesToTransfer);
			securitiesTransfer.setFrom(from);

			// to
			Account account = new Account();
			account.setBIC(securitiesTransferForm.getSwiftBenef());
			account.setAccountNumber(ibanHelper.formatIban(securitiesTransferForm.getIbanBenef()));
			account.setIban(securitiesTransferForm.getIbanBenef());
			account.setHolder(securitiesTransferForm.getLibBenef());
			securitiesTransfer.setToAccount(account);
		} catch (Exception e) {
			log.error("Error during the generation of the fax for the securities transfer. policy: " + policyDTO.getPolId(), e);
		}

		return securitiesTransfer;
	}

	@Override
	protected DocumentType getDocumentType() {
		return DocumentType.FAX_SECURITIES_TRANSFER;
	}

	@Override
	protected String getXmlPath(EditingRequest editingRequest, String policyId) {
		return buildXmlPath(policyId, filePath);
	}
}
