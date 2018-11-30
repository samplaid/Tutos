package lu.wealins.webia.core.service.impl;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.WebiaWorkflowQueueService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.webia.services.WorkflowGroupDTO;
import lu.wealins.common.dto.webia.services.WorkflowQueueDTO;
import lu.wealins.common.dto.webia.services.WorkflowUserDTO;

@Service
public class WebiaWorkflowQueueServiceImpl implements WebiaWorkflowQueueService {

	private static final String WORKFLOW_QUEUE_CANNOT_BE_NULL = "Workflow queue cannot be null.";
	private static final String USER_ID_CANNOT_BE_NULL = "User id cannot be null.";
	private static final String WEBIA_LOAD_WORKFLOW_QUEUE = "webia/workflowQueue/";
	private static final String WEBIA_IS_ASSIGNED_TO_WORKFLOW_QUEUE = "webia/workflowQueue/isAssignTo/";
	private static final String WEBIA_HAS_PERSONAL_QUEUE = "webia/workflowQueue/hasPersonalQueue/";
	@Autowired
	private LiabilityWorkflowService workflowService;

	@Autowired
	private RestClientUtils restClientUtils;


	@Override
	public String getWorkflowQueueId(String workflowItemId, String usrId) {
		Assert.notNull(workflowItemId);
		WorkflowGeneralInformationDTO workflowGeneralInformation = workflowService.getWorkflowGeneralInformation(workflowItemId, usrId);

		return workflowGeneralInformation.getQueueId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaWorkflowQueueService#getWorkflowQueue(java.lang.String)
	 */
	@Override
	public WorkflowQueueDTO getWorkflowQueue(String workflowQueueId) {
		Assert.notNull(workflowQueueId, WORKFLOW_QUEUE_CANNOT_BE_NULL);

		return restClientUtils.get(WEBIA_LOAD_WORKFLOW_QUEUE, workflowQueueId, WorkflowQueueDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaWorkflowQueueService#getUserNameOrUserGroupName(java.lang.String)
	 */
	@Override
	public String getUserNameOrUserGroupName(String workflowQueueId) {
		WorkflowQueueDTO workflowQueue = getWorkflowQueue(workflowQueueId);

		if (workflowQueue != null) {
			WorkflowUserDTO user = workflowQueue.getUser();
			if (user != null) {
				return user.getName0();
			}
			WorkflowGroupDTO userGroup = workflowQueue.getUserGroup();
			if (userGroup != null) {
				return userGroup.getName0();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaWorkflowQueueRESTService#isAssignTo(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean isAssignTo(String workflowQueueId, String userId) {
		Assert.notNull(workflowQueueId, WORKFLOW_QUEUE_CANNOT_BE_NULL);
		Assert.notNull(userId, USER_ID_CANNOT_BE_NULL);

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("userId", userId);

		return restClientUtils.get(WEBIA_IS_ASSIGNED_TO_WORKFLOW_QUEUE, workflowQueueId, params, Boolean.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaWorkflowQueueService#hasPersonalQueue(java.lang.String)
	 */
	@Override
	public Boolean hasPersonalQueue(String userId) {
		Assert.notNull(userId, USER_ID_CANNOT_BE_NULL);

		return restClientUtils.get(WEBIA_HAS_PERSONAL_QUEUE, userId, Boolean.class);
	}

}
