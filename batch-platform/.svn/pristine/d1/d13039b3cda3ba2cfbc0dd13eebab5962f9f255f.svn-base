package lu.wealins.rest.model.acl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.rest.model.acl.common.ACL;
import lu.wealins.rest.model.acl.common.Group;
import lu.wealins.rest.model.acl.common.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadGroupResponse {

	private Group group;
	private Group Parent;
	private List<Group> childGroups;
	private List<ACL> acls;
	private List<User> users;

	/**
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * @return the parent
	 */
	public Group getParent() {
		return Parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Group parent) {
		Parent = parent;
	}

	/**
	 * @return the childGroups
	 */
	public List<Group> getChildGroups() {
		return childGroups;
	}

	/**
	 * @param childGroups the childGroups to set
	 */
	public void setChildGroups(List<Group> childGroups) {
		this.childGroups = childGroups;
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

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

}
