package lu.wealins.webia.services.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.WorkflowGroupDTO;
import lu.wealins.webia.services.core.service.WorkflowGroupService;
import lu.wealins.webia.services.ws.rest.WorkflowGroupRESTService;

@Component
public class WorkflowGroupRESTServiceImpl implements WorkflowGroupRESTService {

	@Autowired
	private WorkflowGroupService workflowGroupService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.WorkflowGroupRESTService#getWorkflowGroup(javax.ws.rs.core.SecurityContext, java.lang.Integer)
	 */
	@Override
	public WorkflowGroupDTO getWorkflowGroup(SecurityContext context, Integer id) {
		return workflowGroupService.getWorkflowGroup(id);
	}

	@Override
	public Collection<WorkflowGroupDTO> getWorkflowGroupsByUser(SecurityContext context, String usrId) {
		return workflowGroupService.getWorkflowGroups(usrId);
	}
}
