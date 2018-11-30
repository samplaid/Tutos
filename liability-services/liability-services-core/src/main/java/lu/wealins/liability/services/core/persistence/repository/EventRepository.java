package lu.wealins.liability.services.core.persistence.repository; 
import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.liability.services.core.persistence.entity.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, Integer> {
	EventEntity findByEvtId(int eventType);
}
