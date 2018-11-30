package lu.wealins.rest.model.acl;

import java.util.ArrayList;
import java.util.List;

import lu.wealins.rest.model.acl.common.User;



/**
 * The user search response
 * 
 * @author xqv60
 *
 */
public class SearchUserResponse {

	/**
	 * The user list
	 */
	private List<User> users;

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		if (users == null) {
			users = new ArrayList<>();
		}
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

}
