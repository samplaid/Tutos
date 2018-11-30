package lu.wealins.webia.ws.rest.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemAllActionsDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.StepLightDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.annotation.CheckStepUpdateAccess;
import lu.wealins.webia.core.annotation.UpdateStepAccess;
import lu.wealins.webia.core.service.LiabilityStepService;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.MetadataService;
import lu.wealins.webia.core.service.WebiaStepService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.webia.core.service.impl.DocumentReportStepFacade;
import lu.wealins.webia.core.service.impl.workflow.header.WorkflowHeaderUtilityService;
import lu.wealins.webia.core.service.validations.builder.WorkflowValidationBuilderService;
import lu.wealins.webia.ws.rest.WebiaStepRESTService;
import lu.wealins.webia.ws.rest.impl.exception.ReportExceptionHelper;
import lu.wealins.webia.ws.rest.impl.exception.StepException;

@Component
public class WebiaStepRESTServiceImpl implements WebiaStepRESTService
{

	private static final String REJECT_REASON_CANNOT_BE_NULL = "Reject reason cannot be null.";

	@Autowired
	private WebiaStepService webiaStepService;

	@Autowired
	private LiabilityStepService liabilityStepService;

	@Autowired
	private LiabilityWorkflowService workflowService;

	@Autowired
	private WebiaWorkflowUserService workflowUserService;


	@Autowired
	private WorkflowHeaderUtilityService workflowHeaderUtilityService;

	@Autowired
	DocumentReportStepFacade DocumentReportService;

	@Autowired
	private WorkflowValidationBuilderService workflowValidationBuilderService;

	@Autowired
	private MetadataService metadataService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.WebiaStepRESTService#getStep(javax.ws.rs.core.SecurityContext, java.lang.Integer, java.lang.String)
	 */
	@Override
	@UpdateStepAccess
	public StepDTO getStep(SecurityContext context, Integer workflowItemId, String stepWorkflow) {
		Assert.notNull(workflowItemId, "the workflow item id can't be null");

		String userId = workflowUserService.getUserId(context);

		String stepWorkflowName = getStepWorkflowName(workflowItemId, stepWorkflow, userId);

		WorkflowItemDataDTO workflowItem = workflowService.getWorkflowItem(workflowItemId.toString(), userId);
		// Load the screen data and checks
		StepDTO step = webiaStepService.getStep(workflowItemId, stepWorkflowName, workflowItem.getWorkflowItemTypeId());

		step = liabilityStepService.enrichStep(step, userId);

		setupStepWorkflowStatus(workflowItemId, userId, step);
		workflowHeaderUtilityService.setupHeader(step, userId);

		return step;
	}

	private void setupStepWorkflowStatus(Integer workflowItemId, String userId, StepDTO step) {
		if (workflowItemId != null) {
			WorkflowGeneralInformationDTO workFlowInfo = workflowService.getWorkflowGeneralInformation("" + workflowItemId, userId);
			if (workFlowInfo != null && StringUtils.hasText(workFlowInfo.getStatus())) {
				step.setWorkFlowStatus(Integer.parseInt(workFlowInfo.getStatus()));
			}
		}
	}

