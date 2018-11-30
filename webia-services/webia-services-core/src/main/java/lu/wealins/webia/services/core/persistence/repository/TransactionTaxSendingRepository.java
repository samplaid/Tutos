package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.webia.services.core.persistence.entity.TransactionTaxSendingEntity;

public interface TransactionTaxSendingRepository extends JpaRepository<TransactionTaxSendingEntity, Long> {
	List<TransactionTaxSendingEntity> findAllByTransactionTaxId(long id);
}
