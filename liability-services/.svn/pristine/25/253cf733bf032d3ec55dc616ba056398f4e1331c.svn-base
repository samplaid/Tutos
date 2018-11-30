package lu.wealins.liability.services.core.business.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.tempuri.wssupdpst.Exception_Exception;
import org.tempuri.wssupdpst.WSSUPDPST;
import org.tempuri.wssupdpst.WssupdpstExport;
import org.tempuri.wssupdpst.WssupdpstImport;
import org.tempuri.wssupdpst.WssupdpstImport.ImpGrpPst;
import org.tempuri.wssupdpst.WssupdpstImport.ImpGrpPst.Row;
import org.tempuri.wssupdpst.WssupdpstImport.ImpGrpPst.Row.ImpItmPstPostingSets;
import org.tempuri.wssupdpst.WssupdpstImport.ImpValidationUsers;

import lu.wealins.liability.services.core.mapper.PstPostingSetMapper;
import lu.wealins.liability.services.core.persistence.entity.AccountTransactionEntity;
import lu.wealins.liability.services.core.persistence.entity.AgentEntity;
import lu.wealins.liability.services.core.persistence.entity.ControlEntity;
import lu.wealins.liability.services.core.persistence.entity.ExchangeRateEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyCoverageEntity;
import lu.wealins.liability.services.core.persistence.repository.AccountTransactionRepository;
import lu.wealins.liability.services.core.persistence.repository.AgentRepository;
import lu.wealins.liability.services.core.persistence.repository.ControlRepository;
import lu.wealins.liability.services.core.persistence.repository.ExchangeRateRepository;
import lu.wealins.liability.services.core.persistence.repository.FundRepository;
import lu.wealins.liability.services.core.persistence.repository.FundTransactionRepository;
import lu.wealins.liability.services.core.persistence.repository.PolicyAgentShareRepository;
import lu.wealins.liability.services.core.persistence.repository.PolicyCoverageRepository;
import lu.wealins.liability.services.core.persistence.repository.RateRepository;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.liability.services.core.utils.constantes.Constantes;
import lu.wealins.common.dto.liability.services.AccountTransactionDTO;
import lu.wealins.common.dto.liability.services.PstPostingSetsDTO;
import lu.wealins.common.dto.liability.services.enums.InterfaceLissiaType;
import lu.wealins.liability.services.ws.rest.exception.WssupdpstImportException;

/**
 * @author xqv95
 *
 */
@Component
public final class FundTransactionBusinessProcessUtils {
	private static final int ADM_FEE = 5;
	private static final BigDecimal NUMERIC_VALUE_3 = new BigDecimal(3);
	private static final String C12PRA = "C12PRA";
	private static final String AGENTBAL = "AGENTBAL";
	private static final char DBCR_C = 'C';
	private static final char DBCR_D = 'D';
	private static final String EUR = "EUR";
	private static final String ABL_PERIOD_OPEN = "ABL_PERIOD_OPEN";
	private static final List<Integer> ENTRY_COST_EVENT_SUB_TYPE = new ArrayList<Integer>(Arrays.asList(2));
	private static final List<Integer> ADMIN_COST_EVENT_SUB_TYPE = new ArrayList<Integer>(Arrays.asList(12, 13));

	private static final Logger logger = LoggerFactory.getLogger(FundTransactionBusinessProcessUtils.class);
	@Autowired
	private FundTransactionRepository fundTransactionRepository;
	@Autowired
	private AccountTransactionRepository accountTransactionRepository;
	@Autowired
	private PolicyCoverageRepository policyCoverageRepository;
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;
	@Autowired
	private FundRepository fundRepository;
	@Autowired
	private AgentRepository agentRepository;
	@Autowired
	private PolicyAgentShareRepository policyAgentShareRepository;
	@Autowired
	private ControlRepository controlRepository;
	@Autowired
	private RateRepository rateRepository;
	@Autowired
	private StringToTrimString stringToTrimString;
	@Autowired
	private PstPostingSetMapper pstPostingSetMapper;
	@Autowired
	private WSSUPDPST wssupdpst;
	@Value("${liability.ws.credential.login}")
	private String wsLoginCredential;

