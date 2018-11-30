package lu.wealins.webia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.BeneficiaryChangeFormDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.service.lps.CommonLpsUtilityService;

@Service
public class BeneficiaryChangeStepServiceImpl extends AmendmentStepServiceImpl<BeneficiaryChangeFormDTO> {

	@Autowired
	@Qualifier(value = "BeneficiaryChangeLpsUtilityService")
	private CommonLpsUtilityService<BeneficiaryChangeFormDTO> lpsUtilityService;

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.BENEFICIARY_CHANGE;
	}

	@Override
	public CommonLpsUtilityService<BeneficiaryChangeFormDTO> getLpsUtilityService() {
		return lpsUtilityService;
	}

}
