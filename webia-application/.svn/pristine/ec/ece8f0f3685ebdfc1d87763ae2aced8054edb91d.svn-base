package lu.wealins.webia.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.StepCommentDTO;
import lu.wealins.common.dto.webia.services.StepCommentRequest;

public interface WebiaStepCommentService {

	
	/**
	 * Load all the comments of a given workflow Item.  
	 * 
	 * @param workflowItemId The workflow item id.
	 * @return The comments.
	 */
	Collection<StepCommentDTO> getStepComments(Long workflowItemId);

	/**
	 * Add a comment on a given workflow Item.  
	 * 
	 * @param StepCommentRequest The request with a workflow Item id, a comment and an optional due date.
	 * @return
	 */
	StepCommentDTO addStepComment(StepCommentRequest request);

	/**
	 * Update the comment corresponding to the {@link StepCommentRequest} provided in parameter.
	 * 
	 * @param StepCommentRequest The request with a workflow Item id, a comment and an optional due date.
	 * @return the {@link StepCommentDTO} after the update
	 */
	StepCommentDTO updateStepComment(StepCommentRequest request);

}
