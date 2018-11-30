package lu.wealins.webia.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemActionsDTO;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.webia.ws.rest.LiabilityWorkflowRESTService;

@Component
public class LiabilityWorkflowRESTServiceImpl implements LiabilityWorkflowRESTService {

	@Autowired
	private LiabilityWorkflowService workflowService;

	@Autowired
	private WebiaWorkflowUserService workflowUserService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityWorkflowRESTService#getWorkItemActions(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public WorkflowItemActionsDTO getWorkItemActions(SecurityContext context, String workItemId) {
		return workflowService.getWorkflowItemActions(workItemId, workflowUserService.getUserId(context));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityWorkflowRESTService#getGeneralInformation(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public WorkflowGeneralInformationDTO getGeneralInformation(SecurityContext context, String workflowItemId) {
		return workflowService.getWorkflowGeneralInformation(workflowItemId, workflowUserService.getUserId(context));
	}

}
