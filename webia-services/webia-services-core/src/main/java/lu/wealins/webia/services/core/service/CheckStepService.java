package lu.wealins.webia.services.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.StepDTO;

public interface CheckStepService {

	/**
	 * Get comments history having an answer not null.
	 * 
	 * @param workflowItemId The workflow itemid.
	 * @return The comments history.
	 */
	Collection<CheckStepDTO> getCommentsHistory(Integer workflowItemId, Integer workflowItemTypeId);

	/**
	 * Get check step according the check code.
	 * 
	 * @param step The step.
	 * @param checkCode The check code.
	 * @return The check step. Be careful, the check data won't be set!!! You need to setup it manually if it is needed.
	 */
	CheckStepDTO getCheckStep(StepDTO step, String checkCode);

	/**
	 * Add check data in check step object of a step
	 * @param workflowItemId
	 * @param step
	 */
	void addCheckDataAtCheckStepLevel(Integer workflowItemId, StepDTO step);

}
