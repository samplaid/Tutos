package lu.wealins.webia.services.core.service.impl.form.data;

import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;

public class WorkFlowFormValidationType {
	
	private WorkflowType workfolwType;
	private StepTypeDTO stepType;

	public WorkFlowFormValidationType() {

	}

	public WorkFlowFormValidationType(WorkflowType workfolwType, StepTypeDTO stepType) {
		super();
		this.workfolwType = workfolwType;
		this.stepType = stepType;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stepType == null) ? 0 : stepType.hashCode());
		result = prime * result + ((workfolwType == null) ? 0 : workfolwType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkFlowFormValidationType other = (WorkFlowFormValidationType) obj;
		if (stepType != other.stepType)
			return false;
		if (workfolwType != other.workfolwType)
			return false;
		return true;
	}
	
	public WorkflowType getWorkfolwType() {
		return workfolwType;
	}
	public void setWorkfolwType(WorkflowType workfolwType) {
		this.workfolwType = workfolwType;
	}
	public StepTypeDTO getStepType() {
		return stepType;
	}
	public void setStepType(StepTypeDTO stepType) {
		this.stepType = stepType;
	}

	
}
