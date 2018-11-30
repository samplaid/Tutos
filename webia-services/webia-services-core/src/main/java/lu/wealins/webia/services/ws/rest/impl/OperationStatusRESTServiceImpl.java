package lu.wealins.webia.services.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.UpdateOperationStatusRequest;
import lu.wealins.webia.services.core.service.OperationStatusService;
import lu.wealins.webia.services.ws.rest.OperationStatusRESTService;

@Component
public class OperationStatusRESTServiceImpl implements OperationStatusRESTService {

	@Autowired
	private OperationStatusService operationStatusService;

	@Override
	public Boolean updateStatus(SecurityContext context, UpdateOperationStatusRequest request) {
		return operationStatusService.updateStatus(request);
	}

}
