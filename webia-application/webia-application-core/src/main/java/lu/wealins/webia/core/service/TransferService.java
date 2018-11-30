package lu.wealins.webia.core.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.webia.services.SepaDocumentRequest;
import lu.wealins.common.dto.webia.services.SurrenderTransferFormDataDTO;
import lu.wealins.common.dto.webia.services.TransferComptaDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.TransferExecutionRequest;
import lu.wealins.common.dto.webia.services.TransferRefuseDTO;
import lu.wealins.common.dto.webia.services.WithdrawalTransferFormDataDTO;

public interface TransferService {

	WithdrawalTransferFormDataDTO getWithdrawalFormData(Integer workflowItemId);
	
	SurrenderTransferFormDataDTO getSurrenderFormData(Integer workflowItemId);

	Collection<TransferDTO> getTransfers(List<Long> ids);
	
	<T extends TransferDTO> Collection<T> updateTransfers(List<T> transfers);

	HashMap<String, List<TransferDTO>> groupBySwiftRecipientContribCurrency(List<Long> ids) throws Exception;

	Collection<TransferComptaDTO> executeFax(SecurityContext context, TransferExecutionRequest transferExecutionRequest);

	Collection<TransferComptaDTO> executeSepa(SecurityContext context, SepaDocumentRequest sepaDocumentRequest);

	TransferDTO createAdminFeesTransfer(Integer workflowItemId, String policyId, BigDecimal amount, String currency);

	boolean hasReady(Collection<TransferDTO> transfers);

	Collection<TransferComptaDTO> getComptaPayments();

	Collection<TransferComptaDTO> accept(Long transferId);

	TransferComptaDTO refuse(Long transferId, TransferRefuseDTO refuseDTO);

	TransferDTO initSecuritiesTransferForSurrender(Integer workflowItemId, FundLiteDTO fund);

	String getBankSwift(String depositBank);
	
	Collection<TransferComptaDTO> executeCsv(SecurityContext context, TransferExecutionRequest transferExecutionRequest);
}
