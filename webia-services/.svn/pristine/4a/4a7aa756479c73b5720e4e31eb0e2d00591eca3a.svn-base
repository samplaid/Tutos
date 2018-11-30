package lu.wealins.webia.services.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import lu.wealins.common.dto.liability.services.AgentDataForTransfersResponse;
import lu.wealins.common.dto.liability.services.BrokerProcessDTO;
import lu.wealins.common.dto.webia.services.PageResult;
import lu.wealins.webia.services.core.components.CommissionToPayWrapper;
import lu.wealins.webia.services.core.persistence.entity.CommissionToPayEntity;
import lu.wealins.webia.services.core.persistence.entity.TransferEntity;

public interface PaymentCommissionService {

	List<CommissionToPayWrapper> getCommissionsToPayByType(String comType);

	PageResult<CommissionToPayWrapper> getCommissionsToPayByType(String comType, int page, int size);

	List<CommissionToPayEntity> update(List<CommissionToPayEntity> commissionToPayEntities);

	List<BrokerProcessDTO> getCommissionAvailableForReportDistinctByBroker(List<String> type, List<String> period, String agent);
	
	@Transactional(rollbackFor={Exception.class})
	List<TransferEntity> processBrokerAndCreateTransfers(AgentDataForTransfersResponse agentDataForTransfersResponse) throws Exception;
	
	
	List<CommissionToPayEntity> exportCommissionsToPayReadyForGenerateStatement(Long transferId);

}
