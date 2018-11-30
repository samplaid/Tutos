package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Sas order entity : Map the table SAS_ORDER
 * 
 * @author xqv60
 *
 */
@Table(name = "SAS_ORDER")
@Entity
public class SasOrderEntity implements Serializable {

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -8798542751194224509L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SAS_ORDRE_ID")
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
	
	@Column(name = "FLG_AGG")
	private String aggregationFlag;

	@Column(name = "SEND_DT")
	@Temporal(TemporalType.DATE)
	private Date sendDate;
	
	@Transient
	private BigDecimal valueFundCurrency;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the lineId
	 */
	public String getLineId() {
		return lineId;
	}

	/**
	 * @param lineId the lineId to set
	 */
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	/**
	 * @return the groupeId
	 */
	public String getGroupeId() {
		return groupeId;
	}

	/**
	 * @param groupeId the groupeId to set
	 */
	public void setGroupeId(String groupeId) {
		this.groupeId = groupeId;
	}

	/**
	 * @return the cancelId
	 */
	public String getCancelId() {
		return cancelId;
	}

	/**
	 * @param cancelId the cancelId to set
	 */
	public void setCancelId(String cancelId) {
		this.cancelId = cancelId;
	}

	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
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
	 * @return the natureId
	 */
	public String getNatureId() {
		return natureId;
	}

	/**
	 * @param natureId the natureId to set
	 */
	public void setNatureId(String natureId) {
		this.natureId = natureId;
	}

	/**
	 * @return the securityId
	 */
	public String getSecurityId() {
		return securityId;
	}

	/**
	 * @param securityId the securityId to set
	 */
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	/**
	 * @return the contractId
	 */
	public String getContractId() {
		return contractId;
	}

	/**
	 * @param contractId the contractId to set
	 */
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	/**
	 * @return the policyId
	 */
	public String getPolicyId() {
		return policyId;
	}

	/**
	 * @param policyId the policyId to set
	 */
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	/**
	 * @return the portfolio
	 */
	public String getPortfolio() {
		return portfolio;
	}

	/**
	 * @param portfolio the portfolio to set
	 */
	public void setPortfolio(String portfolio) {
		this.portfolio = portfolio;
	}

	/**
	 * @return the sct
	 */
	public String getSct() {
		return sct;
	}

	/**
	 * @param sct the sct to set
	 */
	public void setSct(String sct) {
		this.sct = sct;
	}

	/**
	 * @return the entryDate
	 */
	public Date getEntryDate() {
		return entryDate;
	}

	/**
	 * @param entryDate the entryDate to set
	 */
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	/**
	 * @return the valuationDate
	 */
	public Date getValuationDate() {
		return valuationDate;
	}

	/**
	 * @param valuationDate the valuationDate to set
	 */
	public void setValuationDate(Date valuationDate) {
		this.valuationDate = valuationDate;
	}

	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the amountCurrency
	 */
	public String getAmountCurrency() {
		return amountCurrency;
	}

	/**
	 * @param amountCurrency the amountCurrency to set
	 */
	public void setAmountCurrency(String amountCurrency) {
		this.amountCurrency = amountCurrency;
	}

	/**
	 * @return the nav
	 */
	public BigDecimal getNav() {
		return nav;
	}

	/**
	 * @param nav the nav to set
	 */
	public void setNav(BigDecimal nav) {
		this.nav = nav;
	}

	/**
	 * @return the navDate
	 */
	public Date getNavDate() {
		return navDate;
	}

	/**
	 * @param navDate the navDate to set
	 */
	public void setNavDate(Date navDate) {
		this.navDate = navDate;
	}

	/**
	 * @return the isInvestment
	 */
	public String getIsInvestment() {
		return isInvestment;
	}

	/**
	 * @param isInvestment the isInvestment to set
	 */
	public void setIsInvestment(String isInvestment) {
		this.isInvestment = isInvestment;
	}

	/**
	 * @return the isConversion
	 */
	public String getIsConversion() {
		return isConversion;
	}

	/**
	 * @param isConversion the isConversion to set
	 */
	public void setIsConversion(String isConversion) {
		this.isConversion = isConversion;
	}

	/**
	 * @return the isTechnicalCancel
	 */
	public String getIsTechnicalCancel() {
		return isTechnicalCancel;
	}

	/**
	 * @param isTechnicalCancel the isTechnicalCancel to set
	 */
	public void setIsTechnicalCancel(String isTechnicalCancel) {
		this.isTechnicalCancel = isTechnicalCancel;
	}

	/**
	 * @return the isCancel
	 */
	public String getIsCancel() {
		return isCancel;
	}

	/**
	 * @param isCancel the isCancel to set
	 */
	public void setIsCancel(String isCancel) {
		this.isCancel = isCancel;
	}

	/**
	 * @return the isEstimated
	 */
	public String getIsEstimated() {
		return isEstimated;
	}

	/**
	 * @param isEstimated the isEstimated to set
	 */
	public void setIsEstimated(String isEstimated) {
		this.isEstimated = isEstimated;
	}

	/**
	 * @return the sendDate
	 */
	public Date getSendDate() {
		return sendDate;
	}

	/**
	 * @param sendDate the sendDate to set
	 */
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	/**
	 * @return the isQantity
	 */
	public String getIsQantity() {
		return isQantity;
	}

	/**
	 * @param isQantity the isQantity to set
	 */
	public void setIsQantity(String isQantity) {
		this.isQantity = isQantity;
	}

	public String getAggregationFlag() {
		return aggregationFlag;
	}

	public void setAggregationFlag(String aggregationFlag) {
		this.aggregationFlag = aggregationFlag;
	}

	public BigDecimal getValueFundCurrency() {
		return valueFundCurrency;
	}

	public void setValueFundCurrency(BigDecimal valueFundCurrency) {
		this.valueFundCurrency = valueFundCurrency;
	}
	
	

}
