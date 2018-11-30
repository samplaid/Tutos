package lu.wealins.liability.services.core.business;

import lu.wealins.common.dto.liability.services.EventDTO;

public interface EventService {
	EventDTO getEventByType(int eventType);
}
