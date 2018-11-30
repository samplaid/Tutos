package lu.wealins.webia.ws.rest.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The document generation request
 * 
 * @author bqv55
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentGenerationResponse {
	private String successMessage;
	private String errorMessage;
	private EditingRequest request;

	public EditingRequest getRequest() {
		return request;
	}

	public void setRequest(EditingRequest request) {
		this.request = request;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the successMessage
	 */
	public String getSuccessMessage() {
		return successMessage;
	}

	/**
	 * @param successMessage
	 *            the successMessage to set
	 */
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

}
