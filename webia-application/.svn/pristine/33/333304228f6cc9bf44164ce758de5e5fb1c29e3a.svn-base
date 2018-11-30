/**
 * 
 */
package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDTO;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.CreateEditingRequest;
import lu.wealins.common.dto.webia.services.CreateEditingResponse;
import lu.wealins.common.dto.webia.services.EditingRequestStatus;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.editing.common.webia.Account;
import lu.wealins.editing.common.webia.AmountType;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.Documents;
import lu.wealins.editing.common.webia.Fee;
import lu.wealins.editing.common.webia.FollowUp;
import lu.wealins.editing.common.webia.FollowUp.Destination;
import lu.wealins.editing.common.webia.Header;
import lu.wealins.editing.common.webia.SubscriptionFollowUp;
import lu.wealins.editing.common.webia.SubscriptionFollowUp.PaymentDetails;
import lu.wealins.webia.core.mapper.AppFormMapper;
import lu.wealins.webia.core.mapper.FollowUpFundMapper;
import lu.wealins.webia.core.mapper.PersonMapper;
import lu.wealins.webia.core.service.DocumentGenerationServiceBase;
import lu.wealins.webia.core.service.LiabilityAgentService;
import lu.wealins.webia.core.service.LiabilityFundTransactionService;
import lu.wealins.webia.core.service.helper.FollowUpDocumentContentHelper;
import lu.wealins.webia.core.utils.Constantes;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.EditingUser;

/**
 * @author NGA
 * @param <AgentService>
 *
 */
public class FollowUpDocumentGeneratorcCommonServiceImpl extends DocumentGenerationServiceBase {

	private static final String ACTIVE_CONTACT_STATUS = "1";
	private static final String CPS_FUNCTION = "CPS";
	private static final String DEFAULT_LANGUAGE = "EN";
	/**
	 * Env. variable
	 */
	@Value("${editique.xml.path}")
	private String filePath;

	@Autowired
	private LiabilityAgentService agentService;

	@Autowired
	private LiabilityFundTransactionService transactionService;

	@Autowired
	private AppFormMapper appFormMapper;

	@Autowired
	private PersonMapper personMapper;

	@Autowired
	private FollowUpDocumentContentHelper followUpHelper;

