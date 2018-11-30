package lu.wealins.liability.services.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lu.wealins.liability.services.core.persistence.entity.PolicyEventEntity;

public interface PolicyEventRepository extends JpaRepository<PolicyEventEntity, Long> {

	@Query("SELECT max(pe.pevId) FROM PolicyEventEntity pe")
	long findMaxPevId();

	PolicyEventEntity findBycreatedProcessAndEvent(String createdProcess, String event);

}
