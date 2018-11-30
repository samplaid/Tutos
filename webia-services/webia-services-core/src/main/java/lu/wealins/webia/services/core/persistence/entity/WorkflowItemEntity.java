package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WORKFLOW_ITEM")
// WORKFLOW_USERS is a SYNONYM
public class WorkflowItemEntity implements Serializable {

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "ACTION_REQUIRED")
	private Integer actionRequired;

	@Column(name = "ACTION_OTHER")
	private String actionOther;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_TIMESTAMP")
	private Date createdTimestamp;

	@Column(name = "DUE_DATE")
	private Date dueDate;

	@Column(name = "SUSPEND_DATE")
	private Date suspendDate;

	@Column(name = "PRIORITY")
	private Integer priority;

	@Column(name = "FK_WORKFLOW_QUEID")
	private Long workflowQueueid;

	@Column(name = "FK_AUDIT_HISTORAH_ID")
	private Long auditHistorahId;

	@Column(name = "STATUS_TIMESTAMP")
	private Date statusTimestamp;

	@Column(name = "LOCK_USER")
	private String lockUser;

	@Column(name = "LOCK_TIMESTAMP")
	private Date lockTimestamp;

	@Column(name = "FK_POLICIESPOL_ID")
	private Long policiespolId;

	@Column(name = "FK_GENERAL_EVENPEV_ID")
	private Long generalEvenpevId;

	@Column(name = "FK_CLIENTSCLI_ID")
	private Long clientscliId;

	@Column(name = "FK_WORKFLOW_ITEM_TYPE")
	private Integer workflowItemType;

	@Column(name = "FK_LINKED_TO_ID")
	private Long fkLinkedToId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getActionRequired() {
		return actionRequired;
	}

	public void setActionRequired(Integer actionRequired) {
		this.actionRequired = actionRequired;
	}

	public String getActionOther() {
		return actionOther;
	}

	public void setActionOther(String actionOther) {
		this.actionOther = actionOther;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getSuspendDate() {
		return suspendDate;
	}

	public void setSuspendDate(Date suspendDate) {
		this.suspendDate = suspendDate;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Long getWorkflowQueueid() {
		return workflowQueueid;
	}

	public void setWorkflowQueueid(Long workflowQueueid) {
		this.workflowQueueid = workflowQueueid;
	}

	public Long getAuditHistorahId() {
		return auditHistorahId;
	}

	public void setAuditHistorahId(Long auditHistorahId) {
		this.auditHistorahId = auditHistorahId;
	}

	public Date getStatusTimestamp() {
		return statusTimestamp;
	}

	public void setStatusTimestamp(Date statusTimestamp) {
		this.statusTimestamp = statusTimestamp;
	}

	public String getLockUser() {
		return lockUser;
	}

	public void setLockUser(String lockUser) {
		this.lockUser = lockUser;
	}

	public Date getLockTimestamp() {
		return lockTimestamp;
	}

	public void setLockTimestamp(Date lockTimestamp) {
		this.lockTimestamp = lockTimestamp;
	}

	public Long getPoliciespolId() {
		return policiespolId;
	}

	public void setPoliciespolId(Long policiespolId) {
		this.policiespolId = policiespolId;
	}

	public Long getGeneralEvenpevId() {
		return generalEvenpevId;
	}

	public void setGeneralEvenpevId(Long generalEvenpevId) {
		this.generalEvenpevId = generalEvenpevId;
	}

	public Long getClientscliId() {
		return clientscliId;
	}

	public void setClientscliId(Long clientscliId) {
		this.clientscliId = clientscliId;
	}

	public Integer getWorkflowItemType() {
		return workflowItemType;
	}

	public void setWorkflowItemType(Integer workflowItemType) {
		this.workflowItemType = workflowItemType;
	}

	public Long getFkLinkedToId() {
		return fkLinkedToId;
	}

	public void setFkLinkedToId(Long fkLinkedToId) {
		this.fkLinkedToId = fkLinkedToId;
	}

}
