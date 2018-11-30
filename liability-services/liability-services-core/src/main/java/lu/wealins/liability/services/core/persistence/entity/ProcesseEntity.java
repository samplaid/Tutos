package lu.wealins.liability.services.core.persistence.entity;
// Generated Dec 1, 2016 12:16:30 PM by Hibernate Tools 4.3.1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ProcesseEntity generated by hbm2java
 */
@Entity
@Table(name = "PROCESSES"

)
public class ProcesseEntity implements java.io.Serializable {

	private String prsId;
	private Character finLimitDisable;
	private Character createDisable;
	private Character amendDisable;
	private String createdProcess;
	private String modifyProcess;
	private int status;
	private String createdBy;
	private Date createdDate;
	private Date createdTime;
	private String modifyBy;
	private Date modifyDate;
	private Date modifyTime;
	private String name;

	@Id
	@Column(name = "PRS_ID", unique = true, nullable = false, length = 12)
	public String getPrsId() {
		return this.prsId;
	}

	public void setPrsId(String prsId) {
		this.prsId = prsId;
	}

	@Column(name = "FIN_LIMIT_DISABLE", length = 1)
	public Character getFinLimitDisable() {
		return this.finLimitDisable;
	}

	public void setFinLimitDisable(Character finLimitDisable) {
		this.finLimitDisable = finLimitDisable;
	}

	@Column(name = "CREATE_DISABLE", length = 1)
	public Character getCreateDisable() {
		return this.createDisable;
	}

	public void setCreateDisable(Character createDisable) {
		this.createDisable = createDisable;
	}

	@Column(name = "AMEND_DISABLE", length = 1)
	public Character getAmendDisable() {
		return this.amendDisable;
	}

	public void setAmendDisable(Character amendDisable) {
		this.amendDisable = amendDisable;
	}

	@Column(name = "CREATED_PROCESS", nullable = false, length = 15)
	public String getCreatedProcess() {
		return this.createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	@Column(name = "MODIFY_PROCESS", length = 15)
	public String getModifyProcess() {
		return this.modifyProcess;
	}

	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
	}

	@Column(name = "STATUS", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "CREATED_BY", nullable = false, length = 5)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false, length = 23)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIME", nullable = false, length = 23)
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "MODIFY_BY", length = 5)
	public String getModifyBy() {
		return this.modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_DATE", length = 23)
	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_TIME", length = 23)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "NAME", nullable = false, length = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
