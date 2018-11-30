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
@Table(name = "TRANSACTION_TAX_DETAILS")
public class TransactionTaxDetailsEntity extends AuditingEntity {
	
	private static final long serialVersionUID = 8133588838087086685L;
	
	@Id
	@Column(name = "TRN_TAX_DETAIL_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "TRN_TAX_ID", nullable = false, updatable = false)
	private TransactionTaxEntity transactionTax;

	@Column(name = "PREMIUM_DATE")
	private Date premiumDate;

	@Column(name = "PREMIUM_VALUE_BEFORE")
	private BigDecimal premiumValueBefore;

	@Column(name = "PREMIUM_VALUE_AFTER")
	private BigDecimal premiumValueAfter;

	@Column(name = "SPLIT_PERCENT")
	private BigDecimal splitPercent;

	@Column(name = "CAPITAL_GAIN_AMT")
	private BigDecimal capitalGainAmount;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TransactionTaxEntity getTransactionTax() {
		return transactionTax;
	}

	public void setTransactionTax(TransactionTaxEntity transactionTax) {
		this.transactionTax = transactionTax;
	}

	public Date getPremiumDate() {
		return premiumDate;
	}

	public void setPremiumDate(Date premiumDate) {
		this.premiumDate = premiumDate;
	}

	public BigDecimal getPremiumValueBefore() {
		return premiumValueBefore;
	}

	public void setPremiumValueBefore(BigDecimal premiumValueBefore) {
		this.premiumValueBefore = premiumValueBefore;
	}

	public BigDecimal getPremiumValueAfter() {
		return premiumValueAfter;
	}

	public void setPremiumValueAfter(BigDecimal premiumValueAfter) {
		this.premiumValueAfter = premiumValueAfter;
	}

	public BigDecimal getSplitPercent() {
		return splitPercent;
	}

	public void setSplitPercent(BigDecimal splitPercent) {
		this.splitPercent = splitPercent;
	}

	public BigDecimal getCapitalGainAmount() {
		return capitalGainAmount;
	}

	public void setCapitalGainAmount(BigDecimal capitalGainAmount) {
		this.capitalGainAmount = capitalGainAmount;
	}
}
