package lu.wealins.liability.services.ws.rest.exception;

import java.io.Serializable;

public class RESTServiceExceptionResponse implements Serializable {
	
	private static final long serialVersionUID = -1018839009155508901L;

	private String errorType;
	
	private String errorMessage;
	
	public RESTServiceExceptionResponse(String errorType, String errorMessage) {
		super();
		this.errorType = errorType;
		this.errorMessage = errorMessage;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
