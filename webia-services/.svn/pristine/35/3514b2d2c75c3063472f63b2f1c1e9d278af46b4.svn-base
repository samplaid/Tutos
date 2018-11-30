package lu.wealins.webia.services.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.CheckWorkflowDTO;
import lu.wealins.webia.services.core.mapper.CheckWorkflowMapper;
import lu.wealins.webia.services.core.persistence.entity.CheckWorkflowEntity;
import lu.wealins.webia.services.core.persistence.repository.CheckWorkflowRepository;
import lu.wealins.webia.services.core.service.CheckWorkflowService;

@Service
public class CheckWorkflowServiceImpl implements CheckWorkflowService {

	@Autowired
	private CheckWorkflowRepository checkWorkflowRepository;

	@Autowired
	private CheckWorkflowMapper checkWorkflowMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.CheckWorkflowService#getCheckWorkflow(java.lang.String)
	 */
	@Override
	public CheckWorkflowDTO getCheckWorkflow(String checkCode) {
		CheckWorkflowEntity checkWorkflow = checkWorkflowRepository.findBycheckCode(checkCode);

		return checkWorkflowMapper.asCheckWorkflowDTO(checkWorkflow);
	}
}
