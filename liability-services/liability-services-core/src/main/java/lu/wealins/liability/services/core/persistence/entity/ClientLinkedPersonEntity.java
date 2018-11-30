package lu.wealins.liability.services.core.persistence.entity;

import java.io.Serializable;
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
@Table(name = "CLIENT_LINKED_PERSONS")
public class ClientLinkedPersonEntity implements Serializable {

	@Id
	@Column(name = "CLP_ID")
	private Long clpId;
	@Column(name = "ENTITY")
	private Integer entity;
	@Column(name = "CONTROLLING_PERSON")
	private Integer controllingPerson;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "TYPE")
	private String type;
	@Column(name = "SUB_TYPE")
	private String subType;
	@Column(name = "START_DATE")
	private Date startDate;
	@Column(name = "END_DATE")
	private Date endDate;
	@Column(name = "CREATED_PROCESS")
	private String createdProcess;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIME")
	private Date createdTime;
	@Column(name = "MODIFY_BY")
	private String modifyBy;
	@Column(name = "MODIFY_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyTime;
	@Column(name = "MODIFY_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDate;
	@Column(name = "MODIFY_PROCESS")
	private String modifyProcess;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_CLIENTSCLI_ID")
	@NotFound(action = NotFoundAction.IGNORE)
	private ClientEntity client;

	public Long getClpId() {
		return clpId;
	}
	public void setClpId(Long clpId) {
		this.clpId = clpId;
	}

	public Integer getEntity() {
		return entity;
	}

	public void setEntity(Integer entity) {
		this.entity = entity;
	}
	public Integer getControllingPerson() {
		return controllingPerson;
	}
	public void setControllingPerson(Integer controllingPerson) {
		this.controllingPerson = controllingPerson;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getCreatedProcess() {
		return createdProcess;
	}
	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getModifyProcess() {
		return modifyProcess;
	}
	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
	}

	public ClientEntity getClient() {
		return client;
	}

	public void setClient(ClientEntity client) {
		this.client = client;
	}

}
