package lu.wealins.webia.core.service.impl;

import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.common.dto.webia.services.WorkflowUserDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.MetadataService;
import lu.wealins.webia.core.service.WebiaWorkflowQueueService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.webia.core.service.WorkflowItemDataService;
import lu.wealins.webia.core.service.helper.metadata.CpsMetadataHelper;
import lu.wealins.webia.core.service.impl.form.data.WorkflowFormService;
import lu.wealins.webia.core.utils.RestClientUtils;

public abstract class AbstractWorkflowFormService<T extends FormDataDTO> implements WorkflowFormService<T> {

	private final Logger LOGGER = LoggerFactory.getLogger(AbstractWorkflowFormService.class);

	@Autowired
	protected RestClientUtils restClientUtils;

	@Autowired
	protected MetadataService metadataService;

	@Autowired
	private CpsMetadataHelper cpsMetadataHelper;

	@Autowired
	private WebiaWorkflowUserService workflowUserService;

	@Autowired
	private WebiaWorkflowQueueService workflowQueueService;

	@Autowired
	protected LiabilityWorkflowService workflowService;

	@Autowired
	protected WorkflowItemDataService workflowItemService;

	protected abstract WorkflowItemDataDTO getCompleteMetadata(T formData, String usrId, StepTypeDTO stepType);

	protected abstract StepTypeDTO getFirstStep();

	@Override
	public T completeFormData(T formData, String stepWorkflow, String usrId) {
		saveMetaData(formData, stepWorkflow, usrId);
		return formData;
	}

	@Override
	public T preCompleteFormData(T formData, String stepWorkflow, String usrId) {

		return formData;
	}

	@Override
	public T previousFormData(T appForm, String stepWorkflow, String usrId) {
		workflowService.saveMetada(createRejectCommentMetadata(appForm, usrId));
		return appForm;
	}

	public WorkflowItemDataDTO createRejectCommentMetadata(T appForm, String usrId) {
		WorkflowUserDTO workflowUser = workflowUserService.getWorkflowUser(usrId);

		if (workflowUser == null) {
			LOGGER.warn("There is no workflow user for the user id {} ", usrId);
			return null;
		}

		String name = StringUtils.isNotBlank(workflowUser.getName0()) ? workflowUser.getName0() : workflowUser.getLoginId();

		MetadataDTO rejectedCommentMetadata = metadataService.createMetadata(Metadata.REJECTED_COMMENT.getMetadata(), "Rejected by " + name + ".");
		Long workflowItemId = new Long(appForm.getWorkflowItemId().intValue());

		return metadataService.createWorkflowItemData(workflowItemId, usrId, rejectedCommentMetadata);
	}

	private void saveMetaData(T formData, String stepWorkflow, String usrId) {
		WorkflowItemDataDTO workflowItemDataDTO = createWorkflowItemData(formData, stepWorkflow, usrId);
		workflowService.saveMetada(workflowItemDataDTO);
	}

	private WorkflowItemDataDTO createWorkflowItemData(T formData, String stepWorkflow, String usrId) {
		StepTypeDTO stepType = StepTypeDTO.getStepType(stepWorkflow);

		WorkflowItemDataDTO workflowItemData = getCompleteMetadata(formData, usrId, stepType);

		Long workflowItemId = new Long(formData.getWorkflowItemId());
		// reset previous comment metadata
		addMetadata(Metadata.REJECTED_COMMENT.getMetadata(), workflowItemData, workflowItemId, usrId, null);
		updateCpsUser(workflowItemData, stepType, workflowItemId, usrId);

		return workflowItemData;
	}

	/**
	 * During the subscription, a step can be re-assign to a new user, it is necessary to update the CPS users according to the step.
	 * 
	 * @param usrId The user id.
	 * @param stepType The step type.
	 * @param workflowItemData The workflow item data.
	 */
	private void updateCpsUser(WorkflowItemDataDTO workflowItemData, StepTypeDTO stepType, Long workflowItemId, String usrId) {
		// If the user has no personal queue then we skip the CPS user update otherwise the workflow won't be available in E-Lissia.
		boolean hasPersonalQueue = BooleanUtils.isTrue(workflowQueueService.hasPersonalQueue(usrId));
		if (!hasPersonalQueue) {
			return;
		}

		boolean isSuperUser = workflowUserService.isSuperUser(usrId);

		if (isCps1Step(stepType)) {

			String cpsUser = cpsMetadataHelper.getFirstCpsUser(workflowItemId + "", usrId);

			// if the current user is a super user and the CpsUser has been already set then we don't update this one.
			if (isSuperUser && StringUtils.isNotBlank(cpsUser)) {
				return;
			}

			List<MetadataDTO> metadata = cpsMetadataHelper.createFirstCpsMetadata(usrId);
			addMetadata(workflowItemData, workflowItemId, usrId, metadata);
		} else if (isCps2Step(stepType)) {
			String cpsUser = cpsMetadataHelper.getSecondCpsUser(workflowItemId + "", usrId);
			// if the current user is a super user and the CpsUser has been already set then we don't update this one.
			if (isSuperUser && StringUtils.isNotBlank(cpsUser)) {
				return;
			}

			List<MetadataDTO> metadata = cpsMetadataHelper.createSecondCpsMetadata(usrId);
			addMetadata(workflowItemData, workflowItemId, usrId, metadata);
		}
	}

	protected abstract boolean isCps1Step(StepTypeDTO stepType);

	protected abstract boolean isCps2Step(StepTypeDTO stepType);

	private void addMetadata(String metadata, WorkflowItemDataDTO workflowItemData, Long workflowItemId, String usrId, String value) {
		enrichWorkflowItem(workflowItemData, workflowItemId, usrId);
		metadataService.addMetadata(workflowItemData, metadata, value);
	}

	private void addMetadata(WorkflowItemDataDTO workflowItemData, Long workflowItemId, String usrId, List<MetadataDTO> metadataList) {
		enrichWorkflowItem(workflowItemData, workflowItemId, usrId);
		metadataService.addMetadata(workflowItemData, metadataList);
	}

	private void enrichWorkflowItem(WorkflowItemDataDTO workflowItemData, Long workflowItemId, String usrId) {
		if (workflowItemData.getWorkflowItemId() == null) {
			workflowItemData.setWorkflowItemId(workflowItemId);
		}
		if (workflowItemData.getUsrId() == null) {
			workflowItemData.setUsrId(usrId);
		}
	}

	@Override
	public T updateFormData(T formData, String stepWorkflow, String userId) {

		createMetadataForElissiaSearcher(formData, stepWorkflow, userId);

		return formData;
	}

	/**
	 * Create the common metadata in order to display them in the E-Lissia Workflow page.
	 * 
	 * @param formData The form data.
	 * @param userId The user id.
	 */
	protected void createMetadataForElissiaSearcher(T formData, String stepWorkflow, String userId) {

		StepTypeDTO stepType = StepTypeDTO.getStepType(stepWorkflow);
		if (!getFirstStep().equals(stepType)) {
			return;
		}

		String policyId = formData.getPolicyId();
		if (StringUtils.isBlank(policyId)) {
			return;
		}

		WorkflowItemDataDTO workflowItemData = createCommonMetadata(formData, userId, policyId);
		if (workflowItemData != null) {
			workflowService.saveMetada(workflowItemData);
		}
	}

	protected WorkflowItemDataDTO createCommonMetadata(T formData, String userId, String policyId) {
		return workflowItemService.createCommonMetadata(policyId, new Long(formData.getWorkflowItemId().longValue()), userId);
	}


}
