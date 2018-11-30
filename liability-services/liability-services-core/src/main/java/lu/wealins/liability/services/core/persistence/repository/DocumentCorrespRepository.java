package lu.wealins.liability.services.core.persistence.repository; 
import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.liability.services.core.persistence.entity.DocumentCorrespEntity;
import lu.wealins.liability.services.core.persistence.entity.DocumentCorrespEntityId;

public interface DocumentCorrespRepository extends JpaRepository<DocumentCorrespEntity, DocumentCorrespEntityId> {
}
