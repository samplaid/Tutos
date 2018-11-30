package lu.wealins.liability.services.ws.rest.impl;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.CountryDTO;
import lu.wealins.liability.services.core.business.CountryService;
import lu.wealins.liability.services.ws.rest.CountryRESTService;

/**
 * @author XQV89
 *
 */
@Component
public class CountryRESTServiceImpl implements CountryRESTService {

	@Autowired
	private CountryService countryService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.CountryRESTService#getActiveCountries(javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public List<CountryDTO> getActiveCountries(SecurityContext context) {

		return countryService.getActiveCountries();
	}
		
	/* (non-Javadoc)
	 * @see lu.wealins.liability.services.ws.rest.CountryRESTService#getCountry(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public CountryDTO getCountry(SecurityContext context, String ctyId) {
		return countryService.getCountry(ctyId);
	}

	@Override
	public Collection<String> getPolicyCountry(SecurityContext context, String policyId) {
		return countryService.getPolicyCountry(policyId);
	}

}
