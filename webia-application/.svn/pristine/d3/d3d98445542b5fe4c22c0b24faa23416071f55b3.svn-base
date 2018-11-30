package lu.wealins.webia.core.service.validations.transactionform;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.common.validations.FormDataRetriever;
import lu.wealins.common.validations.ValidationStepService;

public abstract class TransactionFormValidationService implements ValidationStepService, FormDataRetriever<TransactionFormDTO> {

	public static Set<WorkflowType> SUPPORTED_WORFLOW_TYPES = Stream.of(WorkflowType.WITHDRAWAL, WorkflowType.SURRENDER).collect(Collectors.toSet());

	@Override
	public Set<WorkflowType> getSupportedWorkflowTypes() {
		return SUPPORTED_WORFLOW_TYPES;
	}

}
