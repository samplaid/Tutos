package lu.wealins.webia.core.service.validations.beneficiarychange;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lu.wealins.common.dto.webia.services.BeneficiaryChangeFormDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.common.validations.FormDataRetriever;
import lu.wealins.common.validations.ValidationStepService;

public abstract class BeneficiaryChangeFormValidationService implements ValidationStepService, FormDataRetriever<BeneficiaryChangeFormDTO> {

	public static Set<WorkflowType> SUPPORTED_WORFLOW_TYPES = Stream.of(WorkflowType.BENEFICIARY_CHANGE).collect(Collectors.toSet());

	@Override
	public Set<WorkflowType> getSupportedWorkflowTypes() {
		return SUPPORTED_WORFLOW_TYPES;
	}

}
