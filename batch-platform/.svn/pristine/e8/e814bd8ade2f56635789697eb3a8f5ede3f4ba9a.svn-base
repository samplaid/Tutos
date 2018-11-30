/**
 * 
 */
package lu.wealins.batch.extract.mathematicalReserve;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lu.wealins.common.dto.liability.services.ExchangeRateDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundPriceDTO;
import lu.wealins.common.dto.liability.services.FundPriceSearchRequest;
import lu.wealins.common.dto.liability.services.FundTransactionDTO;
import lu.wealins.common.dto.liability.services.PoliciesForMathematicalReserveRequest;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.TransactionGroupedByFundDTO;
import lu.wealins.common.dto.liability.services.TransactionsForMathematicalReserveForPolicyRequest;
import lu.wealins.common.dto.webia.services.AccountingNavDTO;
import lu.wealins.common.dto.webia.services.GetAccountingNavRequest;
import lu.wealins.common.dto.webia.services.GetAccountingNavResponse;
import lu.wealins.common.dto.webia.services.MathematicalReserveDTO;
import lu.wealins.rest.model.SearchResult;
import lu.wealins.common.dto.webia.services.DeleteMathematicalReserveRequest;
import lu.wealins.common.dto.webia.services.DeleteMathematicalReserveResponse;
import lu.wealins.common.dto.webia.services.GetMathematicalReserveRequest;
import lu.wealins.common.dto.webia.services.GetMathematicalReserveResponse;
import lu.wealins.common.dto.webia.services.SaveMathematicalReserveRequest;
import lu.wealins.common.dto.webia.services.SaveMathematicalReserveResponse;
import lu.wealins.utils.RestCallUtils;

/**
 * @author xqt5q
 *
 */
public class ExtractLISSIAPoliciesForMathematicalReserveTaskLet extends AbstractExtractLISSIAPoliciesForMathematicalReserveTaskLet {

	private static String EXTRACT_LISSIA_POLICIES = "liability/policy/policiesForMathematicalReserve";
	private static String EXTRACT_LISSIA_TRANSACTIONS_FOR_POLICY = "liability/transaction/transactionsForMathematicalReserve";
	private static String EXTRACT_LISSIA_FUNDPRICES = "liability/fundPrice/search";
	private static String EXTRACT_LISSIA_EXCHANGE_RATE = "liability/exchangeRate";
	private static String GET_LISSIA_FUND = "liability/fund/one";
	private static String SAVE_REPORTING_MATHEMATICAL_RESERVE = "reporting/mathematicalReserve/save";
	private static String GET_REPORTING_MATHEMATICAL_RESERVE = "reporting/mathematicalReserve/getByModeAndDate";
	private static String DELETE_REPORTING_MATHEMATICAL_RESERVE = "reporting/mathematicalReserve/deleteMathematicalReserve";
	private static String GET_WEBIA_ACCOUNTING_NAV_BY_FUND = "webia/accountingNav/getAccountingNavByFundAndDate";
	private static String GET_WEBIA_ACCOUNTING_NAV_BY_ISIN = "webia/accountingNav/getAccountingNavByIsinAndDate";

