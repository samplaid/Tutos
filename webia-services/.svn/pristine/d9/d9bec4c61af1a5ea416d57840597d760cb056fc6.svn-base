package lu.wealins.webia.services.core.service.builder.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.services.core.service.builder.WorkflowBuilderService;
import lu.wealins.webia.services.core.service.impl.form.data.WorkflowItemSpecific;


@Service
public abstract class AbstractWorkflowBuilderServiceImpl<T extends WorkflowItemSpecific> implements WorkflowBuilderService<T> {

	private Map<WorkflowType, T> servicesByOperation;

	@Autowired
	public void setServicesMap(List<T> services) {
		servicesByOperation = services.stream().collect(Collectors.toMap(T::getSupportedWorkflowType, Function.identity()));
	}

	@Override
	public T getService(Integer workflowType) {
		WorkflowType enumType = WorkflowType.getType(workflowType);
		T workflowFormService = servicesByOperation.get(enumType);
		if (workflowFormService == null) {
			throw new IllegalArgumentException(String.format("No workflow service defined for type : %s", enumType));
		}
		return workflowFormService;
	}

}
