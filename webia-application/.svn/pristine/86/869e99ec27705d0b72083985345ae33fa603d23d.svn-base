package lu.wealins.webia.core.service.document;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.common.dto.liability.services.enums.AgentContactFunction;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.webia.services.AmountDTO;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.editing.common.webia.Account;
import lu.wealins.editing.common.webia.AmountType;
import lu.wealins.editing.common.webia.CorrespondenceAddress;
import lu.wealins.editing.common.webia.CoverLetter;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.FollowUp;
import lu.wealins.editing.common.webia.FollowUp.Destination;
import lu.wealins.editing.common.webia.Header;
import lu.wealins.editing.common.webia.PersonLight;
import lu.wealins.editing.common.webia.Policy;
import lu.wealins.editing.common.webia.User;
import lu.wealins.editing.common.webia.WithdrawalFollowUp;
import lu.wealins.editing.common.webia.WithdrawalFollowUp.Fund;
import lu.wealins.webia.core.service.DocumentService;
import lu.wealins.webia.core.service.FundFormTransactionService;
import lu.wealins.webia.core.service.LiabilityCountryService;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.MetadataService;
import lu.wealins.webia.core.service.WebiaCheckDataService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.webia.core.service.document.trait.AgentBasedDocumentGenerationService;
import lu.wealins.webia.core.service.document.trait.TransactionFormBasedDocument;
import lu.wealins.webia.core.service.impl.PolicyDocumentService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.TranscoType;

/**
 * Document for Asset Manager / Instructions for Asset Manager
 *
 */
@Service
public class EnoughCashDocumentService extends PolicyDocumentService implements AgentBasedDocumentGenerationService, TransactionFormBasedDocument {

	/**
	 * Constants
	 */
	private static final String FINANCIAL_FEES_DIRECTLY_DEBITED_BY_AM_WITHOUT_INVOICE = "FIN_FEES_DEBITED_AM";

	@Autowired
	private FundFormTransactionService fundFormTransactionService;

	@Autowired
	private LiabilityFundService fundService;

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private MetadataService metadataService;

	@Autowired
	private WebiaWorkflowUserService userService;

	@Autowired
	private LiabilityWorkflowService workflowService;

	@Autowired
	private LiabilityCountryService countryService;

	@Autowired
	private WebiaCheckDataService checkDataService;

	@Autowired
	private DocumentService documentService;

