package lu.wealins.common.dto.liability.services;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowItemDataDTO {

	private Long workflowItemId;
	private Collection<MetadataDTO> metadata = new ArrayList<>();
	private String usrId;
	private Integer workflowItemTypeId;

	public Long getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(Long workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public Collection<MetadataDTO> getMetadata() {
		return metadata;
	}

	public void setMetadata(Collection<MetadataDTO> metadata) {
		this.metadata = metadata;
	}

	public String getUsrId() {
		return usrId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	public Integer getWorkflowItemTypeId() {
		return workflowItemTypeId;
	}

	public void setWorkflowItemTypeId(Integer workflowItemTypeId) {
		this.workflowItemTypeId = workflowItemTypeId;
	}

}
