package lu.wealins.webia.services.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.common.dto.webia.services.SapAccountingDTO;
import lu.wealins.webia.services.core.mapper.SapAccountingMapper;
import lu.wealins.webia.services.core.persistence.entity.SapAccountingEntity;
import lu.wealins.webia.services.core.persistence.repository.SapAccountingRepository;
import lu.wealins.webia.services.core.service.SapAccountingService;

@Service
@Transactional
public class SapAccountingServiceImpl implements SapAccountingService {

	@Autowired
	private SapAccountingRepository sapAccountingRepository;

	@Autowired
	private SapAccountingMapper sapAccountingMapper;

	@Override
	public List<SapAccountingDTO> findByOriginIdForEncashments(Long originId, String fundId) {
		List<SapAccountingEntity> entities = sapAccountingRepository.findByOriginIdAndFundIdForEncashments(originId, fundId);
		if (entities == null) {
			return null;
		} else {
			List<SapAccountingDTO> dtos = new ArrayList<SapAccountingDTO>();
			dtos = (List<SapAccountingDTO>) sapAccountingMapper.asSapAccountingDTOs(entities);
			return dtos;
		}
	}

}
