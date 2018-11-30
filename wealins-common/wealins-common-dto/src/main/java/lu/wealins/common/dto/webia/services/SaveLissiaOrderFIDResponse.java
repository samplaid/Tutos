package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Save lissia order FID response
 * 
 * @author xqt5q
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveLissiaOrderFIDResponse {

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
