package lu.wealins.liability.services.core.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "AGENT_CONTACTS")
public class AgentContactEntity implements ChangeMetadata {

	@Id
	@Column(name = "AGC_ID")
	private Integer agcId;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENT_NO")
	@NotFound(action = NotFoundAction.IGNORE)
	private AgentEntity agent;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTACT_ID")
	@NotFound(action = NotFoundAction.IGNORE)
	private AgentEntity contact;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "CONTACT_FUNCTION")
	private String contactFunction;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIME")
	private Date createdTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = "CREATED_PROCESS")
	private String createdProcess;
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	@Column(name = "MODIFIED_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedTime;
	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;
	@Column(name = "MODIFIED_PROCESS")
	private String modifiedProcess;
	@Column(name = "FK_AGENTSAGT_ID")
	private String agentId;

	public Integer getAgcId() {
		return agcId;
	}

	public void setAgcId(Integer agcId) {
		this.agcId = agcId;
	}

	public AgentEntity getAgent() {
		return agent;
	}

	public void setAgent(AgentEntity agent) {
		this.agent = agent;
	}

	public AgentEntity getContact() {
		return contact;
	}

	public void setContact(AgentEntity contact) {
		this.contact = contact;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContactFunction() {
		return contactFunction;
	}

	public void setContactFunction(String contactFunction) {
		this.contactFunction = contactFunction;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedProcess() {
		return createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedProcess() {
		return modifiedProcess;
	}

	public void setModifiedProcess(String modifiedProcess) {
		this.modifiedProcess = modifiedProcess;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

}
