package lu.wealins.webia.services.core.service.impl;

import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.BeneficiaryChangeFormDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;

@Service
public class BeneficiaryChangeFormServiceImpl extends AmendmentWorkflowServiceImpl<BeneficiaryChangeFormDTO> {

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.BENEFICIARY_CHANGE;
	}

}
