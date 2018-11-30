package lu.wealins.webia.services.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.webia.services.core.service.WorkflowQueueService;
import lu.wealins.webia.services.ws.rest.WorkflowQueueRESTService;
import lu.wealins.common.dto.webia.services.WorkflowQueueDTO;

@Component
public class WorkflowQueueRESTServiceImpl implements WorkflowQueueRESTService {

	@Autowired
	private WorkflowQueueService workflowQueueService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.WorkflowQueueRESTService#getWorkflowQueue(javax.ws.rs.core.SecurityContext, java.lang.Integer)
	 */
	@Override
	public WorkflowQueueDTO getWorkflowQueue(SecurityContext context, Integer id) {
		return workflowQueueService.getWorkflowQueue(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.WorkflowQueueRESTService#isAssignTo(javax.ws.rs.core.SecurityContext, java.lang.Integer, java.lang.String)
	 */
	@Override
	public boolean isAssignTo(SecurityContext context, Integer workflowQueueId, String userId) {
		return workflowQueueService.isAssignTo(workflowQueueId, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.WorkflowQueueRESTService#hasPersonalQueue(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public boolean hasPersonalQueue(SecurityContext context, String usrId) {
		return workflowQueueService.hasPersonalQueue(usrId);
	}
}
