package lu.wealins.common.dto.liability.services;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.liability.services.enums.TransferType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyTransferDTO {

	private Long policyTransferId;

	private String policy;

	private Integer coverage;

	private String fromPolicy;

	private Date fromPolicyEffectDt;

	private TransferType transferType;

	private String fkPoliciespolId;

	public Long getPolicyTransferId() {
		return policyTransferId;
	}

	public void setPolicyTransferId(Long policyTransferId) {
		this.policyTransferId = policyTransferId;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public Integer getCoverage() {
		return coverage;
	}

	public void setCoverage(Integer coverage) {
		this.coverage = coverage;
	}

	public String getFromPolicy() {
		return fromPolicy;
	}

	public void setFromPolicy(String fromPolicy) {
		this.fromPolicy = fromPolicy;
	}

	public Date getFromPolicyEffectDt() {
		return fromPolicyEffectDt;
	}

	public void setFromPolicyEffectDt(Date fromPolicyEffectDt) {
		this.fromPolicyEffectDt = fromPolicyEffectDt;
	}

	public TransferType getTransferType() {
		return transferType;
	}

	public void setTransferType(TransferType transferType) {
		this.transferType = transferType;
	}

	public String getFkPoliciespolId() {
		return fkPoliciespolId;
	}

	public void setFkPoliciespolId(String fkPoliciespolId) {
		this.fkPoliciespolId = fkPoliciespolId;
	}

}
