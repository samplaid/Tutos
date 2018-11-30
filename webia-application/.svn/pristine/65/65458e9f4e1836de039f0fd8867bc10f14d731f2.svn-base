package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.PolicyEntryFeesDto;
import lu.wealins.common.dto.liability.services.PolicyHolderLiteDTO;
import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.FundFormDTO;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.service.AdditionalPremiumService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityProductValueService;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.StepEnricher;
import lu.wealins.webia.core.service.WebiaAppFormService;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.service.WebiaFormDataService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;

@Service
public class AdditionalPremiumServiceImpl implements AdditionalPremiumService {

	@Autowired
	@Qualifier(value = "additionalPremiumLissiaStepEnricher")
	private StepEnricher lissiaStepEnricher;

	@Autowired
	private LiabilityPolicyService policyService;

	@Autowired
	private LiabilityProductValueService productValueService;

	@Autowired
	private WebiaApplicationParameterService appliParamService;

	@Autowired
	private LiabilityWorkflowService workflowService;

	@Autowired
	private WebiaWorkflowUserService workflowUserService;

	@Autowired
	private AbstractFundFormService<FundFormDTO> fundFormService;

	@Autowired
	private WebiaAppFormService appFormService;

	@Autowired
	private WebiaFormDataService formDataService;

	private static final String BELGIUM = "BEL";
	private static final String BELGIUM_TAX_CODE = "TAX_BELGIUM";


	@Override
	public AppFormDTO initAdditionalPremium(String policyId, Integer workflowItemId) {
		AppFormDTO additionalPremium = formDataService.initFormData(workflowItemId, WorkflowType.ADDITIONAL_PREMIUM.getValue(), AppFormDTO.class);

		PolicyLightDTO policy = policyService.getPolicyLight(policyId);

		if (policy == null) {
			return additionalPremium;
		}

		additionalPremium.setPolicyId(policyId);
		additionalPremium.setProductCd(policy.getPrdId());
		additionalPremium.setProductCountryCd(policy.getNlCountry());
		additionalPremium.setWorkflowItemId(workflowItemId);

		// retrieve the fund from the policy valuation
		Collection<FundFormDTO> fundForms = fundFormService.getFundForms(policyId);
		additionalPremium.setFundForms(fundForms);

		boolean belgiumTaxOverride = isBelgiumTaxOverrided(policy.getPolicyHolders());
		if (belgiumTaxOverride) {
			BigDecimal taxRate = appliParamService.getBigDecimalValue(BELGIUM_TAX_CODE);
			additionalPremium.setTax(true);
			additionalPremium.setTaxRate(taxRate);
		}

		lissiaStepEnricher.enrichAppForm(additionalPremium);

		PolicyEntryFeesDto lastCoverageFees = productValueService.getLastCoverageFees(policyId);

		updateLastCoverageFees(additionalPremium, lastCoverageFees);

		// Reset Date of Effect
		additionalPremium.setPaymentDt(null);
		additionalPremium.setPolicyTransfer(Boolean.FALSE);
		additionalPremium.setPaymentTransfer(Boolean.FALSE);

		return additionalPremium;
	}

	// This logic should be in the lissia enricher, but putting it there with an abstract layer increases the code complexity.
	// The enricher should be refactored.
	private void updateLastCoverageFees(AppFormDTO additionalPremium, PolicyEntryFeesDto lastCoverageFees) {
		boolean isPercentage = BooleanUtils.isTrue(lastCoverageFees.getIsPercentage());
		PartnerFormDTO broker = additionalPremium.getBroker();
		BigDecimal entryFees = lastCoverageFees.getEntryFees();
		BigDecimal brokerEntryFees = lastCoverageFees.getBrokerEntryFees();

		if (isPercentage) {
			additionalPremium.setEntryFeesPct(entryFees);
			if (broker != null) {
				broker.setEntryFeesPct(brokerEntryFees);
			}
		} else {
			additionalPremium.setEntryFeesAmt(entryFees);
			if (broker != null) {
				broker.setEntryFeesAmt(brokerEntryFees);
			}
		}
	}

	private boolean isBelgiumTaxOverrided(Collection<PolicyHolderLiteDTO> policyHolders) {
		if (policyHolders == null) {
			return false;
		}
		return policyHolders
				.stream()
				.map(PolicyHolderLiteDTO::getCountry)
				.anyMatch(BELGIUM::equals);
	}

	@Override
	public AppFormDTO recreate(SecurityContext context, Integer workflowItemId) {
		String userId = workflowUserService.getUserId(context);
		workflowService.recreateWorkflow(workflowItemId, userId);
		return appFormService.recreate(workflowItemId);
	}
}
