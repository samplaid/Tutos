package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyAgentShareDTO {

	private long pasId;
	private String polId;
	private AgentLightDTO agent;
	private Integer type;
	private int status;
	private BigDecimal percentage;
	private String contractReference;
	private Boolean partnerAuthorized;
	private ProductValueDTO contractManagementFee;
	private BigDecimal specificIce;
	private Integer coverage;
	private Boolean primaryAgent;

	private String modifyBy;
	private Date modifyDate;
	private Date createdDate;
	private String createdBy;

	private Date endDate;
	private Date activeDate;

	public Integer getCoverage() {
		return coverage;
	}

	public void setCoverage(Integer coverage) {
		this.coverage = coverage;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public ProductValueDTO getContractManagementFee() {
		return contractManagementFee;
	}

	public void setContractManagementFee(ProductValueDTO contractManagementFee) {
		this.contractManagementFee = contractManagementFee;
	}

	public AgentLightDTO getAgent() {
		return agent;
	}

	public void setAgent(AgentLightDTO agent) {
		this.agent = agent;
	}

	public Boolean getPartnerAuthorized() {
		return partnerAuthorized;
	}

	public void setPartnerAuthorized(Boolean partnerAuthorized) {
		this.partnerAuthorized = partnerAuthorized;
	}

	public String getContractReference() {
		return contractReference;
	}

	public void setContractReference(String contractReference) {
		this.contractReference = contractReference;
	}

	public long getPasId() {
		return pasId;
	}

	public void setPasId(long pasId) {
		this.pasId = pasId;
	}

	public String getPolId() {
		return polId;
	}

	public void setPolId(String polId) {
		this.polId = polId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + (int) (pasId ^ (pasId >>> 32));
		result = prime * result + ((polId == null) ? 0 : polId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		PolicyAgentShareDTO other = (PolicyAgentShareDTO) obj;
		if (agent == null) {
			if (other.agent != null)
				return false;
		} else if (!agent.equals(other.agent))
			return false;
		if (pasId != other.pasId)
			return false;
		if (polId == null) {
			if (other.polId != null)
				return false;
		} else if (!polId.equals(other.polId))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public BigDecimal getSpecificIce() {
		return specificIce;
	}

	public void setSpecificIce(BigDecimal specificIce) {
		this.specificIce = specificIce;
	}

	public Boolean getPrimaryAgent() {
		return primaryAgent;
	}

	public void setPrimaryAgent(Boolean primaryAgent) {
		this.primaryAgent = primaryAgent;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
