package lu.wealins.webia.services.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.StepCommentDTO;
import lu.wealins.common.dto.webia.services.StepCommentRequest;

public interface StepCommentService {

	/**
	 * Get all comments of the workflow item id.
	 * 
	 * @param workflowItemId The workflow Item id.
	 * @return The step.
	 */

	Collection<StepCommentDTO> getStepComments(Long workflowItemId);


	/**
	 * Save the step.
	 * 
	 * @param StepCommentRequest The request.
	 * @return the comment added
	 */
	StepCommentDTO addStepComment(StepCommentRequest request);


	/**
	 * Update the ste
	 * 
	 * @param StepCommentRequest The request.
	 * @return the comment updated
	 */
	StepCommentDTO updateStepComment(StepCommentRequest request);

}
