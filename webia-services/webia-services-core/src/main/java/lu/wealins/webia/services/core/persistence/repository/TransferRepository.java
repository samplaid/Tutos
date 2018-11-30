package lu.wealins.webia.services.core.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.webia.services.core.persistence.entity.TransferEntity;

public interface TransferRepository extends JpaRepository<TransferEntity, Long> {

	@Query("SELECT DISTINCT o.brokerId FROM TransferEntity o WHERE o.statementId = :statementId")
	List<String> findDistinctTransfersIdByStatement(@Param("statementId") Long statementId);

	Collection<TransferEntity> findAllByBrokerId(String agentId);

	Collection<TransferEntity> findAllByStatementIdAndBrokerId(Long statementId, String agentId);

	Collection<TransferEntity> findByTransferStatusInAndTransferCdInAndTransferType(List<String> statusList, List<String> transferCd, String transferType);

	@Query("SELECT t FROM TransferEntity t WHERE t.statementId = :statementId")
	Collection<TransferEntity> findPaymentSlipsByStatementId(@Param("statementId") Long statementId);

}
