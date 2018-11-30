package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;

/**
 * Composite key of the SasMappingEntity
 * 
 * @author xqv60
 * @see SasMappingEntity
 *
 */
public class SasMappingEntityId implements Serializable {

	/**
	 * The serial version uid
	 */
	private static final long serialVersionUID = 1749323268547727055L;

	/**
	 * The type
	 */
	private String type;

	/**
	 * The input
	 */
	private String input;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the input
	 */
	public String getInput() {
		return input;
	}

	/**
	 * @param input the input to set
	 */
	public void setInput(String input) {
		this.input = input;
	}

}
