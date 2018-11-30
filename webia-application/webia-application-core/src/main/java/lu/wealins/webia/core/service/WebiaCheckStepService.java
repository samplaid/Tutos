package lu.wealins.webia.core.service;

import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.StepDTO;

public interface WebiaCheckStepService {

	/**
	 * Get check step according the check code.
	 * 
	 * @param step The step.
	 * @param checkCode The check code.
	 * @return The check step.
	 */
	CheckStepDTO getCheckStep(StepDTO step, String checkCode);
}
