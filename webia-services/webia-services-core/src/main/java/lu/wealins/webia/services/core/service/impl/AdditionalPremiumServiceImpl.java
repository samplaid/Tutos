package lu.wealins.webia.services.core.service.impl;

import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.enums.WorkflowType;

@Service(value = "additionalPremiumService")
public class AdditionalPremiumServiceImpl extends AppFormServiceImpl {

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.ADDITIONAL_PREMIUM;
	}
}
