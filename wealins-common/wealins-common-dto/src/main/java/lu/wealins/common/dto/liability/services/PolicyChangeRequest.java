package lu.wealins.common.dto.liability.services;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.liability.services.enums.PolicyChangeStatus;
import lu.wealins.common.dto.liability.services.enums.PolicyChangeType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyChangeRequest {

	private PolicyChangeType type;
	private Integer workflowItemId;
	private String policyId;
	private PolicyChangeStatus status;
	private Date dateOfChange;

	public PolicyChangeType getType() {
		return type;
	}

	public void setType(PolicyChangeType type) {
		this.type = type;
	}

	public Integer getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(Integer workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public PolicyChangeStatus getStatus() {
		return status;
	}

	public void setStatus(PolicyChangeStatus status) {
		this.status = status;
	}

	public Date getDateOfChange() {
		return dateOfChange;
	}

	public void setDateOfChange(Date dateOfChange) {
		this.dateOfChange = dateOfChange;
	}

}
