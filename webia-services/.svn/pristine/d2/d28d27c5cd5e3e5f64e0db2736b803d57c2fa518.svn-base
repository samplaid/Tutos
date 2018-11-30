package lu.wealins.webia.services.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.WorkflowUserDTO;

public interface WorkflowUserService {

	/**
	 * Get workflow user according its id.
	 * 
	 * @param usrId The workflow user id.
	 * @return The workflow user.
	 */
	WorkflowUserDTO getWorkflowUser(String usrId);

	/**
	 * Get workflow user according its login.
	 * 
	 * @param usrId The workflow user login.
	 * @return The workflow user.
	 */
	WorkflowUserDTO getWorkflowUserWithLogin(String login);

	/**
	 * Get workflow users.
	 * 
	 * @return The workflow users.
	 */
	Collection<WorkflowUserDTO> getWorkflowUsers();

	/**
	 * Get workflow user according the user token.
	 * 
	 * @param userToken The user token.
	 * @return The workflow user.
	 */
	WorkflowUserDTO getWorkflowUserWithToken(String userToken);
}
