package lu.wealins.webia.services.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.common.dto.webia.services.DeleteMathematicalReserveRequest;
import lu.wealins.common.dto.webia.services.DeleteMathematicalReserveResponse;
import lu.wealins.common.dto.webia.services.GetMathematicalReserveRequest;
import lu.wealins.common.dto.webia.services.GetMathematicalReserveResponse;
import lu.wealins.common.dto.webia.services.MathematicalReserveDTO;
import lu.wealins.common.dto.webia.services.PageResult;
import lu.wealins.common.dto.webia.services.SapAccountingRowNoEntityDTO;
import lu.wealins.common.dto.webia.services.SaveMathematicalReserveRequest;
import lu.wealins.common.dto.webia.services.SaveMathematicalReserveResponse;
import lu.wealins.common.dto.webia.services.SaveSapAccountingRequest;
import lu.wealins.common.dto.webia.services.SaveSapAccountingResponse;
import lu.wealins.webia.services.core.mapper.MathematicalReserveMapper;
import lu.wealins.webia.services.core.mapper.SapAccountingMapper;
import lu.wealins.webia.services.core.persistence.entity.MathematicalReserveEntity;
import lu.wealins.webia.services.core.persistence.entity.SapAccountingEntity;
import lu.wealins.webia.services.core.persistence.entity.SapMappingEntity;
import lu.wealins.webia.services.core.persistence.repository.MathematicalReserveRepository;
import lu.wealins.webia.services.core.persistence.repository.SapAccountingRepository;
import lu.wealins.webia.services.core.persistence.repository.SapMappingRepository;
import lu.wealins.webia.services.core.service.MathematicalReserveService;

@Service
@Transactional
public class MathematicalReserveServiceImpl implements MathematicalReserveService {

	Log logger = LogFactory.getLog(MathematicalReserveServiceImpl.class);
	
	@Autowired
	MathematicalReserveRepository mathematicalReserveRepository;
	
	@Autowired
	MathematicalReserveMapper mathematicalReserveMapper;
	
	@Autowired
	SapAccountingMapper sapAccountingMapper;
	
	@Autowired
	SapMappingRepository sapMappingRepository;
	
	@Autowired
	SapAccountingRepository sapAccountingRepository;;
	
	private static final String FUND_TYPE = "FUND_TYPE";
	private static final String CURRENCY = "CURRENCY";
	
	private static final String _3020000 = "3020000";
	private static final String _8020000500 = "8020000500";
	
	private static List<SapMappingEntity> fundTypeMappings = new ArrayList<SapMappingEntity>();
	private static List<SapMappingEntity> currencyMappings = new ArrayList<SapMappingEntity>();
	
	@Override
	public SaveMathematicalReserveResponse saveMathematicalReserve(SaveMathematicalReserveRequest request) {
		List<MathematicalReserveDTO> mathematicalReserveList = request.getMathematicalReserveList();
		List<MathematicalReserveEntity> entitiesToSave = mathematicalReserveMapper.asMathematicalReserveEntityList(mathematicalReserveList);
		mathematicalReserveRepository.save(entitiesToSave);
		SaveMathematicalReserveResponse response = new SaveMathematicalReserveResponse();
		response.setSuccess(true);
		return response;
	}

	@Override
	public GetMathematicalReserveResponse getMathematicalReserve(GetMathematicalReserveRequest request) {
		Page<MathematicalReserveEntity> entities  = mathematicalReserveRepository.findByModeAndDate(request.getMode(), request.getDate(), new PageRequest(request.getPageNum(), request.getPageSize()));
		List<MathematicalReserveDTO> toReturn = mathematicalReserveMapper.asMathematicalReserveDTOList(entities.getContent());
		
	
		GetMathematicalReserveResponse response = new GetMathematicalReserveResponse();
		PageResult<MathematicalReserveDTO> result = new PageResult<MathematicalReserveDTO>();
		
		result.setContent(toReturn);
		response.setMathematicalReserveList(result);
		return response;
	}
	
