package lu.wealins.webia.services.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.CheckWorkflowDTO;
import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.StepLightDTO;
import lu.wealins.webia.services.core.mapper.StepMapper;
import lu.wealins.webia.services.core.persistence.entity.StepEntity;
import lu.wealins.webia.services.core.persistence.repository.ScoreBCFTRepository;
import lu.wealins.webia.services.core.persistence.repository.StepRepository;
import lu.wealins.webia.services.core.predicates.CheckStepVisibilityEvaluator;
import lu.wealins.webia.services.core.service.CheckDataService;
import lu.wealins.webia.services.core.service.CheckStepService;
import lu.wealins.webia.services.core.service.FormDataService;
import lu.wealins.webia.services.core.service.StepService;
import lu.wealins.webia.services.core.service.validations.builder.WorkflowValidationBuilderService;

@Service
public class StepServiceImpl implements StepService {

	private static final String WORKFLOW_ITEM_ID_CANNOT_BE_NULL = "Workflow item id cannot be null.";
	private static final String STEP_WORKFLOW_CANNOT_BE_NULL = "Step id cannot be null.";
	private static final String STEP_CANNOT_BE_NULL = "Step cannot be null.";
	private static final String SCORE = "SCORE";

	@Autowired
	private StepRepository stepRepository;

	@Autowired
	private StepMapper stepMapper;

	@Autowired
	private FormDataService formDataService;

	@Autowired
	private CheckDataService checkDataService;

	@Autowired
	private CheckStepService checkStepService;

	@Autowired
	private ScoreBCFTRepository scoreBCFTRepository;
	
	@Autowired
	private WorkflowValidationBuilderService workflowValidationBuilderService;

	@Autowired
	private CheckStepVisibilityEvaluator checkStepVisibilityEvaluator;

	@Override
	public StepLightDTO getStep(Integer stepId) {
		Assert.notNull(stepId, STEP_CANNOT_BE_NULL);
		StepEntity step = stepRepository.findOne(stepId);
		Assert.notNull(step, STEP_CANNOT_BE_NULL);
		return stepMapper.asStepLightDTO(step);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.StepService#getStep(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public StepDTO getStep(Integer workflowItemId, String stepWorkflow, Integer workflowItemTypeId) {

		Assert.notNull(stepWorkflow, STEP_WORKFLOW_CANNOT_BE_NULL);
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);

		// Load the STEP and CHECK_STEP entities by the workflow action name.
		StepEntity step = stepRepository.findFirstByStepWorkflowIgnoreCaseAndWorkflowItemTypeIdOrderByStepIdAsc(stepWorkflow, workflowItemTypeId);
		Assert.notNull(step, STEP_CANNOT_BE_NULL);

		StepDTO stepDTO = stepMapper.asStepDTO(step);
		stepDTO.setWorkflowItemId(workflowItemId);

		// Load the check responses by the step id and work item id.
		checkStepService.addCheckDataAtCheckStepLevel(workflowItemId, stepDTO);

		// Load the form data by workflow item id
		addformData(stepDTO);

		// Check filter
		filterQuestionsWithVisibleIf(stepDTO);

