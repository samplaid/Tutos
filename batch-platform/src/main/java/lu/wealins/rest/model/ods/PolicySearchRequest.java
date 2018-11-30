package lu.wealins.rest.model.ods;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.rest.model.ods.common.Person;



/**
 * Policy search request class
 * 
 * @author xqv60
 *
 */
public class PolicySearchRequest {

	/**
	 * The policy id
	 */
	private String code;

	/**
	 * The person : could be a moral or a physical person
	 */
	private Person person;

	/**
	 * The product name
	 */
	private String productName;

	/**
	 * The FID number
	 */
	private String fidNumber;

	/**
	 * The initial contract
	 * 
	 * Careful : This is available only for the BIL
	 */
	private String initialContract;

	/**
	 * The with closed flag Permit to search the police activated or the whole policies
	 */
	private boolean withClosed;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
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
	 * @return the fidNumber
	 */
	public String getFidNumber() {
		return fidNumber;
	}

	/**
	 * @param fidNumber the fidNumber to set
	 */
	public void setFidNumber(String fidNumber) {
		this.fidNumber = fidNumber;
	}

	/**
	 * @return the initialContract
	 */
	public String getInitialContract() {
		return initialContract;
	}

	/**
	 * @param initialContract the initialContract to set
	 */
	public void setInitialContract(String initialContract) {
		this.initialContract = initialContract;
	}

	/**
	 * @return the withClosed
	 */
	public boolean isWithClosed() {
		return withClosed;
	}

	/**
	 * @param withClosed the withClosed to set
	 */
	public void setWithClosed(boolean withClosed) {
		this.withClosed = withClosed;
	}

}