	@Override
	public SaveSapAccountingResponse saveSapAccounting(SaveSapAccountingRequest request) {
		
		SaveSapAccountingResponse response = new SaveSapAccountingResponse();
		response.setSuccess(true);
		
		fundTypeMappings = sapMappingRepository.findByType(FUND_TYPE);
		currencyMappings = sapMappingRepository.findByType(CURRENCY);
		
		List<SapAccountingRowNoEntityDTO> listToModify = request.getSapAccountingList();
		
		for (Iterator<SapAccountingRowNoEntityDTO> iter = listToModify.iterator(); iter.hasNext(); ) {
			SapAccountingRowNoEntityDTO element = iter.next();
			element.setSupport(findMapping(fundTypeMappings, element.getSupport()));
		}
		
		Date calculDate = listToModify.get(0).getCalculDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calculDate);
		
		/**
		 * OLD 
		 */
		// get and group the mathematical reserve for sap accounting insertion
		//List<SapAccountingRowNoEntity> entities  = mathematicalReserveRepository.findMathematicalReserveToExport(request.getMode());		
		List<SapAccountingEntity> toSave = mathematicalReserveMapper.asSapAccountingEntityList(request.getSapAccountingList());
		
		
		//SimpleDateFormat sdf = new SimpleDateFormat("MM-YYYY");
		
		int month = calendar.get(Calendar.MONTH)+1;
		int year = calendar.get(Calendar.YEAR);
		
		toSave.stream().forEach(sap -> sap.setCreationDate(new Date()));
		toSave.stream().forEach(sap -> sap.setCurrency(sap.getCurrency().trim()));
		toSave.stream().forEach(sap -> sap.setReconciliation(sap.getReconciliation()+month+"-"+year));
		
		/**
		 * ACCOUNTINGS
		 */
		
		//insert 100 by 100
		List<List<SapAccountingEntity>> entitiesListsToSave = ListUtils.partition(toSave, 100);
		List<SapAccountingEntity> entitiesSaved = new ArrayList<SapAccountingEntity>();
		
		logger.info("INSERTING MATHEMATICAL RESERVE IN SAP ACCOUNTING ... ("+toSave.size()+" entities)");
		
		for(List<SapAccountingEntity> list : entitiesListsToSave) {
			try {
				List<SapAccountingEntity> saveResult = sapAccountingRepository.save(list);
				entitiesSaved.addAll(saveResult);
				logger.info(entitiesSaved.size()+" SAP ACCOUNTING ENTITIES INSERTED");
			}catch(Exception e) {
				logger.error("ERROR DURING THE INSERT OF MATHEMATICAL RESERVE IN SAP ACCOUNTING");
				rollbackEntities(entitiesSaved);
				response.setSuccess(false);
				return response;
			}
		}
		
		
		/**
		 * CANCELLING ACCOUNTINGS
		 */
		
		List<SapAccountingEntity> cancellingAccountings = createCancellingAccountings(entitiesSaved);
		//insert 100 by 100
		List<List<SapAccountingEntity>> entitiesCancellingListsToSave = ListUtils.partition(cancellingAccountings, 100);
		List<SapAccountingEntity> entitiesCancellingSaved = new ArrayList<SapAccountingEntity>();
		
		for(List<SapAccountingEntity> list : entitiesCancellingListsToSave) {
			try {
				List<SapAccountingEntity> saveResult = sapAccountingRepository.save(list);
				entitiesCancellingSaved.addAll(saveResult);
				logger.info(entitiesCancellingSaved.size()+" SAP ACCOUNTING ENTITIES INSERTED");
			}catch(Exception e) {
				logger.error("ERROR DURING THE INSERT OF MATHEMATICAL RESERVE IN SAP ACCOUNTING (CANCELLING ACCOUNTINGS)");
				rollbackEntities(entitiesCancellingSaved);
				response.setSuccess(false);
				return response;
			}
		}
		
		logger.info("INSERT COMPLETED");
		
