package lu.wealins.common.dto.liability.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetManagerStrategyDTO {

	private Long amsId;
	private String agentNo;
	private String riskProfile;
	private String riskProfileDescription;
	private String classOfRisk;
	private String riskType;
	private String status;
	private boolean linkedToFund;

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	public void setLinkedToFund(boolean linkedToFund) {
		this.linkedToFund = linkedToFund;
	}

	public boolean isLinkedToFund() {
		return linkedToFund;
	}
	
	public Long getAmsId() {
		return amsId;
	}

	public void setAmsId(Long amsId) {
		this.amsId = amsId;
	}

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public String getRiskProfile() {
		return riskProfile;
	}

	public void setRiskProfile(String riskProfile) {
		this.riskProfile = riskProfile;
	}

	public String getRiskProfileDescription() {
		return riskProfileDescription;
	}

	public void setRiskProfileDescription(String riskProfileDescription) {
		this.riskProfileDescription = riskProfileDescription;
	}

	public String getClassOfRisk() {
		return this.classOfRisk;
	}

	public void setClassOfRisk(String classOfRisk) {
		this.classOfRisk = classOfRisk;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agentNo == null) ? 0 : agentNo.hashCode());
		result = prime * result + ((amsId == null) ? 0 : amsId.hashCode());
		result = prime * result + ((classOfRisk == null) ? 0 : classOfRisk.hashCode());
		result = prime * result + ((riskProfile == null) ? 0 : riskProfile.hashCode());
		result = prime * result + ((riskProfileDescription == null) ? 0 : riskProfileDescription.hashCode());
		result = prime * result + ((riskType == null) ? 0 : riskType.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		AssetManagerStrategyDTO other = (AssetManagerStrategyDTO) obj;
		if (agentNo == null) {
			if (other.agentNo != null)
				return false;
		} else if (!agentNo.equals(other.agentNo))
			return false;
		if (amsId == null) {
			if (other.amsId != null)
				return false;
		} else if (!amsId.equals(other.amsId))
			return false;
		if (classOfRisk == null) {
			if (other.classOfRisk != null)
				return false;
		} else if (!classOfRisk.equals(other.classOfRisk))
			return false;
		if (riskProfile == null) {
			if (other.riskProfile != null)
				return false;
		} else if (!riskProfile.equals(other.riskProfile))
			return false;
		if (riskProfileDescription == null) {
			if (other.riskProfileDescription != null)
				return false;
		} else if (!riskProfileDescription.equals(other.riskProfileDescription))
			return false;
		if (riskType == null) {
			if (other.riskType != null)
				return false;
		} else if (!riskType.equals(other.riskType))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AssetManagerStrategyDTO [amsId=").append(amsId).append(", agentNo=").append(agentNo).append(", riskProfile=").append(riskProfile).append(", riskType=").append(riskType)
				.append("]");
		return builder.toString();
	}

}
