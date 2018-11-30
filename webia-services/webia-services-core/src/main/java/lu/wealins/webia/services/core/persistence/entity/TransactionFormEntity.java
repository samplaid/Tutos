package lu.wealins.webia.services.core.persistence.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lu.wealins.common.dto.webia.services.enums.AmountType;
import lu.wealins.common.dto.webia.services.enums.PaymentType;
import lu.wealins.common.dto.webia.services.enums.TransactionType;

@Entity
@Table(name = "TRANSACTION_FORM")
public class TransactionFormEntity extends WorkflowFormEntity {

	private static final long serialVersionUID = 1326032200561777599L;

	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;

	@Column(name = "TRANSACTION_TYPE")
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	@Column(name = "TRANSACTION_FEES")
	private BigDecimal transactionFees;
	
	@Column(name = "BROKER_TRANSACTION_FEES")
	private BigDecimal brokerTransactionFees;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "AMOUNT_TYPE")
	@Enumerated(EnumType.STRING)
	private AmountType amountType;

	@Column(name = "PAYMENT_TYPE")
	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;

	@Column(name = "SPECIFIC_AMOUNT_BY_FUND")
	private Boolean specificAmountByFund;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@OrderBy("fundTransactionFormId")
	@JoinColumn(name = "TRANSACTION_ID")
	private Collection<FundTransactionFormEntity> fundTransactionForms = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE })
	@Where(clause = "TRANSFER_TYPE = 'CASH_TRANSFER'")
	@JoinColumn(name = "TRANSACTION_ID")
	private Collection<TransferEntity> payments = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE })
	@Where(clause = "TRANSFER_TYPE = 'SECURITY_TRANSFER'")
	@JoinColumn(name = "TRANSACTION_ID")
	private Collection<TransferEntity> securitiesTransfer = new ArrayList<>();

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public void setSecuritiesTransfer(Collection<TransferEntity> securitiesTransfer) {
		this.securitiesTransfer = securitiesTransfer;
	}

	public Collection<TransferEntity> getPayments() {
		return payments;
	}

	public void setPayments(Collection<TransferEntity> payments) {
		this.payments = payments;
	}

	public Collection<TransferEntity> getSecuritiesTransfer() {
		return securitiesTransfer;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public AmountType getAmountType() {
		return amountType;
	}

	public void setAmountType(AmountType amountType) {
		this.amountType = amountType;
	}

	public Boolean getSpecificAmountByFund() {
		return specificAmountByFund;
	}

	public void setSpecificAmountByFund(Boolean specificAmountByFund) {
		this.specificAmountByFund = specificAmountByFund;
	}

	public Collection<FundTransactionFormEntity> getFundTransactionForms() {
		return fundTransactionForms;
	}

	public void setFundTransactionForms(Collection<FundTransactionFormEntity> fundTransactionForms) {
		this.fundTransactionForms = fundTransactionForms;
	}
}
