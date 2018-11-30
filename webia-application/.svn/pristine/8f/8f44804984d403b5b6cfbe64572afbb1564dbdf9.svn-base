package lu.wealins.webia.ws.rest.impl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.StepCommentDTO;
import lu.wealins.common.dto.webia.services.StepCommentRequest;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.WebiaStepCommentService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.webia.core.utils.DateUtils;
import lu.wealins.webia.ws.rest.StepCommentRESTService;

@Component
public class StepCommentRESTServiceImpl implements StepCommentRESTService {

	@Autowired
	private WebiaStepCommentService stepCommentService;
	
	@Autowired
	private LiabilityWorkflowService liabilityWorkflowService;
	
	@Autowired
	private WebiaWorkflowUserService workflowUserService;
	
	private static final String DUE_DATE_CANNOT_BE_PAST = "The due date can neither be set in the past nor be null";
	private final Format dateFormatter = new SimpleDateFormat("MM.dd.yyyy");

	
	@Override
	public Collection<StepCommentDTO> getStepComments(SecurityContext context, Long workflowItemId){
		return stepCommentService.getStepComments(workflowItemId);
	}

	@Override
	public StepCommentDTO addStepComment(SecurityContext context, StepCommentRequest request) throws Exception{
		Assert.notNull(request, "The Step Comment Request cannot be null.");
		Date nextDueDate = request.getNextDueDate();
		if (nextDueDate != null) {
			Date now = new Date();
			if (DateUtils.compareDayDates(now, nextDueDate) == 1) {
				throw new Exception(DUE_DATE_CANNOT_BE_PAST);
			}
			String dueDateStr = dateFormatter.format(nextDueDate);
			String userId = workflowUserService.getUserId(context);	
			liabilityWorkflowService.updateDueDateWorkflowItem(request.getWorkflowItemId().toString(), dueDateStr, userId);
		}

		StepCommentDTO lastComment = stepCommentService.getStepComments(request.getWorkflowItemId()).stream().sorted((o1, o2) -> o2.getStepCommentId().compareTo(o1.getStepCommentId())).findFirst()
				.orElse(null);

		if (lastComment != null && lastComment.getStep().getStepId().equals(request.getStepId()) && workflowUserService.getUserId(context).equalsIgnoreCase(lastComment.getUpdateUser())) {
			request.setStepCommentId(lastComment.getStepCommentId());
			return stepCommentService.updateStepComment(request);
		}

		return stepCommentService.addStepComment(request);
	}
}
