package lu.wealins.webia.services.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.ReportingComDTO;
import lu.wealins.common.dto.webia.services.TransactionDTO;

public interface TransactionExtractionCommissionService {

	/**
	 * Insert Transactions into Commission to pay
	 * 
	 * @param transactionDTOs
	 * @return List of POSTING_SETS ID
	 */
	Collection<Long> processTransactionsIntoCommissionToPay(Collection<TransactionDTO> transactionDTOs);

	/**
	 * Insert Transactions into Reporting Com
	 * 
	 * @param transactionDTOs
	 * @param reportId 
	 * @return List of POSTING_SETS ID
	 */
	Collection<Long> processTransactionsIntoReportingCom(Collection<TransactionDTO> transactionDTOs, Long reportId);

	/**
	 * Insert Transactions not fid into Reporting Com
	 * @param reportId 
	 * 
	 * @param transactionDTOs
	 * @return List of POSTING_SETS ID
	 */
	Collection<Long> processTransactionsExternal132IntoReportingCom(Collection<ReportingComDTO> reportingComDTO, Long reportId);
	
	/**
	 * Insert Transactions fid into Reporting Com
	 * @param reportId 
	 * 
	 * @param transactionDTOs
	 * @return List of POSTING_SETS ID
	 */
	Collection<Long> processTransactionsExternal133IntoReportingCom(Collection<ReportingComDTO> reportingComDTO, Long reportId);
}
