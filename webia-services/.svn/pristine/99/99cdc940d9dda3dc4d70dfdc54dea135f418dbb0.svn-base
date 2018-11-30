package lu.wealins.webia.services.core.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.webia.services.core.persistence.entity.ReportingComEntity;

public interface ReportingComRepository extends JpaRepository<ReportingComEntity, Long> {
	
	@Query("SELECT COUNT(report) FROM ReportingComEntity report WHERE report.originId = :atrId  ")
	long countByOriginId(@Param("atrId") long atrId);
	
	@Query("SELECT rep FROM ReportingComEntity rep WHERE rep.exportDt IS NULL "
			+ "AND rep.reportId = :reportId "
			+ "AND rep.reportComId > :lastId "
			+ "ORDER BY rep.reportComId ASC")
	Page<ReportingComEntity> findByReportIdAndExportDateIsNull(@Param("lastId") Long lastId, @Param("reportId") Long reportId, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("delete from ReportingComEntity c WHERE c.originId = :originId "
			+ "AND c.reportId = :reportId "
			+ "AND c.exportDt IS NULL")
	void deleteByOriginIdNotExported(@Param("originId") Long l, @Param("reportId") Long reportId);

	List<ReportingComEntity> findByOriginIdAndCurrencyAndExportDtIsNotNull(Long originId, String currency);
	
	@Transactional
	@Modifying
	@Query("UPDATE ReportingComEntity c "
			+ "SET c.reportId = NULL "
			+ "WHERE c.reportId = :reportId "
			+ "AND c.comDt > :comDt "
			+ "AND c.exportDt IS NULL")
	void updateReportIdByReportIdAndComDt(@Param("reportId") Long reportId, @Param("comDt") Date comDt);
	
	@Transactional
	@Modifying
	@Query("UPDATE ReportingComEntity c "
			+ "SET c.reportId = :reportId "
			+ "WHERE c.comDt <= :comDt "
			+ "AND c.reportId IS NULL "
			+ "AND c.exportDt IS NULL")
	void updateReportIdByComDt(@Param("reportId") Long reportId, @Param("comDt") Date comDt);
}
