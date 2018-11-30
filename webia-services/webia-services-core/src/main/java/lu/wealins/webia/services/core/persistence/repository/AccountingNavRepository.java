package lu.wealins.webia.services.core.persistence.repository;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lu.wealins.webia.services.core.persistence.entity.AccountingNavEntity;

/**
 * The Accounting NAV repository
 * 
 * @See AccountingNavEntity
 * @author xqt5q
 *
 */
@Repository
public interface AccountingNavRepository extends JpaRepository<AccountingNavEntity, Long> {

	@Query("select a from AccountingNavEntity a where a.fundId = :fundId and a.navDate = :navDate")
	AccountingNavEntity findByFundIdAndNavDate(@Param("fundId") String fundId, @Param("navDate") Date navDate);
	
	@Modifying
	@Query("update AccountingNavEntity accountingNav SET accountingNav.nav = :navCompleted where accountingNav.fundId = :fundId and accountingNav.currency = :currency and accountingNav.navDate = :navDate")
	int updateFIDandFAS(@Param("navCompleted") BigDecimal navCompleted, @Param("fundId") String fundId,
			@Param("currency") String currency, @Param("navDate") Date navDate);
	
	@Modifying
	@Query("update AccountingNavEntity accountingNav SET accountingNav.nav = :navCompleted where accountingNav.isinCode = :isinCode and accountingNav.currency = :currency and accountingNav.navDate = :navDate")
	int updateFE(@Param("navCompleted") BigDecimal navCompleted, @Param("isinCode") String isinCode,
			@Param("currency") String currency, @Param("navDate") Date navDate);

	@Query("select a from AccountingNavEntity a where a.isinCode = :isin and a.navDate = :navDate and a.currency = :currency")
	AccountingNavEntity findByIsinAndNavDate(@Param("isin") String isin, @Param("navDate") Date navDate, @Param("currency") String currency);


	@Query("Select count(*) from AccountingNavEntity  accNav where accNav.isinCode = :isinCode  and accNav.currency = :currency and accNav.navDate = :navDate")
	int ExistFeVni(@Param("isinCode") String isinCode, @Param("currency") String currency,
			@Param("navDate") Date navDate);

	@Query("Select count(*) from AccountingNavEntity  accNav where accNav.fundId = :fundId  and accNav.currency = :currency and accNav.navDate = :navDate")
	int ExistFidFasVni(@Param("fundId") String fundId, @Param("currency") String currency,
			@Param("navDate") Date navDate);

	@Query("select a from AccountingNavEntity a where a.fundId = :fundId and  a.currency = :currency and a.navDate = :navDate")
	AccountingNavEntity findByFundIdCurrencyAndNavDate(@Param("fundId") String fundId,
			@Param("currency") String currency, @Param("navDate") Date navDate);

	@Query("select a from AccountingNavEntity a where a.isinCode = :isinCode and a.currency = :currency and a.navDate = :navDate")
	AccountingNavEntity findByIsinCodeCurrencyAndNavDate(@Param("isinCode") String isinCode,
			@Param("currency") String currency, @Param("navDate") Date navDate);

}
