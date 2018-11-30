package lu.wealins.webia.services.core.persistence.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.webia.services.core.persistence.entity.TransactionTaxEntity;

public interface TransactionTaxRepository extends JpaRepository<TransactionTaxEntity, Long> {

	@Query("select t from TransactionTaxEntity t where t.status in :statusList order by t.policyEffectDate, t.id asc")
	List<TransactionTaxEntity> findAllByStatusIn(@Param("statusList") List<Integer> statusList);

	@Query("select t from TransactionTaxEntity t where t.status in :statusList and  t.origin  = :origin order by t.policyEffectDate, t.id asc")
	List<TransactionTaxEntity> findAllByStatusInAndOrigin(@Param("statusList") List<Integer> statusList,
			@Param("origin") String origin);


	@Query("select t from TransactionTaxEntity t where t.originId = :originId")
	TransactionTaxEntity findByOriginId(@Param("originId") BigDecimal originId);

	@Query("select t.policy from TransactionTaxEntity t where t.origin = :origin and t.status = :status")
	List<String> findByOriginAndPolicyStatus(@Param("origin") String origin, @Param("status") Integer status);

	@Query("select max(t.id) from TransactionTaxEntity t where t.policy = :policy and t.transactionType in ('PREM','WITH', 'SURR') and t.status !=3 ")
	Integer getLastInsertedTransactionIdByPolicy(@Param("policy") String policy);

	@Query("select t from TransactionTaxEntity t where t.policy = :policy and t.id > :id")
	List<TransactionTaxEntity> getTransactionByPolicyAndId(@Param("policy") String policy, @Param("id") Long id);

	List<TransactionTaxEntity> findAllByIdInAndStatus(List<Long> ids, Integer status);

	@Query(" select t from TransactionTaxEntity t where t.policy = :policy order by t.policyEffectDate asc")
	List<TransactionTaxEntity> findByPolicy(@Param("policy") String policy);

	@Query(" select sum (t.transactionNetAmount) from TransactionTaxEntity t where t.originId = :policy and t.id >= :transactionId and t.transactionType in :types ")
	BigDecimal findByTotalAmountByTypes(@Param("policy") String policy, @Param("transactionId") Long transactionId,
			@Param("types") List<String> types);

}
