package lu.wealins.webia.ws.rest.impl.exception;

public class EntrepriseServiceBusException extends RuntimeException {

	private static final long serialVersionUID = 5730650493441142881L;

	private int status;

	public EntrepriseServiceBusException(int status, String message) {
		super(message);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
