/**
 * 
 */
package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents the response for batch upload document into FileNet.
 * 
 * @author lax
 *
 */
public class BatchUploadResponse {
	private List<String> success = new ArrayList<>();
	private List<String> failure = new ArrayList<>();
	
	/**
	 * @return the success
	 */
	public List<String> getSuccess() {
		if (success == null) {
			success = new ArrayList<String>();
		}
		return success;
	}
	
	/**
	 * @param success the success to set
	 */
	public void setSuccess(List<String> success) {
		this.success = success;
	}
	
	/**
	 * @return the failure
	 */
	public List<String> getFailure() {
		if (failure == null) {
			failure = new ArrayList<String>();
		}
		return failure;
	}
	
	/**
	 * @param failure the failure to set
	 */
	public void setFailure(List<String> failure) {
		this.failure = failure;
	}
}
