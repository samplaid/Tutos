package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.liability.services.ClientContactDetailDTO;
import lu.wealins.common.dto.liability.services.CountryDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderDTO;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.webia.services.EditingRequestStatus;
import lu.wealins.editing.common.webia.Account;
import lu.wealins.editing.common.webia.AmountType;
import lu.wealins.editing.common.webia.CorrespondenceAddress;
import lu.wealins.editing.common.webia.CoverLetter;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.Documents;
import lu.wealins.editing.common.webia.Fee;
import lu.wealins.editing.common.webia.Fund;
import lu.wealins.editing.common.webia.Header;
import lu.wealins.editing.common.webia.MandatGestion;
import lu.wealins.editing.common.webia.MandatGestion.Fees;
import lu.wealins.editing.common.webia.MandatGestion.Fees.AllInFees;
import lu.wealins.editing.common.webia.MandatGestion.Fees.AssetManagementFees;
import lu.wealins.editing.common.webia.MandatGestion.Fees.CustodianFees;
import lu.wealins.editing.common.webia.PersonLight;
import lu.wealins.editing.common.webia.Policy;
import lu.wealins.editing.common.webia.RiskProfile;
import lu.wealins.webia.core.mapper.FundMapper;
import lu.wealins.webia.core.mapper.PersonMapper;
import lu.wealins.webia.core.mapper.PolicyMapper;
import lu.wealins.webia.core.service.DocumentService;
import lu.wealins.webia.core.service.LiabilityAgentService;
import lu.wealins.webia.core.service.LiabilityCountryService;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.MandatGestionDocumentGenerationService;
import lu.wealins.webia.core.service.MetadataService;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.EditingUser;

@Service
@Transactional(readOnly = true)
public class MandatGestionDocumentGenerationServiceImpl implements MandatGestionDocumentGenerationService {

	/**
	 * The logger
	 */
	private static final Logger log = LoggerFactory.getLogger(MandatGestionDocumentGenerationServiceImpl.class);

	/**
	 * Services
	 */
	@Autowired
	private LiabilityPolicyService policyService;

	@Autowired
	private LiabilityFundService fundService;

	@Autowired
	private LiabilityAgentService agentService;

	@Autowired
	private DocumentService documentGenerationService;

	@Autowired
	private LiabilityCountryService countryService;

	@Autowired
	private WebiaApplicationParameterService webiaApplicationParameterService;
	
	@Autowired
	private WebiaWorkflowUserService workflowUserService;
	
	@Autowired
	private MetadataService metadataService;

	@Autowired
	private WebiaWorkflowUserService userService;

	/** Application parameter code for the Value After Tax of Asset Manager. */
	private static final String VAT_AM = "VAT_AM";

	/** Application parameter code for the Value After Tax of Depositary Bank. */
	private static final String VAT_DB = "VAT_DB";

	/**
	 * Mappers
	 */
	@Autowired
	private PolicyMapper policyMapper;

	@Autowired
	private FundMapper fundMapper;

	@Autowired
	private PersonMapper personMapper;

	/**
	 * Constants
	 */
	private static final String DEFAULT_TITLE_ID = "MME_MR";
	private static final String GBR = "GBR";
	private static final String BEL = "BEL";

	/**
	 * Env. variable
	 */
	@Value("${editique.xml.path}")
	private String filePath;

