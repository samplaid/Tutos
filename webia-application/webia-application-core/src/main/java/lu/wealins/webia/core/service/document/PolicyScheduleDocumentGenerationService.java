package lu.wealins.webia.core.service.document;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.liability.services.DeathCoverageClauseDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundTransactionDTO;
import lu.wealins.common.dto.liability.services.PolicyClausesDTO;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyPremiumDTO;
import lu.wealins.common.dto.liability.services.ProductValueDTO;
import lu.wealins.common.dto.liability.services.enums.AlternativeFundType;
import lu.wealins.editing.common.webia.AmountType;
import lu.wealins.editing.common.webia.DeathCoverageDurationType;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.EndOnInsuredDeathType;
import lu.wealins.editing.common.webia.Fee;
import lu.wealins.editing.common.webia.Fund;
import lu.wealins.editing.common.webia.Header;
import lu.wealins.editing.common.webia.InvestmentStrategy;
import lu.wealins.editing.common.webia.OrderedPerson;
import lu.wealins.editing.common.webia.Policy;
import lu.wealins.editing.common.webia.PolicyHolder;
import lu.wealins.editing.common.webia.PolicySchedule;
import lu.wealins.editing.common.webia.PolicySchedule.Fees;
import lu.wealins.webia.core.service.document.trait.PolicyBasedDocumentGenerationService;
import lu.wealins.webia.core.service.impl.PolicyDocumentService;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.EditingUser;
import lu.wealins.webia.ws.rest.request.TranscoType;

@Service
@Transactional(readOnly = true)
public class PolicyScheduleDocumentGenerationService extends PolicyDocumentService implements PolicyBasedDocumentGenerationService {

	@Override
	protected PolicyDTO getPolicyByRequest(EditingRequest editingRequest) {
		Assert.notNull(editingRequest.getPolicy(), "the policy id can't be null");
		return policyService.getPolicy(editingRequest.getPolicy());
	}

	@Override
	protected Document createSpecificDocument(EditingRequest editingRequest, PolicyDTO policyDTO, String productCountry, String language, String userId) {
		PolicySchedule policySchedule = generatePolicySchedule(policyDTO, productCountry);
		Policy policy = policySchedule.getPolicy();

		Document document = createPolicyDocument(editingRequest.getCreationUser(), policyDTO, productCountry, language, policy);
		document.setPolicySchedule(policySchedule);
		return document;
	}

	@Override
	protected boolean isAlternativeFund(FundDTO fundDTO, Fund fund) {
		switch(fundDTO.getFundSubType()) {
			case "FID":
				try {
					return AlternativeFundType.valueOf(fundDTO.getAlternativeFunds()).isFIDAlternativeFund();
				}
				catch(IllegalArgumentException e) {
					log.error("Unknown alternative funds " + fund);
				}
		}
		return super.isAlternativeFund(fundDTO, fund);
	}

	private PolicySchedule generatePolicySchedule(PolicyDTO policyDTO, String productCountry) {
		PolicySchedule policySchedule = new PolicySchedule();

		String policyId = policyDTO.getPolId();

		List<PolicyHolder> holders = generateHolders(policyDTO.getPolicyHolders());

		Policy policy = getXmlPolicy(policyDTO, holders);

		if (!holders.isEmpty()) {
			policySchedule.setPolicyHolders(holders);
		}

		// Insured
		List<OrderedPerson> insureds = generateInsureds(policyDTO.getInsureds());
		policySchedule.setInsureds(insureds);

		policySchedule.setPolicy(policy);

		// Owner on death
		policySchedule.setOnDeathPolicyHolders(generateOnDeathPolicyHolders(policyDTO));

		/** Clauses and Beneficiaries **/
		PolicyClausesDTO clauses = policyService.getClauses(policy.getPolicyId());

		// Death
		policySchedule.setBeneficiaryClauseDeath(generateClauses(DEATH, clauses.getDeath(), policyDTO.getDeathBeneficiaries(), policyDTO.getLifeBeneficiaries()));

		// Life
		policySchedule.setBeneficiaryClauseLife(generateClauses(LIFE, clauses.getMaturity(), policyDTO.getDeathBeneficiaries(), policyDTO.getLifeBeneficiaries()));

		/** Investment and Investment strategy **/

		Collection<FundTransactionDTO> fundTransactions = getFundTransactions(policy);

		// Investment
		policySchedule.setInvestments(generateInvestments(fundTransactions, policy));

		// Investment strategy
		InvestmentStrategy investmentStrategy = generateInvestmentStrategy(fundTransactions);
		policySchedule.setInvestmentStrategy(investmentStrategy);

		// Premium
		List<PolicyPremiumDTO> premiums = policyDTO.getPolicyPremiums();
		PolicyPremiumDTO firstPremium = getFirstPremium(premiums);
		policySchedule.setInitialPremium(generateInitialPremium(firstPremium));

		// Limits
		policySchedule.setLimits(generateLimits(productCountry));

		// Fees
		policySchedule.setFees(generateFees(policyDTO));

		/** Other **/
		// Correspondence address
		AgentLightDTO agentLightDTO = policyDTO.getMailToAgent();
		AgentDTO mailToAgent = null;
		if (agentLightDTO != null && StringUtils.isNotBlank(agentLightDTO.getAgtId())) {
			mailToAgent = agentService.getAgent(agentLightDTO.getAgtId());
		}

		AgentContactLiteDTO agentContact = null;
		if (mailToAgent != null) {
			agentContact = documentGenerationService.getAgentContact(getMailToAgentFund(mailToAgent, fundTransactions), policyDTO, mailToAgent);
		}
		policySchedule.setCorrespondenceAddress(generateCorrespondenceAddress(policyDTO, agentContact));

		// Send copy to
		if (sendACopy(policyDTO) && policyDTO.getMailToAgent() != null) {
			policySchedule.setSendCopyTo(policyDTO.getMailToAgent().getName());
		}

		// Transfer
		policySchedule.setTransfers(generateTransfer(policyDTO));

		EndOnInsuredDeathType endOnInsuredDeathEnum = getEndOnInsuredDeath(policyDTO.getFirstPolicyCoverages());

		if (endOnInsuredDeathEnum != null) {
			policySchedule.setEndOnInsuredDeath(endOnInsuredDeathEnum);
		}

		// Death coverage
		DeathCoverageClauseDTO deathCoverageDTO = liabilityDeathCoverageService.getPolicyDeathCoverage(policyId);
		DeathCoverageDurationType deathCoverageDurationType = computeDeathCoverageDuration(insureds, policy.getEffectDate(), policy.getContractDuration());
		policySchedule.setDeathCoverage(generateDeathCoverage(deathCoverageDTO, policyDTO.getFirstPolicyCoverages(), deathCoverageDurationType));

		policySchedule.setMedicalQuestionnaire(hasMedicalQuestionnaire(policyId));

		policySchedule.setGeneralConditionsRef(liabilityProductService.getGeneralCondition(policyDTO));

		return policySchedule;
	}