	@Override
	protected Document createSpecificDocument(EditingRequest editingRequest, PolicyDTO policyDTO, String productCountry, String language, String userId) {

		// retrieve the agent from its id and the transaction form
		final AgentLightDTO agent = StringUtils.isEmpty(editingRequest.getAgent()) ? null : agentService.getAgentLite(editingRequest.getAgent());
		TransactionFormDTO transactionFormDTO = retrieveTransactionForm(restClientUtils, editingRequest.getWorkflowItemId());

		Collection<Long> fundTransactionFormIds = new ArrayList<>(Arrays.asList(editingRequest.getFundTransactionFormIds().split(","))).stream().map(x -> new Long(x)).collect(Collectors.toList());
		// retrieve the fund form transaction from policy and filter them based on the ids provided by the editing request
		Collection<FundTransactionFormDTO> fundTransactionForms = transactionFormDTO.getFundTransactionForms().stream()
				.filter(x -> fundTransactionFormIds.contains(x.getFundTransactionFormId()))
				.collect(Collectors.toList());

		AgentLightDTO contactAgent = extractContactAgent(fundTransactionForms);

		FollowUp enoughCashFollowUp = generateFollowUp(policyDTO, editingRequest.getWorkflowItemId(), fundTransactionForms, agent, contactAgent, userId);
		Policy policy = getXmlPolicy(policyDTO, new ArrayList<>());

		Document document = createEnouhCashDocument(editingRequest.getWorkflowItemId(), policyDTO, productCountry, extractDocumentLanguage(contactAgent), policy, userId);
		document.setFollowUp(enoughCashFollowUp);
		document.setCoverLetter(createSpecificCoverLetter(contactAgent, agent, policy));
		return document;
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

	private FollowUp generateFollowUp(PolicyDTO policyDTO, Long workflowItemId, Collection<FundTransactionFormDTO> fundTransactionForms, AgentLightDTO agent, AgentLightDTO contactAgent,
			String userId) {

		FollowUp followUp = new FollowUp();


		WorkflowType workflowType = WorkflowType.getType(workflowService.getWorkflowItem(workflowItemId.toString(), userId).getWorkflowItemTypeId());

		// Retrieve the transaction form
		TransactionFormDTO transactionFormDTO = retrieveTransactionForm(restClientUtils, workflowItemId);
		
		// set effective date if necessary ( i.e. in early simulation)
		if (transactionFormDTO.getEffectiveDate() == null) {
			transactionFormDTO.setEffectiveDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		}

		PolicyValuationDTO valuation = getPolicyValuation(policyDTO.getPolId(), sdf.format(transactionFormDTO.getEffectiveDate()));

		// Set destination (actually the same recipient as for the cover letter)
		List<Destination> destinations = new ArrayList<>();
		Destination destination = new Destination();
		destination.setEmail(contactAgent.getEmail());
		destination.setPerson(personMapper.asPersonLight(contactAgent));
		destinations.add(destination);
		followUp.setDestinations(destinations);

		// retrieve some date from the metadata
		Boolean securitiesTransfer = extractMetadata(workflowItemId, userId, Metadata.SECURITIES_TRANSFER);

		// prepare funds
		List<Fund> funds = new ArrayList<>();
		fundTransactionForms.stream().forEach(fundTransactionForm -> {
			Fund fund = new Fund();
			FundDTO fundDTO = fundService.getFund(fundTransactionForm.getFundId());
			fund.setAmount(
					createAmount(transactionFormDTO, fundTransactionForm, valuation.getHoldings().stream().filter(x -> x.getFundId().equals(fundTransactionForm.getFundId())).findFirst().get()));
			fund.setContactPerson(getContactPerson(fundDTO));
			fund.setFundId(fundTransactionForm.getFundId());
			fund.setFundSubType(fundDTO.getFundSubType());
			fund.setClosureAccount(fundTransactionForm.getClosure());
			fund.setSecuritiesTransfer(securitiesTransfer);

			// prepare the account
			Account custodianAccount = new Account();
			custodianAccount.setAccountNumber(fundDTO.getAccountRoot());
			custodianAccount.setBankName(fundDTO.getDepositBankAgent().getName());
			custodianAccount.setBIC(fundDTO.getDepositBankAgent().getPaymentAccountBic());
			custodianAccount.setCurrency(fundDTO.getCurrency());
			custodianAccount.setHolder("N/A");
			custodianAccount.setIban(fundDTO.getIban());
			fund.setCustodianAccount(custodianAccount);

			// define Asset Manager or PSI depending on the sub type
			if (FundSubType.FID.name().equals(fundDTO.getFundSubType())) {
				fund.setAssetManager(agent != null ? agent.getName() : null);
			} else if (FundSubType.FAS.name().equals(fundDTO.getFundSubType())) {
				fund.setPsi(agent != null ? agent.getName() : null);
			}
			funds.add(fund);
		});

		// Set Withdrawal
		WithdrawalFollowUp withdrawalFollowUp = new WithdrawalFollowUp();
		withdrawalFollowUp.setSurrenderType(workflowType.equals(WorkflowType.SURRENDER) ? "TOTAL" : "PARTIAL");
		withdrawalFollowUp.setContractCurrency(policyDTO.getCurrency());
		withdrawalFollowUp.setFunds(funds);
		withdrawalFollowUp.setPolicyIds(Arrays.asList(policyDTO.getPolId()));
		withdrawalFollowUp.setProduct(policyDTO.getProduct().getName());
		withdrawalFollowUp.setStep(workflowService.getWorkflowGeneralInformation(workflowItemId.toString(), userId).getCurrentStepName());
		withdrawalFollowUp.setTotalAmount(buildTotalAmount(valuation));
		withdrawalFollowUp.setValueDate(transactionFormDTO.getEffectiveDate());
		withdrawalFollowUp.setFinancialFeesDebitedByAM(extractQuestionValue(workflowItemId, FINANCIAL_FEES_DIRECTLY_DEBITED_BY_AM_WITHOUT_INVOICE));

		followUp.setWithdrawal(withdrawalFollowUp);

		return followUp;
	}

	/**
	 * Get the value of question defined by its checkCode
	 * 
	 * @param workflowItemId the workflow item id
	 * @param checkCode the checkcode to match
	 * @return the value of a question defined by its checkcode
	 */
	private Boolean extractQuestionValue(Long workflowItemId, String checkCode) {
		CheckDataDTO checkDataDTO = checkDataService.getCheckData(new Integer(workflowItemId.intValue()), checkCode);
		if (checkDataDTO == null || checkDataDTO.getDataValueYesNoNa() == null) {
			return null;
		}
		return "Yes".equalsIgnoreCase(checkDataDTO.getDataValueYesNoNa()) ? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * retrieve the value of a metadata for the given worklfow item id and metadata
	 * 
	 * @param workflowItemId the workflow item id
	 * @param userId the user id
	 * @param metadata the metadata to inspect
	 * @return the value of a metadata for the given worklfow item id and metadata
	 */
	private Boolean extractMetadata(Long workflowItemId, String userId, Metadata metadata) {
		MetadataDTO metadataDTO = metadataService.getMetadata(workflowItemId.toString(), metadata.getMetadata(), userId);
		return metadataDTO == null ? null : ("Yes".equalsIgnoreCase(metadataDTO.getValue()) ? Boolean.TRUE : Boolean.FALSE);
	}

	/**
	 * Retrieve the contact person of the specified {@link FundLiteDTO}
	 * 
	 * @param fund the {@link FundLiteDTO} that we we want to extract the contact person from
	 * @return the contact person of the specified {@link FundLiteDTO}
	 */
	private String getContactPerson(FundLiteDTO fund) {
		
		AgentLightDTO contact = agentService.getAgentLite(fund.getSalesRep());

		return contact != null
				? Arrays.asList(contact.getFirstname(), contact.getName()).stream().filter(Objects::nonNull).collect(Collectors.joining(" "))
				: null;
	}

	/**
	 * Build the {@link AmountType} object corresponding to the total amount from the provided {@link PolicyValuationDTO}.
	 * 
	 * @param valuation the {@link PolicyValuationDTO} the Policy valuation
	 * @return the {@link AmountType} object corresponding to the total amount from the provided {@link PolicyValuationDTO}
	 */
	private AmountType buildTotalAmount(PolicyValuationDTO valuation) {
		AmountType amountType = new AmountType();
		amountType.setCurrencyCode(valuation.getPolicyCurrency());
		amountType.setValue(valuation.getTotalPolicyCurrency());
		return amountType;
	}

	/**
	 * Create an {@link AmountType} from the provided {@link fundTransactionForm}
	 * 
	 * @param fundTransactionForm the {@link fundTransactionForm} used to create the {@link AmountType}
	 * @param policyValuationHolding
	 * @return an {@link AmountType} from the provided {@link fundTransactionForm}
	 */
	private AmountType createAmount(TransactionFormDTO transactionFormDTO, FundTransactionFormDTO fundTransactionForm, PolicyValuationHoldingDTO policyValuationHolding) {
		AmountDTO transactionFundAmount = fundFormTransactionService.getTransactionFundAmount(transactionFormDTO, fundTransactionForm, policyValuationHolding);

		AmountType amount = new AmountType();
		amount.setCurrencyCode(transactionFundAmount.getCurrency());
		amount.setValue(transactionFundAmount.getAmount());

		return amount;
	}

	private Document createEnouhCashDocument(Long workflowItemId, PolicyDTO policyDTO, String productCountry, String language, Policy policy, String userId) {
		String transcodedProductCountry = documentGenerationService.getTransco(TranscoType.PAYS, productCountry);
		MetadataDTO cps1Metada = metadataService.getMetadata(workflowItemId.toString(), Metadata.FIRST_CPS_USER.getMetadata(), userId);
		String trigram = userService.getLogin(cps1Metada.getValue());
		User user = documentGenerationService.getUserFromTrigram(trigram);
		Header header = documentGenerationService.generateHeader(user, getDocumentType(), language, transcodedProductCountry, null);

		return getPolicyDocument(policyDTO, policy, header);
	}

	/**
	 * Build and return a {@link CoverLetter} specific to the 'Instructions to Asset Manager' document a.k.a. 'enough Cash'. Actually we can't simply override generateCoverLetter method, since it
	 * wouldn't be possible to retrieve the agent
	 * 
	 * @param contactAgent the contact agent
	 * @param agent the agent
	 * @return the {@link CoverLetter} specific to the 'Instructions to Asset Manager' document
	 */
	private CoverLetter createSpecificCoverLetter(AgentLightDTO contactAgent, AgentLightDTO agent, Policy policy) {

		CoverLetter coverLetter = new CoverLetter();
		CorrespondenceAddress correspondenceAddress = new CorrespondenceAddress();
		correspondenceAddress.setPhone(contactAgent.getTelephone());
		correspondenceAddress.setFax(contactAgent.getFax());
		correspondenceAddress.setEmail(contactAgent.getEmail());
		correspondenceAddress.setCity(contactAgent.getTown());
		correspondenceAddress.setCountryCode(contactAgent.getCountryCode());
		correspondenceAddress.setZipCode(contactAgent.getPostcode());
		correspondenceAddress.setCountry(countryService.getCountry(contactAgent.getCountry()).getName());
		correspondenceAddress.setName(agent != null ? agent.getName() : null);
		correspondenceAddress
				.setStreet(Arrays.asList(contactAgent.getAddressLine1(), contactAgent.getAddressLine2(), contactAgent.getAddressLine3(), contactAgent.getAddressLine4())
				.stream()
				.filter(line -> !StringUtils.isEmpty(line))
				.filter(line -> !line.matches("[Cc]/[Oo]\\s+.*"))
				.collect(Collectors.joining("\n")));
		PersonLight person = new PersonLight();
		person.setFirstName(contactAgent.getFirstname());
		person.setLastName(contactAgent.getName());
		correspondenceAddress.setPersons(Arrays.asList(person));
		coverLetter.setCorrespondenceAddress(correspondenceAddress);
		coverLetter.setPolicy(policy);

		return coverLetter;
	}

	/**
	 * Extract an unique {@link AgentLightDTO} that will be used as contact for the cover letter and the communication by mail.
	 * 
	 * @param fundTransactionForms the fund transactions
	 * @return an unique {@link AgentLightDTO} that will be used as contact for the cover letter and the communication by mail.
	 */
	private AgentLightDTO extractContactAgent(Collection<FundTransactionFormDTO> fundTransactionForms) {

		// retrieve the deposit bank
		FundDTO selectedFund = fundService.getFund(fundTransactionForms.stream().findFirst().get().getFundId());
		AgentDTO assetManager = selectedFund.getAssetManagerAgent();

		// try to retrieve the payment contact or the cps contact
		Collection<AgentLightDTO> possibleContacts = new ArrayList<>();
		possibleContacts.add(!StringUtils.isEmpty(selectedFund.getSalesRep()) ? agentService.getAgentLite(selectedFund.getSalesRep()) : null);
		possibleContacts.add(assetManager.getAgentContacts().stream().filter(agentContactLiteDTO -> AgentContactFunction.CPS.name().equals(agentContactLiteDTO.getContactFunction()))
				.findFirst().map(agentContactLiteDTO -> agentContactLiteDTO.getContact()).orElse(null));

		// use the first possible candidate
		AgentLightDTO coverLetterContact = possibleContacts.stream().filter(Objects::nonNull).findFirst().get();
		return coverLetterContact;
	}

	@Override
	protected DocumentType getDocumentType() {
		return DocumentType.WITHDRAWAL_FOLLOWUP;
	}

	@Override
	protected String getXmlPath(EditingRequest editingRequest, String policyId) {
		return buildXmlPath(editingRequest, policyId, filePath);
	}
}
