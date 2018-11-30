package lu.wealins.liability.services.core.business.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lu.wealins.liability.services.core.business.AgentHierarchyService;
import lu.wealins.liability.services.core.mapper.AgentHierarchyMapper;
import lu.wealins.liability.services.core.persistence.entity.AgentHierarchyEntity;
import lu.wealins.liability.services.core.persistence.repository.AgentHierarchyRepository;
import lu.wealins.liability.services.core.persistence.specification.AgentHierarchySpecifications;
import lu.wealins.common.dto.liability.services.AgentHierarchyDTO;
import lu.wealins.common.dto.liability.services.AgentHierarchyPageableRequest;
import lu.wealins.common.dto.liability.services.AgentHierarchyRequest;
import lu.wealins.common.dto.liability.services.SearchResult;

@Service
public class AgentHierarchyServiceImpl implements AgentHierarchyService {
	@Autowired
	private AgentHierarchyRepository agentHierarchyRepository;
	
	@Autowired
	private AgentHierarchyMapper agentHierarchyMapper;
	
	@Override
	public SearchResult<AgentHierarchyDTO> search(AgentHierarchyPageableRequest criteria) {
		SearchResult<AgentHierarchyDTO> result = new SearchResult<>();
		if(criteria == null){
			return result;
		}
		
		int pageNum = criteria.getPageNum() == null || criteria.getPageNum().intValue() < 1 ? 1
				: criteria.getPageNum().intValue();
		int pageSize = criteria.getPageSize() == null || criteria.getPageSize().intValue() <= 1
				|| criteria.getPageSize().intValue() > SearchResult.MAX_PAGE_SIZE ? SearchResult.DEFAULT_PAGE_SIZE
						: criteria.getPageSize().intValue();
		
		Pageable pageable = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "aghId");
		Page<AgentHierarchyEntity> pageResult = agentHierarchyRepository.findAll(createSpecification(criteria), pageable);
		
		result.setPageSize(criteria.getPageSize());
		result.setTotalPageCount(pageResult.getTotalPages());
		result.setTotalRecordCount(pageResult.getTotalElements());
		result.setCurrentPage(pageResult.getNumber() + 1);
		result.setContent(new ArrayList<AgentHierarchyDTO>(agentHierarchyMapper.asAgentHierarchyDTOs(pageResult.getContent())));
		return result;
	}

	private Specifications<AgentHierarchyEntity> createSpecification(AgentHierarchyRequest criteria) {
		Specifications<AgentHierarchyEntity> specifs = Specifications.where(AgentHierarchySpecifications.initial());
		
		if(criteria.getAghId() != null){
			specifs = specifs.and(AgentHierarchySpecifications.withAghId(criteria.getAghId()));
		}
		if(StringUtils.hasText(criteria.getMasterBrokerId())){
			specifs = specifs.and(AgentHierarchySpecifications.withMasterBrokerId(criteria.getMasterBrokerId()));
		}
		if(StringUtils.hasText(criteria.getSubBrokerId())){
			specifs = specifs.and(AgentHierarchySpecifications.withWritingAgent(criteria.getSubBrokerId()));
		}
		if(criteria.getStatus() != null){
			specifs = specifs.and(AgentHierarchySpecifications.withStatus(criteria.getStatus()));
		}
		if(criteria.getType() != null){
			specifs = specifs.and(AgentHierarchySpecifications.withType(criteria.getType()));
		}
		return specifs;
	}
	
	@Override
	public Collection<AgentHierarchyDTO> findAgentHierarchies(AgentHierarchyRequest criteria){
		List<AgentHierarchyEntity> agentHierarchies = agentHierarchyRepository.findAll(createSpecification(criteria));
		return agentHierarchyMapper.asAgentHierarchyDTOs(agentHierarchies);
	}
	
}
