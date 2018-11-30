package lu.wealins.common.dto.liability.services;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AmendmentDTO {

	private String policyId;
	private Integer workflowItemId;
	private Date changeDate;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public Integer getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(Integer workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

}
