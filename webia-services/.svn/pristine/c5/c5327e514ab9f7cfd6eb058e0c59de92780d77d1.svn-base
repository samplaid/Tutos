package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lu.wealins.webia.services.core.persistence.entity.SasOrderEntity;

/**
 * The SAS order repository
 * 
 * @See SasOrderEntity
 * @author xqv60
 *
 */
@Repository
public interface SasOrderRepository extends JpaRepository<SasOrderEntity, Long> {

	@Query("select o from SasOrderEntity o where o.sendDate is null and ( "
			+ "((o.quantity != 0 and o.quantity IS NOT NULL) and (o.amount = 0 or o.amount IS NULL)) or "
			+ "((o.quantity = 0 or o.quantity IS NULL) and (o.amount != 0 and o.amount IS NOT NULL)) or "
			+ "((o.quantity != 0 and o.quantity IS NOT NULL) and (o.amount != 0 AND o.amount IS NOT NULL))"
			+ ") order by portfolio, securityId, transactionType, navDate")
	List<SasOrderEntity> findOrderToExport();

	@Query("select o from SasOrderEntity o where o.lineId = :lineId and o.isEstimated = :isEstimated and o.transactionType = :transactionType")
	List<SasOrderEntity> findByLineIdAndEstimatedFlagAndTransactionType(@Param("lineId") String lineId, @Param("isEstimated") String isEstimated, @Param("transactionType") String transactionType);

}
