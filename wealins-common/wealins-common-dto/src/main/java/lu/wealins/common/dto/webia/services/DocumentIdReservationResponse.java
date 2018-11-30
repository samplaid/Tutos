package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The document id reservation response
 * 
 * @author SKG
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentIdReservationResponse {

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
