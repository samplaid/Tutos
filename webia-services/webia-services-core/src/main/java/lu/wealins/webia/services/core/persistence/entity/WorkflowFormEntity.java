package lu.wealins.webia.services.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class WorkflowFormEntity extends AuditingEntity {

	private static final long serialVersionUID = -3565817009250764972L;

	@Id
	@Column(name = "FORM_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer formId;

	@Column(name = "WORKFLOW_ITEM_ID")
	private Integer workflowItemId;

	@Column(name = "POLICY_ID")
	private String policyId;

	@Column(name = "SECOND_CPS_USER")
	private String secondCpsUser;

	@Column(name = "FIRST_CPS_USER")
	private String firstCpsUser;

	@Column(name = "STATUS_CD")
	private String statusCd;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getSecondCpsUser() {
		return secondCpsUser;
	}

	public void setSecondCpsUser(String secondCpsUser) {
		this.secondCpsUser = secondCpsUser;
	}

	public Integer getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(Integer workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getFirstCpsUser() {
		return firstCpsUser;
	}

	public void setFirstCpsUser(String firstCpsUser) {
		this.firstCpsUser = firstCpsUser;
	}
}
