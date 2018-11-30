package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyFundInstructionDetailDTO {

	private long pidId;
	private Long pfiId;
	private String toFund;
	private BigDecimal toFundPcg;
	private String createdBy;
	private Date createdDate;
	private Date createdTime;
	private String createdProcess;
	private String modifyBy;
	private Date modifyDate;
	private Date modifyTime;
	private String modifyProcess;
	private Integer overrideNavPriceBasis;

	public long getPidId() {
		return pidId;
	}

	public void setPidId(long pidId) {
		this.pidId = pidId;
	}

	public Long getPfiId() {
		return pfiId;
	}

	public void setPfiId(Long pfiId) {
		this.pfiId = pfiId;
	}

	public String getToFund() {
		return toFund;
	}

	public void setToFund(String toFund) {
		this.toFund = toFund;
	}

	public BigDecimal getToFundPcg() {
		return toFundPcg;
	}

	public void setToFundPcg(BigDecimal toFundPcg) {
		this.toFundPcg = toFundPcg;
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

	public String getCreatedProcess() {
		return createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
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

	public String getModifyProcess() {
		return modifyProcess;
	}

	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
	}

	public Integer getOverrideNavPriceBasis() {
		return overrideNavPriceBasis;
	}

	public void setOverrideNavPriceBasis(Integer overrideNavPriceBasis) {
		this.overrideNavPriceBasis = overrideNavPriceBasis;
	}

}
