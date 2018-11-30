package lu.wealins.rest.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xqv66
 *
 */
public class BatchInjectionControlResponse {

	private Boolean success;
	private List<Error> errors;
	private List<String> lines;

	public Boolean isSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
	 * @return the errors
	 */
	public List<Error> getErrors() {
		if (errors == null) {
			errors = new ArrayList<>();
		}
		return errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	/**
	 * @return the lines
	 */
	public List<String> getLines() {
		if (lines == null) {
			lines = new ArrayList<>();
		}
		return lines;
	}

	/**
	 * @param lines the lines to set
	 */
	public void setLines(List<String> lines) {
		this.lines = lines;
	}
}
