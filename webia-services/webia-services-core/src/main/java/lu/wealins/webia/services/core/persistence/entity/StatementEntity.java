package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author xqv95
 *
 */
@Entity
@Table(name = "STATEMENTS")
public class StatementEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 231654656541L;
	@Id
	@Column(name = "STATEMENT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long statementId;

	@Column(name = "STATEMENT_TYPE")
	private String statementType;

	@Column(name = "STATEMENT_STATUS")
	private String statementStatus;

	@Column(name = "AGENT_ID")
	private String agentId;

	@Column(name = "PERIOD")
	private String period;

	@Column(name = "STATEMENT_DATE")
	private Date statementDate;
	
	@Column(name = "ERROR_DESC")
	private String errorDesc;

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

	/**
	 * @return the errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}

	/**
	 * @param errorDesc the errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
}
