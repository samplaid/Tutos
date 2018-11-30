package lu.wealins.webia.core.service.document;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.BrokerChangeDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.PolicyAgentShareDTO;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyEntryFeesDto;
import lu.wealins.common.dto.liability.services.PolicyHolderDTO;
import lu.wealins.common.dto.liability.services.ProductValueDTO;
import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.liability.services.enums.PolicyAgentShareStatus;
import lu.wealins.common.dto.liability.services.enums.SendingRuleType;
import lu.wealins.editing.common.webia.Account;
import lu.wealins.editing.common.webia.AmountType;
import lu.wealins.editing.common.webia.ChangeBrokerDetails;
import lu.wealins.editing.common.webia.ChangeBrokerDetails.NewBroker;
import lu.wealins.editing.common.webia.ChangeBrokerDetails.PreviousBroker;
import lu.wealins.editing.common.webia.CorrespondenceAddress;
import lu.wealins.editing.common.webia.CoverLetter;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.Endorsement;
import lu.wealins.editing.common.webia.Fee;
import lu.wealins.editing.common.webia.Header;
import lu.wealins.editing.common.webia.MailingAddress;
import lu.wealins.editing.common.webia.Policy;
import lu.wealins.editing.common.webia.PolicyHolder;
import lu.wealins.editing.common.webia.User;
import lu.wealins.webia.core.mapper.PolicyMapper;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.MetadataService;
import lu.wealins.webia.core.service.document.trait.PolicyBasedDocumentGenerationService;
import lu.wealins.webia.core.service.impl.PolicyDocumentService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.TranscoType;

@Service
public class PolicyBrokerChangeService extends PolicyDocumentService implements PolicyBasedDocumentGenerationService {


	private static final Logger logger = LoggerFactory.getLogger(PolicyBrokerChangeService.class);

	private static final String LIABILITY_BROKER_CHANGE = "liability/brokerChange/";

	private static final String LIABILITY_BROKER_PREVIOUS = "liability/policyagentshare/previousBroker/";

	private static final String LOAD = "load";

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private MetadataService metadataService;

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private LiabilityFundService liabilityFundService;

	@Autowired
	protected PolicyMapper ploicyMapper;

	@Override
	protected Document createSpecificDocument(EditingRequest editingRequest, PolicyDTO policyDTO, String productCountry, String language, String userId) {
		Endorsement endorsement = generateEndorsement(policyDTO, productCountry, editingRequest.getWorkflowItemId());
		Policy policy = endorsement.getPolicy();

		Document document = createPolicyDocument(editingRequest.getWorkflowItemId(), policyDTO, productCountry, language, policy, userId);
		document.setEndorsement(endorsement);
		return document;
	}

	@Override
	protected DocumentType getDocumentType() {
		return DocumentType.CHANGE_BROKER;
	}

	@Override
	protected String getXmlPath(EditingRequest editingRequest, String policyId) {
		return buildXmlPath(policyId, filePath);
	}

	private Endorsement generateEndorsement(PolicyDTO policyDTO, String productCountry, Long workflowItemId) {
		Endorsement endorsement = new Endorsement();

		MultivaluedMap<String, Object> queryParam = new MultivaluedHashMap<>();
		queryParam.add("workflowItemId", workflowItemId);

		BrokerChangeDTO brokerChange = restClientUtils.get(LIABILITY_BROKER_CHANGE, LOAD, queryParam, BrokerChangeDTO.class);

		ChangeBrokerDetails changeBrokerDetails = getChangeDetails(brokerChange, policyDTO, workflowItemId);
		List<PolicyHolder> holders = generateHolders(policyDTO.getPolicyHolders());
		Policy policy = getXmlPolicy(policyDTO, holders);

		endorsement.setChangeBrokerDetails(changeBrokerDetails);
		endorsement.setPolicy(policy);
		endorsement.setEffectDate(brokerChange.getChangeDate());

		return endorsement;
	}

	private ChangeBrokerDetails getChangeDetails(BrokerChangeDTO brokerChange, PolicyDTO policyDTO, Long workflowItemId) {

		String policyId = policyDTO.getPolId();
		PolicyCoverageDTO firstCoverage = policyDTO.getFirstPolicyCoverages();
		String productLineId = firstCoverage.getProductLine();
		Integer coverage = firstCoverage.getCoverage();

		ProductValueDTO contractManagementFees = liabilityProductValueService.getContractManagementFees(policyId, productLineId, coverage);

		ChangeBrokerDetails details = new ChangeBrokerDetails();

		details.setNewBroker(getNewBroker(brokerChange, policyDTO));
		details.setPreviousBroker(getPreviousBroker(brokerChange, policyDTO, workflowItemId));
		details.setFunds(getChangeBrokerFunds(brokerChange));
		details.setAdminFees(getAdminFees(brokerChange, contractManagementFees));
		details.setEntryFees(getEntryFees(brokerChange, productLineId));

		details.setEndFeesDatePreviousBroker(getEndOfQuarter(brokerChange.getChangeDate()));

		return details;
	}

