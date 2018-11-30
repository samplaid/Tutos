package lu.wealins.webia.core.service.impl;

import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.enums.WorkflowType;

@Service
public class SurrenderWebiaStepServiceImpl extends WithdrawalWebiaStepServiceImpl {

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.SURRENDER;
	}

}
