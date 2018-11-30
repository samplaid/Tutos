package lu.wealins.webia.core.service.impl;

import java.util.Collection;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.WorkflowGroupDTO;
import lu.wealins.webia.core.service.WebiaWorkflowGroupService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class WebiaWorkflowGroupServiceImpl implements WebiaWorkflowGroupService {

	private static final String WEBIA_LOAD_WORKFLOW_GROUP = "webia/workflowGroup/";
	private static final String USER_ID_QUERY_PARAM = "userId";

	@Autowired
	private RestClientUtils restClientUtils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaWorkflowGroupService#getWorkflowGroup(java.lang.Integer)
	 */
	@Override
	public WorkflowGroupDTO getWorkflowGroup(Integer workflowGroupId) {
		Assert.notNull(workflowGroupId, "Workflow group id cannot be null");

		return restClientUtils.get(WEBIA_LOAD_WORKFLOW_GROUP, workflowGroupId.toString(), WorkflowGroupDTO.class);
	}

	@Override
	public Collection<WorkflowGroupDTO> getWorkflowGroupsByUser(String userId) {
		Assert.notNull(userId, "User id cannot be null");

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.putSingle(USER_ID_QUERY_PARAM, userId);

		return restClientUtils.get(WEBIA_LOAD_WORKFLOW_GROUP, "", params, new GenericType<Collection<WorkflowGroupDTO>>() {
		});
	}

}
