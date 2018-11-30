package lu.wealins.liability.services.core.persistence.repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lu.wealins.liability.services.core.persistence.entity.AccountTransactionEntity;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransactionEntity, Long> {
	
	/**
	 * Find by transaction0 and account
	 * 
	 * @param transaction0
	 * @param account
	 * @return
	 */
	List<AccountTransactionEntity> findByTransaction0AndAccount(Long transaction0, String account);
	
	/**
	 * Find all account_transaction not exported for sap_accounting
	 * 
	 * @param eventTypeList
	 * @return
	 */
	@Query("SELECT DISTINCT ac FROM AccountTransactionEntity ac WHERE ac.postingSet.sapStatus IS NULL "
			+ "AND ac.postingSet.status = 2 "
			+ "AND ac.eventType IN :eventTypeList ")
	List<AccountTransactionEntity> findAllDistinctAccountTransaction(@Param("eventTypeList") int[] eventTypeList);
	
	/**
	 * Find all account_transaction not exported for sap_accounting
	 * 
	 * @param eventTypeList
	 * @param pageable
	 * @return
	 */
	@Query("SELECT DISTINCT ac FROM AccountTransactionEntity ac WHERE ac.postingSet.sapStatus IS NULL "
			+ "AND ac.postingSet.status = 2 "
			+ "AND ac.eventType IN :eventTypeList ")
	Page<AccountTransactionEntity> findAllDistinctAccountTransaction(@Param("eventTypeList") int[] eventTypeList, Pageable pageable);
	
	/**
	 * Find all account_transaction not exported for sap_accounting
	 * 
	 * @param eventTypeList
	 * @param currentPst
	 * @return
	 */
	@Query("SELECT DISTINCT ac FROM AccountTransactionEntity ac WHERE ac.postingSet.sapStatus IS NULL "
			+ "AND ac.postingSet.pstId = :currentPst "
			+ "AND ac.postingSet.status = 2 "
			+ "AND ac.eventType IN :eventTypeList ")
	List<AccountTransactionEntity> findAllDistinctAccountTransaction(@Param("eventTypeList") int[] eventTypeList, @Param("currentPst") Long currentPst);
	
	/**
	 * Find all account_transaction not exported for sap_accounting
	 * 
	 * @param eventTypeList
	 * @param minAtrId
	 * @param currentPst
	 * @param pageable
	 * @return
	 */
	@Query("SELECT DISTINCT ac FROM AccountTransactionEntity ac WHERE ac.postingSet.sapStatus IS NULL "
			+ "AND ac.postingSet.pstId = :currentPst "
			+ "AND ac.postingSet.status = 2 "
			+ "AND ac.eventType IN :eventTypeList "
			+ "AND ac.atrId > :minAtrId "
			+ "ORDER BY ac.atrId ASC ")
	Page<AccountTransactionEntity> findAllDistinctAccountTransaction(@Param("eventTypeList") int[] eventTypeList, @Param("minAtrId") Long minAtrId, @Param("currentPst") Long currentPst, Pageable pageable);
	
	/**
	 * Distinct transaction0 of account_transaction not exported for commission_to_pay 
	 * 
	 * @param accountList
	 * @param commissionEventSubTypeList 
	 * @param lastId
	 * @param currentPst
	 * @param pageable
	 * @return
	 */
	@Query("SELECT DISTINCT ac.transaction0 FROM AccountTransactionEntity ac, AgentEntity ag WHERE ac.centre = ag.agtId "
			+ "AND ag.agentCategoryId = 'BK' "
			+ "AND ag.agtId != :codeBil "
			+ "AND ac.postingSet.sapStatus >= 1 "
			+ "AND (ac.postingSet.comStatus = 0 OR ac.postingSet.comStatus IS NULL) "
			+ "AND ac.postingSet.sapExportDate IS NOT NULL "
			+ "AND ac.amount != 0 "
			+ "AND ac.account IN :accountList "
			+ "AND ac.eventSubType IN :eventSubList "
			+ "AND ac.postingSet.pstId = :currentPst "
			+ "AND ac.transaction0 > :lastId "
			+ "ORDER BY ac.transaction0")
	Page<Long> findAllAccountTransactionNotExportedCommissionToPayDistinctByTransaction0(@Param("accountList") List<String> accountList, @Param("eventSubList") int[] commissionEventSubTypeList, @Param("lastId") Long lastId, @Param("currentPst") Long currentPst, @Param("codeBil") String codeBil, Pageable pageable);
	
	/**
	 * Find all account transaction not exported for commission_to_pay
	 * 
	 * @param accountList
	 * @param commissionEventSubTypeList 
	 * @param lastId
	 * @param transaction0Available
	 * @param pageable
	 * @return
	 */
	@Query("SELECT distinct ac FROM AccountTransactionEntity ac , AgentEntity ag WHERE ac.centre = ag.agtId "
			+ "AND ag.agentCategoryId = 'BK' "
			+ "AND ag.agtId != :codeBil "
			+ "AND ac.postingSet.sapStatus >= 1 "
//			+ "AND (ac.postingSet.comStatus = 0 OR ac.postingSet.comStatus IS NULL) "
			+ "AND ac.postingSet.sapExportDate IS NOT NULL "
			+ "AND ac.amount != 0 "
			+ "AND ac.account IN :accountList "
			+ "AND ac.eventSubType IN :eventSubList "
			+ "AND ac.postingSet.pstId = :currentPst "
//			+ "AND ac.transaction0 = :transaction0Available "
			+ "AND ac.atrId > :lastId "
			+ "ORDER BY ac.atrId")
	Page<AccountTransactionEntity> findAllAccountTransactionNotExportedCommissionToPay(@Param("accountList") List<String> accountList, @Param("eventSubList") int[] commissionEventSubTypeList, @Param("lastId") Long lastId, @Param("currentPst") Long currentPst, @Param("codeBil") String codeBil, Pageable pageable);
	
	/**
	 * Find amount commission of account_transaction for entry cost
	 * 
	 * @param currentPst
	 * @param transaction0
	 * @param account 
	 * @param dbcr
	 * @return
	 */
	@Query("SELECT SUM(ABS(ac.amount)) FROM AccountTransactionEntity ac WHERE ac.amount != 0 "
			+ "AND ac.transaction0 = :transaction0 "
			+ "AND ac.account = :account "
			+ "AND ac.postingSet.pstId = :currentPst "
			+ "AND ac.dbcr != :dbcr ")
	List<BigDecimal> findBaseAmoutCommissionForOtherCase(@Param("currentPst") Long currentPst, @Param("transaction0") Long transaction0, @Param("account") String account, @Param("dbcr") char dbcr);
	
	/**
	 * Find amount commission of account_transaction for entry cost
	 * 
	 * @param atrId
	 * @param polSharesType
	 * @return
	 */
	@Query("SELECT prime.amount FROM AccountTransactionEntity ac, AccountTransactionEntity prime, PolicyAgentShareEntity pol "
			+ "WHERE ac.transaction.trnId = prime.transaction.trnId "
			+ "AND pol.polId = ac.policy "
			+ "AND ac.account = 'AGENTBAL' "
			+ "AND ac.eventSubType = ac.eventSubType "
			+ "AND ac.policyCoverage.pocId = concat(TRIM(ac.policy), '_', substring(concat(cast(pol.coverage AS string), '    '), 0, 3)) "
			+ "AND pol.type IN :polSharesType "
			+ "AND pol.agent.agtId = ac.centre "
			+ "AND prime.account IN ('CASHSUSP', 'CASHBOOK') "
			+ "AND ac.atrId = :atrId "
			+ "ORDER BY prime.amount")
	List<BigDecimal> findAmoutCommissionOfAccountTransactionForEntryCost(@Param("atrId") Long atrId, @Param("polSharesType") List<Integer> polSharesType);
	
	/**
	 * Find percentage of commission for account_transaction
	 * 
	 * @param atrId
	 * @param evtSubTypeList
	 * @param polSharesType
	 * @return
	 */
	@Query("SELECT MAX(pol.percentage) FROM AccountTransactionEntity ac, PolicyAgentShareEntity pol "
			+ "WHERE pol.polId = ac.policy "
			+ "AND ac.account = 'AGENTBAL' "
			+ "AND ac.eventSubType IN :evtSubTypeList "
			+ "AND ac.policyCoverage.pocId = concat(TRIM(ac.policy), '_', substring(concat(cast(pol.coverage AS string), '    '), 0, 3)) "
			+ "AND pol.type IN :polSharesType "
			+ "AND pol.agent.agtId = ac.centre "
//			+ "AND pol.status = 1 "
			+ "AND ac.atrId = :atrId "
			+ "ORDER BY pol.percentage")
	List<BigDecimal> findPercentageCommissionOfAccountTransaction(@Param("atrId") Long atrId, @Param("evtSubTypeList") List<Integer> evtSubTypeList, @Param("polSharesType") List<Integer> polSharesType);
	
	/**
	 * Distinct transaction0 of account_transaction not exported for reporting_com 
	 * 
	 * @param eventSubType
	 * @param accountTypeReportList
	 * @param lastId
	 * @param codeBilApplicationParam
	 * @param currentPst
	 * @param pageableTransaction
	 * @return
	 */
	@Query("SELECT DISTINCT ac.transaction0 FROM AccountTransactionEntity ac, PolicyEntity pol WHERE ac.policy = pol.polId "
			+ "AND ac.eventSubType IN :eventSubType "
			+ "AND EXISTS(SELECT share FROM PolicyAgentShareEntity share WHERE share.polId = ac.policy "
			+ "AND share.agent.agtId = :codeBilApplicationParam "
			+ "AND share.status = 1) "
			+ "AND (pol.brokerRefContract IS NOT NULL AND TRIM(pol.brokerRefContract) != '') "
			+ "AND ac.postingSet.sapStatus >= 1 "
			+ "AND ac.postingSet.sapExportDate IS NOT NULL "
			+ "AND (ac.postingSet.reportStatus = 0 OR ac.postingSet.reportStatus IS NULL) "
			+ "AND ac.postingSet.pstId = :currentPst "
			+ "AND ac.account IN :accountList "
			+ "AND ac.transaction0 > :lastId "
			+ "ORDER BY ac.transaction0")
	Page<Long> findAllAccountTransactionNotExportedReportingComDistinctByTransaction0(@Param("eventSubType") int[] eventSubType, @Param("accountList") List<String> accountTypeReportList, @Param("lastId") Long lastId, @Param("codeBilApplicationParam") String codeBilApplicationParam, @Param("currentPst") Long currentPst, Pageable pageableTransaction);
	
	/**
	 * Find all account transaction not exported for reporting_com
	 * 
	 * @param eventSubType
	 * @param accountList
	 * @param transaction0Available
	 * @param pageable
	 * @return
	 */
	@Query("SELECT DISTINCT ac FROM AccountTransactionEntity ac, PolicyEntity pol WHERE ac.policy = pol.polId "
			+ "AND ac.eventSubType IN :eventSubType "
			// + "AND EXISTS(SELECT share FROM PolicyAgentShareEntity share WHERE share.polId = ac.policy "
//			+ "AND share.agent.agtId = :codeBilApplicationParam "
//			+ "AND share.status = 1) "
//			+ "AND (pol.brokerRefContract IS NOT NULL AND TRIM(pol.brokerRefContract) != '') "
			+ "AND ac.postingSet.sapStatus >= 1 "
			+ "AND ac.postingSet.sapExportDate IS NOT NULL "
//			+ "AND (ac.postingSet.reportStatus = 0 OR ac.postingSet.reportStatus IS NULL) "
//			+ "AND ac.postingSet.pstId = :currentPst "
			+ "AND ac.account IN :accountList "
			+ "AND ac.transaction0 = :transaction0Available "
			+ "ORDER BY ac.atrId")
	Page<AccountTransactionEntity> findAllAccountTransactionNotExportedSapReportingCom(@Param("eventSubType") int[] eventSubType, @Param("accountList") List<String> accountList, @Param("transaction0Available") Long transaction0Available, Pageable pageable);
	
	
	/**
	 * Distinct posting_sets available for sap_accounting
	 * 
	 * @return
	 */
	@Query("SELECT DISTINCT ac.postingSet.pstId FROM AccountTransactionEntity ac WHERE ac.postingSet.sapStatus IS NULL  "
			+ "AND ac.postingSet.status = 2 "
			+ "ORDER BY ac.postingSet.pstId ASC")
	List<Long> findAllAccountTransactionDistinctPostingIdForSapAccounting();
	
	/**
	 * Distinct posting_sets available for commission_to_pay
	 * @return
	 */
	@Query("SELECT DISTINCT ac.postingSet.pstId FROM AccountTransactionEntity ac WHERE ac.postingSet.sapStatus >= 1  "
			+ "AND ac.postingSet.sapExportDate IS NOT NULL "
			+ "AND (ac.postingSet.comStatus = 0 OR ac.postingSet.comStatus IS NULL) "
			+ "ORDER BY ac.postingSet.pstId ASC")
	List<Long> findAllAccountTransactionDistinctPostingIdForCommissionToPay();
	
	/**
	 * Distinct posting_sets available for reporting_com
	 * @return
	 */
	@Query("SELECT DISTINCT ac.postingSet.pstId  FROM AccountTransactionEntity ac, PolicyEntity pol WHERE ac.policy = pol.polId "
			+ "AND (pol.brokerRefContract IS NOT NULL AND TRIM(pol.brokerRefContract)  != '') "
			+ "AND ac.postingSet.sapExportDate IS NOT NULL "
			+ "AND ac.postingSet.sapStatus >= 1 "
			+ "AND (ac.postingSet.reportStatus = 0 OR ac.postingSet.reportStatus IS NULL) "
			+ "ORDER BY ac.postingSet.pstId ASC")
	List<Long> findAllAccountTransactionDistinctPostingIdForReportingCom();
	
	/**
	 * Distinct fund by fundSubType not in specified list of fundSubType
	 * 
	 * @param fundSubTypeList
	 * @return
	 */
	@Query("SELECT DISTINCT fu.fundSubType FROM FundEntity fu "
			+ "WHERE fu.fundSubType NOT IN :fundSubType")
	List<String> distinctFundByFundSubTypeNotInSpecifiedTypeList(@Param("fundSubType") List<String> fundSubTypeList);	
	
	/**
	 * Distinct fund bil by fundSubType
	 * 
	 * @param fundType
	 * @param codeBilApplicationParam
	 * @return
	 */
	@Query("SELECT DISTINCT fu.fdsId FROM PolicyFundHoldingEntity ph, FundPriceEntity fp, FundEntity fu "
			+ "WHERE EXISTS(SELECT a FROM PolicyAgentShareEntity a "
			+ "WHERE a.polId = ph.policy.polId "
			+ "AND a.agent.agtId = :codeBilApplicationParam "
			+ "AND a.status = 1) "
			+ "AND ph.status = 1 "
			+ "AND fp.fund = ph.fund "
			+ "AND fp.fund.fdsId = fu.fdsId "
			+ "AND fu.fundSubType = :fundSubType "
			+ "AND fp.recordType = 1")
	List<String> distinctFundBilByFundSubType(@Param("fundSubType") String fundType, @Param("codeBilApplicationParam") String codeBilApplicationParam);
	
	/**
	 * Get max date external funds
	 * 
	 * @param fundSubType
	 * @param codeBilApplicationParam
	 * @return
	 */
	@Query("SELECT max(fp.date0) FROM PolicyFundHoldingEntity ph, FundPriceEntity fp, FundEntity fu "
			+ "WHERE EXISTS(SELECT a FROM PolicyAgentShareEntity a "
			+ "WHERE a.polId = ph.policy.polId "
			+ "AND a.agent.agtId = :codeBilApplicationParam "
			+ "AND a.status = 1) "
			+ "AND ph.status = 1 "
			+ "AND fp.fund = ph.fund "
			+ "AND fp.fund.fdsId = fu.fdsId "
			+ "AND fu.fundSubType = :fundSubType "
			+ "AND fp.recordType = 1")
	Date findMaxDateForFundBilByFundSubType(@Param("fundSubType") String fundSubType, @Param("codeBilApplicationParam") String codeBilApplicationParam);
	
	/**
	 * Construct external funds
	 * 
	 * @param fundSubType
	 * @param codeBilApplicationParam
	 * @param maxDate
	 * @param fundIds
	 * @return
	 */
	@Query("SELECT ph.policy.polId, ph.policy.product.prdId, fp.currency, (ph.units * fp.price), ph.policy.brokerRefContract, fu.isinCode FROM PolicyFundHoldingEntity ph, FundPriceEntity fp, FundEntity fu "
			+ "WHERE EXISTS(SELECT a FROM PolicyAgentShareEntity a "
			+ "WHERE a.polId = ph.policy.polId "
			+ "AND a.agent.agtId = :codeBilApplicationParam "
			+ "AND a.status = 1) "
			+ "AND ph.status = 1 "
			+ "AND fu.fdsId = :fundIds "
			+ "AND fp.fund.fdsId = fu.fdsId "
			+ "AND ph.fund.fdsId = fu.fdsId "
			+ "AND fu.fundSubType = :fundSubType "
			+ "AND fp.recordType = 1 "
			+ "AND fp.date0 = :maxDate "
			+ "ORDER BY fp.date0 DESC")
	Collection<Object[]> constructExternalFundBySubTypeAndId(@Param("fundSubType") String fundSubType, @Param("codeBilApplicationParam") String codeBilApplicationParam, @Param("maxDate") Date maxDate, @Param("fundIds") String fundIds);

	@Query("SELECT DISTINCT ac FROM AccountTransactionEntity ac WHERE ac.transaction0 = :transaction0 "
			+ "AND ac.account = 'AGENTBAL' "
			+ "AND ac.amount = :amount "
			+ "AND ac.dbcr = 'C'")
	List<AccountTransactionEntity> findAccountTransactionReversedByTransaction0AndAmount(@Param("transaction0") Long transaction0, @Param("amount") BigDecimal amount);
	

}
