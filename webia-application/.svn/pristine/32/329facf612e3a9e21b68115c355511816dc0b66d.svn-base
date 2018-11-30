package lu.wealins.webia.core.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.webia.services.AmountDTO;
import lu.wealins.common.dto.webia.services.CreateEditingRequest;
import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.webia.core.service.FundFormTransactionService;
import lu.wealins.webia.core.service.LiabilityPolicyValuationService;
import lu.wealins.webia.core.service.WebiaTransactionFormService;
import lu.wealins.webia.core.service.impl.AbstractFundFormService;

@Component
public class WithdrawalEnoughCashHelper {

	@Autowired
	private WebiaTransactionFormService transactionFormService;

	@Autowired
	private FundFormTransactionService fundFormTransactionService;

	@Autowired
	private LiabilityPolicyValuationService policyValuationService;
	
	@Autowired
	private AbstractFundFormService<FundTransactionFormDTO> fundFormService;

	private static final String WORKFLOW_ITEM_NOT_NULL = "Workflow Item ID can not be null.";

	public Collection<CreateEditingRequest> createEnoughCashEditings(Integer workflowItemId, boolean isSimulation) {
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_NOT_NULL);

		TransactionFormDTO transactionForm = transactionFormService.getFormData(workflowItemId);

		PolicyValuationDTO policyValuation = policyValuationService.getPolicyValuation(transactionForm.getPolicyId(), transactionForm.getCurrency(), transactionForm.getEffectiveDate());

		Collection<FundTransactionFormDTO> desinvestedFundForms = transactionForm.getFundTransactionForms();

		if (BooleanUtils.isTrue(transactionForm.getSpecificAmountByFund())) {
			desinvestedFundForms = desinvestedFundForms.stream()
					.filter(fundTransactionForm -> hasAmount(transactionForm, fundTransactionForm, policyValuation))
					.collect(Collectors.toList());
		}

		if (CollectionUtils.isEmpty(desinvestedFundForms)) {
			return Collections.emptyList();
		}

		fundFormService.enrichFunds(desinvestedFundForms);

		Map<String, List<FundTransactionFormDTO>> desinvestedFundFormsByType = desinvestedFundForms.stream()
				.collect(Collectors.groupingBy(fundFormDTO -> fundFormDTO.getFund().getFundSubType()));

		Collection<CreateEditingRequest> createEditingRequests = new ArrayList<>();

		createEditingRequests.addAll(createFasEnoughCashEditings(desinvestedFundFormsByType.get(FundSubType.FAS.toString()), workflowItemId, isSimulation));
		createEditingRequests.addAll(createFidEnoughCashEditings(desinvestedFundFormsByType.get(FundSubType.FID.toString()), workflowItemId, isSimulation));

		return createEditingRequests;
	}

	private boolean hasAmount(TransactionFormDTO transactionForm, FundTransactionFormDTO fundTransactionForm, PolicyValuationDTO policyValuation) {
		PolicyValuationHoldingDTO fundValuation = policyValuationService.getFundValuation(policyValuation, fundTransactionForm.getFundId());
		AmountDTO fundAmount = fundFormTransactionService.getTransactionFundAmount(transactionForm, fundTransactionForm, fundValuation);
		return fundAmount != null && fundAmount.getAmount() != null && fundAmount.getAmount().compareTo(BigDecimal.ZERO) == 1;
	}

	private Collection<CreateEditingRequest> createFasEnoughCashEditings(Collection<FundTransactionFormDTO> fasFundTransactionForms, Integer workflowItemId, boolean isSimulation) {

		if (CollectionUtils.isEmpty(fasFundTransactionForms)) {
			return Collections.emptyList();
		}

		Map<String, List<FundTransactionFormDTO>> formsByFinancialAdvisor = fasFundTransactionForms.stream().filter(fundForm -> fundForm.getFund().getFinancialAdvisor() != null)
				.collect(Collectors.groupingBy(fundForm -> fundForm.getFund().getFinancialAdvisor()));

		return formsByFinancialAdvisor.entrySet().stream().map(entry -> createEditingRequest(entry.getKey(), entry.getValue(), workflowItemId, isSimulation))
				.collect(Collectors.toList());
	}

	private Collection<CreateEditingRequest> createFidEnoughCashEditings(Collection<FundTransactionFormDTO> fidFundTransactionForms, Integer workflowItemId, boolean isSimulation) {

		if (CollectionUtils.isEmpty(fidFundTransactionForms)) {
			return Collections.emptyList();
		}

		Map<String, List<FundTransactionFormDTO>> formsByAssetManager = fidFundTransactionForms.stream().collect(Collectors.groupingBy(this::getAssetManager));

		return formsByAssetManager.entrySet().stream().map(entry -> createEditingRequest(entry.getKey(), entry.getValue(), workflowItemId, isSimulation))
				.collect(Collectors.toList());
	}

	private String getAssetManager(FundTransactionFormDTO fundTransactionForm) {
		String assetManager = fundTransactionForm.getFund().getAssetManager();
		if (StringUtils.isEmpty(assetManager)) {
			throw new IllegalStateException(String.format("The fid %s has no asset manager", fundTransactionForm.getFund().getFdsId()));
		}
		return assetManager;
	}

	private CreateEditingRequest createEditingRequest(String agentId, List<FundTransactionFormDTO> fundForms, Integer workflowItemId, boolean isSimulation) {
		String fundFormIds = fundForms.stream().map(fundForm -> fundForm.getFundTransactionFormId().toString()).collect(Collectors.joining(","));

		CreateEditingRequest request = new CreateEditingRequest();
		request.setAgent(agentId);
		request.setDocumentType(DocumentType.WITHDRAWAL_FOLLOWUP);
		request.setFundTransactionFormIds(fundFormIds);
		request.setWorkflowItemId(workflowItemId.longValue());
		request.setSimulation(isSimulation);
		request.setSynchronous(isSimulation);

		return request;
	}
}
