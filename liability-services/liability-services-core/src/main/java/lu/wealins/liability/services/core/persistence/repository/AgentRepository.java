package lu.wealins.liability.services.core.persistence.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.common.dto.liability.services.PaymentMethodsDTO;
import lu.wealins.liability.services.core.persistence.entity.AgentEntity;

public interface AgentRepository extends JpaRepository<AgentEntity, String>, JpaSpecificationExecutor<AgentEntity> {

	AgentEntity findByAgtId(String agtId);

	List<AgentEntity> findByAgentCategoryId(String agentCategoryId);

	@Query("select count(a.agtId) from AgentEntity a")
	Long findCountAll();

	// @Query(value = "SELECT a FROM AGENTS a INNER JOIN FUNDS f ON a.AGT_ID = f.SALES_REP WHERE f.FDS_ID = :fdsId", nativeQuery = true)
	@Query(nativeQuery = true, name = "AgentEntity.findSalesManager")
	AgentEntity findSalesManager(@Param("fdsId") String fdsId);

	@Query(value = "select a from AgentEntity a where a.agentCategory.acaId = ?2"
			+ " and (upper(a.name) like %?1% or upper(a.agtId) like %?1%) "
			+ " and a.status = ?3"
			+ " order by trim(leading from a.name), a.agtId")
	Page<AgentEntity> findByNameAndAgentIdAndCategoryAndStatus(String search, String cat, int status, Pageable pageable);

	@Query(value = "select a from AgentEntity a where a.agentCategory.acaId = ?2"
			+ " and (upper(a.name) like %?1% or upper(a.agtId) like %?1%) "
			+ " order by trim(leading from a.name), a.agtId")
	Page<AgentEntity> findByNameAndAgentIdAndCategory(String search, String cat, Pageable pageable);

	@Query(value = "select a from AgentEntity a where trim(upper(a.paymentAccountBic)) = trim(upper(?1)) and a.agentCategory.acaId='DB' and a.status=1")
	List<AgentEntity> findByPaymentAccountBic(String bic);

	@Transactional
	@Modifying
	@Query("DELETE from AgentEntity ag WHERE ag.agtId LIKE :agentId")
	void removeLike(@Param("agentId") String agentId);
	
	@Query(value = "select a from AgentEntity a where (upper(a.name) like :search or upper(a.agtId) like :search)"
			+ " and a.agentCategory.acaId in :categories"
			+ " and a.status = :status"
			+ " order by case"
			+ " when a.agentCategory.acaId = 'PSI' then 0"
			+ " when a.agentCategory.acaId = 'IFI' then 1"
			+ " else 2"
			+ " end"
			+ " ,a.name, a.agtId")
	Page<AgentEntity> findFinancialAdvisors(@Param("search") String search, @Param("categories") List<String> categories, @Param("status") int status, Pageable pageable);

	/**
	 * Retrieves all agents whose commissions have already payed.
	 * 
	 * @param agentIds list of agent id.
	 * @return set of agent.
	 */
	@Query("SELECT agt FROM AgentEntity agt WHERE agt.agentCategoryId = 'BK' AND agt.paymentCommission = 1 AND agt.desiredReport = 1 AND agt.paymentAccountBic IS NOT NULL AND agt.paymentMethod IS NOT NULL AND agt.agtId IN :agentIds")
	List<AgentEntity> findPayableCommissionAgentOwnerByIds(@Param("agentIds") List<String> agentIds);

	@Query(value = "SELECT a " + 
            "FROM AgentEntity a " +
	        "WHERE ((convert(date, a.createdDate) > convert(date, :creationDate)) OR (convert(date, a.createdDate) = convert(date, :creationDate) AND convert(time, a.createdTime) >= convert(time, :creationDate)))")
	Page<AgentEntity> findByCreationDate(@Param("creationDate") Date creationDate, Pageable pageable);
	
	@Query(value = "SELECT a " + 
            "FROM AgentEntity a " +
	        "WHERE ((convert(date, a.createdDate) > convert(date, :creationDate)) OR (convert(date, a.createdDate) = convert(date, :creationDate) AND convert(time, a.createdTime) >= convert(time, :creationDate))) " +
            "AND a.createdBy = :createdBy")
	Page<AgentEntity> findByCreationDate(@Param("creationDate") Date creationDate, @Param("createdBy") String createdBy, Pageable pageable);

	@Query("SELECT NEW lu.wealins.common.dto.liability.services.PaymentMethodsDTO(trim(f.fdsId), a.paymentMethod) from FundEntity f inner join f.depositBankAgent a WHERE f.fdsId IN :fundIds")
	Collection<PaymentMethodsDTO> findByFundIds(@Param("fundIds") Collection<String> fundIds);
}
