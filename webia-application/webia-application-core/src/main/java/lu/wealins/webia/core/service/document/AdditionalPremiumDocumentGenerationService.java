package lu.wealins.webia.core.service.document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.AccountTransactionDTO;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.DeathCoverageClauseDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundTransactionDTO;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyPremiumDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.ProductValueDTO;
import lu.wealins.common.dto.liability.services.TransactionDTO;
import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.liability.services.enums.EventType;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.editing.common.webia.AdditionalPremiumDetails;
import lu.wealins.editing.common.webia.AdditionalPremiumDetails.AdditionalPremium;
import lu.wealins.editing.common.webia.AmountType;
import lu.wealins.editing.common.webia.AssetManager;
import lu.wealins.editing.common.webia.DeathCoverageDurationType;
import lu.wealins.editing.common.webia.DedicatedFundsRepartition;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.Endorsement;
import lu.wealins.editing.common.webia.Fee;
import lu.wealins.editing.common.webia.Fund;
import lu.wealins.editing.common.webia.Policy;
import lu.wealins.editing.common.webia.PolicyHolder;
import lu.wealins.editing.common.webia.SpecializedInsuranceFundsRepartition;
import lu.wealins.webia.core.mapper.FundMapper;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.LiabilityFundTransactionService;
import lu.wealins.webia.core.service.LiabilityTransactionService;
import lu.wealins.webia.core.service.document.trait.PolicyBasedDocumentGenerationService;
import lu.wealins.webia.core.service.impl.PolicyDocumentService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Service
public class AdditionalPremiumDocumentGenerationService extends PolicyDocumentService implements PolicyBasedDocumentGenerationService {

	private static final Logger logger = LoggerFactory.getLogger(AdditionalPremiumDocumentGenerationService.class);
	private static final String ACCOUNT_TYPE_PREMTAX = "PREMTAX";
	private static final String WEBIA_FORM = "webia/appForm/workFlowItemId";
	private static final String LIABILITY_COVERAGE = "liability/coverage/";

	@Autowired
	private FundMapper fundMapper;

	@Autowired
	private LiabilityFundService fundService;

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private LiabilityTransactionService transactionService;

	@Autowired
	private LiabilityFundTransactionService fundTransactionService;

	@Override
	protected Document createSpecificDocument(EditingRequest editingRequest, PolicyDTO policyDTO, String productCountry, String language, String userId) {
		Endorsement endorsement = generateEndorsement(policyDTO, productCountry, editingRequest.getWorkflowItemId());
		Policy policy = endorsement.getPolicy();

		Document document = createPolicyDocument(editingRequest.getWorkflowItemId(), policyDTO, productCountry, language, policy, userId);
		document.setEndorsement(endorsement);
		return document;
	}

	private Endorsement generateEndorsement(PolicyDTO policyDTO, String productCountry, Long workflowItemId) {
		Endorsement endorsement = new Endorsement();

			Integer coverage = getFormData(workflowItemId.intValue()).getCoverage();

			TransactionDTO transaction = transactionService.getTransactionByPolicyAndCoverage(policyDTO.getPolId(), coverage, Arrays.asList(EventType.PREMIUM_PAYMENT.getEvtId()));

			List<PolicyHolder> holders = generateHolders(policyDTO.getPolicyHolders());

			Policy policy = getXmlPolicy(policyDTO, holders);
			endorsement.setPolicy(policy);

			// Investment

			Collection<FundTransactionDTO> fundTransactions = fundTransactionService.filterNonPremiumFundTransactions(transaction);
			endorsement.setInvestments(generateInvestments(fundTransactions, policy));

			// Situation

			endorsement.setSituations(getPolicyValuation(policy, transaction.getEffectiveDate()));
			
		PolicyValuationDTO valuation = getPolicyValuation(policy.getPolicyId(), sdf.format(transaction.getEffectiveDate()));
			// endorsement.setSituations(generateSituation(policyFundTransactions, policy, transaction.getEffectiveDate()));

			// EffectDate
			endorsement.setEffectDate(transaction.getEffectiveDate());
			endorsement.setEventId(transaction.getTrnId().toString());
			endorsement.setEventType(DocumentType.ADD_PREMIUM.name());

			// AdditionalPremiumDetails
		AdditionalPremiumDetails details = generateAdditionalPremiumDetails(transaction, policyDTO, valuation);

			endorsement.setAdditionalPremiumDetails(details);

		return endorsement;
	}