	private static String GET_WEBIA_ACCOUNTING_NAV = "webia/accountingNav/getAccountingNavByFundAndDate";
	private static String EURO_CURRENCY = "EUR";
	private static String FID_TYPE = "FID";
	private static String FAS_TYPE = "FAS";
	private static String FE_TYPE = "FE";
	private static String FIC_TYPE = "FIC";
	private static String CLOTURE_MODE = "C";

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	Log logger = LogFactory.getLog(ExtractLISSIAPoliciesForMathematicalReserveTaskLet.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org. springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		String mode = this.getMode();
		// Calendar calendar = new GregorianCalendar(2017,5,30);
		Date date = this.getDate();
		logger.info("Calculation date of batch execution :" + date);
		// to avoid multiple calls, we keep the fund history
		HashMap<String, FundDTO> fundHistory = new HashMap<String, FundDTO>();

		// to avoid multiple calls, we keep the fundPrice history
		HashMap<String, FundPriceDTO> fundPriceHistory = new HashMap<String, FundPriceDTO>();

		// to avoid multiple calls, we keep the fundPrice history
		HashMap<String, AccountingNavDTO> accountingNavHistory = new HashMap<String, AccountingNavDTO>();

		// to avoid multiple calls, we keep the exchangeRate history
		HashMap<String, ExchangeRateDTO> exchangeRateHistory = new HashMap<String, ExchangeRateDTO>();

		if (checkNoExistingMathematicalReserves(mode, date)) {

			List<PolicyDTO> policies = getPoliciesForMathematicalReserve(date, mode);
			HashMap<String, PolicyDTO> policiesMap = new HashMap<String, PolicyDTO>();
			String polIdTrim = null;
			for (PolicyDTO p : policies) {
				polIdTrim = StringUtils.trim(p.getPolId());
				policiesMap.put(polIdTrim, p);
			}

			List<MathematicalReserveDTO> mathematicalReserveList = new ArrayList<MathematicalReserveDTO>();

			// TODO get all the transaction ==> new service get transaction by pol id list

			List<String> policyIds = policies.stream().map(p -> p.getPolId().trim()).collect(Collectors.toList());
			List<List<String>> policyIdsPartition = ListUtils.partition(policyIds, 100);

			List<TransactionGroupedByFundDTO> fundTransactions = new ArrayList<TransactionGroupedByFundDTO>();
			for (List<String> partition : policyIdsPartition) {
				// logger.info("TREATING TRANSACTIONS ...");
				List<TransactionGroupedByFundDTO> transactions = getTransactionsForPolicy(date, partition);
				fundTransactions.addAll(transactions);
			}

			logger.info("REMOVING BAD UNITS SUMS ...");
			removeBadUnitsSums(fundTransactions);
			logger.info("REMOVED BAD UNITS SUMS ...");

			boolean reservesOk = true;

			for (TransactionGroupedByFundDTO tra : fundTransactions) {
				if (tra.getPolicyId() != null && !tra.getPolicyId().equals("")) {
					MathematicalReserveDTO m = null;
					try {
						// logger.info("CONSTRUCTION MATHEMATICAL RESERVE ...");
						m = constructMathematicalReserve(date, mode, policiesMap.get(tra.getPolicyId().trim()), tra, fundHistory, accountingNavHistory, fundPriceHistory, exchangeRateHistory);
					} catch (Exception e) {
						logger.error("ERROR IN CONSTRUCTION OF MATHEMATICAL RESERVE");
						throw new Exception(e.getMessage());
					}

					if (m != null)
						mathematicalReserveList.add(m);
				}

			}

			// for(PolicyDTO policy : policies) {
			// List<FundTransactionDTO> filterByPolicyId = fundTransactions.stream().filter(t -> t.getPolId().equals(policy.getPolId())).collect(Collectors.toList());
			// get the sum of units by funds for transactions
			// HashMap<String, BigDecimal> transactionsByFund = getSumOfUnitsByFund(filterByPolicyId);
			// delete where units sum is <= 0
			// removeBadUnitsSums(transactionsByFund);
			// mathematicalReserveList.addAll(constructMathematicalReserve(date, mode, policy, transactionsByFund, fundHistory, fundPriceHistory, exchangeRateHistory));
			// }

			/*
			 * for(PolicyDTO policy : policies) { List<FundTransactionDTO> fundTransactions = getTransactionsForPolicy(date, policy.getPolId());
			 * 
			 * // get the sum of units by funds for transactions HashMap<String, BigDecimal> transactionsByFund = getSumOfUnitsByFund(fundTransactions); //delete where units sum is <= 0
			 * removeBadUnitsSums(transactionsByFund);
			 * 
			 * mathematicalReserveList.addAll(constructMathematicalReserve(date, mode, policy, transactionsByFund, fundHistory, fundPriceHistory, exchangeRateHistory));
			 * 
			 * }
			 */

			// if no problems, add all mathematicalReserve in WEBIA
			List<List<MathematicalReserveDTO>> toSave = ListUtils.partition(mathematicalReserveList, 100);

			for (List<MathematicalReserveDTO> listToSave : toSave) {
				SaveMathematicalReserveResponse saveResponse = saveMathematicalReserves(listToSave);
			}

		}

		return RepeatStatus.FINISHED;
	}

