/**
 * 
 */
package lu.wealins.rest.model.ewealins.rs.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class contains the basic criteria for internationalization based on the {@code type} and the {@code language}
 * 
 * @author oro
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicInternationalizationRequest implements Serializable{
	private static final long serialVersionUID = -6116640241136196653L;

	private String type;
	private String language;

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
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BasicInternationalizationRequest [type=");
		builder.append(type);
		builder.append(", language=");
		builder.append(language);
		builder.append("]");
		return builder.toString();
	}

}
