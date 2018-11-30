package lu.wealins.liability.services.core.persistence.repository; 
import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.liability.services.core.persistence.entity.ExchangeInfoEntity;
import lu.wealins.liability.services.core.persistence.entity.ExchangeInfoEntityId;

public interface ExchangeInfoRepository extends JpaRepository<ExchangeInfoEntity, ExchangeInfoEntityId> {
}