	@Override
	protected DocumentType getDocumentType() {
		return DocumentType.POLICY_SCHEDULE;
	}

	@Override
	protected String getXmlPath(EditingRequest editingRequest, String polId) {
		return buildXmlPath(polId, filePath);
	}

	private Document createPolicyDocument(EditingUser creationUser, PolicyDTO policyDTO, String productCountry, String language, Policy policy) {
		String transcodedProductCountry = documentGenerationService.getTransco(TranscoType.PAYS, productCountry);
		Header header = documentGenerationService.generateHeader(creationUser, getDocumentType(), language, transcodedProductCountry, null);

		return getPolicyDocument(policyDTO, policy, header);
	}

	private Fees generateFees(PolicyDTO policyDTO) {
		Fees fees = new Fees();
		Fee fee = null;
		boolean hasFees = false;

		String policyId = policyDTO.getPolId();
		PolicyCoverageDTO firstCoverage = policyDTO.getFirstPolicyCoverages();
		String productLineId = firstCoverage.getProductLine();
		Integer coverage = firstCoverage.getCoverage();

		ProductValueDTO contractManagementFees = liabilityProductValueService.getContractManagementFees(policyId, productLineId, coverage);
		ProductValueDTO policyFees = liabilityProductValueService.getPolicyFees(policyId, productLineId, coverage);
		ProductValueDTO surrenderFees = liabilityProductValueService.getSurrenderFees(policyId, productLineId);
		ProductValueDTO switchFees = liabilityProductValueService.getSwitchFees(policyId, productLineId);
		ProductValueDTO withdrawalFees = liabilityProductValueService.getWithdrawalFees(policyId, productLineId);

		// Management fees
		if (contractManagementFees != null) {
			fee = new Fee();
			fee.setRate(contractManagementFees.getNumericValue());
			fees.setAnnualManagementFees(fee);
			hasFees = true;
		}

		// Entry fees
		if (policyFees != null) {
			fee = new Fee();
			if (liabilityProductValueService.isPercentagePolicyFee(productLineId)) {
				// Percentage
				fee.setRate(policyFees.getNumericValue());
			} else {
				// Amount
				AmountType policyFeeAmount = new AmountType();
				policyFeeAmount.setCurrencyCode(policyDTO.getCurrency());
				policyFeeAmount.setValue(policyFees.getNumericValue());
				fee.setAmount(policyFeeAmount);
			}

			fees.setEntryFees(fee);
			hasFees = true;
		}

		// Surrender fees
		if (surrenderFees != null) {
			fee = new Fee();
			fee.setRate(surrenderFees.getNumericValue());
			// fees.setSurrenderFees(fee);
			hasFees = true;
		}

		// Withdrawal fees
		if (withdrawalFees != null) {
			fee = new Fee();
			fee.setRate(withdrawalFees.getNumericValue());
			fees.setWithdrawalFees(fee);
			hasFees = true;
		}

		// Switch fees
		if (switchFees != null) {
			fee = new Fee();
			fee.setRate(switchFees.getNumericValue());
			fees.setSwitchFees(fee);
			hasFees = true;
		}

		if (hasFees) {
			return fees;
		}
		return null;
	}
}
