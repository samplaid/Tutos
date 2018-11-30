package lu.wealins.webia.core.service.impl;

import static lu.wealins.common.dto.liability.services.enums.Metadata.SECURITIES_TRANSFER;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.PolicySurrenderDTO;
import lu.wealins.common.dto.webia.services.CreateEditingRequest;
import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.mapper.SurrenderMapper;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.service.LiabilityFundTransactionService;
import lu.wealins.webia.core.service.LiabilityPolicyService;

@Service(value = "LiabilitySurrenderService")
public class LiabilitySurrenderServiceImpl extends LiabilityMoneyOutService {

	@Autowired
	private AbstractFundFormService<FundTransactionFormDTO> fundFormService;

	@Autowired
	private LiabilityPolicyService liabilityPolicyService;

	@Autowired
	private SurrenderMapper surrenderMapper;
	
	@Autowired
	private EditingService editingService;

	@Autowired
	private LiabilityFundTransactionService liabilityFundTransactionService;

	@Override
	public TransactionFormDTO completeFormData(TransactionFormDTO transactionForm, String stepWorkflow, String usrId) {
		super.completeFormData(transactionForm, stepWorkflow, usrId);

		switch (StepTypeDTO.getStepType(stepWorkflow)) {

		case AWAITING_PUT_IN_FORCE:
			PolicySurrenderDTO surrenderDTO = surrenderMapper.asPolicySurrenderDTO(transactionForm);
			liabilityPolicyService.surrenderPolicy(surrenderDTO);
			liabilityFundTransactionService.executeFundTransactionValuation(transactionForm, usrId);
			break;
		case VALIDATE_ANALYSIS:
			generateEndOfManagmentMandateForEachFund(transactionForm);
			Integer workflowItemId = transactionForm.getWorkflowItemId();
			createAssetManagerDocument(workflowItemId);
			break;
		case GENERATE_DOCUMENTATION:
			editingService.createWorkflowDocumentRequest(Long.valueOf(transactionForm.getWorkflowItemId()), DocumentType.TOTAL_SURRENDER, false, false);
			break;
		default:
			break;
		}

		return transactionForm;
	}

	@Override
	public TransactionFormDTO preCompleteFormData(TransactionFormDTO transactionForm, String stepWorkflow, String usrId) {

		TransactionFormDTO transactionFormTarget = super.preCompleteFormData(transactionForm, stepWorkflow, usrId);
		switch (StepTypeDTO.getStepType(stepWorkflow)) {

		case NEW:
			initTransactionForm(transactionFormTarget);
			break;
		default:
			break;
		}

		return transactionFormTarget;
	}

	private void initTransactionForm(TransactionFormDTO transactionForm) {
		String updateUser = transactionForm.getUpdateUser();
		if (updateUser == null) {
			updateUser = transactionForm.getCreationUser();
		}
		if (metadataService.isYes(transactionForm.getWorkflowItemId(), SECURITIES_TRANSFER, updateUser)) {
			Collection<TransferDTO> securitiesTransfer = new ArrayList<>();
			fundFormService.getFidOrFas(transactionForm.getFundTransactionForms()).forEach(x -> {

				TransferDTO transfer = transferService.initSecuritiesTransferForSurrender(transactionForm.getWorkflowItemId(), x.getFund());

				securitiesTransfer.add(transfer);
			});
			transactionForm.setSecuritiesTransfer(securitiesTransfer);
		}
	}

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.SURRENDER;
	}

	@Override
	protected boolean isCps1Step(StepTypeDTO stepType) {
		return StepTypeDTO.SURRENDER_CPS1_GROUP.contains(stepType);
	}

	@Override
	protected boolean isCps2Step(StepTypeDTO stepType) {
		return StepTypeDTO.SURRENDER_CPS2_GROUP.contains(stepType);
	}
	
	private void generateEndOfManagmentMandateForEachFund(TransactionFormDTO transactionForm){
		Long workflowItemId = Long.valueOf(transactionForm.getWorkflowItemId());
		String policyId = transactionForm.getPolicyId();
		Collection<CreateEditingRequest> createEditingRequests = new ArrayList<CreateEditingRequest>();
		
		transactionForm.getFundTransactionForms().forEach(ftf -> {
			if ( !StringUtils.isBlank(ftf.getFundTp()) && ("FID".equals(ftf.getFundTp()) || "FAS".equals(ftf.getFundTp()))){
				CreateEditingRequest cer = new CreateEditingRequest();
				cer.setWorkflowItemId(workflowItemId);
				cer.setPolicy(policyId);
				cer.setFund(ftf.getFundId());
				cer.setSynchronous(false);
				cer.setDocumentType(DocumentType.MANAGEMENT_MANDATE_END);
				createEditingRequests.add(cer);
			}
		});
		if (CollectionUtils.isNotEmpty(createEditingRequests)){
			editingService.createEditingRequests(createEditingRequests);
		}
	}

}
