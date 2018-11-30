package lu.wealins.webia.services.core.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "STEP_COMMENT")
public class StepCommentEntity extends AuditingEntity {

	private static final long serialVersionUID = 753768765392623486L;

	@Id
	@Column(name = "STEP_COMMENT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stepCommentId;	
	
	@ManyToOne
	@JoinColumn(name = "STEP_ID")
	private StepEntity step;

	@Column(name = "WORKFLOW_ITEM_ID")
	private Long workflowItemId;

	@Column(name = "COMMENT")
	private String comment;
	
	@Column(name = "DUE_DT")
	private Date nextDueDate;

	public Long getStepCommentId() {
		return stepCommentId;
	}

	public void setStepCommentId(Long stepCommentId) {
		this.stepCommentId = stepCommentId;
	}

	public StepEntity getStep() {
		return step;
	}

	public void setStep(StepEntity step) {
		this.step = step;
	}

	public Long getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(Long workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(Date nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	
}
