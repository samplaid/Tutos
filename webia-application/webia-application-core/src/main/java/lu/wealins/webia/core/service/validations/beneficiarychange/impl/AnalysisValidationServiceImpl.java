package lu.wealins.webia.core.service.validations.beneficiarychange.impl;

import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.ANALYSIS;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.GenericType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryChangeFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.validations.beneficiarychange.BeneficiaryChangeFormValidationService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.NordicValidationRequest;

@Service(value = "BeneficiaryChangeFormAnalysisValidationService")
public class AnalysisValidationServiceImpl extends BeneficiaryChangeFormValidationService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = Stream.of(ANALYSIS).collect(Collectors.toSet());
	private static final String VALIDATE_NORDIC = "webia/validate/nordic";

	@Autowired
	LiabilityPolicyService policyService;

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		BeneficiaryChangeFormDTO formData = getFormData(step);

		PolicyDTO policy = policyService.getPolicy(formData.getPolicyId());

		NordicValidationRequest request = new NordicValidationRequest();
		request.setClauses(formData.getDeathBenefClauseForms());
		request.setPolicyHolderSize(policy.getPolicyHolders().size());
		request.setProductCountry(policy.getProduct().getNlCountry());
		request.setLives(policy.getFirstPolicyCoverages().getLives().getNumber());

		return restClientUtils.post(VALIDATE_NORDIC, request, new GenericType<Collection<String>>() {
		}).stream().collect(Collectors.toList());
	}

	@Override
	public boolean needValidateBeforeComplete(StepDTO step) {
		return ENABLE_STEPS.contains(StepTypeDTO.getStepType(step.getStepWorkflow()));
	}

	@Override
	public boolean needValidateBeforeSave(StepDTO step) {
		return false;
	}

}
