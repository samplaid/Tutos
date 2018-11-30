package lu.wealins.webia.core.service.impl;

import static lu.wealins.common.dto.webia.services.enums.OperationStatus.COMPLETED;
import static lu.wealins.common.dto.webia.services.enums.OperationStatus.DRAFT;
import static lu.wealins.common.dto.webia.services.enums.OperationStatus.IN_FORCE;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.AWAITING_PUT_IN_FORCE;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.NEW;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.SENDING;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.OperationStatus;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.service.lps.CommonLpsUtilityService;

@Service
public class WithdrawalWebiaStepServiceImpl extends OperationWebiaStepService<TransactionFormDTO> {

	@Autowired
	@Qualifier(value = "WithdrawalLpsUtilityService")
	private CommonLpsUtilityService<TransactionFormDTO> lpsUtilityService;

	private static final Map<StepTypeDTO, OperationStatus> TRANSITIONS_MAP = Collections.unmodifiableMap(new HashMap<StepTypeDTO, OperationStatus>() {
		{
			put(NEW, DRAFT);
			put(AWAITING_PUT_IN_FORCE, IN_FORCE);
			put(SENDING, COMPLETED);
		}
	});

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.WITHDRAWAL;
	}

	@Override
	protected OperationStatus getNextOperationStatus(StepDTO step) {
		StepTypeDTO stepType = StepTypeDTO.getStepType(step.getStepWorkflow());
		return TRANSITIONS_MAP.get(stepType);
	}

	@Override
	public CommonLpsUtilityService<TransactionFormDTO> getLpsUtilityService() {
		return lpsUtilityService;
	}

}
