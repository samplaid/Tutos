package lu.wealins.liability.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyFundHoldingEntity;

public interface PolicyFundHoldingRepository extends JpaRepository<PolicyFundHoldingEntity, Long> {

	public List<PolicyFundHoldingEntity> findByPolicy(PolicyEntity policy);

	@Query("select pfh from PolicyFundHoldingEntity pfh where pfh.policy.polId = :policy and pfh.status = 1")
	public List<PolicyFundHoldingEntity> findByPolicyId(@Param("policy") String policy);

	@Query("select pfh from PolicyFundHoldingEntity pfh where pfh.policy.polId = :policy and pfh.status = 1 and pfh.holdingNo = :holdingNumero")
	public List<PolicyFundHoldingEntity> findByPolicyIdAndHoldingNumber(@Param("policy") String policy, @Param("holdingNumero") int holdingNumero);

	@Query("select pfh from PolicyFundHoldingEntity pfh where pfh.policy.polId = :policy and pfh.fund.fdsId = :fund and pfh.status = 1")
	public List<PolicyFundHoldingEntity> findByPolicyIdAndFund(@Param("policy") String policy, @Param("fund") String fund);

	@Query("select pfh from PolicyFundHoldingEntity pfh where pfh.policy.polId = :policy and pfh.status = 1 and pfh.units > 0")
	public List<PolicyFundHoldingEntity> findByPolicy(@Param("policy") String policy);
}
