package lu.wealins.liability.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import lu.wealins.liability.services.core.persistence.entity.AgentEntity;
import lu.wealins.liability.services.core.persistence.entity.AgentHierarchyEntity;

public interface AgentHierarchyRepository extends JpaRepository<AgentHierarchyEntity, Long>, JpaSpecificationExecutor<AgentHierarchyEntity> {

	@Query(value = "select h.agent from AgentHierarchyEntity h where h.masterBroker.agtId = ?1")
	List<AgentEntity> findSubBroker(String agtId);

	@Query(value = "select h.agent from AgentHierarchyEntity h where h.masterBroker.agtId = ?1 and h.status=1 and h.agent.status=1 and h.agent.agentCategory = 'SB'")
	List<AgentEntity> findActiveSubBroker(String agtId);
}