	@Value("${liability.ws.credential.password}")
	private String wsPasswordCredential;

	/**
	 * Process post mapping for SAP_ACCOUNTING / COMMISSION_TO_PAY / REPORTING_COM
	 * 
	 * @param accountTransactionDTO
	 * @param interfaceLissiaType
	 * @param comDate
	 * @return
	 */
	public AccountTransactionDTO processPostMapping(AccountTransactionDTO accountTransactionDTO, InterfaceLissiaType interfaceLissiaType) {
		try {
			// change rate
			List<ExchangeRateEntity> rateList = exchangeRateRepository.findActiveByDateAndFromCcyToCcy(getLastBusinessDay(accountTransactionDTO.getEffectiveDate()),
					accountTransactionDTO.getCurrency(), EUR);
			ExchangeRateEntity rate = null;
			if (!CollectionUtils.isEmpty(rateList)) {
				for (ExchangeRateEntity t : rateList) {
					// Get rate only if id not contains underscore
					if (!t.getXrsId().contains("_")) {
						rate = t;
					}
				}
			}
			BigDecimal changeRate = null;
			// If currency is EUR set changeRate = 1
			if (accountTransactionDTO.getCurrency().equals("EUR")) {
				changeRate = BigDecimal.ONE;
			} else {
				changeRate = rate != null ? rate.getMidRate() : null;
			}

			// No change rate throw exception
			if (changeRate == null) {
				new Exception("No change rate for account transaction " + accountTransactionDTO.getAtrId() + "date = " + getLastBusinessDay(accountTransactionDTO.getEffectiveDate()) + " currency = "
						+ accountTransactionDTO.getCurrency());
			}

			accountTransactionDTO.setChangeRate(changeRate);
			// Find Fund
			String fund = fundRepository.findFsIdByID(stringToTrimString.asTrimString(accountTransactionDTO.getCentre()));
			accountTransactionDTO.setFund(fund);
			// Find Agent
			AgentEntity agent = agentRepository.findByAgtId(stringToTrimString.asTrimString(accountTransactionDTO.getCentre()));
			accountTransactionDTO.setAgent(agent == null ? null : agent.getAgtId().trim());

			// If SAP_ACCOUNTING Interface control effective date with ABL_PERIOD_OPEN
			if (InterfaceLissiaType.SAP_ACCOUNTING.equals(interfaceLissiaType)) {
				// Change effective date
				ControlEntity control = controlRepository.findOne(ABL_PERIOD_OPEN);
				if (control != null && control.getNumberValue() != null && control.getNumberValue().toString().length() == 6) {
					String controlDate = control.getNumberValue().toString();
					Integer year = Integer.valueOf(controlDate.substring(0, 4));
					Integer month = Integer.valueOf(controlDate.substring(4, 6));

					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.YEAR, year);
					cal.set(Calendar.MONTH, month - 1);
					cal.set(Calendar.DAY_OF_MONTH, 1);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);

					Date modifiedDate = cal.getTime();

					if (accountTransactionDTO.getEffectiveDate().before(modifiedDate)) {
						accountTransactionDTO.setEffectiveDate(cal.getTime());
					}
				}
			}

