package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicySurrenderDTO {

	private String policyId;
	private Date effectiveDate;
	private BigDecimal transactionFees;
	private BigDecimal brokerTransactionFees;

	public BigDecimal getTransactionFees() {
		return transactionFees;
	}

	public void setTransactionFees(BigDecimal transactionFees) {
		this.transactionFees = transactionFees;
	}

	public BigDecimal getBrokerTransactionFees() {
		return brokerTransactionFees;
	}

	public void setBrokerTransactionFees(BigDecimal brokerTransactionFees) {
		this.brokerTransactionFees = brokerTransactionFees;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
}
