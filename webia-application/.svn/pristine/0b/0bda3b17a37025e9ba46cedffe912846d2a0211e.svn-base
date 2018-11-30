package lu.wealins.webia.core.service.impl;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.webia.core.service.WebiaFormDataService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Component
public class WebiaFormDataServiceImpl implements WebiaFormDataService {

	private static final String WEBIA_FORM_DATA = "webia/formData/";
	private static final String INIT = "init";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public <T extends FormDataDTO> T initFormData(Integer workflowItemId, Integer workflowItemTypeId, Class<T> type) {

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("workFlowItemId", workflowItemId);
		params.add("workflowItemTypeId", workflowItemTypeId);

		return restClientUtils.get(WEBIA_FORM_DATA, INIT, params, type);
	}

}
