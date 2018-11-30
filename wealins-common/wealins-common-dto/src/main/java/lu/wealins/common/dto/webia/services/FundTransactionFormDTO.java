package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.webia.services.enums.FundTransactionInputType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FundTransactionFormDTO extends AbstractFundFormDTO {

	private Long fundTransactionFormId;
	private Long transactionId;
	private FundTransactionInputType inputType;
	private BigDecimal percentage;
	private BigDecimal amount;
	private String currency;
	private BigDecimal units;
	private Boolean closure;

	public Boolean getClosure() {
		return closure;
	}

	public void setClosure(Boolean closure) {
		this.closure = closure;
	}
	public Long getFundTransactionFormId() {
		return fundTransactionFormId;
	}
	public void setFundTransactionFormId(Long fundTransactionFormId) {
		this.fundTransactionFormId = fundTransactionFormId;
	}
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public FundTransactionInputType getInputType() {
		return inputType;
	}
	public void setInputType(FundTransactionInputType inputType) {
		this.inputType = inputType;
	}
	public BigDecimal getPercentage() {
		return percentage;
	}
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getUnits() {
		return units;
	}
	public void setUnits(BigDecimal units) {
		this.units = units;
	}

}
