package lu.wealins.common.dto.liability.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentHierarchyDTO {

	private Long aghId;
	private AgentLightDTO masterBroker;
	private AgentLightDTO subBroker;
	private Integer status;
	private Integer type;
	private boolean updated;
		
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public AgentLightDTO getMasterBroker() {
		return masterBroker;
	}
	public void setMasterBroker(AgentLightDTO masterBroker) {
		this.masterBroker = masterBroker;
	}
	public AgentLightDTO getSubBroker() {
		return subBroker;
	}
	public void setSubBroker(AgentLightDTO subBroker) {
		this.subBroker = subBroker;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getAghId() {
		return aghId;
	}
	public void setAghId(Long aghId) {
		this.aghId = aghId;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
		
}