	private Date getEndOfQuarter(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate lastDayOfQuarter = localDate.withMonth(localDate.get(IsoFields.QUARTER_OF_YEAR) * 3).with(TemporalAdjusters.lastDayOfMonth());

		Date endQuarterDay = Date.from(lastDayOfQuarter.atStartOfDay(ZoneId.systemDefault()).toInstant());
		return endQuarterDay;
	}

	private NewBroker getNewBroker(BrokerChangeDTO brokerChange, PolicyDTO policyDTO) {

		NewBroker newBroker = new NewBroker();

		if (brokerChange.getBroker() != null) {

			MailingAddress address = generateMailingAddress(brokerChange.getBroker().getAgent(), policyMapper.asPolicy(policyDTO), policyDTO, null);
			newBroker.setBrokerName(brokerChange.getBroker().getAgent().getName());
			newBroker.setLanguage(address.getLanguage());
			newBroker.setCorrespondenceAddress(address.getCorrespondenceAddress());
			newBroker.setTitleId(address.getTitleId());

		}

		return newBroker;
	}

	private PreviousBroker getPreviousBroker(BrokerChangeDTO brokerChange, PolicyDTO policyDTO, Long workflowItemId) {

		PolicyAgentShareDTO previousBrokerDto = new PolicyAgentShareDTO();
		if (brokerChange.getBroker().getStatus() == PolicyAgentShareStatus.AMENDMENT.getStatus()) {
			previousBrokerDto = policyDTO.getBroker();
		} else {

			MultivaluedMap<String, Object> queryParam = new MultivaluedHashMap<>();
			queryParam.add("polId", brokerChange.getPolicyId());
			queryParam.add("effectiveDate", DATE_FORMAT.format(brokerChange.getChangeDate()));
			previousBrokerDto = restClientUtils.get(LIABILITY_BROKER_PREVIOUS, "", queryParam, PolicyAgentShareDTO.class);
		}

		PreviousBroker previousBroker = new PreviousBroker();

		if (previousBrokerDto != null) {
			MailingAddress address = generateMailingAddress(previousBrokerDto.getAgent(), policyMapper.asPolicy(policyDTO), policyDTO, null);

			previousBroker.setBrokerName(previousBrokerDto.getAgent().getName());
			previousBroker.setLanguage(address.getLanguage());
			previousBroker.setCorrespondenceAddress(address.getCorrespondenceAddress());
			previousBroker.setTitleId(address.getTitleId());
		}

		return previousBroker;
	}

