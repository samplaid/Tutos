package lu.wealins.webia.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.StepLightDTO;

public interface WebiaStepService {

	/**
	 * Get a step according its the step id and the workflow item id.
	 * 
	 * @param stepId The step id.
	 * @param workflowItemId The workflow item id.
	 * @param workflowItemId The workflow item Type id.
	 * @return The step.
	 */
	StepDTO getStep(Integer workflowItemId, String stepWorkflow, Integer workflowItemTypeId);
	
	/**
	 * Return the steps corresponding to the provided workflow item type id.
	 * 
	 * @param workflowItemTypeId the workflow item type id.
	 * @return the {@link StepDTO}.
	 */
	Collection<StepLightDTO> getStepsByWorkflowItemTypeId(Integer workflowItemTypeId);

	/**
	 * Update the step.
	 * 
	 * @param step The step.
	 * @return The updated step.
	 */
	StepDTO update(StepDTO step);

	/**
	 * Complete the step.
	 * 
	 * @param step The step.
	 * @return The completed step.
	 */
	StepDTO complete(StepDTO step);
	
	StepDTO postComplete(StepDTO step);
	
	/**
	 * Abort the step
	 * 
	 * @param step the step
	 * @return the aborted step
	 */
	StepDTO abort(StepDTO step);
}
