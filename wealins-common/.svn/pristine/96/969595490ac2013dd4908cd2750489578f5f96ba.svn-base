package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;
import java.util.Collection;

public class PolicyTransactionsHistoryDetailsDTO {
	private Collection<TransactionHistoryDetailsDTO> transactionHistoryDetails;
	private BigDecimal commission;
	private String commissionCurrency;
	private String agentLabel;

	public static PolicyTransactionDetailsBuilder builder() {
		return new PolicyTransactionsHistoryDetailsDTO.PolicyTransactionDetailsBuilder();
	}

	/**
	 * @return the transactionHistoryDetails
	 */
	public Collection<TransactionHistoryDetailsDTO> getTransactionHistoryDetails() {
		return transactionHistoryDetails;
	}

	/**
	 * @param transactionHistoryDetails
	 *            the transactionHistoryDetails to set
	 */
	public void setTransactionHistoryDetails(Collection<TransactionHistoryDetailsDTO> transactionHistoryDetails) {
		this.transactionHistoryDetails = transactionHistoryDetails;
	}

	/**
	 * @return the commission
	 */
	public BigDecimal getCommission() {
		return commission;
	}

	/**
	 * @param commission
	 *            the commission to set
	 */
	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	/**
	 * @return the commissionCurrency
	 */
	public String getCommissionCurrency() {
		return commissionCurrency;
	}

	/**
	 * @param commissionCurrency
	 *            the commissionCurrency to set
	 */
	public void setCommissionCurrency(String commissionCurrency) {
		this.commissionCurrency = commissionCurrency;
	}

	public String getAgentLabel() {
		return agentLabel;
	}

	public void setAgentLabel(String agentLabel) {
		this.agentLabel = agentLabel;
	}

	public static class PolicyTransactionDetailsBuilder {
		private PolicyTransactionsHistoryDetailsDTO instance = new PolicyTransactionsHistoryDetailsDTO();

		public PolicyTransactionDetailsBuilder() {
		}

		public PolicyTransactionDetailsBuilder withCommission(BigDecimal commission) {
			instance.commission = commission;
			return this;
		}

		public PolicyTransactionDetailsBuilder withCommissionCurrency(String commissionCurrency) {
			instance.commissionCurrency = commissionCurrency;
			return this;
		}

		public PolicyTransactionDetailsBuilder withDetails(Collection<TransactionHistoryDetailsDTO> details) {
			instance.transactionHistoryDetails = details;
			return this;
		}

		public PolicyTransactionDetailsBuilder withAgentLabel(String agentLabel) {
			instance.setAgentLabel(agentLabel);
			return this;
		}
		public PolicyTransactionsHistoryDetailsDTO build() {
			return instance;
		}
	}

}