	/**
	 * check if there is no existing mathematical reserves for this mode and date
	 * 
	 * @param mode
	 * @param date
	 */
	private boolean checkNoExistingMathematicalReserves(String mode, Date date) {
		GetMathematicalReserveResponse response = getMathematicalReserves(mode, date, 1, 1);
		if (mode.equals("T")) {
			DeleteMathematicalReserveResponse deleteOk = deleteMathematicalReserves(mode, date);
			return deleteOk.isSuccess();
		} else {
			return response.getMathematicalReserveList().getContent().isEmpty();
		}
	}

	private MathematicalReserveDTO constructMathematicalReserve(Date calculationDate, String mode, PolicyDTO policy, TransactionGroupedByFundDTO transactionGroupedByFund,
			HashMap<String, FundDTO> fundHistory, HashMap<String, AccountingNavDTO> accountingNavHistory, HashMap<String, FundPriceDTO> fundPriceHistory,
			HashMap<String, ExchangeRateDTO> exchangeRateHistory) throws Exception {
		MathematicalReserveDTO mathematicalReserve = new MathematicalReserveDTO();

		String fund = transactionGroupedByFund.getFundId();
		BigDecimal units = transactionGroupedByFund.getUnits();

		if (!fund.equals("") && policy != null && policy.getPolId() != null && !policy.getPolId().equals("")) {

			logger.debug("Treating FUND " + transactionGroupedByFund.getFundId());

			mathematicalReserve.setCalculDate(calculationDate);
			mathematicalReserve.setMode(mode);
			mathematicalReserve.setPolicyId(policy.getPolId());

			if (policy.getProduct() != null) {
				mathematicalReserve.setProductId(policy.getProduct().getPrdId());
				mathematicalReserve.setAccountingProduct(policy.getProduct().getNlProduct());
				mathematicalReserve.setProductCountry(policy.getProduct().getNlCountry());
			}

			// GET FUND
			FundDTO fundEntity = null;
			if (fundHistory.get(fund) != null)
				fundEntity = fundHistory.get(fund);
			else
				fundEntity = getFund(fund);

			if (fundEntity != null) {
				mathematicalReserve.setFundId(fundEntity.getFdsId());
				mathematicalReserve.setIsin(fundEntity.getIsinCode());
				mathematicalReserve.setFundSubType(fundEntity.getFundSubType());
				mathematicalReserve.setCurrency(fundEntity.getCurrency());
				fundHistory.put(fund, fundEntity);
			} else {
				logger.error("FUND " + fund + " does not exist");
				throw new Exception("No FUND found with the ID : " + fund + " - POLICY " + policy.getPolId());
			}

			// GET FUNDPRICE
			FundPriceDTO fundPrice = null;
			AccountingNavDTO accountingNav = null;
			if (fundPriceHistory.containsKey(fund)) {
				fundPrice = fundPriceHistory.get(fund);
				mathematicalReserve.setNav(fundPrice.getPrice());
				mathematicalReserve.setNavDate(fundPrice.getDate0());
			} else if (accountingNavHistory.containsKey(fund)) {
				accountingNav = accountingNavHistory.get(fund);
				mathematicalReserve.setNav(accountingNav.getNav());
				mathematicalReserve.setNavDate(accountingNav.getNavDate());
			} else {
				if ((fundEntity.getFundSubType().contains(FID_TYPE) || fundEntity.getFundSubType().contains(FAS_TYPE)) && mode.equals(CLOTURE_MODE)) {
					accountingNav = getLastFundPriceBeforeDateForFIDOrFAS(calculationDate, fund, fundEntity.getCurrency());
					if (accountingNav != null) {
						mathematicalReserve.setNav(accountingNav.getNav());
						mathematicalReserve.setNavDate(accountingNav.getNavDate());
						accountingNavHistory.put(fund, accountingNav);
					}
				} else if ((fundEntity.getFundSubType().contains(FE_TYPE) || fundEntity.getFundSubType().contains(FIC_TYPE)) && mode.equals(CLOTURE_MODE)) {
					if (fundEntity.getIsinCode() == null)
						throw new Exception("ISIN NULL FOR FUND " + fundEntity.getFdsId());
					accountingNav = getLastFundPriceBeforeDateForFEOrFIC(calculationDate, fundEntity.getIsinCode(), fundEntity.getCurrency());
					if (accountingNav != null) {
						mathematicalReserve.setNav(accountingNav.getNav());
						mathematicalReserve.setNavDate(accountingNav.getNavDate());
						accountingNavHistory.put(fund, accountingNav);
					}
				} else {
					fundPrice = getLastFundPriceBeforeDate(calculationDate, fund);
					if (fundPrice != null) {
						mathematicalReserve.setNav(fundPrice.getPrice());
						mathematicalReserve.setNavDate(fundPrice.getDate0());
						fundPriceHistory.put(fund, fundPrice);
					}
				}

			}

			mathematicalReserve.setUnits(units);

			// GET EXCHANGE RATE

			ExchangeRateDTO exchangeRate = null;
			if (fundEntity != null) {
				if (fundEntity.getCurrency() != null && exchangeRateHistory.containsKey(fundEntity.getCurrency())) {
					exchangeRate = exchangeRateHistory.get(fundEntity.getCurrency());
					if (exchangeRate != null && exchangeRate.getMidRate() != null) {
						exchangeRateHistory.put(fundEntity.getCurrency(), exchangeRate);
						mathematicalReserve.setChangeRate(exchangeRate.getMidRate());
					} else {
						logger.error("No exchange rate found for FUND " + fund + " - POLICY " + policy.getPolId());
						throw new Exception("No exchange rate found for FUND " + fund + " - POLICY " + policy.getPolId());
					}

				} else {
					if (!fundEntity.getCurrency().equals(EURO_CURRENCY)) {
						exchangeRate = getLastExchangeRate(calculationDate, fundEntity.getCurrency(), EURO_CURRENCY);
						if (exchangeRate != null) {
							if ((mode.equals("C") && datesAreEqualsForMathematicalReserve(calculationDate, exchangeRate.getDate0())) || mode.equals("T")) {

								if (exchangeRate.getMidRate() != null) {
									exchangeRateHistory.put(fundEntity.getCurrency(), exchangeRate);
									mathematicalReserve.setChangeRate(exchangeRate.getMidRate());
								} else {
									logger.error("No exchange rate found for FUND " + fund + " - POLICY " + policy.getPolId());
									throw new Exception("No exchange rate found for FUND " + fund + " - POLICY " + policy.getPolId());
								}

							} else {
								logger.error("Exchange rate date must be the same as the parameter date in C mode : FUND " + fund + " - POLICY " + policy.getPolId());
								logger.error("date exchange date : " + exchangeRate.getDate0() + "  -  calculation date : " + calculationDate);
							}
						} else {
							logger.error("No exchange rate found for FUND " + fund + " - POLICY " + policy.getPolId());
							throw new Exception("No exchange rate found for FUND " + fund + " - POLICY " + policy.getPolId());
						}
					} else {
						mathematicalReserve.setChangeRate(new BigDecimal(1));
					}
				}

			}

			mathematicalReserve.setCreationDate(new Date());
			mathematicalReserve.setExtractionDate(null);
			if (mathematicalReserve.getNav() != null && mathematicalReserve.getUnits() != null) {
				if (mathematicalReserve.getNav().compareTo(BigDecimal.ZERO) == 0 || mathematicalReserve.getUnits().compareTo(BigDecimal.ZERO) == 0)
					mathematicalReserve.setAmount(new BigDecimal(0));
				else
					mathematicalReserve.setAmount(mathematicalReserve.getNav().multiply(mathematicalReserve.getUnits()).round(new MathContext(6, RoundingMode.HALF_DOWN)));
			} else {
				// TODO TO DELETE FOR PRODUCTION VERSION
				/* ########################################## */
				mathematicalReserve.setNav(new BigDecimal(-1));
				mathematicalReserve.setAmount(new BigDecimal(-1));
				/* ########################################## */

				logger.error("Nav or units is null for policy : " + policy.getPolId() + " and fund : " + fund);
			}

		} else {
			logger.error("NULL fundId founded in database, please check");
			mathematicalReserve = null;
		}

		if (mathematicalReserve.getChangeRate() == null) {
			logger.error("No exchange rate found for FUND " + fund + " - POLICY " + policy.getPolId());
			throw new Exception("No exchange rate found for FUND " + fund + " - POLICY " + policy.getPolId());
			// mathematicalReserve.setChangeRate(new BigDecimal(1));

		}
		return mathematicalReserve;

	}

