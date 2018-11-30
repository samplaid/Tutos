package lu.wealins.webia.services.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.webia.services.core.service.RejectDataService;
import lu.wealins.webia.services.ws.rest.RejectDataRESTService;
import lu.wealins.common.dto.webia.services.RejectDataDTO;

@Component
public class RejectDataRESTServiceImpl implements RejectDataRESTService {

	private static final String STEP_ID_CANNOT_BE_NULL = "step id cannot be null.";
	private static final String WORKFLOW_ITEM_ID_CANNOT_BE_NULL = "Workflow item id cannot be null.";
	private static final String SECURITY_CONTEXT_CANNOT_BE_NULL = "Security context cannot be null.";

	@Autowired
	private RejectDataService rejectDataService;

	@Override
	public Collection<RejectDataDTO> getRejectData(SecurityContext context, Integer workflowItemId) {
		Assert.notNull(context, SECURITY_CONTEXT_CANNOT_BE_NULL);
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);

		return rejectDataService.getRejectData(workflowItemId);
	}

	@Override
	public Collection<RejectDataDTO> getRejectData(SecurityContext context, Integer workflowItemId, Integer stepId) {
		Assert.notNull(context, SECURITY_CONTEXT_CANNOT_BE_NULL);
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);
		Assert.notNull(stepId, STEP_ID_CANNOT_BE_NULL);

		return rejectDataService.getRejectData(workflowItemId, stepId);
	}

	@Override
	public RejectDataDTO save(SecurityContext context, RejectDataDTO rejectData) {
		Assert.notNull(context);
		Assert.notNull(rejectData);

		return rejectDataService.save(rejectData);
	}

}
