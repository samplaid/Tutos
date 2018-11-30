package lu.wealins.webia.ws.rest.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The document generation response
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
