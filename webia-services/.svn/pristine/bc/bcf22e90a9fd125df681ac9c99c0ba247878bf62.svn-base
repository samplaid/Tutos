package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.webia.services.core.persistence.entity.SasIsinEntity;

/**
 * Repository for managing the {@link SasIsinEntity} entities.
 *
 */
public interface SasIsinRepository extends JpaRepository<SasIsinEntity, Integer> {

	/**
	 * 
	 * Find a {@link SasIsinEntity} by looking on its isin and currency.
	 * 
	 * @param isin the isin
	 * @param currency the currency
	 * @return the corresponding entity if found, null otherwise
	 */
	@Query(value = "select sasIsin from SasIsinEntity sasIsin where sasIsin.isin = :isin and sasIsin.currency = :currency")
	SasIsinEntity findByIsinAndCurrency(@Param("isin") String isin, @Param("currency") String currency);

	/**
	 * Retrieve the {@link SasIsinEntity}s corresponding to the provide isin code.
	 * 
	 * @param isin the isin
	 * @return the list of corresponding entity if found, null otherwise
	 */
	@Query(value = "select sasIsin from SasIsinEntity sasIsin where sasIsin.isin = :isin")
	List<SasIsinEntity> findByIsin(@Param("isin") String isin);

	/**
	 * Retrieve the {@link SasIsinEntity}s corresponding to the provide isin code and having the SEND or RECEIVED values for the status code.
	 * 
	 * @param isin the isin
	 * @return the {@link SasIsinEntity}s corresponding to the provide isin code and having the SEND or RECEIVED values for the status code.
	 */
	@Query(value = "select sasIsin from SasIsinEntity sasIsin where sasIsin.isin = :isin and (sasIsin.statusCode = 'SEND' or sasIsin.statusCode = 'RECEIVED')")
	List<SasIsinEntity> findByIsinWithStatusSendOrReceived(@Param("isin") String isin);

}
