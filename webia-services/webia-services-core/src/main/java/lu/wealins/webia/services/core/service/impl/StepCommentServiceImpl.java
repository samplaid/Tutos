package lu.wealins.webia.services.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.StepCommentDTO;
import lu.wealins.common.dto.webia.services.StepCommentRequest;
import lu.wealins.webia.services.core.mapper.StepCommentMapper;
import lu.wealins.webia.services.core.persistence.entity.StepCommentEntity;
import lu.wealins.webia.services.core.persistence.entity.StepEntity;
import lu.wealins.webia.services.core.persistence.repository.StepCommentRepository;
import lu.wealins.webia.services.core.persistence.repository.StepRepository;
import lu.wealins.webia.services.core.service.StepCommentService;

@Service
public class StepCommentServiceImpl implements StepCommentService {

	private static final String WORKFLOW_ITEM_ID_CANNOT_BE_NULL = "Workflow item id cannot be null.";
	private static final String STEP_ID_CANNOT_BE_NULL = "Step id cannot be null.";
	private static final String REQUEST_CANNOT_BE_NULL = "Step Comment Request cannot be null.";
	private static final String STEP_COMMENT_ID_CANNOT_BE_NULL = "Step Comment Id cannot be null.";
	private static final String STEP_WORKFLOW_CANNOT_BE_NULL = "No Step Entity found for the Step id.";
	
	@Autowired
	private StepCommentRepository stepCommentRepository;

	@Autowired
	private StepRepository stepRepository;
	
	@Autowired
	private StepCommentMapper stepCommentMapper;


	@Override
	public Collection<StepCommentDTO> getStepComments(Long workflowItemId) {
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);

		Collection<StepCommentEntity> stepComments = stepCommentRepository.findAllByWorkflowItemIdOrderByCreationDtDesc(workflowItemId);
		if (CollectionUtils.isEmpty(stepComments)){
			return new ArrayList<StepCommentDTO>();
		}
		return stepCommentMapper.asStepCommentDTOs(stepComments);
	}
	

	@Override
	public StepCommentDTO addStepComment(StepCommentRequest request) {

		Assert.notNull(request, REQUEST_CANNOT_BE_NULL);
		Assert.notNull(request.getStepId(), STEP_ID_CANNOT_BE_NULL);
		Assert.notNull(request.getWorkflowItemId(), WORKFLOW_ITEM_ID_CANNOT_BE_NULL);

		StepCommentEntity stepCommentEntity = new StepCommentEntity();
		stepCommentEntity.setComment(request.getComment());
		stepCommentEntity.setNextDueDate(request.getNextDueDate());
		stepCommentEntity.setWorkflowItemId(request.getWorkflowItemId());
		StepEntity step = stepRepository.getOne(request.getStepId());
		Assert.notNull(step, STEP_WORKFLOW_CANNOT_BE_NULL);
		stepCommentEntity.setStep(step);	
		stepCommentEntity = stepCommentRepository.save(stepCommentEntity);

		return stepCommentMapper.asStepCommentDTO(stepCommentEntity);
	}

	@Override
	public StepCommentDTO updateStepComment(StepCommentRequest request) {

		Assert.notNull(request, REQUEST_CANNOT_BE_NULL);
		Assert.notNull(request.getStepId(), STEP_ID_CANNOT_BE_NULL);
		Assert.notNull(request.getWorkflowItemId(), WORKFLOW_ITEM_ID_CANNOT_BE_NULL);
		Assert.notNull(request.getStepCommentId(), STEP_COMMENT_ID_CANNOT_BE_NULL);

		StepCommentEntity stepCommentEntity = stepCommentRepository.findOne(request.getStepCommentId());
		stepCommentEntity.setComment(request.getComment());
		stepCommentEntity.setNextDueDate(request.getNextDueDate());
		StepEntity step = stepRepository.getOne(request.getStepId());
		Assert.notNull(step, STEP_WORKFLOW_CANNOT_BE_NULL);
		stepCommentEntity.setStep(step);
		stepCommentEntity = stepCommentRepository.save(stepCommentEntity);

		return stepCommentMapper.asStepCommentDTO(stepCommentEntity);
	}

}
