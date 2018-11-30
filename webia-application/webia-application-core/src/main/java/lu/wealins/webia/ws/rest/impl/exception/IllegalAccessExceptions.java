package lu.wealins.webia.ws.rest.impl.exception;

public class IllegalAccessExceptions extends RuntimeException {
	private static final long serialVersionUID = 4956985210146234039L;

	public IllegalAccessExceptions() {
		super();
	}

	public IllegalAccessExceptions(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalAccessExceptions(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalAccessExceptions(String message) {
		super(message);
	}

	public IllegalAccessExceptions(Throwable cause) {
		super(cause);
	}
	
}
