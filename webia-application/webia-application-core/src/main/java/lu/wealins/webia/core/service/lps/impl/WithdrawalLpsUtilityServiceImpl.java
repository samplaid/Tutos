package lu.wealins.webia.core.service.lps.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.impl.CommonLpsUtilityServiceImpl;

@Service(value = "WithdrawalLpsUtilityService")
public class WithdrawalLpsUtilityServiceImpl extends CommonLpsUtilityServiceImpl<TransactionFormDTO> {

	private static final String MOV1 = "MOV1";
	private static final String MOV12 = "MOV12";
	private static final String MOV13 = "MOV13";
	private static final String MOV15 = "MOV15";
	private static final String TRANSACTION_FORM_CANNOT_BE_NULL = "Transaction change form cannot be null.";

	public List<String> ALL_LPS_CHECK_CODES = Arrays.asList(MOV1, MOV12, MOV13, MOV15);

	@Autowired
	private LiabilityPolicyService policyService;

	@Override
	public Map<String, CheckDataDTO> setupCheckDataForLps(TransactionFormDTO transactionForm, Collection<CheckStepDTO> checkSteps) {
		Assert.notNull(transactionForm, TRANSACTION_FORM_CANNOT_BE_NULL);

		Map<String, CheckDataDTO> checkDataByCheckCode = getCheckData(transactionForm.getWorkflowItemId());
		Map<Integer, ClientDTO> clientsMap = getclientsMap(transactionForm);
		
		String policyId = transactionForm.getPolicyId();
		Assert.notNull(policyId, "Policy id cannot be null");
		PolicyDTO policy = policyService.getPolicy(policyId);
		Assert.notNull(policyId, "Policy " + policyId + " cannot be retrieved");

		Date effectiveDate = transactionForm.getEffectiveDate();
		if (effectiveDate == null) {
			effectiveDate = new Date();
		}

		setupCheckData(transactionForm.getWorkflowItemId(), MOV1, checkMov1(policy.getDateOfCommencement(), effectiveDate), checkDataByCheckCode);
		setupCheckData(transactionForm.getWorkflowItemId(), MOV12, checkLps16(transactionForm, clientsMap), checkDataByCheckCode);
		setupCheckData(transactionForm.getWorkflowItemId(), MOV13, checkMov13(transactionForm, clientsMap), checkDataByCheckCode);
		setupCheckData(transactionForm.getWorkflowItemId(), MOV15, checkLps14(transactionForm, clientsMap), checkDataByCheckCode);

		return checkDataByCheckCode;
	}

	private Map<String, CheckDataDTO> getCheckData(Integer workflowItemId) {
		Assert.notNull(workflowItemId);

		return webiaCheckDataService.getCheckData(workflowItemId, ALL_LPS_CHECK_CODES);
	}

	@Override
	public String getPremiumCountryCd(TransactionFormDTO formData) {
		return null;
	}

	@Override
	public PartnerFormDTO getBroker(TransactionFormDTO formData) {
		return null;
	}

	@Override
	public BigDecimal getPaymentAmt(TransactionFormDTO formData) {
		return null;
	}

	@Override
	public BigDecimal getExpectedPremium(TransactionFormDTO formData) {
		return null;
	}

	@Override
	public String getContractCurrency(TransactionFormDTO formData) {
		return null;
	}

	@Override
	public Date getPaymentDt(TransactionFormDTO formData) {
		return null;
	}

	@Override
	public Collection<Integer> getClientIdsForPolicyHolders(TransactionFormDTO formData) {
		PolicyLightDTO policyLight = policyService.getPolicyLight(formData.getPolicyId());
		Set<Integer> clientIds = new HashSet<>();
		if (policyLight != null) {
			clientIds = policyLight.getPolicyHolders().stream().map(x -> x.getCliId()).collect(Collectors.toSet());
		}

		return clientIds;
	}

	@Override
	public Collection<Integer> getClientIdsForLifeBeneficiaries(TransactionFormDTO formData) {
		return new ArrayList<Integer>();
	}

	@Override
	public Collection<Integer> getClientIdsForDeathBeneficiaries(TransactionFormDTO formData) {
		return new ArrayList<Integer>();
	}

}
