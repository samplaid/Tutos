package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionTaxDTO {

	private Long transactionTaxId;
	private String origin;
	private Integer originId;
	private String currency;
	private String taxCountry;
	private Date policyEffectDate;
	private Date transactionDate;
	private String transactionType;
	private Long previousTransactionId;
	private BigDecimal policyValue;
	private BigDecimal transactionNetAmount;
	private Integer status;
	private String policy;
	private String user;
	private String creationUser;

	/**
	 * @return the creationUser
	 */
	public String getCreationUser() {
		return creationUser;
	}

	/**
	 * @param creationUser
	 *            the creationUser to set
	 */
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	/**
	 * @return the transactionTaxId
	 */
	public Long getTransactionTaxId() {
		return transactionTaxId;
	}

	/**
	 * @param transactionTaxId the transactionTaxId to set
	 */
	public void setTransactionTaxId(Long transactionTaxId) {
		this.transactionTaxId = transactionTaxId;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
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
	 * @return the taxCountry
	 */
	public String getTaxCountry() {
		return taxCountry;
	}

	/**
	 * @param taxCountry the taxCountry to set
	 */
	public void setTaxCountry(String taxCountry) {
		this.taxCountry = taxCountry;
	}

	/**
	 * @return the policyEffectDate
	 */
	public Date getPolicyEffectDate() {
		return policyEffectDate;
	}

	/**
	 * @param policyEffectDate the policyEffectDate to set
	 */
	public void setPolicyEffectDate(Date policyEffectDate) {
		this.policyEffectDate = policyEffectDate;
	}

	/**
	 * @return the transactionDate
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the previousTrnsactionId
	 */
	public Long getPreviousTransactionId() {
		return previousTransactionId;
	}

	/**
	 * @param previousTrnsactionId the previousTrnsactionId to set
	 */
	public void setPreviousTransactionId(Long previousTransactionId) {
		this.previousTransactionId = previousTransactionId;
	}

	/**
	 * @return the policyValue
	 */
	public BigDecimal getPolicyValue() {
		return policyValue;
	}

	/**
	 * @param policyValue the policyValue to set
	 */
	public void setPolicyValue(BigDecimal policyValue) {
		this.policyValue = policyValue;
	}

	/**
	 * @return the transactionNetAmount
	 */
	public BigDecimal getTransactionNetAmount() {
		return transactionNetAmount;
	}

	/**
	 * @param transactionNetAmount the transactionNetAmount to set
	 */
	public void setTransactionNetAmount(BigDecimal transactionNetAmount) {
		this.transactionNetAmount = transactionNetAmount;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the originId
	 */
	public Integer getOriginId() {
		return originId;
	}

	/**
	 * @param originId the originId to set
	 */
	public void setOriginId(Integer originId) {
		this.originId = originId;
	}

	/**
	 * @return the policy
	 */
	public String getPolicy() {
		return policy;
	}

	/**
	 * @param policy the policy to set
	 */
	public void setPolicy(String policy) {
		this.policy = policy;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((originId == null) ? 0 : originId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TransactionTaxDTO)) {
			return false;
		}
		TransactionTaxDTO other = (TransactionTaxDTO) obj;
		if (originId == null) {
			if (other.originId != null) {
				return false;
			}
		} else if (!originId.equals(other.originId)) {
			return false;
		}
		return true;
	}

}
