package lu.wealins.webia.services.core.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lu.wealins.webia.services.core.persistence.entity.MathematicalReserveEntity;
import lu.wealins.webia.services.core.persistence.entity.SapAccountingRowNoEntity;

/**
 * The MathematicalReserve repository
 * 
 * @See MathematicalReserveEntity
 * @author xqt5q
 *
 */
@Repository
public interface MathematicalReserveRepository extends JpaRepository<MathematicalReserveEntity, Long> {

	@Query("SELECT mre FROM MathematicalReserveEntity mre WHERE mre.mode LIKE :mode AND mre.calculDate = :date")
	Page<MathematicalReserveEntity> findByModeAndDate(@Param("mode") String mode, @Param("date") Date date, Pageable pageable);
	
	@Query("SELECT mre FROM MathematicalReserveEntity mre WHERE mre.mode LIKE :mode AND mre.extractionDate IS NULL")
	Page<MathematicalReserveEntity> findMathematicalReserveNotExported(@Param("mode") String mode, Pageable pageable);
	
	@Query(nativeQuery = true, name = "MathematicalReserveEntity.findMathematicalReserveToExport")
	List<SapAccountingRowNoEntity> findMathematicalReserveToExport(@Param("mode") String mode);
	
	@Modifying
	@Query("update MathematicalReserveEntity mre set mre.extractionDate = :date where mre.id IN :ids")
	void updateExportDate(@Param("date") Date date, @Param("ids") List<Long> ids);
	
	@Modifying
	@Query("delete FROM MathematicalReserveEntity mre WHERE mre.calculDate = :date AND mre.mode = :mode")
	void deleteByModeAndCalculDate(@Param("date") Date date, @Param("mode") String mode);
}
