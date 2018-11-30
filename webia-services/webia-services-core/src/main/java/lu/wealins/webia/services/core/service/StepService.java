package lu.wealins.webia.services.core.service;

import java.util.Collection;
import java.util.List;

import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.StepLightDTO;

public interface StepService {

	/**
	 * Get the step light object according its id.
	 * 
	 * @param stepId The step id.
	 * @return The step light object.
	 */
	StepLightDTO getStep(Integer stepId);

	/**
	 * Get the step according its id and its workflowItemId.
	 * 
	 * @param stepId The step id.
	 * @param workflowItemId The workflow item id.
	 * @param workflowItemTypeId The workflow item type id.
	 * @return The step.
	 */
	StepDTO getStep(Integer workflowItemId, String stepWorkflow, Integer workflowItemTypeId);

	/**
	 * Get the checkSteps of a step according its id and its workflowItemId.
	 * 
	 * @param stepId The step id.
	 * @param workflowItemId The workflow item id.
	 * @param workflowItemTypeId The workflow item type id.
	 * @return a collection of checkStep.
	 */
	Collection<CheckStepDTO> getCheckSteps(Integer workflowItemId, String stepWorkflow, Integer workflowItemTypeId);
	
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
	StepDTO updateStep(StepDTO step);
	
	/**
	 * Abort the step.
	 * 
	 * @param step The step.
	 * @return The aborted step.
	 */
	StepDTO abortStep(StepDTO step);

	List<String> validateBeforeSave(StepDTO step);

	/**
	 * Validate the step before the step completion.
	 * 
	 * @param step The step
	 */
	List<String> validateBeforeCompleteStep(StepDTO step);

	StepDTO completeStep(StepDTO stepDTO);

}
