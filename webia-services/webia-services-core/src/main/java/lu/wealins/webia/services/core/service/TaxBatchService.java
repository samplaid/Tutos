package lu.wealins.webia.services.core.service;

import java.util.List;
import lu.wealins.common.dto.webia.services.TransactionTaxDTO;


public interface TaxBatchService {

	/**
	 * Create the transaction tax details and update the transaction taxes.
	 * 
	 * @return the list of transaction tax ids which need to be included in the report.
	 * @param List of transaction details to persist.
	 */
	List<Long> createTransactionTaxDetails();

	
	/**
	 * Generate and save transactionsTaxDetails from TransactionTaxDTO.
	 * 
	 * @param transactionTax
	 * @param frenchTaxable 
	 * 				The flag true if and only if transaction is French taxable.
	 */
	void createTransactionTaxDetails(List<TransactionTaxDTO> transactionTax,boolean frenchTaxable);
}
