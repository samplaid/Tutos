package lu.wealins.rest.model.reporting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Save mathematical reserve response
 * 
 * @author xqt5q
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveMathematicalReserveResponse {

	/**
	 * Success flag
	 */
	private boolean success;

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
