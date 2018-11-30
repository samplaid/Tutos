package lu.wealins.webia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.UpdateOperationStatusRequest;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class WebiaOperationStatusServiceImpl implements WebiaOperationStatusService {

	private static final String WEBIA_STATUS = "webia/operationStatus/";
	private static final String UPDATE = "update";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public Boolean updateStatus(UpdateOperationStatusRequest request) {
		return restClientUtils.post(WEBIA_STATUS + UPDATE, request, Boolean.class);
	}

}