		/**
		 * REVERSAL ACCOUNTINGS
		 */
		
		// same process for reversals
		List<SapAccountingEntity> reversalsToSave = createReversalsForSapAccountingEntities(entitiesSaved);
		//insert 100 by 100
		List<List<SapAccountingEntity>> reversalsEntitiesListsToSave = ListUtils.partition(reversalsToSave, 100);
		List<SapAccountingEntity> reversalsEntitiesSaved = new ArrayList<SapAccountingEntity>();
		
		logger.info("INSERTING MATHEMATICAL RESERVE IN SAP ACCOUNTING (REVERSAL) ... ("+reversalsToSave.size()+" entities)");
		
		for(List<SapAccountingEntity> list : reversalsEntitiesListsToSave) {
			try {
				List<SapAccountingEntity> saveResult = sapAccountingRepository.save(list);
				reversalsEntitiesSaved.addAll(saveResult);
				logger.info(reversalsEntitiesSaved.size()+" SAP ACCOUNTING ENTITIES INSERTED  (REVERSAL)");
			}catch(Exception e) {
				logger.error("ERROR DURING THE INSERT OF MATHEMATICAL RESERVE IN SAP ACCOUNTING (REVERSAL)");
				rollbackEntities(reversalsEntitiesSaved);
				rollbackEntities(entitiesSaved);
				response.setSuccess(false);
				return response;
			}
		}
		
		
		/**
		 * CANCELLING REVERSAL ACCOUNTINGS
		 */
		
		List<SapAccountingEntity> cancellingReversalAccountings = createCancellingReversalAccountings(reversalsEntitiesSaved);
		//insert 100 by 100
		List<List<SapAccountingEntity>> entitiesReversalCancellingListsToSave = ListUtils.partition(cancellingReversalAccountings, 100);
		List<SapAccountingEntity> entitiesReversalCancellingSaved = new ArrayList<SapAccountingEntity>();
		
		for(List<SapAccountingEntity> list : entitiesReversalCancellingListsToSave) {
			try {
				List<SapAccountingEntity> saveResult = sapAccountingRepository.save(list);
				entitiesReversalCancellingSaved.addAll(saveResult);
				logger.info(entitiesReversalCancellingSaved.size()+" SAP ACCOUNTING ENTITIES INSERTED");
			}catch(Exception e) {
				logger.error("ERROR DURING THE INSERT OF MATHEMATICAL RESERVE IN SAP ACCOUNTING ( REVERSAL CANCELLING ACCOUNTINGS)");
				rollbackEntities(entitiesReversalCancellingSaved);
				response.setSuccess(false);
				return response;
			}
		}
		
		
		logger.info("INSERT COMPLETED (REVERSAL)");

