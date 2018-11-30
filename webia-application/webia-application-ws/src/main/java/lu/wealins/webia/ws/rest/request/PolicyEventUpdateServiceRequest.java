package lu.wealins.webia.ws.rest.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Policy service request exposed object
 * 
 * @author TEY
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyEventUpdateServiceRequest {

	private Long eventId;
	private BigDecimal plusValueBefore;
	private BigDecimal plusValueAfter;

	/**
	 * @return the eventId
	 */
	public Long getEventId() {
		return eventId;
	}
	
	/**
	 * @param eventId
	 *            the eventId to set
	 */
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the plusValueBefore
	 */
	public BigDecimal getPlusValueBefore() {
		return plusValueBefore;
	}

	/**
	 * @param plusValueBefore
	 *            the plusValueBefore to set
	 */
	public void setPlusValueBefore(BigDecimal plusValueBefore) {
		this.plusValueBefore = plusValueBefore;
	}
	
	/**
	 * @return the plusValueAfter
	 */
	public BigDecimal getPlusValueAfter() {
		return plusValueAfter;
	}
	
	/**
	 * @param plusValueAfter
	 *            the plusValueAfter to set
	 */
	public void setPlusValueAfter(BigDecimal plusValueAfter) {
		this.plusValueAfter = plusValueAfter;
	}


}
