package lu.wealins.liability.services.core.persistence.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.liability.services.core.persistence.entity.CliPolRelationshipEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;

public interface CliPolRelationshipRepository extends JpaRepository<CliPolRelationshipEntity, String> {

	@Query("SELECT cpr FROM CliPolRelationshipEntity cpr WHERE cpr.clientId = ?1 ORDER BY cpr.status ASC")
	List<CliPolRelationshipEntity> findByClientId(Integer clientId);

	@Query("SELECT cpr FROM CliPolRelationshipEntity cpr WHERE cpr.clientId = ?1 AND (cpr.endDate < '1800-01-01' OR cpr.endDate > ?2 ) ORDER BY cpr.status ASC")
	List<CliPolRelationshipEntity> findActiveByClientIdAndDate(Integer clientId, Date date);

	@Query("SELECT cpr FROM CliPolRelationshipEntity cpr WHERE cpr.clientId = :clientId AND cpr.status IN :statuses ORDER BY cpr.policyId")
	List<CliPolRelationshipEntity> findByClientIdAndStatuses(@Param("clientId") Integer clientId, @Param("statuses") List<Integer> statuses);

	@Query("SELECT cpr FROM CliPolRelationshipEntity cpr WHERE cpr.clientId = :clientId AND cpr.type IN :types AND cpr.status IN :statuses ORDER BY cpr.policyId")
	List<CliPolRelationshipEntity> findByClientIdAndTypesAndStatuses(@Param("clientId") Integer clientId, @Param("types") List<Integer> types, @Param("statuses") List<Integer> statuses);

	@Query("SELECT cpr FROM CliPolRelationshipEntity cpr WHERE cpr.clientId = :clientId AND cpr.type IN :types AND (cpr.endDate < '1800-01-01' OR cpr.endDate > :date ) ORDER BY cpr.policyId")
	List<CliPolRelationshipEntity> findActiveByClientIdAndTypesAndDate(@Param("clientId") Integer clientId, @Param("types") List<Integer> types, @Param("date") Date date);

	List<CliPolRelationshipEntity> findAllByPolicyIdAndTypeAndStatusOrderByTypeNumberAsc(String policyId, int type, int status);

	@Query("SELECT cpr FROM CliPolRelationshipEntity cpr WHERE cpr.policyId = :policyId AND cpr.clientId = :clientId AND cpr.type IN :types")
	Collection<CliPolRelationshipEntity> findAllByPolicyIdAnClientIdAndTypes(@Param("policyId") String policyId, @Param("clientId") Integer clientId, @Param("types") List<Integer> types);

	@Query("SELECT cpr FROM CliPolRelationshipEntity cpr WHERE cpr.policyId = :policyId AND cpr.clientId NOT IN :excludedClientIds AND cpr.type IN :types AND (cpr.endDate < '1800-01-01' OR cpr.endDate > :endDate)")
	Collection<CliPolRelationshipEntity> findEnabledByPolicyIdAndNotClientIdAndTypes(@Param("policyId") String policyId, @Param("excludedClientIds") List<Integer> excludedClientIds,
			@Param("types") List<Integer> types, @Param("endDate") Date endDate);

	@Query("SELECT cpr FROM CliPolRelationshipEntity cpr WHERE cpr.policyId = :policyId AND cpr.type IN :types AND (cpr.endDate < '1800-01-01' OR cpr.endDate > :endDate)")
	Collection<CliPolRelationshipEntity> findEnabledByPolicyIdAndTypes(@Param("policyId") String policyId, @Param("types") List<Integer> types, @Param("endDate") Date endDate);

	@Query("SELECT cpr FROM CliPolRelationshipEntity cpr WHERE cpr.modifyProcess = :modifyProcess")
	Collection<CliPolRelationshipEntity> findAllByModifyProcess(@Param("modifyProcess") String modifyProcess);

	@Transactional
	@Modifying
	@Query("DELETE from CliPolRelationshipEntity cpr WHERE cpr.modifyProcess = :modifyProcess and cpr.activeDate = cpr.endDate")
	void deleteDisabledWithModifyProcess(@Param("modifyProcess") String modifyProcess);

	@Query("select max(cpr.cprId) FROM CliPolRelationshipEntity cpr where cpr.cprId like :cprId%")
	String findLastCprId(@Param("cprId") String cprId);

	CliPolRelationshipEntity findByPolicyIdAndTypeAndTypeNumberAndCoverageAndClientId(String policyId, int type, Integer typeNumber, Integer coverage, int clientId);

	long countByPolicyIdAndTypeAndTypeNumberAndCoverage(String policyId, int type, Integer typeNumber, Integer coverage);

	@Query("SELECT p1 FROM CliPolRelationshipEntity cpr1, PolicyEntity p1 WHERE cpr1.policy.polId = p1.polId AND cpr1.client.cliId = :clientId AND cpr1.type IN (4,5,6) AND (cpr1.endDate < '1800-01-01' OR cpr1.endDate > current_date() ) AND ( "
			// check if client is the last living assured linked to the policy
			+ " NOT EXISTS (select cpr2 from CliPolRelationshipEntity cpr2"
			+ "		where cpr2.policy.polId=cpr1.policy.polId and cpr2.type in (4,5,6) and (cpr2.endDate < '1800-01-01' or cpr2.endDate > current_date()) and cpr2.client.cliId != cpr1.client.cliId "
			+ " 		and not exists (select ccd from ClientClaimsDetailEntity ccd where ccd.client.cliId=cpr2.client.cliId and (ccd.dateOfDeath > '1800-01-01' or ccd.dateOfDeath < current_date())) )"
			// check if policy settlement is not trigger on first death (event if there is more than 1 insured)
			+ " OR EXISTS (select pc from PolicyCoverageEntity pc where pc.policy.polId=cpr1.policy.polId and pc.livesType = 2))")
	List<PolicyEntity> getPoliciesSettlementInCaseOfDeath(@Param("clientId") Integer clientId);

	Collection<CliPolRelationshipEntity> findAllByModifyProcessAndType(String workflowItemId, int type);

	@Query("SELECT cpr FROM CliPolRelationshipEntity cpr WHERE cpr.policyId = :policyId AND cpr.type IN :types AND cpr.endDate < '1800-01-01' ORDER BY cpr.typeNumber, cpr.clientId")
	List<CliPolRelationshipEntity> findActiveByPolicyIdAndTypes(@Param("policyId") String policyId, @Param("types") List<Integer> types);

	@Query("SELECT cpr FROM CliPolRelationshipEntity cpr WHERE cpr.clientId = :clientId AND cpr.policyId = :policyId AND cpr.type IN :types AND cpr.endDate < '1800-01-01' ORDER BY cpr.policyId")
	List<CliPolRelationshipEntity> findActiveByClientIdAndPolicyIdAndTypes(@Param("clientId") Integer clientId, @Param("policyId") String policyId, @Param("types") List<Integer> types);

	@Query("SELECT cpr FROM CliPolRelationshipEntity cpr WHERE cpr.policyId = :policyId AND cpr.type = :type AND cpr.endDate < '1800-01-01'")
	List<CliPolRelationshipEntity> findActiveByPolicyIdAndType(@Param("policyId") String policyId, @Param("type") Integer type);

	@Query("SELECT cpr FROM CliPolRelationshipEntity cpr WHERE cpr.policyId = :policyId ORDER BY cpr.typeNumber, cpr.clientId")
	List<CliPolRelationshipEntity> findByPolicyId(@Param("policyId") String policyId);

}
