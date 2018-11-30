package lu.wealins.rest.model.acl;

import lu.wealins.rest.model.acl.common.User;

public class AssignGroupToUserAclResponse {

	private User user;

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

}
