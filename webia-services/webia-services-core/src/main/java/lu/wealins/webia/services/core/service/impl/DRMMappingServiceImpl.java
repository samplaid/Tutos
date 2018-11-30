package lu.wealins.webia.services.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.common.dto.webia.services.DRMMappingDTO;
import lu.wealins.webia.services.core.mapper.DRMMappingMapper;
import lu.wealins.webia.services.core.persistence.entity.DRMMappingEntity;
import lu.wealins.webia.services.core.persistence.repository.DRMMappingRepository;
import lu.wealins.webia.services.core.service.DRMMappingService;

@Service
@Transactional
public class DRMMappingServiceImpl implements DRMMappingService {

	@Autowired
	private DRMMappingRepository drmMappingRepository;

	@Autowired
	private DRMMappingMapper drmMappingMapper;

	@Override
	public List<DRMMappingDTO> findByType(String type) {
		List<DRMMappingEntity> entities = drmMappingRepository.findByType(type);
		if (entities != null) {
			return drmMappingMapper.asDRMMappingDTOs(entities);
		} else {
			return null;
		}
	}

}
