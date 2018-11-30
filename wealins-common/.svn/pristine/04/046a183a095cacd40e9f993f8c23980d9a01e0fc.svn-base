package lu.wealins.common.security;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ACLContext {

	private String user;
	
	private String trigram;

	private List<Functionality> functionalities;

	private List<ACL> acls;

	/**
	 * Default constructor
	 */
	public ACLContext() {

	}

	/**
	 * Constructor with parameter
	 * 
	 * @param user the user
	 */
	public ACLContext(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public String getTrigram() {
		return trigram;
	}

	public void setTrigram(String trigram) {
		this.trigram = trigram;
	}	

	public List<Functionality> getFunctionalities() {
		return functionalities;
	}

	public void setFunctionalities(List<Functionality> functionalities) {
		this.functionalities = functionalities;
	}

	public List<ACL> getAcls() {
		return acls;
	}

	public void setAcls(List<ACL> acls) {
		this.acls = acls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ACLContext [\n User=" + user + "\n Trigram=" + trigram +"\n Functionalities=" + functionalities + "\n Acls=" + acls + "]";
	}
}
