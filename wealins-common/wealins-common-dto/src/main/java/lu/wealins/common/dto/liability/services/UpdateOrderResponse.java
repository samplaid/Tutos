package lu.wealins.common.dto.liability.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The update order response
 * 
 * @author xqv60
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateOrderResponse {

	/**
	 * The succuess flag
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
