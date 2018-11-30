package lu.wealins.webia.services.core.service;

import java.util.List;

import lu.wealins.webia.services.core.persistence.entity.SasIsinEntity;
import lu.wealins.common.dto.webia.services.SasIsinDTO;

/**
 * Service for managing signaletique.
 *
 */
public interface SignaletiqueService {

	/**
	 * Tells if the provided isin/currency pair is found in webia or if a record with the same isin and a status SEND or received exist in webia
	 * 
	 * @param isinDTO the process
	 * @return <code>true</code> if found, false otherwise
	 */
	public boolean isAvailableInBloomberg(SasIsinDTO isinDto);

	/**
	 * Find all the {@link SasIsinEntity} matching the provided isin code.
	 * 
	 * @param isin the isin value to look for
	 * @return a list of the {@link SasIsinEntity} matching the provided isin code.
	 */
	List<SasIsinEntity> findByIsin(String isin);

	/**
	 * Create the corresponding Isin Record if it does not exist in database, or update it if found in the database.
	 * 
	 * @param isinDto the {@link SasIsinDTO} to persist.
	 * @return the created/updated {@link SasIsinEntity}.
	 */
	SasIsinEntity createOrUpdate(SasIsinDTO isinDto);

	/**
	 * Create the corresponding Isin Record only if it does not exist in database
	 * 
	 * @param isinDto the {@link SasIsinDTO} to persist.
	 * @return the created/updated {@link SasIsinEntity}.
	 */
	SasIsinEntity inject(SasIsinDTO isinDto);
}
