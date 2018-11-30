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
 * ReinsuranceTreatyEventEntity generated by hbm2java
 */
@Entity
@Table(name = "REINSURANCE_TREATY_EVENTS"

)
public class ReinsuranceTreatyEventEntity implements java.io.Serializable {

	private long rteId;
	private String treaty;
	private int status;
	private Date createdTime;
	private Date createdDate;
	private String createdBy;
	private String createdProcess;
	private Date actualEventDate;
	private Date systemDate;
	private Date effectiveDate;
	private String event;

	@Id
	@Column(name = "RTE_ID", unique = true, nullable = false, precision = 15, scale = 0)
	public long getRteId() {
		return this.rteId;
	}

	public void setRteId(long rteId) {
		this.rteId = rteId;
	}

	@Column(name = "TREATY", nullable = false, length = 6)
	public String getTreaty() {
		return this.treaty;
	}

	public void setTreaty(String treaty) {
		this.treaty = treaty;
	}

	@Column(name = "STATUS", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIME", nullable = false, length = 23)
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false, length = 23)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CREATED_BY", nullable = false, length = 5)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_PROCESS", nullable = false, length = 15)
	public String getCreatedProcess() {
		return this.createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTUAL_EVENT_DATE", nullable = false, length = 23)
	public Date getActualEventDate() {
		return this.actualEventDate;
	}

	public void setActualEventDate(Date actualEventDate) {
		this.actualEventDate = actualEventDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SYSTEM_DATE", nullable = false, length = 23)
	public Date getSystemDate() {
		return this.systemDate;
	}

	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFFECTIVE_DATE", nullable = false, length = 23)
	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Column(name = "EVENT", nullable = false, length = 35)
	public String getEvent() {
		return this.event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
