package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PolicyValuationHoldingDTO {

	private String fundId;
	
	private BigDecimal feeRate;
	
	private BigDecimal commissionRate;
	
	private String agent;

	private String fundSubType;

	private String fundDisplayName;

	private String fundCurrency;

	private BigDecimal units;

	private BigDecimal price;

	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date priceDate;

	private BigDecimal holdingValueFundCurreny;

	private BigDecimal holdingValuePolicyCurrency;

	private BigDecimal policyCurrencyRate;

	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date policyCurrencyDate;

	private BigDecimal holdingValueOtherCurrency;

	private BigDecimal otherCurrencyRate;

	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date otherCurrencyDate;

	public String getFundId() {
		return fundId;
	}

	public void setFundId(String fundId) {
		this.fundId = fundId;
	}

	public String getFundSubType() {
		return fundSubType;
	}

	public void setFundSubType(String fundSubType) {
		this.fundSubType = fundSubType;
	}

	public String getFundDisplayName() {
		return fundDisplayName;
	}

	public void setFundDisplayName(String fundDisplayName) {
		this.fundDisplayName = fundDisplayName;
	}

	public String getFundCurrency() {
		return fundCurrency;
	}

	public void setFundCurrency(String fundCurrency) {
		this.fundCurrency = fundCurrency;
	}

	public BigDecimal getUnits() {
		return units;
	}

	public void setUnits(BigDecimal units) {
		this.units = units;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}

	public BigDecimal getHoldingValueFundCurreny() {
		return holdingValueFundCurreny;
	}

	public void setHoldingValueFundCurreny(BigDecimal holdingValueFundCurreny) {
		this.holdingValueFundCurreny = holdingValueFundCurreny;
	}

	public BigDecimal getHoldingValuePolicyCurrency() {
		return holdingValuePolicyCurrency;
	}

	public void setHoldingValuePolicyCurrency(BigDecimal holdingValuePolicyCurrency) {
		this.holdingValuePolicyCurrency = holdingValuePolicyCurrency;
	}

	public BigDecimal getHoldingValueOtherCurrency() {
		return holdingValueOtherCurrency;
	}

	public void setHoldingValueOtherCurrency(BigDecimal holdingValueOtherCurrency) {
		this.holdingValueOtherCurrency = holdingValueOtherCurrency;
	}

	public BigDecimal getPolicyCurrencyRate() {
		return policyCurrencyRate;
	}

	public void setPolicyCurrencyRate(BigDecimal policyCurrencyRate) {
		this.policyCurrencyRate = policyCurrencyRate;
	}

	public Date getPolicyCurrencyDate() {
		return policyCurrencyDate;
	}

	public void setPolicyCurrencyDate(Date policyCurrencyDate) {
		this.policyCurrencyDate = policyCurrencyDate;
	}

	public BigDecimal getOtherCurrencyRate() {
		return otherCurrencyRate;
	}

	public void setOtherCurrencyRate(BigDecimal otherCurrencyRate) {
		this.otherCurrencyRate = otherCurrencyRate;
	}

	public Date getOtherCurrencyDate() {
		return otherCurrencyDate;
	}

	public void setOtherCurrencyDate(Date otherCurrencyDate) {
		this.otherCurrencyDate = otherCurrencyDate;
	}

	public BigDecimal getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}

	public BigDecimal getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(BigDecimal commissionRate) {
		this.commissionRate = commissionRate;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

}
