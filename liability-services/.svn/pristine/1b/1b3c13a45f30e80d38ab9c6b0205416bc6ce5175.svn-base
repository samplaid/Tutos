package lu.wealins.liability.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lu.wealins.liability.services.core.persistence.entity.AssetManagerStrategyEntity;

public interface AssetManagerStrategyRepository extends JpaRepository<AssetManagerStrategyEntity, Long> {

	@Query(value = "select a from AssetManagerStrategyEntity a where trim(upper(a.riskProfile)) = trim(upper(?1))") // !this shouldn't care of the status of agent or strategy
	List<AssetManagerStrategyEntity> findByRiskProfile(String riskProdile);
	
	@Query(value="select ams from AssetManagerStrategyEntity ams where ams.agent.agtId = ?1")
	List<AssetManagerStrategyEntity> findByAgentId(String agentId);

	@Query(value = "select a from AssetManagerStrategyEntity a where a.status='1' and trim(upper(a.riskProfile)) = trim(upper(?1))") // !this shouldn't care of the status of agent or strategy
	List<AssetManagerStrategyEntity> findByRiskProfileAndActive(String riskProdile);
}
