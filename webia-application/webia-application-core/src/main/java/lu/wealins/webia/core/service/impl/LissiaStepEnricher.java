package lu.wealins.webia.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.ClientRoleActivationFlagDTO;
import lu.wealins.common.dto.liability.services.PolicyClientRoleViewRequest;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.webia.core.mapper.AbstractAppFormMapper;
import lu.wealins.webia.core.service.ClientRoleActivationFlagService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityProductService;
import lu.wealins.webia.core.service.StepEnricher;

public abstract class LissiaStepEnricher extends AbstractStepEnricher implements StepEnricher {

	@Autowired
	private LiabilityPolicyService policyService;

	@Autowired
	private ClientRoleActivationFlagService clientRoleActivationFlagService;

	@Autowired
	private LiabilityProductService productService;

	@Override
	public AppFormDTO enrichAppForm(AppFormDTO appForm) {

		if (StringUtils.isBlank(appForm.getPolicyId())) {
			return appForm;
		}

		String countryCode = productService.getCountryCode(appForm.getProductCd());
		ClientRoleActivationFlagDTO solveBeneficiaryRoleActivation = clientRoleActivationFlagService.solveBeneficiaryRoleActivation(countryCode);

		PolicyClientRoleViewRequest request = new PolicyClientRoleViewRequest();
		request.setClientRoleActivationFlag(solveBeneficiaryRoleActivation);
		request.setPolicyId(appForm.getPolicyId());

		// Load Policy DTO from Lissia
		PolicyDTO policy = policyService.getPolicy(request);

		if (policy == null) {
			throw new IllegalStateException("Policy " + appForm.getPolicyId() + " does not exist.");
		}

		enrichProduct(appForm);
		enrichFunds(appForm);

		// Retrieve information from LISSIA
		getFormMapper().updateAppFormDTO(policy, appForm);

		enrichClients(appForm);

		return appForm;
	}

	protected abstract AbstractAppFormMapper getFormMapper();
}
