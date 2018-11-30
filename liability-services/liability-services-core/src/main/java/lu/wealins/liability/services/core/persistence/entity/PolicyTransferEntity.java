package lu.wealins.liability.services.core.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lu.wealins.common.dto.liability.services.enums.TransferType;
import lu.wealins.liability.services.core.persistence.converter.PolicyIdConverter;

@Entity
@Table(name = "POLICY_TRANSFER")
public class PolicyTransferEntity extends LissiaAuditEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POLICY_TRANSFER_ID")
	private Long policyTransferId;

	@Convert(converter = PolicyIdConverter.class)
	@Column(name = "POLICY")
	private String policy;

	@Column(name = "COVERAGE")
	private Integer coverage;

	@Column(name = "FROM_POLICY")
	private String fromPolicy;

	@Column(name = "FROM_POLICY_EFFECT_DT")
	@Temporal(TemporalType.DATE)
	private Date fromPolicyEffectDt;

	@Column(name = "TRANSFER_TYPE")
	@Enumerated(EnumType.STRING)
	private TransferType transferType;

	@Convert(converter = PolicyIdConverter.class)
	@Column(name = "FK_POLICIESPOL_ID")
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