	@Override
	public EditingRequest generateDocumentMandatGestion(SecurityContext context, EditingRequest editingRequest) {

		try {

			Assert.notNull(editingRequest, "The edition request can not be null");
			editingRequest.setStatus(EditingRequestStatus.IN_PROGRESS);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);

			String policyId = editingRequest.getPolicy();
			String fundId = editingRequest.getFund();
			EditingUser creationUser = editingRequest.getCreationUser();

			Assert.notNull(policyId, "The policy ID can not be null");
			Assert.notNull(fundId, "The fund ID can not be null");
			Assert.notNull(creationUser, "The creation user of the document generation request can not be null");

			PolicyDTO policyDTO = policyService.getPolicy(policyId);
			Assert.notNull(policyDTO, "No policy found with ID:" + policyId);
			Policy policy = generatePolicy(policyDTO);

			FundDTO fund = fundService.getFund(fundId);

			// Test if the fund is FAS
			// If yes, and the product country is not FRANCE => No management mandate will be generated
			// MCN : 02/03/2018 No need to do this check.
			// http://sv-1354wvp26.win.iwi.local:8080/browse/KAN-191
			//
			// if (isFAS(fund)) {
			// boolean isFrenchProduct = "FRA".equals(policyDTO.getProduct().getNlCountry());
			// Assert.isTrue(isFrenchProduct, "No management mandate generated for FAS fund of non-french product");
			// }
			Assert.notNull(fund, "No fund found with ID:" + fundId);

			Documents documents = new Documents();
			List<Document> docs = new ArrayList<>();
			log.info("Generating management mandat for fund : " + fund.getDisplayName() + ", Policy : " + policyId);

			/**
			 * Generate the document
			 */

			Collection<PolicyHolderDTO> holders = policyDTO.getPolicyHolders();
			PolicyHolderDTO firstHolder = null;
			if (holders != null && !holders.isEmpty()) {
				firstHolder = holders.iterator().next();
			}

			AgentContactLiteDTO agentContact = getAgentContact(fund, policyDTO);
			String language = documentGenerationService.getLanguage(agentContact);

			Header header = documentGenerationService.generateHeader(creationUser, DocumentType.MANAGEMENT_MANDATE, language, null, null);
			MandatGestion mandatGestion = generateMandatGestion(policy, fund, firstHolder);
			CoverLetter coverLetter = generateCoverLetter(policy, agentContact);

			Document document = documentGenerationService.generateDocument(header, coverLetter, null);
			document.setMandatGestion(mandatGestion);
			docs.add(document);
			documents.setDocuments(docs);

			editingRequest = saveXML(policyId, fundId, documents, editingRequest);

		} catch (Exception e) {
			editingRequest.setStatus(EditingRequestStatus.IN_ERROR);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);
			log.error("Error during generating the management mandate for Policy : " + editingRequest.getPolicy()
					+ " and fund : " + editingRequest.getFund(), e);
		}

		return editingRequest;
	}
	
	@Override
	public EditingRequest generateDocumentMandatGestionEnd(SecurityContext context, EditingRequest editingRequest) {

		try {

			Assert.notNull(editingRequest, "The edition request can not be null");
			editingRequest.setStatus(EditingRequestStatus.IN_PROGRESS);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);

			Long workflowItemId = editingRequest.getWorkflowItemId();
			String policyId = editingRequest.getPolicy();
			String fundId = editingRequest.getFund();
			EditingUser creationUser = editingRequest.getCreationUser();

			Assert.notNull(workflowItemId, "The workflow Item ID can not be null");
			Assert.notNull(policyId, "The policy ID can not be null");
			Assert.notNull(fundId, "The fund ID can not be null");
			Assert.notNull(creationUser, "The creation user of the document generation request can not be null");

			PolicyDTO policyDTO = policyService.getPolicy(policyId);
			Assert.notNull(policyDTO, "No policy found with ID:" + policyId);
			Policy policy = generatePolicy(policyDTO);

			FundDTO fund = fundService.getFund(fundId);

			Assert.notNull(fund, "No fund found with ID:" + fundId);

			Documents documents = new Documents();
			List<Document> docs = new ArrayList<>();
			log.info("Generating end of management mandat for fund : " + fund.getDisplayName() + ", Policy : " + policyId);

			AgentContactLiteDTO agentContact = getAgentContact(fund, policyDTO);
			String language = documentGenerationService.getLanguage(agentContact);

			String cps1Mail = getCps1MailFromWorkflow(workflowItemId, workflowUserService.getUserId(context));
			Header header = documentGenerationService.generateHeader(cps1Mail, DocumentType.MANAGEMENT_MANDATE_END, language, null, null);
			
			MandatGestion mandatGestion = generateMandatGestionEnd(policy, fund);
			CoverLetter coverLetter = generateCoverLetter(policy, agentContact);

			Document document = documentGenerationService.generateDocument(header, coverLetter, null);
			document.setMandatGestion(mandatGestion);
			docs.add(document);
			documents.setDocuments(docs);

			editingRequest = saveXML(policyId, fundId, documents, editingRequest);

		} catch (Exception e) {
			editingRequest.setStatus(EditingRequestStatus.IN_ERROR);
			editingRequest = documentGenerationService.updateEditingRequest(editingRequest);
			log.error("Error during generating the fax notifying the end of management mandate for Policy : " + editingRequest.getPolicy()
					+ " and fund : " + editingRequest.getFund(), e);
		}

		return editingRequest;
	}	
	
	private EditingRequest saveXML(String policyId, String fundId, Documents documents, EditingRequest editingRequest){
		/**
		 * Save as XML
		 */
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		String formatedDate = date.format(formatter);
		String path = filePath + "/" + policyId + "_" + fundId + "_" + formatedDate + ".xml";
		String saveResponse = documentGenerationService.saveXmlDocuments(documents, path);

		/**
		 * Update the request
		 */
		editingRequest.setInputStreamPath(saveResponse);
		editingRequest.setStatus(EditingRequestStatus.XML_GENERATED);
		editingRequest = documentGenerationService.updateEditingRequest(editingRequest);
		return editingRequest;
	}
	
	private String getCps1MailFromWorkflow(Long workflowItemId, String userId) {
		MetadataDTO cps1Metada = metadataService.getMetadata(workflowItemId.toString(), Metadata.FIRST_CPS_USER.getMetadata(), userId);
		String trigram = userService.getLogin(cps1Metada.getValue());
		return documentGenerationService.getMailFromTrigram(trigram);
	}

	private Policy generatePolicy(PolicyDTO policyDTO) {
		Policy policy = policyMapper.asPolicy(policyDTO);

		boolean belgianResident = false;

		Collection<PolicyHolderDTO> holders = policyDTO.getPolicyHolders();
		for (PolicyHolderDTO holderClient : holders) {
			if (holderClient.getHomeAddress() != null && BEL.equals(holderClient.getHomeAddress().getCountry())) {
				belgianResident = true;
				break;
			}
		}

		boolean ukResidentAtSubscription = policyDTO.getIssueCountryOfResidence() != null && GBR.equals(policyDTO.getIssueCountryOfResidence());

		policy.setResidentBE(belgianResident);
		policy.setResidentUKatSubscription(ukResidentAtSubscription);
		policy.setContractType(documentGenerationService.getContractType(policyDTO.getProduct()));
		return policy;
	}

	private MandatGestion generateMandatGestion(Policy policy, FundDTO fundDTO, PolicyHolderDTO firstHolder) {

		MandatGestion mandatGestion = new MandatGestion();
		mandatGestion.setPolicy(policy);

		if (isFAS(fundDTO)) {
			String financialAdvisor = fundDTO.getFinancialAdvisor();
			if (StringUtils.isNotBlank(financialAdvisor)) {
				mandatGestion.setAssetManager(agentService.getAgent(financialAdvisor).getName());
			}
		} else {
			String assetManager = fundDTO.getAssetManager();
			if (StringUtils.isNotBlank(assetManager)) {
				mandatGestion.setAssetManager(agentService.getAgent(assetManager).getName());
			}
		}

		String salesRep = fundDTO.getSalesRep();
		if (StringUtils.isNotBlank(salesRep)) {
			AgentDTO contactPerson = agentService.getAgent(salesRep);
			if (contactPerson != null) {
				String firstname = contactPerson.getFirstname() != null && !contactPerson.getFirstname().isEmpty() ? contactPerson.getFirstname() + " " : "";
				String name = contactPerson.getName();
				mandatGestion.setContactPerson(firstname + name);
			}
		}

		mandatGestion.setCustodianAccount(generateCustodianAccount(fundDTO));
		mandatGestion.setFees(generateFees(fundDTO));

		Fund fund = fundMapper.asFund(fundDTO);
		fund.setRiskProfile(generateRiskProfil(fundDTO));
		fund.setSpecificRiskActive(documentGenerationService.isSpecificRiskActive(fundDTO.getAlternativeFunds()));

		mandatGestion.setFund(fund);

		mandatGestion.setResidenceCountry(generateResidenceCountry(firstHolder));

		return mandatGestion;
	}

	private MandatGestion generateMandatGestionEnd(Policy policy, FundDTO fundDTO) {

		MandatGestion mandatGestion = new MandatGestion();
		mandatGestion.setPolicy(policy);

		if (isFAS(fundDTO)) {
			String financialAdvisor = fundDTO.getFinancialAdvisor();
			if (StringUtils.isNotBlank(financialAdvisor)) {
				mandatGestion.setAssetManager(agentService.getAgent(financialAdvisor).getName());
			}
		} else {
			String assetManager = fundDTO.getAssetManager();
			if (StringUtils.isNotBlank(assetManager)) {
				mandatGestion.setAssetManager(agentService.getAgent(assetManager).getName());
			}
		}

		String salesRep = fundDTO.getSalesRep();
		if (StringUtils.isNotBlank(salesRep)) {
			AgentDTO contactPerson = agentService.getAgent(salesRep);
			if (contactPerson != null) {
				String firstname = contactPerson.getFirstname() != null && !contactPerson.getFirstname().isEmpty() ? contactPerson.getFirstname() + " " : "";
				String name = contactPerson.getName();
				mandatGestion.setContactPerson(firstname + name);
			}
		}

		mandatGestion.setCustodianAccount(generateCustodianAccount(fundDTO));
		Fund fund = fundMapper.asFund(fundDTO);
		mandatGestion.setFund(fund);

		return mandatGestion;
	}
	
	
	private String generateResidenceCountry(PolicyHolderDTO firstHolder) {
		if (firstHolder != null) {
			ClientContactDetailDTO firstHolderAddress = firstHolder.getHomeAddress();
			if (firstHolderAddress != null) {
				CountryDTO country = countryService.getCountry(firstHolderAddress.getCountry());
				if (country != null) {
					return country.getIsoCode2Pos();
				}
			}
		}
		return null;
	}

	private RiskProfile generateRiskProfil(FundDTO fund) {

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

	private Account generateCustodianAccount(FundDTO f) {
		Account account = new Account();
		account.setAccountNumber(f.getAccountRoot());

		String depositBank = f.getDepositBank();
		if (StringUtils.isNotBlank(depositBank)) {
			account.setBankName(agentService.getAgent(depositBank).getName());
		}

		account.setCurrency(f.getCurrency());
		account.setIban(f.getIban());
		return account;
	}

	private CoverLetter generateCoverLetter(Policy policy, AgentContactLiteDTO agentContact) {
		CoverLetter coverLetter = new CoverLetter();

		AgentDTO agentDTO = agentService.getAgent(agentContact.getAgentId());
		AgentLightDTO contactAgent = agentContact.getContact();

		CorrespondenceAddress correspondenceAddress = personMapper.asCorrespondenceAddress(contactAgent);

		PersonLight contactPerson = personMapper.asPersonLight(contactAgent);
		
		if (!contactAgent.getAgtId().equals(agentDTO.getAgtId())) {
			correspondenceAddress.setPersons(Arrays.asList(contactPerson));
		}

		if (contactPerson != null) {
			coverLetter.setTitleId(contactPerson.getTitleId());
		}

		correspondenceAddress.setName(agentDTO.getName());
		coverLetter.setCorrespondenceAddress(correspondenceAddress);

		coverLetter.setPolicy(policy);

		return coverLetter;

	}

	private Fees generateFees(FundDTO fund) {
		Fees fees = new Fees();

		if (fund == null) {
			return null;
		}

		BigDecimal finFeesFlatAmount = fund.getFinFeesFlatAmount();
		BigDecimal assetManagerFee = fund.getAssetManagerFee();
		BigDecimal bankDepositFee = fund.getBankDepositFee();
		BigDecimal bankDepositAmout = fund.getDepositBankFlatFee();

		String performanceFee = fund.getPerformanceFee();

		if (finFeesFlatAmount == null && performanceFee == null && assetManagerFee == null) {
			return null;
		}

		// Performance Fees
		fees.setPerformanceFees(performanceFee);

		if (BooleanUtils.isTrue(fund.getExAllInFees())) {
			AllInFees allInFees = new AllInFees();
			BigDecimal allInFeesTaxRate = new BigDecimal(webiaApplicationParameterService.getApplicationParameter(VAT_AM).getValue());
			allInFees.setTaxRate(allInFeesTaxRate);

			Fee totalFees = new Fee();
			// if amount is provided, populate the amount
			if (finFeesFlatAmount != null && finFeesFlatAmount.compareTo(BigDecimal.ZERO) > 0) {
				AmountType allInFeesAmountType = new AmountType();
				allInFeesAmountType.setCurrencyCode(fund.getAssetManFeeCcy());
				allInFeesAmountType.setValue(finFeesFlatAmount);
				totalFees.setAmount(allInFeesAmountType);
			}
			// if rate is provided, populate the rate
			if (assetManagerFee != null) {
				totalFees.setRate(assetManagerFee);
			}

			allInFees.setTotalFees(totalFees);
			allInFees.setNettoFees(calculateNettoValues(allInFees.getTotalFees(), allInFeesTaxRate));
			allInFees.setNotes(fund.getExAssManNotes());
			fees.setAllInFees(allInFees);

		} else {
			if (finFeesFlatAmount != null || assetManagerFee != null) {

				// AssetManagementFees
				AssetManagementFees assetManagementFees = new AssetManagementFees();
				BigDecimal assetManagmentFeesTaxRate = new BigDecimal(webiaApplicationParameterService.getApplicationParameter(VAT_AM).getValue());
				assetManagementFees.setTaxRate(assetManagmentFeesTaxRate);

				Fee totalFees = new Fee();
				// if amount is provided, populate the amount
				if (finFeesFlatAmount != null && finFeesFlatAmount.compareTo(BigDecimal.ZERO) > 0) {
					AmountType assetManagementFeeAmountType = new AmountType();
					assetManagementFeeAmountType.setCurrencyCode(fund.getAssetManFeeCcy());
					assetManagementFeeAmountType.setValue(finFeesFlatAmount);
					totalFees.setAmount(assetManagementFeeAmountType);
				}
				// if rate is provided, populate the rate
				if (assetManagerFee != null) {
					totalFees.setRate(assetManagerFee);
				}
				assetManagementFees.setTotalFees(totalFees);
				assetManagementFees.setNettoFees(calculateNettoValues(assetManagementFees.getTotalFees(), assetManagmentFeesTaxRate));
				assetManagementFees.setNotes(fund.getExAssManNotes());
				fees.setAssetManagementFees(assetManagementFees);

			}

			if (bankDepositFee != null) {

				// Custodian Fees
				CustodianFees custodianFees = new CustodianFees();
				BigDecimal custodianFeesTaxRate = new BigDecimal(webiaApplicationParameterService.getApplicationParameter(VAT_DB).getValue());
				custodianFees.setTaxRate(custodianFeesTaxRate);

				Fee totalFees = new Fee();
				// if amount is provided, populate the amount
				if (bankDepositAmout != null && bankDepositAmout.compareTo(BigDecimal.ZERO) > 0) {
					AmountType CustodianFeeAmountType = new AmountType();
					CustodianFeeAmountType.setCurrencyCode(fund.getBankDepFeeCcy());
					CustodianFeeAmountType.setValue(bankDepositAmout);
					totalFees.setAmount(CustodianFeeAmountType);
				}
				// if rate is provided, populate the rate
				if (assetManagerFee != null) {
					totalFees.setRate(bankDepositFee);
				}
				custodianFees.setTotalFees(totalFees);
				custodianFees.setNettoFees(calculateNettoValues(custodianFees.getTotalFees(), custodianFeesTaxRate));
				custodianFees.setNotes(fund.getExDepBankNotes());
				fees.setCustodianFees(custodianFees);
			}
		}

		// Supra bonds fees are only for FAS funds
		if (isFAS(fund) && fund.getFinAdvisorFee() != null) {
			Fee supraBondsFee = new Fee();
			supraBondsFee.setRate(fund.getFinAdvisorFee());
			fees.setSupraBondsFees(supraBondsFee);
		}

		return fees;
	}

	private Fee calculateNettoValues(Fee totalFees, BigDecimal vatRate) {
		Assert.notNull(totalFees);
		Fee nettoFee = new Fee();
		// calculate netto rate
		if (totalFees.getRate() != null) {
			BigDecimal nettoRate = totalFees.getRate().divide(BigDecimal.ONE.add(vatRate.divide(new BigDecimal(100), 3, RoundingMode.HALF_UP)), 3, RoundingMode.HALF_UP);
			nettoFee.setRate(nettoRate);
		}
		// calculate netto amount
		if (totalFees.getAmount() != null) {
			BigDecimal nettoAmountValue = totalFees.getAmount().getValue().divide(BigDecimal.ONE.add(vatRate.divide(new BigDecimal(100), 3, RoundingMode.HALF_UP)), 3, RoundingMode.HALF_UP);
			AmountType nettoAmountType = new AmountType();
			nettoAmountType.setCurrencyCode(totalFees.getAmount().getCurrencyCode());
			nettoAmountType.setValue(nettoAmountValue);
			nettoFee.setAmount(nettoAmountType);
		}

		return nettoFee;
	}

	private boolean isFAS(FundDTO fundDTO) {
		return FundSubType.FAS.name().equals(fundDTO.getFundSubType());
	}

	private AgentContactLiteDTO getAgentContact(FundDTO fund, PolicyDTO policyDTO) {

		String agent;
		if (isFAS(fund)) {
			agent = fund.getFinancialAdvisor();
		} else {
			agent = fund.getAssetManager();
		}

		if (agent != null && StringUtils.isNotBlank(agent)) {
			AgentDTO agentDTO = agentService.getAgent(agent);
			return documentGenerationService.getAgentContact(fund, policyDTO, agentDTO);
		}
		return null;
	}

}
