package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommissionToPayWrapperDTO {
	private String agentId;
	private String currency;
	private BigDecimal totalAmount;
	private List<CommissionToPayDTO> commissionToPayEntities = new ArrayList<>();
	private Long transferId;


	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getTransferId() {
		return transferId;
	}

	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}

	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the commissionToPayEntities
	 */
	public List<CommissionToPayDTO> getCommissionToPayEntities() {
		return commissionToPayEntities;
	}

	/**
	 * @param commissionToPayEntities the commissionToPayEntities to set
	 */
	public void setCommissionToPayEntities(List<CommissionToPayDTO> commissionToPayEntities) {
		this.commissionToPayEntities = commissionToPayEntities;
	}

}
