package lu.wealins.webia.services.core.persistence.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.common.dto.webia.services.constantes.CommissionConstant;
import lu.wealins.webia.services.core.persistence.entity.CommissionToPayEntity;

public interface CommissionToPayRepository extends JpaRepository<CommissionToPayEntity, Long> {
	
	@Query("SELECT ctp FROM CommissionToPayEntity ctp WHERE ctp.transferId = null  "
			+ "AND ctp.comType = :comType ")
	List<CommissionToPayEntity> findByComType(@Param("comType") String comType);
	
	@Query("SELECT ctp FROM CommissionToPayEntity ctp WHERE ctp.transferId = null  "
			+ "AND ctp.comType = :comType ")
	Page<CommissionToPayEntity> findByComType(@Param("comType") String comType, Pageable pageable);
	
	@Query("SELECT COUNT(ctp) FROM CommissionToPayEntity ctp WHERE ctp.originId = :atrId  ")
	long countByOriginId(@Param("atrId") long atrId);

	List<CommissionToPayEntity> findByOriginIdAndComTypeAndComCurrencyAndTransferIdIsNotNullAndStatementIdIsNotNull(Long originId, String comType, String comCurrency);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM CommissionToPayEntity c WHERE c.transaction0 = :transaction0 "
			+ "AND c.statementId IS NULL "
			+ "AND c.transferId IS NULL")
	void deleteWithTransaction0WhereNoExported(@Param("transaction0") Long transaction0);

	List<CommissionToPayEntity> findByAgentId(String value);
	
	@Query("SELECT DISTINCT ctp.agentId, ctp.comCurrency FROM CommissionToPayEntity ctp WHERE ctp.accountingMonth <= :period "
			+ "AND ctp.comType IN :type "
			+ "AND ctp.statementId IS NULL "
			+ "AND ctp.agentId != (SELECT ape.value FROM ApplicationParameterEntity ape WHERE ape.code = 'BROKER_BIL')")
	List<Object[]> findByTypeAndPeriodDistinctByAgent(@Param("type") List<String> type, @Param("period") String period);
	
	@Query("SELECT DISTINCT ctp.agentId, ctp.comCurrency FROM CommissionToPayEntity ctp WHERE ctp.accountingMonth <= :period "
			+ "AND ctp.comType IN :type "
			+ "AND ctp.agentId = :agent "
			+ "AND ctp.statementId IS NULL")
	List<Object[]> findByTypeAndPeriodAndAgentDistinctByAgent(@Param("type") List<String> type, @Param("period") String period, @Param("agent") String agent);
	
	@Query("SELECT ctp FROM CommissionToPayEntity ctp WHERE ctp.accountingMonth <= :period "
			+ "AND ctp.comType IN :type "
			+ "AND ctp.agentId = :agent "
			+ "AND ctp.comCurrency = :currency "
			+ "AND ctp.statementId IS NULL")
	List<CommissionToPayEntity> findByTypeAndPeriodAndAgentAndCurrency(@Param("type") List<String> statementTypeValue, @Param("period") String periodValue, @Param("agent") String brokerId, @Param("currency") String currency);

	/**
	 * Find all reconcilable commission by type. The {@code PORTFOLIO} commission type is mapped to the set of values {@code 'ADM','SWITCH','SURR','OPCVM','PRADM'} and the {@code ENTRY} type takes the
	 * same value.
	 * 
	 * @param commissionTypes the commission type.
	 * @return a list of reconcilable commission.
	 */
	@Query("SELECT ctp "
			+ "FROM CommissionToPayEntity ctp "
			+ "WHERE (ctp.status = '" + CommissionConstant.VALIDATED + "' OR ctp.status is NULL) "
			+ "AND ctp.comType IN :commissionTypes")
	List<CommissionToPayEntity> findReconcilableCommissionByType(@Param("commissionTypes") List<String> commissionTypes);

	List<CommissionToPayEntity> findByTransferId(Long transfertId);

	List<CommissionToPayEntity> findByAgentIdAndComAmountAndTransaction0AndStatementIdIsNullAndTransferIdIsNull(String agentId, BigDecimal comAmount, Long transaction0);

}
