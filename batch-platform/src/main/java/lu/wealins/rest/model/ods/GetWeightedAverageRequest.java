package lu.wealins.rest.model.ods;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Fund NAVs load request class
 * 
 * @author bqv55
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetWeightedAverageRequest {

	private EventPortfolioSearchRequest request;

	private boolean isForTG;

	/**
	 * @return the request
	 */
	public EventPortfolioSearchRequest getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(EventPortfolioSearchRequest request) {
		this.request = request;
	}

	/**
	 * @return the isForTG
	 */
	public boolean isForTG() {
		return isForTG;
	}

	/**
	 * @param isForTG the isForTG to set
	 */
	public void setForTG(boolean isForTG) {
		this.isForTG = isForTG;
	}
}
