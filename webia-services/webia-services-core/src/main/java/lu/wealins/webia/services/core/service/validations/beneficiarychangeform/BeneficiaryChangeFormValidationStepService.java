package lu.wealins.webia.services.core.service.validations.beneficiarychangeform;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lu.wealins.common.dto.webia.services.BeneficiaryChangeFormDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.services.core.service.validations.impl.AmendmentFormValidationService;

public abstract class BeneficiaryChangeFormValidationStepService extends AmendmentFormValidationService<BeneficiaryChangeFormDTO> {

	private static Set<WorkflowType> SUPPORTED_WORFLOW_TYPES = Stream.of(WorkflowType.BENEFICIARY_CHANGE).collect(Collectors.toSet());

	@Override
	public Set<WorkflowType> getSupportedWorkflowTypes() {
		return SUPPORTED_WORFLOW_TYPES;
	}

}
