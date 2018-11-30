
package lu.wealins.liability.services.ws.rest.exception;
public class WssupdpstImportException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -381781503102234042L;
	private String code;

	public WssupdpstImportException(String message) {
		super(message);
	}

	public WssupdpstImportException(String message, String code) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public WssupdpstImportException(Throwable cause) {
		super(cause);
	}
}
