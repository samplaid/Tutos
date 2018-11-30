package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "STEP")
public class StepEntity implements Serializable {

	private static final long serialVersionUID = -4537178159289539233L;

	@Id
	@Column(name = "STEP_ID")
	private Integer stepId;

	@Column(name = "STEP_WORKFLOW")
	private String stepWorkflow;

	@Column(name = "WORKFLOW_ITEM_TYPE_ID")
	private Integer workflowItemTypeId;

	@Column(name = "STEP_REJECTABLE")
	private Boolean stepRejectable;
	
	@Column(name = "STEP_AUTOMATIC")
	private Boolean stepAutomatic;

	@Column(name = "STEP_ABORTABLE")
	private Boolean stepAbortable;

	@Column(name = "STEP_REVERSABLE")
	private Boolean stepReversable;

	@OneToMany(mappedBy = "step", fetch = FetchType.LAZY)
	private Collection<CheckStepEntity> checkSteps;

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public String getStepWorkflow() {
		return stepWorkflow;
	}

	public void setStepWorkflow(String stepWorkflow) {
		this.stepWorkflow = stepWorkflow;
	}

	public Collection<CheckStepEntity> getCheckSteps() {
		return checkSteps;
	}

	public void setCheckSteps(Collection<CheckStepEntity> checkSteps) {
		this.checkSteps = checkSteps;
	}

	public Integer getWorkflowItemTypeId() {
		return workflowItemTypeId;
	}

	public void setWorkflowItemTypeId(Integer workflowItemTypeId) {
		this.workflowItemTypeId = workflowItemTypeId;
	}

	public Boolean getStepRejectable() {
		return stepRejectable;
	}

	public void setStepRejectable(Boolean stepRejectable) {
		this.stepRejectable = stepRejectable;
	}
	
	public Boolean getStepAutomatic() {
		return stepAutomatic;
	}
	
	public void setStepAutomatic(Boolean stepAutomatic) {
		this.stepAutomatic = stepAutomatic;
	}

	public Boolean getStepAbortable() {
		return stepAbortable;
	}

	public void setStepAbortable(Boolean stepAbortable) {
		this.stepAbortable = stepAbortable;
	}

	public Boolean getStepReversable() {
		return stepReversable;
	}

	public void setStepReversable(Boolean stepReversable) {
		this.stepReversable = stepReversable;
	}
}
