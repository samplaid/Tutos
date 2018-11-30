package lu.wealins.liability.services.core.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.liability.services.core.business.AgentCategoryService;
import lu.wealins.liability.services.core.mapper.AgentCategoryMapper;
import lu.wealins.liability.services.core.persistence.repository.AgentCategoryRepository;
import lu.wealins.common.dto.liability.services.AgentCategoryDTO;

@Service
public class AgentCategoryServiceImpl implements AgentCategoryService {
	
	@Autowired
	AgentCategoryRepository agentCategoryRepository;
	
	@Autowired
	AgentCategoryMapper mapper;
	
	@Override
	public List<AgentCategoryDTO> retrieveAll() {		
		return mapper.asAgentCategoryDTOs(agentCategoryRepository.findAll());
	}

}
