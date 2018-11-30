package lu.wealins.rest.model.editing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The document generation request
 * 
 * @author bqv55
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentGenerationRequest {

	private EditingRequest request;

	public EditingRequest getRequest() {
		return request;
	}

	public void setRequest(EditingRequest request) {
		this.request = request;
	}

}
