package lu.wealins.liability.services.core.business;

import java.util.List;

import lu.wealins.common.dto.liability.services.CountryDTO;

public interface CountryService {

	/**
	 * Get the active countries.
	 * 
	 * @return The active countries.
	 */
	List<CountryDTO> getActiveCountries();
	
	/**
	 * Retrieve a country identified by the parameter
	 * @param ctyId the country id
	 * @return a country. Null if not found.
	 */
	CountryDTO getCountry(String ctyId);

	/**
	 * Get a countries of policy holders.
	 * 
	 * @param context
	 *            The security context.
	 * @param policyId
	 *            the Id of policy
	 * @return The isoCode2 countries.
	 */
	List<String> getPolicyCountry(String policyId);

}
