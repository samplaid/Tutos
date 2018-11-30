package lu.wealins.webia.core.service.lps.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.webia.core.service.LiabilityExchangeRateService;
import lu.wealins.webia.core.service.LiabilityPolicyValuationService;
import lu.wealins.webia.core.service.impl.CommonLpsUtilityServiceImpl;

@Service(value = "AdditionalPremiumLpsUtilityService")
public class AdditionalPremiumLpsUtilityServiceImpl extends CommonLpsUtilityServiceImpl<AppFormDTO> {

	private static final String APP_FORM_CANNOT_BE_NULL = "App form cannot be null.";
	private static final String MOV15 = "MOV15";
	private static final String MOV12 = "MOV12";
	private static final String MOV9 = "MOV9";
	private static final String MOV10 = "MOV10";
	private static final String MOV13 = "MOV13";
	private static final String MOV11 = "MOV11";
	private static final List<String> MOV11_SUB_QUESTIONS = Arrays.asList("MOV11-LPS6", "MOV11-LPS9", "MOV11-LPS7");
	private static final String FUNDS_ORIGIN_CLIENT = "FUNDS_ORIGIN_CLIENT";
	private static final String COUNTRY_SENSITIVE = "COUNTRY_SENSITIVE";
	public List<String> ALL_LPS_CHECK_CODES = Arrays.asList(MOV15, MOV12, MOV9, MOV10, MOV13, FUNDS_ORIGIN_CLIENT, COUNTRY_SENSITIVE, MOV11);
	private static final String SENSITIVE_COUNTRIES_CODE = "SENSITIVE_COUNTRIES";

	@Autowired
	private LiabilityPolicyValuationService valuationService;

	@Autowired
	private LiabilityExchangeRateService exchangeRateService;

	@Override
	public Map<String, CheckDataDTO> setupCheckDataForLps(AppFormDTO appForm, Collection<CheckStepDTO> checkSteps) {
		Assert.notNull(appForm, APP_FORM_CANNOT_BE_NULL);

		Map<String, CheckDataDTO> checkDataByCheckCode = getCheckData(appForm.getWorkflowItemId());
		Map<Integer, ClientDTO> clientsMap = getclientsMap(appForm);

		initFromForm(appForm, checkDataByCheckCode, clientsMap);
		initFromOtherQuestions(appForm.getWorkflowItemId(), checkDataByCheckCode, checkSteps);

		return checkDataByCheckCode;
	}

	private void initFromOtherQuestions(Integer workflowItemId, Map<String, CheckDataDTO> checkDataByCheckCode, Collection<CheckStepDTO> checkSteps) {
		// This init is different than the others because we use the other questions inside.
		// If we want to init the other questions that we depend on, we should do it before this call.
		setupCheckData(workflowItemId, MOV11, checkMov11(checkSteps), checkDataByCheckCode);
	}

	private void initFromForm(AppFormDTO appForm, Map<String, CheckDataDTO> checkDataByCheckCode, Map<Integer, ClientDTO> clientsMap) {
		setupCheckData(appForm.getWorkflowItemId(), MOV9, checkMove9(appForm), checkDataByCheckCode);
		setupCheckData(appForm.getWorkflowItemId(), MOV10, checkMove10(appForm), checkDataByCheckCode);
		setupCheckData(appForm.getWorkflowItemId(), MOV12, checkLps16(appForm, clientsMap), checkDataByCheckCode);
		setupCheckData(appForm.getWorkflowItemId(), MOV13, checkMov13(appForm, clientsMap), checkDataByCheckCode);
		setupCheckData(appForm.getWorkflowItemId(), MOV15, checkLps14(appForm, clientsMap), checkDataByCheckCode);
		setupCheckData(appForm.getWorkflowItemId(), FUNDS_ORIGIN_CLIENT, checkFundsOriginWithClientsOrigin(appForm, clientsMap), checkDataByCheckCode);
		setupCheckData(appForm.getWorkflowItemId(), COUNTRY_SENSITIVE, checkCountrySensitivity(appForm, clientsMap), checkDataByCheckCode);
	}

	private Boolean checkMov11(Collection<CheckStepDTO> checkSteps) {
		return checkSteps.stream()
				.filter(this::isMov11SubQuestion)
				.map(this::getBooleanValue)
				.anyMatch(Boolean.TRUE::equals);
	}

	private boolean isMov11SubQuestion(CheckStepDTO checkStep) {
		return MOV11_SUB_QUESTIONS.contains(checkStep.getCheck().getCheckCode());
	}

	private Boolean getBooleanValue(CheckStepDTO checkStep) {
		return convert(checkStep.getCheck().getCheckData().getDataValueYesNoNa());
	}

