package lu.wealins.rest.model.ods.common;

/**
 * Address linked to a Person
 * Model returned as component of a service response
 * 
 * @author bqv55
 *
 */
public class Address {
	
	/**
	 * The full address to display
	 * 914, Place Basse \n94459\nARLON
	 */
	private String addressBlock;

	/**
	 * Postal Code
	 */
	private String postalCode;

	/**
	 * Town
	 */
	private String town;

	/**
	 * Country
	 */
	private String countryCode;

	public String getAddressBlock() {
		return addressBlock;
	}

	public void setAddressBlock(String addressBlock) {
		this.addressBlock = addressBlock;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	   
}
