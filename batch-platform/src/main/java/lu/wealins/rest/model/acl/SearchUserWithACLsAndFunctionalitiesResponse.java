package lu.wealins.rest.model.acl;

import java.util.Map;


/**
 * The user search response
 * 
 * @author xqv60
 *
 */
public class SearchUserWithACLsAndFunctionalitiesResponse {

	/**
	 * The users with ACLs
	 */
	private Map<String, ACLsAndFunctionalities> usersWithACLsAndFunctionalities;

	/**
	 * @return the usersWithACLs
	 */
	public Map<String, ACLsAndFunctionalities> getUsersWithACLsAndFunctionalities() {
		return usersWithACLsAndFunctionalities;
	}

	/**
	 * @param usersWithACLs the usersWithACLs to set
	 */
	public void setUsersWithACLsAndFunctionalities(Map<String, ACLsAndFunctionalities> usersWithACLsAndFunctionalities) {
		this.usersWithACLsAndFunctionalities = usersWithACLsAndFunctionalities;
	}

}
