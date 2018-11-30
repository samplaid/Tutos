package lu.wealins.webia.services.core.service;

import lu.wealins.common.dto.webia.services.DeleteMathematicalReserveRequest;
import lu.wealins.common.dto.webia.services.DeleteMathematicalReserveResponse;
import lu.wealins.common.dto.webia.services.GetMathematicalReserveRequest;
import lu.wealins.common.dto.webia.services.GetMathematicalReserveResponse;
import lu.wealins.common.dto.webia.services.SaveMathematicalReserveRequest;
import lu.wealins.common.dto.webia.services.SaveMathematicalReserveResponse;
import lu.wealins.common.dto.webia.services.SaveSapAccountingRequest;
import lu.wealins.common.dto.webia.services.SaveSapAccountingResponse;

public interface MathematicalReserveService {


	/**
	 * Save the mathematical reserve
	 * 
	 * @param saveMathematicalReserveRequest
	 * @return saveMathematicalReserveResponse
	 */
	SaveMathematicalReserveResponse saveMathematicalReserve(SaveMathematicalReserveRequest saveMathematicalReserveRequest);
	
	/**
	 * get mathematical reserves
	 * @param getMathematicalReserveRequest
	 * @return
	 */
	GetMathematicalReserveResponse getMathematicalReserve(GetMathematicalReserveRequest getMathematicalReserveRequest);

	/**
	 * save sap accounting
	 * @param saveSapAccounting
	 * @return
	 */
	SaveSapAccountingResponse saveSapAccounting(SaveSapAccountingRequest request);
	
	/**
	 * delete mathematical reserve by mode and date
	 * @param request
	 * @return
	 */
	DeleteMathematicalReserveResponse deleteByModeAndDate(DeleteMathematicalReserveRequest request);
	



	

	
}
