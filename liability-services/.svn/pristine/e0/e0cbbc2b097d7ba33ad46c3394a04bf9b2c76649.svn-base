package lu.wealins.liability.services.core.persistence.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;

public interface PolicyRepository extends JpaRepository<PolicyEntity, String>, JpaSpecificationExecutor<PolicyEntity> {

	@Query("select count(distinct p.polId) from PolicyEntity p JOIN p.policyFundHoldings h JOIN h.fund f "
			+ " WHERE f.fdsId = :fdsId AND (p.status = 1 OR (p.status = 2 AND p.subStatus = 2)) "
			+ " AND h.status = 1")
	public int countPoliciesByFund(@Param("fdsId") String fdsId);
	
	@Query("select distinct p.polId from PolicyEntity p JOIN p.policyFundHoldings h JOIN h.fund f "
			+ " WHERE f.fdsId = :fdsId AND (p.status = 1 OR (p.status = 2 AND p.subStatus = 2)) " + " AND h.status = 1"
			+ " group by p.polId ")
	public List<String> policiesByFund(@Param("fdsId") String fdsId);
	
	@Query("SELECT p from PolicyEntity p "
			+ "WHERE p.status != 5 "
			+ "AND p.polId NOT IN (SELECT p.polId " + 
			"FROM PolicyEntity p, TransactionEntity tr "
			+ "where tr.policy = p " + 
			"AND p.status = 2 "
			+ "AND tr.eventType in (4,17,21)"
			+ "group by p.polId having max(tr.effectiveDate) <= :date) ORDER BY p.polId")
	public Page<PolicyEntity> findPoliciesForMathematicalReserveFunctionOfDate(@Param("date") Date date, Pageable pageable);

	@Query("Select p.polId from PolicyEntity p where p.polId like :policyId%")
	public List<String> findAllVersionOfPolicyId(@Param("policyId") String policyId);

	@Query("Select p.currency from PolicyEntity p where p.polId like :policyId")
	public String findPolicyCurrency(@Param("policyId") String policyId);

	@Query(value = "select top 1 numeric_value from product_values WHERE control='polfee' and prc_id like %:policyId% order by PRC_ID DESC ", nativeQuery = true)
	public BigDecimal findEntryFees(@Param("policyId") String policyId);

	@Query("Select p.brokerRefContract from PolicyEntity p where p.status != 5 AND p.polId like :policyId%")
	public String findBrokerRefContractWithPolicyId(@Param("policyId") String policyId);

	@Query("select distinct p.polId from PolicyEntity p JOIN p.policyAgentShares pas"
			+ " WHERE pas.agent.agtId = :agtId AND (p.status = 1 OR (p.status = 2 AND p.subStatus = 2)) " + " AND pas.status = 1")
	public List<String> policiesByAgent(@Param("agtId") String agtId);

	@Query("select trim(p.polId) from PolicyEntity p where p.status = 1 AND p.polId like %:query%")
	public List<String> findIdsByPolicyIdLike(@Param("query") String query, Pageable pageable);
}
