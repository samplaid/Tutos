package lu.wealins.webia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.webia.services.AmendmentFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.core.service.ScoreUtilityService;
import lu.wealins.webia.core.service.WebiaStepServiceByOperation;

public abstract class AmendmentStepServiceImpl<T extends AmendmentFormDTO> implements WebiaStepServiceByOperation<T> {

	@Autowired
	private ScoreUtilityService scoreUtilityService;

	@Override
	public StepDTO complete(StepDTO step) {
		saveScoreInLissia(step);

		return step;
	}

	private void saveScoreInLissia(StepDTO step) {
		StepTypeDTO stepType = StepTypeDTO.getStepType(step.getStepWorkflow());

		if (StepTypeDTO.AWAITING_ACTIVATION == stepType) {
			scoreUtilityService.saveScoreInLissia(step);
		}
	}

}
