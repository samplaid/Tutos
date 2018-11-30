package lu.wealins.liability.services.core.persistence.repository; 
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.PolicyCoverageEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;

public interface PolicyCoverageRepository extends JpaRepository<PolicyCoverageEntity, String> {
	
	public PolicyCoverageEntity findByPolicyAndCoverage(PolicyEntity policy, int coverage);
	
	@Modifying
	@Query("UPDATE PolicyCoverageEntity pc SET pc.livesType = :livesType WHERE pc.policy.polId = :polId")
	void updateLivesType(@Param("polId") String polId, @Param("livesType") int livesType);

	List<PolicyCoverageEntity> findByPolicyAndStatus(PolicyEntity policyId, int status);
	
	@Query("SELECT pc FROM PolicyCoverageEntity pc, ProductValueEntity pv WHERE pc.productLine = pv.productLine.prlId "
			+ "AND pc.policyId = :policy "
			+ "AND pv.control = :control "
			+ "AND pv.numericValue = :numericValue ")
	public List<PolicyCoverageEntity> findByPolicyAndProductValue_ControlAndProductValue_NumericValue(@Param("policy") String policy, @Param("control") String control, @Param("numericValue") BigDecimal numericValue);

	public PolicyCoverageEntity findFirstByStatusAndPolicyIdOrderByCoverageDesc(int status, String policyId);

	@Query("SELECT CASE WHEN COUNT(pc) > 0 THEN true ELSE false END FROM PolicyCoverageEntity pc WHERE pc.status = :status and pc.policyId = :policyId")
	boolean hasPendingCoverage(@Param("policyId") String policyId, @Param("status") int status);

	PolicyCoverageEntity findByPolicyIdAndCoverage(String policyId, int coverage);
}
