package lu.wealins.liability.services.core.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.liability.services.core.business.CurrencyService;
import lu.wealins.liability.services.core.mapper.CurrencyMapper;
import lu.wealins.liability.services.core.persistence.repository.CurrencyRepository;
import lu.wealins.common.dto.liability.services.CurrencyDTO;

@Service
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	private CurrencyRepository currencyRepository;

	@Autowired
	private CurrencyMapper currencyMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.CurrencyService#getActiveCurrencies()
	 */
	@Override
	public List<CurrencyDTO> getActiveCurrencies() {
		return currencyMapper.asCurrencyDTOs(currencyRepository.findActiveCurrencies());
	}

}
