package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PolicyTransactionsHistoryDTO {

	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date effectiveDate;
	private String eventName;
	private Integer eventType;
	private BigDecimal grossAmount;
	private BigDecimal netAmount;
	private BigDecimal feeAmount;
	private BigDecimal taxAmount;
	private String currency;
	private String status;
	private Integer statusCode;
	private Integer coverage;
	private BigDecimal lastTrnId;
	private boolean isFrenchTaxable;
	private String policyClientCountry;
	private boolean isEventCanBeReported;
	private boolean isEventDateEligible;

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Integer getEventType() {
		return eventType;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}

	public BigDecimal getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(BigDecimal grossAmount) {
		this.grossAmount = grossAmount;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public BigDecimal getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Integer getCoverage() {
		return coverage;
	}

	public void setCoverage(Integer coverage) {
		this.coverage = coverage;
	}

	/**
	 * @return the lastTrnId
	 */
	public BigDecimal getLastTrnId() {
		return lastTrnId;
	}

	/**
	 * @param lastTrnId the lastTrnId to set
	 */
	public void setLastTrnId(BigDecimal lastTrnId) {
		this.lastTrnId = lastTrnId;
	}

	/**
	 * @return the policyClientCountry
	 */
	public String getPolicyClientCountry() {
		return policyClientCountry;
	}

	/**
	 * @param policyClientCountry the policyClientCountry to set
	 */
	public void setPolicyClientCountry(String policyClientCountry) {
		this.policyClientCountry = policyClientCountry;
	}

	/**
	 * @return the isFrenchTaxable
	 */
	public boolean isFrenchTaxable() {
		return isFrenchTaxable;
	}

	/**
	 * @param isFrenchTaxable
	 *            the isFrenchTaxable to set
	 */
	public void setFrenchTaxable(boolean isFrenchTaxable) {
		this.isFrenchTaxable = isFrenchTaxable;
	}

	/**
	 * @return the isEventCanBeReported
	 */
	public boolean isEventCanBeReported() {
		return isEventCanBeReported;
	}

	/**
	 * @param isEventCanBeReported the isEventCanBeReported to set
	 */
	public void setEventCanBeReported(boolean isEventCanBeReported) {
		this.isEventCanBeReported = isEventCanBeReported;
	}

	/**
	 * @return the isEventDateEligible
	 */
	public boolean isEventDateEligible() {
		return isEventDateEligible;
	}

	/**
	 * @param isEventDateEligible the isEventDateEligible to set
	 */
	public void setEventDateEligible(boolean isEventDateEligible) {
		this.isEventDateEligible = isEventDateEligible;
	}


}
