package lu.wealins.webia.services.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.webia.services.core.service.CheckWorkflowService;
import lu.wealins.webia.services.ws.rest.CheckWorkflowRESTService;
import lu.wealins.common.dto.webia.services.CheckWorkflowDTO;

@Component
public class CheckWorkflowRESTServiceImpl implements CheckWorkflowRESTService {
	private static final String CHECK_CODE_CANNOT_BE_NULL = "Check code cannot be null.";
	private static final String SECURITY_CONTEXT_CANNOT_BE_NULL = "Security context cannot be null.";

	@Autowired
	private CheckWorkflowService checkWorkflowService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.CheckWorkflowRESTService#getCheckWorkflow(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public CheckWorkflowDTO getCheckWorkflow(SecurityContext context, String checkCode) {
		Assert.notNull(context, SECURITY_CONTEXT_CANNOT_BE_NULL);
		Assert.notNull(checkCode, CHECK_CODE_CANNOT_BE_NULL);
		return checkWorkflowService.getCheckWorkflow(checkCode);
	}

}
