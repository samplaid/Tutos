package lu.wealins.liability.services.core.business.exceptions;

public class AuditServiceException extends RuntimeException {

	private static final long serialVersionUID = -8125476096040434832L;

	public AuditServiceException(String message) {
		super(message);
	}

	public AuditServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
