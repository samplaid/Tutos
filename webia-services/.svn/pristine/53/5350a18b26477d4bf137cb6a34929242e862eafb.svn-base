package lu.wealins.webia.services.core.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import lu.wealins.webia.services.core.components.PstIdWrapper;
import lu.wealins.common.dto.webia.services.TransactionDTO;

public interface TransactionExtractionService {

	/**
	 * Insert Transactions into Commission to pay
	 * 
	 * @param transactionDTOs
	 * @return List of PstIdWrapper
	 */
	List<PstIdWrapper> processAccountTransactionsIntoSAPAccounting(Collection<TransactionDTO> transactionDTOs);

	/**
	 * Delete Transactions from SAP_ACCOUNTING
	 * 
	 * @param ids
	 * @return record deleted
	 */
	Long removeTransactionsFromSAPAccounting(List<Long> sapAccIds);
}