	private CorrespondenceAddress getCorrespondanceAddress(PolicyDTO policyDTO, BrokerChangeDTO brokerChange) {

		SendingRuleType sendingRuleType = SendingRuleType.valueOf(brokerChange.getCategory().getKeyValue());

		AgentDTO mailToAgent = agentService.getAgent(brokerChange.getMailToAgent().getAgtId());

		AgentContactLiteDTO agentContact = documentGenerationService.getAgentContact(getMailToAgentFund(mailToAgent, getFundTransactions(policyMapper.asPolicy(policyDTO))), policyDTO, mailToAgent);
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

	public ChangeBrokerDetails.AdminFees getAdminFees(BrokerChangeDTO brokerChange, ProductValueDTO contractManagementFees) {
		Fee totalAdminFees = getTotalAdminFees(brokerChange, contractManagementFees);
		Fee intermediaryAdminFees = getIntermediairyAdminFees(brokerChange);
		ChangeBrokerDetails.AdminFees adminFees = null;

		if (totalAdminFees != null || intermediaryAdminFees != null) {
			adminFees = new ChangeBrokerDetails.AdminFees();
			adminFees.setIntermediaryFees(intermediaryAdminFees);
			adminFees.setTotalFees(totalAdminFees);
		}
		return adminFees;
	}

	private Fee getIntermediairyAdminFees(BrokerChangeDTO brokerChange) {
		Fee fee = null;
		BigDecimal value = null;
		BigDecimal rate = null;
		String currency = null;

		if (brokerChange.getPolicyValuation() != null) {
			currency = brokerChange.getPolicyValuation().getPolicyCurrency();
			// rate = brokerChange.getPolicyValuation().getHoldings().stream().findFirst().get().getFeeRate();
			rate = brokerChange.getPolicyValuation().getHoldings().stream().findFirst().get().getCommissionRate();
		}
		if (value != null || rate != null || currency != null) {
			fee = getFee(rate, value, currency);
		}
		return fee;
	}

	private Fee getTotalAdminFees(BrokerChangeDTO brokerChange, ProductValueDTO contractManagementFees) {

		Fee fee = null;
		BigDecimal value = null;
		BigDecimal rate = null;
		String currency = null;

		if (brokerChange.getPolicyValuation() != null) {
			brokerChange.getPolicyValuation().getPolicyCurrency();
		}

		rate = contractManagementFees.getNumericValue();
		rate = brokerChange.getPolicyValuation().getHoldings().stream().findFirst().get().getCommissionRate();

		if (value != null || rate != null || currency != null) {
			fee = getFee(rate, value, currency);
		}
		return fee;
	}

	public ChangeBrokerDetails.EntryFees getEntryFees(BrokerChangeDTO brokerChange, String productLineId) {

		PolicyEntryFeesDto entryFees = liabilityProductValueService.getLastCoverageFees(brokerChange.getPolicyId());
		Fee totalEntryFees = getTotalEntryFees(brokerChange, entryFees, productLineId);
		Fee intermediaryEntryFees = getIntermediairyEntryFees(brokerChange, entryFees);
		ChangeBrokerDetails.EntryFees entryFee = null;

		if (totalEntryFees != null || intermediaryEntryFees != null) {
			entryFee = new ChangeBrokerDetails.EntryFees();
			entryFee.setIntermediaryFees(intermediaryEntryFees);
			entryFee.setTotalFees(totalEntryFees);
		}
		return entryFee;
	}

	private Fee getIntermediairyEntryFees(BrokerChangeDTO brokerChange, PolicyEntryFeesDto entryFees) {

		boolean isPercentage = BooleanUtils.isTrue(entryFees.getIsPercentage());
		Fee fee = null;
		BigDecimal value = null;
		BigDecimal rate = null;
		String currency = null;

		if (brokerChange.getPolicyValuation() != null) {
			currency = brokerChange.getPolicyValuation().getPolicyCurrency();
		}

		if (isPercentage) {
			rate = entryFees.getBrokerEntryFees();
		} else {
			value = entryFees.getBrokerEntryFees();
		}

		if ((value != null || rate != null) && currency != null) {
			fee = getFee(rate, value, currency);
		}
		return fee;
	}

	private Fee getTotalEntryFees(BrokerChangeDTO brokerChange, PolicyEntryFeesDto entryFees, String productLineId) {

		boolean isPercentage = BooleanUtils.isTrue(entryFees.getIsPercentage());
		Fee fee = null;
		BigDecimal value = null;
		BigDecimal rate = null;
		String currency = null;

		if (brokerChange.getPolicyValuation() != null) {
			currency = brokerChange.getPolicyValuation().getPolicyCurrency();
		}

		if (isPercentage) {
			rate = entryFees.getEntryFees();
		} else {
			value = entryFees.getEntryFees();
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

	@Override
	protected Document createPolicyDocument(Long workflowItemId, PolicyDTO policyDTO, String productCountry, String language, Policy policy, String userId) {
		String transcodedProductCountry = documentGenerationService.getTransco(TranscoType.PAYS, productCountry);
		MetadataDTO cps1Metada = metadataService.getMetadata(workflowItemId.toString(), Metadata.FIRST_CPS_USER.getMetadata(), userId);
		User user = documentGenerationService.getUserFromTrigram(cps1Metada.getValue());
		Header header = documentGenerationService.generateHeader(user, getDocumentType(), language, transcodedProductCountry, null);

		CoverLetter coverLetter = generateCoverLetter(policy, policyDTO.getBroker(), policy.getPolicyHolders());

		MultivaluedMap<String, Object> queryParam = new MultivaluedHashMap<>();
		queryParam.add("workflowItemId", workflowItemId);

		BrokerChangeDTO brokerChange = restClientUtils.get(LIABILITY_BROKER_CHANGE, LOAD, queryParam, BrokerChangeDTO.class);

		SendingRuleType sendingRuleType = SendingRuleType.valueOf(brokerChange.getCategory().getKeyValue());

		policyDTO.setMailToAgent(brokerChange.getMailToAgent());

		return documentGenerationService.generateDocument(header, coverLetter, generateMailingAddress(policyDTO, sendingRuleType));
	}

	public List<ChangeBrokerDetails.Fund> getChangeBrokerFunds(BrokerChangeDTO brokerChange) {

		if (brokerChange == null || brokerChange.getPolicyValuation().getHoldings() == null) {
			return null;
		}

		Collection<String> fundIds = brokerChange.getPolicyValuation().getHoldings().stream()
				.map(fund -> fund.getFundId())
				.collect(Collectors.toList());

		Collection<FundLiteDTO> fundList = liabilityFundService.getFunds(fundIds);

		List<ChangeBrokerDetails.Fund> brokerFundList = new ArrayList<ChangeBrokerDetails.Fund>();

		for (FundLiteDTO fundLite : fundList) {

			FundDTO fundDto = liabilityFundService.getFund(fundLite.getFdsId());
			ChangeBrokerDetails.Fund brokerFund = new ChangeBrokerDetails.Fund();
			String assetManager = fundLite.getAssetManager();
			String psi = fundLite.getFinancialAdvisor();
			String accountRoot = fundLite.getAccountRoot();
			brokerFund.setFundId(StringUtils.stripToNull(accountRoot));
			String contactPerson = getPersonName(fundLite.getSalesRep());
			brokerFund.setContactPerson(StringUtils.stripToNull(contactPerson));
			Account account = new Account();
			account.setIban(fundLite.getIban());
			account.setBankName(fundDto.getDepositBankAgent().getName());
			account.setCurrency(fundLite.getCurrency());
			account.setAccountNumber(fundLite.getAccountRoot());
			brokerFund.setCustodianAccount(account);

			if (isFAS(fundLite)) {
				brokerFund.setPsi(StringUtils.stripToNull(getPersonName(psi)));
				brokerFund.setAssetManager(null);
				brokerFund.setFundSubType(fundLite.getFundSubType());
			} else if (isFID(fundLite)) {
				brokerFund.setPsi(null);
				brokerFund.setAssetManager(StringUtils.stripToNull(getPersonName(assetManager)));
				brokerFund.setFundSubType(fundLite.getFundSubType());
			} else {
				brokerFund.setPsi(null);
				brokerFund.setAssetManager(null);
			}

			brokerFund.setFinancialManagementFees(getFinancialMngtFees(fundDto));
			brokerFundList.add(brokerFund);
		}

		return brokerFundList;
	}

	private boolean isFAS(FundLiteDTO fundDTO) {
		return FundSubType.FAS.name().equals(fundDTO.getFundSubType());
	}

	private boolean isFID(FundLiteDTO fundDTO) {
		return FundSubType.FID.name().equals(fundDTO.getFundSubType());
	}

	private String getPersonName(String personId) {
		String personName = personId;
		if (personId != null && !personId.trim().isEmpty()) {
			AgentDTO agent = agentService.getAgent(personId);
			if (agent != null) {
				personName = String.join(StringUtils.SPACE, agent.getName(),
						agent.getFirstname() != null ? agent.getFirstname() : StringUtils.EMPTY);
			}
		}
		return personName;
	}

	private ChangeBrokerDetails.Fund.FinancialManagementFees getFinancialMngtFees(FundDTO fundDTO) {
		ChangeBrokerDetails.Fund.FinancialManagementFees finFees = new ChangeBrokerDetails.Fund.FinancialManagementFees();
		if (isFAS(fundDTO)) {
			finFees.setPsiFees(getPsiFees(fundDTO));
		} else if (isFID(fundDTO)) {
			if (fundDTO != null && Boolean.TRUE.equals(fundDTO.getExAllInFees())) {
				finFees.setAllInFees(getAssetManagerFees(fundDTO));
			} else if (fundDTO != null && !Boolean.TRUE.equals(fundDTO.getExAllInFees())) {
				finFees.setAssetManagementFees(getAssetManagerFees(fundDTO));
			}
		}

		finFees.setCustodianFees(getCustodianFees(fundDTO));
		finFees.setInsurerFees(getInsurrerFees(fundDTO));
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

	private Fee getPsiFees(FundDTO fundDTO) {
		Fee fee = null;
		BigDecimal value = null;
		BigDecimal rate = null;
		String currency = null;
		if (isPsi(fundDTO.getFinancialAdvisorAgent()) && fundDTO.getFinAdvisorFee() != null) {
			rate = fundDTO.getFinAdvisorFee();
			currency = fundDTO.getCurrency();
		}

		if (value != null || rate != null || currency != null) {
			fee = getFee(rate, value, currency);
		}

		return fee;
	}

	private boolean isPsi(AgentDTO agentDTO) {
		return agentDTO != null && AgentCategory.PRESTATION_SERVICE_INVEST.getCategory().equals(agentDTO.getCategory());
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
}
