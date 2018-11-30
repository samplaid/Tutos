package lu.wealins.webia.core.service.impl;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.PolicyChangeDTO;
import lu.wealins.common.dto.liability.services.PolicyChangeRequest;
import lu.wealins.webia.core.service.LiabilitPolicyChangeService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilitPolicyChangeServiceImpl implements LiabilitPolicyChangeService {

	private static final String LIABILITY_POLICY_CHANGE = "liability/policyChange/";
	private static final String CANCEL_BY_WORKFLOW_ITEM_ID = "cancelByworkflowItemId";
	private static final String SAVE_CHANGES = "saveChanges";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public PolicyChangeDTO getPolicyChange(Integer workflowItemId) {
		if (workflowItemId == null) {
			return null;
		}

		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();

		queryParams.add("workflowItemId", workflowItemId);

		return restClientUtils.get(LIABILITY_POLICY_CHANGE, "", queryParams, PolicyChangeDTO.class);
	}
	
	@Override
	public void cancelByWorkflowItemId(Integer workflowItemId) {
		Assert.notNull(workflowItemId);
		
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.add("workflowItemId", workflowItemId);

		restClientUtils.get(LIABILITY_POLICY_CHANGE, CANCEL_BY_WORKFLOW_ITEM_ID, queryParams, Void.class);
	}

	@Override
	public void saveChanges(PolicyChangeRequest policyChangeRequest) {
		Assert.notNull(policyChangeRequest);

		restClientUtils.post(LIABILITY_POLICY_CHANGE + SAVE_CHANGES, policyChangeRequest, Boolean.class);
	}

}