			// Process rules for commission_to_pay and reporting_com
			accountTransactionDTO = processPostMappingCommissionToPayAndReportCom(accountTransactionDTO, agent, interfaceLissiaType);

		} catch (Exception e) {
			logger.error("Error during mapping accountTransactionDTO id=[" + accountTransactionDTO.getAtrId() + "] ", e);
			throw e;
		}

		return accountTransactionDTO;
	}

	/**
	 * 
	 * Process post rules for COMMISSION_TO_PAY / REPORTING_COM
	 * 
	 * @param accountTransactionDTO
	 * @param agent
	 * @param interfaceLissiaType
	 * @return
	 */
	private AccountTransactionDTO processPostMappingCommissionToPayAndReportCom(AccountTransactionDTO accountTransactionDTO, AgentEntity agent, InterfaceLissiaType interfaceLissiaType) {

		List<Integer> polSharesType = new ArrayList<Integer>();

		List<BigDecimal> comBase = new ArrayList<BigDecimal>();

		// SET ComBase result
		if (accountTransactionDTO.getEventSubType() != null && ADMIN_COST_EVENT_SUB_TYPE.contains(accountTransactionDTO.getEventSubType())) {
			// Override com base for ADMIN FEE
			polSharesType.add(5);
			comBase = fundTransactionRepository.findAmoutCommissionOfAccountingTransactionForAdminCost(accountTransactionDTO.getAtrId());
		} else if (accountTransactionDTO.getEventSubType() != null && ENTRY_COST_EVENT_SUB_TYPE.contains(accountTransactionDTO.getEventSubType())) {
			// Override com base for ENTRY FEE
			polSharesType.add(3);
			comBase = accountTransactionRepository.findAmoutCommissionOfAccountTransactionForEntryCost(accountTransactionDTO.getAtrId(), polSharesType);
			// When account transaction cancel another account transaction in same transaction0, com base is null.
			if (CollectionUtils.isEmpty(comBase)) {
				// Search canceled account transaction in same transaction0.
				List<AccountTransactionEntity> listAccountTransactionReverse = accountTransactionRepository
						.findAccountTransactionReversedByTransaction0AndAmount(accountTransactionDTO.getTransaction0(), accountTransactionDTO.getAmount());
				if (!CollectionUtils.isEmpty(listAccountTransactionReverse)) {
					// If canceled account transaction exists, get com base.
					comBase = accountTransactionRepository.findAmoutCommissionOfAccountTransactionForEntryCost(listAccountTransactionReverse.get(0).getAtrId(), polSharesType);
				}
			}
		} else if (InterfaceLissiaType.COMMISSION_TO_PAY.equals(interfaceLissiaType) && Constantes.eventTypesTransco.containsKey(accountTransactionDTO.getEventSubType())) {
			// Override com base for commission to pay other case
			comBase = accountTransactionRepository.findBaseAmoutCommissionForOtherCase(accountTransactionDTO.getPstId(), accountTransactionDTO.getTransaction0(),
					Constantes.eventTypesTransco.get(accountTransactionDTO.getEventSubType()), accountTransactionDTO.getDbcr());
		}

		if (InterfaceLissiaType.COMMISSION_TO_PAY.equals(interfaceLissiaType)) {
			// Control = 'C12PRA' and NUMERIC_VALUE = 3
			List<PolicyCoverageEntity> policyCoverageEntities = policyCoverageRepository.findByPolicyAndProductValue_ControlAndProductValue_NumericValue(accountTransactionDTO.getPolicy(), C12PRA,
					NUMERIC_VALUE_3);
			if (CollectionUtils.isEmpty(policyCoverageEntities)) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(accountTransactionDTO.getEffectiveDate());
				accountTransactionDTO.setFinancialYear(cal.get(Calendar.YEAR));
				accountTransactionDTO.setPeriod(cal.get(Calendar.MONTH) + 1);
			} else {
				Calendar cal = Calendar.getInstance();
				cal.setTime(accountTransactionDTO.getEffectiveDate());
				accountTransactionDTO.setFinancialYear(cal.get(Calendar.YEAR));
				accountTransactionDTO.setPeriod(cal.get(Calendar.MONTH) + 2);
			}
		}

		if (!CollectionUtils.isEmpty(comBase)) {
			accountTransactionDTO.setComBase(comBase.get(0) == null ? BigDecimal.ZERO : comBase.get(0).abs());
		}

		switch (accountTransactionDTO.getEventSubType()) {
		case 4:
			polSharesType.add(9);
			break;
		case 15:
			polSharesType.add(7);
			break;
		case 19:
			polSharesType.add(8);
			break;
		default:
			break;
		}

		setSubBrokerCategorySb(accountTransactionDTO, polSharesType);
		setComRateWithCategoryBK(accountTransactionDTO, polSharesType);

		return accountTransactionDTO;
	}

	/**
	 * Set com rate for broker category is BK if polSharesType is not empty
	 * 
	 * @param accountTransactionDTO
	 * @param polSharesType
	 */
	private void setComRateWithCategoryBK(AccountTransactionDTO accountTransactionDTO, List<Integer> polSharesType) {
		List<BigDecimal> comRate = new ArrayList<BigDecimal>();
		// Set COM_RATE for agent category is BK
		if (!CollectionUtils.isEmpty(polSharesType)) {
			if (accountTransactionDTO.getCoverage() != null) {
				comRate = policyAgentShareRepository.findPercentageOfPolAgentShareWithPolicyIdAndCoverage(accountTransactionDTO.getPolicy(), accountTransactionDTO.getCoverage(), polSharesType);
			} else {
				comRate = policyAgentShareRepository.findPercentageOfPolAgentShareWithPolicyId(accountTransactionDTO.getPolicy(), polSharesType);
			}
			if (!CollectionUtils.isEmpty(comRate)) {
				accountTransactionDTO.setComRate(comRate.get(0));
			}
		}

		if (polSharesType.contains(ADM_FEE) && (accountTransactionDTO.getComRate() == null || accountTransactionDTO.getComRate().compareTo(BigDecimal.ZERO) != 1)) {
			// For admin fee, when com_rate is null. Get com_rate step by step method.
			List<BigDecimal> comRateStepByStep = rateRepository.findRateComStepByStep(accountTransactionDTO.getPolId(), accountTransactionDTO.getAgent(), accountTransactionDTO.getEffectiveDate());
			if (!CollectionUtils.isEmpty(comRateStepByStep)) {
				accountTransactionDTO.setComRate(comRateStepByStep.get(0));
			}
		}
		/*
		 * PROPOSITION : Nous cherchons les taux de commission avec un status actif. Sur certaines polices il n'y a pas de taux de com actif. Dans ces cas en recuperant les taux non actif et en
		 * verifiant que nous pouvons calculer le montant de commission le taux de com est valide.
		 * 
		 * if (accountTransactionDTO.getComRate() == null || accountTransactionDTO.getComRate().compareTo(BigDecimal.ZERO) != 1) { // Si com_rate est null, je recupère la liste des taux sans le status
		 * actif et je verifie si je peux calculer le montant de commission. List<BigDecimal> lastComRateDesactivate =
		 * policyAgentShareRepository.findPercentageOfPolAgentShareWithPolicyIdDesactivate(accountTransactionDTO.getPolicy(), polSharesType);
		 * 
		 * for (BigDecimal comRateDesactivate : lastComRateDesactivate) { if (accountTransactionDTO.getComBase() != null) { // Verification : (ComBase * ComRate) / 100 = COM_AMOUNT BigDecimal
		 * verificationOne = accountTransactionDTO.getComBase().multiply(comRateDesactivate).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP); if
		 * (verificationOne.equals(accountTransactionDTO.getAmount())) { accountTransactionDTO.setComRate(comRateDesactivate); break; } // Verification : ((ComBase * ComRate) / 100) / 4 = COM_AMOUNT
		 * BigDecimal verificationTwo = accountTransactionDTO.getComBase().multiply(comRateDesactivate).divide(new BigDecimal(100)).divide(new BigDecimal(4), 2, RoundingMode.HALF_UP); if
		 * (verificationTwo.equals(accountTransactionDTO.getAmount())) { accountTransactionDTO.setComRate(comRateDesactivate); break; } // Verification : (((ComBase * ComRate) / 100) / 4) / 3 =
		 * COM_AMOUNT BigDecimal verificationThree = accountTransactionDTO.getComBase().multiply(comRateDesactivate).divide(new BigDecimal(100)).divide(new BigDecimal(4)).divide(new BigDecimal(3), 2,
		 * RoundingMode.HALF_UP); if (verificationThree.equals(accountTransactionDTO.getAmount())) { accountTransactionDTO.setComRate(comRateDesactivate); break; } } }
		 */
	}

	/**
	 * Set Sub Broker for broker category is SB if polSharesType is not empty
	 * 
	 * @param accountTransactionDTO
	 * @param polSharesType
	 */
	private void setSubBrokerCategorySb(AccountTransactionDTO accountTransactionDTO, List<Integer> polSharesType) {
		// Set Sub Agent for agent category is SB
		List<String> subAgent = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(polSharesType)) {
			subAgent = policyAgentShareRepository.findSubBrokerOfPolAgentShareWithPolicyId(accountTransactionDTO.getPolicy(), polSharesType);
			if (!CollectionUtils.isEmpty(subAgent)) {
				accountTransactionDTO.setSubAgentId(subAgent.get(0));
			}
		}
	}

	/**
	 * Internal soap call update posting_sets status
	 * 
	 * @param pstPostingSetsDtos
	 * @param usrId
	 * @return
	 * @throws WssupdpstImportException
	 */
	public WssupdpstExport _internal_soap(Collection<PstPostingSetsDTO> pstPostingSetsDtos, String usrId) throws WssupdpstImportException {
		WssupdpstImport req = new WssupdpstImport();
		Date now = new Date();
		// Log
		logger.info("user {}", usrId);
		// Posting sets
		Collection<ImpItmPstPostingSets> pstPostingSets = pstPostingSetMapper.asImpItmPstPostingSets(pstPostingSetsDtos);
		ImpGrpPst psts = new ImpGrpPst();
		pstPostingSets.forEach(pst -> {
			Row newRow = new Row();
			pst.setSapExportDate(DateFormatUtils.format(now, "yyyyMMdd"));
			newRow.setImpItmPstPostingSets(pst);
			psts.getRows().add(newRow);
		});
		req.setImpGrpPst(psts);

		// Set credential to access the WS
		ImpValidationUsers value = new ImpValidationUsers();
		value.setLoginId(wsLoginCredential);
		value.setPassword(wsPasswordCredential);
		req.setImpValidationUsers(value);

		// Call web service
		try {
			WssupdpstExport resp = wssupdpst.wssupdpstcall(req);

			if (resp.getExpErrorMessageBrowserFields() != null
					&& resp.getExpErrorMessageBrowserFields().getErrorMessage() != null
					&& resp.getExpErrorMessageBrowserFields().getErrorMessage().trim().length() != 0) {

				throw new WssupdpstImportException(
						resp.getExpErrorMessageBrowserFields().getErrorTxt(),
						resp.getExpErrorMessageBrowserFields().getErrorMessage());
			}
			return resp;

		} catch (Exception_Exception e) {
			// Just wrap it to an interface exception
			throw new WssupdpstImportException(e);
		}

	}

	/**
	 * Get last business date before weekend
	 * 
	 * @param date
	 * @return
	 */
	private Date getLastBusinessDay(Date date) {
		Calendar lastBusinessDay = Calendar.getInstance();
		lastBusinessDay.setTime(date);

		// Get last business day if date is weekend
		if (lastBusinessDay.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			lastBusinessDay.add(Calendar.DAY_OF_MONTH, -1);
		}
		if (lastBusinessDay.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			lastBusinessDay.add(Calendar.DAY_OF_MONTH, -2);
		}

		return lastBusinessDay.getTime();
	}

	/**
	 * Sum amount of account transaction by currency and account
	 * 
	 * @param accountTransactionGroupByCurrencyAndAccount
	 * @param amountSumGroupByCurrencyAndAccount
	 */
	public void groupingSumAmountByCurrencyAndAccount(Map<String, Map<String, List<AccountTransactionDTO>>> accountTransactionGroupByCurrencyAndAccount,
			Map<String, BigDecimal> amountSumGroupByCurrencyAndAccount) {
		// Foreach Currency
		for (Entry<String, Map<String, List<AccountTransactionDTO>>> entry : accountTransactionGroupByCurrencyAndAccount.entrySet()) {
			String currency = entry.getKey();
			Map<String, List<AccountTransactionDTO>> listTransactionGroupByAccount = entry.getValue();
			// Foreach Account
			for (Map.Entry<String, List<AccountTransactionDTO>> mapTransaction : listTransactionGroupByAccount.entrySet()) {
				String account = mapTransaction.getKey();
				List<AccountTransactionDTO> accountTransactionEntityList = mapTransaction.getValue();
				// Sum all amount by account
				BigDecimal sumAmount = groupAndCalculateAmountByAccount(accountTransactionEntityList);
				amountSumGroupByCurrencyAndAccount.put(account + "-" + currency, sumAmount);
			}
		}
	}

	/**
	 * Calcule amount for accountTransaction by account
	 * 
	 * @param accountTransactionEntityList
	 * @return
	 */
	private BigDecimal groupAndCalculateAmountByAccount(List<AccountTransactionDTO> accountTransactionEntityList) {
		BigDecimal sumAmount = BigDecimal.ZERO;

		// Foreach account sum amount with specified DBCR
		for (AccountTransactionDTO t : accountTransactionEntityList) {
			if (DBCR_C == t.getDbcr()) {
				sumAmount = sumAmount.add(t.getAmount());
			} else {
				sumAmount = sumAmount.add(t.getAmount().negate());
			}
		}
		return sumAmount;
	}

	/**
	 * Group account transaction by currency and account and set correct amount
	 * 
	 * @param regroupementAccount
	 * @param accountTransactionGroupByCurrencyAndAccount
	 * @param amountSumGroupByCurrencyAndAccount
	 * @param interfaceLissiaType
	 */
	public void groupingTransactionByCurrencyAndAccount(List<AccountTransactionDTO> regroupementAccount,
			Map<String, Map<String, List<AccountTransactionDTO>>> accountTransactionGroupByCurrencyAndAccount,
			Map<String, BigDecimal> amountSumGroupByCurrencyAndAccount, InterfaceLissiaType interfaceLissiaType) {

		// FOR REPORT BIL
		if (InterfaceLissiaType.REPORT_BIL.equals(interfaceLissiaType)) {
			// Foreach currency
			for (Entry<String, Map<String, List<AccountTransactionDTO>>> entry : accountTransactionGroupByCurrencyAndAccount.entrySet()) {
				// String currency = entry.getKey();
				Map<String, List<AccountTransactionDTO>> listTransactionGroupByAccount = entry.getValue();
				// Foreach account write line for all account without AGENTBAL
				for (Map.Entry<String, List<AccountTransactionDTO>> mapTransaction : listTransactionGroupByAccount.entrySet()) {
					String account = mapTransaction.getKey();
					if (!AGENTBAL.trim().equals(account.trim())) {
						List<AccountTransactionDTO> accountTransactionEntityList = mapTransaction.getValue();
						if (!CollectionUtils.isEmpty(accountTransactionEntityList)) {
							Collections.sort(accountTransactionEntityList, (a, b) -> a.getAtrId() < b.getAtrId() ? -1 : a.getAtrId() == b.getAtrId() ? 0 : 1);
							AccountTransactionDTO firstAccount = accountTransactionEntityList.get(0);

							firstAccount.setComBase(amountSumGroupByCurrencyAndAccount.get(account + "-" + firstAccount.getCurrency()) == null ? BigDecimal.ZERO
									: amountSumGroupByCurrencyAndAccount.get(account + "-" + firstAccount.getCurrency()).abs());

							if (amountSumGroupByCurrencyAndAccount.containsKey(AGENTBAL.trim() + "-" + firstAccount.getCurrency())) {
								if (amountSumGroupByCurrencyAndAccount.get(AGENTBAL + "-" + firstAccount.getCurrency()).compareTo(BigDecimal.ZERO) < 0) {
									firstAccount.setDbcr(DBCR_D);
									firstAccount.setAmount(amountSumGroupByCurrencyAndAccount.get(AGENTBAL + "-" + firstAccount.getCurrency()).negate());
								} else {
									firstAccount.setDbcr(DBCR_C);
									firstAccount.setAmount(amountSumGroupByCurrencyAndAccount.get(AGENTBAL + "-" + firstAccount.getCurrency()));
								}
							} else {
								firstAccount.setAmount(BigDecimal.ZERO);
							}
							logger.debug("Account_transaction -> Reporting_com is grouped for account=" + account.trim() + " and currency=" + entry.getKey() + " group id=" + firstAccount.getAtrId());
							regroupementAccount.add(firstAccount);
						}
					}
				}
			}
		}
	}

}
