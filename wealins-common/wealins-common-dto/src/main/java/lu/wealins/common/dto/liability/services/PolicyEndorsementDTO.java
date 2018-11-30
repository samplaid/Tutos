package lu.wealins.common.dto.liability.services;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyEndorsementDTO {

	private long penId;
	private String polId;
	private long pevId;
	private Integer endorsementNo;
	private Integer coverage;
	private String type;
	private String valueToken;
	private String valueBefore;
	private String valueAfter;
	private Date effectiveDate;
	private Integer status;
	private String createdProcess;
	private String createdBy;
	private Date createdDate;
	private Date createdSystemDate;
	private Date createdTime;

	public long getPenId() {
		return penId;
	}

	public void setPenId(long penId) {
		this.penId = penId;
	}

	public String getPolId() {
		return polId;
	}

	public void setPolId(String polId) {
		this.polId = polId;
	}

	public Integer getEndorsementNo() {
		return endorsementNo;
	}

	public void setEndorsementNo(Integer endorsementNo) {
		this.endorsementNo = endorsementNo;
	}

	public Integer getCoverage() {
		return coverage;
	}

	public void setCoverage(Integer coverage) {
		this.coverage = coverage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValueToken() {
		return valueToken;
	}

	public void setValueToken(String valueToken) {
		this.valueToken = valueToken;
	}

	public String getValueBefore() {
		return valueBefore;
	}

	public void setValueBefore(String valueBefore) {
		this.valueBefore = valueBefore;
	}

	public String getValueAfter() {
		return valueAfter;
	}

	public void setValueAfter(String valueAfter) {
		this.valueAfter = valueAfter;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Date getCreatedSystemDate() {
		return createdSystemDate;
	}

	public void setCreatedSystemDate(Date createdSystemDate) {
		this.createdSystemDate = createdSystemDate;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public long getPevId() {
		return pevId;
	}

	public void setPevId(long pevId) {
		this.pevId = pevId;
	}

}
