/**
 * 
 */
package lu.wealins.webia.core.exceptions;

/**
 * @author oro
 *
 */
public class FileUploadException extends RuntimeException {

	private static final long serialVersionUID = -3186711772065964141L;

	/**
	 * 
	 */
	public FileUploadException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public FileUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public FileUploadException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public FileUploadException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public FileUploadException(Throwable cause) {
		super(cause);
	}

}
