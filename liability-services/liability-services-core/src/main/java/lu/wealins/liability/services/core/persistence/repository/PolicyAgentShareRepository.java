package lu.wealins.liability.services.core.persistence.repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.liability.services.core.persistence.entity.PolicyAgentShareEntity;

public abstract interface PolicyAgentShareRepository
		extends JpaRepository<PolicyAgentShareEntity, Long>, JpaSpecificationExecutor<PolicyAgentShareEntity> {
	public Set<PolicyAgentShareEntity> findByType(Integer paramInteger);

	public Set<PolicyAgentShareEntity> findByPolIdAndType(String polId, Integer paramInteger);

	@Query("SELECT pas FROM PolicyAgentShareEntity pas WHERE pas.polId = :policyId AND pas.type = :type and pas.primaryAgent = :primaryAgent order by pas.coverage desc")
	public Set<PolicyAgentShareEntity> findByPolicyIdAndTypeAndPrimaryAgent(@Param("policyId") String policyId, @Param("type") Integer type, @Param("primaryAgent") Integer primaryAgent);

	@Query("SELECT pas FROM PolicyAgentShareEntity pas WHERE pas.polId = :policyId AND pas.status = 1")
	public Set<PolicyAgentShareEntity> findByPolicyId(@Param("policyId") String policyId);

	@Query("SELECT pas FROM PolicyAgentShareEntity pas WHERE pas.status=1 AND pas.agent.agtId= :agtId AND pas.type = :type")
	public Set<PolicyAgentShareEntity> findByAgentAndType(@Param("agtId") String agtId, @Param("type") Integer type);

	@Query("SELECT DISTINCT pas FROM PolicyAgentShareEntity pas WHERE pas.status=1 AND pas.agent.agtId= :agtId AND pas.type = :type AND pas.coverage = :coverage")
	public Set<PolicyAgentShareEntity> findByAgentAndTypeAndCoverage(@Param("agtId") String agtId, @Param("type") Integer type, @Param("coverage") Integer coverage);

	@Query(nativeQuery = true, name = "PolicyAgentShareEntity.findByAgentAndTypeAndCoverageExternalFunds")
	List<String> findByAgentAndTypeAndCoverageExternalFunds(@Param("agtId") String agtId, @Param("type") Integer type, @Param("coverage") Integer coverage);

	/**
	 * Find percentage of policy agent share with policyId
	 * 
	 * @param policyId
	 * @param polSharesType
	 * @return
	 */
	@Query("SELECT MAX(pol.percentage) FROM AgentEntity ag, PolicyAgentShareEntity pol "
			+ "WHERE pol.agent.agtId = ag.agtId "
			+ "AND pol.type IN :polSharesType "
			+ "AND ag.agentCategoryId = 'BK' "
			+ "AND pol.status = 1 "
			+ "AND pol.polId = :policyId ")
	public List<BigDecimal> findPercentageOfPolAgentShareWithPolicyId(@Param("policyId") String policyId, @Param("polSharesType") List<Integer> polSharesType);

	/**
	 * Find percentage of policy agent share with policyId and the coverage
	 * 
	 * @param coverage
	 * @param policyId
	 * @param polSharesType
	 * @return
	 */
	@Query("SELECT MAX(pol.percentage) FROM AgentEntity ag, PolicyAgentShareEntity pol "
			+ "WHERE pol.agent.agtId = ag.agtId "
			+ "AND pol.type IN :polSharesType "
			+ "AND ag.agentCategoryId = 'BK' "
			+ "AND pol.status = 1 "
			+ "AND pol.coverage = :coverage "
			+ "AND pol.polId = :policyId ")
	public List<BigDecimal> findPercentageOfPolAgentShareWithPolicyIdAndCoverage(@Param("policyId") String policyId, @Param("coverage") int coverage,
			@Param("polSharesType") List<Integer> polSharesType);

	/**
	 * Find subBorker of policy agent share with policyId
	 * 
	 * @param policyId
	 * @param polSharesType
	 * @return
	 */
	@Query("SELECT pol.agent.agtId FROM AgentEntity ag, PolicyAgentShareEntity pol "
			+ "WHERE pol.agent.agtId = ag.agtId "
			+ "AND pol.type IN :polSharesType "
			+ "AND ag.agentCategoryId = 'SB' "
			+ "AND pol.status = 1 "
			+ "AND pol.polId = :policyId ")
	public List<String> findSubBrokerOfPolAgentShareWithPolicyId(@Param("policyId") String policyId, @Param("polSharesType") List<Integer> polSharesType);

	/**
	 * Find percentage of policy agent share with policyId without status
	 * 
	 * @param policyId
	 * @param polSharesType
	 * @return
	 */
	@Query("SELECT MAX(pol.percentage) FROM AgentEntity ag, PolicyAgentShareEntity pol "
			+ "WHERE pol.agent.agtId = ag.agtId "
			+ "AND pol.type IN :polSharesType "
			+ "AND ag.agentCategoryId = 'BK' "
			+ "AND pol.polId = :policyId ")
	public List<BigDecimal> findPercentageOfPolAgentShareWithPolicyIdDesactivate(@Param("policyId") String policyId, @Param("polSharesType") List<Integer> polSharesType);

	@Transactional
	@Modifying
	@Query("DELETE from PolicyAgentShareEntity pas WHERE pas.modifyProcess = :modifyProcess")
	void deleteWithModifyProcess(@Param("modifyProcess") String modifyProcess);

	@Query("SELECT pas FROM PolicyAgentShareEntity pas WHERE pas.modifyProcess=:modifyProcess AND pas.type = :type AND pas.agent.agentCategoryId= :category AND pas.status = :status")
	Collection<PolicyAgentShareEntity> findByModifyProcessAndTypeAndCategoryAndStatus(@Param("modifyProcess") String modifyProcess, @Param("type") int type, @Param("category") String category,
			@Param("status") int status);

	@Query("SELECT pas FROM PolicyAgentShareEntity pas WHERE pas.modifyProcess=:modifyProcess AND pas.type = :type AND pas.agent.agentCategoryId= :category")
	Collection<PolicyAgentShareEntity> findByModifyProcessAndTypeAndCategory(@Param("modifyProcess") String modifyProcess, @Param("type") int type, @Param("category") String category);

	@Query("SELECT pas FROM PolicyAgentShareEntity pas WHERE pas.modifyProcess=:modifyProcess AND pas.type = :type AND pas.status = :status")
	Collection<PolicyAgentShareEntity> findByModifyProcessAndTypeAndStatus(@Param("modifyProcess") String modifyProcess, @Param("type") int type, @Param("status") int status);

	@Query("SELECT pas FROM PolicyAgentShareEntity pas WHERE pas.modifyProcess=:modifyProcess AND pas.status = :status")
	Collection<PolicyAgentShareEntity> findByModifyProcessAndStatus(@Param("modifyProcess") String modifyProcess, @Param("status") int status);

	@Query("SELECT max(pas.pasId) FROM PolicyAgentShareEntity pas")
	Long findMaxPasId();

	@Query("SELECT pas FROM PolicyAgentShareEntity pas where pas.primaryAgent=true and pas.agent.status = 1 and pas.status = 1 and pas.polId = :policyId and pas.type=5 and pas.agent.agentCategory.acaId = 'BK' order by pas.coverage desc")
	List<PolicyAgentShareEntity> findAdvisorFeesAgent(@Param("policyId") String policyId);

	@Query("SELECT pas FROM PolicyAgentShareEntity pas WHERE pas.polId = :policyId AND pas.agent.agtId= :agtId AND pas.type = :type AND pas.coverage = :coverage AND pas.agent.status = 1 AND pas.status=1")
	Optional<PolicyAgentShareEntity> findByPolicyAndAgentAndTypeAndCoverage(@Param("policyId") String policyId, @Param("agtId") String agtId, @Param("type") Integer type,
			@Param("coverage") Integer coverage);

}
