package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.webia.services.core.persistence.entity.StatementEntity;

public interface StatementRepository extends JpaRepository<StatementEntity, Long> {

	public List<StatementEntity> findAllByStatementTypeAndPeriodAndAgentIdAndStatementStatusNotIn(String type, String period, String broker, List<String> excludeStatement);

}
