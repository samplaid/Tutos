package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Order class
 * 
 * Represent the order after extraction from webia
 * 
 * @author xqv60
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO {

	/**
	 * The origin (LISSIA OR DALI)
	 */
	private String origin;

	/**
	 * The line id
	 */
	private String lineId;

	/**
	 * The groupe id
	 */
	private String groupeId;

	/**
	 * The cancel id
	 */
	private String cancelId;

	/**
	 * The event type
	 */
	private String eventType;

	/**
	 * The transaction type
	 */
	private String transactionType;

	/**
	 * The nature id
	 */
	private String natureId;

	/**
	 * The security id
	 */
	private String securityId;

	/**
	 * The contract id
	 */
	private String contractId;

	/**
	 * The policy id
	 */
	private String policyId;

	/**
	 * The portfolio
	 */
	private String portfolio;

	/**
	 * The sct code
	 */
	private String sct;

	/**
	 * The entry date
	 */
	private Date entryDate;

	/**
	 * The valudation date
	 */
	private Date valuationDate;

	/**
	 * The quantity
	 */
	private BigDecimal quantity;

	/**
	 * The amount
	 */
	private BigDecimal amount;

	/**
	 * The amount currency
	 */
	private String amountCurrency;

	/**
	 * The nav
	 */
	private BigDecimal nav;

	/**
	 * The nav date
	 */
	private Date navDate;

	/**
	 * The investment flag
	 */
	private String isInvestment;

	/**
	 * The conversio flag
	 */
	private String isConversion;

	/**
	 * The technical canceled flag
	 */
	private String isTechnicalCancel;

	/**
	 * The canceled flag
	 */
	private String isCancel;

	/**
	 * The estimated flag
	 */
	private String isEstimated;

	/**
	 * The quantity flag
	 */
	private String isQantity;
	
	/**
	 * The aggregation flag
	 */
	private String aggregationFlag;

	/**
	 * The send date : date of the file creation
	 */
	private Date sendDate;

	/**
	 * The estimated flag
	 */
	private short estimatedFlag;

	/**
	 * The valorized flag
	 */
	private short valorizedFlag;

	/**
	 * The status of the order
	 */
	private short status;
	
	/**
	 * value in fund currency
	 */
	private BigDecimal valueFundCurrency;

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
	 * @return the estimatedFlag
	 */
	public short getEstimatedFlag() {
		return estimatedFlag;
	}

	/**
	 * @param estimatedFlag the estimatedFlag to set
	 */
	public void setEstimatedFlag(short estimatedFlag) {
		this.estimatedFlag = estimatedFlag;
	}

	/**
	 * @return the valorizedFlag
	 */
	public short getValorizedFlag() {
		return valorizedFlag;
	}

	/**
	 * @param valorizedFlag the valorizedFlag to set
	 */
	public void setValorizedFlag(short valorizedFlag) {
		this.valorizedFlag = valorizedFlag;
	}

	/**
	 * @return the status
	 */
	public short getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(short status) {
		this.status = status;
	}

	public BigDecimal getValueFundCurrency() {
		return valueFundCurrency;
	}

	public void setValueFundCurrency(BigDecimal valueFundCurrency) {
		this.valueFundCurrency = valueFundCurrency;
	}

	public String getAggregationFlag() {
		return aggregationFlag;
	}

	public void setAggregationFlag(String aggregationFlag) {
		this.aggregationFlag = aggregationFlag;
	}

}
