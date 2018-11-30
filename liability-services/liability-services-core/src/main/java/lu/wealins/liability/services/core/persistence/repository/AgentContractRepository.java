package lu.wealins.liability.services.core.persistence.repository; 
import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.liability.services.core.persistence.entity.AgentContractEntity;

public interface AgentContractRepository extends JpaRepository<AgentContractEntity, String> {
}
