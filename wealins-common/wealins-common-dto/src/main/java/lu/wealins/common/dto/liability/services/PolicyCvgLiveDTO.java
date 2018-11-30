package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyCvgLiveDTO {

	private String pclId;
	private String pocId;
	private Long client;
	private Integer lifeNumber;
	private Integer smoker;
	private Integer sex;
	private BigDecimal issueAge;
	private Integer ageRating;
	private int coverage;
	private String policy;
	private BigDecimal addnFactor;
	private BigDecimal addnRpm;
	private String ratingReason;
	private String createdProcess;
	private String modifyProcess;
	private int status;
	private String createdBy;
	private Date createdDate;
	private Date createdTime;
	private String modifyBy;
	private Date modifyDate;
	private Date modifyTime;
	private Integer ratingTerm;

	public String getPclId() {
		return pclId;
	}

	public void setPclId(String pclId) {
		this.pclId = pclId;
	}

	public String getPocId() {
		return pocId;
	}

	public void setPocId(String pocId) {
		this.pocId = pocId;
	}

	public Long getClient() {
		return client;
	}

	public void setClient(Long client) {
		this.client = client;
	}

	public Integer getLifeNumber() {
		return lifeNumber;
	}

	public void setLifeNumber(Integer lifeNumber) {
		this.lifeNumber = lifeNumber;
	}

	public Integer getSmoker() {
		return smoker;
	}

	public void setSmoker(Integer smoker) {
		this.smoker = smoker;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public BigDecimal getIssueAge() {
		return issueAge;
	}

	public void setIssueAge(BigDecimal issueAge) {
		this.issueAge = issueAge;
	}

	public Integer getAgeRating() {
		return ageRating;
	}

	public void setAgeRating(Integer ageRating) {
		this.ageRating = ageRating;
	}

	public int getCoverage() {
		return coverage;
	}

	public void setCoverage(int coverage) {
		this.coverage = coverage;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public BigDecimal getAddnFactor() {
		return addnFactor;
	}

	public void setAddnFactor(BigDecimal addnFactor) {
		this.addnFactor = addnFactor;
	}

	public BigDecimal getAddnRpm() {
		return addnRpm;
	}

	public void setAddnRpm(BigDecimal addnRpm) {
		this.addnRpm = addnRpm;
	}

	public String getRatingReason() {
		return ratingReason;
	}

	public void setRatingReason(String ratingReason) {
		this.ratingReason = ratingReason;
	}

	public String getCreatedProcess() {
		return createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	public String getModifyProcess() {
		return modifyProcess;
	}

	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
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

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getRatingTerm() {
		return ratingTerm;
	}

	public void setRatingTerm(Integer ratingTerm) {
		this.ratingTerm = ratingTerm;
	}

}
