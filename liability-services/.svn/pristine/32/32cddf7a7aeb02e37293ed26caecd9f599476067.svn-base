package lu.wealins.liability.services.core.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.liability.services.core.business.ControlService;
import lu.wealins.liability.services.core.mapper.ControlMapper;
import lu.wealins.liability.services.core.persistence.repository.ControlRepository;
import lu.wealins.common.dto.liability.services.ControlDTO;

@Service
public class ControlServiceImpl implements ControlService {

	@Autowired
	private ControlRepository controlRepository;

	@Autowired
	private ControlMapper controlMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ControlService#getControl(java.lang.String)
	 */
	@Override
	public ControlDTO getControl(String ctlId) {
		return controlMapper.asControlDTO(controlRepository.findOne(ctlId));
	}

}
