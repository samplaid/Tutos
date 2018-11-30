package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * The SAS MAPPING entity
 * 
 * @author xqv60
 *
 */
@Table(name = "SAS_MAPPING")
@Entity
@IdClass(SasMappingEntityId.class)
public class SasMappingEntity implements Serializable {

	/**
	 * The serial version uid
	 */
	private static final long serialVersionUID = -7334270304519998007L;

	@Id
	@Column(name = "TYPE")
	private String type;

	@Id
	@Column(name = "DATA_IN")
	private String input;

	@Column(name = "DATA_OUT")
	private String output;

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

	/**
	 * @return the output
	 */
	public String getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(String output) {
		this.output = output;
	}

}
