/**
 * 
 */
package lu.wealins.rest.model.ods;

import java.util.List;

import lu.wealins.common.security.ACL;
import lu.wealins.rest.model.acl.common.Functionality;
import lu.wealins.rest.model.ods.common.Policy;

/**
 * @author lax
 *
 */
public class PoliciesForPortfolioRequest {
	
	private List<Policy> policies;
	
	private String user;

	private List<Functionality> functionalities;

	private List<ACL> acls;

	public List<Policy> getPolicies() {
		return policies;
	}

	public void setPolicies(List<Policy> policies) {
		this.policies = policies;
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
