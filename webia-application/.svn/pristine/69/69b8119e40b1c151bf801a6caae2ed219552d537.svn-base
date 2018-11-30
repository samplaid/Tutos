package lu.wealins.webia.ws.rest.impl;

import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.CreateEditingRequest;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.service.SynchronousDocumentService;
import lu.wealins.webia.core.service.TransactionFormService;
import lu.wealins.webia.core.service.helper.WithdrawalEnoughCashHelper;
import lu.wealins.webia.ws.rest.WithdrawalFormRESTService;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Component
public class WithdrawalFormRESTServiceImpl implements WithdrawalFormRESTService {

	@Autowired
	private WithdrawalEnoughCashHelper withdrawalEnoughCashHelper;

	@Autowired
	private SynchronousDocumentService synchronousDocumentService;

	@Autowired
	private EditingService editingService;

	@Autowired
	@Qualifier(value = "WithdrawalFormService")
	private TransactionFormService withdrawalFormService;

	@Override
	public TransactionFormDTO initWithdrawalForm(SecurityContext context, String policyId, Integer workflowItemId) {
		return withdrawalFormService.initTransactionForm(policyId, workflowItemId);
	}

	@Override
	public Collection<EditingRequest> createEnoughCashEditings(SecurityContext context, Integer workflowItemId) {
		Collection<CreateEditingRequest> createEditings = withdrawalEnoughCashHelper.createEnoughCashEditings(workflowItemId, true);
		if (!CollectionUtils.isEmpty(createEditings)) {

			Collection<EditingRequest> editingRequests = editingService.createEditingRequests(createEditings);
			editingRequests.stream().forEach(editingRequest -> synchronousDocumentService.createDocumentSynchronously(context, editingRequest));
			return editingRequests;
		}
		return Collections.emptyList();
	}

	@Override
	public Response generateEnoughCashDocument(SecurityContext context, Long editingRequestId) {
		editingService.getEditingRequestById(editingRequestId);
		return null;
	}
}
