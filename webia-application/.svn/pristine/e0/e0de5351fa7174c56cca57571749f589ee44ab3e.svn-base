package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.AgentBankAccountDTO;
import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.liability.services.BeneficiaryDTO;
import lu.wealins.common.dto.liability.services.ClientContactDetailDTO;
import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.liability.services.DeathCoverageClauseDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundTransactionDTO;
import lu.wealins.common.dto.liability.services.InsuredDTO;
import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.OptionDetailDTO;
import lu.wealins.common.dto.liability.services.OtherClientDTO;
import lu.wealins.common.dto.liability.services.PolicyAgentShareDTO;
import lu.wealins.common.dto.liability.services.PolicyBeneficiaryClauseDTO;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderDTO;
import lu.wealins.common.dto.liability.services.PolicyPremiumDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.liability.services.enums.CliPolRelationshipType;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.liability.services.enums.SendingRuleType;
import lu.wealins.common.dto.webia.services.BenefClauseStdDTO;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.EditingRequestStatus;
import lu.wealins.editing.common.webia.Account;
import lu.wealins.editing.common.webia.AmountType;
import lu.wealins.editing.common.webia.AssetManager;
import lu.wealins.editing.common.webia.Beneficiary;
import lu.wealins.editing.common.webia.BeneficiaryClause;
import lu.wealins.editing.common.webia.Clause;
import lu.wealins.editing.common.webia.Clause.StandardClause;
import lu.wealins.editing.common.webia.CorrespondenceAddress;
import lu.wealins.editing.common.webia.CoverLetter;
import lu.wealins.editing.common.webia.DeathCoverage;
import lu.wealins.editing.common.webia.DeathCoverageDurationType;
import lu.wealins.editing.common.webia.DedicatedFundsRepartition;
import lu.wealins.editing.common.webia.DeliveryChannelType;
import lu.wealins.editing.common.webia.DestinationType;
import lu.wealins.editing.common.webia.Disinvestment;
import lu.wealins.editing.common.webia.DisinvestmentDetail;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.Documents;
import lu.wealins.editing.common.webia.EndOnInsuredDeathType;
import lu.wealins.editing.common.webia.Fee;
import lu.wealins.editing.common.webia.Fund;
import lu.wealins.editing.common.webia.Header;
import lu.wealins.editing.common.webia.Investment;
import lu.wealins.editing.common.webia.InvestmentDetail;
import lu.wealins.editing.common.webia.InvestmentStrategy;
import lu.wealins.editing.common.webia.LetterType;
import lu.wealins.editing.common.webia.MailingAddress;
import lu.wealins.editing.common.webia.OrderedPerson;
import lu.wealins.editing.common.webia.Person;
import lu.wealins.editing.common.webia.PersonLight;
import lu.wealins.editing.common.webia.Policy;
import lu.wealins.editing.common.webia.PolicyHolder;
import lu.wealins.editing.common.webia.PolicySchedule.InitialPremium;
import lu.wealins.editing.common.webia.PolicySchedule.Limits;
import lu.wealins.editing.common.webia.PolicySchedule.Transfer;
import lu.wealins.editing.common.webia.RiskProfile;
import lu.wealins.editing.common.webia.SimpleFundsRepartition;
import lu.wealins.editing.common.webia.SimpleFundsRepartition.FundInvested;
import lu.wealins.editing.common.webia.Situation;
import lu.wealins.editing.common.webia.SpecializedInsuranceFundsRepartition;
import lu.wealins.editing.common.webia.User;
import lu.wealins.webia.core.mapper.BeneficiaryMapper;
import lu.wealins.webia.core.mapper.FundMapper;
import lu.wealins.webia.core.mapper.PersonMapper;
import lu.wealins.webia.core.mapper.PolicyMapper;
import lu.wealins.webia.core.mapper.PolicyTransferMapper;
import lu.wealins.webia.core.service.DocumentService;
import lu.wealins.webia.core.service.LiabilityAgentService;
import lu.wealins.webia.core.service.LiabilityDeathCoverageService;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.LiabilityFundTransactionService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityPolicyValuationService;
import lu.wealins.webia.core.service.LiabilityProductService;
import lu.wealins.webia.core.service.LiabilityProductValueService;
import lu.wealins.webia.core.service.MetadataService;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.service.WebiaBenefClauseStdService;
import lu.wealins.webia.core.service.WebiaCheckDataService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.webia.core.service.document.PolicyScheduleDocumentGenerationService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.EditingUser;
import lu.wealins.webia.ws.rest.request.FundTransactionRequest;
import lu.wealins.webia.ws.rest.request.TranscoType;

public abstract class PolicyDocumentService {

	private static final List<String> SPAIN_PRODUCTS_WITH_EN = Arrays.asList("OLS", "OLSD");
	private static final String SPAIN = "ESP";
	private static final String PT = "PT";
	private static final String PORTUGUESE_PRODUCT_WITH_PT = "OPORT";
	private static final String PORTUGAL = "PRT";

	private static final String LIABILITY_VALUATION = "liability/policy/valuation/";
	protected static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");

	/**
	 * The logger
	 */
	protected static final Logger log = LoggerFactory.getLogger(PolicyScheduleDocumentGenerationService.class);
	/**
	 * Services
	 */
	@Autowired
	protected LiabilityPolicyService policyService;
	@Autowired
	private WebiaBenefClauseStdService webiaBenefClauseStdService;
	@Autowired
	private LiabilityFundService fundService;
	@Autowired
	protected LiabilityDeathCoverageService liabilityDeathCoverageService;
	@Autowired
	private LiabilityFundTransactionService liabilityFundTransactionService;
	@Autowired
	protected LiabilityProductValueService liabilityProductValueService;
	@Autowired
	protected LiabilityProductService liabilityProductService;
	@Autowired
	protected DocumentService documentGenerationService;
	@Autowired
	private WebiaApplicationParameterService webiaApplicationParameterService;
	@Autowired
	private WebiaCheckDataService webiaCheckDataService;
	@Autowired
	private BeneficiaryMapper beneficiaryMapper;
	@Autowired
	protected LiabilityAgentService agentService;
	@Autowired
	private WebiaWorkflowUserService workflowUserService;
	@Autowired
	private MetadataService metadataService;
	@Autowired
	private WebiaWorkflowUserService userService;
	
	@Autowired
	protected LiabilityPolicyValuationService policyValuationService;

	@Autowired
	private RestClientUtils restClientUtils;
	/**
	 * Mappers
	 */
	@Autowired
	protected PolicyMapper policyMapper;
	@Autowired
	private FundMapper fundMapper;
	@Autowired
	protected PersonMapper personMapper;
	@Autowired
	PolicyTransferMapper policyTransferMapper;

	private static final String MIN_ADDITIONAL_PAYMENT_AMOUNT = "MIN_ADDITIONAL_PAYMENT_AMOUNT";
	private static final String MIN_CONTRACT_AMOUNT = "MIN_CONTRACT_AMOUNT";
	private static final String MIN_FID_AMOUNT = "MIN_FID_AMOUNT";
	private static final String MIN_WITHDRAWAL_AMOUNT = "MIN_WITHDRAWAL_AMOUNT";
	private static final Integer MEDICAL_QUEST_CHECK_ID = 75;
	private static final Date DEFAULT_DATE = new Date("01/01/1753");
	private static final String DEFAULT_TITLE_ID = "MME_MR";
	protected static final String BELGIUM = "BEL";
	protected static final String NL = "NL";
	protected static final String FR = "FR";
	protected static final String DEATH = "Death";
	protected static final String LIFE = "Life";
	private static final List<String> LUXEMBOURG_PRODUCTS_WITH_EN = Arrays.asList("OPPL", "PRSEIN");
	private static final String LUXEMBOURG = "LUX";
	private static final String EN = "EN";

	@Value("${editique.xml.path}")
	protected String filePath;

	protected abstract Document createSpecificDocument(EditingRequest editingRequest, PolicyDTO policyDTO, String productCountry, String language, String userId);

	protected abstract DocumentType getDocumentType();

	protected abstract String getXmlPath(EditingRequest editingRequest, String policyId);
	
	protected boolean isAlternativeFund(FundDTO fundDTO, Fund fund) {
		return fund.isAlternativeFund();
	}

