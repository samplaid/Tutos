package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.webia.services.enums.OperationStatus;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateOperationStatusRequest {

	private Integer workflowItemId;
	private OperationStatus status;
	private WorkflowType workflowType;

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}

	public Integer getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(Integer workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public WorkflowType getWorkflowType() {
		return workflowType;
	}

	public void setWorkflowType(WorkflowType workflowType) {
		this.workflowType = workflowType;
	}


}
