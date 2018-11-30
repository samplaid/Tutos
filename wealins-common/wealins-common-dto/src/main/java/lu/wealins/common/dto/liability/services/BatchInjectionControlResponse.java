package lu.wealins.common.dto.liability.services;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchInjectionControlResponse {

	private Boolean success;
	private List<ErrorDTO> errors = new ArrayList<>();
	private List<String> lines = new ArrayList<>();

	public Boolean isSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
	 * @return the errors
	 */
	public List<ErrorDTO> getErrors() {
		if (errors == null) {
			errors = new ArrayList<>();
		}
		return errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(List<ErrorDTO> errors) {
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
