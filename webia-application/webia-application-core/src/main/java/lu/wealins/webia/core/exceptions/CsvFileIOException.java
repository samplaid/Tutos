/**
 * 
 */
package lu.wealins.webia.core.exceptions;

/**
 * This class throws an exception if a csv file cannot be generated or read from a location.
 * 
 * @author ORO
 *
 */
public class CsvFileIOException extends RuntimeException {

	private static final long serialVersionUID = -822726584133617319L;

	/**
	 * Default constructor
	 */
	public CsvFileIOException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CsvFileIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CsvFileIOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public CsvFileIOException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public CsvFileIOException(Throwable cause) {
		super(cause);
	}

}
