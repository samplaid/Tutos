package lu.wealins.webia.services.core.persistence.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lu.wealins.webia.services.core.persistence.entity.AccountPaymentEntity;

@Repository
public interface AccountPaymentRepository extends JpaRepository<AccountPaymentEntity, Long> {

	AccountPaymentEntity findByTypeAccountAndBicAndCurrency(String typeAccount, String bic, String currency);

	AccountPaymentEntity findByCurrencyAndIsDefault(String currency, Boolean isDefault);

	Collection<AccountPaymentEntity> findByTypeAccountAndBicAndDepositAccount(String typeAccount, String bic, String depositAccount);
}