		return stepDTO;
	}
	

	@Override
	public Collection<CheckStepDTO> getCheckSteps(Integer workflowItemId, String stepWorkflow, Integer workflowItemTypeId) {

		Assert.notNull(stepWorkflow, STEP_WORKFLOW_CANNOT_BE_NULL);
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);

		// Load the STEP and CHECK_STEP entities by the workflow action name.
		StepEntity step = stepRepository.findFirstByStepWorkflowIgnoreCaseAndWorkflowItemTypeIdOrderByStepIdAsc(stepWorkflow, workflowItemTypeId);
		Assert.notNull(step, STEP_CANNOT_BE_NULL);

		StepDTO stepDTO = stepMapper.asStepDTO(step);
		stepDTO.setWorkflowItemId(workflowItemId);

		// Load the check responses by the step id and work item id.
		checkStepService.addCheckDataAtCheckStepLevel(workflowItemId, stepDTO);

		// Check filter
		filterQuestionsWithVisibleIf(stepDTO);

		return stepDTO.getCheckSteps();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.StepService#updateStep(lu.wealins.common.dto.webia.services.StepDTO)
	 */
	@Override
	public StepDTO updateStep(StepDTO step) {
		Assert.notNull(step, STEP_CANNOT_BE_NULL);

		// update answers
		updateCheckDataAtCheckStepLevel(step);
		setupScore(step);
		updateformData(step);

		return step;
	}
	
	
	/* (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.StepService#abortStep(lu.wealins.common.dto.webia.services.StepDTO)
	 */
	@Override
	public StepDTO abortStep(StepDTO step) {
		Assert.notNull(step, STEP_CANNOT_BE_NULL);
		
		FormDataDTO formData = step.getFormData();
		if (formData != null) {
			step.setFormData(formDataService.abort(formData, step.getStepWorkflow(), step.getWorkflowItemTypeId()));
		}		
		
		return step;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.StepService#validateBeforeSave(lu.wealins.common.dto.webia.services.StepDTO)
	 */
	@Override
	public List<String> validateBeforeSave(StepDTO step) {

		List<String> errors = new ArrayList<String>();

		workflowValidationBuilderService.getValidationStepServicesForBeforeSave(step).forEach(x -> errors.addAll(x.validateBeforeSave(step)));

		return errors;
	}

	private void setupScore(StepDTO step) {
		CheckStepDTO checkStep = checkStepService.getCheckStep(step, SCORE);

		if (checkStep != null) {
			CheckWorkflowDTO check = checkStep.getCheck();

			// if a SCORE check_Workflow exists, then calculate the score to save it
			if (SCORE.equals(check.getCheckCode())) {
				CheckDataDTO checkData = check.getCheckData();
				if (checkData == null) {
					checkData = checkDataService.createCheckData(check.getCheckId(), step.getWorkflowItemId());
				}
				Integer score = scoreBCFTRepository.computeScore(step.getWorkflowItemId());
				checkData.setDataValueNumber(score != null ? BigDecimal.valueOf(score) : null);
				
				check.setCheckData(checkDataService.update(checkData));

				// hack : update the score for the new business because the Lissia webservice use this data in order to create/update the policy score.
				if (step.getFormData() instanceof AppFormDTO) {
					((AppFormDTO) step.getFormData()).setScore(score);
			}

		}
	}
	}

	/**
	 * Filter questions on visibleIF
	 * 
	 * @param step The step
	 */
	// TODO: Some filtering is done in the client side based on the form...etc.
	// We should centralize all the filtering here and expose by REST on form events.
	private void filterQuestionsWithVisibleIf(StepDTO step) {
		Collection<CheckStepDTO> initialCheckSteps = step.getCheckSteps();
		Collection<CheckStepDTO> filteredCheckSteps = checkStepVisibilityEvaluator.filterVisbility(initialCheckSteps, step.getWorkflowItemId());
		step.setCheckSteps(filteredCheckSteps);
	}

	private void updateCheckDataAtCheckStepLevel(StepDTO step) {

		// Update each checkStep
		for (CheckStepDTO questionByStep : step.getCheckSteps()) {

			// Do not update the response when the question is not updatable on the current step.
			if (BooleanUtils.isTrue(questionByStep.getIsUpdatable())
					&& questionByStep.getCheck() != null) {
				CheckWorkflowDTO question = questionByStep.getCheck();

				if (question.getCheckData() != null) {
					question.setCheckData(checkDataService.update(question.getCheckData()));
				}
			}
		}
	}

	private void updateformData(StepDTO step) {
		FormDataDTO formData = step.getFormData();
		if (formData != null) {
			step.setFormData(formDataService.updateFormData(formData, step.getStepWorkflow(), step.getWorkflowItemTypeId()));
		}
	}

	private void addformData(StepDTO step) {
		FormDataDTO formData = formDataService.getFormData(step.getWorkflowItemId(), step.getWorkflowItemTypeId());
		step.setFormData(formData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.StepService#validateBeforeCompleteStep(lu.wealins.common.dto.webia.services.StepDTO)
	 */
	@Override
	public List<String> validateBeforeCompleteStep(StepDTO stepDTO) {
		List<String> errors = new ArrayList<String>();

		workflowValidationBuilderService.getValidationStepServicesForBeforeComplete(stepDTO).forEach(x -> errors.addAll(x.validateBeforeComplete(stepDTO)));

		validateQuestionnaire(stepDTO, errors);

		return errors;
	}

	private void validateQuestionnaire(StepDTO stepDTO, List<String> errors) {
		for (CheckStepDTO checkStepDTO : stepDTO.getCheckSteps()) {
			CheckWorkflowDTO check = checkStepDTO.getCheck();
			CheckDataDTO checkData = check.getCheckData();
			// check If mandatory
			if (BooleanUtils.isTrue(checkStepDTO.getIsMandatory())) {
				if (!checkDataService.hasValue(checkData)) {
					errors.add("Question '" + check.getCheckDesc() + "' is mandatory.");
				}
			}
			// check If CommentIf is needed
			String commentIf = check.getCommentIf();
			if (commentIf != null && checkData != null) {
				boolean isEmptyComment = StringUtils.isEmpty(checkData.getCommentIf());
				boolean isMandatoryCommentForYesNoNa = commentIf.equals(checkData.getDataValueYesNoNa());
				boolean isMandatoryCommentForDataValueAmount = checkData.getDataValueAmount() != null && commentIf.equals(checkData.getDataValueAmount().toString());
				boolean isMandatoryCommentForDataValueNumber = checkData.getDataValueNumber() != null && commentIf.equals(checkData.getDataValueNumber().toString());
				boolean isMandatoryComment = isMandatoryCommentForYesNoNa || isMandatoryCommentForDataValueAmount || isMandatoryCommentForDataValueNumber;
				if (isEmptyComment && isMandatoryComment) {
					errors.add("Question '" + check.getCheckDesc() + "' need a comment value");
				}
			}
		}
	}

	@Override
	public Collection<StepLightDTO> getStepsByWorkflowItemTypeId(Integer workflowItemTypeId) {
		return stepRepository.findByWorkflowItemTypeId(workflowItemTypeId).stream().map(step -> stepMapper.asStepLightDTO(step)).collect(Collectors.toList());
	}

	@Override
	public StepDTO completeStep(StepDTO step) {
		FormDataDTO formData = step.getFormData();
		if (formData != null) {
			step.setFormData(formDataService.completeFormData(formData, step.getStepWorkflow(), step.getWorkflowItemTypeId(), step.getWorkflowItemId()));
		}
		return step;
	}

}
