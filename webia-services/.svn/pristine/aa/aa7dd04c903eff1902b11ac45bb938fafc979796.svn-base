package lu.wealins.webia.services.core.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTION_TAX")
public class TransactionTaxEntity extends AuditingEntity {

	private static final long serialVersionUID = 1443887014255256052L;

	@Id
	@Column(name = "TRN_TAX_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ORIGIN")
	private String origin;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "TAX_COUNTRY")
	private String taxCountry;

	@Column(name = "POLICY_EFFECT_DATE")
	private Date policyEffectDate;

	@Column(name = "TRN_DATE")
	private Date transactionDate;

	@Column(name = "TRN_TYPE")
	private String transactionType;

	@OneToOne
	@JoinColumn(name = "PREV_TRN_TAX_ID")
	private TransactionTaxEntity previousTransactionTax;

	@Column(name = "POLICY_VALUE")
	private BigDecimal policyValue;

	@Column(name = "TRN_NET_AMT")
	private BigDecimal transactionNetAmount;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "POLICY")
	private String policy;

	@Column(name = "ORIGIN_ID")
	private BigDecimal originId;

	@Column(name = "CPS_USER")
	private String user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTaxCountry() {
		return taxCountry;
	}

	public void setTaxCountry(String taxCountry) {
		this.taxCountry = taxCountry;
	}

	public Date getPolicyEffectDate() {
		return policyEffectDate;
	}

	public void setPolicyEffectDate(Date policyEffectDate) {
		this.policyEffectDate = policyEffectDate;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public TransactionTaxEntity getPreviousTransactionTax() {
		return previousTransactionTax;
	}

	public void setPreviousTransactionTax(TransactionTaxEntity previousTransactionTax) {
		this.previousTransactionTax = previousTransactionTax;
	}

	public BigDecimal getPolicyValue() {
		return policyValue;
	}

	public void setPolicyValue(BigDecimal policyValue) {
		this.policyValue = policyValue;
	}

	public BigDecimal getTransactionNetAmount() {
		return transactionNetAmount;
	}

	public void setTransactionNetAmount(BigDecimal transactionNetAmount) {
		this.transactionNetAmount = transactionNetAmount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
	 * @return the originId
	 */
	public BigDecimal getOriginId() {
		return originId;
	}

	/**
	 * @param originId the originId to set
	 */
	public void setOriginId(BigDecimal originId) {
		this.originId = originId;
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

}
