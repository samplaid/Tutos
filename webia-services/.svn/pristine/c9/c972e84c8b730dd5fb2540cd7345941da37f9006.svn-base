package lu.wealins.webia.services.core.service;

import java.util.Collection;
import java.util.List;

import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.TransferExecutionRequest;
import lu.wealins.common.dto.webia.services.TransferRefuseDTO;

public interface TransferService {

	Collection<TransferDTO> updateTransfers(Collection<TransferDTO> transferDTOs);
	
	Collection<TransferDTO> updateWithdrawalTransfers(Collection<TransferDTO> payments);

	Collection<TransferDTO> exportTransferReadyForGenerateStatement(Long statementId, String agentId);
	
	List<String> getDistinctTransfersByStatement(Long statementId);

	Collection<TransferDTO> getComptaPayments();

	TransferDTO acceptByCompta(Long transferId);

	TransferDTO refuse(Long transferId, TransferRefuseDTO refuseDTO);
	
	TransferDTO getTransfer(Long transferId);

	Collection<TransferDTO> getTransfers(List<Long> transferId);

	Collection<TransferDTO> execute(TransferExecutionRequest transferExecutionRequest);

	Collection<TransferDTO> updateToComptaStatus(Collection<TransferDTO> transferDTOs);

	Collection<TransferDTO> acceptByCps2(Collection<TransferDTO> transfers);
}
