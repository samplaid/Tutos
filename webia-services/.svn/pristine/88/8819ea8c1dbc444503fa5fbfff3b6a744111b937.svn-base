package lu.wealins.webia.services.core.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.UpdateOperationStatusRequest;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.services.core.service.OperationStatusService;
import lu.wealins.webia.services.core.service.UpdatableStatusService;

@Service
public class StatusServiceImpl implements OperationStatusService {

	@SuppressWarnings("rawtypes")
	private Map<WorkflowType, UpdatableStatusService> servicesByOperation;

	@SuppressWarnings("rawtypes")
	@Autowired
	public void setServicesMap(List<UpdatableStatusService> services) {
		servicesByOperation = services.stream()
				.collect(Collectors.toMap(UpdatableStatusService::getSupportedWorkflowType, Function.identity()));
	}

	@Override
	public Boolean updateStatus(UpdateOperationStatusRequest request) {
		return getService(request.getWorkflowType()).updateStatus(request.getWorkflowItemId(), request.getStatus());
	}

	@SuppressWarnings("rawtypes")
	private UpdatableStatusService getService(WorkflowType workfowType) {
		UpdatableStatusService workflowFormService = servicesByOperation.get(workfowType);
		if (workflowFormService == null) {
			throw new IllegalArgumentException(String.format("No form service defined for type : %s", workfowType));
		}
		return workflowFormService;
	}

}
