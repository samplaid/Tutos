package lu.wealins.rest.model.acl;

import lu.wealins.rest.model.acl.common.User;

/**
 * The user ceation response
 * 
 * @author bqv55
 *
 */
public class CreateAclUserResponse {

	/**
	 * The user list
	 */
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
