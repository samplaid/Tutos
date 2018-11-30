package lu.wealins.webia.services.core.service.impl;

import java.util.List;

import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;

public interface TransactionTaxDetailsGenerator {
	
	
	List<TransactionTaxDetailsDTO> generateTransactionTaxDetails(TransactionTaxDTO transactionTax, List<TransactionTaxDetailsDTO> previousDetails, boolean frenchTaxable);

	boolean supportsType(String transactionType);
	
}
