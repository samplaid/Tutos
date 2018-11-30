package lu.wealins.webia.core.service.lps.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.webia.core.service.impl.CommonLpsUtilityServiceImpl;

@Service(value = "AppFormLpsUtilityService")
public class AppFormLpsUtilityServiceImpl extends CommonLpsUtilityServiceImpl<AppFormDTO> {

	private static final String APP_FORM_CANNOT_BE_NULL = "App form cannot be null.";
	private static final String LPS16 = "LPS16";
	private static final String LPS15 = "LPS15";
	private static final String LPS14 = "LPS14";
	private static final String LPS13 = "LPS13";
	private static final String LPS10 = "LPS10";
	private static final String LPS5 = "LPS5";
	private static final String LPS4 = "LPS4";
	private static final String LPS3 = "LPS3";
	private static final String LPS2 = "LPS2";
	private static final String LPS1 = "LPS1";
	public List<String> ALL_LPS_CHECK_CODES = Arrays.asList(LPS1, LPS2, LPS3, LPS4, LPS5, LPS10, LPS13, LPS14, LPS15, LPS16);

	@Override
	public Map<String, CheckDataDTO> setupCheckDataForLps(AppFormDTO appForm, Collection<CheckStepDTO> checkSteps) {
		Assert.notNull(appForm, APP_FORM_CANNOT_BE_NULL);

		Map<String, CheckDataDTO> checkDataByCheckCode = getCheckData(appForm.getWorkflowItemId());
		Map<Integer, ClientDTO> clientsMap = getclientsMap(appForm);

		setupCheckData(appForm.getWorkflowItemId(), LPS1, checkLps1(appForm, clientsMap), checkDataByCheckCode);
		setupCheckData(appForm.getWorkflowItemId(), LPS2, checkLps2(appForm, clientsMap), checkDataByCheckCode);
		setupCheckData(appForm.getWorkflowItemId(), LPS3, checkLps3(appForm), checkDataByCheckCode);
		setupCheckData(appForm.getWorkflowItemId(), LPS4, checkLps4(appForm, clientsMap), checkDataByCheckCode);
		setupCheckData(appForm.getWorkflowItemId(), LPS5, checkLps5(appForm), checkDataByCheckCode);
		setupCheckData(appForm.getWorkflowItemId(), LPS10, checkLps10(appForm, clientsMap), checkDataByCheckCode);
		setupCheckData(appForm.getWorkflowItemId(), LPS13, checkLps13(appForm, clientsMap), checkDataByCheckCode);
		setupCheckData(appForm.getWorkflowItemId(), LPS14, checkLps14(appForm, clientsMap), checkDataByCheckCode);
		setupCheckData(appForm.getWorkflowItemId(), LPS15, checkLps15(appForm, clientsMap), checkDataByCheckCode);
		setupCheckData(appForm.getWorkflowItemId(), LPS16, checkLps16(appForm, clientsMap), checkDataByCheckCode);

		return checkDataByCheckCode;
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

}
