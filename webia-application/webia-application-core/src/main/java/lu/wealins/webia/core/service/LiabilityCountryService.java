package lu.wealins.webia.core.service;


import java.util.List;

import lu.wealins.common.dto.liability.services.CountryDTO;

public interface LiabilityCountryService {

	/**
	 * Get country according its id.
	 * 
	 * @param countryId The country id.
	 * @return The country.
	 */
	CountryDTO getCountry(String countryId);

	/**
	 * Get isocode2 policy holders countries.
	 * 
	 * @param policyId
	 *            the policy id
	 * @return
	 */
	List<String> getPolicyCountries(String policyId);
}
