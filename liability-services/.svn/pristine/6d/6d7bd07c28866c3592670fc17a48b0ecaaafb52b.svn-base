package lu.wealins.liability.services.core.business;

import java.util.Collection;

import lu.wealins.common.dto.liability.services.PolicyChangeDTO;
import lu.wealins.common.dto.liability.services.PolicyChangeRequest;

public interface PolicyChangeService {
	
	Boolean saveChanges(PolicyChangeRequest policyChangeRequest);
	
	void cancelByworkflowItemId( Integer workflowItemId);

	PolicyChangeDTO getPolicyChange(Integer workflowItemId);
	
	Collection<PolicyChangeDTO> getPolicyChanges(String polId);
}
