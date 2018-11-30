package lu.wealins.webia.services.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.StepCommentDTO;
import lu.wealins.common.dto.webia.services.StepCommentRequest;
import lu.wealins.webia.services.core.service.StepCommentService;
import lu.wealins.webia.services.ws.rest.StepCommentRESTService;

@Component
public class StepCommentRESTServiceImpl implements StepCommentRESTService {

	@Autowired
	private StepCommentService stepCommentService;


	@Override
	public Collection<StepCommentDTO> getStepComments(SecurityContext context, Long workflowItemId) {
		return stepCommentService.getStepComments(workflowItemId);
	}

	@Override
	public StepCommentDTO addStepComment(SecurityContext context, StepCommentRequest request) {
		return stepCommentService.addStepComment(request);
	}

	@Override
	public StepCommentDTO updateStepComment(SecurityContext context, StepCommentRequest request) {
		return stepCommentService.updateStepComment(request);
	}
}
