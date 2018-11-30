package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationDTO {

	private String workflowItemId;
	private String policyId;
	private String productCd;
	private String statusCd;
	private String clientName;
	private String actionOther;
	private String currentStepName;
	private String CPS1;
	private String CPS2;

	public String getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(String workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getProductCd() {
		return productCd;
	}

	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getActionOther() {
		return actionOther;
	}

	public void setActionOther(String actionOther) {
		this.actionOther = actionOther;
	}

	public String getCurrentStepName() {
		return currentStepName;
	}

	public void setCurrentStepName(String currentStepName) {
		this.currentStepName = currentStepName;
	}

	public String getCPS1() {
		return CPS1;
	}

	public void setCPS1(String CPS1) {
		this.CPS1 = CPS1;
	}
	
	public String getCPS2() {
		return CPS2;
	}

	public void setCPS2(String CPS2) {
		this.CPS2 = CPS2;
	}

}
