package lu.wealins.liability.services.core.business.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.tempuri.wssqtdset.Exception_Exception;
import org.tempuri.wssqtdset.WSSQTDSET;
import org.tempuri.wssqtdset.WssqtdsetExport;
import org.tempuri.wssqtdset.WssqtdsetExport.ExpBatchRestartBatchRestart;
import org.tempuri.wssqtdset.WssqtdsetExport.ExpErrorMessageBrowserFields;
import org.tempuri.wssqtdset.WssqtdsetImport;
import org.tempuri.wssupdpst.WssupdpstExport;

import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.liability.services.AccountTransactionDTO;
import lu.wealins.common.dto.liability.services.FundTransactionDTO;
import lu.wealins.common.dto.liability.services.FundTransactionPolicyDTO;
import lu.wealins.common.dto.liability.services.FundTransactionRequest;
import lu.wealins.common.dto.liability.services.FundTransactionResponse;
import lu.wealins.common.dto.liability.services.FundTransactionsValidationRequest;
import lu.wealins.common.dto.liability.services.FundTransactionsValuationRequest;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.common.dto.liability.services.PstPostingSetsDTO;
import lu.wealins.common.dto.liability.services.ReportingComDTO;
import lu.wealins.common.dto.liability.services.ResponsePostingSetIdsDTO;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.common.dto.liability.services.TransactionGroupedByFundDTO;
import lu.wealins.common.dto.liability.services.TransactionsForMathematicalReserveForPolicyRequest;
import lu.wealins.common.dto.liability.services.enums.FundTransactionCode;
import lu.wealins.common.dto.liability.services.enums.FundTransactionStatus;
import lu.wealins.common.dto.liability.services.enums.InterfaceLissiaType;
import lu.wealins.common.dto.liability.services.enums.TransactionCode;
import lu.wealins.liability.services.core.business.EventService;
import lu.wealins.liability.services.core.business.FundService;
import lu.wealins.liability.services.core.business.FundTransactionService;
import lu.wealins.liability.services.core.business.PolicyService;
import lu.wealins.liability.services.core.business.PolicyValuationService;
import lu.wealins.liability.services.core.mapper.AccountTransactionMapper;
import lu.wealins.liability.services.core.mapper.FundTransactionMapper;
import lu.wealins.liability.services.core.mapper.TransactionGroupedByFundMapper;
import lu.wealins.liability.services.core.mapper.WssqtdsetImportMapper;
import lu.wealins.liability.services.core.persistence.entity.AccountTransactionEntity;
import lu.wealins.liability.services.core.persistence.entity.FundEntity;
import lu.wealins.liability.services.core.persistence.entity.FundTransactionEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.liability.services.core.persistence.entity.TransactionGroupedByFundNoEntity;
import lu.wealins.liability.services.core.persistence.repository.AccountTransactionRepository;
import lu.wealins.liability.services.core.persistence.repository.ExchangeRateRepository;
import lu.wealins.liability.services.core.persistence.repository.FundTransactionRepository;
import lu.wealins.liability.services.core.persistence.specification.FundTransactionSpecification;
import lu.wealins.liability.services.core.utils.FundTransactionOrdersRequest;
import lu.wealins.liability.services.core.utils.constantes.Constantes;


/**
 * @author xqv95
 *
 */
@Service
public class FundTransactionServiceImpl implements FundTransactionService {
	private static final String POLICY_ID_CANT_BE_NULL = "The policy id can't be null";
	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(FundTransactionServiceImpl.class);
	/**
	 * FundTransaction repository
	 */
	@Autowired
	private FundTransactionRepository fundTransactionRepository;
	/**
	 * AccountTransaction repository
	 */
	@Autowired
	private AccountTransactionRepository accountTransactionRepository;
	/**
	 * ExchangeRate repository
	 */

	
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;
	
	@Autowired
	private PolicyService policyService;

	@Autowired
	private FundService fundService;

	/**
	 * AccountTransaction mapper
	 */
	@Autowired
	private AccountTransactionMapper accountTransactionMapper;
	/**
	 * FundTransaction mapper
	 */
	@Autowired
	private FundTransactionMapper fundTransactionMapper;

	@Autowired
	private TransactionGroupedByFundMapper transactionGroupedByFundMapper;
	
	@Autowired
	PolicyValuationService policyValuationService;

