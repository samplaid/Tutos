package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SAP_OPEN_BALANCE")
public class SapOpenBalanceEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SAP_OPENBAL_ID")
	private Long id;

	@Column(name = "COMPANY") 
	private String company;

	@Column(name = "ACCOUNT")
	private String account;

	@Column(name = "ASSIGNMENT") 
	private String assignment;

	@Column(name = "REFERENCE")
	private String reference;

	@Column(name = "DOCUMENT_NUMBER")
	private String documentNumber;

	@Column(name = "DOCUMENT_TYPE")
	private String documentType;

	@Column(name = "POSTING_KEY")
	private String positingKey;

	@Column(name = "CLEARING_DOCUMENT_NUMBER")
	private String clearingDocumentNumber;

	@Column(name = "DOCUMENT_DESC") 
	private String documentDesc;

	@Column(name = "COST_CENTER")
	private String costCenter;

	@Column(name = "ORDER_CD")
	private String orderCd;

	@Column(name = "AGENT")
	private String agent;

	@Column(name = "OPERATION")
	private String operation;

	@Column(name = "COUNTRY_CD")
	private String countryCode;

	@Column(name = "POLICY_ID")
	private String policyId;

	@Column(name = "PRODUCT")
	private String product;

	@Column(name = "SUPPORT")
	private String support;

	@Column(name = "FUND")
	private String fund;

	@Column(name = "DOCUMENT_DATE") 
	@Temporal(TemporalType.DATE)
	private Date documentDate;

	@Column(name = "INPUT_DATE")
	@Temporal(TemporalType.DATE)
	private Date inputDate;

	@Column(name = "ACCOUNTING_DATE")
	@Temporal(TemporalType.DATE)
	private Date accountingDate;

	@Column(name = "AMOUNT_EUR")
	private BigDecimal amountInEur;

	// TODO groupBy this one
	@Column(name="CURRENCY")
	private String currency;

	//TODO Sum this one
	@Column(name = "AMOUNT_CCY")
	private BigDecimal amountInCurrency;

	@Column(name = "COMMISSION_TYPE")
	private String commissionType;

	@Column(name = "LOAD_DATE")
	@Temporal(TemporalType.DATE)
	private Date loadDate;

	@Column(name = "STATUS")
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the orderCd
	 */
	public String getOrderCd() {
		return orderCd;
	}

	/**
	 * @param orderCd the orderCd to set
	 */
	public void setOrderCd(String orderCd) {
		this.orderCd = orderCd;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAssignment() {
		return assignment;
	}

	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getPositingKey() {
		return positingKey;
	}

	public void setPositingKey(String positingKey) {
		this.positingKey = positingKey;
	}

	public String getClearingDocumentNumber() {
		return clearingDocumentNumber;
	}

	public void setClearingDocumentNumber(String clearingDocumentNumber) {
		this.clearingDocumentNumber = clearingDocumentNumber;
	}

	public String getDocumentDesc() {
		return documentDesc;
	}

	public void setDocumentDesc(String documentDesc) {
		this.documentDesc = documentDesc;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public Date getAccountingDate() {
		return accountingDate;
	}

	public void setAccountingDate(Date accountingDate) {
		this.accountingDate = accountingDate;
	}

	public BigDecimal getAmountInEur() {
		return amountInEur;
	}

	public void setAmountInEur(BigDecimal amountInEur) {
		this.amountInEur = amountInEur;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmountInCurrency() {
		return amountInCurrency;
	}

	public void setAmountInCurrency(BigDecimal amountInCurrency) {
		this.amountInCurrency = amountInCurrency;
	}

	public Date getLoadDate() {
		return loadDate;
	}

	public void setLoadDate(Date loadDate) {
		this.loadDate = loadDate;
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

}