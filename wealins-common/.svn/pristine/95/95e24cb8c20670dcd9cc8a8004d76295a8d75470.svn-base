package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.webia.services.enums.AmountType;
import lu.wealins.common.dto.webia.services.enums.PaymentType;
import lu.wealins.common.dto.webia.services.enums.TransactionType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionFormDTO extends FormDataDTO {

	private Date effectiveDate;
	private TransactionType transactionType;
	private BigDecimal transactionFees;
	private BigDecimal brokerTransactionFees;
	private BigDecimal amount;
	private String currency;
	private AmountType amountType;
	private Boolean specificAmountByFund;
	private Collection<FundTransactionFormDTO> fundTransactionForms = new ArrayList<>();
	private Collection<TransferDTO> payments = new ArrayList<>();
	private Collection<TransferDTO> securitiesTransfer = new ArrayList<>();
	private PaymentType paymentType;
	private String creationUser;
	private Date creationDt;
	private String updateUser;
	private Date updateDt;

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public Date getCreationDt() {
		return creationDt;
	}

	public void setCreationDt(Date creationDt) {
		this.creationDt = creationDt;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Collection<TransferDTO> getPayments() {
		return payments;
	}

	public void setPayments(Collection<TransferDTO> payments) {
		this.payments = payments;
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


	public Collection<FundTransactionFormDTO> getFundTransactionForms() {
		return fundTransactionForms;
	}



	public void setFundTransactionForms(Collection<FundTransactionFormDTO> fundTransactionForms) {
		this.fundTransactionForms = fundTransactionForms;
	}

	public Collection<TransferDTO> getSecuritiesTransfer() {
		return securitiesTransfer;
	}

	public void setSecuritiesTransfer(Collection<TransferDTO> securitiesTransfer) {
		this.securitiesTransfer = securitiesTransfer;
	}
}
