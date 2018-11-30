package lu.wealins.liability.services.core.persistence.repository; 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lu.wealins.liability.services.core.persistence.entity.AccountBalanceEntity;
import lu.wealins.liability.services.core.persistence.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {
	
	public List<AccountEntity> findByName(String name);
	
	public List<AccountEntity> findByAccountBalances(AccountBalanceEntity abe);
	
	@Query("select count(a.accId) from AccountEntity a")
	public Long findCountAll();
}
