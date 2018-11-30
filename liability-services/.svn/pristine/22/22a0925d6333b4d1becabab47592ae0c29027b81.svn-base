package lu.wealins.liability.services.core.business.exceptions;

import java.util.ArrayList;
import java.util.Collection;

public abstract class ReportException extends RuntimeException {

	private static final long serialVersionUID = -1242331712662901046L;
	
	private Collection<String> errors = new ArrayList<>();
	private Collection<String> warns = new ArrayList<>();
	
	public Collection<String> getErrors() {
		return errors;
	}

	public void setErrors(Collection<String> errors) {
		this.errors = errors;
	}

	public Collection<String> getWarns() {
		return warns;
	}

	public void setWarns(Collection<String> warns) {
		this.warns = warns;
	}
	
}
