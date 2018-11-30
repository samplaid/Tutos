package lu.wealins.webia.services.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.webia.services.ReportingComDTO;
import lu.wealins.common.dto.webia.services.TransactionDTO;
import lu.wealins.webia.services.core.mapper.ReportingComMapper;
import lu.wealins.webia.services.core.persistence.entity.CommissionToPayEntity;
import lu.wealins.webia.services.core.persistence.entity.ReportingComEntity;
import lu.wealins.webia.services.core.persistence.entity.SapMappingEntity;
import lu.wealins.webia.services.core.persistence.repository.CommissionToPayRepository;
import lu.wealins.webia.services.core.persistence.repository.ReportingComRepository;
import lu.wealins.webia.services.core.persistence.repository.SapMappingRepository;
import lu.wealins.webia.services.core.service.TransactionExtractionCommissionService;


@Service
public class TransactionExtractionCommissionServiceImpl implements TransactionExtractionCommissionService {

	/**
	 * Default constant DBCR M (STRING)
	 */
	private static final String DBCR_STRING_M = "M";
	/**
	 * Default constant DBCR P (STRING)
	 */
	private static final String DBCR_STRING_P = "P";
	/**
	 * Default constant DBCR M (CHAR)
	 */
	private static final Character DBCR_M = 'M';
	/**
	 * Default constant DBCR P (CHAR)
	 */
	private static final Character DBCR_P = 'P';
	/**
	 * Default constant DBCR C (CHAR)
	 */
	private static final Character DBCR_C = 'C';
	/**
	 * Default constant lissia
	 */
	private static final String LISSIA = "LISSIA";
	/**
	 * Default constant mapping type CDCOM
	 */
	private static final String C_MAPPING_TYPE_CDCOM = "CDCOM";
	/**
	 * Default constant mapping type PRODUCT
	 */
	private static final String C_MAPPING_TYPE_PRODUCT = "PRODUCT";
	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(TransactionExtractionCommissionServiceImpl.class);
	/**
	 * SapMapping repository
	 */
	@Autowired
	private SapMappingRepository sapMappingRepository;
	/**
	 * CommissionToPay repository
	 */
	@Autowired
	private CommissionToPayRepository commissionToPayRepository;
	/**
	 * ReportingCom repository
	 */
	@Autowired
	private ReportingComRepository reportingComRepository;
	/**
	 * ReportingCom mapper
	 */
	@Autowired
	private ReportingComMapper reportingComMapper;

	/* (non-Javadoc)
	 * @see lu.wealins.webia.services.core.service.TransactionExtractionCommissionService#processTransactionsIntoCommissionToPay(java.util.Collection)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Long> processTransactionsIntoCommissionToPay(Collection<TransactionDTO> transactionDTOs) {
		List<Long> pstIds = new ArrayList<Long>();
		
		// Verify transaction exists and delete if not exported
		/*
		transactionDTOs.forEach(t -> {
			try {
				commissionToPayRepository.deleteWithTransaction0WhereNoExported(t.getTransaction0());
			} catch (Exception e) {
				logger.error("Error for delete commission to pay with transaction0 " + t.getTransaction0() + " not exported ", e);
			}
		});*/
		
		// Transform transaction to commission to pay
		Collection<CommissionToPayEntity> comToPayEntities = doMapToCommissionToPay(transactionDTOs);
		
		// Control and save commissions
		for (CommissionToPayEntity com : comToPayEntities) {
			try { 
				// Verify commission already generated				
				long alreadyExist = commissionToPayRepository.countByOriginId(com.getOriginId());
				// If never generate save commissions
				if (alreadyExist == 0L) {
					if (BigDecimal.ZERO.compareTo(com.getComAmount()) == 0) {
						logger.info("Account Transaction=[" + com.getOriginId() + "], Transaction0=[" + com.getTransaction0() + "], commission amount 0.");
						pstIds.add(com.getOriginId());
					} else {
						List<CommissionToPayEntity> isReverse = commissionToPayRepository.findByAgentIdAndComAmountAndTransaction0AndStatementIdIsNullAndTransferIdIsNull(com.getAgentId(), com.getComAmount().negate(), com.getTransaction0());
						if (CollectionUtils.isEmpty(isReverse)) {
							CommissionToPayEntity comSave = commissionToPayRepository.save(com);
							pstIds.add(comSave.getOriginId());
						} else {
							commissionToPayRepository.delete(isReverse);
							pstIds.add(com.getOriginId());
						}
					}
				} else {
					pstIds.add(com.getOriginId());
				}
			} catch (Exception e) {
				logger.error("Error save commission to pay ", e);
			}
		}

