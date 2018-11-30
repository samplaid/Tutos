package lu.wealins.webia.core.service;

import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.WorkflowUserDTO;

public interface WebiaWorkflowUserService {

	/**
	 * Get the workflow user according its id.
	 * 
	 * @param workflowUserId The workflow user id.
	 * @return The workflow user.
	 */
	WorkflowUserDTO getWorkflowUser(String workflowUserId);

	/**
	 * Get the workflow user according to the login in the security context.
	 * 
	 * @param context The security context.
	 * @return The workflow user.
	 */
	WorkflowUserDTO getWorkflowUser(SecurityContext context);

	/**
	 * Get the workflow user id according to the login in the security context.
	 * 
	 * @param context The security context.
	 * @return The workflow user id.
	 */
	String getUserId(SecurityContext context);

	/**
	 * Get user id linked to the login.
	 * 
	 * @param login The login.
	 * @return The user id.
	 */
	String getUserId(String login);

	/**
	 * Get login linked to the user id.
	 * @param usrId The user id.
	 * @return The login.
	 */
	String getLogin(String usrId);

	/**
	 * Get login of the current user.
	 * 
	 * @param context The security context.
	 * @return The login.
	 */
	String getLogin(SecurityContext context);

	/**
	 * Check if the user is a super user. It means that the login associated to the user is in the list of the LOGINS_BY_PASS_STEP_ACCESS application parameter.
	 * 
	 * @param usrId The user id.
	 * @return True, if successful.
	 */
	boolean isSuperUser(String usrId);

	WorkflowUserDTO getAssignedUser(String workflowItemId, String usrId);
}