		return response;
	}

	/**
	 * get list of mathematical reserve entities to update
	 * @return
	 */
	private List<MathematicalReserveEntity> getListToUpdate() {
		
		List<MathematicalReserveEntity> result = new ArrayList<MathematicalReserveEntity>();
		
		int pageNumber = 0;
		int sizeOfResult = 500;
		PageRequest pageable = new PageRequest(pageNumber, 500);
		while(sizeOfResult >= 500) {
			Page<MathematicalReserveEntity> resultList = mathematicalReserveRepository.findMathematicalReserveNotExported("C", pageable);
			result.addAll(resultList.getContent());
			sizeOfResult = resultList.getContent().size();
			pageNumber++;
			pageable = new PageRequest(pageNumber, 500);
		}
		
		return result;
	}

	/**
	 * rollback when error in the save
	 * @param entitiesSaved
	 */
	private void rollbackEntities(List<SapAccountingEntity> entitiesSaved) {
		List<List<SapAccountingEntity>> entitiesListsToRollback = ListUtils.partition(entitiesSaved, 100);
		logger.info("ROLLBACKING ...");
		for(List<SapAccountingEntity> list : entitiesListsToRollback) {
			sapAccountingRepository.delete(list);
		}		
		logger.info("ROLLBACK COMPLETED");
	}
	
	/**
	 * create reversals for SAP accounting list
	 * @param list
	 * @return
	 */
	private List<SapAccountingEntity> createReversalsForSapAccountingEntities(List<SapAccountingEntity> list){
		List<SapAccountingEntity> reversals = new ArrayList<SapAccountingEntity>();
		for(SapAccountingEntity entity : list) {
			SapAccountingEntity newAccounting = new SapAccountingEntity();
			BeanUtils.copyProperties(entity, newAccounting);
			newAccounting.setIdSapAcc(null);
			if(newAccounting.getDebitCredit() != null && newAccounting.getDebitCredit().equals("D")) {
				newAccounting.setDebitCredit("C");
				newAccounting.setAccount(_8020000500);
			}
			else {
				newAccounting.setDebitCredit("D");	
				newAccounting.setAccount(_3020000+findMapping(currencyMappings, entity.getCurrency()));
			}
			
			if(newAccounting.getAccountDate() != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(newAccounting.getAccountDate());
				cal.add( Calendar.DATE, 1 );
				newAccounting.setAccountDate(cal.getTime());
			}
			
			reversals.add(newAccounting);
		}
		
		return reversals;
	}
	
	/**
	 * create cancelling accounting
	 * @param list
	 * @return
	 */
	private List<SapAccountingEntity> createCancellingAccountings(List<SapAccountingEntity> list){
		List<SapAccountingEntity> reversals = new ArrayList<SapAccountingEntity>();
		for(SapAccountingEntity entity : list) {
			SapAccountingEntity newAccounting = new SapAccountingEntity();
			BeanUtils.copyProperties(entity, newAccounting);
			newAccounting.setIdSapAcc(null);
			if(newAccounting.getDebitCredit() != null && newAccounting.getDebitCredit().equals("D")) {
				newAccounting.setDebitCredit("C");	
				newAccounting.setAccount(_3020000+findMapping(currencyMappings, entity.getCurrency()));
			}
			else {	
				newAccounting.setDebitCredit("D");
				newAccounting.setAccount(_8020000500);
			}
			
			reversals.add(newAccounting);
		}
		
		return reversals;
	}
	
	/**
	 * create cancelling accounting
	 * @param list
	 * @return
	 */
	private List<SapAccountingEntity> createCancellingReversalAccountings(List<SapAccountingEntity> list){
		List<SapAccountingEntity> reversals = new ArrayList<SapAccountingEntity>();
		for(SapAccountingEntity entity : list) {
			SapAccountingEntity newAccounting = new SapAccountingEntity();
			BeanUtils.copyProperties(entity, newAccounting);
			newAccounting.setIdSapAcc(null);
			if(newAccounting.getDebitCredit() != null && newAccounting.getDebitCredit().equals("C")) {
				newAccounting.setDebitCredit("D");	
				newAccounting.setAccount(_3020000+findMapping(currencyMappings, entity.getCurrency()));
			}
			else {	
				newAccounting.setDebitCredit("C");
				newAccounting.setAccount(_8020000500);
			}
			
			reversals.add(newAccounting);
		}
		
		return reversals;
	}
	
	/**
	 * delete by mode and date
	 * @param date
	 * @param mode
	 */
	@Override
	public DeleteMathematicalReserveResponse deleteByModeAndDate(DeleteMathematicalReserveRequest request) {
		mathematicalReserveRepository.deleteByModeAndCalculDate(request.getDate(), request.getMode());
		DeleteMathematicalReserveResponse response = new DeleteMathematicalReserveResponse();
		response.setSuccess(true);
		return response;
	}
	
	
	private String findMapping(List<SapMappingEntity> mappings, String dataIn) {
		String mapping = "";
		for(SapMappingEntity map : mappings) {
			if(dataIn.trim().contains(map.getDataIn().trim()))
				mapping = map.getDataOut();
		}
		return mapping;
	}
	
	

	
}
