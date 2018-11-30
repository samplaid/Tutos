package lu.wealins.webia.core.service.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.enums.AgentContactFunction;
import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.editing.common.webia.Account;
import lu.wealins.editing.common.webia.CorrespondenceAddress;
import lu.wealins.editing.common.webia.CoverLetter;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.PersonLight;
import lu.wealins.editing.common.webia.Policy;
import lu.wealins.editing.common.webia.SecuritiesTransfer;
import lu.wealins.editing.common.webia.SecuritiesTransfer.From;
import lu.wealins.editing.common.webia.SecuritiesTransfer.From.SecurityToTransfer;
import lu.wealins.webia.core.service.DocumentService;
import lu.wealins.webia.core.service.LiabilityCountryService;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.document.trait.AgentBasedDocumentGenerationService;
import lu.wealins.webia.core.service.document.trait.TransactionFormBasedDocument;
import lu.wealins.webia.core.service.helper.IbanHelper;
import lu.wealins.webia.core.service.impl.AbstractFundFormService;
import lu.wealins.webia.core.service.impl.PolicyDocumentService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.TranscoType;

@Service
public class FaxSurrenderDepositBankDocumentGenerationService extends PolicyDocumentService implements AgentBasedDocumentGenerationService, TransactionFormBasedDocument {

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private LiabilityWorkflowService workflowService;

	@Autowired
	private AbstractFundFormService<FundTransactionFormDTO> fundFormService;

	@Autowired
	private LiabilityFundService fundService;

	@Autowired
	private LiabilityCountryService countryService;

	@Autowired
	private DocumentService documentService;

	private static final String SENDER_WEALINS = "CPS";
	private static final String SENDER_SERVICE_COMPTA = "ACCOUNTING";

	@Autowired
	private IbanHelper ibanHelper;

	@Override
	protected Document createSpecificDocument(EditingRequest editingRequest, PolicyDTO policyDTO, String productCountry, String language, String userId) {

		// retrieve the current step
		StepTypeDTO currentStep = StepTypeDTO.getStepType(workflowService.getWorkflowGeneralInformation(editingRequest.getWorkflowItemId().toString(), userId).getCurrentStepName());

		// At first, retrieve the transaction form DTO and the agent
		TransactionFormDTO transactionFormDTO = retrieveTransactionForm(restClientUtils, editingRequest.getWorkflowItemId());
		TransferDTO securitiesTransferForm = transactionFormDTO.getSecuritiesTransfer().stream().findFirst().get();
		final AgentLightDTO agent = StringUtils.isEmpty(editingRequest.getAgent()) ? null : agentService.getAgentLite(editingRequest.getAgent());

		AgentLightDTO coverLetterContact = extractContactAgent(securitiesTransferForm);

		fundFormService.enrichFunds(transactionFormDTO.getFundTransactionForms());

		SecuritiesTransfer securitiesTransfer = generateSecuritiesTransfer(securitiesTransferForm, currentStep, policyDTO, transactionFormDTO.getFundTransactionForms());

		Policy policy = getXmlPolicy(policyDTO, new ArrayList<>()); // TODO decide if policy holders are required or not...

		Document document = createPolicyDocument(editingRequest.getWorkflowItemId(), policyDTO, productCountry, extractDocumentLanguage(coverLetterContact), policy, userId);
		document.setCoverLetter(createSpecificCoverLetter(coverLetterContact, agent));

		document.setSecuritiesTransfer(securitiesTransfer);		
		return document;
	}

	private SecuritiesTransfer generateSecuritiesTransfer(TransferDTO securitiesTransferForm, StepTypeDTO currentStep, PolicyDTO policyDTO, Collection<FundTransactionFormDTO> fundTransactions) {
		SecuritiesTransfer securitiesTransfer = new SecuritiesTransfer();

		// retrieve the fund transaction corresponding to the fund
		FundTransactionFormDTO fundTransactionForm = fundTransactions.stream().filter(FundTransactionForm -> FundTransactionForm.getFundId().equals(securitiesTransferForm.getFdsId())).findFirst()
				.get();


		// set the sender according to the
		if (StepTypeDTO.ANALYSIS.equals(currentStep) || StepTypeDTO.VALIDATE_ANALYSIS.equals(currentStep)) {
			securitiesTransfer.setSender(SENDER_WEALINS);
		} else {
			securitiesTransfer.setSender(SENDER_SERVICE_COMPTA);
		}

		// TODO : set closing account
		Account closingAccount = new Account();
		closingAccount.setAccountNumber(ibanHelper.formatIban(securitiesTransferForm.getIbanBenef()));
		// closingAccount.setBankName("dummy bank name");
		// closingAccount.setBIC("dummy bank");
		// closingAccount.setCurrency("dummy currency");
		// closingAccount.setHolder("dummy holder");
		// closingAccount.setIban("dummy iban");
		securitiesTransfer.setClosingAccount(closingAccount);

		Account toAccount = new Account();
		toAccount.setAccountNumber(ibanHelper.formatIban(securitiesTransferForm.getIbanBenef()));
		// toAccount.setBankName("dummy bank name");
		toAccount.setBIC(securitiesTransferForm.getSwiftBenef());
		// toAccount.setCurrency("dummy currency");
		toAccount.setHolder(securitiesTransferForm.getLibBenef());
		// toAccount.setIban("dummy iban");
		securitiesTransfer.setToAccount(toAccount);

		securitiesTransfer.setSubject("Total Surrender");
		securitiesTransfer.setTransferRef("Transfer Ref (TBD)");
		securitiesTransfer.setComment(org.apache.commons.lang3.StringUtils.trimToNull(securitiesTransferForm.getTransferSecuritiesComment()));
		securitiesTransfer.setCommunication("Total Surrender " + policyDTO.getPolId());

		// set from
		From from = new From();
		from.setAccountNumber(ibanHelper.formatIban(securitiesTransferForm.getIbanDonOrd()));
		SecurityToTransfer securityToTransfer = new SecurityToTransfer();
		securityToTransfer.setFundName(fundTransactionForm.getFund().getName());
		securityToTransfer.setISIN(fundTransactionForm.getFund().getIsinCode());
		securityToTransfer.setNumberOfUnit(fundTransactionForm.getUnits());
		securityToTransfer.setClosed(fundTransactionForm.getClosure());
		from.setSecurityToTransfers(Arrays.asList(securityToTransfer));
		securitiesTransfer.setFrom(from);

		return securitiesTransfer;
	}

