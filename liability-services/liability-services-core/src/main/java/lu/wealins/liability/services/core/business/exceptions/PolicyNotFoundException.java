package lu.wealins.liability.services.core.business.exceptions;

public class PolicyNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8294197101271642128L;

	public PolicyNotFoundException(String message) {
		super(message);
	}

}
