package lu.wealins.liability.services.core.persistence.repository; 

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.CurrencyEntity;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, String> {

	@Query("SELECT cur FROM CurrencyEntity cur WHERE RTRIM(cur.ccyId) = RTRIM(:currency) AND cur.status = 1 ORDER BY cur.ccyId ASC")
	CurrencyEntity findActiveCurrency(@Param("currency") String currency);

	@Query("SELECT cur FROM CurrencyEntity cur WHERE cur.status = 1")
	List<CurrencyEntity> findActiveCurrencies();
}