	@Autowired
	private FundTransactionBusinessProcessUtils fundTransactionBusinessProcessUtils;

	@Autowired
	private WSSQTDSET wssqtdset;

	@Autowired
	private WssqtdsetImportMapper wssqtdsetImportMapper;


	@Autowired
	private EventService eventService;

	@Autowired
	private JdbcTemplate jdbcTemplate;


	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundTransactionService#countTransactions(java.lang.String)
	 */
	@Override
	public int countTransactions(String fdsId) {
		return fundTransactionRepository.countTransactions(fdsId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundTransactionService#findAllNoExportedTransactionsWithClosedPostingSet()
	 */
	@Override
	public List<AccountTransactionDTO> getAllNoExportedTransactionsWithClosedPostingSet() {
		return accountTransactionMapper.asAccountTransactionDTOs(accountTransactionRepository.findAllDistinctAccountTransaction(Constantes.EVENT_TYPE_LIST));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundTransactionService#findAllNoExportedTransactionsWithClosedPostingSet(int, int)
	 */
	@Override
	@Transactional(readOnly = true)
	public PageResult<AccountTransactionDTO> getAllNoExportedTransactionsWithClosedPostingSet(int page, int size) {
		Pageable pageable = new PageRequest(page, size);

		PageResult<AccountTransactionDTO> result = new PageResult<AccountTransactionDTO>();
		Page<AccountTransactionEntity> pageResult = accountTransactionRepository.findAllDistinctAccountTransaction(Constantes.EVENT_TYPE_LIST, pageable);
		if (size != 0)
			result.setSize(size);
		result.setTotalPages(pageResult.getTotalPages());
		result.setTotalRecordCount((int) pageResult.getTotalElements());
		result.setCurrentPage(pageResult.getNumber());
		result.setContent(accountTransactionMapper.asAccountTransactionDTOs((pageResult.getContent())).stream()
				.map(t -> fundTransactionBusinessProcessUtils.processPostMapping(t, InterfaceLissiaType.SAP_ACCOUNTING)).collect(Collectors.toList()));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundTransactionService#findAllNoExportedTransactionsWithClosedPostingSetForSapAccounting(int, int, long, long)
	 */
	@Override
	@Transactional(readOnly = true)
	public FundTransactionResponse getSubcriptionOrAdditionFundTransactions(FundTransactionRequest fundTransactionRequest) {
		FundTransactionResponse response = new FundTransactionResponse();
		List<FundTransactionEntity> fundTransactions = fundTransactionRepository.findSubcriptionOrAdditionTransactions(fundTransactionRequest.getPolicyId(), fundTransactionRequest.getEventDate());
		response.setFundTransactions(fundTransactionMapper.asFundTransactionDTOs(fundTransactions));
		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<AccountTransactionDTO> getAllNoExportedTransactionsWithClosedPostingSetForSapAccounting(int page, int size, long lastMaxId, long currentPst) {
		Pageable pageable = new PageRequest(page, size);

		PageResult<AccountTransactionDTO> result = new PageResult<AccountTransactionDTO>();
		Page<AccountTransactionEntity> pageResult = accountTransactionRepository.findAllDistinctAccountTransaction(Constantes.EVENT_TYPE_LIST, lastMaxId, currentPst, pageable);
		if (size != 0)
			result.setSize(size);
		result.setTotalPages(pageResult.getTotalPages());
		result.setTotalRecordCount((int) pageResult.getTotalElements());
		result.setCurrentPage(pageResult.getNumber());
		if (pageResult.getTotalElements() > 0)
			result.setLastId(pageResult.getContent().get(pageResult.getContent().size() - 1).getAtrId());
		else
			result.setLastId(lastMaxId);
		result.setContent(accountTransactionMapper.asAccountTransactionDTOs((pageResult.getContent())).stream()
				.map(t -> fundTransactionBusinessProcessUtils.processPostMapping(t, InterfaceLissiaType.SAP_ACCOUNTING)).collect(Collectors.toList()));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundTransactionService#getAllNoExportedSapTransactionsCommissionToPay(int, int, java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public PageResult<AccountTransactionDTO> getAllNoExportedSapTransactionsCommissionToPay(int page, int size, Long lastId, Long currentPst, String codeBil) {
		Pageable pageable = new PageRequest(page, size);
		Page<AccountTransactionEntity> pageResult = null;
		PageResult<AccountTransactionDTO> result = new PageResult<AccountTransactionDTO>();
		pageResult = accountTransactionRepository.findAllAccountTransactionNotExportedCommissionToPay(Constantes.ACCOUNT_TYPE_COMMISSION_LIST, Constantes.COMMISSION_EVENT_SUB_TYPE_LIST, lastId, currentPst, codeBil, pageable);

		if (size != 0)
			result.setSize(size);
		result.setTotalPages(pageResult.getTotalPages());
		result.setTotalRecordCount((int) pageResult.getTotalElements());
		result.setCurrentPage(pageResult.getNumber());
		result.setContent(accountTransactionMapper.asAccountTransactionDTOs((pageResult.getContent())).stream().map(t -> fundTransactionBusinessProcessUtils.processPostMapping(t, InterfaceLissiaType.COMMISSION_TO_PAY)).collect(Collectors.toList()));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundTransactionService#getAllNoExportedSapTransactionsReportingCom(int, int, java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public PageResult<AccountTransactionDTO> getAllNoExportedSapTransactionsReportingCom(int page, int size, Long lastId, String codeBilApplicationParam, Long currentPst) {
		Pageable pageable = new PageRequest(page, size);
		Page<AccountTransactionEntity> pageResult = null;
		PageResult<AccountTransactionDTO> result = new PageResult<AccountTransactionDTO>();

		Pageable pageableTransaction = new PageRequest(page, 1000);
		Page<Long> listTransaction0Available = accountTransactionRepository.findAllAccountTransactionNotExportedReportingComDistinctByTransaction0(Constantes.REPORT_EVENT_TYPE_LIST,
				Constantes.ACCOUNT_TYPE_REPORT_LIST, lastId, codeBilApplicationParam, currentPst, pageableTransaction);
		List<AccountTransactionDTO> regroupementAccount = new ArrayList<AccountTransactionDTO>();
		result.setTotalPages(listTransaction0Available.getTotalPages());
		for (Long transaction0Available : listTransaction0Available.getContent()) {
			pageResult = accountTransactionRepository.findAllAccountTransactionNotExportedSapReportingCom(Constantes.REPORT_EVENT_TYPE_LIST, Constantes.ACCOUNT_TYPE_REPORT_LIST, transaction0Available,
					pageable);
			result.setCurrentPage(pageResult.getNumber());
			Map<String, Map<String, List<AccountTransactionDTO>>> accountTransactionGroupByCurrencyAndAccount = accountTransactionMapper.asAccountTransactionDTOs((pageResult.getContent())).stream()
					.collect(Collectors.groupingBy(AccountTransactionDTO::getCurrency, Collectors.groupingBy(AccountTransactionDTO::getAccount)));

			// Grouping by ACCOUNT
			Map<String, BigDecimal> amountSumGroupByAccount = new HashMap<String, BigDecimal>();
			fundTransactionBusinessProcessUtils.groupingSumAmountByCurrencyAndAccount(accountTransactionGroupByCurrencyAndAccount, amountSumGroupByAccount);
			fundTransactionBusinessProcessUtils.groupingTransactionByCurrencyAndAccount(regroupementAccount, accountTransactionGroupByCurrencyAndAccount, amountSumGroupByAccount,
					InterfaceLissiaType.REPORT_BIL);
		}

		if (size != 0)
			result.setSize(size);
		result.setTotalRecordCount(regroupementAccount.size());
		result.setContent(regroupementAccount.stream().map(t -> fundTransactionBusinessProcessUtils.processPostMapping(t, InterfaceLissiaType.REPORT_BIL)).collect(Collectors.toList()));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundTransactionService#findAllExtrenalFundsReportingCom132(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public Collection<ReportingComDTO> findAllExtrenalFundsReportingCom132(String policy, Date comDate) {
		Collection<ReportingComDTO> response = new ArrayList<ReportingComDTO>();

		List<String> resultatList = accountTransactionRepository.distinctFundByFundSubTypeNotInSpecifiedTypeList(Constantes.FUND_SUB_TYPE_LIST);
		//Collection<PolicyAgentShareEntity> policyAgentShareBil = policyAgentShareRepository.findByAgentAndTypeAndCoverage(codeBilApplicationParam, 5, 1);
		logger.info("Get ExtrenalFunds ReportingCom 132 for policy {} ", policy);
		PolicyEntity policyBil = policyService.getPolicyEntity(policy);
		if (policyBil != null) {
			PolicyValuationDTO policyValuation = policyValuationService.getPolicyValuation(policyBil.getPolId(), comDate);
			for (PolicyValuationHoldingDTO policyHolding : policyValuation.getHoldings()) {
				if (resultatList.contains(policyHolding.getFundSubType())) {
					ReportingComDTO report = new ReportingComDTO();
					Calendar cal = Calendar.getInstance();
					cal.setTime(comDate);
					DecimalFormat mFormat = new DecimalFormat("00");
					report.setPeriod(String.valueOf(cal.get(Calendar.YEAR)) + mFormat.format(Double.valueOf(cal.get(Calendar.MONTH) + 1)));
					report.setPolicy(policyBil.getPolId());
					report.setBrokerPolicy(policyBil.getBrokerRefContract());
					report.setProductCd(policyBil.getProduct() != null ? policyBil.getProduct().getPrdId() : null);
					report.setComDt(comDate);
					report.setCurrency(policyValuation.getPolicyCurrency());
					report.setBaseAmt(policyHolding.getHoldingValueFundCurreny());
					report.setSignbase('P');
					report.setComAmt(new BigDecimal(0));
					report.setSigncom('P');

					FundEntity fund = fundService.getFundEntity(policyHolding.getFundId());
					report.setCodeIsin(fund.getIsinCode());

					report.setOrigin("LISSIA");
					report.setMvtCd("132");
					report.setOriginId(132L);
					report.setCreationDt(new Date());
					response.add(report);
				}
			}
		}

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundTransactionService#findAllExtrenalFundsReportingCom133(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public Collection<ReportingComDTO> findAllExtrenalFundsReportingCom133(String policy, Date comDate) {
		Collection<ReportingComDTO> response = new ArrayList<ReportingComDTO>();
		//Collection<PolicyAgentShareEntity> policyAgentShareBil = policyAgentShareRepository.findByAgentAndTypeAndCoverage(codeBilApplicationParam, 5, 1);
		logger.info("Get ExtrenalFunds ReportingCom 133 for policy {} ", policy);
		PolicyEntity policyBil = policyService.getPolicyEntity(policy);
		if (policyBil != null) {
			PolicyValuationDTO policyValuation = policyValuationService.getPolicyValuation(policyBil.getPolId(), comDate);
			for (PolicyValuationHoldingDTO policyHolding : policyValuation.getHoldings()) {
				if (Constantes.FUND_SUB_TYPE_LIST.contains(policyHolding.getFundSubType())) {
					ReportingComDTO report = new ReportingComDTO();
					Calendar cal = Calendar.getInstance();
					cal.setTime(comDate);
					DecimalFormat mFormat = new DecimalFormat("00");
					report.setPeriod(String.valueOf(cal.get(Calendar.YEAR)) + mFormat.format(Double.valueOf(cal.get(Calendar.MONTH) + 1)));
					report.setPolicy(policyBil.getPolId());
					report.setBrokerPolicy(policyBil.getBrokerRefContract());
					report.setProductCd(policyBil.getProduct() != null ? policyBil.getProduct().getPrdId() : null);
					report.setComDt(comDate);
					report.setCurrency(policyValuation.getPolicyCurrency());
					report.setBaseAmt(policyHolding.getHoldingValueFundCurreny());
					report.setSignbase('P');
					report.setComAmt(new BigDecimal(0));
					report.setSigncom('P');

					// FundEntity fund = fundService.getFundEntity(policyHolding.getFundId());
					//report.setCodeIsin(fund.getIsinCode());

					report.setOrigin("LISSIA");
					report.setMvtCd("133");
					report.setOriginId(133L);
					report.setCreationDt(new Date());
					response.add(report);
				}
			}
		}
		
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundTransactionService#updateTransactionPostingSetStatus(java.util.Collection, java.lang.String)
	 */
	@Override
	public Long updateTransactionPostingSetStatus(Collection<PstPostingSetsDTO> pstPostingSetsDtos, String usrId) {
		WssupdpstExport resp = fundTransactionBusinessProcessUtils._internal_soap(pstPostingSetsDtos, usrId);
		Long recordsUpdated = resp.getExpRecordsUpdatedCommunications().getRecordsUpdated();
		logger.info("WSSUPDPST has successfully updated Sap Status {}", recordsUpdated);

		return recordsUpdated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundTransactionService#findAllPostingSetDinstictByPostingIdForSapAccounting()
	 */
	@Override
	public ResponsePostingSetIdsDTO getAllPostingSetDinstictByPostingIdForSapAccounting() {
		ResponsePostingSetIdsDTO response = new ResponsePostingSetIdsDTO();
		List<Long> result = accountTransactionRepository.findAllAccountTransactionDistinctPostingIdForSapAccounting();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		// Get last business day if date is weekend
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			cal.add(Calendar.DAY_OF_MONTH, -2);
		}

		Date dateExchangeRate = cal.getTime();

		Long countExchange = exchangeRateRepository.countExchangeRateForDate0(dateExchangeRate);

		if (countExchange == 0) {
			response.setErrorMessage("Exchange Rate not found for date=[" + dateExchangeRate + "]");
			return response;
		}

		/*
		 * // Validate rate transaction for (Long t : result) { List<AccountTransactionEntity> accList = accountTransactionRepository.findAllDistinctAccountTransaction(Constantes.EVENT_TYPE_LIST, t);
		 * for (AccountTransactionEntity acc : accList) { if (acc.getCurrency() != null && !EUR.equals(acc.getCurrency().trim())) { List<ExchangeRateEntity> rateExchange =
		 * exchangeRateRepository.findActiveByDateAndFromCcyToCcy(acc.getEffectiveDate(), acc.getCurrency(), EUR); if (CollectionUtils.isEmpty(rateExchange)) {
		 * response.setErrorMessage("Exchange Rate not found for date=[" + acc.getEffectiveDate() + "] currency=[" + acc.getCurrency() + "]"); return response; } } } }
		 */
		response.setPostingSetAvailable(result);
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundTransactionService#findAllPostingSetDinstictByPostingIdForCommissionToPay()
	 */
	@Override
	public List<Long> getAllPostingSetDinstictByPostingIdForCommissionToPay() {
		return accountTransactionRepository.findAllAccountTransactionDistinctPostingIdForCommissionToPay();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundTransactionService#findAllPostingSetDinstictByPostingIdForReportingCom(java.lang.String)
	 */
	@Override
	public List<Long> getAllPostingSetDinstictByPostingIdForReportingCom(String codeBilApplicationParam) {
		// codeBilApplicationParam not used now
		return accountTransactionRepository.findAllAccountTransactionDistinctPostingIdForReportingCom();
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundTransactionService#fundTransactionsValuation(lu.wealins.common.dto.liability.services.FundTransactionsValuationRequest)
	 */
	@Override
	public Response fundTransactionsValuation(FundTransactionsValuationRequest fundTransactionsValuationRequest) {
		runFundTransactionsValuation(fundTransactionsValuationRequest);

		return Response.ok("Fund transaction valuation web-service successfuly executed.").build();
	}

	private void runFundTransactionsValuation(FundTransactionsValuationRequest fundTransactionsValuationRequest) {
		WssqtdsetImport wssqtdsetImport = wssqtdsetImportMapper.asWssqtdsetImport(fundTransactionsValuationRequest);
		try {
			boolean completed = false;
			while (!completed) {
				WssqtdsetExport wssqtdsetExport = wssqtdset.wssqtdsetcall(wssqtdsetImport);

				ExpErrorMessageBrowserFields expErrorMessageBrowserFields = wssqtdsetExport.getExpErrorMessageBrowserFields();
				if (expErrorMessageBrowserFields != null && StringUtils.isNotBlank(expErrorMessageBrowserFields.getErrorMessage())) {
					logger.error("Error in the wssqtdset webservice :" + expErrorMessageBrowserFields.getErrorMessage() + ".");
					throw new IllegalStateException("Error in the wssqtdset webservice :" + expErrorMessageBrowserFields.getErrorMessage() + ".");
				}
				
				completed = isCompleted(wssqtdsetExport);
				if (!completed) {
					wssqtdsetImport = wssqtdsetImportMapper.asWssqtdsetImport(wssqtdsetExport);
				}
			}

		} catch (Exception_Exception e) {
			logger.error("Cannot run wssqtdset webservice", e);
			throw new IllegalStateException("Cannot run wssqtdset webservice", e);
		} catch (Exception e) {
			logger.error("Cannot run wssqtdset webservice", e);
			throw e;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRES_NEW)
	public Response executePOST_FPC() {
		fundTransactionRepository.pr_POST_FPC01();
		logger.debug("POST_FPC01 successfuly executed.");
		fundTransactionRepository.pr_POST_FPC02();
		logger.debug("POST_FPC02 successfuly executed.");
		fundTransactionRepository.pr_POST_FPC03();
		logger.debug("POST_FPC03 successfuly executed.");

		return Response.ok("Stored procedure POST_FPC successfuly executed.").build();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRES_NEW)
	public Response executePRE_FPC() {
		fundTransactionRepository.pr_PRE_FPC01();
		logger.debug("PRE_FPC01 successfuly executed.");
		fundTransactionRepository.pr_PRE_FPC02();
		logger.debug("PRE_FPC02 successfuly executed.");
		fundTransactionRepository.pr_PRE_FPC03();
		logger.debug("PRE_FPC03 successfuly executed.");

		return Response.ok("Stored procedure PRE_FPC successfuly executed.").build();
	}

	private boolean isCompleted(WssqtdsetExport response) {
		ExpBatchRestartBatchRestart expBatchRestartBatchRestart = response.getExpBatchRestartBatchRestart();
		if (expBatchRestartBatchRestart != null) {
			return expBatchRestartBatchRestart.getOutstandingWork() == 0;
		}

		throw new IllegalStateException("WSSQTDSET webservice ");
	}


	@Override
	public SearchResult<TransactionGroupedByFundDTO> getTransactionsForMathematicalReserveFunctionOfPolicyAndDate(TransactionsForMathematicalReserveForPolicyRequest request) {
	
		List<TransactionGroupedByFundNoEntity> list = fundTransactionRepository.findUnitsSumOfTransactionsGroupedByPolicyAndFund(request.getPolicyIds(), request.getDate());
		
		List<TransactionGroupedByFundDTO> listToReturn = transactionGroupedByFundMapper.asTransactionGroupedByFundDTOList(list);
		
		SearchResult<TransactionGroupedByFundDTO> results = new SearchResult<TransactionGroupedByFundDTO>();
		results.setContent(listToReturn);
		results.setCurrentPage(request.getPageNum());
		results.setPageSize(listToReturn.size());
		
		return results;
	}
	
	@SuppressWarnings("boxing")
	@Override
	public Collection<FundTransactionDTO> getFundTransactions(String policyId, String fundId, TransactionCode eventType, FundTransactionStatus status) {
		List<String> fundIds = null;

		if (fundId != null) {
			fundIds = new ArrayList<>();
			fundIds.add(fundId);
		}
		return getFundTransactions(policyId, fundIds, eventType, status);
	}

	@Override
	public Collection<FundTransactionDTO> getFundTransactions(String policyId, Collection<String> fundIds, TransactionCode eventType, FundTransactionStatus status) {

		Specifications<FundTransactionEntity> fundTransactionSpecifications = Specifications.where(FundTransactionSpecification.initial());
		if (policyId != null) {
			fundTransactionSpecifications = fundTransactionSpecifications.and(FundTransactionSpecification.withPolicyId(policyId));
		}
		if (fundIds != null) {
			fundTransactionSpecifications = fundTransactionSpecifications.and(FundTransactionSpecification.withFundId(fundIds.toArray(new String[fundIds.size()])));
		}
		if (eventType != null) {
			fundTransactionSpecifications = fundTransactionSpecifications.and(FundTransactionSpecification.withEventType(eventType.getCode()));
		}
		if (status != null) {
			fundTransactionSpecifications = fundTransactionSpecifications.and(FundTransactionSpecification.withStatus(status.getStatus()));
		}

		return this.fundTransactionMapper.asFundTransactionDTOs(fundTransactionRepository.findAll(fundTransactionSpecifications));
	}


	@Override
	public Collection<String> validateFundTransactions(FundTransactionsValidationRequest request) {
		validateRequest(request);
		
		return fundTransactionRepository
				.findByPolicyIdAndEventTypeIn(request.getPolicyId(), request.getEventTypes())
				.parallelStream()
				.flatMap(transaction -> getErrors(transaction, request).stream())
				.collect(Collectors.toSet());
	}


	private void validateRequest(FundTransactionsValidationRequest request) {
		Assert.notNull(request);
		Assert.notNull(request.getPolicyId());
		Assert.notNull(request.getEffectiveDate());
		Assert.notEmpty(request.getEventTypes());
		Assert.notEmpty(request.getUnValorizedStatus());
		Assert.notEmpty(request.getValorizedStatus());
	}

	private Set<String> getErrors(FundTransactionEntity fundTransaction, FundTransactionsValidationRequest request) {
		Set<String> errors = new HashSet<>();

		if (isOlder(fundTransaction, request.getEffectiveDate()) && hasStatus(fundTransaction, request.getUnValorizedStatus())) {
			String errorMessage = getOlderErrorMessage(fundTransaction, request.getEffectiveDate());
			errors.add(errorMessage);
		} else if (isMoreRecent(fundTransaction, request.getEffectiveDate()) && hasStatus(fundTransaction, request.getValorizedStatus())) {
			String errorMessage = getRecentErrorMessage(fundTransaction, request.getEffectiveDate());
			errors.add(errorMessage);
		}

		return errors;
	}

	private String getRecentErrorMessage(FundTransactionEntity fundTransaction, Date effectiveDate) {
		return new StringBuilder()
				.append(getErrorMessage(fundTransaction))
				.append(" which is more recent than the effective date : ")
				.append(DATE_FORMAT.format(effectiveDate))
				.toString();
				
	}

	private String getOlderErrorMessage(FundTransactionEntity fundTransaction, Date effectiveDate) {
		return new StringBuilder()
				.append(getErrorMessage(fundTransaction))
				.append(" which is older than the effective date : ")
				.append(DATE_FORMAT.format(effectiveDate))
				.toString();
	}

	private String getErrorMessage(FundTransactionEntity fundTransaction) {
		String eventName = eventService.getEventByType(fundTransaction.getEventType()).getName();

		StringBuilder builder = new StringBuilder();

		builder.append("A transaction of type '")
				.append(eventName).append("'");

		if (fundTransaction.getTransaction() != null) {
			builder.append(" (transaction id = " + fundTransaction.getTransaction().getTrnId() + ")");
		}

		builder.append(" with a status '")
				.append(FundTransactionStatus.toFundTransactionStatus(fundTransaction.getStatus()).getDescription())
				.append("' has the effective date : ")
				.append(DATE_FORMAT.format(fundTransaction.getDate0()));

		return builder.toString();
	}

	private boolean isMoreRecent(FundTransactionEntity fundTransaction, Date effectiveDate) {
		return fundTransaction.getDate0().after(effectiveDate);
	}

	private boolean isOlder(FundTransactionEntity fundTransaction, Date effectiveDate) {
		return fundTransaction.getDate0().before(effectiveDate);
	}

	private boolean hasStatus(FundTransactionEntity fundTransaction, List<Integer> status) {
		return status.contains(fundTransaction.getStatus());
	}

	@Override
	public Collection<String> validatePosted(String policyId, Integer coverage) {

		Assert.notNull(policyId, POLICY_ID_CANT_BE_NULL);
		Assert.notNull(coverage, "The coverage can't be null");

		List<String> errors = new ArrayList<>();

		boolean hasNotPosted = fundTransactionRepository
				.findByPolicyIdAndCoverageAndEventType(policyId, coverage, TransactionCode.PRE_ALLOC.getCode())
				.stream()
				.anyMatch(this::isNotPosted);

		if (hasNotPosted) {
			errors.add("All the fund transactions should be posted");
		}

		return errors;
	}

	@Override
	public Collection<String> validatePostedForWithdrawal(String policyId, Date effectiveDate) {
		return validateForTransactionType(policyId, effectiveDate, FundTransactionCode.WITHDRAWAL);
	}

	private Collection<String> validateForTransactionType(String policyId, Date effectiveDate, FundTransactionCode fundTransactionCode) {
		Assert.notNull(policyId, POLICY_ID_CANT_BE_NULL);
		Assert.notNull(effectiveDate, "The effective date can't be null");

		List<String> errors = new ArrayList<>();
		Collection<Integer> fundTransactionStatusToFilter = Arrays.asList(
				FundTransactionStatus.POSTED.getStatus(),
				FundTransactionStatus.CANCELLED.getStatus(),
				FundTransactionStatus.REVERSED.getStatus(),
				FundTransactionStatus.CANCELLED_REV.getStatus(),
				FundTransactionStatus.DELETED_FP.getStatus());

		boolean hasNotPosted = fundTransactionRepository
				.findByPolicyIdAndTransactionEffectiveDateAndEventTypeAndNotStatus(policyId, effectiveDate, fundTransactionCode.getCode(), fundTransactionStatusToFilter)
				.stream().findAny().isPresent();

		if (hasNotPosted) {
			errors.add("All the fund transactions should be posted");
		}

		return errors;
	}

	private boolean isNotPosted(FundTransactionEntity transaction) {
		return !FundTransactionStatus.POSTED.getStatus().equals(transaction.getStatus());
	}

	@Override
	public BigDecimal getFundTransactionOrder(String policyId, String TransactionTaxEventType, String effectiveDate) {
		Date EffectiveDateParsed = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			EffectiveDateParsed = format.parse(effectiveDate);
		} catch (Exception parseException) {
			logger.error("Error parsing of effective Date external  {} ", parseException);
		}

		List<FundTransactionPolicyDTO> transactionFunds = jdbcTemplate.query(
				FundTransactionOrdersRequest.getPolicyFundTransactionOrdersRequest(),
				new Object[] { policyId, TransactionTaxEventType, EffectiveDateParsed },
				new RowMapper<FundTransactionPolicyDTO>() {

					@Override
					public FundTransactionPolicyDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

						FundTransactionPolicyDTO transactionFund = new FundTransactionPolicyDTO();
						transactionFund.setPolicy(rs.getString(1));
						transactionFund.setTransactionTaxType(rs.getString(2));
						transactionFund.setEffectiveDate(rs.getDate(3));
						transactionFund.setTransactionId(rs.getInt(4));
						transactionFund.setFundTransactionAmount(rs.getBigDecimal(5));
						return transactionFund;
					}

				});

		if (transactionFunds == null || transactionFunds.isEmpty()) {
			return null;
		}

		return transactionFunds.stream().filter(transaction -> transaction.getFundTransactionAmount() != null)
				.map(FundTransactionPolicyDTO::getFundTransactionAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public Collection<String> validatePostedForSurrender(String policyId, Date effectiveDate) {
		return validateForTransactionType(policyId, effectiveDate, FundTransactionCode.SURRENDER);
	}

	@Override
	public Collection<String> validateAwaitingFundTransactions(String policyId, Date effectiveDate) {
		Assert.notNull(policyId, POLICY_ID_CANT_BE_NULL);
		Assert.notNull(effectiveDate, "The effective date can't be null");

		List<String> errors = new ArrayList<>();
		Collection<Integer> fundTransactionStatusToFilter = Arrays.asList(
				FundTransactionStatus.AWAITING_PRICE_PSTG.getStatus(),
				FundTransactionStatus.AWAITING_FWD_REP.getStatus(),
				FundTransactionStatus.AWAITING_LINKAGE.getStatus());

		List<FundTransactionEntity> awaitingFundTransactions = fundTransactionRepository
				.findByPolicyIdAndDate0BeforeAndStatusIn(policyId, effectiveDate, fundTransactionStatusToFilter);

		boolean hasAwaitingFundTransaction = awaitingFundTransactions.stream().findAny().isPresent();

		if (hasAwaitingFundTransaction) {
			errors.add("There is an awaiting transaction");
			String joinedIds = awaitingFundTransactions.stream().map(ft -> ft.getFtrId() + "").collect(Collectors.joining(", "));
			logger.info("The following fund transactions are awaiting for the policy {} at the date {} : {}", policyId, effectiveDate, joinedIds);
		}

		return errors;
	}

	@Override
	@Transactional
	public void postSurrenderAwaitingFundTransactions(String policyId) {
		Assert.notNull(policyId, POLICY_ID_CANT_BE_NULL);

		fundTransactionRepository.getAwaitingForSurrender(policyId).forEach(fundTransaction -> {
			fundTransaction.setStatus(FundTransactionStatus.POSTED.getStatus());
		});
	}
}

