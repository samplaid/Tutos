package lu.wealins.webia.core.service.lps.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryChangeFormDTO;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.impl.CommonLpsUtilityServiceImpl;

@Service(value = "BeneficiaryChangeLpsUtilityService")
public class BeneficiaryChangeLpsUtilityServiceImpl extends CommonLpsUtilityServiceImpl<BeneficiaryChangeFormDTO> {

	private static final String MOV15 = "MOV15";
	private static final String MOV12 = "MOV12";
	private static final String MOV13 = "MOV13";
	private static final String MOV14 = "MOV14";
	private static final String MOV4 = "MOV4";
	private static final String BENEFICIARY_CHANGE_FORM_CANNOT_BE_NULL = "beneficiary change form cannot be null.";

	public List<String> ALL_LPS_CHECK_CODES = Arrays.asList(MOV4, MOV12, MOV13, MOV14, MOV15);

	@Autowired
	private LiabilityPolicyService policyService;

	@Override
	public Map<String, CheckDataDTO> setupCheckDataForLps(BeneficiaryChangeFormDTO beneficiaryChange, Collection<CheckStepDTO> checkSteps) {
		Assert.notNull(beneficiaryChange, BENEFICIARY_CHANGE_FORM_CANNOT_BE_NULL);

		Map<String, CheckDataDTO> checkDataByCheckCode = getCheckData(beneficiaryChange.getWorkflowItemId());
		Map<Integer, ClientDTO> clientsMap = getclientsMap(beneficiaryChange);

		Boolean mov4 = checkLps13(beneficiaryChange, clientsMap);

		if (mov4 == null) {
			mov4 = Boolean.FALSE;
		}

		setupCheckData(beneficiaryChange.getWorkflowItemId(), MOV4, mov4, checkDataByCheckCode);
		setupCheckData(beneficiaryChange.getWorkflowItemId(), MOV12, checkLps16(beneficiaryChange, clientsMap), checkDataByCheckCode);
		setupCheckData(beneficiaryChange.getWorkflowItemId(), MOV13, checkMov13(beneficiaryChange, clientsMap), checkDataByCheckCode);
		setupCheckData(beneficiaryChange.getWorkflowItemId(), MOV14, Boolean.FALSE, checkDataByCheckCode);
		setupCheckData(beneficiaryChange.getWorkflowItemId(), MOV15, checkLps14(beneficiaryChange, clientsMap), checkDataByCheckCode);

		return checkDataByCheckCode;
	}

	private Map<String, CheckDataDTO> getCheckData(Integer workflowItemId) {
		Assert.notNull(workflowItemId);

		return webiaCheckDataService.getCheckData(workflowItemId, ALL_LPS_CHECK_CODES);
	}

	@Override
	public String getPremiumCountryCd(BeneficiaryChangeFormDTO formData) {
		return null;
	}

	@Override
	public PartnerFormDTO getBroker(BeneficiaryChangeFormDTO formData) {
		return null;
	}

	@Override
	public BigDecimal getPaymentAmt(BeneficiaryChangeFormDTO formData) {
		return null;
	}

	@Override
	public BigDecimal getExpectedPremium(BeneficiaryChangeFormDTO formData) {
		return null;
	}

	@Override
	public String getContractCurrency(BeneficiaryChangeFormDTO formData) {
		return null;
	}

	@Override
	public Date getPaymentDt(BeneficiaryChangeFormDTO formData) {
		return null;
	}

	@Override
	public Collection<Integer> getClientIdsForPolicyHolders(BeneficiaryChangeFormDTO formData) {
		PolicyLightDTO policyLight = policyService.getPolicyLight(formData.getPolicyId());
		Set<Integer> clientIds = new HashSet<>();
		if (policyLight != null) {
			clientIds = policyLight.getPolicyHolders().stream().map(x -> x.getCliId()).collect(Collectors.toSet());
		}

		return clientIds;
	}

	@Override
	public Collection<Integer> getClientIdsForLifeBeneficiaries(BeneficiaryChangeFormDTO formData) {
		return formData.getLifeBeneficiaries().stream().map(x -> x.getClientId()).collect(Collectors.toSet());
	}

	@Override
	public Collection<Integer> getClientIdsForDeathBeneficiaries(BeneficiaryChangeFormDTO formData) {
		return formData.getDeathBeneficiaries().stream().map(x -> x.getClientId()).collect(Collectors.toSet());
	}

}
