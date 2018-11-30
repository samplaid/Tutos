package lu.wealins.webia.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.BeneficiaryChangeRequest;
import lu.wealins.common.dto.liability.services.ClientRoleActivationFlagDTO;
import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.webia.core.service.ClientRoleActivationFlagService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityProductService;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;


@Component
public class LiabilityBeneficiaryChangeRequestHelper {

	private static Logger LOGGER = LoggerFactory.getLogger(LiabilityBeneficiaryChangeRequestHelper.class);

	private static final String CHANGE_BENEF_AVAILABLE_SUB_ROLES = "CHANGE_BENEF_AVAILABLE_SUB_ROLES";
	private static final String RULES_2_POLICYHOLDERS_NORD = "RULES_2_POLICYHOLDERS_NORD";

	@Autowired
	private ClientRoleActivationFlagService clientRoleActivationFlagService;
	
	@Autowired
	private WebiaApplicationParameterService applicationParameterService;

	@Autowired
	private LiabilityPolicyService policyService;

	@Autowired
	private LiabilityProductService productService;

	public BeneficiaryChangeRequest createBeneficiaryChangeRequest(String policyId, Integer workflowItemId) {
		ClientRoleActivationFlagDTO beneficiaryRoleActivation = getClientRoleActivationFlag(policyId);

		BeneficiaryChangeRequest request = new BeneficiaryChangeRequest();

		if (workflowItemId != null) {
			request.setWorkflowItemId(workflowItemId);
		}
		request.setPolicyId(policyId);
		request.setClientRoleActivationFlagDTO(beneficiaryRoleActivation);
		setupApplicationParams(request);

		return request;
	}

	private void setupApplicationParams(BeneficiaryChangeRequest request) {
		// provide application parameters used by liability to process the form
		String[] applicationParameterKey = new String[] { RULES_2_POLICYHOLDERS_NORD, CHANGE_BENEF_AVAILABLE_SUB_ROLES };

		Map<String, String> applicationParams = new HashMap<>();
		for (String key : applicationParameterKey) {
			applicationParams.put(key, applicationParameterService.getApplicationParameter(key).getValue());
		}

		request.setApplicationParams(applicationParams);
	}

	private ClientRoleActivationFlagDTO getClientRoleActivationFlag(String policyId) {
		PolicyLightDTO policyLight = policyService.getPolicyLight(policyId);
		if (policyLight == null) {
			throw new IllegalStateException("Policy " + policyId + " cannot be found.");
		}

		String countryCode = productService.getCountryCode(policyLight.getPrdId());

		if (countryCode == null) {
			LOGGER.warn("No country code liked to the " + policyLight.getPrdId() + ".");
		}

		return clientRoleActivationFlagService.solveBeneficiaryRoleActivation(countryCode);
	}
}
