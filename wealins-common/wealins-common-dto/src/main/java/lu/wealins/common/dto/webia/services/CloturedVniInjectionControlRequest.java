package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;

public class CloturedVniInjectionControlRequest {
	private String fundId;
	private String priceDate;
	private BigDecimal price;
	private String currency;
	private String isinCode;

	private String fundType;

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
	 * @return the fundId
	 */
	public String getFundId() {
		return fundId;
	}

	/**
	 * @param fundId
	 *            the fundId to set
	 */
	public void setFundId(String fundId) {
		this.fundId = fundId;
	}

	/**
	 * @return the priceDate
	 */
	public String getPriceDate() {
		return priceDate;
	}

	/**
	 * @param priceDate
	 *            the priceDate to set
	 */
	public void setPriceDate(String priceDate) {
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
	 * @return the isinCode
	 */
	public String getIsinCode() {
		return isinCode;
	}

	/**
	 * @param isinCode
	 *            the isinCode to set
	 */
	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

}
