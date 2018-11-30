package lu.wealins.rest.model.ods.common;

import java.math.BigDecimal;
import java.util.Date;

/**
 * NAV Model returned as component of a service response
 * 
 * @author bqv55
 *
 */
public class Nav {

	/**
	 * NAV date
	 */
	private Date date;

	/**
	 * Value of the NAV
	 */
	private BigDecimal value;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
