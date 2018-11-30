package lu.wealins.rest.model.ewealins.rs.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.rest.model.ewealins.Translation;

/**
 * The response for internationalization loading
 * 
 * @author lax
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadInternationalizationResponse {

	private List<Translation> translations;

	/**
	 * @return the translations
	 */
	public List<Translation> getTranslations() {
		if (translations == null) {
			translations = new ArrayList<>();
		}
		return translations;
	}

	/**
	 * @param translations the translations to set
	 */
	public void setTranslations(List<Translation> translations) {
		this.translations = translations;
	}
}
