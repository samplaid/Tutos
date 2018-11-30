package lu.wealins.webia.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.enums.WorkflowStatus;
import lu.wealins.common.dto.webia.services.RelaunchStepRequest;
import lu.wealins.common.dto.webia.services.RelaunchStepResponse;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.webia.core.service.RelaunchStepUtilityService;
import lu.wealins.webia.core.service.WebiaWorkflowUtilityService;
import lu.wealins.webia.ws.rest.WebiaStepRESTService;
import lu.wealins.webia.ws.rest.impl.exception.StepException;

@Service
public class RelaunchStepUtilityServiceImpl implements RelaunchStepUtilityService {

	private static final String AWAITING_VALUATION_STEP_REQUEST_CANNOT_BE_NULL = "Awaiting valuation step request cannot be null.";

	@Autowired
	private WebiaStepRESTService webiaStepService;

	@Autowired
	private WebiaWorkflowUtilityService workflowUtilityService;

	protected final Logger log = LoggerFactory.getLogger(RelaunchStepUtilityServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.AwaitingValuationUtilityService#relaunchAwaitingValuation(javax.ws.rs.core.SecurityContext, lu.wealins.webia.ws.rest.dto.AwaitingValuationRequest)
	 */
	@Override
	public RelaunchStepResponse relaunchStep(SecurityContext context, RelaunchStepRequest relaunchStepRequest) {
		Assert.notNull(relaunchStepRequest, AWAITING_VALUATION_STEP_REQUEST_CANNOT_BE_NULL);

		List<String> messages = new ArrayList<>();
		if (relaunchStepRequest.getWorkflowItemId() != null) {
			relaunchStep(context, relaunchStepRequest.getActionOther(), relaunchStepRequest.getWorkflowItemId(), messages);
		} else {
			List<Integer> excludedStatus = Arrays.asList(WorkflowStatus.ENDED.getValue(), WorkflowStatus.COMPLETED.getValue());

			Collection<Long> workflowItemIds = workflowUtilityService.getWorkflowItemIds(relaunchStepRequest.getWorkflowItemType(), relaunchStepRequest.getActionRequired(),
					excludedStatus);

			for (Long workflowItemId : workflowItemIds) {
				relaunchStep(context, relaunchStepRequest.getActionOther(), workflowItemId, messages);
			}
		}

		return createRelaunchStepResponse(messages);
	}

	@SuppressWarnings("boxing")
	private void relaunchStep(SecurityContext context, String workflowAction, Long workflowItemId, List<String> messages) {
		try {

			StepDTO step = webiaStepService.complete(context, workflowItemId.intValue());
			formatMessages(workflowAction, workflowItemId, messages, step.getErrors());
		} catch (StepException e) {
			formatMessages(workflowAction, workflowItemId, messages, e.getErrors());
		} catch (Exception e) {
			log.error("Cannot relaunch " + workflowAction + " action for the workflow item id = " + workflowItemId + " : ", e);
			String message = "[ERROR] - " + workflowAction + " cannot be relaunch for the workflow item id = " + workflowItemId + " (see logs).";

			if (e.getMessage() != null) {
				message += e.getMessage();
			}
			messages.add(message);
		}

	}

	private void formatMessages(String workflowAction, Long workflowItemId, List<String> messages, Collection<String> errors) {
		if (CollectionUtils.isEmpty(errors)) {
			messages.add("[SUCCESS] - " + workflowAction + " has been relaunched successfuly for the workflow item id = " + workflowItemId + ".");
		} else {
			messages.addAll(errors);
			log.error("Cannot relaunch " + workflowAction + " action for the workflow item id = " + workflowItemId + " : ");
			errors.forEach(x -> log.error("  -- > " + x));
		}
	}

	private RelaunchStepResponse createRelaunchStepResponse(List<String> messages) {
		RelaunchStepResponse response = new RelaunchStepResponse();
		response.setMessages(messages);
		return response;
	}

}
