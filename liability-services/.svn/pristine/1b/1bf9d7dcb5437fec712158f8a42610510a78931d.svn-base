package lu.wealins.liability.services.core.persistence.repository; 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.liability.services.core.persistence.entity.AccountBalanceEntity;
import lu.wealins.liability.services.core.persistence.entity.AccountEntity;

public interface AccountBalanceRepository extends JpaRepository<AccountBalanceEntity, Long> {
	
	public List<AccountBalanceEntity> findByAccount(AccountEntity ae);
} 
