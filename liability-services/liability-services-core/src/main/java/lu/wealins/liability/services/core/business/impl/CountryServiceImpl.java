package lu.wealins.liability.services.core.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.CountryDTO;
import lu.wealins.liability.services.core.business.CountryService;
import lu.wealins.liability.services.core.mapper.CountryMapper;
import lu.wealins.liability.services.core.persistence.repository.CountryRepository;
import lu.wealins.liability.services.core.utils.PolicyHoldersCountriesUtils;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private CountryMapper countryMapper;

	@Autowired
	private PolicyHoldersCountriesUtils policyUtils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.CountryService#getActiveCountries()
	 */
	@Override
	public List<CountryDTO> getActiveCountries() {
		return countryMapper.asCountryDTOs(countryRepository.findActiveCountries());
	}

	/* (non-Javadoc)
	 * @see lu.wealins.liability.services.core.business.CountryService#getCountry(java.lang.String)
	 */
	@Override
	public CountryDTO getCountry(String ctyId) {
		return countryMapper.asCountryDTO(countryRepository.findOne(ctyId));
	}

	@Override
	public List<String> getPolicyCountry(String policyId) {
		return policyUtils.getPolicyCountries(policyId);
	}

}
