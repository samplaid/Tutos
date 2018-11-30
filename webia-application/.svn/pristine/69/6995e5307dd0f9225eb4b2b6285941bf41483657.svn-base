package lu.wealins.webia.core.service.impl;

import static lu.wealins.common.dto.webia.services.enums.OperationStatus.COMPLETED;
import static lu.wealins.common.dto.webia.services.enums.OperationStatus.DRAFT;
import static lu.wealins.common.dto.webia.services.enums.OperationStatus.IN_FORCE;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.NEW;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.SENDING;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.VALIDATE_ADDITIONAL_PREMIUM;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.OperationStatus;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.service.lps.CommonLpsUtilityService;

@Service
public class AdditionalPremiumWebiaStepServiceImpl extends OperationWebiaStepService<AppFormDTO> {

	@Autowired
	@Qualifier(value = "AdditionalPremiumLpsUtilityService")
	private CommonLpsUtilityService<AppFormDTO> lpsUtilityService;

	private static final Map<StepTypeDTO, OperationStatus> TRANSITIONS_MAP = Collections.unmodifiableMap(new HashMap<StepTypeDTO, OperationStatus>() {
		{
			put(NEW, DRAFT);
			put(VALIDATE_ADDITIONAL_PREMIUM, IN_FORCE);
			put(SENDING, COMPLETED);
		}
	});

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.ADDITIONAL_PREMIUM;
	}

	@Override
	protected OperationStatus getNextOperationStatus(StepDTO step) {
		StepTypeDTO stepType = StepTypeDTO.getStepType(step.getStepWorkflow());
		return TRANSITIONS_MAP.get(stepType);
	}

	@Override
	public CommonLpsUtilityService<AppFormDTO> getLpsUtilityService() {
		return lpsUtilityService;
	}


}
