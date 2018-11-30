package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lu.wealins.webia.services.core.persistence.entity.SasOrderAggEntity;

/**
 * The SAS order repository
 * 
 * @See SasOrderEntity
 * @author xqv60
 *
 */
@Repository
public interface SasOrderAggRepository extends JpaRepository<SasOrderAggEntity, Long> {
	
	@Query("select o from SasOrderAggEntity o where o.lineId = :lineId and o.isEstimated = :isEstimated and o.transactionType = :transactionType")
	List<SasOrderAggEntity> findByLineIdAndEstimatedFlagAndTransactionType(@Param("lineId") String lineId, @Param("isEstimated") String isEstimated, @Param("transactionType") String transactionType);

}
