package lu.wealins.webia.services.ws.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.webia.services.core.service.TransactionFormService;
import lu.wealins.webia.services.ws.rest.TransactionFormRESTService;

@Component
public class TransactionFormRESTServiceImpl implements TransactionFormRESTService {

	@Autowired
	@Qualifier(value = "WithdrawalFormService")
	private TransactionFormService transactionFormService;

	@Override
	public TransactionFormDTO getFormData(Integer workflowItemId) {
		return transactionFormService.getFormData(workflowItemId);
	}
}
