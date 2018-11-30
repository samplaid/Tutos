package lu.wealins.rest.model.acl.common;

/**
 * The model Profil exposed. It used in any response and request exposed
 * 
 * @author xqv60
 *
 */
public class Profil {

	/**
	 * The name
	 */
	private String name;

	/**
	 * THe description
	 */
	private String description;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
