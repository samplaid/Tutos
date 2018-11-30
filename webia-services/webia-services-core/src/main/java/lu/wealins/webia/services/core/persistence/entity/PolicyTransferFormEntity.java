package lu.wealins.webia.services.core.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "POLICY_TRANSFER_FORM")
@Entity
public class PolicyTransferFormEntity extends AuditingEntity {

	/**	Generated Serial ID.	*/
	private static final long serialVersionUID = 3135409122331404740L;

	@Id
	@Column(name = "POLICY_TRANSFER_FORM_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer policyTransferFormId;
	
	@Column(name = "FORM_ID")
	private Integer formId;
	
	@Column(name = "FROM_POLICY")
	private String fromPolicy;
	
	@Column(name = "FROM_POLICY_EFFECT_DT")
	private Date fromPolicyEffectDt;

	public Integer getPolicyTransferFormId() {
		return policyTransferFormId;
	}

	public void setPolicyTransferFormId(Integer policyTransferFormId) {
		this.policyTransferFormId = policyTransferFormId;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
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
}
