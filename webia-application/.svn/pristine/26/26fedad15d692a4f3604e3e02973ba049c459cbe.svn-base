package lu.wealins.webia.ws.rest.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the response of the GetUser REST service. It is a description of a user.
 * 
 * It is designed to be fully self contained and to be marshaled to a classic JSON response payload.
 */
public class GetUserResponse {

	private String id;
	private String username;
	private String lastname;
	private String firstname;
	private String email;

	private boolean enabled;
	private boolean emailVerified;
	private boolean userExists;

	private List<String> realmRoles = new ArrayList<>();
	private List<Group> groups = new ArrayList<>();
	private Map<String, List<String>> clientRoles = new HashMap<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public List<String> getRealmRoles() {
		return realmRoles;
	}

	public void setRealmRoles(List<String> realmRoles) {
		this.realmRoles = realmRoles;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Map<String, List<String>> getClientRoles() {
		return clientRoles;
	}

	public void setClientRoles(Map<String, List<String>> clientRoles) {
		this.clientRoles = clientRoles;
	}

	public boolean isUserExists() {
		return userExists;
	}

	public void setUserExists(boolean userExists) {
		this.userExists = userExists;
	}

}
