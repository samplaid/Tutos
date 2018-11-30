package lu.wealins.webia.services.core.service.builder;

import lu.wealins.webia.services.core.service.impl.form.data.WorkflowItemSpecific;

public interface WorkflowBuilderService<T extends WorkflowItemSpecific> {

	T getService(Integer workflowType);
}
