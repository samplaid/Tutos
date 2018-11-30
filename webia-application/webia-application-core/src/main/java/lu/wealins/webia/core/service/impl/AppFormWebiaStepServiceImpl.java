package lu.wealins.webia.core.service.impl;

import static lu.wealins.common.dto.webia.services.enums.OperationStatus.COMPLETED;
import static lu.wealins.common.dto.webia.services.enums.OperationStatus.CREATED;
import static lu.wealins.common.dto.webia.services.enums.OperationStatus.DRAFT;
import static lu.wealins.common.dto.webia.services.enums.OperationStatus.IN_FORCE;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.FOLLOW_UP;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.PREMIUM_INPUT_AND_NAV;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.REGISTRATION;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.VALIDATE_PREMIUM_AND_NAV;

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
public class AppFormWebiaStepServiceImpl extends OperationWebiaStepService<AppFormDTO> {

	@Autowired
	@Qualifier(value = "AppFormLpsUtilityService")
	private CommonLpsUtilityService<AppFormDTO> lpsUtilityService;

	private static final Map<StepTypeDTO, OperationStatus> TRANSITIONS_MAP = Collections.unmodifiableMap(new HashMap<StepTypeDTO, OperationStatus>() {
		{
			put(REGISTRATION, DRAFT);
			put(PREMIUM_INPUT_AND_NAV, CREATED);
			put(VALIDATE_PREMIUM_AND_NAV, IN_FORCE);
			put(FOLLOW_UP, COMPLETED);
		}
	});

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.APP_FORM;
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
