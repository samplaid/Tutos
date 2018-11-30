package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;
import java.util.Date;

public class AccountingNavToInject {
	private String fund;

	private BigDecimal navAmount;
	private String currency;
	private Date navDate;
	private Date CreateDate;
	private String IsinCode;
	private String fundType;
	private String OperationType;

	/**
	 * @return the fund
	 */
	public String getFund() {
		return fund;
	}

	/**
	 * @param fund
	 *            the fund to set
	 */
	public void setFund(String fund) {
		this.fund = fund;
	}

	/**
	 * @return the navAmount
	 */
	public BigDecimal getNavAmount() {
		return navAmount;
	}

	/**
	 * @param navAmount
	 *            the navAmount to set
	 */
	public void setNavAmount(BigDecimal navAmount) {
		this.navAmount = navAmount;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the navDate
	 */
	public Date getNavDate() {
		return navDate;
	}

	/**
	 * @param navDate
	 *            the navDate to set
	 */
	public void setNavDate(Date navDate) {
		this.navDate = navDate;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}

	/**
	 * @return the isinCode
	 */
	public String getIsinCode() {
		return IsinCode;
	}

	/**
	 * @param isinCode
	 *            the isinCode to set
	 */
	public void setIsinCode(String isinCode) {
		IsinCode = isinCode;
	}

	/**
	 * @return the fundType
	 */
	public String getFundType() {
		return fundType;
	}

	/**
	 * @param fundType
	 *            the fundType to set
	 */
	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	/**
	 * @return the operationType
	 */
	public String getOperationType() {
		return OperationType;
	}

	/**
	 * @param operationType
	 *            the operationType to set
	 */
	public void setOperationType(String operationType) {
		OperationType = operationType;
	}

}
