package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.liability.services.FundLiteDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractFundFormDTO {

	private String fundId;
	private String fundTp;
	private BigDecimal split;
	private String creationUser;
	private Date creationDt;
	private String updateUser;
	private Date updateDt;
	private FundLiteDTO fund;
	private BigDecimal valuationAmt;

	public BigDecimal getValuationAmt() {
		return valuationAmt;
	}

	public void setValuationAmt(BigDecimal valuationAmt) {
		this.valuationAmt = valuationAmt;
	}

	public String getFundTp() {
		return fundTp;
	}

	public void setFundTp(String fundTp) {
		this.fundTp = fundTp;
	}

	public String getFundId() {
		return fundId;
	}
	public void setFundId(String fundId) {
		this.fundId = fundId;
	}
	public BigDecimal getSplit() {
		return split;
	}
	public void setSplit(BigDecimal split) {
		this.split = split;
	}
	public String getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	public Date getCreationDt() {
		return creationDt;
	}
	public void setCreationDt(Date creationDt) {
		this.creationDt = creationDt;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}
	public FundLiteDTO getFund() {
		return fund;
	}
	public void setFund(FundLiteDTO fund) {
		this.fund = fund;
	}
}
