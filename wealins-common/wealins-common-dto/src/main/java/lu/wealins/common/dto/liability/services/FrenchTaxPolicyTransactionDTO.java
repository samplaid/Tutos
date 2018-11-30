/**
 * 
 */
package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author NGA
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FrenchTaxPolicyTransactionDTO {
	private String policy;
	private Integer lastTransactionId;
	private String transactionType;
	private Integer transactionsAggregated;
	private Date effectiveDate;
	private BigDecimal netTransactionAmount;
	private String transactionCurrency;
	private String accountCurrency;


	/**
	 * @return the policy
	 */
	public String getPolicy() {
		return policy;
	}

	/**
	 * @param policy
	 *            the policy to set
	 */
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	public Integer getLastTransactionId() {
		return lastTransactionId;
	}

	public void setLastTransactionId(Integer lastTransactionId) {
		this.lastTransactionId = lastTransactionId;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType
	 *            the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the transactionsAggregated
	 */
	public Integer getTransactionsAggregated() {
		return transactionsAggregated;
	}

	/**
	 * @param transactionsAggregated
	 *            the transactionsAggregated to set
	 */
	public void setTransactionsAggregated(Integer transactionsAggregated) {
		this.transactionsAggregated = transactionsAggregated;
	}

	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate
	 *            the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the netTransactionAmount
	 */
	public BigDecimal getNetTransactionAmount() {
		return netTransactionAmount;
	}

	/**
	 * @param netTransactionAmount
	 *            the netTransactionAmount to set
	 */
	public void setNetTransactionAmount(BigDecimal netTransactionAmount) {
		this.netTransactionAmount = netTransactionAmount;
	}

	/**
	 * @return the transactionCurrency
	 */
	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	/**
	 * @param transactionCurrency
	 *            the transactionCurrency to set
	 */
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	/**
	 * @return the accountCurrency
	 */
	public String getAccountCurrency() {
		return accountCurrency;
	}

	/**
	 * @param accountCurrency
	 *            the accountCurrency to set
	 */
	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}


}
