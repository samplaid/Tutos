/**
 * 
 */
package lu.wealins.batch.extract.encashmentSapAccounting;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.ProductDTO;
import lu.wealins.common.dto.webia.services.AccountingPeriodDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.EncashmentFundFormDTO;
import lu.wealins.common.dto.webia.services.FindSapEncashmentsResponse;
import lu.wealins.common.dto.webia.services.GetAccountingPeriodResponse;
import lu.wealins.common.dto.webia.services.SapAccountingDTO;
import lu.wealins.common.dto.webia.services.SapMappingDTO;
import lu.wealins.common.dto.webia.services.SaveEncashmentsInSapAccountingRequest;
import lu.wealins.common.dto.webia.services.SaveEncashmentsInSapAccountingResponse;
import lu.wealins.common.dto.webia.services.UpdateEncashmentsRequest;
import lu.wealins.common.dto.webia.services.UpdateEncashmentsResponse;
import lu.wealins.utils.RestCallUtils;

/**
 * @author xqt5q
 *
 */
public class ExtractEncashmentForSAPAccountingTaskLet extends AbstractExtractEncashmentForSAPAccountingTaskLet {

	private static final String COMPANY = "1600";
	private static final String PIECE = "PR";
	private static final String ACCOUNT_GENERAL = "V";
	private static final String LISSIA = "LISSIA";
	private static final String CREDIT_ACCOUNT = "4802000";
	private static final String DEBIT_ACCOUNT = "2500002000";
	private static final String CREDIT = "C";
	private static final String DEBIT = "D";
	private static final String NEW_STATUS = "NEW";
	private static final String CANCEL_STATUS = "CANCEL";
	private static String EXTRACT_WEBIA_ENCASHMENT = "webia/encashments/getSapEncashments";
	private static String GET_ACTIVE_ACCOUNTING_PERIOD = "webia/accountingPeriod/getActiveAccountingPeriod";
	private static String GET_APP_FORM = "webia/appFormEntry/appForm/";
	private static String GET_POLICY_FROM_FORM = "liability/policy/workflowItemId/";
	private static String GET_LISSIA_FUND = "liability/fund/one";
	private static String GET_LISSIA_PRODUCT = "liability/product/";
	private static String EXTRACT_LISSIA_EXCHANGE_RATE = "liability/exchangeRate";
	private static String SAVE_ACCOUNTINGS = "webia/encashments/saveSapAccountings";
	private static String UPDATE_ENCASHMENTS = "webia/encashments/updateEncashments";
	private static String GET_SAP_ACCOUNTING = "webia/sapAccountings/getByOriginId/";
	private static String GET_SAP_MAPPING = "webia/sapMapping/type/";

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final String EUR_CURRENCY = "EUR";
	private static final String FUND_TYPE = "FUND_TYPE";
	private static final String CURRENCY = "CURRENCY";
	private static final String EXPLAIN_SAP_ACCOUNTING_DELIMITER = " ";

