package lu.wealins.webia.services.ws.rest.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.TransferExecutionRequest;
import lu.wealins.common.dto.webia.services.TransferRefuseDTO;
import lu.wealins.webia.services.core.service.TransferService;
import lu.wealins.webia.services.ws.rest.TransferRESTService;

@Component
public class TransferRESTServiceImpl implements TransferRESTService {

	@Autowired
	private TransferService transferService;

	@Override
	public Collection<TransferDTO> getComptaPayments() {
		return transferService.getComptaPayments();
	}

	@Override
	public TransferDTO accept(Long transferId) {
		return transferService.acceptByCompta(transferId);
	}

	@Override
	public TransferDTO refuse(Long transferId, TransferRefuseDTO refuseDTO) {
		return transferService.refuse(transferId, refuseDTO);
	}

	@Override
	public TransferDTO getTransfer(Long transferId) {
		return transferService.getTransfer(transferId);
	}	

	@Override
	public Collection<TransferDTO> getTransfers(List<Long> transferId) {
		return transferService.getTransfers(transferId);
	}

	@Override
	public Collection<TransferDTO> execute(TransferExecutionRequest transferExecutionRequest) {
		return transferService.execute(transferExecutionRequest);
	}

	@Override
	public Collection<TransferDTO> updateTransfers(Collection<TransferDTO> transferDTOs) {
		return transferService.updateTransfers(transferDTOs);
	}
}
