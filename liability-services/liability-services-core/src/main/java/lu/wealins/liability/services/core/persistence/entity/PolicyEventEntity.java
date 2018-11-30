package lu.wealins.liability.services.core.persistence.entity;
// Generated Dec 1, 2016 12:16:30 PM by Hibernate Tools 4.3.1

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.liability.services.core.utils.Historizable;

/**
 * PolicyEventEntity generated by hbm2java
 */
@Entity
@Table(name = "POLICY_EVENTS"

)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "createdProcess", "modifyProcess", "createdBy", "createdDate", "createdTime", "modifyDate", "modifyTime", "modifyBy" })
@Historizable(id = "pevId")
public class PolicyEventEntity implements java.io.Serializable {

	private long pevId;
	private String polId;
	private String policy;
	private String event;
	private Date effectiveDate;
	private Date systemDate;
	private int coverage;
	private Long transaction0;
	private Date actualEventDate;
	private String createdProcess;
	private Integer occurrence;
	private int status;
	private String createdBy;
	private Date createdDate;
	private Date createdTime;
	private Collection<PolicyEndorsementEntity> policyEndorsements = new HashSet<PolicyEndorsementEntity>(0);

	@Id
	@Column(name = "PEV_ID", unique = true, nullable = false, precision = 15, scale = 0)
	public long getPevId() {
		return this.pevId;
	}

	public void setPevId(long pevId) {
		this.pevId = pevId;
	}

	@Column(name = "FK_POLICIESPOL_ID", length = 14)
	public String getPolId() {
		return this.polId;
	}

	public void setPolId(String polId) {
		this.polId = polId;
	}

	@Column(name = "EVENT", length = 10)
	public String getEvent() {
		return this.event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "effective_date", nullable = false, length = 23)
	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "system_date", nullable = false, length = 23)
	public Date getSystemDate() {
		return this.systemDate;
	}

	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}

	@Column(name = "COVERAGE", nullable = false)
	public int getCoverage() {
		return this.coverage;
	}

	public void setCoverage(int coverage) {
		this.coverage = coverage;
	}

	@Column(name = "POLICY", nullable = false, length = 14)
	public String getPolicy() {
		return this.policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	@Column(name = "TRANSACTION0", precision = 15, scale = 0)
	public Long getTransaction0() {
		return this.transaction0;
	}

	public void setTransaction0(Long transaction0) {
		this.transaction0 = transaction0;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTUAL_EVENT_DATE", length = 23)
	public Date getActualEventDate() {
		return this.actualEventDate;
	}

	public void setActualEventDate(Date actualEventDate) {
		this.actualEventDate = actualEventDate;
	}

	@Column(name = "CREATED_PROCESS", nullable = false, length = 15)
	public String getCreatedProcess() {
		return this.createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	@Column(name = "OCCURRENCE")
	public Integer getOccurrence() {
		return this.occurrence;
	}

	public void setOccurrence(Integer occurrence) {
		this.occurrence = occurrence;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pevId")
	public Collection<PolicyEndorsementEntity> getPolicyEndorsements() {
		return this.policyEndorsements;
	}

	public void setPolicyEndorsements(Collection<PolicyEndorsementEntity> policyEndorsements) {
		this.policyEndorsements = policyEndorsements;
	}

}
