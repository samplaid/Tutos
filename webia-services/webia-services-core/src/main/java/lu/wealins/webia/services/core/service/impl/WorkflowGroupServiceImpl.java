package lu.wealins.webia.services.core.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.webia.services.core.mapper.WorkflowGroupMapper;
import lu.wealins.webia.services.core.persistence.entity.WorkflowGroupEntity;
import lu.wealins.webia.services.core.persistence.repository.WorkflowGroupRepository;
import lu.wealins.webia.services.core.service.WorkflowGroupService;
import lu.wealins.webia.services.core.service.WorkflowQueueService;
import lu.wealins.common.dto.webia.services.WorkflowGroupDTO;


@Service
public class WorkflowGroupServiceImpl implements WorkflowGroupService {

	@Autowired
	private WorkflowGroupRepository workflowGroupRepository;

	@Autowired
	private WorkflowGroupMapper workflowGroupMapper;

	@Autowired
	private WorkflowQueueService workflowQueueService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.WorkflowGroupService#getWorkflowGroup(java.lang.Integer)
	 */
	@Override
	public WorkflowGroupDTO getWorkflowGroup(Integer id) {
		WorkflowGroupEntity workflowGroup = workflowGroupRepository.findOne(id);

		List<String> workflowQueueUsrIds = workflowQueueService.getWorkflowQueueUsrIds();

		WorkflowGroupDTO workflowGroupDTO = workflowGroupMapper.asWorkflowGroupDTO(workflowGroup);

		workflowGroupDTO.getUsers().removeIf(user -> !workflowQueueUsrIds.contains(user.getUsrId()));

		return workflowGroupDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.WorkflowGroupService#getWorkflowGroups(java.lang.String)
	 */
	@Override
	public Collection<WorkflowGroupDTO> getWorkflowGroups(String usrId) {
		return workflowGroupMapper.asWorkflowGroupDTOs(workflowGroupRepository.findWorkflowGroupsByWorkflowUser(usrId));
	}
}
