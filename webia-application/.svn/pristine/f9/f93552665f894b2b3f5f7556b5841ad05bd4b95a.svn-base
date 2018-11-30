package lu.wealins.webia.core.service.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.webia.services.CreateEditingRequest;
import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.webia.core.service.WebiaTransactionFormService;
import lu.wealins.webia.core.service.impl.AbstractFundFormService;

@Component
public class FaxSurrenderDepositBankHelper {
	
	@Autowired
	private WebiaTransactionFormService transactionFormService;

	@Autowired
	private AbstractFundFormService<FundTransactionFormDTO> fundFormService;

	private static final String WORKFLOW_ITEM_NOT_NULL = "Workflow Item ID can not be null.";

	public Collection<CreateEditingRequest> createSurrenderDepositBankFaxEditings(Integer workflowItemId, boolean isSimulation) {

		Assert.notNull(workflowItemId, WORKFLOW_ITEM_NOT_NULL);

		TransactionFormDTO transactionForm = transactionFormService.getFormData(workflowItemId);

		Collection<FundTransactionFormDTO> desinvestedFundForms = transactionForm.getFundTransactionForms();
		fundFormService.enrichFunds(desinvestedFundForms);

		Collection<CreateEditingRequest> createEditingRequests = new ArrayList<>();

		createEditingRequests.addAll(createFaxSurrenderDepositBankEditings(desinvestedFundForms, workflowItemId, isSimulation));

		return createEditingRequests;
	}

	private Collection<CreateEditingRequest> createFaxSurrenderDepositBankEditings(Collection<FundTransactionFormDTO> fundTransactionForms, Integer workflowItemId, boolean isSimulation) {

		if (CollectionUtils.isEmpty(fundTransactionForms)) {
			return Collections.emptyList();
		}

		Map<String, List<FundTransactionFormDTO>> formsByDepositbank = fundTransactionForms.stream()
				.filter(fundForm -> fundForm.getFund().getDepositBank() != null)
				.filter(fundForm -> FundSubType.FID.name().equals(fundForm.getFundTp()) || FundSubType.FAS.name().equals(fundForm.getFundTp()))
				.collect(Collectors.groupingBy(fundForm -> fundForm.getFund().getDepositBank()));

		return formsByDepositbank.entrySet().stream().map(entry -> createEditingRequest(entry.getKey(), entry.getValue(), workflowItemId, isSimulation))
				.collect(Collectors.toList());
	}

	private CreateEditingRequest createEditingRequest(String agentId, List<FundTransactionFormDTO> fundForms, Integer workflowItemId, boolean isSimulation) {
		String fundFormIds = fundForms.stream().map(fundForm -> fundForm.getFundTransactionFormId().toString()).collect(Collectors.joining(","));

		CreateEditingRequest request = new CreateEditingRequest();
		request.setAgent(agentId);
		request.setDocumentType(DocumentType.FAX_SURRENDER_DEPOSIT_BANK);
		request.setFundTransactionFormIds(fundFormIds);
		request.setWorkflowItemId(Long.valueOf(workflowItemId.longValue()));
		request.setSimulation(Boolean.valueOf(isSimulation));
		request.setSynchronous(Boolean.valueOf(isSimulation));

		return request;
	}
}