	/**
	 * 
	 * @param transactionsByFund
	 */
	private void removeBadUnitsSums(List<TransactionGroupedByFundDTO> transactionsGroupedByFund) {
		Iterator<TransactionGroupedByFundDTO> it = transactionsGroupedByFund.iterator();
		while (it.hasNext()) {
			TransactionGroupedByFundDTO tr = (TransactionGroupedByFundDTO) it.next();
			if (tr.getUnits().floatValue() <= 0f)
				it.remove();
		}
	}

	/*
	 * /** remove funds where sum of units is <= 0
	 * 
	 * @param transactionsByFund
	 * 
	 * private void removeBadUnitsSums(HashMap<String, BigDecimal> transactionsByFund) { Iterator<Entry<String, BigDecimal>> it = transactionsByFund.entrySet().iterator(); while(it.hasNext()) {
	 * Entry<String, BigDecimal> entry = (Entry<String, BigDecimal>) it.next(); if(entry.getValue().intValue() <= 0) it.remove(); }
	 * 
	 * }
	 */

	/**
	 * calculation
	 * 
	 * @param fundTransactions
	 */
	private HashMap<String, BigDecimal> getSumOfUnitsByFund(List<FundTransactionDTO> fundTransactions) {
		HashMap<String, BigDecimal> transactionsByFund = new HashMap<String, BigDecimal>();

		for (FundTransactionDTO transaction : fundTransactions) {
			if (!transaction.getFund().equals("")) {
				if (!transactionsByFund.containsKey(transaction.getFund())) {
					transactionsByFund.put(transaction.getFund(), new BigDecimal(0));
				} else {
					if (transaction.getUnits() != null)
						transactionsByFund.put(transaction.getFund(), transactionsByFund.get(transaction.getFund()).add(transaction.getUnits()));
				}
			} else {
				logger.error("INCORRECT FUND FOUNDED");
			}
		}

		return transactionsByFund;
	}

