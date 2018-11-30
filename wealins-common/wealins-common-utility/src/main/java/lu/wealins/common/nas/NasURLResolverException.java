package lu.wealins.common.nas;

/**
 * Thrown to indicate that NasURL cannot be resolved.
 */
public class NasURLResolverException extends Exception {

	private static final long serialVersionUID = -1732002853385442116L;

	/**
	 * Constructs a <code>NasURLResolverException</code> with the specified detail message.
	 * 
	 * @param msg the detail message.
	 */
	public NasURLResolverException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a <code>NasURLResolverException</code> with the specified cause.
	 * 
	 * @param cause the cause.
	 */
	public NasURLResolverException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a <code>NasURLResolverException</code> with the specified detail message and the specified cause.
	 * 
	 * @param msg the detail message.
	 * @param cause the cause.
	 */
	public NasURLResolverException(String msg, Throwable cause) {
		super(msg, cause);
	}
}