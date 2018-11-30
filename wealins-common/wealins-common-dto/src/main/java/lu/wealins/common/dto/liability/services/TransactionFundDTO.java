/**
 * 
 */
package lu.wealins.common.dto.liability.services;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author NGA
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionFundDTO implements Serializable {

	/**
	 * Generated Serial UID.
	 */
	private static final long serialVersionUID = -978366843489804773L;
	private BigDecimal tranactionId;
	private BigDecimal netAmount;
	/**
	 * @return the tranactionId
	 */
	public BigDecimal getTranactionId() {
		return tranactionId;
	}
	/**
	 * @param tranactionId the tranactionId to set
	 */
	public void setTranactionId(BigDecimal tranactionId) {
		this.tranactionId = tranactionId;
	}
	/**
	 * @return the netAmount
	 */
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	
	
}
