package lu.wealins.liability.services.ws.rest.exception;

public class WssUpdateFundException extends RuntimeException {
	
	private static final long serialVersionUID = 212746530264269539L;

	private String code;
	
	public WssUpdateFundException(Throwable cause) {
		super(cause);
	}
	
	public WssUpdateFundException(String message, String code) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
