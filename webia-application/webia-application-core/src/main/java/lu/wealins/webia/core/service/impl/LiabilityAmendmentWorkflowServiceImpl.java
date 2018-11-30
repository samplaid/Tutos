package lu.wealins.webia.core.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.AmendmentRequest;
import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.PolicyChangeDTO;
import lu.wealins.common.dto.liability.services.PolicyChangeRequest;
import lu.wealins.common.dto.liability.services.enums.PolicyChangeStatus;
import lu.wealins.common.dto.liability.services.enums.PolicyChangeType;
import lu.wealins.common.dto.webia.services.AmendmentFormDTO;
import lu.wealins.common.dto.webia.services.WorkflowUserDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.core.service.LiabilitPolicyChangeService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.webia.core.service.helper.metadata.CpsMetadataHelper;

public abstract class LiabilityAmendmentWorkflowServiceImpl<T extends AmendmentFormDTO>
		extends AbstractWorkflowFormService<T> {

	private static final String WORKFLOW_ITEM_ID_MUST_SET_IN_THE_FORM_DATA = "Workflow item id must set in the form data.";
	private static final String FORM_DATA_CANNOT_BE_NULL = "Form data cannot be null.";

	@Autowired
	private CpsMetadataHelper cpsMetadataHelper;

	@Autowired
	private WebiaWorkflowUserService webiaWorkflowUserService;

	@Autowired
	protected LiabilitPolicyChangeService policyChangeService;

	@Override
	public T updateFormData(T formData, String stepWorkflow, String userId) {
		super.updateFormData(formData, stepWorkflow, userId);

		StepTypeDTO stepType = StepTypeDTO.getStepType(stepWorkflow);

		if (stepType != null && stepType.isAfterOrEquals(StepTypeDTO.AWAITING_ACTIVATION)) {
			return formData;
		}

		if (StringUtils.isBlank(formData.getFirstCpsUser())) {
			WorkflowUserDTO user = webiaWorkflowUserService.getAssignedUser(formData.getWorkflowItemId().toString(),
					userId);
			formData.setFirstCpsUser(user.getUsrId());
		}

		if (StringUtils.isNotEmpty(formData.getPolicyId())) {
			PolicyChangeRequest policyChangeRequest = createPolicyChangeRequest(formData, PolicyChangeStatus.PENDING);
			policyChangeService.saveChanges(policyChangeRequest);
		}

		updateMetadata(formData, userId);

		return formData;
	}

	private void updateMetadata(T formData, String userId) {

		List<MetadataDTO> cpsMetadata = cpsMetadataHelper.createFirstCpsMetadata(formData.getFirstCpsUser());
		cpsMetadata.addAll(cpsMetadataHelper.createSecondCpsMetadata(formData.getSecondCpsUser()));
		workflowService.saveMetada(new Long(formData.getWorkflowItemId()), userId, cpsMetadata);
	}

	@Override
	public T enrichFormData(T formData, String stepWorkflow, String userId) {
		Assert.notNull(formData, FORM_DATA_CANNOT_BE_NULL);
		Assert.notNull(formData.getWorkflowItemId(), WORKFLOW_ITEM_ID_MUST_SET_IN_THE_FORM_DATA);
		formData.setFirstCpsUser(cpsMetadataHelper.getFirstCpsUser(formData.getWorkflowItemId() + "", userId));
		formData.setSecondCpsUser(cpsMetadataHelper.getSecondCpsUser(formData.getWorkflowItemId() + "", userId));

		PolicyChangeDTO policyChange = policyChangeService.getPolicyChange(formData.getWorkflowItemId());
		if (policyChange != null) {
			formData.setChangeDate(policyChange.getDateOfChange());
			formData.setPolicyId(policyChange.getPolicyId());
		}

		return formData;
	}

	@Override
	public void abortFormData(T formData) {
		policyChangeService.cancelByWorkflowItemId(formData.getWorkflowItemId());
	}

	@Override
	public T completeFormData(T formData, String stepWorkflow, String usrId) {
		super.completeFormData(formData, stepWorkflow, usrId);

		if (StringUtils.isNotEmpty(formData.getPolicyId())) {
			PolicyChangeRequest policyChangeRequest = createPolicyChangeRequest(formData, PolicyChangeStatus.IN_FORCE);
			policyChangeService.saveChanges(policyChangeRequest);
		}

		return formData;
	}

	protected AmendmentRequest createAmendmentRequest(String policyId, Integer workflowItemId) {
		AmendmentRequest request = new AmendmentRequest();

		request.setPolicyId(policyId);
		request.setWorkflowItemId(workflowItemId);

		return request;
	}

	private PolicyChangeRequest createPolicyChangeRequest(T formData, PolicyChangeStatus status) {
		PolicyChangeRequest policyChangeRequest = new PolicyChangeRequest();

		policyChangeRequest.setDateOfChange(formData.getChangeDate());
		policyChangeRequest.setPolicyId(formData.getPolicyId());
		policyChangeRequest.setStatus(status);
		policyChangeRequest.setWorkflowItemId(formData.getWorkflowItemId());
		policyChangeRequest.setType(PolicyChangeType.from(getSupportedWorkflowType()));

		return policyChangeRequest;
	}

	// TODO : The reassign was used for subscription and additional premium so far, check with AMC if it should be used for other operations.
	@Override
	protected boolean isCps1Step(StepTypeDTO stepType) {
		return false;
	}

	// TODO : The reassign was used for subscription and additional premium so far, check with AMC if it should be used for other operations.
	@Override
	protected boolean isCps2Step(StepTypeDTO stepType) {
		return false;
	}

	@Override
	protected StepTypeDTO getFirstStep() {
		return StepTypeDTO.ANALYSIS;
	}

}