	@Autowired
	private FollowUpFundMapper fundMapper;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constantes.YYYYMMDDHHMMSSSSS);

	@Override
	public EditingRequest generateDocument(SecurityContext context, EditingRequest request, AppFormDTO enrichedAppForm,
			String language, boolean isBroker) {
		EditingRequest updatedEditingRequest = updateEditingRequestStatus(request, EditingRequestStatus.IN_PROGRESS);

		String xmlDocumentPath = generateXmlPath(request);
		Documents xmlDocuments = createDocument(request, enrichedAppForm, language, isBroker);

		String saveResponse = documentGenerationService.saveXmlDocuments(xmlDocuments, xmlDocumentPath);

		updatedEditingRequest.setInputStreamPath(saveResponse);
		return updatedEditingRequest;
	}

	private String generateXmlPath(EditingRequest request) {
		LocalDateTime date = LocalDateTime.now();
		String today = date.format(formatter);
		String xmlDocumentPath = initXmlDocumentPath(request.getPolicy(), request.getProduct(), today);
		return xmlDocumentPath;
	}

	private FollowUp generateFollowUpDocument(AppFormDTO enrichedAppForm, boolean isBroker) {
		FollowUp followUp = new FollowUp();
		List<FollowUp.Destination> destinations = initializeDestinations(enrichedAppForm, isBroker);
		SubscriptionFollowUp subscriptionFollowUp = initializeSubscriptionFollowUp(enrichedAppForm, isBroker);
		followUp.setDestinations(destinations);
		followUp.setSubscription(subscriptionFollowUp);
		removeUnusedFieldForSpecificFollowUpReport(followUp, isBroker);
		return followUp;
	}

	private FollowUp removeUnusedFieldForSpecificFollowUpReport(FollowUp followUp, boolean isBroker) {
		SubscriptionFollowUp subscriptionFollowUp = followUp.getSubscription();
		StepTypeDTO currentStep = StepTypeDTO.getStepType(subscriptionFollowUp.getStep());

		switch (currentStep) {
		case REGISTRATION:
			removeUnusedFieldForConfirmationFeesReport(followUp);
			break;
		case ACCOUNT_OPENING_REQUEST:
		case AWAITING_ACCOUNT_OPENING:
		case PREMIUM_TRANSFER_REQUEST:
			removeUnusedFieldForAcceptationReport(followUp);
			break;
		case GENERATE_DOCUMENTATION:
			removeUnusedFieldForReceptionPrimeReport(followUp, isBroker);
			break;

		default:
			break;
		}

		return followUp;

	}

	private FollowUp removeUnusedFieldForConfirmationFeesReport(FollowUp followUp) {
		
		if (followUp == null || followUp.getSubscription() == null || followUp.getSubscription().getFunds() == null) {
			return followUp;
		}
		List<SubscriptionFollowUp.Fund> followUpFunds = followUp.getSubscription().getFunds().stream().map(fund -> {

			if (fund == null) {
				return fund;
			}
			Account account = fund.getCustodianAccount();
			if (account != null) {
				account.setAccountNumber(null);
				account.setCurrency(null);
				account.setIban(null);
				account.setCurrency(null);
			}
			fund.setContactPerson(null);
			return fund;
		}).collect(Collectors.toList());
		
		followUp.getSubscription().setPolicyIds(null);
		followUp.getSubscription().setFunds(followUpFunds);
		return followUp;
	}


	private FollowUp removeUnusedFieldForAcceptationReport(FollowUp followUp) {
		if (followUp == null || followUp.getSubscription() == null) {
			return followUp;
		}

		followUp.getSubscription().setEntryFees(null);
		followUp.getSubscription().setAdminFees(null);
		followUp.getSubscription().setPaymentDetails(null);
		if (followUp.getSubscription().getFunds() == null) {
			return followUp;
		}

		List<SubscriptionFollowUp.Fund> followUpFunds = followUp.getSubscription().getFunds().stream().map(fund -> {

			if (fund == null) {
				return fund;
			}
			fund.setFinancialManagementFees(null);

			Account account = fund.getCustodianAccount();

			if (account != null) {
				account.setBankName(account.getBankName() != null && account.getBankName().trim().isEmpty() ? null
						: account.getBankName());

				account.setIban(
						account.getIban() != null && account.getIban().trim().isEmpty() ? null : account.getIban());
				
				account.setCurrency(account.getCurrency() != null && account.getCurrency().trim().isEmpty() ? null
						: account.getCurrency());
			}
			fund.setContactPerson(null);
			return fund;
		}).collect(Collectors.toList());

		followUp.getSubscription().setFunds(followUpFunds);
		return followUp;
	}


	private FollowUp removeUnusedFieldForReceptionPrimeReport(FollowUp followUp, boolean isBroker) {
		if (followUp == null || followUp.getSubscription() == null) {
			return followUp;
		}

		followUp.getSubscription().setEntryFees(null);
		followUp.getSubscription().setAdminFees(null);
		if (followUp.getSubscription().getFunds() == null) {
			return followUp;
		}

		List<SubscriptionFollowUp.Fund> followUpFunds = followUp.getSubscription().getFunds().stream().map(fund -> {

			if (fund == null) {
				return fund;
			}

			fund.setFinancialManagementFees(null);
			if (isBroker) {
				fund.setContactPerson(null);
			}
			return fund;
		}).collect(Collectors.toList());

		followUp.getSubscription().setFunds(followUpFunds);
		return followUp;
	}

	private Documents createDocument(EditingRequest editingRequest, AppFormDTO enrichedAppForm, String language,
			boolean isRiskFees) {
		Documents documentParentNode = new Documents();
		List<Document> documents = new ArrayList<>();

		Document document = new Document();
		document.setDocumentType(DocumentType.SOUSCRIPTION_FOLLOWUP.val());
		document.setHeader(initDocumentHeader(editingRequest.getCreationUser(), DocumentType.SOUSCRIPTION_FOLLOWUP,
				language, null, null));
		document.setFollowUp(generateFollowUpDocument(enrichedAppForm, isRiskFees));
		documents.add(document);
		documentParentNode.setDocuments(documents);
		return documentParentNode;
	}


	@Override
	public CreateEditingResponse registerEditingRequest(CreateEditingRequest createdEditingRequest) {
		return editingService.applyFollowUpRequest(createdEditingRequest);
	}

	Header initDocumentHeader(EditingUser creationUser, DocumentType documentType, String language, String county,
			UUID docUid) {
		return documentGenerationService.generateHeader(creationUser, documentType, language, county, docUid);
	}

	private String initXmlDocumentPath(String policy, String product, String today) {

		String cleanPolice = StringUtils.replaceAll(policy, "////", StringUtils.EMPTY)
				.replaceAll("\\\\", StringUtils.EMPTY)
				.replaceAll("/", StringUtils.EMPTY);

		StringBuilder pathBuilder = new StringBuilder();
		pathBuilder.append(filePath);
		pathBuilder.append("FollowUP");
		pathBuilder.append("_");
        pathBuilder.append(cleanPolice);
		pathBuilder.append("_");
		pathBuilder.append(today);
		pathBuilder.append(".xml");
		return pathBuilder.toString();
	}


	public List<FollowUp.Destination> initializeDestinations(AppFormDTO enrichedAppForm, boolean isBroker) {
		List<FollowUp.Destination> collates = new ArrayList<>();
		List<FollowUp.Destination> brokerAgentContacts = getBrokerContact(enrichedAppForm);
		List<FollowUp.Destination> countryManagerContacts = getCountryManagerContact(enrichedAppForm);
		StepTypeDTO currentStep = StepTypeDTO.getStepType(getStep(enrichedAppForm));

		switch (currentStep) {

		case REGISTRATION:
			collates.addAll(brokerAgentContacts);
			collates.addAll(countryManagerContacts);
			break;
		case ACCOUNT_OPENING_REQUEST:
			collates.addAll(brokerAgentContacts);
			collates.addAll(countryManagerContacts);
			break;
		case AWAITING_ACCOUNT_OPENING:
			collates.addAll(brokerAgentContacts);
			collates.addAll(countryManagerContacts);
			break;
		case PREMIUM_TRANSFER_REQUEST:
			collates.addAll(brokerAgentContacts);
			collates.addAll(countryManagerContacts);
			break;
		case GENERATE_DOCUMENTATION:
			if (isBroker) {
				collates.addAll(brokerAgentContacts);
			}
			else {
				collates.addAll(getAssetManagerAgentContact(enrichedAppForm));
			}
			break;

		default:
			break;
		}

		return collates;
	}

	private List<Destination> getBrokerContact(AppFormDTO enrichedAppForm) {
		AgentDTO broker = null;
		if (enrichedAppForm != null && enrichedAppForm.getBroker() != null
				&& enrichedAppForm.getBroker().getPartnerId() != null
				&& !enrichedAppForm.getBroker().getPartnerId().trim().isEmpty()) {
			broker = agentService.getAgent(enrichedAppForm.getBroker().getPartnerId().trim());
		}

		if (broker == null) {
			return new ArrayList<FollowUp.Destination>();
		}

		Optional<Destination> optionalBrokerDestination = getDestinationOfAgent(broker);
				
					return Stream.of(optionalBrokerDestination)
							.filter(optionalDestination -> optionalDestination.isPresent())
				.map(optionalsDestination -> optionalsDestination.get())
				.collect(Collectors.toList());
	}

	@Override
	public String initLanguage(AppFormDTO enrichedAppForm) {

		String language = getBrokerLanguage(enrichedAppForm);
		if (language == null || language.isEmpty()) {
			return getDefaultLanguage();
		}
		return language;
	}

	List<FollowUp.Destination> getCountryManagerContact(AppFormDTO enrichedAppForm) {
		if (enrichedAppForm != null && enrichedAppForm.getCountryManagers() != null
				&& !enrichedAppForm.getCountryManagers().isEmpty()) {
			return enrichedAppForm.getCountryManagers().stream()
					.filter(countryManager -> countryManager.getPartnerId() != null
							&& !countryManager.getPartnerId().isEmpty())
					.map(manager -> agentService.getAgent(manager.getPartnerId()))
					.map(agentDTO -> personMapper.agentDTOFollowUpDestination(agentDTO)).collect(Collectors.toList());
		}
		return new ArrayList<FollowUp.Destination>();
	}

	private boolean isFAS(FundLiteDTO fundDTO) {
		return fundDTO != null && fundDTO.getFundSubType() != null
				&& FundSubType.FAS.name().equals(fundDTO.getFundSubType().trim());
	}

	private boolean isFID(FundLiteDTO fundDTO) {
		return fundDTO != null && fundDTO.getFundSubType() != null
				&& FundSubType.FID.name().equals(fundDTO.getFundSubType().trim());
	}

	private boolean isFIDOrFAS(FundLiteDTO fundDTO) {
		boolean isFid = isFID(fundDTO);
		boolean isFas = isFAS(fundDTO);
		return isFid || isFas;
	}

	protected List<FollowUp.Destination> getAssetManagerAgentContact(AppFormDTO enrichedAppForm) {

		if (enrichedAppForm == null || enrichedAppForm.getFundForms() == null
				|| enrichedAppForm.getFundForms().isEmpty()) {
			return new ArrayList<FollowUp.Destination>();
		}

		List<FollowUp.Destination> assetManagerDestination = enrichedAppForm.getFundForms().stream()
				.map(fundForm -> fundForm.getFund())
				.filter(fund -> fund != null && isFID(fund) && !isAgentWealinsAssetManager(fund.getAssetManager()))
				.map(fund -> fund.getAssetManager())
				.filter(assetManagerId -> assetManagerId != null && !assetManagerId.isEmpty())
				.map(assetManagersId -> agentService.getAgent(assetManagersId.trim()))
				.map(assetManager -> getDestinationOfAgent(assetManager))
				.filter(optionalDestination -> optionalDestination.isPresent())
				.map(optionalMailDestination -> optionalMailDestination.get())
				.collect(Collectors.toList());

		return assetManagerDestination;
					}

	private Optional<Destination> getDestinationOfAgent(AgentDTO principalAgent) {

		if (principalAgent == null) {
			return Optional.empty();
	}

		FollowUp.Destination principalAgentDestination = personMapper.agentDTOFollowUpDestination(principalAgent);
		List<AgentContactLiteDTO> principalAgentContacts = (List<AgentContactLiteDTO>) principalAgent
				.getAgentContacts();
		if (principalAgentContacts == null || principalAgentContacts.isEmpty()) {
			return Optional.ofNullable(principalAgentDestination);
		}

		Optional<Destination> principalAgentCPSContactDestination = principalAgentContacts.stream()
				.filter(contact -> CPS_FUNCTION.equalsIgnoreCase(contact.getContactFunction())
						&& ACTIVE_CONTACT_STATUS.equals(StringUtils.stripToNull(contact.getStatus())))
				.map(agent -> personMapper.agentDTOFollowUpDestination(agent.getContact()))
				.findAny();

		if (!principalAgentCPSContactDestination.isPresent()) {
			return Optional.ofNullable(principalAgentDestination);
		}

		return principalAgentCPSContactDestination;
	}

	private List<Destination> getDestinationFromAgentContacts(List<AgentContactLiteDTO> agentContacts) {
		List<AgentContactLiteDTO> agentContactsLites = agentContacts.stream()
				.filter(contact -> CPS_FUNCTION.equalsIgnoreCase(contact.getContactFunction())
						&& ACTIVE_CONTACT_STATUS.equals(StringUtils.stripToNull(contact.getStatus())))
				.collect(Collectors.toList());

		if (agentContactsLites == null || agentContactsLites.isEmpty()) {
			return new ArrayList<FollowUp.Destination>();
		}

		return agentContactsLites.stream()
				.map(agentContact -> personMapper.agentDTOFollowUpDestination(agentContact.getContact()))
				.collect(Collectors.toList());
	}

	@Override
	public List<SubscriptionFollowUp.Fund> getFollowUpFunds(AppFormDTO enrichedAppForm) {

		if (enrichedAppForm == null || enrichedAppForm.getFundForms() == null) {
			return null;
		}

		int policiesSize = followUpHelper.getPoliciesNumber(enrichedAppForm);
		return enrichedAppForm.getFundForms().stream().map(fundForm -> fundForm.getFund())
				.filter(fundLiteDTO -> isFIDOrFAS(fundLiteDTO))
				.map(fundLite -> getFollowUpFund(fundLite, policiesSize))
				.collect(Collectors.toList());
	}

	private SubscriptionFollowUp.Fund getFollowUpFund(FundLiteDTO fundLite, int policiesSize) {
		SubscriptionFollowUp.Fund followUpFund = fundMapper.asFollowUpFund(fundLite);
		if (policiesSize < 2) {
			followUpFund.setPolicyIds(null);
		}
		followUpFund.setCustodianAccount(fundMapper.asFollowUpFundAccount(fundLite));
		followUpFund.setFinancialManagementFees(getFollowUpFinancialMngtFees(fundLite));
		return followUpFund;
	}

	private SubscriptionFollowUp.Fund.FinancialManagementFees getFollowUpFinancialMngtFees(FundLiteDTO fundLite) {
		SubscriptionFollowUp.Fund.FinancialManagementFees finFees = new SubscriptionFollowUp.Fund.FinancialManagementFees();
		if (isFAS(fundLite)) {
			finFees.setPsiFees(getPsiFees(fundLite));
		} else if (isFID(fundLite)) {
			if (fundLite != null && Boolean.TRUE.equals(fundLite.getExAllInFees())) {
				finFees.setAllInFees(getAssetManagerFees(fundLite));
			} else if (fundLite != null && !Boolean.TRUE.equals(fundLite.getExAllInFees())) {
				finFees.setAssetManagementFees(getAssetManagerFees(fundLite));
			}
		}

		finFees.setCustodianFees(getCustodianFees(fundLite));
		finFees.setInsurerFees(getInsurrerFees(fundLite));
		return finFees;
	}

	private Fee getAssetManagerFees(FundLiteDTO fundLite) {
		Fee fee = null;
		BigDecimal value = null;
		BigDecimal rate = null;
		String currency = null;
		if (fundLite.getAssetManagerFee() != null) {
			rate = fundLite.getAssetManagerFee();
			currency = fundLite.getCurrency();
		} else if (fundLite.getFinFeesFlatAmount() != null) {
			value = fundLite.getFinFeesFlatAmount();
			currency = fundLite.getAssetManFeeCcy();
		}

		if (value != null || rate != null || currency != null) {
			fee = getFee(rate, value, currency);
		}
		return fee;
	}

	private Fee getCustodianFees(FundLiteDTO fundLite) {
		Fee fee = null;
		BigDecimal value = null;
		BigDecimal rate = null;
		String currency = null;
		if (fundLite.getBankDepositFee() != null) {
			rate = fundLite.getBankDepositFee();
			currency = fundLite.getCurrency();
		} else if (fundLite.getDepositBankFlatFee() != null) {
			value = fundLite.getDepositBankFlatFee();
			currency = fundLite.getBankDepFeeCcy();
		}
		if (value != null || rate != null || currency != null) {
			fee = getFee(rate, value, currency);
		}
		return fee;
	}

	private Fee getPsiFees(FundLiteDTO fundLite) {
		Fee fee = null;
		BigDecimal value = null;
		BigDecimal rate = null;
		String currency = null;
		if (fundLite.getFinAdvisorFee() != null) {
			rate = fundLite.getFinAdvisorFee();
			currency = fundLite.getCurrency();
		}

		if (value != null || rate != null || currency != null) {
			fee = getFee(rate, value, currency);
		}

		return fee;
	}

	private Fee getInsurrerFees(FundLiteDTO fundLite) {
		Fee fee = null;
		BigDecimal value = null;
		BigDecimal rate = null;
		String currency = null;
		if (fundLite.getAssetManagerFee() != null) {
			rate = fundLite.getAssetManagerFee();
			currency = fundLite.getCurrency();
		} else if (fundLite.getFinFeesFlatAmount() != null) {
			value = fundLite.getFinFeesFlatAmount();
			currency = fundLite.getAssetManFeeCcy();
		}

		if (value != null || rate != null || currency != null) {
			fee = getFee(rate, value, currency);
		}
		return fee;
	}

	@Override
	public SubscriptionFollowUp.EntryFees getEntryFees(AppFormDTO appForm) {
		Fee totalEntryFees = getTotalEntryFees(appForm);
		Fee intermediaryEntryFees = getIntermediairyEntryFees(appForm);
		SubscriptionFollowUp.EntryFees entryFees = null;

		if (totalEntryFees != null || intermediaryEntryFees != null) {
			entryFees = new SubscriptionFollowUp.EntryFees();
			entryFees.setIntermediaryFees(intermediaryEntryFees);
			entryFees.setTotalFees(totalEntryFees);
		}
		return entryFees;
	}

	@Override
	public SubscriptionFollowUp.AdminFees getAdminFees(AppFormDTO appForm) {
		Fee totalAdminFees = getTotalAdminFees(appForm);
		Fee intermediaryAdminFees = getIntermediairyAdminFees(appForm);
		SubscriptionFollowUp.AdminFees adminFees = null;

		if (totalAdminFees != null || intermediaryAdminFees != null) {
			adminFees = new SubscriptionFollowUp.AdminFees();
			adminFees.setIntermediaryFees(intermediaryAdminFees);
			adminFees.setTotalFees(totalAdminFees);
		}
		return adminFees;
	}

	private Fee getTotalEntryFees(AppFormDTO appForm) {
		Fee fee = null;
		BigDecimal value = null;
		BigDecimal rate = null;
		String currency = appForm.getContractCurrency();
		if (appForm.getEntryFeesPct() != null) {
			rate = appForm.getEntryFeesPct();
		} else if (appForm.getEntryFeesAmt() != null) {
			value = appForm.getEntryFeesAmt();
		}

		if (value != null || rate != null || currency != null) {
			fee = getFee(rate, value, currency);
		}
		return fee;
	}

	private Fee getTotalAdminFees(AppFormDTO appForm) {
		Fee fee = null;
		BigDecimal value = null;
		BigDecimal rate = null;
		String currency = appForm.getContractCurrency();
		if (appForm.getMngtFeesPct() != null) {
			rate = appForm.getMngtFeesPct();
		}
		if (value != null || rate != null || currency != null) {
			fee = getFee(rate, value, currency);
		}
		return fee;
	}

	private Fee getIntermediairyEntryFees(AppFormDTO appForm) {
		Fee fee = null;
		BigDecimal value = null;
		BigDecimal rate = null;
		String currency = appForm.getContractCurrency();
		if (appForm.getBroker() != null && appForm.getBroker().getEntryFeesPct() != null) {
			rate = appForm.getBroker().getEntryFeesPct();
		}

		if (appForm.getBroker() != null && appForm.getBroker().getEntryFeesAmt() != null) {
			value = appForm.getBroker().getEntryFeesAmt();
		}
		if ((value != null || rate != null) && currency != null) {
			fee = getFee(rate, value, currency);
		}
		return fee;
	}

	private Fee getIntermediairyAdminFees(AppFormDTO appForm) {
		Fee fee = null;
		BigDecimal value = null;
		BigDecimal rate = null;
		String currency = appForm.getContractCurrency();
		if (appForm.getBroker() != null && appForm.getBroker().getMngtFeesPct() != null) {
			rate = appForm.getBroker().getMngtFeesPct();
		}
		if (value != null || rate != null || currency != null) {
			fee = getFee(rate, value, currency);
		}
		return fee;
	}

	private Fee getFee(BigDecimal rate, BigDecimal value, String currency) {
		AmountType amount = null;

		Fee fee = new Fee();

		if (rate == null) {
			amount = new AmountType();
			amount.setValue(value);
			amount.setCurrencyCode(currency);
		}
		fee.setRate(rate);
		fee.setAmount(amount);

		return fee;
	}

	private AmountType getPaymentAmountType(BigDecimal value, String currency) {
		AmountType amountType = new AmountType();
		amountType.setCurrencyCode(currency);
		amountType.setValue(value);
		return amountType;
	}

	@Override
	public PaymentDetails getPaymentDetail(AppFormDTO appForm, boolean isBroker) {

		Integer premiumEventTypes = new Integer(2);

		PaymentDetails paymentDetails = null;
		if (appForm != null && appForm.getPaymentAmt() != null) {
			String policyId = appForm.getPolicyId();

			List<PolicyTransactionsHistoryDTO> policeHistories = (List<PolicyTransactionsHistoryDTO>) transactionService
					.getPolicyTransactionsHistory(policyId);

			if(policeHistories == null ||policeHistories.isEmpty() ) {
				return null;
			}
			
			Optional<PolicyTransactionsHistoryDTO> policeHistory = policeHistories.stream()
					.filter(transaction -> (premiumEventTypes.compareTo(transaction.getEventType())) == 0)
					.sorted(Comparator.comparing(PolicyTransactionsHistoryDTO::getEffectiveDate)).findFirst();

			paymentDetails = new PaymentDetails();

			if (policeHistory.isPresent()) {
				PolicyTransactionsHistoryDTO transaction = policeHistory.get();

				paymentDetails.setPaymentAmount(
						getPaymentAmountType(transaction.getGrossAmount(), transaction.getCurrency()));
				paymentDetails.setPaymentDate(transaction.getEffectiveDate());


				if (transaction.getTaxAmount() != null) {
					paymentDetails.setTaxDeductionAmount(
							getPaymentAmountType(transaction.getTaxAmount(), transaction.getCurrency()));
				}

				if (transaction.getFeeAmount() != null) {
					paymentDetails.setEntryFeesAmount(
							getPaymentAmountType(transaction.getFeeAmount(), transaction.getCurrency()));
				}

				if (isBroker) {
					BigDecimal mortalityCharge = getRiskFees(policeHistories, transaction.getEffectiveDate());
					if(mortalityCharge != null){
						paymentDetails.setRiskFeesAmount(getPaymentAmountType(mortalityCharge, transaction.getCurrency()));
					}						
				}
			}
		}

		return paymentDetails;
	}


	private BigDecimal getRiskFees(List<PolicyTransactionsHistoryDTO> policeHistories,
			Date effectiveDate) {
		final String POSTED_CHARGE = "Posted";
		if (effectiveDate == null || policeHistories == null || policeHistories.isEmpty()) {
			return null;
		}

		Integer mortalityEventTypes = new Integer(3);
		BigDecimal mortalityCharge = policeHistories.stream().filter(
				mortalitytransaction -> (mortalityEventTypes.compareTo(mortalitytransaction.getEventType())) == 0)
				.filter(charges -> charges != null && charges.getStatus() != null
						&& POSTED_CHARGE.equalsIgnoreCase(charges.getStatus().trim()))
				.filter(charge -> charge != null && charge.getEffectiveDate().equals(effectiveDate))
				.map(mortalitiesCharge -> mortalitiesCharge.getGrossAmount()).filter(anyCharge -> anyCharge != null)
				.map(notNullamount -> notNullamount.abs()).reduce(BigDecimal.ZERO, BigDecimal::add);
		return mortalityCharge;
	}

	protected String getBrokerLanguage(AppFormDTO enrichedAppForm) {
		List<Destination> brokerContact = getBrokerContact(enrichedAppForm);
		if (brokerContact == null || brokerContact.isEmpty()) {
			return getDefaultLanguage();
		}
		String language = brokerContact.get(0).getLanguage();

		if (language == null || language.isEmpty()) {
			return getDefaultLanguage();
		}

		return language;
		}

	String getDefaultLanguage() {
		return DEFAULT_LANGUAGE;
	}
}
