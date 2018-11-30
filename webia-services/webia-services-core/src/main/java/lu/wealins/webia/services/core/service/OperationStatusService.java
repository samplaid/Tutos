package lu.wealins.webia.services.core.service;

import lu.wealins.common.dto.webia.services.UpdateOperationStatusRequest;

public interface OperationStatusService {

	/**
	 * Update the operation status.
	 * 
	 * @param request the update operation status request.
	 */
	Boolean updateStatus(UpdateOperationStatusRequest request);

}
