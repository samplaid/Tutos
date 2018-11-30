package lu.wealins.webia.core.service.impl;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.CheckWorkflowDTO;
import lu.wealins.webia.core.service.WebiaCheckWorkflowService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Component
public class WebiaCheckWorkflowServiceImpl implements WebiaCheckWorkflowService {

	private static final String LOAD = "load";
	private static final String WEBIA_LOAD_CHECK_DATA = "webia/checkWorkflow/";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public CheckWorkflowDTO getCheckWorkflow(String checkCode) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();

		params.add("checkCode", checkCode);

		return restClientUtils.get(WEBIA_LOAD_CHECK_DATA, LOAD, params, CheckWorkflowDTO.class);
	}

}
