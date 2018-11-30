package lu.wealins.liability.services.core.persistence.repository; 
import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.liability.services.core.persistence.entity.BankAccountEntity; 
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {
	BankAccountEntity findByBkaIdAndClientCliId(Long accountId, Integer clientId);
}
