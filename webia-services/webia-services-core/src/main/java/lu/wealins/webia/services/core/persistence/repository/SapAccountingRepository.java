package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lu.wealins.webia.services.core.persistence.entity.SapAccountingEntity;

@Repository
public interface SapAccountingRepository extends JpaRepository<SapAccountingEntity, Long> {

	@Query("SELECT sac FROM SapAccountingEntity sac WHERE sac.exportDate = null  "
			+ "AND sac.origin = :origin ORDER BY sac.pieceNb ASC, currency")
	Page<SapAccountingEntity> findByExportDateIsNull(@Param("origin") String origin, Pageable pageable);

	@Modifying
	@Query("DELETE FROM  SapAccountingEntity sac where sac.idSapAcc in :ids")
	void deleteSapAccountingsWithIds(@Param("ids") List<Long> ids);

	SapAccountingEntity findByOriginId(Long originId);

	@Query("SELECT DISTINCT sac.originId FROM SapAccountingEntity sac WHERE sac.origin = :origin")
	List<Long> findOriginIdByOrigin(@Param("origin") String origin);

	SapAccountingEntity findByOriginIdAndAccount(Long originId, String account);

	@Query("SELECT sac FROM SapAccountingEntity sac WHERE sac.originId = :originId")
	List<SapAccountingEntity> findByOriginIdForEncashments(@Param("originId") Long originId);

	@Query("SELECT sac FROM SapAccountingEntity sac WHERE sac.originId = :originId AND sac.explain LIKE :fundId%")
	List<SapAccountingEntity> findByOriginIdAndFundIdForEncashments(@Param("originId") Long originId, @Param("fundId") String fundId);

}
