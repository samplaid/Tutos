package lu.wealins.webia.services.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.common.security.SecurityContextHelper;
import lu.wealins.webia.services.core.service.WorkflowUserService;
import lu.wealins.webia.services.ws.rest.WorkflowUserRESTService;
import lu.wealins.common.dto.webia.services.WorkflowUserDTO;

@Component
public class WorkflowUserRESTServiceImpl implements WorkflowUserRESTService {

	@Autowired
	private WorkflowUserService workflowUserService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.WorkflowUserRESTService#getWorkflowUser(javax.ws.rs.core.SecurityContext, java.lang.Integer)
	 */
	@Override
	public WorkflowUserDTO getWorkflowUser(SecurityContext context, String usrId) {
		return workflowUserService.getWorkflowUser(usrId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.WorkflowUserRESTService#getWorkflowUser(javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public WorkflowUserDTO getWorkflowUser(SecurityContext context) {
		Assert.notNull(context, "Security context cannot be null");

		return workflowUserService.getWorkflowUserWithLogin(SecurityContextHelper.getPreferredUsername(context));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.WorkflowUserRESTService#getWorkflowUserWithLogin(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public WorkflowUserDTO getWorkflowUserWithLogin(SecurityContext context, String login) {
		return workflowUserService.getWorkflowUserWithLogin(login);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.WorkflowUserRESTService#getWorkflowUserWithToken(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public WorkflowUserDTO getWorkflowUserWithToken(SecurityContext context, String userToken) {
		return workflowUserService.getWorkflowUserWithToken(userToken);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.WorkflowUserRESTService#getWorkflowUsers(javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public Collection<WorkflowUserDTO> getWorkflowUsers(SecurityContext context) {
		return workflowUserService.getWorkflowUsers();
	}

}