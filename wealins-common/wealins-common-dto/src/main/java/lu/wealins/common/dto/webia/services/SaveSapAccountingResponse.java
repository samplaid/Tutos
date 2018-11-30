package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Save sap accounting response
 * 
 * @author xqt5q
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveSapAccountingResponse {

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
