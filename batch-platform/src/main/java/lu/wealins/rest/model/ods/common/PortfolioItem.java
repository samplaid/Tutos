/**
 * 
 */
package lu.wealins.rest.model.ods.common;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lax
 *
 */
public class PortfolioItem {

	// Policy ID
	private String policyId;

	// Product name
	private String productName;

	// Policy effective date
	private Date policyEffectiveDate;

	// Fund ISIN code
	private String isin;

	// Label (name) of the fund
	private String fundLabel;

	// Weighted average purchase price
	private BigDecimal weightedAverage;

	// Fund currency
	private String fundCurrency;

	// Last NAV date
	private Date navDate;

	// Last NAV value
	private BigDecimal nav;

	// Number of units
	private BigDecimal numberUnits;

	// Total Amount
	private BigDecimal totalAmount;

	// Amount In Policy Currency
	private BigDecimal amountInPolicyCurrency;

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
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the policyEffectiveDate
	 */
	public Date getPolicyEffectiveDate() {
		return policyEffectiveDate;
	}

	/**
	 * @param policyEffectiveDate the policyEffectiveDate to set
	 */
	public void setPolicyEffectiveDate(Date policyEffectiveDate) {
		this.policyEffectiveDate = policyEffectiveDate;
	}

	/**
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * @param isin the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @return the fundLabel
	 */
	public String getFundLabel() {
		return fundLabel;
	}

	/**
	 * @param fundLabel the fundLabel to set
	 */
	public void setFundLabel(String fundLabel) {
		this.fundLabel = fundLabel;
	}

	/**
	 * @return the weightedAverage
	 */
	public BigDecimal getWeightedAverage() {
		return weightedAverage;
	}

	/**
	 * @param weightedAverage the weightedAverage to set
	 */
	public void setWeightedAverage(BigDecimal weightedAverage) {
		this.weightedAverage = weightedAverage;
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
	 * @return the numberUnits
	 */
	public BigDecimal getNumberUnits() {
		return numberUnits;
	}

	/**
	 * @param numberUnits the numberUnits to set
	 */
	public void setNumberUnits(BigDecimal numberUnits) {
		this.numberUnits = numberUnits;
	}

	/**
	 * @return the amountInPolicyCurrency
	 */
	public BigDecimal getAmountInPolicyCurrency() {
		return amountInPolicyCurrency;
	}

	/**
	 * @param amountInPolicyCurrency the amountInPolicyCurrency to set
	 */
	public void setAmountInPolicyCurrency(BigDecimal amountInPolicyCurrency) {
		this.amountInPolicyCurrency = amountInPolicyCurrency;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

}
