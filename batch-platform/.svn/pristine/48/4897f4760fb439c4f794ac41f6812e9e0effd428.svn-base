package lu.wealins.rest.model.ods;

import java.util.List;

import lu.wealins.common.security.ACL;
import lu.wealins.common.security.Functionality;
import lu.wealins.rest.model.ods.common.Person;



/**
 * Policy search request class
 * 
 * @author xqv60
 *
 */
public class PolicyWithACLContextSearchRequest {

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
	
	private String user;

	private List<Functionality> functionalities;

	private List<ACL> acls;

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

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the functionalities
	 */
	public List<Functionality> getFunctionalities() {
		return functionalities;
	}

	/**
	 * @param functionalities the functionalities to set
	 */
	public void setFunctionalities(List<Functionality> functionalities) {
		this.functionalities = functionalities;
	}

	/**
	 * @return the acls
	 */
	public List<ACL> getAcls() {
		return acls;
	}

	/**
	 * @param acls the acls to set
	 */
	public void setAcls(List<ACL> acls) {
		this.acls = acls;
	}

}
