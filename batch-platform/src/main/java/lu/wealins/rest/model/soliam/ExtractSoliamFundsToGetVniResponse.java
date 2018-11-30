package lu.wealins.rest.model.soliam;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.rest.ServerError;

/**
 * Wrapper with the fund list which need to get a VNI
 * 
 * @author xqv60
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtractSoliamFundsToGetVniResponse extends ServerError {

	/**
	 * Flag to indicate if the response if the first page of the paginated response
	 */
	private boolean firstPage;

	/**
	 * Flag to indicate if the response if the last page of the paginated response
	 */
	private boolean lastPage;

	/**
	 * List of code ISIN
	 */
	private List<String> isins;

	/**
	 * @return the isins
	 */
	public List<String> getIsins() {
		if (isins == null) {
			isins = new ArrayList<>();
		}
		return isins;
	}

	/**
	 * @param isins the isins to set
	 */
	public void setIsins(List<String> isins) {
		this.isins = isins;
	}

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
}