	public EditingRequest generateDocumentPolicy(SecurityContext context, EditingRequest editingRequest) {

		try {

			Assert.notNull(editingRequest, "The edition request can not be null");

			/**
			 * Set the request in progress
			 */
			editingRequest.setStatus(EditingRequestStatus.IN_PROGRESS);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);

			EditingUser creationUser = editingRequest.getCreationUser();

			Assert.notNull(creationUser, "The creation user of the document generation request can not be null");

			/**
			 * Search policy
			 */
			PolicyDTO policyDTO = getPolicyByRequest(editingRequest);

			String policyId = policyDTO.getPolId();
			Assert.notNull(policyDTO, "No policy found with ID:" + policyId);
			String productCountry = policyDTO.getProduct().getNlCountry();

			Documents documents = new Documents();
			List<Document> docs = new ArrayList<>();
			log.info("Generating policy schedule for policy : " + policyId);

			/**
			 * Generate the document
			 */
			String language = "FR";
			if (!policyDTO.getPolicyHolders().isEmpty()) {
				PolicyHolderDTO firstHolder = policyDTO.getPolicyHolders().iterator().next();
				String product = policyDTO.getProduct().getPrdId();
				language = getLanguage(firstHolder, productCountry, product);
			}

			String userId = workflowUserService.getUserId(context);
			Document document = createSpecificDocument(editingRequest, policyDTO, productCountry, language, userId);
			docs.add(document);
			documents.setDocuments(docs);

			/**
			 * Save as XML
			 */
			String path = getXmlPath(editingRequest, policyId);
			String saveResponse = documentGenerationService.saveXmlDocuments(documents, path);

			/**
			 * Update the request
			 */
			editingRequest.setInputStreamPath(saveResponse);
			editingRequest.setStatus(EditingRequestStatus.XML_GENERATED);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);

		} catch (Exception e) {
			editingRequest.setStatus(EditingRequestStatus.IN_ERROR);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);

			log.error("Error during generating the policy schedule for the policy : " + editingRequest.getPolicy(), e);
		}

		return editingRequest;
	}

	protected PolicyDTO getPolicyByRequest(EditingRequest editingRequest) {
		Assert.notNull(editingRequest.getWorkflowItemId(), "the workflow item id can't be null");
		return policyService.getPolicy(editingRequest.getWorkflowItemId());
	}

	private String getLanguage(ClientDTO clientDTO, String productCountry, String productId) {
		String language = null;
		if (clientDTO.getDocumentationLanguage() != null) {
			language = documentGenerationService.getTransco(TranscoType.DOCUMENT_LANGUAGE, clientDTO.getDocumentationLanguage().toString());
		}

		if (productCountry == null) {
			return language;
		}

		Optional<String> overridedLanguage = getOverridedLanguage(language, productCountry, productId);

		return overridedLanguage.orElse(documentGenerationService.getTransco(TranscoType.DOCUMENT_LANGUAGE, productCountry));
	}

	// TODO : this logic should be implemented in the DB like for the other country transco.
	private Optional<String> getOverridedLanguage(String language, String productCountry, String productId) {
		if (BELGIUM.equals(productCountry) && NL.equals(language)) {
			return Optional.of(NL);
		} else if (BELGIUM.equals(productCountry)) {
			return Optional.of(FR);
		} else if (PORTUGAL.equals(productCountry) && PORTUGUESE_PRODUCT_WITH_PT.equals(productId)) {
			return Optional.of(PT);
		} else if ((SPAIN.equals(productCountry) && SPAIN_PRODUCTS_WITH_EN.contains(productId)) || (LUXEMBOURG.equals(productCountry) && LUXEMBOURG_PRODUCTS_WITH_EN.contains(productId))) {
			return Optional.of(EN);
		} else {
			return Optional.empty();
		}
	}

	protected List<OrderedPerson> generateOnDeathPolicyHolders(PolicyDTO policyDTO) {
		List<CliPolRelationshipType> cliPolRelationships = CliPolRelationshipType.toCliPolRelationshipTypes(
				Arrays.asList(CliPolRelationshipType.JOINT_OWNER_ON_DEATH.getValue(), CliPolRelationshipType.OWNER_ON_DEATH.getValue(), CliPolRelationshipType.ADDITIONAL_OWNER_ON_DEATH.getValue()));
		List<OtherClientDTO> ownersOnDeath = policyDTO.getOtherClients().stream()
				.filter(otherClient -> cliPolRelationships.contains(CliPolRelationshipType.toCliPolRelationshipType(otherClient.getRoleNumber()))).collect(Collectors.toList());

		if (!ownersOnDeath.isEmpty()) {
			return personMapper.asOrderedPersonList(ownersOnDeath);
		}
		return null;
	}

	protected DeathCoverage generateDeathCoverage(
			DeathCoverageClauseDTO deathCoverageClauseDTO, PolicyCoverageDTO policyCoverageDTO,
			DeathCoverageDurationType deathCoverageDurationType) {

		if (deathCoverageClauseDTO.getDeathCoverageTp() == null) {
			return null;
		}
		DeathCoverage deathCoverage = new DeathCoverage();

		Boolean indexed = deathCoverageClauseDTO.getIndexed() != null ? deathCoverageClauseDTO.getIndexed() : null;
		deathCoverage.setIndexed(indexed);

		Boolean optional = deathCoverageClauseDTO.getStandard() != null ? !deathCoverageClauseDTO.getStandard() : null;
		deathCoverage.setOptional(optional);

		deathCoverage.setType(deathCoverageClauseDTO.getDeathCoverageTp());
		deathCoverage.setDuration(deathCoverageDurationType);

		boolean isPercentage = "PERCENTAGE".equals(deathCoverageClauseDTO.getInputType()) || "MULTIPLIER".equals(deathCoverageClauseDTO.getControlType());
		String deathCoverageDefaultValue = deathCoverageClauseDTO.getDefaultValue();
		if (deathCoverageDefaultValue != null) {
			BigDecimal deathCoverageValue = new BigDecimal(deathCoverageDefaultValue);
			if (isPercentage) {
				deathCoverage.setPercentage(deathCoverageValue);
			} else {
				AmountType deathCoverageAmount = new AmountType();
				deathCoverageAmount.setCurrencyCode(policyCoverageDTO.getCurrency());
				deathCoverageAmount.setValue(deathCoverageValue);
				deathCoverage.setAmount(deathCoverageAmount);
			}
		}
		return deathCoverage;
	}

	private Collection<Beneficiary> generateBeneficiaries(Collection<BeneficiaryDTO> beneficiariesDTO) {
		Collection<Beneficiary> beneficiaries = new ArrayList<>();

		if (!CollectionUtils.isEmpty(beneficiariesDTO)) {
			beneficiaries = beneficiaryMapper.asBeneficiaries(beneficiariesDTO);
		}

		return beneficiaries.isEmpty() ? null : beneficiaries;
	}

	protected BeneficiaryClause generateClauses(String clauseType, Collection<PolicyBeneficiaryClauseDTO> policyBeneficiaryClausesDTO, Collection<BeneficiaryDTO> deathBeneficiaries,
			Collection<BeneficiaryDTO> lifeBeneficiaries) {
		BeneficiaryClause beneficiaryClauses = new BeneficiaryClause();

		HashMap<Integer, Collection<PolicyBeneficiaryClauseDTO>> hash = new HashMap<>();
		HashMap<Integer, Collection<BeneficiaryDTO>> hashBenef = new HashMap<>();
		boolean hasClause = false;

		for (PolicyBeneficiaryClauseDTO policyBeneficiaryClauseDTO : policyBeneficiaryClausesDTO) {
			hasClause = true;
			Collection<PolicyBeneficiaryClauseDTO> benefClauseAtRank = hash.get(policyBeneficiaryClauseDTO.getRank());
			if (CollectionUtils.isEmpty(benefClauseAtRank)) {
				hash.put(policyBeneficiaryClauseDTO.getRank(), new ArrayList<>());
			}
			hash.get(policyBeneficiaryClauseDTO.getRank()).add(policyBeneficiaryClauseDTO);
		}

		Collection<BeneficiaryDTO> beneficiaries;
		if (DEATH.equals(clauseType)) {
			beneficiaries = deathBeneficiaries;
		} else if (LIFE.equals(clauseType)) {
			beneficiaries = lifeBeneficiaries;
		} else {
			beneficiaries = null;
		}

		for (BeneficiaryDTO beneficiary : beneficiaries) {
			hasClause = true;
			Collection<BeneficiaryDTO> benefClauseAtRank = hashBenef.get(beneficiary.getTypeNumber());
			if (CollectionUtils.isEmpty(benefClauseAtRank)) {
				hashBenef.put(beneficiary.getTypeNumber(), new ArrayList<>());
			}
			hashBenef.get(beneficiary.getTypeNumber()).add(beneficiary);
		}

		for (Integer rank : hash.keySet()) {
			Clause clause = new Clause();
			clause.setRank(rank);
			for (PolicyBeneficiaryClauseDTO policyBeneficiaryClauseDTO : hash.get(rank)) {

				if (policyBeneficiaryClauseDTO.getTypeOfClause().equals("S")) {
					if (policyBeneficiaryClauseDTO.getCode() != null && !policyBeneficiaryClauseDTO.getCode().isEmpty()) {
						Collection<BenefClauseStdDTO> benefClauseStd = webiaBenefClauseStdService.getByBenefClauseCd(policyBeneficiaryClauseDTO.getCode());
						Collection<StandardClause> standardClauses = new ArrayList<>();
						for (BenefClauseStdDTO benefClauseStdDTO : benefClauseStd) {
							StandardClause stdClause = new StandardClause();
							stdClause.setLanguage(documentGenerationService.getTransco(TranscoType.LANGUAGE, benefClauseStdDTO.getLangCd()));
							stdClause.setValue(benefClauseStdDTO.getBenefClauseText());
							standardClauses.add(stdClause);
						}
						clause.getStandardClauses().addAll(standardClauses);
					}
				} else if (policyBeneficiaryClauseDTO.getTypeOfClause().equals("F")) {
					String freeClause = policyBeneficiaryClauseDTO.getTextOfClause();
					if (clause.getFreeClause() == null) {
						clause.setFreeClause(freeClause);
					} else {
						clause.setFreeClause(clause.getFreeClause() + "\n" + freeClause);
					}
				}

				if (!CollectionUtils.isEmpty(hashBenef.get(rank))) {
					Collection<Beneficiary> beneficiariesAtRank = generateBeneficiaries(hashBenef.get(rank));
					clause.setBeneficiaries(beneficiariesAtRank.stream().collect(Collectors.toList()));
				}
			}
			beneficiaryClauses.getClauses().add(clause);
		}

		return hasClause ? beneficiaryClauses : null;
	}

	protected CorrespondenceAddress generateCorrespondenceAddress(PolicyHolderDTO clientHolder, Policy policy, boolean useCorrespondancyAddress, boolean useProvidedHolder) {

		if (clientHolder != null) {
			ClientContactDetailDTO holderAddress = useCorrespondancyAddress && clientHolder.getCorrespondenceAddress() != null
					? clientHolder.getCorrespondenceAddress()
					: clientHolder.getHomeAddress();
			if (holderAddress == null) {
				return null;
			}
			CorrespondenceAddress correspondenceAddress = personMapper.clientContactDetailAsCorrespondenceAddress(holderAddress);

			List<PersonLight> holderAsPersonLightList = personMapper.holderAsPersonLightList(useProvidedHolder ? Arrays.asList(personMapper.asPolicyHolder(clientHolder)) : policy.getPolicyHolders());

			correspondenceAddress.setPersons(holderAsPersonLightList);

			return correspondenceAddress;
		}
		return null;
	}

	protected CorrespondenceAddress generateCorrespondenceAddress(AgentContactLiteDTO agentContact, PolicyDTO policyDTO) {

		if (agentContact == null) {
			return null;
		}

		AgentDTO agentDTO = agentService.getAgent(agentContact.getAgentId());
		AgentLightDTO contactAgent = agentContact.getContact();

		CorrespondenceAddress correspondenceAddress = personMapper.asCorrespondenceAddress(contactAgent);

		if (!contactAgent.getAgtId().equals(agentDTO.getAgtId())) {
			PersonLight contactPerson = personMapper.asPersonLight(contactAgent);
			correspondenceAddress.setPersons(Arrays.asList(contactPerson));
		}

		correspondenceAddress.setName(agentDTO.getName());

		return correspondenceAddress;
	}

	protected CorrespondenceAddress generateCorrespondenceAddress(PolicyDTO policyDTO, AgentContactLiteDTO agentContact) {
		SendingRuleType sendingRuleType = SendingRuleType.valueOf(policyDTO.getCategory().getKeyValue());

		List<PolicyHolder> holders = new ArrayList<>();
		Collection<PolicyHolderDTO> policyHolders = policyDTO.getPolicyHolders();
		PolicyHolderDTO clientHolder = null;
		if (!policyHolders.isEmpty()) {
			Iterator<PolicyHolderDTO> holderIterator = policyHolders.iterator();
			clientHolder = holderIterator.next();
			holders = generateHolders(policyHolders);
		}

		Policy policy = policyMapper.asPolicy(policyDTO);
		policy.setContractType(documentGenerationService.getContractType(policyDTO.getProduct()));
		if (!holders.isEmpty()) {
			policy.setPolicyHolders(holders);
		}

		switch (sendingRuleType) {
		case MCHA:
		case MCHCA:
		case HMA:
			return generateCorrespondenceAddress(clientHolder, policy, false, false);

		case MCH:
		case MCHC:
		case MH1CH2:
		case HM:
			return generateCorrespondenceAddress(clientHolder, policy, false, false);

		case MCC:
			return generateCorrespondenceAddress(clientHolder, policy, true, false);

		case MCCA:
			return generateCorrespondenceAddress(clientHolder, policy, true, false);

		case MA:
			return generateCorrespondenceAddress(agentContact, policyDTO);

		case MAAC:
			return generateCorrespondenceAddress(agentContact, policyDTO);

		default:
			return null;
		}
	}

	protected InitialPremium generateInitialPremium(PolicyPremiumDTO firstPremium) {

		if (firstPremium == null) {
			return null;
		}

		String currency = firstPremium.getCurrency();
		BigDecimal modalPremium = firstPremium.getModalPremium();
		BigDecimal netPremium = firstPremium.getNetPremium();

		InitialPremium initialPremium = new InitialPremium();

		AmountType premiumAmount = new AmountType();
		premiumAmount.setCurrencyCode(currency);
		premiumAmount.setValue(modalPremium);
		initialPremium.setAmount(premiumAmount);

		if (BigDecimal.ZERO.compareTo(netPremium) < 0) {
			AmountType premiumNetAmount = new AmountType();
			premiumNetAmount.setCurrencyCode(currency);
			premiumNetAmount.setValue(netPremium);
			initialPremium.setNettoAmount(premiumNetAmount);

			AmountType premiumTaxAmount = new AmountType();
			premiumTaxAmount.setCurrencyCode(currency);
			premiumTaxAmount.setValue(modalPremium.subtract(netPremium));
			initialPremium.setTaxAmount(premiumTaxAmount);
		}

		return initialPremium;
	}

	protected List<Transfer> generateTransfer(PolicyDTO policyDTO) {
		Collection<Transfer> transferList = policyTransferMapper.asTransferList(policyDTO.getPolicyTransfers());
		return transferList.stream().collect(Collectors.toList());
	}

	protected InvestmentStrategy generateInvestmentStrategy(Collection<FundTransactionDTO> fundTransactions) {

		InvestmentStrategy investmentStrategy = new InvestmentStrategy();
		SimpleFundsRepartition externalFunds = new SimpleFundsRepartition();
		SimpleFundsRepartition internalFunds = new SimpleFundsRepartition();
		List<SpecializedInsuranceFundsRepartition> specializedInsuranceFunds = new ArrayList<>();
		List<DedicatedFundsRepartition> dedicatedFunds = new ArrayList<>();

		BigDecimal sumFE = BigDecimal.ZERO;
		BigDecimal sumFIC = BigDecimal.ZERO;
		BigDecimal policyValue = BigDecimal.ZERO;
		
		FundInvested fundInvested;

		for (FundTransactionDTO fundTransactionDTO : fundTransactions) {
			policyValue = policyValue.add(fundTransactionDTO.getValuePolCcy());
		}

		for (FundTransactionDTO fundTransactionDTO : fundTransactions) {
			Fund fund = null;
			FundDTO fundDTO = fundService.getFund(fundTransactionDTO.getFund());
			Fee financialFee = null;
			Fee financialAdvisorFee = null;
			Fee financialInsurerFee = null;
			Fee bankDepositFee = null;

			BigDecimal finAdvisorFee = fundDTO.getFinAdvisorFee();
			BigDecimal assetManagerFee = fundDTO.getAssetManagerFee();
			BigDecimal finFeeFlatAmount = fundDTO.getFinFeesFlatAmount();

			switch (fundDTO.getFundSubType()) {
			case "FID":
				fund = fundMapper.asFund(fundDTO);
				fund.setRiskProfile(generateRiskProfil(fundDTO));
				fund.setSpecificRiskActive(documentGenerationService.isSpecificRiskActive(fundDTO.getAlternativeFunds()));

				DedicatedFundsRepartition dedicatedFundsRepartition = new DedicatedFundsRepartition();
				dedicatedFundsRepartition.setFund(fund);
				AssetManager assetManager = personMapper.asAssetManager(fundDTO.getAssetManagerAgent());
				assetManager.setSalesRep(fundDTO.getSalesRep());
				dedicatedFundsRepartition.setAssetManager(assetManager);
				dedicatedFundsRepartition.setCustodianAccount(getCustodianAccount(fundDTO));
				dedicatedFundsRepartition.setInvestmentPart(computePercentage(policyValue, fundTransactionDTO.getValuePolCcy()));
				dedicatedFundsRepartition.setOtherFees(fundDTO.getPerformanceFee());

				if (BooleanUtils.isTrue(fundDTO.getExAllInFees())) {
					financialFee = new Fee();

					if (finFeeFlatAmount != null && BigDecimal.ZERO.compareTo(finFeeFlatAmount) < 0) {
						AmountType financialFeeAmount = new AmountType();
						financialFeeAmount.setCurrencyCode(fundDTO.getAssetManFeeCcy());
						financialFeeAmount.setValue(finFeeFlatAmount);
						financialFee.setAmount(financialFeeAmount);

					} else {
						financialFee.setRate(assetManagerFee);
					}

					dedicatedFundsRepartition.setAllInFees(financialFee);
				} else {
					financialFee = new Fee();

					if (finFeeFlatAmount != null && BigDecimal.ZERO.compareTo(finFeeFlatAmount) < 0) {
						AmountType financialFeeAmount = new AmountType();
						financialFeeAmount.setCurrencyCode(fundDTO.getAssetManFeeCcy());
						financialFeeAmount.setValue(finFeeFlatAmount);
						financialFee.setAmount(financialFeeAmount);

					} else {
						financialFee.setRate(assetManagerFee);
					}

					dedicatedFundsRepartition.setFinancialFees(financialFee);

					bankDepositFee = new Fee();
					bankDepositFee.setRate(fundDTO.getBankDepositFee());

					dedicatedFundsRepartition.setCustodianFees(bankDepositFee);
				}

				dedicatedFunds.add(dedicatedFundsRepartition);

				break;
			case "FIC":
				fund = fundMapper.asFund(fundDTO);

				fundInvested = new FundInvested();
				fundInvested.setFund(fund);
				internalFunds.setInvestmentPart(computePercentage(policyValue, fundTransactionDTO.getValuePolCcy()));
				internalFunds.getFundInvesteds().add(fundInvested);

				sumFIC = sumFIC.add(fundTransactionDTO.getValuePolCcy());

				break;
			case "FE":
				fund = fundMapper.asFund(fundDTO);

				fundInvested = new FundInvested();
				fundInvested.setFund(fund);
				fundInvested.setInvestedAmount(fundTransactionDTO.getValuePolCcy());
				externalFunds.getFundInvesteds().add(fundInvested);

				sumFE = sumFE.add(fundTransactionDTO.getValuePolCcy());

				break;

			case "FAS":
				fund = fundMapper.asFund(fundDTO);
				fund.setRiskProfile(generateRiskProfil(fundDTO));
				fund.setSpecificRiskActive(documentGenerationService.isSpecificRiskActive(fundDTO.getAlternativeFunds()));

				SpecializedInsuranceFundsRepartition specializedInsuranceFundsRepartition = new SpecializedInsuranceFundsRepartition();
				specializedInsuranceFundsRepartition.setFund(fund);
				specializedInsuranceFundsRepartition.setCustodianAccount(getCustodianAccount(fundDTO));

				BigDecimal financialFeeRate = finAdvisorFee;
				if (financialFeeRate == null) {
					financialFeeRate = assetManagerFee;
				} else {
					if (assetManagerFee != null) {
						financialFeeRate = financialFeeRate.add(assetManagerFee);
					}
				}

				// No financial advisor fees for non PSI financial advisor on FAS
				AgentDTO financialAdvisorAgent = fundDTO.getFinancialAdvisorAgent();
				if (financialAdvisorAgent != null) {
					String financialAdvisorCategory = financialAdvisorAgent.getCategory();
					if (financialAdvisorCategory != null && AgentCategory.PRESTATION_SERVICE_INVEST.getCategory().equals(financialAdvisorCategory)) {
						financialAdvisorFee = new Fee();
						financialAdvisorFee.setRate(finAdvisorFee);
						specializedInsuranceFundsRepartition.setFinancialAdvisorFees(financialAdvisorFee);
					}
				}

				financialInsurerFee = new Fee();
				financialInsurerFee.setRate(assetManagerFee);
				specializedInsuranceFundsRepartition.setFinancialInsurerFees(financialInsurerFee);

				if (financialFeeRate != null) {
					financialFee = new Fee();
					financialFee.setRate(financialFeeRate);
					specializedInsuranceFundsRepartition.setFinancialFees(financialFee);
				}

				bankDepositFee = new Fee();
				bankDepositFee.setRate(fundDTO.getBankDepositFee());
				specializedInsuranceFundsRepartition.setCustodianFees(bankDepositFee);

				specializedInsuranceFundsRepartition.setInvestmentPart(computePercentage(policyValue, fundTransactionDTO.getValuePolCcy()));

				specializedInsuranceFunds.add(specializedInsuranceFundsRepartition);
				break;
			case "UNKNOWN":

				break;

			default:
				break;
			}
			
			if(fund != null) {
				fund.setAlternativeFund(isAlternativeFund(fundDTO, fund));
			}
		}

		final BigDecimal sFE = sumFE;
		final BigDecimal sFIC = sumFIC;

		// Repartition for FE
		externalFunds.setInvestmentPart(computePercentage(policyValue, sumFE));
		externalFunds.getFundInvesteds().forEach(fe -> {
			fe.setRepartition(computePercentage(sFE, fe.getInvestedAmount()));
		});
		investmentStrategy.setExternalFunds(externalFunds.getFundInvesteds().isEmpty() ? null : externalFunds);

		// Repartition for FIC
		internalFunds.setInvestmentPart(computePercentage(policyValue, sumFIC));
		internalFunds.getFundInvesteds().forEach(fic -> {
			fic.setRepartition(computePercentage(sFIC, fic.getInvestedAmount()));
		});
		investmentStrategy.setInternalFunds(internalFunds.getFundInvesteds().isEmpty() ? null : internalFunds);

		investmentStrategy.setDedicatedFunds(dedicatedFunds.isEmpty() ? null : dedicatedFunds);
		investmentStrategy.setSpecializedInsuranceFunds(specializedInsuranceFunds.isEmpty() ? null : specializedInsuranceFunds);

		return investmentStrategy;
	}

	protected List<Investment> generateInvestments(Collection<FundTransactionDTO> fundTransactions, Policy policy) {

		Date eventDate = policy.getEffectDate();

		if (fundTransactions == null || fundTransactions.isEmpty()) {
			return null;
		}

		BigDecimal policyValue = BigDecimal.ZERO;
		Date valueDate;

		List<Investment> investments = new ArrayList<>();
		List<InvestmentDetail> investmentDetails = new ArrayList<>();
		Investment investment = new Investment();

		for (FundTransactionDTO fundTransactionDTO : fundTransactions) {
			policyValue = policyValue.add(fundTransactionDTO.getValuePolCcy());
		}

		for (FundTransactionDTO fundTransaction : fundTransactions) {
			InvestmentDetail investmentDetail = new InvestmentDetail();

			FundDTO fund = fundService.getFund(fundTransaction.getFund());
			investmentDetail.setFundName(fund.getDocumentationName());

			BigDecimal transactionValueInPolicyCurrency = fundTransaction.getValuePolCcy();
			if (transactionValueInPolicyCurrency != null) {
				AmountType holdingValueInPolicyCurrencyAmountType = new AmountType();
				holdingValueInPolicyCurrencyAmountType.setValue(transactionValueInPolicyCurrency);
				holdingValueInPolicyCurrencyAmountType.setCurrencyCode(policy.getCurrency());
				investmentDetail.setAmountInContractCurrency(holdingValueInPolicyCurrencyAmountType);
			}

			BigDecimal transactionValueInFundCurrency = fundTransaction.getValueFundCcy();
			if (transactionValueInFundCurrency != null) {
				AmountType holdingValueInFundCurrencyAmountType = new AmountType();
				holdingValueInFundCurrencyAmountType.setValue(transactionValueInFundCurrency);
				holdingValueInFundCurrencyAmountType.setCurrencyCode(fundTransaction.getFundCurrency());
				investmentDetail.setAmountInFundCurrency(holdingValueInFundCurrencyAmountType);
			}
			investmentDetail.setNumberOfUnit(fundTransaction.getUnits());
			BigDecimal share = computePercentage(policyValue, transactionValueInPolicyCurrency);
			investmentDetail.setShare(share);

			valueDate = DEFAULT_DATE.equals(fundTransaction.getDate0()) ? eventDate : fundTransaction.getDate0();
			investmentDetail.setValueDate(valueDate);

			BigDecimal price = fundTransaction.getPrice();
			if (price != null) {
				AmountType priceAmountType = new AmountType();
				priceAmountType.setValue(price);
				priceAmountType.setCurrencyCode(fundTransaction.getFundCurrency());
				investmentDetail.setValuePerUnit(priceAmountType);
			}

			investmentDetails.add(investmentDetail);

		}
		investment.setInvestmentDetails(investmentDetails);

		AmountType totalInvestment = new AmountType();
		totalInvestment.setCurrencyCode(policy.getCurrency());
		totalInvestment.setValue(policyValue);
		investment.setTotalInvestment(totalInvestment);

		investments.add(investment);

		return investments;
	}

	protected List<Disinvestment> generateDisinvestments(Collection<FundTransactionDTO> fundTransactions, Policy policy) {
		Date eventDate = policy.getEffectDate();

		List<Disinvestment> disinvestments = new ArrayList<Disinvestment>();
		List<DisinvestmentDetail> disinvestmentDetails = new ArrayList<>();
		Disinvestment disinvestment = new Disinvestment();
		
		BigDecimal policyValue = BigDecimal.ZERO;
		Date valueDate;

		for (FundTransactionDTO fundTransactionDTO : fundTransactions) {
			policyValue = policyValue.add(fundTransactionDTO.getValuePolCcy());
		}
		Set<String> fundList = fundTransactions.stream().map(x -> x.getFund()).collect(Collectors.toSet());
		for (String fun : fundList) {

			DisinvestmentDetail disinvestmentDetail = new DisinvestmentDetail();

			FundDTO fund = fundService.getFund(fun);
			disinvestmentDetail.setFundName(fund.getDocumentationName());

			BigDecimal transactionValueInPolicyCurrency = fundTransactions.stream().filter(x -> StringUtils.compareIgnoreCase(fun, x.getFund()) == 0).map(x -> x.getValuePolCcy())
					.reduce(BigDecimal.ZERO, BigDecimal::add); // reduce(identity, accumulator)
																																				// fundTransaction.getValuePolCcy();
			if (transactionValueInPolicyCurrency != null) {
				AmountType holdingValueInPolicyCurrencyAmountType = new AmountType();
				holdingValueInPolicyCurrencyAmountType.setValue(transactionValueInPolicyCurrency.abs());
				holdingValueInPolicyCurrencyAmountType.setCurrencyCode(policy.getCurrency());
				disinvestmentDetail.setAmountInContractCurrency(holdingValueInPolicyCurrencyAmountType);
			}

			BigDecimal transactionValueInFundCurrency = fundTransactions.stream().filter(x -> StringUtils.compareIgnoreCase(fun, x.getFund()) == 0).map(x -> x.getValueFundCcy())
					.reduce(BigDecimal.ZERO, BigDecimal::add); // fundTransaction.getValueFundCcy();
			if (transactionValueInFundCurrency != null) {
				AmountType holdingValueInFundCurrencyAmountType = new AmountType();
				holdingValueInFundCurrencyAmountType.setValue(transactionValueInFundCurrency.abs());
				holdingValueInFundCurrencyAmountType.setCurrencyCode(fund.getCurrency());
				disinvestmentDetail.setAmountInFundCurrency(holdingValueInFundCurrencyAmountType);
			}
			BigDecimal units = fundTransactions.stream().filter(x -> StringUtils.compareIgnoreCase(fun, x.getFund()) == 0).map(x -> x.getUnits()).reduce(BigDecimal.ZERO, BigDecimal::add);
			disinvestmentDetail.setNumberOfUnit(units.abs());
			BigDecimal share = computePercentage(policyValue, transactionValueInPolicyCurrency);
			disinvestmentDetail.setShare(share);

			valueDate = DEFAULT_DATE.equals(fundTransactions.stream().filter(x -> StringUtils.compareIgnoreCase(fun, x.getFund()) == 0).findAny().get().getDate0()) ? eventDate
					: fundTransactions.stream().filter(x -> StringUtils.compareIgnoreCase(fun, x.getFund()) == 0).findAny().get().getDate0();
			disinvestmentDetail.setValueDate(valueDate);

			BigDecimal price = fundTransactions.stream().filter(x -> StringUtils.compareIgnoreCase(fun, x.getFund()) == 0).findAny().get().getPrice(); // fundTransaction.getPrice();
			if (price != null) {
				AmountType priceAmountType = new AmountType();
				priceAmountType.setValue(price);
				priceAmountType.setCurrencyCode(fund.getCurrency());
				disinvestmentDetail.setValuePerUnit(priceAmountType);
			}

			disinvestmentDetails.add(disinvestmentDetail);

		}
		
		disinvestment.setDisinvestmentDetails(disinvestmentDetails);

		AmountType totalInvestment = new AmountType();
		totalInvestment.setCurrencyCode(policy.getCurrency());
		totalInvestment.setValue(policyValue);
		disinvestment.setTotalDisinvestment(totalInvestment);

		disinvestments.add(disinvestment);
		
		return disinvestments;
	}

	protected List<Situation> getPolicyValuation(Policy policy, Date date) {

		List<Situation> situations = new ArrayList<>();

		List<InvestmentDetail> investmentDetails = new ArrayList<>();

		Situation investment = new Situation();

		PolicyValuationDTO valuation = getPolicyValuation(policy.getPolicyId(), sdf.format(date));

		for (PolicyValuationHoldingDTO holding : valuation.getHoldings()) {
			InvestmentDetail investmentDetail = new InvestmentDetail();

			FundDTO fund = fundService.getFund(holding.getFundId());
			investmentDetail.setFundName(fund.getDocumentationName());

			BigDecimal transactionValueInPolicyCurrency = holding.getHoldingValuePolicyCurrency();
			if (transactionValueInPolicyCurrency != null) {
				AmountType holdingValueInPolicyCurrencyAmountType = new AmountType();
				holdingValueInPolicyCurrencyAmountType.setValue(transactionValueInPolicyCurrency);
				holdingValueInPolicyCurrencyAmountType.setCurrencyCode(policy.getCurrency());
				investmentDetail.setAmountInContractCurrency(holdingValueInPolicyCurrencyAmountType);
			}

			BigDecimal transactionValueInFundCurrency = holding.getHoldingValueFundCurreny();
			if (transactionValueInFundCurrency != null) {
				AmountType holdingValueInFundCurrencyAmountType = new AmountType();
				holdingValueInFundCurrencyAmountType.setValue(transactionValueInFundCurrency);
				holdingValueInFundCurrencyAmountType.setCurrencyCode(holding.getFundCurrency());
				investmentDetail.setAmountInFundCurrency(holdingValueInFundCurrencyAmountType);
			}
			investmentDetail.setNumberOfUnit(holding.getUnits());
			BigDecimal share = computePercentage(valuation.getTotalPolicyCurrency(), transactionValueInPolicyCurrency);
			investmentDetail.setShare(share);

			investmentDetail.setValueDate(holding.getPriceDate());

			BigDecimal price = holding.getPrice();
			if (price != null) {
				AmountType priceAmountType = new AmountType();
				priceAmountType.setValue(price);
				priceAmountType.setCurrencyCode(holding.getFundCurrency());
				investmentDetail.setValuePerUnit(priceAmountType);
			}

			investmentDetails.add(investmentDetail);
		}
		investment.setInvestmentDetails(investmentDetails);

		AmountType totalInvestment = new AmountType();
		totalInvestment.setCurrencyCode(policy.getCurrency());
		totalInvestment.setValue(valuation.getTotalPolicyCurrency());

		investment.setTotalInvestment(totalInvestment);
		situations.add(investment);

		return situations;

	}

	protected PolicyValuationDTO getPolicyValuation(String policy, String date) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.add("id", policy);
		queryParams.add("date", date);
		return restClientUtils.get(LIABILITY_VALUATION, "", queryParams, PolicyValuationDTO.class);
	}

	protected List<Situation> generateSituation(Collection<FundTransactionDTO> fundTransactions, Policy policy, Date TransactionDate) {

		Date eventDate = TransactionDate;

		if (fundTransactions == null || fundTransactions.isEmpty()) {
			return null;
		}

		BigDecimal policyValue = BigDecimal.ZERO;
		Date valueDate;

		List<Situation> situations = new ArrayList<>();
		List<InvestmentDetail> investmentDetails = new ArrayList<>();
		Situation investment = new Situation();

		for (FundTransactionDTO fundTransactionDTO : fundTransactions) {
			policyValue = policyValue.add(fundTransactionDTO.getValuePolCcy());
		}

		for (FundTransactionDTO fundTransaction : fundTransactions) {
			InvestmentDetail investmentDetail = new InvestmentDetail();

			FundDTO fund = fundService.getFund(fundTransaction.getFund());
			investmentDetail.setFundName(fund.getDocumentationName());

			BigDecimal transactionValueInPolicyCurrency = fundTransaction.getValuePolCcy();
			if (transactionValueInPolicyCurrency != null) {
				AmountType holdingValueInPolicyCurrencyAmountType = new AmountType();
				holdingValueInPolicyCurrencyAmountType.setValue(transactionValueInPolicyCurrency);
				holdingValueInPolicyCurrencyAmountType.setCurrencyCode(policy.getCurrency());
				investmentDetail.setAmountInContractCurrency(holdingValueInPolicyCurrencyAmountType);
			}

			BigDecimal transactionValueInFundCurrency = fundTransaction.getValueFundCcy();
			if (transactionValueInFundCurrency != null) {
				AmountType holdingValueInFundCurrencyAmountType = new AmountType();
				holdingValueInFundCurrencyAmountType.setValue(transactionValueInFundCurrency);
				holdingValueInFundCurrencyAmountType.setCurrencyCode(fundTransaction.getFundCurrency());
				investmentDetail.setAmountInFundCurrency(holdingValueInFundCurrencyAmountType);
			}
			investmentDetail.setNumberOfUnit(fundTransaction.getUnits());
			BigDecimal share = computePercentage(policyValue, transactionValueInPolicyCurrency);
			investmentDetail.setShare(share);

			valueDate = DEFAULT_DATE.equals(fundTransaction.getDate0()) ? eventDate : fundTransaction.getDate0();
			investmentDetail.setValueDate(valueDate);

			BigDecimal price = fundTransaction.getPrice();
			if (price != null) {
				AmountType priceAmountType = new AmountType();
				priceAmountType.setValue(price);
				priceAmountType.setCurrencyCode(fundTransaction.getFundCurrency());
				investmentDetail.setValuePerUnit(priceAmountType);
			}

			investmentDetails.add(investmentDetail);

		}
		investment.setInvestmentDetails(investmentDetails);

		AmountType totalInvestment = new AmountType();
		totalInvestment.setCurrencyCode(policy.getCurrency());
		totalInvestment.setValue(policyValue);
		investment.setTotalInvestment(totalInvestment);

		situations.add(investment);

		return situations;
	}

	protected RiskProfile generateRiskProfil(FundDTO fund) {

		RiskProfile rp = new RiskProfile();

		int n = 3;
		int length = fund.getRiskProfile().length();
		String result = fund.getRiskProfile().substring(length - n, length);
		Integer max = 0;
		String root = fund.getRiskProfile();
		try {
			max = Integer.parseInt(result);
			root = fund.getRiskProfile().substring(0, length - n);
		} catch (NumberFormatException e) {
			log.info("Risk profil, doesn't end with the rate");
		}

		rp.setRiskProfileCode(fund.getRiskProfile());
		rp.setMaxSharePart(BigDecimal.valueOf(max));
		rp.setRiskProfileRoot(root);
		rp.setNotes(fund.getExRiskProfileNotes());

		return rp;
	}

	protected List<PolicyHolder> generateHolders(Collection<PolicyHolderDTO> holdersDTO) {
		List<PolicyHolder> holders = new ArrayList<>();
		int i = 0;
		for (PolicyHolderDTO holderDTO : holdersDTO) {
			if (!holderDTO.isDead()) {
				PolicyHolder holder = personMapper.asPolicyHolder(holderDTO);

				// If no rank defined
				// -> BareOwner => rank = 0
				// -> !BareOwner => rank = 1
				int order;
				if (BooleanUtils.isTrue(holder.isBareOwner())) {
					order = 0;
				} else {
					order = ++i;
				}
				holder.setOrder(order);

				holder.setLastRanking(holderDTO.getDeathSuccessor() || holderDTO.getLifeSuccessor());

				holders.add(holder);
			}
		}
		return holders;
	}

	protected List<OrderedPerson> generateInsureds(Collection<InsuredDTO> insuredsDTO) {
		List<OrderedPerson> insureds = new ArrayList<>();
		Integer i = 1;
		for (InsuredDTO insuredDTO : insuredsDTO) {
			OrderedPerson orderedPerson = personMapper.asOrderedPerson(insuredDTO);
			orderedPerson.setAddress(personMapper.asAddress(insuredDTO.getHomeAddress()));
			orderedPerson.setOrder(i++);
			insureds.add(orderedPerson);
		}
		return insureds;
	}

	protected Limits generateLimits(String country) {
		AmountType amountType;

		Limits limits = new Limits();

		Collection<String> minAdditionalPaymentAmounts = webiaApplicationParameterService.getApplicationParameters(MIN_ADDITIONAL_PAYMENT_AMOUNT);
		BigDecimal minAdditionalPaymentAmount = extractParamValue(minAdditionalPaymentAmounts, country);
		amountType = new AmountType();
		amountType.setCurrencyCode("EUR");
		amountType.setValue(minAdditionalPaymentAmount);
		limits.setMinAdditionalPaymentAmount(minAdditionalPaymentAmount != null ? amountType : null);

		Collection<String> minContractAmounts = webiaApplicationParameterService.getApplicationParameters(MIN_CONTRACT_AMOUNT);
		BigDecimal minContractAmount = extractParamValue(minContractAmounts, country);
		amountType = new AmountType();
		amountType.setCurrencyCode("EUR");
		amountType.setValue(minContractAmount);
		limits.setMinContractAmount(minContractAmount != null ? amountType : null);

		Collection<String> minFidAmounts = webiaApplicationParameterService.getApplicationParameters(MIN_FID_AMOUNT);
		BigDecimal minFidAmount = extractParamValue(minFidAmounts, country);
		amountType = new AmountType();
		amountType.setCurrencyCode("EUR");
		amountType.setValue(minFidAmount);
		limits.setMinFIDAmount(minFidAmount != null ? amountType : null);

		Collection<String> minWithdrawalAmounts = webiaApplicationParameterService.getApplicationParameters(MIN_WITHDRAWAL_AMOUNT);
		BigDecimal minWithdrawalAmount = extractParamValue(minWithdrawalAmounts, country);
		amountType = new AmountType();
		amountType.setCurrencyCode("EUR");
		amountType.setValue(minWithdrawalAmount);
		limits.setMinWithdrawalAmount(minWithdrawalAmount != null ? amountType : null);

		return limits;

	}

	protected boolean sendACopy(PolicyDTO policyDTO) {
		SendingRuleType sendingRuleType = SendingRuleType.valueOf(policyDTO.getCategory().getKeyValue());

		return SendingRuleType.SEND_A_COPY_GROUP.contains(sendingRuleType);
	}

	protected DeathCoverageDurationType computeDeathCoverageDuration(List<OrderedPerson> insureds, Date policyStartDate, BigInteger contractDuration) {

		// If the collection of LAs is empty (CAPI product)
		if (insureds.isEmpty()) {
			return null;
		}

		int nbInsured = insureds.size();

		// If whole life
		if (BigInteger.ZERO.equals(contractDuration)) {
			if (nbInsured > 1) {
				// More than one LA
				return DeathCoverageDurationType.OLDEST_80;
			} else {
				// One LA
				return DeathCoverageDurationType.INSURED_80;
			}
		}

		// Get the oldest LA
		OrderedPerson oldestInsured = null;
		for (OrderedPerson insured : insureds) {
			if (oldestInsured == null) {
				oldestInsured = insured;
			} else {
				if (oldestInsured.getBirthDate().compareTo(insured.getBirthDate()) > 0) {
					oldestInsured = insured;
				}
			}
		}

		LocalDate insuredBirthDate = oldestInsured.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate policyStartLocalDate = policyStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int oldestInsuredAgeAtSubscription = Period.between(insuredBirthDate, policyStartLocalDate).getYears();
		int oldestInsuredAgeAtTerm = Math.addExact(oldestInsuredAgeAtSubscription, contractDuration.intValue());

		// Not whole life
		if (oldestInsuredAgeAtTerm > 80) {
			// The oldest insured will have more than 80 years at the end of the policy
			if (nbInsured > 1) {
				// More than one LA
				return DeathCoverageDurationType.OLDEST_80;
			} else {
				// One LA
				return DeathCoverageDurationType.INSURED_80;
			}
		} else {
			// The oldest insured will have less than 81 years at the end of the policy
			return DeathCoverageDurationType.CONTRACT;
		}

	}

	/**
	 * Get the first premium
	 * 
	 * @param premiums
	 * @return
	 */
	protected PolicyPremiumDTO getFirstPremium(List<PolicyPremiumDTO> premiums) {
		Comparator<PolicyPremiumDTO> comparatorCreationDate = Comparator.comparing(
				PolicyPremiumDTO::getCreatedDate, (s1, s2) -> {
					return s2.compareTo(s1);
				});
		return premiums.stream().max(comparatorCreationDate).get();
	}

	protected Collection<FundTransactionDTO> getFundTransactions(Policy policy) {
		Date eventDate = policy.getEffectDate();
		String policyId = policy.getPolicyId();

		FundTransactionRequest fundTransactionRequest = new FundTransactionRequest();
		fundTransactionRequest.setEventDate(eventDate);
		fundTransactionRequest.setPolicyId(policyId);

		Collection<FundTransactionDTO> fundTransactions = liabilityFundTransactionService.getSubscriptionOrAdditionFundTransactions(fundTransactionRequest).getFundTransactions();

		if (fundTransactions == null || fundTransactions.isEmpty()) {
			return null;
		}
		return fundTransactions;
	}

	protected Collection<FundTransactionDTO> getFundTransactionsByPolicyAndTranDate(Policy policy, Date transactionDate) {
		String policyId = policy.getPolicyId();

		FundTransactionRequest fundTransactionRequest = new FundTransactionRequest();
		fundTransactionRequest.setEventDate(transactionDate);
		fundTransactionRequest.setPolicyId(policyId);

		Collection<FundTransactionDTO> fundTransactions = liabilityFundTransactionService.getSubscriptionOrAdditionFundTransactions(fundTransactionRequest).getFundTransactions();

		if (fundTransactions == null || fundTransactions.isEmpty()) {
			return null;
		}
		return fundTransactions;
	}

	protected Boolean hasMedicalQuestionnaire(String policyId) {
		CheckDataDTO checkData = webiaCheckDataService.getCheckData(policyId, MEDICAL_QUEST_CHECK_ID);

		if (checkData == null) {
			log.warn("The policy " + policyId + "has not a check data for medical questionaire (ID : 75)");
			return null;
		}

		return "YES".equals(checkData.getDataValueYesNoNa());
	}

	protected BigDecimal computePercentage(BigDecimal numerator, BigDecimal denominator) {
		if (numerator == null || denominator == null || BigDecimal.ZERO.compareTo(denominator) == 0) {
			return null;
		}
		BigDecimal dividedValue = numerator.divide(denominator, 5, RoundingMode.HALF_UP);
		if (dividedValue == null || BigDecimal.ZERO.equals(dividedValue)) {
			return null;
		}
		return BigDecimal.valueOf(100).divide(dividedValue, 2, RoundingMode.HALF_UP);
	}

	private BigDecimal extractParamValue(Collection<String> collectionOfParamValues, String country) {
		for (String minAdditionalPaymentAmount : collectionOfParamValues) {
			String[] keyValue = minAdditionalPaymentAmount.split(":");
			if (country.equals(StringUtils.trim(keyValue[0]))) {
				return new BigDecimal(keyValue[1]);
			}
		}
		return null;
	}

	protected Account getCustodianAccount(FundDTO fundDTO) {
		AgentDTO depositBank = fundDTO.getDepositBankAgent();

		Account account = new Account();
		account.setBankName(depositBank.getName());

		Collection<AgentBankAccountDTO> bankAccounts = depositBank.getBankAccounts();
		if (!bankAccounts.isEmpty()) {
			AgentBankAccountDTO agentBankAccountDTO = bankAccounts.iterator().next();

			account.setAccountNumber(agentBankAccountDTO.getAccountName());
			account.setCurrency(agentBankAccountDTO.getAccountCurrency());
			account.setIban(agentBankAccountDTO.getIban());
		}
		return account;
	}

	protected FundDTO getMailToAgentFund(AgentDTO mailToAgent, Collection<FundTransactionDTO> fundTransactions) {
		String agentCategory = mailToAgent.getCategory();
		String agtId = mailToAgent.getAgtId();

		String am = AgentCategory.ASSET_MANAGER.getCategory();
		String db = AgentCategory.DEPOSIT_BANK.getCategory();

		if (mailToAgent != null && fundTransactions != null) {
			for (FundTransactionDTO fundTransaction : fundTransactions) {
				FundDTO fund = fundService.getFund(fundTransaction.getFund());
				if (am.equals(agentCategory) && agtId.equals(fund.getAssetManager())) {
					return fund;
				} else if (db.equals(agentCategory) && agtId.equals(fund.getDepositBank())) {
					return fund;
				} else if (agtId.equals(fund.getFinancialAdvisor())) {
					return fund;
				}
			}
		}
		return null;

	}

	protected CoverLetter generateCoverLetter(Policy policy, PolicyAgentShareDTO broker, List<PolicyHolder> holders) {
		CoverLetter coverLetter = new CoverLetter();
		coverLetter.setPolicy(policy);
		if (broker != null) {
			AgentLightDTO agentLight = broker.getAgent();
			AgentDTO agent = null;

			if (agentLight != null && agentLight.getAgtId() != null) {
				agent = agentService.getAgent(agentLight.getAgtId());
			}

			Person intermediary = personMapper.asPerson(agent);
			// intermediary.setTitleId(documentGenerationService.getTransco(TranscoType.TITLE, intermediary.getTitleId()));
			coverLetter.setIntermediary(intermediary);
		}

		if (holders != null && !holders.isEmpty()) {

			PolicyHolder firstHolder = holders.get(0);

			CorrespondenceAddress correspondenceAddress = personMapper.holderAsCorrespondenceAddress(firstHolder);
			List<PersonLight> holderAsPersonLightList = personMapper.holderAsPersonLightList(holders);
			correspondenceAddress.setPersons(holderAsPersonLightList);
			coverLetter.setCorrespondenceAddress(correspondenceAddress);
		}
		coverLetter.setTitleId(computeTitleId(holders));

		return coverLetter;
	}

	protected List<MailingAddress> generateMailingAddress(PolicyDTO policyDTO) {
		SendingRuleType sendingRuleType = SendingRuleType.valueOf(policyDTO.getCategory().getKeyValue());

		List<PolicyHolder> holders = new ArrayList<>();
		Collection<PolicyHolderDTO> policyHolders = policyDTO.getPolicyHolders();
		PolicyHolderDTO clientHolder = null;
		PolicyHolderDTO clientHolder2 = null;
		if (!policyHolders.isEmpty()) {
			Iterator<PolicyHolderDTO> holderIterator = policyHolders.iterator();
			clientHolder = holderIterator.next();
			if (holderIterator.hasNext()) {
				clientHolder2 = holderIterator.next();
			}
			holders = generateHolders(policyHolders);
		}

		Policy policy = policyMapper.asPolicy(policyDTO);
		policy.setContractType(documentGenerationService.getContractType(policyDTO.getProduct()));
		if (!holders.isEmpty()) {
			policy.setPolicyHolders(holders);
		}

		List<MailingAddress> mailingAddresses = new ArrayList<>();

		switch (sendingRuleType) {
		case MCH:
		case HM:
		case HMNF:
			return null;

		case MCHA:
		case HMA:
			mailingAddresses.add(generateMailingAddress(policyDTO.getMailToAgent(), policy, policyDTO, LetterType.COPY));
			break;

		case MCHC:
			mailingAddresses.add(generateMailingAddress(clientHolder, policy, LetterType.COPY, true, false));
			break;

		case MCHCA:
			mailingAddresses.add(generateMailingAddress(clientHolder, policy, LetterType.COPY, true, false));
			mailingAddresses.add(generateMailingAddress(policyDTO.getMailToAgent(), policy, policyDTO, LetterType.COPY));
			break;

		case MCC:
			mailingAddresses.add(generateMailingAddress(clientHolder, policy, LetterType.INFO, true, false));
			break;

		case MCCA:
			mailingAddresses.add(generateMailingAddress(clientHolder, policy, LetterType.INFO, true, false));
			mailingAddresses.add(generateMailingAddress(policyDTO.getMailToAgent(), policy, policyDTO, LetterType.COPY));
			break;

		case MA:
			mailingAddresses.add(generateMailingAddress(policyDTO.getMailToAgent(), policy, policyDTO, LetterType.INFO));
			break;
		case MAAC:
			mailingAddresses.add(generateMailingAddress(policyDTO.getMailToAgent(), policy, policyDTO, LetterType.INFO));
			mailingAddresses.add(generateMailingAddress(policyDTO.getMailToAgent(), policy, policyDTO, LetterType.COPY));

			break;
		case MH1CH2:
			mailingAddresses.add(generateMailingAddress(clientHolder2, policy, LetterType.COPY, false, true));
			break;

		default:
			log.error("SendingRuleType not yet implemented");
			return null;
		}
		return mailingAddresses;
	}

	protected List<MailingAddress> generateMailingAddress(PolicyDTO policyDTO, SendingRuleType sendingRuleType) {

		List<PolicyHolder> holders = new ArrayList<>();
		Collection<PolicyHolderDTO> policyHolders = policyDTO.getPolicyHolders();
		PolicyHolderDTO clientHolder = null;
		PolicyHolderDTO clientHolder2 = null;
		if (!policyHolders.isEmpty()) {
			Iterator<PolicyHolderDTO> holderIterator = policyHolders.iterator();
			clientHolder = holderIterator.next();
			if (holderIterator.hasNext()) {
				clientHolder2 = holderIterator.next();
			}
			holders = generateHolders(policyHolders);
		}

		Policy policy = policyMapper.asPolicy(policyDTO);
		policy.setContractType(documentGenerationService.getContractType(policyDTO.getProduct()));
		if (!holders.isEmpty()) {
			policy.setPolicyHolders(holders);
		}

		List<MailingAddress> mailingAddresses = new ArrayList<>();

		switch (sendingRuleType) {
		case MCH:
		case HM:
		case HMNF:
			return null;

		case MCHA:
		case HMA:
			mailingAddresses.add(generateMailingAddress(policyDTO.getMailToAgent(), policy, policyDTO, LetterType.COPY));
			break;

		case MCHC:
			mailingAddresses.add(generateMailingAddress(clientHolder, policy, LetterType.COPY, true, false));
			break;

		case MCHCA:
			mailingAddresses.add(generateMailingAddress(clientHolder, policy, LetterType.COPY, true, false));
			mailingAddresses.add(generateMailingAddress(policyDTO.getMailToAgent(), policy, policyDTO, LetterType.COPY));
			break;

		case MCC:
			mailingAddresses.add(generateMailingAddress(clientHolder, policy, LetterType.INFO, true, false));
			break;

		case MCCA:
			mailingAddresses.add(generateMailingAddress(clientHolder, policy, LetterType.INFO, true, false));
			mailingAddresses.add(generateMailingAddress(policyDTO.getMailToAgent(), policy, policyDTO, LetterType.COPY));
			break;

		case MA:
			mailingAddresses.add(generateMailingAddress(policyDTO.getMailToAgent(), policy, policyDTO, LetterType.INFO));
			break;
		case MAAC:
			mailingAddresses.add(generateMailingAddress(policyDTO.getMailToAgent(), policy, policyDTO, LetterType.INFO));
			mailingAddresses.add(generateMailingAddress(policyDTO.getMailToAgent(), policy, policyDTO, LetterType.COPY));

			break;
		case MH1CH2:
			mailingAddresses.add(generateMailingAddress(clientHolder2, policy, LetterType.COPY, false, true));
			break;

		default:
			log.error("SendingRuleType not yet implemented");
			return null;
		}
		return mailingAddresses;
	}

	private String computeTitleId(List<PolicyHolder> holderAsPersonLightList) {
		HashMap<String, Integer> genderCount = new HashMap<>();
		genderCount.put("MME", 0);
		genderCount.put("MR", 0);
		for (PolicyHolder personLight : holderAsPersonLightList) {
			String titleId = personLight.getTitleId();
			if (titleId != null) {
				Integer gender = genderCount.get(personLight.getTitleId());
				if (gender != null) {
					genderCount.put(personLight.getTitleId(), ++gender);
				}
			}
		}

		if (genderCount.get("MME") > 0 && genderCount.get("MR") == 0) {
			if (genderCount.get("MME") > 1) {
				return "MMES";
			}
			return "MME";
		}

		if (genderCount.get("MME") == 0 && genderCount.get("MR") > 0) {
			if (genderCount.get("MR") > 1) {
				return "MRS";
			}
			return "MR";
		}

		if (genderCount.get("MME") > 0 && genderCount.get("MR") > 0) {
			return "MME_MR";
		}

		return DEFAULT_TITLE_ID;
	}

	private MailingAddress generateMailingAddress(PolicyHolderDTO clientHolder, Policy policy, LetterType letterType, boolean useCorrespondancyAddress, boolean useProvidedHolder) {
		CorrespondenceAddress correspondenceAddress = generateCorrespondenceAddress(clientHolder, policy, useCorrespondancyAddress, useProvidedHolder);

		MailingAddress mailingAddress = new MailingAddress();
		mailingAddress.setCorrespondenceAddress(correspondenceAddress);
		mailingAddress.setDestinationType(useCorrespondancyAddress ? DestinationType.CORRESP : DestinationType.CLIENT);
		mailingAddress.setDeliveryChannel(DeliveryChannelType.PAPER);
		mailingAddress.setLetterType(letterType);
		mailingAddress.setLanguage(getLanguage(clientHolder, null, null));
		mailingAddress.setTitleId(computeTitleId(useProvidedHolder ? Arrays.asList(personMapper.asPolicyHolder(clientHolder)) : policy.getPolicyHolders()));
		mailingAddress.setPolicy(policy);
		return mailingAddress;
	}

	protected MailingAddress generateMailingAddress(AgentLightDTO agentLightDTO, Policy policy, PolicyDTO policyDTO, LetterType letterType) {
		AgentDTO mailToAgent = null;
		if (agentLightDTO != null && StringUtils.isNotBlank(agentLightDTO.getAgtId())) {
			mailToAgent = agentService.getAgent(agentLightDTO.getAgtId());
		}

		if (mailToAgent != null) {
			AgentContactLiteDTO agentContact = documentGenerationService.getAgentContact(getMailToAgentFund(mailToAgent, getFundTransactions(policy)), policyDTO, mailToAgent);
			CorrespondenceAddress correspondenceAddress = generateCorrespondenceAddress(agentContact, policyDTO);

			MailingAddress mailingAddress = new MailingAddress();
			mailingAddress.setCorrespondenceAddress(correspondenceAddress);
			mailingAddress.setDestinationType(DestinationType.AGENT);
			mailingAddress.setDeliveryChannel(DeliveryChannelType.PAPER);
			mailingAddress.setLetterType(letterType);
			mailingAddress.setPolicy(policy);

			mailingAddress.setLanguage(documentGenerationService.getLanguage(agentContact));

			if (agentContact != null) {
				PersonLight contactPersonLight = personMapper.asPersonLight(agentContact);
				String contactPersonLightTitleId = contactPersonLight.getTitleId();
				if (contactPersonLightTitleId != null && !contactPersonLightTitleId.isEmpty()) {
					mailingAddress.setTitleId(contactPersonLight.getTitleId());
				} else {
					mailingAddress.setTitleId(DEFAULT_TITLE_ID);
				}
			} else {
				mailingAddress.setTitleId(DEFAULT_TITLE_ID);
			}

			return mailingAddress;
		}
		return null;
	}

	protected Policy getXmlPolicy(PolicyDTO policyDTO, List<PolicyHolder> holders) {
		Policy policy = policyMapper.asPolicy(policyDTO);
		policy.setContractType(documentGenerationService.getContractType(policyDTO.getProduct()));
		policy.setDemembrement(false);
		if (!holders.isEmpty()) {
			policy.setPolicyHolders(holders);
			policy.setDemembrement(getDemembrement(holders));
		}
		return policy;
	}

	protected boolean getDemembrement(List<PolicyHolder> holders) {
		for (PolicyHolder holder : holders) {
			if (BooleanUtils.isTrue(holder.isBareOwner()) || BooleanUtils.isTrue(holder.isUsuFructuary())) {
				return true;
			}
		}
		return false;
	}

	protected EndOnInsuredDeathType getEndOnInsuredDeath(PolicyCoverageDTO firstPolicyCoverages) {
		EndOnInsuredDeathType endOnInsuredDeathEnum = null;
		if (firstPolicyCoverages != null) {
			OptionDetailDTO lives = firstPolicyCoverages.getLives();
			if (lives != null) {
				String endOnInsuredDeathString = documentGenerationService.getTransco(TranscoType.END_ON_INSURED_DEATH, Long.toString(lives.getNumber()));
				endOnInsuredDeathEnum = EndOnInsuredDeathType.valueOf(endOnInsuredDeathString);
			}
		}
		return endOnInsuredDeathEnum;
	}

	protected String escapePolicyId(String policyId) {
		return StringUtils.replaceChars(policyId, "/", "_");
	}

	protected Document getPolicyDocument(PolicyDTO policyDTO, Policy policy, Header header) {
		CoverLetter coverLetter = generateCoverLetter(policy, policyDTO.getBroker(), policy.getPolicyHolders());
		return documentGenerationService.generateDocument(header, coverLetter, generateMailingAddress(policyDTO));
	}

	/**
	 * @param workflowItemId
	 * @param policyDTO
	 * @param productCountry
	 * @param language
	 * @param policy
	 * @param userId
	 * @return
	 */
	protected Document createPolicyDocument(Long workflowItemId, PolicyDTO policyDTO, String productCountry, String language, Policy policy, String userId) {
		String transcodedProductCountry = documentGenerationService.getTransco(TranscoType.PAYS, productCountry);
		MetadataDTO cps1Metada = metadataService.getMetadata(workflowItemId.toString(), Metadata.FIRST_CPS_USER.getMetadata(), userId);
		String trigram = userService.getLogin(cps1Metada.getValue());
		User user = documentGenerationService.getUserFromTrigram(trigram);
		Header header = documentGenerationService.generateHeader(user, getDocumentType(), language, transcodedProductCountry, null);

		return getPolicyDocument(policyDTO, policy, header);
	}
}