package lu.wealins.webia.ws.rest.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The document id reservation response
 * 
 * @author SKG
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveDocumentIntoFileNetResponse {

	/**
	 * The reserved document ID
	 */
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
