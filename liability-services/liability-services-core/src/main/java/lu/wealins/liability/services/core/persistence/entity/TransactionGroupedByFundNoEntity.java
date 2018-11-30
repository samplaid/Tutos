package lu.wealins.liability.services.core.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Pojo for the order extraction
 * 
 * @author xqv60
 *
 */
public class TransactionGroupedByFundNoEntity {
	
	private String policyId;
	
	private String fundId;
	
	private BigDecimal units;

	public TransactionGroupedByFundNoEntity(String policyId, String fundId, BigDecimal units) {
		super();
		this.policyId = policyId;
		this.fundId = fundId;
		this.units = units;
	}

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

	/**
	 * @return the units
	 */
	public BigDecimal getUnits() {
		return units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(BigDecimal units) {
		this.units = units;
	}
	
	
	

}
