package lu.wealins.webia.core.service.impl;

import java.util.Collection;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.StepCommentDTO;
import lu.wealins.common.dto.webia.services.StepCommentRequest;
import lu.wealins.webia.core.service.WebiaStepCommentService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class WebiaStepCommentServiceImpl implements WebiaStepCommentService {

	private static final String WEBIA_STEP_LOAD = "webia/stepComment/";
	private static final String WEBIA_STEP_ADD = "webia/stepComment/add";
	private static final String WEBIA_STEP_UPDATE = "webia/stepComment/update";

	@Autowired
	private RestClientUtils restClientUtils;


	@Override
	public Collection<StepCommentDTO> getStepComments(Long workflowItemId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("workflowItemId", workflowItemId);
		Collection<StepCommentDTO> stepComments = restClientUtils.get(WEBIA_STEP_LOAD, workflowItemId.toString(), params, new GenericType<Collection<StepCommentDTO>>() {
		});
		return stepComments;
	}

	@Override
	public StepCommentDTO addStepComment(StepCommentRequest request) {
		StepCommentDTO stepComment = restClientUtils.post(WEBIA_STEP_ADD, request, StepCommentDTO.class);
		return stepComment;
	}

	@Override
	public StepCommentDTO updateStepComment(StepCommentRequest request) {
		StepCommentDTO stepComment = restClientUtils.post(WEBIA_STEP_UPDATE, request, StepCommentDTO.class);
		return stepComment;
	}
}
