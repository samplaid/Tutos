package lu.wealins.webia.services.core.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.webia.services.core.mapper.WorkflowQueueMapper;
import lu.wealins.webia.services.core.persistence.entity.WorkflowGroupEntity;
import lu.wealins.webia.services.core.persistence.entity.WorkflowQueueEntity;
import lu.wealins.webia.services.core.persistence.entity.WorkflowUserEntity;
import lu.wealins.webia.services.core.persistence.repository.WorkflowQueueRepository;
import lu.wealins.webia.services.core.service.WorkflowQueueService;
import lu.wealins.common.dto.webia.services.WorkflowQueueDTO;

@Service
public class WorkflowQueueServiceImpl implements WorkflowQueueService {

	private static final String WORKFLOW_QUEUE_ID_CANNOT_BE_NULL = "Workflow queue id cannot be null.";
	private static final String USER_ID_CANNOT_BE_NULL = "User id cannot be null.";

	@Autowired
	private WorkflowQueueRepository workflowQueueRepository;

	@Autowired
	private WorkflowQueueMapper workflowQueueMapper;


	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.WorkflowQueueService#getWorkflowQueue(java.lang.Integer)
	 */
	@Override
	public WorkflowQueueDTO getWorkflowQueue(Integer id) {
		return workflowQueueMapper.asWorkflowQueueDTO(workflowQueueRepository.findById(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.WorkflowQueueService#getWorkflowQueueUsrIds()
	 */
	@Override
	public List<String> getWorkflowQueueUsrIds() {
		return workflowQueueRepository.findWorkflowQueueUsrIds();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.WorkflowQueueService#isAssignTo(java.lang.Integer, java.lang.String)
	 */
	@Override
	public boolean isAssignTo(Integer workflowQueueId, String userId) {
		Assert.notNull(workflowQueueId, WORKFLOW_QUEUE_ID_CANNOT_BE_NULL);
		Assert.notNull(userId, USER_ID_CANNOT_BE_NULL);

		WorkflowQueueEntity workflowQueue = workflowQueueRepository.findById(workflowQueueId);

		if (workflowQueue == null) {
			throw new IllegalStateException("Unknow workflow queue with id " + workflowQueueId);
		}

		WorkflowUserEntity user = workflowQueue.getUser();
		if (user != null) {
			return user.getUsrId().equals(userId);
		}

		WorkflowGroupEntity userGroup = workflowQueue.getUserGroup();

		if (userGroup != null) {
			return userGroup.getUsers().stream().anyMatch(x -> x.getUsrId().equals(userId));
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.WorkflowQueueService#hasPersonalQueue(java.lang.String)
	 */
	@Override
	public boolean hasPersonalQueue(String usrId) {
		return CollectionUtils.isNotEmpty(workflowQueueRepository.findByUserId(usrId));
	}

}
