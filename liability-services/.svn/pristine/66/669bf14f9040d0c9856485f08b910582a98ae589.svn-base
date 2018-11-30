package lu.wealins.liability.services.core.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.FundPriceEntity;

public interface FundPriceRepository extends JpaRepository<FundPriceEntity, String> {

	@Query("SELECT fp FROM FundPriceEntity fp WHERE fp.status = 1 AND fp.recordType = 1 AND fp.fund.fdsId = :fundId AND fp.date0 = :priceDate ORDER BY fp.date0 DESC")
	List<FundPriceEntity> findActiveByFundIdAndPriceDate(@Param("fundId") String fundId, @Param("priceDate") Date priceDate);

	@Query("SELECT fp FROM FundPriceEntity fp WHERE fp.status = 1 AND fp.recordType = 1 AND fp.fund.fdsId = :fundId AND "
			+ "fp.date0 = (SELECT MAX(date0) from FundPriceEntity WHERE status = 1 AND fund.fdsId = :fundId AND date0 < :priceDate AND record_type = 1)")
	FundPriceEntity findPreviousFundPrice(@Param("fundId") String fundId, @Param("priceDate") Date priceDate);

	@Query("SELECT fp FROM FundPriceEntity fp WHERE fp.status = 1 AND fp.recordType = 1 AND fp.fund.fdsId = :fundId AND "
			+ "fp.date0 = (SELECT MAX(date0) from FundPriceEntity WHERE status = 1 AND fund.fdsId = :fundId AND date0 < :priceDate AND record_type = 1)")
	List<FundPriceEntity> findPreviousFundPrices(@Param("fundId") String fundId, @Param("priceDate") Date priceDate);

	@Query("SELECT fp FROM FundPriceEntity fp WHERE fp.status = 1 AND fp.recordType = 1 AND fp.priceType = 1 AND fp.fund.fdsId = :fundId AND "
			+ "fp.date0 = (SELECT MIN(date0) from FundPriceEntity WHERE status = 1 AND fund.fdsId = :fundId AND record_type = 1 AND priceType = 1)")
	FundPriceEntity findMinFundPrice(@Param("fundId") String fundId);

	@Query("SELECT count(fp) FROM FundPriceEntity fp WHERE fp.status = 1 AND fp.recordType = 1 AND fp.fund.fdsId = :fundId")
	int countFundPrice(@Param("fundId") String fundId);

	@Query("SELECT count(1) FROM FundPriceEntity fp WHERE fp.fund.fdsId = :fundId")
	int countLinkedFundPrice(@Param("fundId") String fundId);
	
	@Query("SELECT fp FROM FundPriceEntity fp WHERE fp.status = 1 AND fp.recordType = 1 AND fp.fund.fdsId = :fundId and date0 <= :maxDate and fp.priceType in :types ORDER BY fp.date0 DESC")
	Page<FundPriceEntity> findLastFundPricesBefore(@Param("fundId") String fundId, @Param("types") List<Integer> types, @Param("maxDate") Date maxDate, Pageable pageable);
	
	@Query("SELECT count(1) FROM FundPriceEntity fp WHERE fp.price=100 AND fp.status = 1 AND fp.recordType = 1 AND fp.fund.fdsId = :fundId and date0 = :paymentDate and fp.priceType in :types ")
	int countVniByDate(@Param("fundId") String fundId,  @Param("types") List<Integer> types, @Param("paymentDate") Date paymentDate);
	
	@Query("SELECT count(1) FROM FundPriceEntity fp WHERE fp.status = 1 AND fp.recordType = 1 AND fp.fund.fdsId = :fundId and date0 <= :paymentDate and fp.priceType in :types ")
	int countVniBeforeDate(@Param("fundId") String fundId,  @Param("types") List<Integer> types, @Param("paymentDate") Date paymentDate);
	
}
