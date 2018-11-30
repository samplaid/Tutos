package lu.wealins.webia.core.service.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.WorkflowUserDTO;
import lu.wealins.common.dto.webia.services.enums.OperationStatus;
import lu.wealins.webia.core.service.LiabilityFormDataService;
import lu.wealins.webia.core.service.LiabilityStepService;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.MetadataService;
import lu.wealins.webia.core.service.WebiaCheckStepService;
import lu.wealins.webia.core.service.WebiaRejectDataService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityStepServiceImpl implements LiabilityStepService {


	private static final String C_ACP = "C_ACP";
	private static final String C_AC2 = "C_AC2";
	private static final String REJECT = "REJECT";
	private static final String LIABILITY_WORFLOW_SAVE_METADATA_STEP = "liability/workflow/saveMetadata";
	private static final String STEP_CANNOT_BE_NULL = "Step cannot be null.";
	@Autowired
	private LiabilityFormDataService liabilityFormDataService;
	@Autowired
	private MetadataService metadataService;
	@Autowired
	private RestClientUtils restClientUtils;
	@Autowired
	private WebiaCheckStepService checkStepService;
	@Autowired
	private WebiaWorkflowUserService workflowUserService;
	@Autowired
	private WebiaRejectDataService webiaRejectDataService;
	@Autowired
	private LiabilityWorkflowService workflowService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * lu.wealins.webia.core.service.LiabilityStepService#updateStep(lu.wealins.
	 * webia.ws.rest.dto.StepDTO, java.lang.String)
	 */
	@Override
	public StepDTO updateStep(StepDTO step, String usrId) {
		Assert.notNull(step, STEP_CANNOT_BE_NULL);

		// validateBeforeSave(step);
		//
		FormDataDTO formData = step.getFormData();
		if (formData != null) {
			formData = liabilityFormDataService.updateFormData(formData, step.getWorkflowItemTypeId(),
					step.getStepWorkflow(), usrId);
			step.setFormData(formData);
		}

		return step;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * lu.wealins.webia.core.service.LiabilityStepService#complete(lu.wealins.
	 * webia.ws.rest.dto.StepDTO, javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public StepDTO complete(StepDTO step, SecurityContext context) {
		Assert.notNull(step, STEP_CANNOT_BE_NULL);

		FormDataDTO formData = step.getFormData();

		String userId = workflowUserService.getUserId(context);
		if (formData != null) {
			updateFormDataStatus(step);

			formData = liabilityFormDataService.completeFormData(formData, step.getWorkflowItemTypeId(), step.getStepWorkflow(), userId);

			step.setFormData(formData);
		}

		return step;
	}

	@Override
	public void goToNextStep(StepDTO step, String userId) {
		workflowService.next(step.getWorkflowItemId(), step.getStepWorkflow(), userId);
	}

	@Override
	public StepDTO preComplete(StepDTO step, SecurityContext context, String userId) {
		Assert.notNull(step, STEP_CANNOT_BE_NULL);

		saveCheckStepsMetadata(step, userId);

		FormDataDTO formData = step.getFormData();

		if (formData != null) {
			formData = liabilityFormDataService.preCompleteFormData(formData, step.getWorkflowItemTypeId(), step.getStepWorkflow(), userId);

			step.setFormData(formData);
		}

		return step;
	}

	private void updateFormDataStatus(StepDTO step) {
		CheckStepDTO checkStep = checkStepService.getCheckStep(step, C_ACP);
		if (checkStep != null) {
			CheckDataDTO checkData = checkStep.getCheck().getCheckData();
			if (checkData != null && REJECT.equals(checkData.getDataValueText())) {
				step.getFormData().setStatusCd(OperationStatus.ABORTED.name());
			}
		}

		/*
		 * The response of C_AC2 is provided by the subscription workflow during
		 * the step "Acceptance BIS".
		 */
		CheckStepDTO c_ac2 = checkStepService.getCheckStep(step, C_AC2);
		if (c_ac2 != null) {
			CheckDataDTO checkData = c_ac2.getCheck().getCheckData();
			if (checkData != null && REJECT.equals(checkData.getDataValueText())) {
				step.getFormData().setStatusCd(OperationStatus.ABORTED.name());
			}
		}

	}

	private void saveCheckStepsMetadata(StepDTO step, String usrId) {
		List<MetadataDTO> metadata = metadataService.createMetadata(step.getCheckSteps());
		@SuppressWarnings("boxing")
		Long workflowItemId = new Long(step.getWorkflowItemId());

		WorkflowItemDataDTO workflowItemData = metadataService.createWorkflowItemData(workflowItemId, metadata, usrId);
		restClientUtils.post(LIABILITY_WORFLOW_SAVE_METADATA_STEP, workflowItemData, String.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * lu.wealins.webia.core.service.LiabilityStepService#previous(lu.wealins.
	 * webia.ws.rest.dto.StepDTO, javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public StepDTO previous(StepDTO step, SecurityContext context) {
		Assert.notNull(step, STEP_CANNOT_BE_NULL);
		FormDataDTO formData = step.getFormData();
		String userId = workflowUserService.getUserId(context);
		if (formData != null) {
			formData = liabilityFormDataService.previousFormData(formData, step.getWorkflowItemTypeId(),
					step.getStepWorkflow(), userId);
			WorkflowUserDTO workflowUser = workflowUserService.getWorkflowUser(context);
			webiaRejectDataService.createRejectData(step, workflowUser);
			step.setFormData(formData);
		}

		// Example : SUBSCRIPTION_ABORT
		workflowService.previous(step.getWorkflowItemId(), step.getStepWorkflow(), userId);

		return step;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * lu.wealins.webia.core.service.LiabilityStepService#abort(lu.wealins.webia
	 * .ws.rest.dto.StepDTO, javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public StepDTO abort(StepDTO step, SecurityContext context) {
		Assert.notNull(step, STEP_CANNOT_BE_NULL);
		String userId = workflowUserService.getUserId(context);

		liabilityFormDataService.abortFormData(step.getWorkflowItemTypeId(), step.getFormData());

		workflowService.abort(step.getWorkflowItemId(), userId);

		return step;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * lu.wealins.webia.core.service.LiabilityStepService#enrichStep(lu.wealins.
	 * webia.ws.rest.dto.StepDTO, java.lang.String)
	 */
	@Override
	public StepDTO enrichStep(StepDTO step, String userId) {
		Assert.notNull(step, STEP_CANNOT_BE_NULL);
		FormDataDTO formData = step.getFormData();
		if (formData != null) {
			formData = liabilityFormDataService.enrichFormData(formData, step.getWorkflowItemTypeId(),
					step.getStepWorkflow(), userId);
			step.setFormData(formData);
		}

		setupRejected(step, userId);

		return step;
	}

	/**
	 * Step has been rejected if the rejected comment is not null.
	 * 
	 * @param userId
	 *            The user id.
	 * @param context
	 *            The security context.
	 */
	private void setupRejected(StepDTO step, String userId) {
		Integer workflowItemId = step.getWorkflowItemId();
		if (workflowItemId != null) {
			MetadataDTO metadata = metadataService.getMetadata(workflowItemId + "",
					Metadata.REJECTED_COMMENT.getMetadata(), userId);
			step.setRejected(new Boolean(metadata != null && metadata.getValue() != null));
		}
	}
}
