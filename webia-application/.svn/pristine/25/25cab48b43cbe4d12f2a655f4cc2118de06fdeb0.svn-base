package lu.wealins.webia.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.ApplicationParameterDTO;
import lu.wealins.common.dto.webia.services.WorkflowGroupDTO;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.service.WebiaWorkflowGroupService;
import lu.wealins.webia.ws.rest.LiabilityWorkflowGroupRESTService;

@Service
public class LiabilityWorkflowGroupRESTServiceImpl implements LiabilityWorkflowGroupRESTService {

	private static final String CPS_ID_GROUP = "CPS_ID_GROUP";

	@Autowired
	private WebiaWorkflowGroupService workflowGroupService;

	@Autowired
	private WebiaApplicationParameterService applicationParameterService;

	@Override
	public WorkflowGroupDTO getCPSWorkflowGroup(SecurityContext context) {

		ApplicationParameterDTO applicationParameter = applicationParameterService.getApplicationParameter(CPS_ID_GROUP);

		if (applicationParameter == null) {
			throw new IllegalStateException("No application parameter " + CPS_ID_GROUP + ".");
		}

		Integer workflowGroupId = Integer.valueOf(applicationParameter.getValue());

		// TODO Auto-generated method stub
		return workflowGroupService.getWorkflowGroup(workflowGroupId);
	}

}
