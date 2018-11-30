package lu.wealins.rest.model.acl;

import java.util.List;

public class AssignACLsToUserRequest {
	
	String userLogin;
	
	List<Long> aclIds;



	/**
	 * @return the userLogin
	 */
	public String getUserLogin() {
		return userLogin;
	}

	/**
	 * @param userLogin the userLogin to set
	 */
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	/**
	 * @return the aclIds
	 */
	public List<Long> getAclIds() {
		return aclIds;
	}

	/**
	 * @param aclIds the aclIds to set
	 */
	public void setAclIds(List<Long> aclIds) {
		this.aclIds = aclIds;
	}

	
	
	
}