	{
		Log logger = LogFactory.getLog(ExtractEncashmentForSAPAccountingTaskLet.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org. springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		logger.info("Starting encashment batch");
		logger.info("Extracting encashments ....");

		try {
			List<EncashmentFundFormDTO> encashments = getSapEncashments();
			if (encashments != null && encashments.size() > 0) {
				logger.info(encashments.size() + " encashments found");
				logger.info("Building SAP accountings ...");
				List<SapAccountingDTO> sapAccountingDTOs = constructSapAccountings(encashments);
				logger.info("Saving SAP accountings ...");
				List<SapAccountingDTO> entitiesSaved = saveSapAccountings(sapAccountingDTOs);
				logger.info("SAP accountings saved");
				logger.info("Updating encashments ...");
				updateEncashments(encashments);

			}
		} catch (Exception e) {
			logger.info("ERROR IN CONSTRUCTION OF SAP ACCOUNTINGS FOR ENCASHMENTS");
			throw new Exception();
		}

		return RepeatStatus.FINISHED;
	}

	private List<SapAccountingDTO> saveSapAccountings(List<SapAccountingDTO> accountings) {
		ParameterizedTypeReference<SaveEncashmentsInSapAccountingResponse> typeRef = new ParameterizedTypeReference<SaveEncashmentsInSapAccountingResponse>() {
		};
		SaveEncashmentsInSapAccountingRequest request = new SaveEncashmentsInSapAccountingRequest();
		request.setEncashments(accountings);

		ResponseEntity<SaveEncashmentsInSapAccountingResponse> responseCall = RestCallUtils.postRest(getPiaRootContextURL() + SAVE_ACCOUNTINGS, request, SaveEncashmentsInSapAccountingRequest.class,
				typeRef, keycloackUtils, logger);
		SaveEncashmentsInSapAccountingResponse response = responseCall.getBody();
		return response.getEncashments();
	}

	private List<EncashmentFundFormDTO> updateEncashments(List<EncashmentFundFormDTO> encashments) {
		ParameterizedTypeReference<UpdateEncashmentsResponse> typeRef = new ParameterizedTypeReference<UpdateEncashmentsResponse>() {
		};
		UpdateEncashmentsRequest request = new UpdateEncashmentsRequest();
		request.setEncashments(encashments);

		ResponseEntity<UpdateEncashmentsResponse> responseCall = RestCallUtils.postRest(getPiaRootContextURL() + UPDATE_ENCASHMENTS, request, UpdateEncashmentsRequest.class,
				typeRef, keycloackUtils, logger);
		UpdateEncashmentsResponse response = responseCall.getBody();
		return response.getEncashments();
	}

	private List<SapAccountingDTO> constructSapAccountings(List<EncashmentFundFormDTO> encashments) {

		List<SapAccountingDTO> sapAccountings = new ArrayList<SapAccountingDTO>();
		AccountingPeriodDTO activeAccountingPeriod = getActiveAccountingPeriod();

		List<SapMappingDTO> currencies = getSapMapping(CURRENCY);
		List<SapMappingDTO> fundTypes = getSapMapping(FUND_TYPE);

		for (EncashmentFundFormDTO e : encashments) {

			if (e.getCashStatus().equals(NEW_STATUS)) {

				FundDTO fund = getFund(e.getFundId());
				AppFormDTO appForm = getAppForm(e.getFormId().toString());
				// PolicyDTO policy = getPolicy(appForm.getWorkflowItemId());
				String explain = "";
				if (fund != null) {
					explain += String.join(EXPLAIN_SAP_ACCOUNTING_DELIMITER
											,fund.getFdsId()
											,fund.getAccountRoot()
											,fund.getDepositBank());
					
					if (explain.length() > 50) {
						explain = explain.substring(0, 50);
					}
				}

				SapAccountingDTO debitAccounting = constructGlobalSapAccountingFromEncashment(e, activeAccountingPeriod, appForm);
				debitAccounting.setAccount(DEBIT_ACCOUNT);
				debitAccounting.setDebitCredit(DEBIT);
				debitAccounting.setReconciliation(fund.getFdsId());
				debitAccounting.setPolicy(null);
				debitAccounting.setSupport(null);
				debitAccounting.setExplain(explain);
				if (fund != null) {
					debitAccounting.setFund(fund.getFdsId());
				}

				SapAccountingDTO creditAccounting = constructGlobalSapAccountingFromEncashment(e, activeAccountingPeriod, appForm);
				String currencyMapping = "";
				for (SapMappingDTO mapping : currencies) {
					if (mapping.getDataIn().equals(creditAccounting.getCurrency())) {
						currencyMapping = mapping.getDataOut();
						break;
					}
				}
				creditAccounting.setAccount(CREDIT_ACCOUNT + currencyMapping);
				creditAccounting.setDebitCredit(CREDIT);
				creditAccounting.setReconciliation(null);
				creditAccounting.setExplain(explain);
				if (appForm != null && appForm.getPolicyId() != null) {
					creditAccounting.setPolicy(appForm.getPolicyId());
				}
				if (fund != null) {
					String supportMapping = "";
					for (SapMappingDTO mapping : fundTypes) {
						if (mapping.getDataIn().equals(fund.getFundSubType())) {
							supportMapping = mapping.getDataOut();
							break;
						}
					}
					creditAccounting.setSupport(supportMapping);
				}
				creditAccounting.setFund(null);

				sapAccountings.add(creditAccounting);
				sapAccountings.add(debitAccounting);

			} else if (e.getCashStatus().equals(CANCEL_STATUS)) {

				List<SapAccountingDTO> sapAccountingsExtourne = getSapAccountingByOriginId(e.getFormId().longValue(), e.getFundId());

				for (SapAccountingDTO sap : sapAccountingsExtourne) {
					if (sap.getDebitCredit().equals(DEBIT)) {
						sap.setDebitCredit(CREDIT);
					} else if (sap.getDebitCredit().equals(CREDIT)) {
						sap.setDebitCredit(DEBIT);
					}

					sap.setAccountDate(computeAccountDate(sap.getAccountDate(), activeAccountingPeriod));
					sap.setExportDate(null);
					sap.setIdSapAcc(null);
					sapAccountings.add(sap);
				}

			}
		}

		return sapAccountings;
	}

	private SapAccountingDTO constructGlobalSapAccountingFromEncashment(EncashmentFundFormDTO encashment, AccountingPeriodDTO activePeriod, AppFormDTO form) {

		ExchangeRateDTO exchangeRateDTO = null;

		if (!encashment.getCashCurrency().equals(EUR_CURRENCY)) {
			exchangeRateDTO = getLastExchangeRate(encashment.getCashDt(), encashment.getCashCurrency(), EUR_CURRENCY);
		}

		ProductDTO product = null;
		if (form != null && form.getProductCd() != null) {
			product = getProduct(form.getProductCd());
		}

		SapAccountingDTO sapAccounting = new SapAccountingDTO();
		sapAccounting.setCompany(COMPANY);
		sapAccounting.setPiece(PIECE);
		sapAccounting.setCurrency(encashment.getCashCurrency());
		sapAccounting.setPieceNb(encashment.getCashFundFormId().toString());
		sapAccounting.setAccountDate(computeAccountDate(encashment.getCashDt(), activePeriod));
		if (!encashment.getCashCurrency().equals(EUR_CURRENCY)) {
			if (exchangeRateDTO != null && exchangeRateDTO.getMidRate() != null) {
				sapAccounting.setChangeRate(exchangeRateDTO.getMidRate());
			} else {
				logger.error("NO CHANGE RATE AT DATE " + encashment.getCashDt() + " FOR CURRENCY " + encashment.getCashCurrency());
			}
		} else {
			sapAccounting.setChangeRate(new BigDecimal(1));
		}

		sapAccounting.setAccountGeneral(ACCOUNT_GENERAL);
		sapAccounting.setAmount(encashment.getCashAmt());
		if (form != null) {
			sapAccounting.setCountry(form.getCountryCd());
			if (product != null) {
				sapAccounting.setProduct(product.getNlProduct());
				sapAccounting.setCountryOfProduct(product.getNlCountry());
			}
		}
		sapAccounting.setAgent(null);
		sapAccounting.setCreationDate(new Date());
		sapAccounting.setExportDate(null);
		sapAccounting.setOrigin(LISSIA);
		sapAccounting.setOriginId(form.getFormId().longValue());

		return sapAccounting;

	}

	private List<EncashmentFundFormDTO> getSapEncashments() {

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ParameterizedTypeReference<FindSapEncashmentsResponse> typeRef = new ParameterizedTypeReference<FindSapEncashmentsResponse>() {
		};
		logger.info("Trying to get encashments from WEBIA ...");
		ResponseEntity<FindSapEncashmentsResponse> response = RestCallUtils.get(getPiaRootContextURL() + EXTRACT_WEBIA_ENCASHMENT, params, FindSapEncashmentsResponse.class, typeRef, keycloackUtils,
				logger);
		RestCallUtils.get(getPiaRootContextURL() + EXTRACT_WEBIA_ENCASHMENT, params, FindSapEncashmentsResponse.class, typeRef, keycloackUtils, logger);
		FindSapEncashmentsResponse encashmentsResponse = response.getBody();
		return encashmentsResponse.getEncashments();
	}

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

	public AccountingPeriodDTO getActiveAccountingPeriod() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		AccountingPeriodDTO period = new AccountingPeriodDTO();
		ParameterizedTypeReference<GetAccountingPeriodResponse> typeRef = new ParameterizedTypeReference<GetAccountingPeriodResponse>() {
		};
		ResponseEntity<GetAccountingPeriodResponse> response = RestCallUtils.get(getPiaRootContextURL() + GET_ACTIVE_ACCOUNTING_PERIOD, params, String.class, typeRef, keycloackUtils, logger);
		period = response.getBody().getAccountingPeriod();
		return period;
	}