	/**
	 * Build and return a {@link CoverLetter} specific to the Fax Securities Transfer document. This cover letter will contain the deposit bank. Actually we can't simply override generateCoverLetter
	 * method, since it wouldn't be possible to retrieve the TransactionFormDTO
	 * 
	 * @param coverLetterContact the cover letter contact
	 * @param agent the agent (i.e. the deposit bank)
	 * @return a {@link CoverLetter} specific to the Securities Transfer document and displaying the deposit bank.
	 */
	private CoverLetter createSpecificCoverLetter(AgentLightDTO coverLetterContact, AgentLightDTO agent) {

		CoverLetter coverLetter = new CoverLetter();
		CorrespondenceAddress correspondenceAddress = new CorrespondenceAddress();
		correspondenceAddress.setPhone(coverLetterContact.getTelephone());
		correspondenceAddress.setFax(coverLetterContact.getFax());
		correspondenceAddress.setEmail(coverLetterContact.getEmail());
		correspondenceAddress.setCity(coverLetterContact.getTown());
		correspondenceAddress.setCountryCode(coverLetterContact.getCountryCode());
		correspondenceAddress.setZipCode(coverLetterContact.getPostcode());
		correspondenceAddress.setCountry(coverLetterContact.getCountry() != null ? (countryService.getCountry(coverLetterContact.getCountry()).getName()) : null);
		correspondenceAddress.setName(agent != null ? agent.getName() : null);
		correspondenceAddress
				.setStreet(Arrays.asList(coverLetterContact.getAddressLine1(), coverLetterContact.getAddressLine2(), coverLetterContact.getAddressLine3(), coverLetterContact.getAddressLine4())
				.stream()
				.filter(line -> !StringUtils.isEmpty(line))
				.filter(line -> !line.matches("[Cc]/[Oo]\\s+.*"))
				.collect(Collectors.joining("\n")));
		PersonLight person = new PersonLight();
		person.setFirstName(coverLetterContact.getFirstname());
		person.setLastName(coverLetterContact.getName());
		correspondenceAddress.setPersons(Arrays.asList(person));
		coverLetter.setCorrespondenceAddress(correspondenceAddress);
		coverLetter.setTitleId(transcodeTitleId(coverLetterContact.getTitle()));

		return coverLetter;
	}

	/**
	 * Extract an unique {@link AgentLightDTO} that will be used as contact for the cover letter and the communication by mail.
	 * 
	 * @param securitiesTransferForm the securities transfer form
	 * @return an unique {@link AgentLightDTO} that will be used as contact for the cover letter and the communication by mail.
	 */
	private AgentLightDTO extractContactAgent(TransferDTO securitiesTransferForm) {

		// retrieve the deposit bank
		FundDTO selectedFund = fundService.getFund(securitiesTransferForm.getFdsId());
		AgentDTO depositBank = selectedFund.getDepositBankAgent();
		
		// try to retrieve the payment contact or the cps contact
		Collection<AgentLightDTO> possibleContacts = new ArrayList<>();
		possibleContacts.add(!StringUtils.isEmpty(selectedFund.getExDepositBankContact()) ? agentService.getAgentLite(selectedFund.getExDepositBankContact()) : null);
		possibleContacts.add(depositBank.getAgentContacts().stream().filter(agentContactLiteDTO -> AgentContactFunction.CPS.name().equals(agentContactLiteDTO.getContactFunction()))
				.findFirst().map(agentContactLiteDTO -> agentContactLiteDTO.getContact()).orElse(null));
		
		// use the first possible candidate
		AgentLightDTO coverLetterContact = possibleContacts.stream().filter(Objects::nonNull).findFirst().get();
		return coverLetterContact;
	}

	private String transcodeTitleId(String title) {
		if (title == null) {
			return "MME_MR";
		}
		switch (title) {
		case "PP1":
			return "MR";
		case "PP2":
			return "MME";
		default:
			return "MME_MR";
		}
	}

	private String extractDocumentLanguage(AgentLightDTO contactAgent) {
		String documentLanguage = null;
		if (contactAgent.getDocumentationLanguage() != null) {
			documentLanguage = documentService.getTransco(TranscoType.LANGUAGE, contactAgent.getDocumentationLanguage().toString());
		}
		if (documentLanguage == null) {
			documentLanguage = "FR";
		}
		return documentLanguage;
	}

	@Override
	protected DocumentType getDocumentType() {
		return DocumentType.FAX_SURRENDER_DEPOSIT_BANK;
	}

	@Override
	protected String getXmlPath(EditingRequest editingRequest, String policyId) {
		return buildXmlPath(editingRequest, policyId, filePath);
	}

}
