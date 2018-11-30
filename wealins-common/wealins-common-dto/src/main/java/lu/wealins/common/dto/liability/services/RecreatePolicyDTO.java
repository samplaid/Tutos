package lu.wealins.common.dto.liability.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecreatePolicyDTO {
	private Long workflowItemId;
	private Integer formId;

	public Long getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(Long workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}
}