	/**
	 * get all the concerned policies for mathematical reserve calculation
	 * 
	 * @param date
	 * @param mode
	 */
	public List<PolicyDTO> getPoliciesForMathematicalReserve(Date date, String mode) {
		List<PolicyDTO> allPolicies = new ArrayList<PolicyDTO>();
		ParameterizedTypeReference<SearchResult<PolicyDTO>> typeRef = new ParameterizedTypeReference<SearchResult<PolicyDTO>>() {
		};

		PoliciesForMathematicalReserveRequest request = new PoliciesForMathematicalReserveRequest();
		request.setDate(date);
		request.setMode(mode);
		int pageNumber = 0;
		int pageSize = 500;
		request.setPageNum(pageNumber);
		request.setPageSize(pageSize);

		while (pageSize == 500) {
			ResponseEntity<SearchResult<PolicyDTO>> response = RestCallUtils.postRest(getPiaRootContextURL() + EXTRACT_LISSIA_POLICIES, request, PoliciesForMathematicalReserveRequest.class, typeRef,
					keycloackUtils, logger);
			pageSize = response.getBody().getContent().size();
			pageNumber++;
			request.setPageNum(pageNumber);
			allPolicies.addAll(response.getBody().getContent());

		}

		return allPolicies;

	}

	/**
	 * get last fundPrice before date for a fund
	 * 
	 */
	private AccountingNavDTO getLastFundPriceBeforeDateForFIDOrFAS(Date calculationDate, String fund, String currency) {
		AccountingNavDTO accountingNav = new AccountingNavDTO();
		ParameterizedTypeReference<GetAccountingNavResponse> typeRef = new ParameterizedTypeReference<GetAccountingNavResponse>() {
		};
		GetAccountingNavRequest request = new GetAccountingNavRequest();
		request.setNavDate(calculationDate);
		request.setFundId(fund);
		request.setCurrency(currency.trim());

		ResponseEntity<GetAccountingNavResponse> response = RestCallUtils.postRest(getPiaRootContextURL() + GET_WEBIA_ACCOUNTING_NAV_BY_FUND, request, GetAccountingNavRequest.class, typeRef,
				keycloackUtils, logger);
		accountingNav = response.getBody().getAccountingNav();

		if (accountingNav != null)
			return accountingNav;
		else
			return null;
	}

