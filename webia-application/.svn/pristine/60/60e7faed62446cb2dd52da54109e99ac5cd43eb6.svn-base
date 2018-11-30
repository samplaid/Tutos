package lu.wealins.webia.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.webia.core.service.CheckStepService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.webia.ws.rest.CheckStepRESTService;

@Component
public class CheckStepRESTServiceImpl implements CheckStepRESTService {

	@Autowired
	private CheckStepService checkStepService;

	@Autowired
	private WebiaWorkflowUserService workflowUserService;

	@Override
	public Collection<CheckStepDTO> getCheckSteps(SecurityContext context, Integer workflowItemId, String stepWorkflow) {

		Assert.notNull(workflowItemId, "the workflow item id can't be null");
		
		String userId = workflowUserService.getUserId(context);

		return checkStepService.getCheckSteps(userId, workflowItemId, stepWorkflow);
	}
}
