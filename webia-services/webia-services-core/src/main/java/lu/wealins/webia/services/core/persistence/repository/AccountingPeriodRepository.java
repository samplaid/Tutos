package lu.wealins.webia.services.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import lu.wealins.webia.services.core.persistence.entity.AccountingPeriodEntity;

/**
 * The Accounting Period repository
 * 
 * @See AccountingPeriodEntity
 * @author xqt5q
 *
 */
@Repository
public interface AccountingPeriodRepository extends JpaRepository<AccountingPeriodEntity, Long> {

	@Query("select a from AccountingPeriodEntity a where a.status = 1")
	AccountingPeriodEntity findActivePeriod();

}
