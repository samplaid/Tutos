package lu.wealins.liability.services.core.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.EventDTO;
import lu.wealins.liability.services.core.business.EventService;
import lu.wealins.liability.services.core.mapper.EventMapper;
import lu.wealins.liability.services.core.persistence.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventMapper mapper;

	@Autowired
	private EventRepository repository;

	@Override
	public EventDTO getEventByType(int eventType) {
		return mapper.asEventDTO(repository.findByEvtId(eventType));
	}
	
}
