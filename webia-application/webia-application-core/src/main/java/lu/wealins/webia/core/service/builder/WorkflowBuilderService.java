package lu.wealins.webia.core.service.builder;

import lu.wealins.common.validations.WorkflowItemSpecific;

public interface WorkflowBuilderService<T extends WorkflowItemSpecific> {

	T getService(Integer workflowType);
}
