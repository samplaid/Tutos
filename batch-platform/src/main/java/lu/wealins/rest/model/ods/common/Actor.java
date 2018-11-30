package lu.wealins.rest.model.ods.common;

/**
 * Actor Model returned as component of a service response
 * 
 * @author bqv55
 *
 */
public class Actor {

	/**
	 * List of the actor's roles
	 */
	private Role role;

	/**
	 * Person linked to the actor
	 */
	private Person person;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
