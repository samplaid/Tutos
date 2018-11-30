package lu.wealins.webia.services.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.webia.services.core.mapper.SapMappingMapper;
import lu.wealins.webia.services.core.persistence.entity.SapMappingEntity;
import lu.wealins.webia.services.core.persistence.repository.SapMappingRepository;
import lu.wealins.webia.services.core.service.SapMappingService;
import lu.wealins.common.dto.webia.services.SapMappingDTO;

@Service
@Transactional
public class SapMappingServiceImpl implements SapMappingService {

	@Autowired
	private SapMappingRepository sapMappingRepository;

	@Autowired
	private SapMappingMapper sapMappingMapper;

	@Override
	public List<SapMappingDTO> findByType(String type) {
		List<SapMappingEntity> entities = sapMappingRepository.findByType(type);
		if (entities != null) {
			return sapMappingMapper.asSapMappingDTOs(entities);
		} else {
			return null;
		}
	}

}
