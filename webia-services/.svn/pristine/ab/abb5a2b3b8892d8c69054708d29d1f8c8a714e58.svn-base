package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SAS_ORDER database table.
 */
@Entity
@Table(name = "SAS_ORDER_AGG")
public class SasOrderAggEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 165465874714L;
	
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@Column(name = "SAS_ORDRE_AGG_ID")
	private Long id;
	
	@Column(name = "ORIGIN")
	private String origin;
	
	@Column(name = "LINE_ID")
	private String lineId;
	
	@Column(name = "GRP_ID")
	private String groupeId;
	
	@Column(name = "CANCEL_ID")
	private String cancelId;
	
	@Column(name = "EVENT_TYPE")
	private String eventType;
	
	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;
	
	@Column(name = "NATURE_ID")
	private String natureId;
	
	@Column(name = "SECURITY_ID")
	private String securityId;
	
	@Column(name = "CONTRACT_ID")
	private String contractId;
	
	@Column(name = "POLICY_ID")
	private String policyId;
	
	@Column(name = "PORTFOLIO_CD")
	private String portfolio;
	
	@Column(name = "SCT_CD")
	private String sct;
	
	@Column(name = "ENTRY_DT")
	@Temporal(TemporalType.DATE)
	private Date entryDate;
	
	@Column(name = "VALUATION_DT")
	@Temporal(TemporalType.DATE)
	private Date valuationDate;
	
	@Column(name = "QUANTITY")
	private BigDecimal quantity;
	
	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@Column(name = "AMOUNT_CURRENCY")
	private String amountCurrency;
	
	@Column(name = "NAV")
	private BigDecimal nav;

	@Column(name = "NAV_DT")
	@Temporal(TemporalType.DATE)
	private Date navDate;

	@Column(name = "INVESTMENT_FLG")
	private String isInvestment;

	@Column(name = "CONVERSION_FLG")
	private String isConversion;

	@Column(name = "TECH_CANCEL_FLG")
	private String isTechnicalCancel;

	@Column(name = "CANCEL_FLG")
	private String isCancel;

	@Column(name = "ESTIM_FLG")
	private String isEstimated;

	@Column(name = "QTE_FLG")
	private String isQantity;


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

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getGroupeId() {
		return groupeId;
	}

	public void setGroupeId(String groupeId) {
		this.groupeId = groupeId;
	}

	public String getCancelId() {
		return cancelId;
	}

	public void setCancelId(String cancelId) {
		this.cancelId = cancelId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getNatureId() {
		return natureId;
	}

	public void setNatureId(String natureId) {
		this.natureId = natureId;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(String portfolio) {
		this.portfolio = portfolio;
	}

	public String getSct() {
		return sct;
	}

	public void setSct(String sct) {
		this.sct = sct;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getValuationDate() {
		return valuationDate;
	}

	public void setValuationDate(Date valuationDate) {
		this.valuationDate = valuationDate;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAmountCurrency() {
		return amountCurrency;
	}

	public void setAmountCurrency(String amountCurrency) {
		this.amountCurrency = amountCurrency;
	}

	public BigDecimal getNav() {
		return nav;
	}

	public void setNav(BigDecimal nav) {
		this.nav = nav;
	}

	public Date getNavDate() {
		return navDate;
	}

	public void setNavDate(Date navDate) {
		this.navDate = navDate;
	}



	public String getIsInvestment() {
		return isInvestment;
	}

	public void setIsInvestment(String isInvestment) {
		this.isInvestment = isInvestment;
	}

	public String getIsConversion() {
		return isConversion;
	}

	public void setIsConversion(String isConversion) {
		this.isConversion = isConversion;
	}

	public String getIsTechnicalCancel() {
		return isTechnicalCancel;
	}

	public void setIsTechnicalCancel(String isTechnicalCancel) {
		this.isTechnicalCancel = isTechnicalCancel;
	}

	public String getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(String isCancel) {
		this.isCancel = isCancel;
	}

	public String getIsEstimated() {
		return isEstimated;
	}

	public void setIsEstimated(String isEstimated) {
		this.isEstimated = isEstimated;
	}

	public String getIsQantity() {
		return isQantity;
	}

	public void setIsQantity(String isQantity) {
		this.isQantity = isQantity;
	}


	

}
