package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyEntryFeesDto {

	private BigDecimal entryFees;

	private BigDecimal brokerEntryFees;

	private Boolean isPercentage;

	public BigDecimal getEntryFees() {
		return entryFees;
	}

	public void setEntryFees(BigDecimal entryFees) {
		this.entryFees = entryFees;
	}

	public BigDecimal getBrokerEntryFees() {
		return brokerEntryFees;
	}

	public void setBrokerEntryFees(BigDecimal brokerEntryFees) {
		this.brokerEntryFees = brokerEntryFees;
	}

	public Boolean getIsPercentage() {
		return isPercentage;
	}

	public void setIsPercentage(Boolean isPercentage) {
		this.isPercentage = isPercentage;
	}
}
