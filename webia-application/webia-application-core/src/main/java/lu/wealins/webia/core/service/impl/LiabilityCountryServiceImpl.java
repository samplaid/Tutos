package lu.wealins.webia.core.service.impl;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.CountryDTO;
import lu.wealins.webia.core.service.LiabilityCountryService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityCountryServiceImpl implements LiabilityCountryService {

	private static final String COUNTRY_ID_CANNOT_BE_NULL = "Country id cannot be null.";
	private static final String POLICY_ID_CANNOT_BE_NULL = "Policy id cannot be null.";
	private static final String LIABILITY_LOAD_COUNTRY = "liability/country/";
	private static final String LIABILITY_POLICY_COUNTRY = "PolicyCountry";

	@Autowired
	private RestClientUtils restClientUtils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityCountryService#getCountry(java.lang.String)
	 */
	@Override
	public CountryDTO getCountry(String countryId) {
		Assert.notNull(countryId, COUNTRY_ID_CANNOT_BE_NULL);

		return restClientUtils.get(LIABILITY_LOAD_COUNTRY, countryId, CountryDTO.class);
	}


	@Override
	public List<String> getPolicyCountries(String policyId) {
		Assert.notNull(policyId, POLICY_ID_CANNOT_BE_NULL);

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("policyId", policyId);

		return (List<String>) restClientUtils.get(LIABILITY_LOAD_COUNTRY, LIABILITY_POLICY_COUNTRY, params,
				new GenericType<Collection<String>>() {
				});

}
}
