package lu.wealins.common.validations;

import lu.wealins.common.dto.webia.services.enums.WorkflowType;

public interface WorkflowItemSpecific {
	WorkflowType getSupportedWorkflowType();
}
