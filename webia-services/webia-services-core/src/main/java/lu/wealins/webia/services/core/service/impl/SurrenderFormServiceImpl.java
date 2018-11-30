package lu.wealins.webia.services.core.service.impl;

import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.enums.WorkflowType;

@Service(value = "SurrenderFormService")
public class SurrenderFormServiceImpl extends WithdrawalFormServiceImpl {

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.SURRENDER;
	}

}
