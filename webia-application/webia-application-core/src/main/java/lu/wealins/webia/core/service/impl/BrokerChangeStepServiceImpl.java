package lu.wealins.webia.core.service.impl;

import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.BeneficiaryChangeFormDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;

@Service
public class BrokerChangeStepServiceImpl extends AmendmentStepServiceImpl<BeneficiaryChangeFormDTO> {

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.BROKER_CHANGE;
	}

}
