/**
 * 
 */
package lu.wealins.webia.services.ws.rest.request;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author oro
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommissionRequest<T> {
	private int page;
	private int size;

	private List<T> commissions;

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the commissions
	 */
	public List<T> getCommissions() {
		if (commissions == null) {
			commissions = new ArrayList<>();
		}
		return commissions;
	}

	/**
	 * @param commissions the commissions to set
	 */
	public void setCommissions(List<T> commissions) {
		this.commissions = commissions;
	}
}
