/**
 * 
 */
package lu.wealins.common.dto.webia.services;

import java.util.Date;

/**
 * @author Nabil Loutfi
 *
 */
public class StepCommentRequest {

	/**
	 * The workflow Item Id
	 */
	private Long workflowItemId;

	/**
	 * An optional due date linked to the comment
	 */
	private Date nextDueDate;
	
	/**
	 * The text to attached as comment to the workflow Item
	 */
	private String comment;
	
	/**
	 * The current step Id of the workflow
	 */
	private Integer stepId;

	/**
	 * The step comment id.
	 */
	private Long stepCommentId;

	public Long getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(Long workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public Date getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(Date nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}
	
	public Long getStepCommentId() {
		return stepCommentId;
	}
	
	public void setStepCommentId(Long stepCommentId) {
		this.stepCommentId = stepCommentId;
	}
}
