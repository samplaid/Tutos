package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "WORKFLOW_ITEM_METADATA")
// WORKFLOW_ITEM_METADATA is a SYNONYM
@IdClass(WorkflowItemMetadataEntityId.class)
public class WorkflowItemMetadataEntity implements Serializable {

	private static final long serialVersionUID = -8942080846056451941L;

	@Column(name = "VALUE0")
	private String value0;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_WORKFLOW_ITEM_ID")
	@NotFound(action = NotFoundAction.IGNORE)
	private WorkflowItemEntity workflowItem;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_WORKFLOW_METADATA_KEY")
	@NotFound(action = NotFoundAction.IGNORE)
	private WorkflowMetadataKeyEntity workflowMetadata;
	
	@Id
	@Column(name = "item_index")
	private Integer itemIndex;

	@Column(name = "display_value")
	private String displayValue;

	public String getValue0() {
		return value0;
	}

	public void setValue0(String value0) {
		this.value0 = value0;
	}

	public WorkflowItemEntity getWorkflowItem() {
		return workflowItem;
	}

	public void setWorkflowItem(WorkflowItemEntity workflowItem) {
		this.workflowItem = workflowItem;
	}

	public WorkflowMetadataKeyEntity getWorkflowMetadata() {
		return workflowMetadata;
	}

	public void setWorkflowMetadata(WorkflowMetadataKeyEntity workflowMetadata) {
		this.workflowMetadata = workflowMetadata;
	}

	public Integer getItemIndex() {
		return itemIndex;
	}

	public void setItemIndex(Integer itemIndex) {
		this.itemIndex = itemIndex;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	
}
