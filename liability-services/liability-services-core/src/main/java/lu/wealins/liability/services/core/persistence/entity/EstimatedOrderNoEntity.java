package lu.wealins.liability.services.core.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Pojo for the order extraction
 * 
 * @author xqv60
 *
 */
public class EstimatedOrderNoEntity {
	/**
	 * The id
	 */
	private BigDecimal id;

	/**
	 * The event type
	 */
	private short eventType;

	/**
	 * The isin code
	 */
	private String isinCode;

	/**
	 * The currency fund
	 */
	private String fundCurrency;

	/**
	 * The deposit account
	 */
	private String depositAccount;

	/**
	 * The activity date
	 */
	private Date activityDate;

	/**
	 * The valorization date
	 */
	private Date valorizationDate;

	/**
	 * The units number
	 */
	private BigDecimal unitsNb;

	/**
	 * The amount invested or desinvested
	 */
	private BigDecimal amountInvestedOrDesinvested;

	/**
	 * The price
	 */
	private BigDecimal price;

	/**
	 * The policy id
	 */
	private String policyId;

	/**
	 * The status of the order
	 */
	private Short status;

	/**
	 * The estimated flag
	 */
	private Short estimatedFlag;

	/**
	 * The full constructor with parameter. It is required for the result set mapping hibernate
	 * 
	 * @param id
	 * @param eventType
	 * @param isinCode
	 * @param fundCurrency
	 * @param depositAccount
	 * @param activityDate
	 * @param valorizationDate
	 * @param unitsNb
	 * @param amountInvestedOrDesinvested
	 * @param price
	 * @param policyId
	 * @param status
	 * @param estimatedFlag
	 */
	public EstimatedOrderNoEntity(BigDecimal id, short eventType, String isinCode, String fundCurrency, String depositAccount, Date activityDate, Date valorizationDate, BigDecimal unitsNb,
			BigDecimal amountInvestedOrDesinvested, BigDecimal price, String policyId, Short status, Short estimatedFlag) {
		this.id = id;
		this.eventType = eventType;
		this.isinCode = isinCode;
		this.fundCurrency = fundCurrency;
		this.depositAccount = depositAccount;
		this.activityDate = activityDate;
		this.valorizationDate = valorizationDate;
		this.unitsNb = unitsNb;
		this.amountInvestedOrDesinvested = amountInvestedOrDesinvested;
		this.price = price;
		this.policyId = policyId;
		this.status = status;
		this.estimatedFlag = estimatedFlag;
	}

	/**
	 * @return the id
	 */
	public BigDecimal getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(BigDecimal id) {
		this.id = id;
	}

	/**
	 * @return the eventType
	 */
	public short getEventType() {
		return eventType;
	}

	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(short eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the isinCode
	 */
	public String getIsinCode() {
		return isinCode;
	}

	/**
	 * @param isinCode the isinCode to set
	 */
	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	/**
	 * @return the fundCurrency
	 */
	public String getFundCurrency() {
		return fundCurrency;
	}

	/**
	 * @param fundCurrency the fundCurrency to set
	 */
	public void setFundCurrency(String fundCurrency) {
		this.fundCurrency = fundCurrency;
	}

	/**
	 * @return the depositAccount
	 */
	public String getDepositAccount() {
		return depositAccount;
	}

	/**
	 * @param depositAccount the depositAccount to set
	 */
	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}

	/**
	 * @return the activityDate
	 */
	public Date getActivityDate() {
		return activityDate;
	}

	/**
	 * @param activityDate the activityDate to set
	 */
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	/**
	 * @return the valorizationDate
	 */
	public Date getValorizationDate() {
		return valorizationDate;
	}

	/**
	 * @param valorizationDate the valorizationDate to set
	 */
	public void setValorizationDate(Date valorizationDate) {
		this.valorizationDate = valorizationDate;
	}

	/**
	 * @return the unitsNb
	 */
	public BigDecimal getUnitsNb() {
		return unitsNb;
	}

	/**
	 * @param unitsNb the unitsNb to set
	 */
	public void setUnitsNb(BigDecimal unitsNb) {
		this.unitsNb = unitsNb;
	}

	/**
	 * @return the amountInvestedOrDesinvested
	 */
	public BigDecimal getAmountInvestedOrDesinvested() {
		return amountInvestedOrDesinvested;
	}

	/**
	 * @param amountInvestedOrDesinvested the amountInvestedOrDesinvested to set
	 */
	public void setAmountInvestedOrDesinvested(BigDecimal amountInvestedOrDesinvested) {
		this.amountInvestedOrDesinvested = amountInvestedOrDesinvested;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
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
	 * @return the status
	 */
	public Short getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Short status) {
		this.status = status;
	}

	/**
	 * @return the estimatedFlag
	 */
	public Short getEstimatedFlag() {
		return estimatedFlag;
	}

	/**
	 * @param estimatedFlag the estimatedFlag to set
	 */
	public void setEstimatedFlag(Short estimatedFlag) {
		this.estimatedFlag = estimatedFlag;
	}

}
