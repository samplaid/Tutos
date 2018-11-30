package lu.wealins.rest.model.soliam;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.rest.ServerError;

/**
 * Wrapper with the fund list
 * 
 * @author xqv60
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtractSoliamFundsResponse extends ServerError {
	/**
	 * Flag to indicate if the response if the first page of the paginated response
	 */
	private boolean firstPage;

	/**
	 * Flag to indicate if the response if the last page of the paginated response
	 */
	private boolean lastPage;

	/**
	 * List of funds
	 */
	private List<Fund> funds;

	/**
	 * @return the lastPage
	 */
	public boolean isLastPage() {
		return lastPage;
	}

	/**
	 * @param lastPage the lastPage to set
	 */
	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

	/**
	 * @return the firstPage
	 */
	public boolean isFirstPage() {
		return firstPage;
	}

	/**
	 * @param firstPage the firstPage to set
	 */
	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}

	/**
	 * @return the funds
	 */
	public List<Fund> getFunds() {
		if (funds == null) {
			funds = new ArrayList<>();
		}
		return funds;
	}

	/**
	 * @param funds the funds to set
	 */
	public void setFunds(List<Fund> funds) {
		this.funds = funds;
	}

}
