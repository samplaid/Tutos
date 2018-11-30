package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.webia.services.core.persistence.entity.TransactionTaxDetailsEntity;

public interface TransactionTaxDetailsRepository extends JpaRepository<TransactionTaxDetailsEntity, Long> {
	List<TransactionTaxDetailsEntity> findAllByTransactionTaxId(long id);
}
