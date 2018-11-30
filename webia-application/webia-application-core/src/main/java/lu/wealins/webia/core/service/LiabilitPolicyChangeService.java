package lu.wealins.webia.core.service;

import lu.wealins.common.dto.liability.services.PolicyChangeDTO;
import lu.wealins.common.dto.liability.services.PolicyChangeRequest;

public interface LiabilitPolicyChangeService {

	PolicyChangeDTO getPolicyChange(Integer workflowItemId);
	
	void cancelByWorkflowItemId(Integer workflowItemId);

	void saveChanges(PolicyChangeRequest policyChangeRequest);
}
