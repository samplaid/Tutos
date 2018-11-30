package lu.wealins.webia.core.service;

import java.util.List;

import lu.wealins.common.dto.liability.services.AgentDataForTransfersResponse;
import lu.wealins.common.dto.webia.services.AgentIdListResponse;
import lu.wealins.common.dto.liability.services.BrokerProcessDTO;
import lu.wealins.common.dto.webia.services.CommissionToPayWrapperDTO;
import lu.wealins.common.dto.webia.services.StatementDTO;
import lu.wealins.common.dto.webia.services.StatementRequest;
import lu.wealins.common.dto.webia.services.StatementResponse;
import lu.wealins.common.dto.webia.services.StatementUpdateStatusRequest;
import lu.wealins.common.dto.webia.services.StatementWrapperDTO;
import lu.wealins.common.dto.webia.services.TransferWrapperDTO;

public interface WebiaCommissionsService {
	
	/**
	 * Get transfer ready for generate statement.
	 * 
	 * @param agentId
	 * @param statementId
	 * @return The TransferWrapperDTO.
	 */
	TransferWrapperDTO getTransfersReady(String agentId, String statementId);

	/**
	 * Get commissions ready for generate statement.
	 * 
	 * @param transferId The transfer Id.
	 * @return The CommissionToPayWrapperDTO.
	 */
	CommissionToPayWrapperDTO getCommissionsReady(String transferId);

	/**
	 * Get statement.
	 * 
	 * @param statementId The transfer Id.
	 * @return The StatementDTO.
	 */
	StatementDTO getStatement(String statementId);

	/**
	 * create a entry commission statement in webia table.
	 * 
	 * @param statementRequest The {@link StatementRequest}.
	 * @return The StatementResponse.
	 */
	StatementResponse createStatement(StatementRequest statementRequest);
	
	/**
	 * Get available broker commission match with parameters
	 * @param StatementWrapperDTO 
	 * 
	 * @return List<BrokerProcessDTO>
	 */
	List<BrokerProcessDTO> getAvailableBrokerCommission(StatementWrapperDTO statement);

	/**
	 * Get application parameter code for wealins broker
	 * 
	 * @return ApplicationParameterDTO
	 */
	AgentIdListResponse getDistinctTransfersBrokerByStatementId(Long statementId);
	
	/**
	 * Post available broker for create transfers
	 * @param dataAvailableBrokerResponse
	 * 
	 * @return TransferWrapperDTO
	 */
	TransferWrapperDTO processCommissionForCreateTransers(AgentDataForTransfersResponse dataAvailableBrokerResponse);
	
	/**
	 * Update statement status
	 * 
	 * @param statementUpdateStatusRequest
	 */
	void updateStatementStatus(StatementUpdateStatusRequest statementUpdateStatusRequest);
	
}
