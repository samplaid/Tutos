package lu.wealins.liability.services.core.persistence.repository; 

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.liability.services.core.persistence.entity.PolicyChangeEntity;

public interface PolicyChangeRepository extends JpaRepository<PolicyChangeEntity, Long> {
	Optional<PolicyChangeEntity> findByWorkflowItemId(Integer workflowItemId);
	
	Collection<PolicyChangeEntity> findByPolicyIdOrderByDateOfChangeDesc(String polId);
} 
