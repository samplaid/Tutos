package lu.wealins.webia.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.SepaDocumentRequest;
import lu.wealins.common.dto.webia.services.SurrenderTransferFormDataDTO;
import lu.wealins.common.dto.webia.services.TransferComptaDTO;
import lu.wealins.common.dto.webia.services.TransferExecutionRequest;
import lu.wealins.common.dto.webia.services.TransferRefuseDTO;
import lu.wealins.common.dto.webia.services.WithdrawalTransferFormDataDTO;
import lu.wealins.webia.core.service.TransferService;
import lu.wealins.webia.ws.rest.TransferRESTService;

@Component
public class TransferRESTServiceImpl implements TransferRESTService {

	@Autowired
	private TransferService transferService;

	@Override
	public WithdrawalTransferFormDataDTO initFormDataForWithdrawal(Integer workflowItemId) {
		return transferService.getWithdrawalFormData(workflowItemId);
	}

	@Override
	public SurrenderTransferFormDataDTO initFormDataForSurrender(Integer workflowItemId) {
		return transferService.getSurrenderFormData(workflowItemId);
	}

	@Override
	public Collection<TransferComptaDTO> executeFax(SecurityContext context, TransferExecutionRequest transferExecutionRequest) {
		return transferService.executeFax(context, transferExecutionRequest);
	}

	@Override
	public Collection<TransferComptaDTO> executeSepa(SecurityContext context, SepaDocumentRequest sepaDocumentRequest) {
		return transferService.executeSepa(context, sepaDocumentRequest);
	}

	@Override
	public Collection<TransferComptaDTO> accept(Long transferId) {
		return transferService.accept(transferId);
	}

	@Override
	public TransferComptaDTO refuse(Long transferId, TransferRefuseDTO refuseDTO) {
		return transferService.refuse(transferId, refuseDTO);
	}

	@Override
	public Collection<TransferComptaDTO> getComptaPayments() {
		return transferService.getComptaPayments();
	}

	@Override
	public Collection<TransferComptaDTO> executeCsv(SecurityContext context,
			TransferExecutionRequest transferExecutionRequest) {
		
		return transferService.executeCsv(context, transferExecutionRequest);
	}
	
	
}
