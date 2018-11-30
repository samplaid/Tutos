package lu.wealins.webia.services.core.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.webia.services.core.mapper.WorkflowUserMapper;
import lu.wealins.webia.services.core.persistence.repository.WorkflowUserRepository;
import lu.wealins.webia.services.core.service.WorkflowUserService;
import lu.wealins.common.dto.webia.services.WorkflowUserDTO;

@Service
public class WorkflowUserServiceImpl implements WorkflowUserService {

	private static final String USER_ID_CANNOT_BE_NULL = "User id cannot be null.";
	private static final String LOGIN_CANNOT_BE_NULL = "Login cannot be null.";
	private static final String TOKEN_CANNOT_BE_NULL = "Token cannot be null.";

	@Autowired
	private WorkflowUserRepository workflowUserRepository;

	@Autowired
	private WorkflowUserMapper workflowUserMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.WorkflowUserService#getWorkflowUser(java.lang.String)
	 */
	@Override
	public WorkflowUserDTO getWorkflowUser(String usrId) {
		Assert.notNull(usrId, USER_ID_CANNOT_BE_NULL);
		return workflowUserMapper.asWorkflowUserDTO(workflowUserRepository.findByUsrId(usrId));
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.WorkflowUserService#getWorkflowUserWithLogin(java.lang.String)
	 */
	@Override
	public WorkflowUserDTO getWorkflowUserWithLogin(String login) {
		Assert.notNull(login, LOGIN_CANNOT_BE_NULL);
		return workflowUserMapper.asWorkflowUserDTO(workflowUserRepository.findByLoginId(login));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.WorkflowUserService#getWorkflowUsers()
	 */
	@Override
	public Collection<WorkflowUserDTO> getWorkflowUsers() {
		return workflowUserMapper.asWorkflowUserDTOs(workflowUserRepository.findAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.WorkflowUserService#getWorkflowUserWithToken(java.lang.String)
	 */
	@Override
	public WorkflowUserDTO getWorkflowUserWithToken(String userToken) {
		Assert.notNull(userToken, TOKEN_CANNOT_BE_NULL);
		return workflowUserMapper.asWorkflowUserDTO(workflowUserRepository.findByUserToken(userToken));
	}

}
