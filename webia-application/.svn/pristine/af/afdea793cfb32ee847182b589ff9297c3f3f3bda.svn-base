package lu.wealins.webia.ws.rest.impl;

import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.CreateEditingRequest;
import lu.wealins.common.dto.webia.services.SurrenderTransactionDetailsDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.service.SynchronousDocumentService;
import lu.wealins.webia.core.service.TransactionFormService;
import lu.wealins.webia.core.service.helper.FaxSurrenderDepositBankHelper;
import lu.wealins.webia.ws.rest.SurrenderFormRESTService;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Component
public class SurrenderFormRESTServiceImpl implements SurrenderFormRESTService {

	@Autowired
	@Qualifier(value = "SurrenderFormService")
	private TransactionFormService surrenderFormService;

	@Autowired
	private FaxSurrenderDepositBankHelper faxSurrenderDepositBankHelper;

	@Autowired
	private SynchronousDocumentService synchronousDocumentService;

	@Autowired
	private EditingService editingService;

	@Override
	public TransactionFormDTO initSurrenderForm(SecurityContext context, String policyId, Integer workflowItemId) {
		return surrenderFormService.initTransactionForm(policyId, workflowItemId);
	}

	@Override
	public Collection<EditingRequest> createFaxDepositBankEditings(SecurityContext context, Integer workflowItemId) {
		Collection<CreateEditingRequest> createEditings = faxSurrenderDepositBankHelper.createSurrenderDepositBankFaxEditings(workflowItemId, true);
		if (!CollectionUtils.isEmpty(createEditings)) {

			Collection<EditingRequest> editingRequests = editingService.createEditingRequests(createEditings);
			editingRequests.stream().forEach(editingRequest -> synchronousDocumentService.createDocumentSynchronously(context, editingRequest));
			return editingRequests;
		}
		return Collections.emptyList();
	}

	@Override
	public SurrenderTransactionDetailsDTO getSurrenderDetails(Integer workflowItemId) {
		return surrenderFormService.getSurrenderDetails(workflowItemId);
	}
}
