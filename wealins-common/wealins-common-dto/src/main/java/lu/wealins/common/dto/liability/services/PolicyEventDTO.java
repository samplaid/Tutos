package lu.wealins.common.dto.liability.services;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyEventDTO {

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
	private Set<PolicyEndorsementDTO> policyEndorsements = new HashSet<PolicyEndorsementDTO>(0);

	public long getPevId() {
		return pevId;
	}

	public void setPevId(long pevId) {
		this.pevId = pevId;
	}

	public String getPolId() {
		return polId;
	}

	public void setPolId(String polId) {
		this.polId = polId;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getSystemDate() {
		return systemDate;
	}

	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}

	public int getCoverage() {
		return coverage;
	}

	public void setCoverage(int coverage) {
		this.coverage = coverage;
	}

	public Long getTransaction0() {
		return transaction0;
	}

	public void setTransaction0(Long transaction0) {
		this.transaction0 = transaction0;
	}

	public Date getActualEventDate() {
		return actualEventDate;
	}

	public void setActualEventDate(Date actualEventDate) {
		this.actualEventDate = actualEventDate;
	}

	public String getCreatedProcess() {
		return createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	public Integer getOccurrence() {
		return occurrence;
	}

	public void setOccurrence(Integer occurrence) {
		this.occurrence = occurrence;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public Set<PolicyEndorsementDTO> getPolicyEndorsements() {
		return policyEndorsements;
	}

	public void setPolicyEndorsements(Set<PolicyEndorsementDTO> policyEndorsements) {
		this.policyEndorsements = policyEndorsements;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

}
