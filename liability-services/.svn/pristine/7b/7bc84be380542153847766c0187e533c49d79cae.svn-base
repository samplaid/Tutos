package lu.wealins.liability.services.core.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lu.wealins.liability.services.core.persistence.listener.LissiaAuditListener;

@EntityListeners(LissiaAuditListener.class)
@MappedSuperclass
public class LissiaAuditEntity {

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "CREATED_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	@Column(name = "MODIFY_BY")
	private String modifiedBy;

	@Column(name = "MODIFY_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modificationDate;

	@Column(name = "MODIFY_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modificationTime;

	@Column(name = "CREATED_PROCESS")
	private String creationProcess;

	@Column(name = "MODIFY_PROCESS")
	private String modificationProcess;

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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	public String getCreationProcess() {
		return creationProcess;
	}

	public void setCreationProcess(String creationProcess) {
		this.creationProcess = creationProcess;
	}

	public String getModificationProcess() {
		return modificationProcess;
	}

	public void setModificationProcess(String modificationProcess) {
		this.modificationProcess = modificationProcess;
	}
}
