package lu.wealins.webia.core.service;

import lu.wealins.common.dto.webia.services.BrokerChangeFormDTO;

public interface LiabilityBrokerChangeFormService {

	BrokerChangeFormDTO initBrokerChangeForm(String policyId, Integer workflowItemId);

}