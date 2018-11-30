package lu.wealins.rest.model.soliam;

/**
 * Funds information from Soliam
 * 
 * @author TEY
 *
 */
public class Fund {

	/**
	 * The isin code
	 */
	private String isin;

	/**
	 * The currency
	 */
	private String currency;

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
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
