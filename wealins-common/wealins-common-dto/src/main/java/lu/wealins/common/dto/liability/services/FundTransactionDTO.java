package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FundTransactionDTO {

	private long ftrId;
	private FundTransactionDateDTO fundTransactionDate;
	private String polId;
	private long pfiId;
	private Long trnId;
	private Integer priority;
	private String productLine;
	private String accset;
	private Integer holding;
	private String policyId;
	private Integer coverage;
	private BigDecimal price;
	private BigDecimal units;
	private Integer action0;
	private Integer unitType;
	private Date date0;
	private Date activityDate;
	private Date pricingDate;
	private BigDecimal exchangeRate;
	private String fundCurrency;
	private String policyCurrency;
	private BigDecimal valuePolCcy;
	private BigDecimal valueFundCcy;
	private String fund;
	private int eventType;
	private Date dateFwdPriceRep;
	private Long transaction0;
	private int lineNo;
	private BigDecimal holdingValuation;
	private Integer status;
	private String createdBy;
	private Date createdDate;
	private Date createdTime;
	private String createdProcess;
	private Date createdSystemDate;
	private String modifyBy;
	private Date modifyDate;
	private Date modifyTime;
	private String modifyProcess;
	private Date modifySystemDate;
	private Integer jobNumber;
	private Long fkFundTransacftrId;
	private Integer reciprocal;
	private Integer basis;
	private BigDecimal actFundUnits;
	private BigDecimal actFundValueFundCcy;
	private BigDecimal actFundValuePolicyCcy;
	private BigDecimal actFundFactor;
	private BigDecimal marginValue;
	private BigDecimal marginValuePrem;
	@JsonIgnore
	private Set<AccountTransactionDTO> accountTransactions = new HashSet<AccountTransactionDTO>(0);

	public long getFtrId() {
		return ftrId;
	}

	public void setFtrId(long ftrId) {
		this.ftrId = ftrId;
	}

	public String getPolId() {
		return polId;
	}

	public void setPolId(String polId) {
		this.polId = polId;
	}

	public long getPfiId() {
		return pfiId;
	}

	public void setPfiId(long pfiId) {
		this.pfiId = pfiId;
	}

	public Long getTrnId() {
		return trnId;
	}

	public void setTrnId(Long trnId) {
		this.trnId = trnId;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public String getAccset() {
		return accset;
	}

	public void setAccset(String accset) {
		this.accset = accset;
	}

	public Integer getHolding() {
		return holding;
	}

	public void setHolding(Integer holding) {
		this.holding = holding;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public Integer getCoverage() {
		return coverage;
	}

	public void setCoverage(Integer coverage) {
		this.coverage = coverage;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getUnits() {
		return units;
	}

	public void setUnits(BigDecimal units) {
		this.units = units;
	}

	public Integer getAction0() {
		return action0;
	}

	public void setAction0(Integer action0) {
		this.action0 = action0;
	}

	public Integer getUnitType() {
		return unitType;
	}

	public void setUnitType(Integer unitType) {
		this.unitType = unitType;
	}

	public Date getDate0() {
		return date0;
	}

	public void setDate0(Date date0) {
		this.date0 = date0;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public Date getPricingDate() {
		return pricingDate;
	}

	public void setPricingDate(Date pricingDate) {
		this.pricingDate = pricingDate;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getFundCurrency() {
		return fundCurrency;
	}

	public void setFundCurrency(String fundCurrency) {
		this.fundCurrency = fundCurrency;
	}

	public String getPolicyCurrency() {
		return policyCurrency;
	}

	public void setPolicyCurrency(String policyCurrency) {
		this.policyCurrency = policyCurrency;
	}

	public BigDecimal getValuePolCcy() {
		return valuePolCcy;
	}

	public void setValuePolCcy(BigDecimal valuePolCcy) {
		this.valuePolCcy = valuePolCcy;
	}

	public BigDecimal getValueFundCcy() {
		return valueFundCcy;
	}

	public void setValueFundCcy(BigDecimal valueFundCcy) {
		this.valueFundCcy = valueFundCcy;
	}

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public Date getDateFwdPriceRep() {
		return dateFwdPriceRep;
	}

	public void setDateFwdPriceRep(Date dateFwdPriceRep) {
		this.dateFwdPriceRep = dateFwdPriceRep;
	}

	public Long getTransaction0() {
		return transaction0;
	}

	public void setTransaction0(Long transaction0) {
		this.transaction0 = transaction0;
	}

	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public BigDecimal getHoldingValuation() {
		return holdingValuation;
	}

	public void setHoldingValuation(BigDecimal holdingValuation) {
		this.holdingValuation = holdingValuation;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	public String getCreatedProcess() {
		return createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	public Date getCreatedSystemDate() {
		return createdSystemDate;
	}

	public void setCreatedSystemDate(Date createdSystemDate) {
		this.createdSystemDate = createdSystemDate;
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

	public Date getModifySystemDate() {
		return modifySystemDate;
	}

	public void setModifySystemDate(Date modifySystemDate) {
		this.modifySystemDate = modifySystemDate;
	}

	public Integer getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(Integer jobNumber) {
		this.jobNumber = jobNumber;
	}

	public Long getFkFundTransacftrId() {
		return fkFundTransacftrId;
	}

	public void setFkFundTransacftrId(Long fkFundTransacftrId) {
		this.fkFundTransacftrId = fkFundTransacftrId;
	}

	public Integer getReciprocal() {
		return reciprocal;
	}

	public void setReciprocal(Integer reciprocal) {
		this.reciprocal = reciprocal;
	}

	public Integer getBasis() {
		return basis;
	}

	public void setBasis(Integer basis) {
		this.basis = basis;
	}

	public BigDecimal getActFundUnits() {
		return actFundUnits;
	}

	public void setActFundUnits(BigDecimal actFundUnits) {
		this.actFundUnits = actFundUnits;
	}

	public BigDecimal getActFundValueFundCcy() {
		return actFundValueFundCcy;
	}

	public void setActFundValueFundCcy(BigDecimal actFundValueFundCcy) {
		this.actFundValueFundCcy = actFundValueFundCcy;
	}

	public BigDecimal getActFundValuePolicyCcy() {
		return actFundValuePolicyCcy;
	}

	public void setActFundValuePolicyCcy(BigDecimal actFundValuePolicyCcy) {
		this.actFundValuePolicyCcy = actFundValuePolicyCcy;
	}

	public BigDecimal getActFundFactor() {
		return actFundFactor;
	}

	public void setActFundFactor(BigDecimal actFundFactor) {
		this.actFundFactor = actFundFactor;
	}

	public BigDecimal getMarginValue() {
		return marginValue;
	}

	public void setMarginValue(BigDecimal marginValue) {
		this.marginValue = marginValue;
	}

	public BigDecimal getMarginValuePrem() {
		return marginValuePrem;
	}

	public void setMarginValuePrem(BigDecimal marginValuePrem) {
		this.marginValuePrem = marginValuePrem;
	}

	public Set<AccountTransactionDTO> getAccountTransactions() {
		return accountTransactions;
	}

	public void setAccountTransactions(Set<AccountTransactionDTO> accountTransactions) {
		this.accountTransactions = accountTransactions;
	}

	public FundTransactionDateDTO getFundTransactionDate() {
		return fundTransactionDate;
	}

	public void setFundTransactionDate(FundTransactionDateDTO fundTransactionDate) {
		this.fundTransactionDate = fundTransactionDate;
	}

}
