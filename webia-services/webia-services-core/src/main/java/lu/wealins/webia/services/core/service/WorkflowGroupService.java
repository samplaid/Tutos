package lu.wealins.webia.services.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.WorkflowGroupDTO;

public interface WorkflowGroupService {

	/**
	 * Get the workflow group according its id.
	 * 
	 * @param id The workflow group id.
	 * @return The workflow group.
	 */
	WorkflowGroupDTO getWorkflowGroup(Integer id);

	/**
	 * Get workflow groups linked to a workflow user.
	 * 
	 * @param usrId The workflow user id.
	 * @return The workflow groups.
	 */
	Collection<WorkflowGroupDTO> getWorkflowGroups(String usrId);
}
