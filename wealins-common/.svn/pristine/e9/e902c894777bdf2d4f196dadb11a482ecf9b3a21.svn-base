package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PolicyValuationDTO {
	
	private String policyId;
	
	@JsonFormat(shape=JsonFormat.Shape.NUMBER)
	private Date date;
	
	private String policyCurrency;
	
	private String otherCurrency;
				
	private BigDecimal totalPolicyCurrency;
	
	private BigDecimal totalOtherCurrency;

	private List<PolicyValuationHoldingDTO> holdings = new ArrayList<>();

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPolicyCurrency() {
		return policyCurrency;
	}

	public void setPolicyCurrency(String policyCurrency) {
		this.policyCurrency = policyCurrency;
	}

	public String getOtherCurrency() {
		return otherCurrency;
	}

	public void setOtherCurrency(String otherCurrency) {
		this.otherCurrency = otherCurrency;
	}

	public BigDecimal getTotalPolicyCurrency() {
		return totalPolicyCurrency;
	}

	public void setTotalPolicyCurrency(BigDecimal totalPolicyCurrency) {
		this.totalPolicyCurrency = totalPolicyCurrency;
	}

	public BigDecimal getTotalOtherCurrency() {
		return totalOtherCurrency;
	}

	public void setTotalOtherCurrency(BigDecimal totalOtherCurrency) {
		this.totalOtherCurrency = totalOtherCurrency;
	}

	public List<PolicyValuationHoldingDTO> getHoldings() {
		return holdings;
	}

	public void setHoldings(List<PolicyValuationHoldingDTO> holdings) {
		this.holdings = holdings;
	}

}
