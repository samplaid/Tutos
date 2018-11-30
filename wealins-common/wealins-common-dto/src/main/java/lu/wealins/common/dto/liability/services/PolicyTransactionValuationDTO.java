/**
 * 
 */
package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author NGA
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyTransactionValuationDTO {
	private String polId;
	private String fund;
	private BigDecimal units;
	private Date priceDate;
	private BigDecimal price;
	private String fundCurrency;
	private String policyCurrency;
	private String fundType;

	/**
	 * @return the polId
	 */
	public String getPolId() {
		return polId;
	}

	/**
	 * @param polId
	 *            the polId to set
	 */
	public void setPolId(String polId) {
		this.polId = polId;
	}

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
	 * @return the units
	 */
	public BigDecimal getUnits() {
		return units;
	}

	/**
	 * @param units
	 *            the units to set
	 */
	public void setUnits(BigDecimal units) {
		this.units = units;
	}

	/**
	 * @return the priceDate
	 */
	public Date getPriceDate() {
		return priceDate;
	}

	/**
	 * @param priceDate
	 *            the priceDate to set
	 */
	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the fundCurrency
	 */
	public String getFundCurrency() {
		return fundCurrency;
	}

	/**
	 * @param fundCurrency
	 *            the fundCurrency to set
	 */
	public void setFundCurrency(String fundCurrency) {
		this.fundCurrency = fundCurrency;
	}

	/**
	 * @return the policyCurrency
	 */
	public String getPolicyCurrency() {
		return policyCurrency;
	}

	/**
	 * @param policyCurrency
	 *            the policyCurrency to set
	 */
	public void setPolicyCurrency(String policyCurrency) {
		this.policyCurrency = policyCurrency;
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
	
}
