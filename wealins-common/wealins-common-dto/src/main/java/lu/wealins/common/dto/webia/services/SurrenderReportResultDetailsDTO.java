package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SurrenderReportResultDetailsDTO {
	private String policy;
	private Date transactiondate;
	private String transactionTaxEventType;
	private Date premiumDate;
	private BigDecimal premiumValueBefore;
	private BigDecimal policyValue;
	private BigDecimal withNetAmount;
	private BigDecimal premiumValueAfter;
	private BigDecimal splitPercent;
	private BigDecimal capitalGainAmount;

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

	/**
	 * @return the transactiondate
	 */
	public Date getTransactiondate() {
		return transactiondate;
	}

	/**
	 * @param transactiondate
	 *            the transactiondate to set
	 */
	public void setTransactiondate(Date transactiondate) {
		this.transactiondate = transactiondate;
	}

	/**
	 * @return the transactionTaxEventType
	 */
	public String getTransactionTaxEventType() {
		return this.transactionTaxEventType;
	}

	/**
	 * @param transactionTaxEventType
	 *            the transactionTaxEventType to set
	 */
	public void setTransactionTaxEventType(String transactionTaxEventType) {
		this.transactionTaxEventType = transactionTaxEventType;
	}

	/**
	 * @return the premiumDate
	 */
	public Date getPremiumDate() {
		return premiumDate;
	}

	/**
	 * @param premiumDate
	 *            the premiumDate to set
	 */
	public void setPremiumDate(Date premiumDate) {
		this.premiumDate = premiumDate;
	}

	/**
	 * @return the premiumValueBefore
	 */
	public BigDecimal getPremiumValueBefore() {
		return premiumValueBefore;
	}

	/**
	 * @param premiumValueBefore
	 *            the premiumValueBefore to set
	 */
	public void setPremiumValueBefore(BigDecimal premiumValueBefore) {
		this.premiumValueBefore = premiumValueBefore;
	}

	/**
	 * @return the policyValue
	 */
	public BigDecimal getPolicyValue() {
		return policyValue;
	}

	/**
	 * @param policyValue
	 *            the policyValue to set
	 */
	public void setPolicyValue(BigDecimal policyValue) {
		this.policyValue = policyValue;
	}

	/**
	 * @return the withNetAmount
	 */
	public BigDecimal getWithNetAmount() {
		return withNetAmount;
	}

	/**
	 * @param withNetAmount
	 *            the withNetAmount to set
	 */
	public void setWithNetAmount(BigDecimal withNetAmount) {
		this.withNetAmount = withNetAmount;
	}

	/**
	 * @return the premiumValueAfter
	 */
	public BigDecimal getPremiumValueAfter() {
		return premiumValueAfter;
	}

	/**
	 * @param premiumValueAfter
	 *            the premiumValueAfter to set
	 */
	public void setPremiumValueAfter(BigDecimal premiumValueAfter) {
		this.premiumValueAfter = premiumValueAfter;
	}

	/**
	 * @return the splitPercent
	 */
	public BigDecimal getSplitPercent() {
		return splitPercent;
	}

	/**
	 * @param splitPercent
	 *            the splitPercent to set
	 */
	public void setSplitPercent(BigDecimal splitPercent) {
		this.splitPercent = splitPercent;
	}

	/**
	 * @return the capitalGainAmount
	 */
	public BigDecimal getCapitalGainAmount() {
		return capitalGainAmount;
	}

	/**
	 * @param capitalGainAmount
	 *            the capitalGainAmount to set
	 */
	public void setCapitalGainAmount(BigDecimal capitalGainAmount) {
		this.capitalGainAmount = capitalGainAmount;
	}

}
