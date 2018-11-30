package lu.wealins.rest.model.acl;


/**
 * The User search request
 * 
 * 
 * 
 * @author xqv60
 *
 */

public class SearchUserRequest {

	/**
	 * The criteria can be the login, the name or the firstname
	 */
	private String user;

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

}