	private Boolean checkCountrySensitivity(AppFormDTO appForm, Map<Integer, ClientDTO> clientsMap) {
		String fundsCountry = getPremiumCountryCd(appForm);

		if (fundsCountry == null) {
			return null;
		}

		return !applicationParameterService.getApplicationParameters(SENSITIVE_COUNTRIES_CODE).contains(fundsCountry);
	}

	private Boolean checkFundsOriginWithClientsOrigin(AppFormDTO appForm, Map<Integer, ClientDTO> clientsMap) {
		String fundsCountry = getPremiumCountryCd(appForm);

		if (fundsCountry == null) {
			return null;
		}

		return getClientsForPolicyHolders(appForm, clientsMap)
				.stream()
				.map(this::getClientResidence)
				.noneMatch(fundsCountry::equals);
	}

	private String getClientResidence(ClientDTO client) {
		return client.getHomeAddress().getCountry();
	}

	private Map<String, CheckDataDTO> getCheckData(Integer workflowItemId) {
		Assert.notNull(workflowItemId);

		return webiaCheckDataService.getCheckData(workflowItemId, ALL_LPS_CHECK_CODES);
	}

	@Override
	public String getPremiumCountryCd(AppFormDTO appForm) {
		return appForm.getPremiumCountryCd();
	}

	@Override
	public PartnerFormDTO getBroker(AppFormDTO appForm) {
		return appForm.getBroker();
	}

	@Override
	public BigDecimal getPaymentAmt(AppFormDTO appForm) {
		return appForm.getPaymentAmt();
	}

	@Override
	public BigDecimal getExpectedPremium(AppFormDTO appForm) {
		return appForm.getExpectedPremium();
	}

	@Override
	public String getContractCurrency(AppFormDTO appForm) {
		return appForm.getContractCurrency();
	}

	@Override
	public Date getPaymentDt(AppFormDTO appForm) {
		return appForm.getPaymentDt();
	}

	@Override
	public Collection<Integer> getClientIdsForLifeBeneficiaries(AppFormDTO appForm) {
		return appForm.getLifeBeneficiaries().stream().map(x -> x.getClientId()).collect(Collectors.toSet());
	}

	@Override
	public Collection<Integer> getClientIdsForDeathBeneficiaries(AppFormDTO appForm) {
		return appForm.getDeathBeneficiaries().stream().map(x -> x.getClientId()).collect(Collectors.toSet());
	}

	@Override
	public Collection<Integer> getClientIdsForPolicyHolders(AppFormDTO appForm) {
		return appForm.getPolicyHolders().stream().map(x -> x.getClientId()).collect(Collectors.toSet());
	}

	@Override
	public Collection<Integer> getClientIdsForInsureds(AppFormDTO appForm) {
		return appForm.getInsureds().stream().map(x -> x.getClientId()).collect(Collectors.toSet());
	}

	private Boolean checkMove10(AppFormDTO app) {

		BigDecimal amount = new BigDecimal(100000);
		String currency = "EUR";
		Boolean lpsCondition = true;
		PolicyValuationDTO policyValuationDTO = valuationService.getPolicyValuation(app.getPolicyId(), null, null);

		BigDecimal totalPolicyCurrency = policyValuationDTO.getTotalPolicyCurrency();
		totalPolicyCurrency = convert(totalPolicyCurrency, app.getContractCurrency(), currency, app.getPaymentDt());

		if (totalPolicyCurrency.compareTo(amount) == 1) {
			return Boolean.FALSE;
		}

		BigDecimal transactionAmount = app.getPaymentAmt();
		if (transactionAmount == null) {
			transactionAmount = app.getExpectedPremium();
		}

		if (transactionAmount == null) {
			return null;
		}

		BigDecimal policyValutaionAfterPremium = transactionAmount.add(policyValuationDTO.getTotalPolicyCurrency());
		policyValutaionAfterPremium = convert(policyValutaionAfterPremium, app.getContractCurrency(), currency, app.getPaymentDt());

		if (policyValutaionAfterPremium.compareTo(amount) == -1) {
			lpsCondition = Boolean.FALSE;
		}

		return lpsCondition;
	}

	private BigDecimal convert(BigDecimal amount, String currencyFrom, String currencyTo, Date date) {
		if (date != null && !currencyFrom.trim().equalsIgnoreCase(currencyTo)) {
			return exchangeRateService.convert(amount, currencyFrom, currencyTo, date);
		}
		
		return amount;
	}

	private Boolean checkMove9(AppFormDTO app) {
		// This condition only applies to certain german products.
		// It should return false according to CWA.
		// A new rule might be given in the future.
		return false;
	}

}