		return pstIds;
	}

	/**
	 * Verify and Transform transaction to commission_to_pay
	 * 
	 * @param transactionDTOs
	 * @return
	 */
	private Collection<CommissionToPayEntity> doMapToCommissionToPay(Collection<TransactionDTO> transactionDTOs) {
		// Map<Long, List<TransactionDTO>> groupTransactions = transactionDTOs.stream().collect(Collectors.groupingBy(TransactionDTO::getTransaction0));
		List<TransactionDTO> resultTransactions = new ArrayList<>();
		List<CommissionToPayEntity> comToPayEntities = new ArrayList<>();
		List<SapMappingEntity> sapMappings = sapMappingRepository.findAll();

		// save transactions if all transactions with the same Id are mapped
		transactionDTOs.forEach((V) -> {
			if (isMappingForCommissionToPayExists(sapMappings, V))
				resultTransactions.add(V);
		});

		// get POSTING_SETS IDs and get CommissionToPayEntity list
		resultTransactions.stream().forEach(t -> {
			// convert to SapAccountingEntity
			comToPayEntities.add(asCommissionToPayEntity(t, sapMappings));
		});
		return comToPayEntities;
	}
	
	/**
	 * Verify if mapping exist for transform transaction to commission_to_pay
	 * 
	 * @param sapMappings
	 * @param v
	 * @return
	 */
	private Boolean isMappingForCommissionToPayExists(List<SapMappingEntity> sapMappings, TransactionDTO v) {
		for (SapMappingEntity t : sapMappings) {
			if (t.getType().trim().equals(C_MAPPING_TYPE_CDCOM) && t.getDataIn().trim().equals(String.valueOf(v.getEventSubType()))) {
				return true;
			}
		}
		logger.warn("Mapping is missing for transaction ID=[" + v.getAtrId() + "]");
		logger.warn("Mapping is missing for v.getEventSubType() " + String.valueOf(v.getEventSubType()));

		return false;
	}
	
	/**
	 * Transform transaction to commission_to_pay
	 * 
	 * @param transactionDTO
	 * @param sapMappings
	 * @return
	 */
	private CommissionToPayEntity asCommissionToPayEntity(TransactionDTO transactionDTO, List<SapMappingEntity> sapMappings) {
		CommissionToPayEntity commissionToPayEntity = new CommissionToPayEntity();
		commissionToPayEntity.setAgentId(transactionDTO.getCentre().length() > 8 ? transactionDTO.getCentre().substring(0, 8) : transactionDTO.getCentre());
		
		if (!(transactionDTO.getCentre().length() > 8 ? transactionDTO.getCentre().substring(0, 8) : transactionDTO.getCentre()).equals(transactionDTO.getSubAgentId())) {
			commissionToPayEntity.setSubAgentId(transactionDTO.getSubAgentId()); 
		}

		commissionToPayEntity.setOrigin(LISSIA);
		commissionToPayEntity.setOriginId(transactionDTO.getAtrId());

		for (SapMappingEntity t : sapMappings) {
			if (t.getType().trim().equals(C_MAPPING_TYPE_CDCOM) && t.getDataIn().trim().equals(String.valueOf(transactionDTO.getEventSubType()))) {
				commissionToPayEntity.setComType(t.getDataOut());
			}
		}
		/*
		 * Rules : when Control = 'C12PRA' and NUMERIC_VALUE = 3. Accounting mounth + 1. (In liability-services process)
		 */
		commissionToPayEntity.setAccountingMonth(String.valueOf((transactionDTO.getFinancialYear() * 100) + transactionDTO.getPeriod()));
		
		commissionToPayEntity.setComDate(transactionDTO.getEffectiveDate());
		if (DBCR_C.equals(transactionDTO.getDbcr())) {
			commissionToPayEntity.setComAmount(transactionDTO.getAmount());
		} else {
			BigDecimal res = transactionDTO.getAmount().negate();
			commissionToPayEntity.setComAmount(res);
		}
		commissionToPayEntity.setComCurrency(transactionDTO.getCurrency());

		commissionToPayEntity.setComRate(transactionDTO.getComRate()); 
		commissionToPayEntity.setComBase(transactionDTO.getComBase()); 

		commissionToPayEntity.setPolicyId(transactionDTO.getPolicy());
		commissionToPayEntity.setProductCd(transactionDTO.getProduct());

		commissionToPayEntity.setFundName(transactionDTO.getFund());
		
		commissionToPayEntity.setTransaction0(transactionDTO.getTransaction0());

		commissionToPayEntity.setPstId(transactionDTO.getPstId());
		commissionToPayEntity.setReverse(transactionDTO.getEventType() == 20 ? true : false);
		
		return commissionToPayEntity;
	}

	/* (non-Javadoc)
	 * @see lu.wealins.webia.services.core.service.TransactionExtractionCommissionService#processTransactionsIntoReportingCom(java.util.Collection, java.lang.Long)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Collection<Long> processTransactionsIntoReportingCom(Collection<TransactionDTO> transactionDTOs, Long reportId) {
		Collection<Long> pstIds = new ArrayList<Long>();
		
		// Verify transaction exists and delete if not exported
		transactionDTOs.forEach(t -> {
			try {
				reportingComRepository.deleteByOriginIdNotExported(t.getTransaction0(), reportId);
			} catch (Exception e) {
				logger.error("Error for delete commission to pay with transaction0 " + t.getTransaction0() + " and reportId " + reportId + " not exported ", e);
			}
		});

		Collection<ReportingComEntity> reportEntities = doMapToReportCom(transactionDTOs, reportId);

		// Control and save commissions
		for (ReportingComEntity report : reportEntities) {
			try {
				// Verify report already generated				
				List<ReportingComEntity> reportAlreadyGenerated = reportingComRepository.findByOriginIdAndCurrencyAndExportDtIsNotNull(report.getOriginId(), report.getCurrency());
				// If never generate save report
				if (CollectionUtils.isEmpty(reportAlreadyGenerated)) {
					ReportingComEntity comSave = reportingComRepository.save(report);
					pstIds.add(comSave.getOriginId());
				} else {
					BigDecimal calculSumAmount = BigDecimal.ZERO;

					for (ReportingComEntity rpAlreadyGenerate : reportAlreadyGenerated) {
						calculSumAmount = calculSumAmount.add(rpAlreadyGenerate.getComAmt());
					}
					// if already generate, calcul delta com_amount
					report.setComAmt(report.getComAmt().subtract(calculSumAmount));
					ReportingComEntity comSave = reportingComRepository.save(report);
					pstIds.add(comSave.getOriginId());					
				}	
			} catch (Exception e) {
				logger.error("Error save report commission ", e);
				throw e;
			}
		}

		return pstIds;
	}
	
	/* (non-Javadoc)
	 * @see lu.wealins.webia.services.core.service.TransactionExtractionCommissionService#processTransactionsExternal132IntoReportingCom(java.util.Collection, java.lang.Long)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Long> processTransactionsExternal132IntoReportingCom(Collection<ReportingComDTO> reportingComDTO, Long reportId) {
		List<Long> pstIds = new ArrayList<Long>();
		Collection<ReportingComDTO> resultReportingComDTO = new ArrayList<ReportingComDTO>();
		List<SapMappingEntity> sapMappings = sapMappingRepository.findAll();
		// save transactions if all transactions with the same Id are mapped
		reportingComDTO.forEach((V) -> {
			if (isMappingForExternalFundToReportingComExists(sapMappings, V))
				resultReportingComDTO.add(V);
		});
					
		if (!CollectionUtils.isEmpty(resultReportingComDTO)) {
			reportingComRepository.deleteByOriginIdNotExported(132L, reportId);
			
			Collection<ReportingComEntity> res = reportingComMapper.asReportingComEntities(resultReportingComDTO);
			
			res.forEach(t -> {
				String mappingProduct = null;
				for (SapMappingEntity x : sapMappings) {
					if (x.getType().trim().equals(C_MAPPING_TYPE_PRODUCT) && x.getDataIn().trim().equals(String.valueOf(t.getProductCd().trim()))) {
						mappingProduct = x.getDataOut();
					}
				}
				t.setProductCd(mappingProduct);
				t.setReportId(reportId);
				ReportingComEntity saveEntity = reportingComRepository.save(t);
				pstIds.add(saveEntity.getReportComId());
			});
		}
		
		return pstIds;
	}
	

	/* (non-Javadoc)
	 * @see lu.wealins.webia.services.core.service.TransactionExtractionCommissionService#processTransactionsExternal133IntoReportingCom(java.util.Collection, java.lang.Long)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Long> processTransactionsExternal133IntoReportingCom(Collection<ReportingComDTO> reportingComDTO, Long reportId) {
		List<Long> pstIds = new ArrayList<Long>();
		Collection<ReportingComDTO> resultReportingComDTO = new ArrayList<ReportingComDTO>();
		List<SapMappingEntity> sapMappings = sapMappingRepository.findAll();
		// save transactions if all transactions with the same Id are mapped
		reportingComDTO.forEach((V) -> {
			if (isMappingForExternalFundToReportingComExists(sapMappings, V))
				resultReportingComDTO.add(V);
		});
					
		if (!CollectionUtils.isEmpty(resultReportingComDTO)) {
			reportingComRepository.deleteByOriginIdNotExported(133L, reportId);		
			Collection<ReportingComEntity> res = reportingComMapper.asReportingComEntities(resultReportingComDTO);
			
			res.forEach(t -> {
				String mappingProduct = null;
				for (SapMappingEntity x : sapMappings) {
					if (x.getType().trim().equals(C_MAPPING_TYPE_PRODUCT) && x.getDataIn().trim().equals(String.valueOf(t.getProductCd().trim()))) {
						mappingProduct = x.getDataOut();
					}
				}
				t.setProductCd(mappingProduct);
				t.setReportId(reportId);
				ReportingComEntity saveEntity = reportingComRepository.save(t);
				pstIds.add(saveEntity.getReportComId());
			});
		}
		
		return pstIds;
	}
	
	
	/**
	 * Verify and Transform transaction to report_com
	 * 
	 * @param transactionDTOs
	 * @param reportId
	 * @return
	 */
	private Collection<ReportingComEntity> doMapToReportCom(Collection<TransactionDTO> transactionDTOs, Long reportId) {
		// Map<Long, List<TransactionDTO>> groupTransactions = transactionDTOs.stream().collect(Collectors.groupingBy(TransactionDTO::getTransaction0));
		List<TransactionDTO> resultTransactions = new ArrayList<>();
		List<ReportingComEntity> comToReportEntities = new ArrayList<>();
		List<SapMappingEntity> sapMappings = sapMappingRepository.findAll();

		// save transactions if all transactions with the same Id are mapped
		transactionDTOs.forEach((V) -> {
			if (isMappingForReportingComExists(sapMappings, V))
				resultTransactions.add(V);
		});

		// get POSTING_SETS IDs and get ReportingComEntity list
		resultTransactions.stream().forEach(t -> {
			comToReportEntities.add(asReportingComEntity(t, sapMappings, reportId));
		});
		return comToReportEntities;
	}
	
	/**
	 * Verify mapping for transaction to commission_to_pay
	 * 
	 * @param sapMappings
	 * @param v
	 * @return
	 */
	private Boolean isMappingForReportingComExists(List<SapMappingEntity> sapMappings, TransactionDTO v) {
		Boolean mappingProductExist = false;
		Boolean mappingMvtExist = false;
		String bkMapping = null;

		if (v.getEventType() == 20) {
			bkMapping = String.valueOf(v.getEventType()) + String.valueOf(v.getEventSubType()) + v.getAccount();
		} else {
			bkMapping = String.valueOf(v.getEventSubType())  + v.getAccount();
		}
		for (SapMappingEntity t : sapMappings) {
			if (t.getType().trim().equals(C_MAPPING_TYPE_PRODUCT) && t.getDataIn().trim().equals(String.valueOf(v.getProductCd().trim()))) {
				mappingProductExist = true;
			}
			if (t.getType().trim().equals(AgentCategory.BROKER.getCategory()) && t.getDataIn().trim().equals(String.valueOf(bkMapping.trim()))) {
				mappingMvtExist = true;
			}
		}

		if (mappingProductExist && mappingMvtExist) {
			return true;
		} else {
			logger.warn("Mapping is missing for transaction ID=[" + v.getAtrId() + "]");
			if (!mappingProductExist) {
				logger.warn("Mapping is missing for String.valueOf(v.getProductCd()) " + String.valueOf(v.getProductCd().trim()));
			}
			if (!mappingMvtExist) {
				logger.warn("Mapping is missing for bkMapping " + bkMapping.trim());
			}
			return false;
		}
	}
	
	/**
	 * Verify mapping for external fund
	 * 	
	 * @param sapMappings
	 * @param v
	 * @return
	 */
	private Boolean isMappingForExternalFundToReportingComExists(Collection<SapMappingEntity> sapMappings, ReportingComDTO v) {
		Boolean mappingProductExist = false;
		for (SapMappingEntity t : sapMappings) {
			if (t.getType().trim().equals(C_MAPPING_TYPE_PRODUCT) && t.getDataIn().trim().equals(String.valueOf(v.getProductCd().trim()))) {
				mappingProductExist = true;
			}
		}

		if (mappingProductExist) {
			return true;
		} else {
			logger.warn("Mapping is missing for Broker Policy ID=[" + v.getBrokerPolicy() + "]");
			if (!mappingProductExist) {
				logger.warn("Mapping FID Report is missing for String.valueOf(v.getProductCd()) " + String.valueOf(v.getProductCd()));
			}
			return false;
		}
	}

	/**
	 * Transform transaction to report_com
	 * 
	 * @param transactionDTO
	 * @param sapMappings
	 * @param reportId
	 * @return
	 */
	private ReportingComEntity asReportingComEntity(TransactionDTO transactionDTO, List<SapMappingEntity> sapMappings, Long reportId) {
		ReportingComEntity reportingComEntity = new ReportingComEntity();
		
		String mappingProduct = null;
		String mappingMvt = null;
		
		String bkMapping = null;
		Character signDbcr = null;
		
		
		if (transactionDTO.getEventType() == 20) {
			bkMapping = String.valueOf(transactionDTO.getEventType()) + String.valueOf(transactionDTO.getEventSubType()) + transactionDTO.getAccount();
		} else {
			bkMapping = String.valueOf(transactionDTO.getEventSubType()) + transactionDTO.getAccount();
		}
		for (SapMappingEntity t : sapMappings) {
			if (t.getType().trim().equals(C_MAPPING_TYPE_PRODUCT) && t.getDataIn().trim().equals(String.valueOf(transactionDTO.getProductCd().trim()))) {
				mappingProduct = t.getDataOut();
			}
			if (t.getType().trim().equals(AgentCategory.BROKER.getCategory()) && t.getDataIn().trim().equals(String.valueOf(bkMapping.trim()))) {
				if (t.getDataOut().endsWith(DBCR_STRING_P)) {
					signDbcr = DBCR_P;
					mappingMvt = t.getDataOut().replace(DBCR_STRING_P, "");
				} else {
					signDbcr = DBCR_M;
					mappingMvt = t.getDataOut().replace(DBCR_STRING_M, "");
				}
			}
		}

		StringBuilder period = new StringBuilder();
		period.append(transactionDTO.getFinancialYear());
		String month = String.format("%2s", Optional.ofNullable(String.valueOf(transactionDTO.getPeriod())).orElse("")).substring(0, 2).replace(" ", "0");
		period.append(month);
		reportingComEntity.setPeriod(period.toString());
		reportingComEntity.setPolicy(transactionDTO.getPolicy());
		reportingComEntity.setBrokerPolicy(transactionDTO.getBrokerRefContract());
		reportingComEntity.setProductCd(mappingProduct);
		reportingComEntity.setMvtCd(mappingMvt);
		reportingComEntity.setComDt(transactionDTO.getEffectiveDate()); 
		reportingComEntity.setCurrency(transactionDTO.getCurrency());
		
		if (transactionDTO.getComBase().signum() == -1) {
			reportingComEntity.setBaseAmt(transactionDTO.getComBase().negate());
		} else {
			reportingComEntity.setBaseAmt(transactionDTO.getComBase());
		}
			
		reportingComEntity.setComAmt(transactionDTO.getAmount());
		reportingComEntity.setSignbase(signDbcr); 		
		reportingComEntity.setSigncom(signDbcr); 
		reportingComEntity.setOrigin(LISSIA);
		reportingComEntity.setOriginId(transactionDTO.getAtrId());
		reportingComEntity.setReportId(reportId);

		reportingComEntity.setPstId(transactionDTO.getPstId());

		reportingComEntity.setCreationDt(new Date());

		return reportingComEntity;
	}

}
