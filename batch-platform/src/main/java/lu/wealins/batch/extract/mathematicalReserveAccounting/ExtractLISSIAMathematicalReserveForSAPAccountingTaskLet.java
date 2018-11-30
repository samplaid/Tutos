/**
 * 
 */
package lu.wealins.batch.extract.mathematicalReserveAccounting;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

import lu.wealins.common.dto.webia.services.SapAccountingRowNoEntityDTO;
import lu.wealins.common.dto.webia.services.SaveSapAccountingRequest;
import lu.wealins.common.dto.webia.services.SaveSapAccountingResponse;
import lu.wealins.rest.model.reporting.GetMathematicalReserveToExportRequest;
import lu.wealins.rest.model.reporting.GetMathematicalReserveToExportResponse;
import lu.wealins.rest.model.reporting.UpdateMathematicalReserveRequest;
import lu.wealins.rest.model.reporting.UpdateMathematicalReserveResponse;
import lu.wealins.utils.RestCallUtils;

/**
 * @author xqt5q
 *
 */
public class ExtractLISSIAMathematicalReserveForSAPAccountingTaskLet extends AbstractExtractLISSIAMathematicalReserveForSAPAccountingTaskLet{


	private static String UPDATE_REPORTING_MATHEMATICAL_RESERVE = "reporting/mathematicalReserve/updateMathematicalReserve";
	private static String SAVE_WEBIA_SAP_ACCOUNTING = "webia/mathematicalReserve/saveSapAccounting";
	private static String GET_REPORTING_MATHEMATICAL_RESERVE_TO_EXPORT = "reporting/mathematicalReserve/getMathematicalReserveToExport";

	private static String EURO_CURRENCY = "EUR";
	private static String CLOTURE_MODE = "C";
	private static String COMPANY = "1600";
	private static String PIECE = "PR";
	private static String _1706 = "1706";
	private static String FUND_TYPE_KEY = "FUND_TYPE";
	private static String CURRENCY_KEY = "CURRENCY";
	private static String _3020000 = "3020000";
	private static String _802000050 = "802000050";
	private static String CREDIT = "C";
	private static String DEBIT = "D";
	private static String GENERAL_ACCOUNT = "V";
	private static String RESERVE_LABEL = "PM";
	private static String LISSIA_IDENTIFIER = "LISSIA";
	private static String EQUAL_IDENTIFIER = "EQUAL";


	Log logger = LogFactory.getLog(ExtractLISSIAMathematicalReserveForSAPAccountingTaskLet.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org. springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		logger.info("STARTING EXTRACT OF LISSIA MATHEMATICAL RESERVE");
		
		//get the sap accounting to write
		
		GetMathematicalReserveToExportResponse response = getMathematicalReservesToExport(CLOTURE_MODE);
		List<SapAccountingRowNoEntityDTO> sapAccountingsToWrite = response.getSapAccountingList();
	
		logger.info("INSERTING MATHEMATICAL RESERVE IN SAP ACCOUNTING ...");
		
		//insert sap accounting
		
		saveAllSapAccountings(sapAccountingsToWrite);
		
		//update the mathematical reserve
		
		updateMathematicalReserve();
		
		return RepeatStatus.FINISHED;
	
	}




	/**
	 * get mathematical reserves to export
	 * @param mathematicalReserves
	 * @return
	 */
	public GetMathematicalReserveToExportResponse getMathematicalReservesToExport(String mode){
		ParameterizedTypeReference<GetMathematicalReserveToExportResponse> typeRef = new ParameterizedTypeReference<GetMathematicalReserveToExportResponse>() {};
		GetMathematicalReserveToExportRequest request = new GetMathematicalReserveToExportRequest();
		request.setMode(mode);
		ResponseEntity<GetMathematicalReserveToExportResponse> responseCall = RestCallUtils.postRest(getPiaRootContextURL()+GET_REPORTING_MATHEMATICAL_RESERVE_TO_EXPORT, request, GetMathematicalReserveToExportRequest.class, typeRef, keycloackUtils, logger);
		GetMathematicalReserveToExportResponse response = responseCall.getBody();
		return response;

	}
		
	
	/**
	 * save sap accounting
	 * @param mathematicalReserves
	 * @return
	 */
	public SaveSapAccountingResponse saveSapAccounting(SaveSapAccountingRequest request){
		ParameterizedTypeReference<SaveSapAccountingResponse> typeRef = new ParameterizedTypeReference<SaveSapAccountingResponse>() {};
		ResponseEntity<SaveSapAccountingResponse> responseCall = RestCallUtils.postRest(getPiaRootContextURL()+SAVE_WEBIA_SAP_ACCOUNTING, request, SaveSapAccountingRequest.class, typeRef, keycloackUtils, logger);
		SaveSapAccountingResponse response = responseCall.getBody();
		return response;

	}
	
	
	private void saveAllSapAccountings(List<SapAccountingRowNoEntityDTO> list) {
		List<List<SapAccountingRowNoEntityDTO>> listOfList = ListUtils.partition(list, 500);
		
		for (List<SapAccountingRowNoEntityDTO> l : listOfList) {
			SaveSapAccountingRequest request = new SaveSapAccountingRequest();
			request.setSapAccountingList(l);
			SaveSapAccountingResponse response = saveSapAccounting(request);
		}
	}


	/**
	 * update mathematical reserve
	 * @param 
	 * @return
	 */
	public UpdateMathematicalReserveResponse updateMathematicalReserve(){
		ParameterizedTypeReference<UpdateMathematicalReserveResponse> typeRef = new ParameterizedTypeReference<UpdateMathematicalReserveResponse>() {};
		UpdateMathematicalReserveRequest request = new UpdateMathematicalReserveRequest();
		request.setMode(CLOTURE_MODE);
		request.setExportDate(new Date());
		ResponseEntity<UpdateMathematicalReserveResponse> responseCall = RestCallUtils.postRest(getPiaRootContextURL()+UPDATE_REPORTING_MATHEMATICAL_RESERVE, request, UpdateMathematicalReserveRequest.class, typeRef, keycloackUtils, logger);
		UpdateMathematicalReserveResponse response = responseCall.getBody();
		return response;

	}
	



}
