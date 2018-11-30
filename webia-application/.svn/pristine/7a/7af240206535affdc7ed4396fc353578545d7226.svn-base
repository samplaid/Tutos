package lu.wealins.webia.core.service.impl;

import java.util.Collection;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.webia.services.RejectDataDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.WorkflowUserDTO;
import lu.wealins.webia.core.service.WebiaRejectDataService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Component
public class WebiaRejectDataServiceImpl implements WebiaRejectDataService {

	// root
	private static final String WEBIA_REJECT_DATA = "webia/rejectData/";
	// actions
	private static final String LOAD = "load";
	private static final String LOAD_BY_STEP_ID = "loadByStepId";
	private static final String SAVE = "save";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public Collection<RejectDataDTO> getRejectData(Integer workflowItemId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("workflowItemId", workflowItemId);
		return restClientUtils.get(WEBIA_REJECT_DATA, LOAD, params, new GenericType<Collection<RejectDataDTO>>() {
		});
	}

	@Override
	public Collection<RejectDataDTO> getRejectData(Integer workflowItemId, Integer stepId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("workflowItemId", workflowItemId);
		params.add("stepId", stepId);
		return restClientUtils.get(WEBIA_REJECT_DATA, LOAD_BY_STEP_ID, params, new GenericType<Collection<RejectDataDTO>>() {
		});
	}

	@Override
	public RejectDataDTO createRejectData(StepDTO step, WorkflowUserDTO workflowUser) {
		RejectDataDTO rejectData = new RejectDataDTO(step.getWorkflowItemId(), step.getStepId());
		CharSequence headStr = "by " + workflowUser.getName0() + " (" + workflowUser.getLoginId() + ") : ";
		if (StringUtils.hasText(step.getRejectReason()) && step.getRejectReason().contains(headStr)) {
			rejectData.setRejectComment(step.getRejectReason());
		} else {
			rejectData.setRejectComment(headStr + step.getRejectReason());
		}

		return restClientUtils.post(WEBIA_REJECT_DATA + SAVE, rejectData, RejectDataDTO.class);
	}
}
