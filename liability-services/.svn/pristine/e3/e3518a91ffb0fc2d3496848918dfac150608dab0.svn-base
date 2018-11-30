package lu.wealins.liability.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.PatternAccountRootEntity;

public interface PatternAccountRootRepository extends JpaRepository<PatternAccountRootEntity, Integer> {

	PatternAccountRootEntity findByPatternId(Integer patternId);

	@Query(value = "select p.exemple from PatternAccountRootEntity p where p.accountBic = :accountBic")
	List<String> findExampleByAccountBic(@Param("accountBic") String accountBic);

	@Query(value = "select p.pattern from PatternAccountRootEntity p where p.accountBic = :accountBic")
	List<String> findPatternByAccountBic(@Param("accountBic") String accountBic);

}
