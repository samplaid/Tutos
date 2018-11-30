package lu.wealins.webia.core.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.WorkflowQueueDTO;
import lu.wealins.common.dto.webia.services.WorkflowUserDTO;
import lu.wealins.common.security.SecurityContextHelper;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.service.WebiaWorkflowQueueService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.impl.exception.SetupException;

@Service
public class WebiaWorkflowUserServiceImpl implements WebiaWorkflowUserService {

	private static final String USER_ID_CANNOT_BE_NULL = "User id cannot be null.";
	private static final String SECURITY_CONTEXT_CANNOT_BE_NULL = "Security context cannot be null.";
	private static final String LOGIN_CANNOT_BE_NULL = "Login cannot be null.";
	private static final String WEBIA_LOAD_WORKFLOW_USER_WITH_LOGIN = "webia/workflowUser/login/";
	private static final String WEBIA_WORKFLOW_USERS = "webia/workflowUser";

	@Autowired
	private RestClientUtils restClientUtils;
	@Autowired
	private WebiaApplicationParameterService applicationParameterService;
	@Autowired
	private WebiaWorkflowQueueService workflowQueueService;

	private Map<String, WorkflowUserDTO> workflowUsersByLogin = new HashMap<>();
	private Map<String, WorkflowUserDTO> workflowUsersByUserId = new HashMap<>();

	private final Logger log = LoggerFactory.getLogger(WebiaWorkflowUserServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaWorkflowUserService#getWorkflowUser(javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public WorkflowUserDTO getWorkflowUser(SecurityContext context) {
		Assert.notNull(context, "Security context cannot be null");

		return restClientUtils.get(WEBIA_LOAD_WORKFLOW_USER_WITH_LOGIN, SecurityContextHelper.getPreferredUsername(context), WorkflowUserDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaWorkflowUserService#getUserId(javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public String getUserId(SecurityContext context) {
		String login = SecurityContextHelper.getPreferredUsername(context);
		String userId = getUserId(login);
		if (userId == null) {
			throw new SetupException("There is no workflow user having the login " + login + ". Your Webia account must exist in E-Lissia too.");
		}
		return userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaWorkflowUserService#getUsrId(java.lang.String)
	 */
	@Override
	public String getUserId(String login) {

		Assert.notNull(login, LOGIN_CANNOT_BE_NULL);
		WorkflowUserDTO workflowUser = workflowUsersByLogin.get(login.toLowerCase());

		// If the user has been not found then we reload the user from DB.
		if (workflowUser == null) {
			log.info("Login " + login + " has been not found (reload users from DB)");
			Collection<WorkflowUserDTO> workflowUsers = getWorkflowUsers();
			workflowUsersByLogin = workflowUsers.stream().collect(Collectors.toMap(x -> x.getLoginId().toLowerCase(), Function.identity()));
			workflowUser = workflowUsersByLogin.get(login.toLowerCase());
		}

		if (workflowUser == null) {
			log.error("Workflow User with the login " + login + " does not exist");
			throw new IllegalStateException("There is no workflow user having the login '" + login + "'.");
		}

		return workflowUser.getUsrId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaWorkflowUserService#getLogin(java.lang.String)
	 */
	@Override
	public String getLogin(String usrId) {
		Assert.notNull(usrId, USER_ID_CANNOT_BE_NULL);
		WorkflowUserDTO workflowUser = getWorkflowUser(usrId);

		if (workflowUser == null) {
			log.info("Workflow User id " + usrId + " does not exist");
			return null;
		}

		return workflowUser.getLoginId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaWorkflowUserService#getLogin(javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public String getLogin(SecurityContext context) {
		Assert.notNull(context, SECURITY_CONTEXT_CANNOT_BE_NULL);
		WorkflowUserDTO workflowUser = getWorkflowUser(context);

		if (workflowUser == null) {
			log.info("Workflow User cannot be retrieved from the security context.");
			return null;
		}

		return workflowUser.getLoginId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaWorkflowUserService#getWorkflowUser(java.lang.String)
	 */
	@Override
	public WorkflowUserDTO getWorkflowUser(String usrId) {
		WorkflowUserDTO workflowUser = workflowUsersByUserId.get(usrId.toLowerCase());

		// If the user has been not found then we reload the user from DB.
		if (workflowUser == null) {
			log.info("Workflow User id " + usrId + " has been not found (reload users from DB)");
			workflowUsersByUserId = getWorkflowUsers().stream().collect(Collectors.toMap(x -> x.getUsrId().toLowerCase(), Function.identity()));
			workflowUser = workflowUsersByUserId.get(usrId.toLowerCase());
		}

		return workflowUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaWorkflowUserService#isSuperUser(java.lang.String)
	 */
	@Override
	public boolean isSuperUser(String usrId) {
		String login = getLogin(usrId);
		Set<String> superUsers = applicationParameterService.getLoginsByPassStepAccess().stream().map(x -> x.toLowerCase()).collect(Collectors.toSet());

		return superUsers.contains(login.toLowerCase());
	}

	private Collection<WorkflowUserDTO> getWorkflowUsers() {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		return restClientUtils.get(WEBIA_WORKFLOW_USERS, "/all", params, new GenericType<Collection<WorkflowUserDTO>>() {
			// nothing to do.
		});
	}

	@Override
	public WorkflowUserDTO getAssignedUser(String workflowItemId, String usrId) {
		Assert.notNull(workflowItemId);
		String workflowQueueId = workflowQueueService.getWorkflowQueueId(workflowItemId, usrId);
		if (workflowItemId == null) {
			return null;
		}
		WorkflowQueueDTO workflowQueue = workflowQueueService.getWorkflowQueue(workflowQueueId);
		if (workflowQueue == null) {
			return null;
		}
		return workflowQueue.getUser();
	}

}
