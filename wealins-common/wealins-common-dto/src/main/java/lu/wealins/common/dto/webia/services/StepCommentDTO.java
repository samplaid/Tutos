package lu.wealins.common.dto.webia.services;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StepCommentDTO {


	private Long stepCommentId;	
	private StepLightDTO step;
	private Long workflowItemId;
	private String comment;
	private Date nextDueDate;
	private String creationUser;
	private Date creationDt;
	private String updateUser;
	private Date updateDt;
	
	public Long getStepCommentId() {
		return stepCommentId;
	}
	public void setStepCommentId(Long stepCommentId) {
		this.stepCommentId = stepCommentId;
	}
	public StepLightDTO getStep() {
		return step;
	}
	public void setStep(StepLightDTO step) {
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
	public String getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	public Date getCreationDt() {
		return creationDt;
	}
	public void setCreationDt(Date creationDt) {
		this.creationDt = creationDt;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	

}