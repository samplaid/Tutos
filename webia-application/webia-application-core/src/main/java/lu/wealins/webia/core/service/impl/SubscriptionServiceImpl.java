package lu.wealins.webia.core.service.impl;

import java.util.Arrays;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.PolicyRecreateResponse;
import lu.wealins.common.dto.liability.services.RecreatePolicyDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.UpdateAppFormPolicyDTO;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.MetadataService;
import lu.wealins.webia.core.service.SubscriptionService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	private static final String WORKFLOW_ID_PARAM = "workflowItemId";
	private static final String LIABILITY_RECREATE_POLICY_ENDPOINT = "liability/policy/recreate";
	private static final String WEBIA_UPDATE_FORM_POLICY = "webia/appForm/updatePolicy";
	private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private MetadataService metadataService;

	@Autowired
	private LiabilityWorkflowService workflowService;

	@Override
	public PolicyRecreateResponse recreatePolicy(RecreatePolicyDTO recreatePolicyDTO, String userId) {
		Long workflowItemId = recreatePolicyDTO.getWorkflowItemId();
		Integer formId = recreatePolicyDTO.getFormId();

		PolicyRecreateResponse newPolicyResponse = getNewPolicyId(workflowItemId);

		String newPolicyId = newPolicyResponse.getPolicyId();

		savePolicyMetadata(workflowItemId, userId, newPolicyId);
		
		updatePolicyIdForm(formId, newPolicyId);

		submitRecreationAction(workflowItemId, userId);

		return newPolicyResponse;
	}

	private void submitRecreationAction(Long workflowItemId, String userId) {
		LOGGER.info("Submitting the recreation action to elissia for workflow {}", workflowItemId);

		workflowService.recreateWorkflow(workflowItemId.intValue(), userId);
	}

	private PolicyRecreateResponse getNewPolicyId(Long workflowItemId) {
		LOGGER.info("Recreating policy for the workflow item id {}", workflowItemId);

		MultivaluedMap<String, Object> params = getParams(workflowItemId);

		return restClientUtils.get(LIABILITY_RECREATE_POLICY_ENDPOINT, "", params, PolicyRecreateResponse.class);
	}

	private void updatePolicyIdForm(Integer formId, String newPolicyId) {
		LOGGER.info("Updating the policy id {} in the app form having id {}", newPolicyId, formId);
		
		UpdateAppFormPolicyDTO dto = new UpdateAppFormPolicyDTO();
		dto.setFormId(formId);
		dto.setPolicyId(newPolicyId);

		restClientUtils.post(WEBIA_UPDATE_FORM_POLICY, dto, AppFormDTO.class);
	}

	private void savePolicyMetadata(Long workflowItemId, String userId, String policyId) {
		LOGGER.info("Updating the metadata policy id for workflow item id {} with value {}", workflowItemId, policyId);

		MetadataDTO policyIdMetadata = metadataService.createMetadata(Metadata.POLICY_ID.getMetadata(), policyId);
		WorkflowItemDataDTO policyPostMetadata = metadataService.createWorkflowItemData(workflowItemId, userId, policyIdMetadata);
		workflowService.saveMetada(policyPostMetadata);
	}

	private MultivaluedMap<String, Object> getParams(Long workflowItemId) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.put(WORKFLOW_ID_PARAM, Arrays.asList(workflowItemId));
		
		return queryParams;
	}
}
