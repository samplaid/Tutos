package lu.wealins.liability.services.core.persistence.repository;

import java.math.BigDecimal;
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

import lu.wealins.liability.services.core.persistence.entity.EstimatedOrderNoEntity;
import lu.wealins.liability.services.core.persistence.entity.FundTransactionEntity;
import lu.wealins.liability.services.core.persistence.entity.TransactionGroupedByFundNoEntity;
import lu.wealins.liability.services.core.persistence.entity.ValorizedOrderNoEntity;

public interface FundTransactionRepository extends JpaRepository<FundTransactionEntity, Long>, JpaSpecificationExecutor<FundTransactionEntity> {

	@Query(nativeQuery = true, name = "FundTransactionEntity.findTransactionsInWithInputFees")
	List<FundTransactionEntity> findTransactionsInWithInputFees(@Param("fundId") String fundId, @Param("priceDate") Date priceDate);

	@Query(nativeQuery = true, name = "FundTransactionEntity.findTransactionsOutWithOutputFees")
	List<FundTransactionEntity> findTransactionsOutWithOutputFees(@Param("fundId") String fundId, @Param("priceDate") Date priceDate);

	@Query(nativeQuery = true, name = "FundTransactionEntity.findBlockedTransactions")
	List<FundTransactionEntity> findBlockedTransactions(@Param("fundId") String fundId, @Param("priceDate") Date priceDate);
	
	@Query(nativeQuery = true, name = "FundTransactionEntity.findUnitsSumOfTransactionsGroupedByPolicyAndFund")
	List<TransactionGroupedByFundNoEntity> findUnitsSumOfTransactionsGroupedByPolicyAndFund(@Param("policies") List<String> policies, @Param("date") Date date);

	@Query("SELECT count(ft) FROM FundTransactionEntity ft WHERE ft.status = 1 AND ft.fund = ?1")
	public int countTransactions(String fdsId);

	@Query("select ft FROM FundTransactionEntity ft, FundEntity f"
			+ " where ft.fund = f.fdsId"
			+ " and f.fundSubType in ('FID','FAS')"
			+ " and ft.eventType in (3,4,8,12,13,15,17,21,37,38,44,71)"
			+ " and ft.fundTransactionDate IS NOT NULL"
			+ " and (((ft.valorizedFlag IS NULL or ft.valorizedFlag = 0)  and ft.status = 1)"
			+ " or (ft.valorizedFlag = 1 and ft.status = 5))")
	Page<FundTransactionEntity> findValorizedOrdersForFIDNotTransmitted(Pageable pageable);

	@Query("select ft FROM FundTransactionEntity ft"
			+ " where ft.policyId = :policyId"
			+ " and ft.transaction.eventType = 2"
			+ " and ft.transaction.effectiveDate <= :eventDate"
			+ " and ft.status = 1")
	List<FundTransactionEntity> findSubcriptionOrAdditionTransactions(@Param("policyId") String policyId, @Param("eventDate") Date eventDate);

	/**
	 * Use named native query with the same name into FundTransactionEntity
	 * 
	 * Find estimated order
	 * 
	 * @param page the pagination
	 * @return
	 */
	Page<EstimatedOrderNoEntity> findEstimatedOrder(@Param("lastId") Long lastId, Pageable page);

	/**
	 * Use named native query with the same name into FundTransactionEntity
	 * 
	 * Find estimated order
	 * 
	 * @param page the pagination
	 * @return
	 */
	Page<EstimatedOrderNoEntity> findCancelledEstimatedOrder(@Param("lastId") Long lastId, Pageable page);

	/**
	 * Use named native query with the same name into FundTransactionEntity
	 * 
	 * Find valorized order
	 * 
	 * @param page the pagination
	 * @return
	 */
	Page<ValorizedOrderNoEntity> findValorizedOrder(@Param("lastId") Long lastId, Pageable page);

	/**
	 * Use named native query with the same name into FundTransactionEntity
	 * 
	 * Find cancelled valorized order
	 * 
	 * @param page the pagination
	 * @return
	 */
	Page<ValorizedOrderNoEntity> findCancelledValorizedOrder(@Param("lastId") Long lastId, Pageable page);

