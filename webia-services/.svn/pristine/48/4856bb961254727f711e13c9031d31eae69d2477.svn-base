package lu.wealins.webia.services.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.ExtractOrderFIDResponse;
import lu.wealins.common.dto.webia.services.ExtractOrderResponse;
import lu.wealins.common.dto.webia.services.PageResult;
import lu.wealins.common.dto.webia.services.ReportingComDTO;
import lu.wealins.common.dto.webia.services.SapAccountingDTO;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderFIDRequest;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderFIDResponse;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderRequest;
import lu.wealins.common.dto.webia.services.SaveLissiaOrderResponse;

public interface ExtractService {

	PageResult<SapAccountingDTO> extractSapAccounting(String origin, int page, int size, Long lastId);
	
	Boolean confirmExtractSapAccounting(Collection<Long> successIds);
	
	PageResult<ReportingComDTO> extractReportingCom(int page, int size, Long lastId, Long reportId);

	Boolean confirmExtractReportingCom(Collection<Long> successIds);

	/**
	 * Extraction of order from DALI and LISSIA
	 * 
	 * @return the extract order response
	 */
	ExtractOrderResponse extractOrder();

	/**
	 * Save the lissia order
	 * 
	 * @param saveLissiaOrderRequest the save lissia order request
	 * @return the save lissia order response
	 */
	SaveLissiaOrderResponse saveLissiaOrder(SaveLissiaOrderRequest saveLissiaOrderRequest);
	
	/**
	 * Save the lissia order FID
	 * 
	 * @param saveLissiaOrderFIDRequest the save lissia order FID request
	 * @return the save lissia order FID response
	 */
	SaveLissiaOrderFIDResponse saveLissiaOrderFID(SaveLissiaOrderFIDRequest saveLissiaOrderFIDRequest);
	
	/**
	 * Extraction of orderFID for LISSIA
	 * 
	 * @return the extract order response
	 */
	ExtractOrderFIDResponse extractOrderFID();
	
}
