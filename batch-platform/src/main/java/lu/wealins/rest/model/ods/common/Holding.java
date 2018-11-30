package lu.wealins.rest.model.ods.common;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Holding Model returned as component of a service response
 * 
 * @author bqv55
 *
 */
public class Holding {

	/**
	 * Fund
	 */
	private Fund fund;

	/**
	 * Holding validity start date
	 */
	private Date validityStartDate;

	/**
	 * Holding validity end date
	 */
	private Date validityEndDate;

	/**
	 * Number of units
	 */
	private BigDecimal numberOfUnits;

	/**
	 * Interest rate
	 */
	private BigDecimal interestRate;

	/**
	 * Total Amount
	 */
	private BigDecimal totalAmount;
	
	/**
	 * Total Amount
	 */
	private BigDecimal totalAmountInPolicyCurrency;

	/**
	 * Holding currency
	 */
	private String currency;
	
	/**
	 * Nav 
	 */
	private BigDecimal nav;

	/**
	 * Nav date
	 */
	private Date navDate;
	
	/**
	 * Percentage
	 */
	private BigDecimal percentage;

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public Date getValidityStartDate() {
		return validityStartDate;
	}

	public void setValidityStartDate(Date validityStartDate) {
		this.validityStartDate = validityStartDate;
	}

	public Date getValidityEndDate() {
		return validityEndDate;
	}

	public void setValidityEndDate(Date validityEndDate) {
		this.validityEndDate = validityEndDate;
	}

	public BigDecimal getNumberOfUnits() {
		return numberOfUnits;
	}

	public void setNumberOfUnits(BigDecimal numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public Date getNavDate() {
		return navDate;
	}

	public void setNavDate(Date navDate) {
		this.navDate = navDate;
	}

	/**
	 * @return the totalAmountInPolicyCurrency
	 */
	public BigDecimal getTotalAmountInPolicyCurrency() {
		return totalAmountInPolicyCurrency;
	}

	/**
	 * @param totalAmountInPolicyCurrency the totalAmountInPolicyCurrency to set
	 */
	public void setTotalAmountInPolicyCurrency(BigDecimal totalAmountInPolicyCurrency) {
		this.totalAmountInPolicyCurrency = totalAmountInPolicyCurrency;
	}

	/**
	 * @return the percentage
	 */
	public BigDecimal getPercentage() {
		return percentage;
	}

	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}
	
	
	

}
