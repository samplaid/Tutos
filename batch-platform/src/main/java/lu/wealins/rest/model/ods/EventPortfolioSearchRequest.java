package lu.wealins.rest.model.ods;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.rest.model.ods.common.EventType;

/**
 * The event search request for portfolio.
 * 
 * @author lax
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventPortfolioSearchRequest extends PaginateSearchRequest {

	/**
	 * The policy id
	 */
	private String policyId;

	/**
	 * The begin date of the event date
	 */
	private Date beginDate;

	/**
	 * The end date of the event date
	 */
	private Date endDate;

	/**
	 * The event types to search
	 */
	private List<EventType> types;
	
	/**
	 * The fund id.
	 */
	private String fundId;

	/**
	 * @return the policyId
	 */
	public String getPolicyId() {
		return policyId;
	}

	/**
	 * @param policyId the policyId to set
	 */
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	/**
	 * @return the beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<EventType> getTypes() {
		return types;
	}

	public void setTypes(List<EventType> types) {
		this.types = types;
	}

	/**
	 * @return the fundId
	 */
	public String getFundId() {
		return fundId;
	}

	/**
	 * @param fundId the fundId to set
	 */
	public void setFundId(String fundId) {
		this.fundId = fundId;
	}
}