	/**
	 * get last fundPrice before date for a fund
	 * 
	 */
	private AccountingNavDTO getLastFundPriceBeforeDateForFEOrFIC(Date calculationDate, String isin, String currency) {
		AccountingNavDTO accountingNav = new AccountingNavDTO();
		ParameterizedTypeReference<GetAccountingNavResponse> typeRef = new ParameterizedTypeReference<GetAccountingNavResponse>() {
		};
		GetAccountingNavRequest request = new GetAccountingNavRequest();
		request.setNavDate(calculationDate);
		request.setFundId(isin);
		request.setCurrency(currency.trim());

		ResponseEntity<GetAccountingNavResponse> response = RestCallUtils.postRest(getPiaRootContextURL() + GET_WEBIA_ACCOUNTING_NAV_BY_ISIN, request, GetAccountingNavRequest.class, typeRef,
				keycloackUtils, logger);
		accountingNav = response.getBody().getAccountingNav();

		if (accountingNav != null)
			return accountingNav;
		else
			return null;
	}

	/**
	 * get fund transactions for a policy to get mathematical reserve
	 * 
	 * @param date
	 * @param policyIds
	 */
	public List<TransactionGroupedByFundDTO> getTransactionsForPolicy(Date date, List<String> policyIds) {
		List<TransactionGroupedByFundDTO> allTransactions = new ArrayList<TransactionGroupedByFundDTO>();
		ParameterizedTypeReference<SearchResult<TransactionGroupedByFundDTO>> typeRef = new ParameterizedTypeReference<SearchResult<TransactionGroupedByFundDTO>>() {
		};
		TransactionsForMathematicalReserveForPolicyRequest request = new TransactionsForMathematicalReserveForPolicyRequest();
		request.setDate(date);
		request.setPolicyIds(policyIds);
		int pageNumber = 0;
		int pageSize = 500;
		request.setPageNum(pageNumber);
		request.setPageSize(pageSize);

		logger.info("CALLING LIABILITY TRANSACTION SERVICE ...");
		ResponseEntity<SearchResult<TransactionGroupedByFundDTO>> response = RestCallUtils.postRest(getPiaRootContextURL() + EXTRACT_LISSIA_TRANSACTIONS_FOR_POLICY, request,
				TransactionsForMathematicalReserveForPolicyRequest.class, typeRef, keycloackUtils, logger);

		allTransactions.addAll(response.getBody().getContent());

		return allTransactions;

	}

	/**
	 * get last fundPrice before date for a fund
	 * 
	 * @param date
	 * @param fundId
	 */
	public FundPriceDTO getLastFundPriceBeforeDate(Date date, String fundId) {
		FundPriceDTO fundPrice = new FundPriceDTO();
		ParameterizedTypeReference<SearchResult<FundPriceDTO>> typeRef = new ParameterizedTypeReference<SearchResult<FundPriceDTO>>() {
		};
		FundPriceSearchRequest request = new FundPriceSearchRequest();
		request.setDate(date);
		request.setFdsId(fundId);
		request.setTypes(Arrays.asList(1));
		int pageNumber = 0;
		int pageSize = 1;
		request.setPageNum(pageNumber);
		request.setPageSize(pageSize);

		ResponseEntity<SearchResult<FundPriceDTO>> response = RestCallUtils.postRest(getPiaRootContextURL() + EXTRACT_LISSIA_FUNDPRICES, request, FundPriceSearchRequest.class, typeRef, keycloackUtils,
				logger);
		List<FundPriceDTO> fundPrices = response.getBody().getContent();
		if (!fundPrices.isEmpty()) {
			fundPrice = fundPrices.get(0);
			return fundPrice;
		} else {
			return null;
		}

	}

	/**
	 * get last fundPrice before date for a fund
	 * 
	 * @param calculationDate
	 * @param fund
	 */
	private AccountingNavDTO getLastFundPriceBeforeDateForFIDOrFAS(Date calculationDate, String fund) {
		AccountingNavDTO accountingNav = new AccountingNavDTO();
		ParameterizedTypeReference<GetAccountingNavResponse> typeRef = new ParameterizedTypeReference<GetAccountingNavResponse>() {
		};
		GetAccountingNavRequest request = new GetAccountingNavRequest();
		request.setNavDate(calculationDate);
		request.setFundId(fund);

		ResponseEntity<GetAccountingNavResponse> response = RestCallUtils.postRest(getPiaRootContextURL() + GET_WEBIA_ACCOUNTING_NAV, request, GetAccountingNavRequest.class, typeRef, keycloackUtils,
				logger);
		accountingNav = response.getBody().getAccountingNav();

		if (accountingNav != null)
			return accountingNav;
		else
			return null;
	}

