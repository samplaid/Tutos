package lu.wealins.webia.services.core.service.validations.withdrawal;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.common.validations.ValidationStepService;
import lu.wealins.webia.services.core.service.validations.FormDataValidationService;

public abstract class WithdrawalValidationStepService implements ValidationStepService, FormDataValidationService<TransactionFormDTO> {

	private static Set<WorkflowType> SUPPORTED_WORFLOW_TYPES = Stream.of(WorkflowType.WITHDRAWAL).collect(Collectors.toSet());

	@Override
	public Set<WorkflowType> getSupportedWorkflowTypes() {
		return SUPPORTED_WORFLOW_TYPES;
	}

}
