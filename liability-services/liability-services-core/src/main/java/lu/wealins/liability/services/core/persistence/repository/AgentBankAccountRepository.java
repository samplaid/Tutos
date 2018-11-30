package lu.wealins.liability.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.AgentBankAccountEntity;

public interface AgentBankAccountRepository extends JpaRepository<AgentBankAccountEntity, Integer> {

	AgentBankAccountEntity findByAgent0(String agent);

	List<AgentBankAccountEntity> findByAgent0AndCommPaymentCurrencyAndStatusNotIn(String agent, String currency, List<Integer> status);

	List<AgentBankAccountEntity> findByAgent0AndIban(String agent, String iban);

	List<AgentBankAccountEntity> findByAgent0AndCommPaymentCurrencyAndBicAndStatusNotIn(String agent, String currency, String bic, List<Integer> excludeStatus);

	@Query(value = "select aba from AgentBankAccountEntity aba where aba.agent0 LIKE :agent")
	List<AgentBankAccountEntity> findByAgentId(@Param("agent") String agent);

}
