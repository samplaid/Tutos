package lu.wealins.liability.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.AgentContactEntity;

public interface AgentContactRepository extends JpaRepository<AgentContactEntity, Integer> {

	@Query("SELECT COUNT(1) FROM AgentContactEntity agc WHERE agc.agent.agtId = :agentId AND agc.contact.agtId = :contactId AND agc.contactFunction = :contactFunction AND agc.status = :status")
	long count(@Param("agentId") String agentId, @Param("contactId") String contactId, @Param("contactFunction") String contactFunction, @Param("status") String status);

	/**
	 * Count the contacts of an agent identified by the {@code agentId} that have the specified function and still active.
	 * 
	 * @param agentId the agent contact owner identifier
	 * @param contactFunction the contact function
	 * @return 0 if no result found. Otherwise, it returns the number of the result.
	 */
	@Query("SELECT COUNT(1) FROM AgentContactEntity agc WHERE agc.status = '1' AND agc.agent.agtId = :agentId AND agc.contactFunction = :contactFunction")
	long countHaveActiveFunction(@Param("agentId") String agentId, @Param("contactFunction") String contactFunction);

	@Query("SELECT coalesce(MAX(agc.agcId), 0) FROM AgentContactEntity agc")
	int getNextId();

	List<AgentContactEntity> findByAgentId(String agentId);

	AgentContactEntity findByAgentIdAndContactAgtId(String agentId, String contactId);

}
