package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyFundDTO {

	private String policyId;
	private String policyCurrency;
	private String fundId;
	private String fundSubType;
	private String fundDisplayName;
	private String fundCurrency;
    
	private BigDecimal feeRate;
	private BigDecimal commissionRate;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getPolicyCurrency() {
		return policyCurrency;
	}

	public void setPolicyCurrency(String policyCurrency) {
		this.policyCurrency = policyCurrency;
	}

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

	
}
