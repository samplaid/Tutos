package lu.wealins.common.dto.liability.services;

import java.util.ArrayList;
import java.util.List;

import lu.wealins.common.dto.webia.services.StatementWrapperDTO;

public class AgentDataForTransfersResponse {

	private StatementWrapperDTO statementWrapperDTO;
	private List<AgentDataForTransferDTO> agents = new ArrayList<>();
	private String error;
	
	/**
	 * @return the statementWrapperDTO
	 */
	public StatementWrapperDTO getStatementWrapperDTO() {
		return statementWrapperDTO;
	}
	/**
	 * @param statementWrapperDTO the statementWrapperDTO to set
	 */
	public void setStatementWrapperDTO(StatementWrapperDTO statementWrapperDTO) {
		this.statementWrapperDTO = statementWrapperDTO;
	}
	/**
	 * @return the agents
	 */
	public List<AgentDataForTransferDTO> getAgents() {
		return agents;
	}
	/**
	 * @param agents the agents to set
	 */
	public void setAgents(List<AgentDataForTransferDTO> agents) {
		this.agents = agents;
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
