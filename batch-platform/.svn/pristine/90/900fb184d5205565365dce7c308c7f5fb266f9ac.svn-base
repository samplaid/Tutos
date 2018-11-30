/**
 * 
 */
package lu.wealins.rest.model.acl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import lu.wealins.common.security.ACL;
import lu.wealins.common.security.Functionality;
import lu.wealins.rest.model.acl.common.User;

/**
 * @author lax
 *
 */
public class ACLsAndFunctionalities {
	
	private User user;
	private String agentId;
	private List<ACL> ACLs;
	private List<Functionality> functionalities;
	
	/**
	 * Default constructor.
	 */
	public ACLsAndFunctionalities() {
		
	}
	
	/**
	 * Constructor with parameters.
	 * @param user
	 * @param ACLs
	 * @param functionalities
	 */
	public ACLsAndFunctionalities(User user, String agentId, List<ACL> ACLs, List<Functionality> functionalities) {
		this.user = user;
		this.agentId = agentId;
		this.ACLs = ACLs;
		this.functionalities = functionalities;
	}
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	/**
	 * @return the aCLs
	 */
	public List<ACL> getACLs() {
		if (ACLs == null) {
			ACLs =  new ArrayList<ACL>();
		}
		return ACLs;
	}
	
	/**
	 * @param aCLs the aCLs to set
	 */
	public void setACLs(List<ACL> aCLs) {
		ACLs = aCLs;
	}
	
	/**
	 * @return the functionalities
	 */
	public List<Functionality> getFunctionalities() {
		if (functionalities == null) {
			functionalities =  new ArrayList<Functionality>();
		}
		return functionalities;
	}
	
	/**
	 * @param functionalities the functionalities to set
	 */
	public void setFunctionalities(List<Functionality> functionalities) {
		this.functionalities = functionalities;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ACLs == null) ? 0 : ACLs.hashCode());
		result = prime * result + ((functionalities == null) ? 0 : functionalities.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ACLsAndFunctionalities other = (ACLsAndFunctionalities) obj;
		if (ACLs == null) {
			if (other.ACLs != null)
				return false;
		} else if (!ACLs.equals(other.ACLs))
			return false;
		if (functionalities == null) {
			if (other.functionalities != null)
				return false;
		} else if (!functionalities.equals(other.functionalities))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ACLsAndFunctionalities [ACLs=" + ACLs + ", functionalities=" + functionalities + "]";
	}

}
