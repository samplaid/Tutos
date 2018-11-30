package lu.wealins.webia.services.core.service;

import java.util.List;

import lu.wealins.common.dto.webia.services.SurrenderReportResultDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxSendingDTO;

public interface TransactionTaxService {
	public List<TransactionTaxDTO> insertTransactionTax(List<TransactionTaxDTO> transactionTaxList);

	void markAsUpdated(long id);

	List<Long> filterCalculated(List<Long> transactionTaxIds);

	List<TransactionTaxDTO> getNewTransactions();

	public List<TransactionTaxDTO> cancelTransactionTax(List<TransactionTaxDTO> transactionTaxList);

	TransactionTaxDTO getTransactionTax(Long id);

	List<TransactionTaxDTO> getTransactionsTaxByPolicy(String policy);

	TransactionTaxDTO getTransactionTaxByOriginId(Long id);

	SurrenderReportResultDTO getTransactionsTaxReportResult(Long transactionTaxId);

	List<Long> updateTransactions(List<Long> transactionTaxIds);

	TransactionTaxSendingDTO getTransactionTaxSending(Long id);
}
