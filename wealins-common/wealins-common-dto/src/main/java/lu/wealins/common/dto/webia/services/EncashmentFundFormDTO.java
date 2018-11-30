package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EncashmentFundFormDTO {

	private Integer cashFundFormId;
	private Integer formId;
	private String fundId;
	private BigDecimal cashAmt;
	private Date cashDt;
	private String cashCurrency;
	private String cashStatus;
	private String creationUser;
	private Date creationDt;
	private String updateUser;
	private Date updateDt;
	
	public Integer getCashFundFormId() {
		return cashFundFormId;
	}
	public void setCashFundFormId(Integer cashFundFormId) {
		this.cashFundFormId = cashFundFormId;
	}
	public Integer getFormId() {
		return formId;
	}
	public void setFormId(Integer formId) {
		this.formId = formId;
	}
	public String getFundId() {
		return fundId;
	}
	public void setFundId(String fundId) {
		this.fundId = fundId;
	}
	public BigDecimal getCashAmt() {
		return cashAmt;
	}
	public void setCashAmt(BigDecimal cashAmt) {
		this.cashAmt = cashAmt;
	}
	public Date getCashDt() {
		return cashDt;
	}
	public void setCashDt(Date cashDt) {
		this.cashDt = cashDt;
	}
	public String getCashCurrency() {
		return cashCurrency;
	}
	public void setCashCurrency(String cashCurrency) {
		this.cashCurrency = cashCurrency;
	}
	public String getCashStatus() {
		return cashStatus;
	}
	public void setCashStatus(String cashStatus) {
		this.cashStatus = cashStatus;
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
	
	

}
