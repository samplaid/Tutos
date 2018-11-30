package lu.wealins.webia.services.core.service;

import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.webia.services.ws.rest.CheckStepRESTService;
import lu.wealins.common.dto.webia.services.CheckStepDTO;

@Component
public class CheckStepRESTServiceImpl implements CheckStepRESTService {

	private static final String WORKFLOW_ITEM_ID_CANNOT_BE_NULL = "Workflow item id cannot be null.";
	private static final String WORKFLOW_ITEM_TYPE_ID_CANNOT_BE_NULL = "Workflow item type id cannot be null.";
	private static final String SECURITY_CONTEXT_CANNOT_BE_NULL = "Security context cannot be null.";

	@Autowired
	private CheckStepService checkStepService;
	
	@Autowired WorkflowUtilityService workflowUtilityService;

	@Override
	public Collection<CheckStepDTO> getCommentsHistory(SecurityContext context, Integer workflowItemId, Integer workflowItemTypeId) {
		Assert.notNull(context, SECURITY_CONTEXT_CANNOT_BE_NULL);
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);
		Assert.notNull(workflowItemTypeId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);
		
		return checkStepService.getCommentsHistory(workflowItemId, workflowItemTypeId);

	}

}