	private String getStepWorkflowName(Integer workflowItemId, String stepWorkflow, String userId) {
		String action = stepWorkflow;
		// Override the current action, should we check the step (action) name ?
		if (!StringUtils.hasText(stepWorkflow) || stepWorkflow.equalsIgnoreCase("undefined")) {
			// Load the current, fulfilled and future actions of the workflow item.
			WorkflowItemAllActionsDTO actions = workflowService.getWorkflowItemAllActions(workflowItemId, userId);
			// Current workflow item action
			action = actions.getCurrent().getAction();
		}

		return action;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.WebiaStepRESTService#update(javax.ws.rs.core.SecurityContext, lu.wealins.webia.ws.rest.dto.StepDTO)
	 */
	@Override
	@CheckStepUpdateAccess
	@UpdateStepAccess
	public StepDTO update(SecurityContext context, StepDTO step) {
		validateBeforeSave(step);
		String userId = workflowUserService.getUserId(context);
		StepDTO stepDTO = liabilityStepService.updateStep(step, userId);
		stepDTO = webiaStepService.update(stepDTO);

		// after the save in webia-service we have loose enriched information.
		stepDTO = liabilityStepService.enrichStep(stepDTO, userId);

		workflowHeaderUtilityService.setupHeader(stepDTO, userId);

		return stepDTO;
	}

	private void validateBeforeComplete(StepDTO step) {
		List<String> errors = new ArrayList<String>();

		workflowValidationBuilderService.getValidationStepServicesForBeforeComplete(step).forEach(x -> errors.addAll(x.validateBeforeComplete(step)));

		ReportExceptionHelper.throwIfErrorsIsNotEmpty(errors, step, StepException.class);
	}

	private void validateBeforeSave(StepDTO step) {
		List<String> errors = new ArrayList<String>();

		workflowValidationBuilderService.getValidationStepServicesForBeforeSave(step).forEach(x -> errors.addAll(x.validateBeforeSave(step)));

		ReportExceptionHelper.throwIfErrorsIsNotEmpty(errors, step, StepException.class);
	}

	@Override
	public StepDTO complete(SecurityContext context, Integer workflowItemId) {
		return complete(context, getStep(context, workflowItemId, null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.WebiaStepRESTService#complete(javax.ws.rs.core.SecurityContext, lu.wealins.webia.ws.rest.dto.StepDTO)
	 */
	@Override
	@CheckStepUpdateAccess
	public StepDTO complete(SecurityContext context, StepDTO step) {
		StepDTO stepDTO = step;
		
		// Update before complete
		stepDTO = update(context, step);

		// We validate all service layers before to complete them.
		validateBeforeComplete(stepDTO);

		String userId = workflowUserService.getUserId(context);
		// Use to make an operation in LISSIA before the complete in webia-service
		stepDTO = liabilityStepService.preComplete(stepDTO, context, userId);

		// save step
		stepDTO = webiaStepService.complete(stepDTO);
		// after the save in webia-service we have loose enriched information.
		stepDTO = liabilityStepService.enrichStep(stepDTO, userId);
		
		generateReport(context, stepDTO);
		// save MetaData. Exception: Metatadata on the questionnary is saved in pre-Complete
		stepDTO = liabilityStepService.complete(stepDTO, context);

		// Use to make an operation in WEBIA before to go to the next step. (example : update status)
		stepDTO = webiaStepService.postComplete(stepDTO);

		// Push to next step
		liabilityStepService.goToNextStep(step, userId);

		if (isStatusHasBeenUpdated(step, stepDTO)) {
			return update(context, stepDTO);
		}

		workflowHeaderUtilityService.setupHeader(stepDTO, userId);

		return stepDTO;
	}


	private boolean isStatusHasBeenUpdated(StepDTO stepIn, StepDTO stepOut) {
		return stepIn.getFormData().getStatusCd() != null && !stepIn.getFormData().getStatusCd().equals(stepOut.getFormData().getStatusCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.WebiaStepRESTService#previous(javax.ws.rs.core.SecurityContext, lu.wealins.webia.ws.rest.dto.StepDTO)
	 */
	@Override
	@CheckStepUpdateAccess
	public StepDTO previous(SecurityContext context, StepDTO step) {
		Assert.isTrue(step != null && StringUtils.hasText(step.getRejectReason()), REJECT_REASON_CANNOT_BE_NULL);
		return liabilityStepService.previous(step, context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.WebiaStepRESTService#abort(javax.ws.rs.core.SecurityContext, lu.wealins.webia.ws.rest.dto.StepDTO)
	 */
	@Override
	@CheckStepUpdateAccess
	public StepDTO abort(SecurityContext context, StepDTO step) {
		StepDTO abortedStep = liabilityStepService.abort(step, context);
		return webiaStepService.abort(abortedStep);
	}

	@Override
	public Collection<StepLightDTO> getStepsByWorkflowItemTypeId(SecurityContext context, Integer workflowItemTypeId) {
		return webiaStepService.getStepsByWorkflowItemTypeId(workflowItemTypeId);
	}

	private void generateReport(SecurityContext context, StepDTO stepDTO) {
		Integer souscriptionWorkFlowType = new Integer(7);
		if (stepDTO != null && souscriptionWorkFlowType.compareTo(stepDTO.getWorkflowItemTypeId()) == 0) {
			FormDataDTO formData = stepDTO.getFormData();
			if (formData instanceof AppFormDTO) {
				AppFormDTO appform = (AppFormDTO) formData;
				DocumentReportService.generateReport(context, appform);
			}
		}
	}

	@Override
	public Boolean canGenerateAcceptanceDocument(SecurityContext context, Integer workflowItemId) {

		// retrieve all the data and metadata
		String user = workflowUserService.getUserId(context);
		WorkflowType workflowType = WorkflowType.getType(workflowService.getWorkflowItem(workflowItemId.toString(), user).getWorkflowItemTypeId());
		StepTypeDTO currentStep = StepTypeDTO.getStepType(workflowService.getWorkflowGeneralInformation(workflowItemId.toString(), user).getCurrentStepName());
		boolean isAcceptanceNeeded = Boolean.getBoolean(metadataService.getMetadata(workflowItemId.toString(), Metadata.STEP_ACCEPTANCE.name(), user).getValue());

		if (!isAcceptanceNeeded) {
			return Boolean.FALSE;
		}

		switch (workflowType) {
		case WITHDRAWAL:
			boolean hasFeOrFic = Boolean.getBoolean(metadataService.getMetadata(workflowItemId.toString(), Metadata.FE_FIC.name(), user).getValue());
			if (!hasFeOrFic) {
				return Boolean.valueOf(currentStep.isAfterOrEquals(StepTypeDTO.ACCEPTANCE));
			}
			return Boolean.valueOf(currentStep.isAfterOrEquals(StepTypeDTO.REQUEST_TO_CLIENT_PARTNER));
		default:
			return Boolean.valueOf(currentStep.isAfterOrEquals(StepTypeDTO.ACCEPTANCE));
		}
	}

}
