package lu.wealins.liability.services.core.persistence.repository; 
import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.liability.services.core.persistence.entity.OptionEntity;

public interface OptionRepository extends JpaRepository<OptionEntity, String> {
}
