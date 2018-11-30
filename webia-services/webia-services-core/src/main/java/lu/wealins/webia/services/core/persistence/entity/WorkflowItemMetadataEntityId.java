package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;

public class WorkflowItemMetadataEntityId implements Serializable {

	private static final long serialVersionUID = 1L;

	private WorkflowItemEntity workflowItem;

	private WorkflowMetadataKeyEntity workflowMetadata;
	
	private Integer itemIndex;

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
		
}
