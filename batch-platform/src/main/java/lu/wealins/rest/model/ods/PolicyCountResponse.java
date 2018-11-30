/**
 * 
 */
package lu.wealins.rest.model.ods;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author lax
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyCountResponse {

	private int policyNumber;

	/**
	 * @return the policyNumber
	 */
	public int getPolicyNumber() {
		return policyNumber;
	}

	/**
	 * @param policyNumber the policyNumber to set
	 */
	public void setPolicyNumber(int policyNumber) {
		this.policyNumber = policyNumber;
	}	

}
