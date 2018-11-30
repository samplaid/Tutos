package lu.wealins.common.dto.liability.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowTriggerActionDTO {

	private String triggerAction;
	private Integer workflowItemId;
	private String usrId;

	public String getTriggerAction() {
		return triggerAction;
	}

	public void setTriggerAction(String triggerAction) {
		this.triggerAction = triggerAction;
	}

	public Integer getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(Integer workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public String getUsrId() {
		return usrId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

}