	/**
	 * get last fundPrice before date for a fund
	 * 
	 */
	public ExchangeRateDTO getLastExchangeRate(Date date, String fromCurrency, String toCurrency) {
		ExchangeRateDTO exchangeRate = new ExchangeRateDTO();
		ParameterizedTypeReference<ExchangeRateDTO> typeRef = new ParameterizedTypeReference<ExchangeRateDTO>() {
		};
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("fromCurrency", fromCurrency);
		params.add("toCurrency", toCurrency);
		params.add("date", DATE_FORMAT.format(date));

		ResponseEntity<ExchangeRateDTO> response = RestCallUtils.get(getPiaRootContextURL() + EXTRACT_LISSIA_EXCHANGE_RATE, params, ExchangeRateDTO.class, typeRef, keycloackUtils, logger);

		exchangeRate = response.getBody();

		return exchangeRate;

	}

	/**
	 * get last fundPrice before date for a fund
	 * 
	 * @param fundId
	 */
	public FundDTO getFund(String fundId) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		FundDTO fund = new FundDTO();
		ParameterizedTypeReference<FundDTO> typeRef = new ParameterizedTypeReference<FundDTO>() {
		};
		params.add("id", fundId);
		ResponseEntity<FundDTO> response = RestCallUtils.get(getPiaRootContextURL() + GET_LISSIA_FUND, params, String.class, typeRef, keycloackUtils, logger);
		fund = response.getBody();

		return fund;

	}

	/**
	 * save mathematical reserves
	 * 
	 * @param mathematicalReserves
	 */
	public SaveMathematicalReserveResponse saveMathematicalReserves(List<MathematicalReserveDTO> mathematicalReserves) {
		ParameterizedTypeReference<SaveMathematicalReserveResponse> typeRef = new ParameterizedTypeReference<SaveMathematicalReserveResponse>() {
		};
		SaveMathematicalReserveRequest request = new SaveMathematicalReserveRequest();
		request.setMathematicalReserveList(mathematicalReserves);

		ResponseEntity<SaveMathematicalReserveResponse> responseCall = RestCallUtils.postRest(getPiaRootContextURL() + SAVE_REPORTING_MATHEMATICAL_RESERVE, request,
				SaveMathematicalReserveRequest.class, typeRef, keycloackUtils, logger);
		SaveMathematicalReserveResponse response = responseCall.getBody();
		return response;

	}

	/**
	 * get mathematical reserves
	 * 
	 */
	public GetMathematicalReserveResponse getMathematicalReserves(String mode, Date date, int pageNum, int pageSize) {
		ParameterizedTypeReference<GetMathematicalReserveResponse> typeRef = new ParameterizedTypeReference<GetMathematicalReserveResponse>() {
		};
		GetMathematicalReserveRequest request = new GetMathematicalReserveRequest();
		request.setMode(mode);
		request.setDate(date);
		request.setExportDate(null);
		request.setPageNum(pageNum);
		request.setPageSize(pageSize);
		ResponseEntity<GetMathematicalReserveResponse> responseCall = RestCallUtils.postRest(getPiaRootContextURL() + GET_REPORTING_MATHEMATICAL_RESERVE, request, GetMathematicalReserveRequest.class,
				typeRef, keycloackUtils, logger);
		GetMathematicalReserveResponse response = responseCall.getBody();
		return response;

	}

	/**
	 * delete mathematical reserves
	 * 
	 */
	public DeleteMathematicalReserveResponse deleteMathematicalReserves(String mode, Date date) {
		ParameterizedTypeReference<DeleteMathematicalReserveResponse> typeRef = new ParameterizedTypeReference<DeleteMathematicalReserveResponse>() {
		};
		DeleteMathematicalReserveRequest request = new DeleteMathematicalReserveRequest();
		request.setMode(mode);
		request.setDate(date);

		ResponseEntity<DeleteMathematicalReserveResponse> responseCall = RestCallUtils.postRest(getPiaRootContextURL() + DELETE_REPORTING_MATHEMATICAL_RESERVE, request,
				DeleteMathematicalReserveRequest.class, typeRef, keycloackUtils, logger);
		DeleteMathematicalReserveResponse response = responseCall.getBody();
		return response;

	}

	private boolean datesAreEqualsForMathematicalReserve(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);

		if (calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
				&& calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
				&& calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR))
			return true;
		else
			return false;
	}

}
