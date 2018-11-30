package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionTaxDetailsDTO {
	private Long id;
	private Long transactionTaxId;
	private Date premiumDate;
	private BigDecimal premiumValueBefore;
	private BigDecimal premiumValueAfter;
	private BigDecimal splitPercent;
	private BigDecimal capitalGainAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getTransactionTaxId() {
		return transactionTaxId;
	}
	public void setTransactionTaxId(Long transactionTaxId) {
		this.transactionTaxId = transactionTaxId;
	}

	public Date getPremiumDate() {
		return premiumDate;
	}

	public void setPremiumDate(Date premiumDate) {
		this.premiumDate = premiumDate;
	}

	public BigDecimal getPremiumValueBefore() {
		return premiumValueBefore;
	}

	public void setPremiumValueBefore(BigDecimal premiumValueBefore) {
		this.premiumValueBefore = premiumValueBefore;
	}

	public BigDecimal getPremiumValueAfter() {
		return premiumValueAfter;
	}

	public void setPremiumValueAfter(BigDecimal premiumValueAfter) {
		this.premiumValueAfter = premiumValueAfter;
	}

	public BigDecimal getSplitPercent() {
		return splitPercent;
	}

	public void setSplitPercent(BigDecimal splitPercent) {
		this.splitPercent = splitPercent;
	}

	public BigDecimal getCapitalGainAmount() {
		return capitalGainAmount;
	}

	public void setCapitalGainAmount(BigDecimal capitalGainAmount) {
		this.capitalGainAmount = capitalGainAmount;
	}
}
