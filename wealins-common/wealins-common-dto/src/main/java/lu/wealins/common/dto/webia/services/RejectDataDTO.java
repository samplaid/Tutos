package lu.wealins.common.dto.webia.services;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RejectDataDTO {

	private Integer rejectDataId;
	private Integer WorkflowItemId;
	private StepLightDTO step;
	private String rejectComment;
	private String creationUser;
	private Date creationDt;

	public RejectDataDTO() {
	}

	public RejectDataDTO(Integer workflowItemId, Integer stepId) {
		WorkflowItemId = workflowItemId;
		this.step = new StepLightDTO();
		this.step.setStepId(stepId);
	}

	public Integer getRejectDataId() {
		return rejectDataId;
	}

	public void setRejectDataId(Integer rejectDataId) {
		this.rejectDataId = rejectDataId;
	}

	public Integer getWorkflowItemId() {
		return WorkflowItemId;
	}

	public void setWorkflowItemId(Integer workflowItemId) {
		WorkflowItemId = workflowItemId;
	}

	public StepLightDTO getStep() {
		return step;
	}

	public void setStep(StepLightDTO step) {
		this.step = step;
	}

	public String getRejectComment() {
		return rejectComment;
	}

	public void setRejectComment(String rejectComment) {
		this.rejectComment = rejectComment;
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

}
