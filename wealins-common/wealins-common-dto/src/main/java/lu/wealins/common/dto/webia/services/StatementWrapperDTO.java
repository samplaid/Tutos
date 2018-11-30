package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatementWrapperDTO {
	private Long statementId;
	private String statementStatus;
	private String agentId;
	private String statementType;
	private String period;
	private List<String> statementTypeValue = new ArrayList<>();
	private List<String> periodValue = new ArrayList<>();
	private Date statementDate;
	/**
	 * @return the statementId
	 */
	public Long getStatementId() {
		return statementId;
	}
	/**
	 * @param statementId the statementId to set
	 */
	public void setStatementId(Long statementId) {
		this.statementId = statementId;
	}
	/**
	 * @return the statementStatus
	 */
	public String getStatementStatus() {
		return statementStatus;
	}
	/**
	 * @param statementStatus the statementStatus to set
	 */
	public void setStatementStatus(String statementStatus) {
		this.statementStatus = statementStatus;
	}
	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}
	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	/**
	 * @return the statementType
	 */
	public String getStatementType() {
		return statementType;
	}
	/**
	 * @param statementType the statementType to set
	 */
	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}
	/**
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}
	/**
	 * @param period the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}
	/**
	 * @return the statementTypeValue
	 */
	public List<String> getStatementTypeValue() {
		return statementTypeValue;
	}
	/**
	 * @param statementTypeValue the statementTypeValue to set
	 */
	public void setStatementTypeValue(List<String> statementTypeValue) {
		this.statementTypeValue = statementTypeValue;
	}
	/**
	 * @return the periodValue
	 */
	public List<String> getPeriodValue() {
		return periodValue;
	}
	/**
	 * @param periodValue the periodValue to set
	 */
	public void setPeriodValue(List<String> periodValue) {
		this.periodValue = periodValue;
	}
	/**
	 * @return the statementDate
	 */
	public Date getStatementDate() {
		return statementDate;
	}
	/**
	 * @param statementDate the statementDate to set
	 */
	public void setStatementDate(Date statementDate) {
		this.statementDate = statementDate;
	}
}