	public ProductDTO getProduct(String productId) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ParameterizedTypeReference<ProductDTO> typeRef = new ParameterizedTypeReference<ProductDTO>() {
		};
		ResponseEntity<ProductDTO> response = RestCallUtils.get(getPiaRootContextURL() + GET_LISSIA_PRODUCT + productId, params, String.class, typeRef, keycloackUtils, logger);

		return response.getBody();

	}

	public AppFormDTO getAppForm(String formId) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ParameterizedTypeReference<AppFormDTO> typeRef = new ParameterizedTypeReference<AppFormDTO>() {
		};
		ResponseEntity<AppFormDTO> response = RestCallUtils.get(getPiaRootContextURL() + GET_APP_FORM + formId, params, String.class, typeRef, keycloackUtils, logger);
		return response.getBody();
	}

	public PolicyDTO getPolicy(Integer policyId) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ParameterizedTypeReference<PolicyDTO> typeRef = new ParameterizedTypeReference<PolicyDTO>() {
		};
		ResponseEntity<PolicyDTO> response = RestCallUtils.get(getPiaRootContextURL() + GET_POLICY_FROM_FORM + policyId, params, String.class, typeRef, keycloackUtils, logger);
		return response.getBody();
	}

	public List<SapAccountingDTO> getSapAccountingByOriginId(Long originId, String fundId) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("fundId", fundId);
		ParameterizedTypeReference<List<SapAccountingDTO>> typeRef = new ParameterizedTypeReference<List<SapAccountingDTO>>() {
		};
		ResponseEntity<List<SapAccountingDTO>> response = RestCallUtils.get(getPiaRootContextURL() + GET_SAP_ACCOUNTING + originId, params, String.class, typeRef, keycloackUtils, logger);
		return response.getBody();
	}

	public List<SapMappingDTO> getSapMapping(String type) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ParameterizedTypeReference<List<SapMappingDTO>> typeRef = new ParameterizedTypeReference<List<SapMappingDTO>>() {
		};
		ResponseEntity<List<SapMappingDTO>> response = RestCallUtils.get(getPiaRootContextURL() + GET_SAP_MAPPING + type, params, String.class, typeRef, keycloackUtils, logger);
		return response.getBody();
	}

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

	public Date computeAccountDate(Date encashmentDate, AccountingPeriodDTO accountingPeriod) {
		Date returnDate = encashmentDate;
		if (accountingPeriod != null && accountingPeriod.getEffectiveStartDate() != null) {
			if (encashmentDate.before(accountingPeriod.getEffectiveStartDate())) {
				Calendar c = Calendar.getInstance();
				c.setTime(accountingPeriod.getEffectiveStartDate());
				c.set(Calendar.DAY_OF_MONTH, 1);
				returnDate = c.getTime();
			}
		}

		return returnDate;
	}

}
