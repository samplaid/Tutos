package lu.wealins.webia.services.core.service;

import lu.wealins.common.dto.webia.services.enums.OperationStatus;
import lu.wealins.webia.services.core.service.impl.form.data.WorkflowItemSpecific;

public interface UpdatableStatusService extends WorkflowItemSpecific {

	/**
	 * Update the operation status.
	 * 
	 * @param request the update operation status request.
	 */
	Boolean updateStatus(Integer workflowItemId, OperationStatus status);

}
