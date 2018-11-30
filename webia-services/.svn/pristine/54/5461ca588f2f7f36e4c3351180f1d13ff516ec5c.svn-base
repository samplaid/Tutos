package lu.wealins.webia.services.ws.rest.impl;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.webia.services.core.service.WorkflowUtilityService;
import lu.wealins.webia.services.ws.rest.WorkflowUtilityServiceRESTService;

@Component
public class WorkflowUtilityServiceRESTImpl implements WorkflowUtilityServiceRESTService {

	@Autowired
	private WorkflowUtilityService workflowUtilityService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getWorkFlowItemId(SecurityContext context, String policyId) {
		return workflowUtilityService.getWorkflowItemId(policyId);
	}

	@Override
	public Collection<Long> getWorkflowItemIds(SecurityContext context, Integer workflowItemType, Integer actionRequired, List<Integer> excludedStatus) {

		return workflowUtilityService.getWorkflowItemIds(workflowItemType, actionRequired, excludedStatus);
	}

}
