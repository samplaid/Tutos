package lu.wealins.webia.core.service.validations.additionalpremium;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.common.validations.FormDataRetriever;
import lu.wealins.common.validations.ValidationStepService;

public abstract class AdditionalPremiumValidationStepService implements ValidationStepService, FormDataRetriever<AppFormDTO> {

	private static Set<WorkflowType> SUPPORTED_WORFLOW_TYPES = Stream.of(WorkflowType.ADDITIONAL_PREMIUM).collect(Collectors.toSet());

	@Override
	public Set<WorkflowType> getSupportedWorkflowTypes() {
		return SUPPORTED_WORFLOW_TYPES;
	}
}
