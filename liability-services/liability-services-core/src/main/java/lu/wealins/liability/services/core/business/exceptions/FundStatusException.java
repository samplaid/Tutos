package lu.wealins.liability.services.core.business.exceptions;

/**
 * Used to throw an exception when a fund status is not incorrect.
 * @author XQV89
 *
 */
public class FundStatusException extends RuntimeException{

	private static final long serialVersionUID = -4525423246182805395L;

	public FundStatusException() {
		super();
	}

	public FundStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FundStatusException(String message, Throwable cause) {
		super(message, cause);
	}

	public FundStatusException(String message) {
		super(message);
	}

	public FundStatusException(Throwable cause) {
		super(cause);
	}

	
}
