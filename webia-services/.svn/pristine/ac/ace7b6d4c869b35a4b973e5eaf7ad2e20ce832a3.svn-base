package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CHECK_STEP")
@IdClass(CheckStepEntityId.class)
public class CheckStepEntity implements Serializable {

	private static final long serialVersionUID = -2175263996003152522L;

	@Id
	@ManyToOne
	@JoinColumn(name = "CHECK_ID")
	private CheckWorkflowEntity check;

	@Id
	@ManyToOne
	@JoinColumn(name = "STEP_ID")
	private StepEntity step;

	@Column(name = "IS_UPDATABLE")
	private Boolean isUpdatable;

	@Column(name = "IS_MANDATORY")
	private Boolean isMandatory;

	@Column(name = "CHECK_ORDER")
	private Integer checkOrder;

	@Column(name = "METADATA")
	private String metadata;

	@Column(name = "VISIBLEIF")
	private String visibleIf;

	@Id
	@ManyToOne
	@JoinColumn(name = "LABEL_ID")
	private LabelEntity label;

	public CheckWorkflowEntity getCheck() {
		return check;
	}

	public void setCheck(CheckWorkflowEntity check) {
		this.check = check;
	}

	public StepEntity getStep() {
		return step;
	}

	public void setStep(StepEntity step) {
		this.step = step;
	}

	public Boolean getIsUpdatable() {
		return isUpdatable;
	}

	public void setIsUpdatable(Boolean isUpdatable) {
		this.isUpdatable = isUpdatable;
	}

	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public Integer getCheckOrder() {
		return checkOrder;
	}

	public void setCheckOrder(Integer checkOrder) {
		this.checkOrder = checkOrder;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public String getVisibleIf() {
		return visibleIf;
	}

	public void setVisibleIf(String visibleIf) {
		this.visibleIf = visibleIf;
	}

	public LabelEntity getLabel() {
		return label;
	}

	public void setLabel(LabelEntity label) {
		this.label = label;
	}
}
