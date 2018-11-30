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

import lu.wealins.common.dto.liability.services.enums.PolicyChangeStatus;
import lu.wealins.common.dto.liability.services.enums.PolicyChangeType;
import lu.wealins.liability.services.core.persistence.converter.PolicyChangesStatusConverter;

@Entity
@Table(name = "POLICY_CHANGES")
public class PolicyChangeEntity extends LissiaAuditEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PCHANGE_ID")
	private Long id;

	@Column(name = "TYPE")
	@Enumerated(EnumType.STRING)
	private PolicyChangeType type;

	@Column(name = "WORKFLOW_ITEM_ID")
	private Integer workflowItemId;

	@Column(name = "POLICY")
	private String policyId;

	@Column(name = "STATUS")
	@Convert(converter = PolicyChangesStatusConverter.class)
	private PolicyChangeStatus status;

	@Column(name = "CANCEL_DT")
	@Temporal(TemporalType.DATE)
	private Date cancelDate;

	@Column(name = "FK_POLICIESPOL_ID")
	private String fkPoliciespolId;

	@Column(name = "DATE_OF_CHANGE")
	@Temporal(TemporalType.DATE)
	private Date dateOfChange;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PolicyChangeType getType() {
		return type;
	}

	public void setType(PolicyChangeType type) {
		this.type = type;
	}

	public Integer getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(Integer workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public PolicyChangeStatus getStatus() {
		return status;
	}

	public void setStatus(PolicyChangeStatus status) {
		this.status = status;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getFkPoliciespolId() {
		return fkPoliciespolId;
	}

	public void setFkPoliciespolId(String fkPoliciespolId) {
		this.fkPoliciespolId = fkPoliciespolId;
	}

	public Date getDateOfChange() {
		return dateOfChange;
	}

	public void setDateOfChange(Date dateOfChange) {
		this.dateOfChange = dateOfChange;
	}
}
