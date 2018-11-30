package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.webia.services.StatementDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatementResponse {

	private StatementDTO statement;
	private String error;
	/**
	 * @return the statement
	 */
	public StatementDTO getStatement() {
		return statement;
	}
	/**
	 * @param statement the statement to set
	 */
	public void setStatement(StatementDTO statement) {
		this.statement = statement;
	}
	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
	
}
