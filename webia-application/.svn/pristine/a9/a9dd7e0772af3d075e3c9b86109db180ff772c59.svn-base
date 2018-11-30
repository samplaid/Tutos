package lu.wealins.webia.core.service.validations.brokerchange.impl;

import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.ANALYSIS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.common.dto.liability.services.TransactionDTO;
import lu.wealins.common.dto.liability.services.enums.TransactionCode;
import lu.wealins.common.dto.webia.services.BrokerChangeFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.LiabilityPolicyCoverageService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityTransactionService;
import lu.wealins.webia.core.service.validations.FundValidationService;
import lu.wealins.webia.core.service.validations.SendingRulesValidationService;
import lu.wealins.webia.core.service.validations.brokerchange.BrokerChangeFormValidationService;

@Service(value = "BrokerChangeFormAnalysisValidationService")
public class AnalysisValidationServiceImpl extends BrokerChangeFormValidationService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = Stream.of(ANALYSIS).collect(Collectors.toSet());

	@Autowired
	private FundValidationService fundValidationService;
	@Autowired
	private LiabilityFundService fundService;
	@Autowired
	private SendingRulesValidationService sendingRulesValidationService;
	@Autowired
	private LiabilityPolicyService policyService;
	@Autowired
	private LiabilityPolicyCoverageService coverageService;
	@Autowired
	private LiabilityTransactionService transactionService;

	@Override
	public List<String> validateBeforeSave(StepDTO step) {
		BrokerChangeFormDTO formData = getFormData(step);
		List<String> errors = new ArrayList<>();

		validateCommissionRates(formData, errors);
		validateAdminFeesTransaction(formData, errors);

		return errors;
	}

	private void validateAdminFeesTransaction(BrokerChangeFormDTO formData, List<String> errors) {
		Collection<TransactionDTO> transactions = transactionService.getActiveTransactionByPolicyAndEventType(formData.getPolicyId(), Integer.valueOf(TransactionCode.ADM_FEE.getCode()));

		// first, filter the active transaction with admin fees, then retrieve the corresponding fund transactions and filter again on the active fund transactions with admin fees
		if (formData.getChangeDate() != null && transactions.stream().filter(x -> x.getEffectiveDate().after(formData.getChangeDate()))
				.flatMap(x -> x.getFundTransactions().stream()).filter(x -> Integer.valueOf(1).equals(x.getStatus()) && x.getEventType() == TransactionCode.ADM_FEE.getCode())
				.findAny().isPresent()) {
			errors.add("There is a transaction with admin fees which occurs after the broker change.");
		}
	}

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		BrokerChangeFormDTO formData = getFormData(step);
		List<String> errors = new ArrayList<>();

		Collection<String> fundIds = fundService.getFundIds(formData.getPolicyValuation());
		Collection<FundLiteDTO> funds = fundService.getFunds(fundIds);
		fundValidationService.validateExternalFunds(funds, formData.getBroker(), errors);
		sendingRulesValidationService.validate(formData.getSendingRules(), getFirstPolicyHolderCliId(formData), errors);

		return errors;
	}

	private void validateCommissionRates(BrokerChangeFormDTO formData, List<String> errors) {
		
		PolicyValuationDTO policyValuation = formData.getPolicyValuation();
		if(policyValuation == null) {
			return;
		}

		Collection<String> fundIds = fundService.getFundIds(policyValuation);
		
		Map<String, Collection<Integer>> coveragesByFunds = coverageService.getCoverages(formData.getPolicyId(), fundIds);
		Map<String, BigDecimal> feeRatesByFundId = mapFundIdToFeeRate(policyValuation.getHoldings());
		Map<String, BigDecimal> commissionRatesByFundId = mapFundIdToCommissionRate(policyValuation.getHoldings());
		Map<Integer, Collection<String>> fundsByCoverages = getFundsByCoverages(coveragesByFunds);

		Set<String> temp = new HashSet<>();
		for (PolicyValuationHoldingDTO holding : policyValuation.getHoldings()) {
			
			String fundId = holding.getFundId();
			Collection<Integer> coverages = coveragesByFunds.get(fundId);

			// foreach funds having the same coverage we check that we have the same rate!
			for (Integer coverage : coverages) {
				Collection<String> fundsHavingTheSameCoverage = fundsByCoverages.get(coverage);

				boolean allFeeRatesForfundsHavingTheSameCoverageAreEqual = feeRatesByFundId.entrySet().stream().filter(x -> fundsHavingTheSameCoverage.contains(x.getKey())).map(x -> x.getValue())
						.collect(Collectors.toSet()).size() == 1;

				if (!allFeeRatesForfundsHavingTheSameCoverageAreEqual) {
					temp.add("The fee rates for the funds " + fundsHavingTheSameCoverage + " should be equals because they are linked to the same coverage.");
				}

				boolean allCommissionRatesForfundsHavingTheSameCoverageAreEqual = commissionRatesByFundId.entrySet().stream().filter(x -> fundsHavingTheSameCoverage.contains(x.getKey()))
						.map(x -> x.getValue())
						.collect(Collectors.toSet()).size() == 1;

				if (!allCommissionRatesForfundsHavingTheSameCoverageAreEqual) {
					temp.add("The commission rates for the funds " + fundsHavingTheSameCoverage + " should be equals because they are linked to the same coverage.");
				}
			}

		}

		errors.addAll(temp);
	}
	
	private Map<String, BigDecimal> mapFundIdToFeeRate(List<PolicyValuationHoldingDTO> holdings) {
		Map<String, BigDecimal> result = new HashMap<>();
		holdings.forEach((holding) -> result.put(holding.getFundId(), holding.getFeeRate()));
		return result;
	}

	private Map<String, BigDecimal> mapFundIdToCommissionRate(List<PolicyValuationHoldingDTO> holdings) {
		Map<String, BigDecimal> result = new HashMap<>();
		holdings.forEach((holding) -> result.put(holding.getFundId(), holding.getCommissionRate()));
		return result;
	}

	private Map<Integer, Collection<String>> getFundsByCoverages(Map<String, Collection<Integer>> coveragesByFunds) {
		Map<Integer, Collection<String>> fundsByCoverages = new HashMap<>();
		for (Entry<String, Collection<Integer>> coveragesByFund : coveragesByFunds.entrySet()) {
			String fundId = coveragesByFund.getKey();
			for (Integer coverage : coveragesByFund.getValue()) {
				Collection<String> fundIds = fundsByCoverages.get(coverage);
				if(fundIds == null) {
					fundIds = new ArrayList<>();
				}
				
				fundIds.add(fundId);
				fundsByCoverages.put(coverage, fundIds);
			}
		}
		
		return fundsByCoverages;
	}

	private Integer getFirstPolicyHolderCliId(BrokerChangeFormDTO formData) {
		PolicyLightDTO policy = policyService.getPolicyLight(formData.getPolicyId());

		if (policy == null) {
			return null;
		}

		if (CollectionUtils.isEmpty(policy.getPolicyHolders())) {
			return null;
		}

		return policy.getPolicyHolders().iterator().next().getCliId();
	}


	@Override
	public boolean needValidateBeforeComplete(StepDTO step) {
		return ENABLE_STEPS.contains(StepTypeDTO.getStepType(step.getStepWorkflow()));
	}

	@Override
	public boolean needValidateBeforeSave(StepDTO step) {
		return ENABLE_STEPS.contains(StepTypeDTO.getStepType(step.getStepWorkflow()));
	}

}
