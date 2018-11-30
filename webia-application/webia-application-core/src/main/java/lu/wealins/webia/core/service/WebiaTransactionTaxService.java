package lu.wealins.webia.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.SurrenderReportResultDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxSendingDTO;

public interface WebiaTransactionTaxService {

	/**
	 * Get the transaction tax according its id.
	 * 
	 * @param transactionTaxId The transaction tax id.
	 * @return The transaction tax.
	 */
	TransactionTaxDTO getTransactionTax(Long transactionTaxId);
	
	/**
	 * Get the transaction tax details linked to the transaction tax
	 * 
	 * @param transactionTaxId
	 * @return
	 */
	Collection<TransactionTaxDetailsDTO> getTransactionTaxDetails(Long transactionTaxId);

	/**
	 * Get the transaction tax of specified policy.
	 * 
	 * @param policyId
	 *            The policy Id
	 * @return the TransactionTax of policy
	 */
	Collection<TransactionTaxDTO> getTransactionTaxByPolicy(String policyId);

	/**
	 * Get the transaction tax by specified originId.
	 * 
	 * @param originId
	 *            The origin Id the id of tranaction in Lissia
	 * @return the TransactionTax
	 */
	TransactionTaxDTO getTransactionTaxByOriginId(Long originId);

	/**
	 * Get the transaction tax Detail for the specified ID.
	 * @param the transactionTaxId
	 * @return
	 */
	SurrenderReportResultDTO getTransactionTaxReportResult(Long transactionTaxId);

	/**
	 * Get the transaction tax Sending for the specified ID.
	 * 
	 * @param the
	 *            transactionTaxId
	 * @return
	 */
	TransactionTaxSendingDTO getTransactionTaxSending(Long transactionTaxId);

}
