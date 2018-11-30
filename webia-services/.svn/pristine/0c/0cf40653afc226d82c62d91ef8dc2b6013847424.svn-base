package lu.wealins.webia.services.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.OperationStatus;
import lu.wealins.webia.services.core.mapper.TransactionFormMapper;
import lu.wealins.webia.services.core.persistence.entity.TransactionFormEntity;
import lu.wealins.webia.services.core.persistence.repository.TransactionFormRepository;
import lu.wealins.webia.services.core.service.TransactionFormService;
import lu.wealins.webia.services.core.service.UpdatableStatusService;

@Service(value = "TransactionFormService")
public abstract class TransactionFormServiceImpl extends WorkflowFormServiceImpl<TransactionFormDTO> implements UpdatableStatusService, TransactionFormService {

	private static final String TRANSACTION_FORM_CANNOT_BE_NULL = "TransactionForm cannot be null.";

	@Autowired
	protected TransactionFormRepository transactionFormRepository;
	@Autowired
	protected TransactionFormMapper transactionFormMapper;

	@Override
	public TransactionFormDTO getFormData(Integer workflowItemId) {
		return transactionFormMapper.asTransactionFormDTO(transactionFormRepository.findByWorkflowItemId(workflowItemId));
	}

	@Override
	public TransactionFormDTO updateFormData(TransactionFormDTO transactionForm, String stepWorkflow) {
		Assert.notNull(transactionForm, TRANSACTION_FORM_CANNOT_BE_NULL);

		TransactionFormEntity transactionFormEntity = transactionFormMapper.asTransactionFormEntity(transactionForm);
		return transactionFormMapper.asTransactionFormDTO(transactionFormRepository.save(transactionFormEntity));
	}

	@Override
	public TransactionFormDTO abort(TransactionFormDTO transactionForm) {

		transactionForm.setStatusCd(OperationStatus.ABORTED.name());
		TransactionFormEntity transactionFormEntity = transactionFormMapper.asTransactionFormEntity(transactionForm);

		transactionFormRepository.save(transactionFormEntity);

		return transactionForm;
	}

	@Override
	public Boolean updateStatus(Integer workflowItemId, OperationStatus status) {
		Assert.notNull(workflowItemId);
		Assert.notNull(status);

		TransactionFormEntity entity = transactionFormRepository.findByWorkflowItemId(workflowItemId);
		entity.setStatusCd(status.name());
		transactionFormRepository.save(entity);

		return Boolean.TRUE;
	}

}
