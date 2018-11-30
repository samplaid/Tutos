package lu.wealins.liability.services.core.persistence.repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.liability.services.core.persistence.entity.TransactionEntity;

public interface TransactionRepository
		extends JpaRepository<TransactionEntity, Long>, JpaSpecificationExecutor<TransactionEntity> {

	List<TransactionEntity> findByPolicy(PolicyEntity policy);

	@Query("SELECT t FROM TransactionEntity t WHERE t.status = 1 AND t.policyId = :policyId and t.eventType = :eventType")
	List<TransactionEntity> findActiveByPolicyIdAndEventType(@Param("policyId") String policyId, @Param("eventType") Integer eventType);

	@Query("SELECT DISTINCT t FROM TransactionEntity t, PolicyEntity p, CliPolRelationshipEntity cpr, ClientContactDetailEntity ccd, CountryEntity c "
			+ "WHERE t.policyId = p.polId " + "AND p.polId = cpr.policyId " + "AND cpr.clientId = ccd.clientId "
			+ "AND ccd.country.isoCode2Pos in :countryList " + "AND t.eventType in  :eventTypes " + "AND t.status = 1 "
			+ "And t.transactionTaxProcessed = 0 ")
	List<TransactionEntity> loadTransactionTaxLines(@Param("countryList") List<String> countryList,
			@Param("eventTypes") List<Integer> eventTypes, Pageable page);

	@Query("SELECT ccd.country.isoCode2Pos FROM TransactionEntity t, PolicyEntity p, CliPolRelationshipEntity cpr, ClientContactDetailEntity ccd, CountryEntity c "
			+ "WHERE t.policyId = p.polId " + "AND p.polId = cpr.policyId " + "AND cpr.clientId = ccd.clientId "
			+ "AND ccd.country.isoCode2Pos in :countryList " + "AND t.trnId = :trnId")
	List<String> GetCountrybyTransaction(@Param("countryList") List<String> countryList, @Param("trnId") long trnId);

	@Query("SELECT DISTINCT t FROM TransactionEntity t " + "WHERE t.transactionTaxProcessed = 1 "
			+ "AND ( t.transactionTaxCancelProcessed <> 1 or t.transactionTaxCancelProcessed  is NULL) "
			+ "AND t.status in :cancelledTransactionTaxType ")
	List<TransactionEntity> loadCancelledTransactionTaxLines(
			@Param("cancelledTransactionTaxType") List<Integer> cancelledTransactionTaxType);

	@Query("SELECT t.value0 FROM TransactionEntity t WHERE t.policyId = :policyId AND t.status = 1 AND t.effectiveDate = :date AND t.eventType in  :eventTypes ")
	List<BigDecimal> getMortalityCharge(@Param("policyId") String policyId, @Param("date") Date paymentDate,
			@Param("eventTypes") List<Integer> eventTypes);

	@Query("SELECT t FROM TransactionEntity t, FundTransactionEntity ft WHERE ft.transaction.trnId = t.trnId AND t.eventType = 12 AND ft.eventType = 12 AND t.triggerTrnId <> 0 AND t.policyId = :policyId AND t.effectiveDate >= :effectiveDate AND ft.status NOT IN :status")
	List<TransactionEntity> findAdministrationFees(@Param("policyId") String policyId, @Param("effectiveDate") Date effectiveDate, @Param("status") List<Integer> status);

	@Query("SELECT t FROM TransactionEntity t WHERE t.status = 1 AND t.policyId = :policyId and t.eventType = :eventType AND t.effectiveDate = :date ")
	List<TransactionEntity> findActiveByPolicyIdAndEventTypeAndDate(@Param("policyId") String policyId, @Param("eventType") Integer eventType,@Param("date") Date date );

	@Query("SELECT t FROM TransactionEntity t WHERE t.status = 1 AND t.transactionLinked = :transactionId ")
	List<TransactionEntity> findActiveLinkedTransactions(@Param("transactionId") Long transactionId);

	@Query("SELECT t FROM TransactionEntity t WHERE t.status = 1 AND t.transactionLinked in :transactionIds ")
	List<TransactionEntity> findActiveLinkedTransactionsList(@Param("transactionIds") Collection<Long> transactionIds);
}
