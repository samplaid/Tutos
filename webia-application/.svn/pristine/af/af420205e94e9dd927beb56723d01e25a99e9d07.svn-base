package lu.wealins.webia.core.service.impl;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.UpdateAppFormCoverageRequest;
import lu.wealins.webia.core.service.WebiaAppFormService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class WebiaAppFormServiceImpl implements WebiaAppFormService {

	private static final String WEBIA_APP_FORM = "webia/appForm/";
	private static final String RECREATE = "recreate";
	private static final String UPDATE_COVERAGE = "updateCoverage";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public Boolean updateCoverage(Integer formId, Integer coverage) {
		UpdateAppFormCoverageRequest request = new UpdateAppFormCoverageRequest();

		request.setFormId(formId);
		request.setCoverage(coverage);

		return restClientUtils.post(WEBIA_APP_FORM + UPDATE_COVERAGE, request, Boolean.class);
	}

	@Override
	public AppFormDTO recreate(Integer workflowItemId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();

		params.add("workflowItemId", workflowItemId);

		return restClientUtils.get(WEBIA_APP_FORM, RECREATE, params, AppFormDTO.class);
	}

}
