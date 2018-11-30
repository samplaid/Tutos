package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lu.wealins.webia.services.core.persistence.entity.SasOrderEntity;
import lu.wealins.webia.services.core.persistence.entity.SasOrderFIDEntity;

/**
 * The SAS order FID repository
 * 
 * @See SasOrderFIDEntity
 * @author xqt5q
 *
 */
@Repository
public interface SasOrderFIDRepository extends JpaRepository<SasOrderFIDEntity, Long> {

	@Query("select o from SasOrderFIDEntity o where o.sendDate is null and o.quantity != 0 order by portfolioCode, transactionType")
	List<SasOrderFIDEntity> findOrderToExport();

	//@Query("select o from SasOrderEntity o where o.lineId = :lineId and o.isEstimated = :isEstimated and o.transactionType = :transactionType")
	//List<SasOrderEntity> findByLineIdAndEstimatedFlagAndTransactionType(@Param("lineId") String lineId, @Param("isEstimated") String isEstimated, @Param("transactionType") String transactionType);
	
	@Query("select o from SasOrderFIDEntity o where o.transactionId = :transactionId")
	List<SasOrderFIDEntity> findByTransactionId(@Param("transactionId") String transactionId);
}
