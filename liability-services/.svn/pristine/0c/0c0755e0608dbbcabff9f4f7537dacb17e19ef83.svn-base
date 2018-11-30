package lu.wealins.liability.services.core.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.ExchangeRateEntity;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, String> {

	@Query("SELECT r FROM ExchangeRateEntity r WHERE r.status = 1 and r.date0 <= :date0"
			+ " AND r.fromCurrency = :from AND r.toCurrency = :to"
			+ " ORDER BY date0 desc")
	public Page<ExchangeRateEntity> findAllBeforeOrEqualDate0(
			@Param("from") String from, @Param("to") String to, @Param("date0") Date date,
			Pageable pageable);

	@Query("SELECT er FROM ExchangeRateEntity er WHERE er.status = 1 AND er.date0 = :date0 AND er.fromCurrency = :fromCurrency AND er.toCurrency = :toCurrency")
	List<ExchangeRateEntity> findActiveByDateAndFromCcyToCcy(@Param("date0") Date date0, @Param("fromCurrency") String fromCurrency, @Param("toCurrency") String toCurrency);

	@Query("SELECT er FROM ExchangeRateEntity er WHERE er.date0 = :date0 AND er.fromCurrency = :fromCurrency AND er.toCurrency = :toCurrency AND er.status = 1")
	ExchangeRateEntity findByDateAndFromCcyToCcy(@Param("date0") Date date0, @Param("fromCurrency") String fromCurrency, @Param("toCurrency") String toCurrency);

	@Query("SELECT er FROM ExchangeRateEntity er WHERE er.status = 1 AND er.toCurrency = :toCurrency "
			+ "AND er.fromCurrency = :fromCurrency "
			+ "AND er.date0 = (SELECT MAX(er2.date0) FROM ExchangeRateEntity er2 WHERE er2.status = 1 "
			+ "AND er2.toCurrency = :toCurrency AND er2.fromCurrency = :fromCurrency "
			+ "AND er2.date0 < :date0)"
			+ "AND substring(er.xrsId, 4, 1) <> '_'")
	ExchangeRateEntity findPreviousExchangeRate(@Param("date0") Date date0, @Param("fromCurrency") String fromCurrency, @Param("toCurrency") String toCurrency);

	@Query("SELECT er FROM ExchangeRateEntity er WHERE er.status = 1 AND er.toCurrency = :toCurrency "
			+ "AND er.fromCurrency = :fromCurrency "
			+ "AND er.date0 = (SELECT MAX(er2.date0) FROM ExchangeRateEntity er2 WHERE er2.status = 1 "
			+ "AND er2.toCurrency = :toCurrency AND er2.fromCurrency = :fromCurrency "
			+ "AND er2.date0 <= :date0  AND substring(er.xrsId, 4, 1) <> '_')" + "AND substring(er.xrsId, 4, 1) <> '_'")
	ExchangeRateEntity findlatestExchangeRate(@Param("date0") Date date0, @Param("fromCurrency") String fromCurrency,
			@Param("toCurrency") String toCurrency);


	@Query("SELECT COUNT(er) FROM ExchangeRateEntity er WHERE er.status = 1 AND er.date0 = :date0")
	Long countExchangeRateForDate0(@Param("date0") Date date0);

}
