package lu.wealins.webia.services.core.service.validations.brokerchangeform;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lu.wealins.common.dto.webia.services.BrokerChangeFormDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.services.core.service.validations.impl.AmendmentFormValidationService;

public abstract class BrokerChangeFormValidationStepService extends AmendmentFormValidationService<BrokerChangeFormDTO> {

	private static Set<WorkflowType> SUPPORTED_WORFLOW_TYPES = Stream.of(WorkflowType.BROKER_CHANGE).collect(Collectors.toSet());

	@Override
	public Set<WorkflowType> getSupportedWorkflowTypes() {
		return SUPPORTED_WORFLOW_TYPES;
	}

}