	private PolicyCoverageDTO getCoverageByPolicyAndCoverage(String policy, Integer coverage) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.add("policyId", policy);
		queryParams.add("coverage", coverage);
		return restClientUtils.get(LIABILITY_COVERAGE, "", queryParams, PolicyCoverageDTO.class);
	}

	private AppFormDTO getFormData(Integer workflowItemId) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.add("workFlowItemId", workflowItemId);
		return restClientUtils.get(WEBIA_FORM, "", queryParams, AppFormDTO.class);
	}

	@Override
	protected DocumentType getDocumentType() {
		return DocumentType.ADD_PREMIUM;
	}

	@Override
	protected String getXmlPath(EditingRequest editingRequest, String policyId) {
		return buildXmlPath(policyId, filePath);
	}

	private AdditionalPremium getPremium(TransactionDTO transaction, PolicyDTO policyDTO) {

		AdditionalPremium premium = new AdditionalPremium();

		Optional<PolicyPremiumDTO> premiumOpt = policyDTO.getPolicyPremiums().stream().filter(x -> x.getCoverage() == transaction.getCoverage()).findFirst();
		if (premiumOpt.isPresent()) {
			premium.setAmount(getAmount(premiumOpt.get()));

			Optional<AccountTransactionDTO> taxValue = getTax(transaction);

			if (taxValue.isPresent()) {
				premium.setNettoAmount(getNetAmount(premiumOpt.get()));
				premium.setTaxAmount(getTaxAmount(transaction, premiumOpt.get(), taxValue.get()));
			}
		}

		return premium;
	}

	private Optional<AccountTransactionDTO> getTax(TransactionDTO transaction) {
		return transaction.getAccountTransactions().stream().filter(x -> x.getAccount().equalsIgnoreCase(ACCOUNT_TYPE_PREMTAX)).findFirst();
	}

	private AmountType getTaxAmount(TransactionDTO transaction, PolicyPremiumDTO premium, AccountTransactionDTO taxValue) {
		AmountType amountTax = new AmountType();
		amountTax.setCurrencyCode(premium.getCurrency());
		if (transaction.getAccountTransactions().size() > 0) {
			amountTax.setValue(taxValue.getAmount());
		}
		return amountTax;
	}

	private AmountType getNetAmount(PolicyPremiumDTO premium) {
		AmountType amountNetto = new AmountType();
		amountNetto.setCurrencyCode(premium.getCurrency());
		amountNetto.setValue(premium.getNetPremium());
		return amountNetto;
	}

	private AmountType getAmount(PolicyPremiumDTO premium) {
		AmountType amount = new AmountType();
		amount.setCurrencyCode(premium.getCurrency());
		amount.setValue(premium.getModalPremium());
		return amount;
	}

	protected AdditionalPremiumDetails generateAdditionalPremiumDetails(TransactionDTO transaction, PolicyDTO policyDTO, PolicyValuationDTO valuation) {
		logger.info("Getting the AdditionalPremiumDetails for tran :" + transaction.getTrnId());
		AdditionalPremiumDetails details = new AdditionalPremiumDetails();
		// add the premium
		details.setAdditionalPremium(getPremium(transaction, policyDTO));

		// get funds repartitions
		getAdditionalPremiumFunds(details, transaction, valuation);

		// get the fees
		details.setEntryFees(getFees(transaction, policyDTO));

		// Death coverage

		List<PolicyHolder> holders = generateHolders(policyDTO.getPolicyHolders());

		Policy policy = getXmlPolicy(policyDTO, holders);

		DeathCoverageClauseDTO deathCoverageDTO = liabilityDeathCoverageService.getPolicyDeathCoverage(policyDTO.getPolId());
		DeathCoverageDurationType deathCoverageDurationType = computeDeathCoverageDuration(policy.getInsureds(), policy.getEffectDate(), policy.getContractDuration());
		details.setDeathCoverage(generateDeathCoverage(deathCoverageDTO, policyDTO.getFirstPolicyCoverages(), deathCoverageDurationType));

		return details;
	}

	private Fee getFees(TransactionDTO transaction, PolicyDTO policyDTO) {
		Fee fee = new Fee();
		PolicyCoverageDTO policyCoverageDTO = getCoverageByPolicyAndCoverage(policyDTO.getPolId(), transaction.getCoverage());
		String productLineId = policyCoverageDTO.getProductLine();
		String policyId = transaction.getPolicyId();
		Integer coverage = policyCoverageDTO.getCoverage();

		ProductValueDTO policyFees = liabilityProductValueService.getPolicyFees(policyId, productLineId, coverage);
		BigDecimal alphaValue = liabilityProductValueService.getNumericValue(policyFees);

		if (liabilityProductValueService.isPercentagePolicyFee(productLineId)) {
			// Percentage
			fee.setRate(alphaValue);
		} else {
			// Amount
			AmountType policyFeeAmount = new AmountType();
			policyFeeAmount.setCurrencyCode(policyDTO.getCurrency());
			policyFeeAmount.setValue(alphaValue);
			fee.setAmount(policyFeeAmount);
		}

		return fee;
	}

	/*
	 * private BigDecimal getTransactionFee(TransactionDTO transaction, String accountType) { Optional<AccountTransactionDTO> elem = transaction.getAccountTransactions().stream() .filter(x ->
	 * x.getAccount().trim().equalsIgnoreCase(accountType.trim())) .findFirst();
	 * 
	 * if (!elem.isPresent()) { return BigDecimal.ZERO; }
	 * 
	 * return elem.get().getAmount(); }
	 */

	private void getAdditionalPremiumFunds(AdditionalPremiumDetails details, TransactionDTO transaction, PolicyValuationDTO valuation) {
		List<SpecializedInsuranceFundsRepartition> specializedInsuranceFunds = new ArrayList<>();
		List<DedicatedFundsRepartition> dedicatedFunds = new ArrayList<>();

		BigDecimal policyValue = transaction.getValue0();

		Fund fund;
		Collection<FundTransactionDTO> transactionFundTransactions = fundTransactionService.filterNonPremiumFundTransactions(transaction);
		Collection<FundTransactionDTO> newTransactionFundTransactions = new ArrayList<FundTransactionDTO>();


		// fetch only new funds
		for (FundTransactionDTO dto : transactionFundTransactions) {
			if (valuation.getHoldings().stream().anyMatch(x -> x.getFundId().equalsIgnoreCase(dto.getFund()) && x.getUnits().compareTo(dto.getUnits()) == 0)) {
				newTransactionFundTransactions.add(dto);
			}
		}

		for (lu.wealins.common.dto.liability.services.FundTransactionDTO fundTransactionDTO : newTransactionFundTransactions) {
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
		}

		details.setDedicatedFunds(dedicatedFunds);
		details.setSpecializedInsuranceFunds(specializedInsuranceFunds);
	}

}
