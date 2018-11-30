package lu.wealins.rest.model.acl;

public class LoadGroupRequest {

	/**
	 * The group id
	 */
	private Long id;

	/**
	 * Default constructor
	 * 
	 */
	public LoadGroupRequest() {
	}

	/**
	 * Constructor with id group as parameter
	 * 
	 * @param id the group id
	 */
	public LoadGroupRequest(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
