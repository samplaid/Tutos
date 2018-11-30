package lu.wealins.liability.services.core.persistence.repository; 
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.RateEntity; 
public interface RateRepository extends JpaRepository<RateEntity, Long> {
	
	/**
	 * For admin fee, when com_rate is null. Get com_rate step by step method.
	 * 
	 * @param polId
	 * @param agtId
	 * @param effectiveDate
	 * @return
	 */
	@Query("SELECT MAX(r.rateValueNumber) FROM RateEntity r, PolicyEntity pol WHERE pol.polId = :polId "
			+ "AND r.rateDefinitionId = (SELECT MAX(rd.rdfId) FROM RateDefinitionEntity rd WHERE rd.type = 998 "
			+ "AND rd.details like concat('%', TRIM(:agtId), '%') "
			+ "AND rd.rateValues != 0 "
			+ "AND rd.shortId like concat(TRIM(:polId), '%')) "
			+ "AND DATEDIFF(YEAR, pol.dateOfCommencement, :effectiveDate) < cast(r.value1 AS int)")
	public	List<BigDecimal> findRateComStepByStep(@Param("polId") String polId, @Param("agtId") String agtId, @Param("effectiveDate") Date effectiveDate);
} 