	@Query("SELECT MAX(ft.holdingValuation) FROM FundTransactionEntity ft "
			+ "WHERE ft.status IN (1, 5) "
			+ "AND ft.transaction.trnId IN (SELECT ac.transaction0 FROM AccountTransactionEntity ac, TransactionEntity tr "
			+ "WHERE ac.transaction0 = tr.trnId "
			+ "AND ac.atrId = :atrId)")
	List<BigDecimal> findAmoutCommissionOfAccountingTransactionForAdminCost(@Param("atrId") Long atrId);
	
	/**
	 * get transactions concerned for mathematical reserve calculation
	 * @param atrId
	 * @return
	 */
	@Query("SELECT ft FROM FundTransactionEntity ft "
			+ "WHERE TRIM(ft.policy.polId) IN :polIds "
			+ "AND ft.status = 1 "
			+ "AND ft.date0 <= :date")
	Page<FundTransactionEntity> findTransactionsForMathematicalReserveFunctionOfPolicyAndDate(@Param("polIds") List<String> polIds, @Param("date") Date date, Pageable page);

	@Query(nativeQuery = true, name = "FundTransactionEntity.findFundTransactionOrder")
	BigDecimal findTransactionsOrder(@Param("transaction") String transaction);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, name = "FundTransactionEntity.pr_PRE_FPC01")
	void pr_PRE_FPC01();

	@Transactional
	@Modifying
	@Query(nativeQuery = true, name = "FundTransactionEntity.pr_PRE_FPC02")
	void pr_PRE_FPC02();

	@Transactional
	@Modifying
	@Query(nativeQuery = true, name = "FundTransactionEntity.pr_PRE_FPC03")
	void pr_PRE_FPC03();

	@Transactional
	@Modifying
	@Query(nativeQuery = true, name = "FundTransactionEntity.pr_POST_FPC01")
	void pr_POST_FPC01();

	@Transactional
	@Modifying
	@Query(nativeQuery = true, name = "FundTransactionEntity.pr_POST_FPC02")
	void pr_POST_FPC02();

	@Transactional
	@Modifying
	@Query(nativeQuery = true, name = "FundTransactionEntity.pr_POST_FPC03")
	void pr_POST_FPC03();

	@Transactional
	@Modifying
	@Query(nativeQuery = true, name = "FundTransactionEntity.pr_POST_FPC04")
	void pr_POST_FPC04();

	List<FundTransactionEntity> findByPolicyIdAndEventTypeIn(String policyId, List<Integer> eventTypes);

	List<FundTransactionEntity> findByPolicyIdAndCoverageAndEventType(String policyId, Integer coverage, int eventType);

	/**
	 * Find the {@link FundTransactionEntity} matching the provided policy, event type, transaction effective date and not having one the status provided.
	 * 
	 * @param policyId the policy id
	 * @param effectiveDate the effective date of the transaction
	 * @param eventType the event type
	 * @param statusToFilter the list of status to filter
	 * @return the {@link FundTransactionEntity} matching the provided policy, event type, transaction effective date and not having one the status provided.
	 */
	@Query("SELECT ft FROM FundTransactionEntity ft "
			+ " WHERE ft.status not in :statusToFilter "
			+ " AND ft.eventType = :eventType "
			+ " AND ft.policyId = :policyId "
			+ " AND ft.transaction.effectiveDate = :effectiveDate")
	List<FundTransactionEntity> findByPolicyIdAndTransactionEffectiveDateAndEventTypeAndNotStatus(@Param("policyId") String policyId, @Param("effectiveDate") Date effectiveDate,
			@Param("eventType") int eventType, @Param("statusToFilter") Collection<Integer> statusToFilter);

	@Query("SELECT ft FROM FundTransactionEntity ft "
			+ " WHERE ft.status in :statusToFilter "
			+ " AND ft.policyId = :policyId "
			+ " AND ft.date0 <= :effectiveDate")
	List<FundTransactionEntity> findByPolicyIdAndDate0BeforeAndStatusIn(@Param("policyId") String policyId, @Param("effectiveDate") Date effectiveDate,
			@Param("statusToFilter") Collection<Integer> statusToFilter);

	@Query("SELECT ft FROM FundTransactionEntity ft "
			+ " INNER JOIN ft.policy.policyFundHoldings pfh"
			+ " WHERE ft.status in (3,4) "
			+ " AND ft.coverage > 1 "
			+ " AND ft.eventType = 4 "
			+ " AND ft.policyId = :policyId "
			+ " AND pfh.holdingNo = ft.coverage "
			+ " AND (pfh.units is null or pfh.units=0)")
	List<FundTransactionEntity> getAwaitingForSurrender(@Param("policyId") String policyId);

}
