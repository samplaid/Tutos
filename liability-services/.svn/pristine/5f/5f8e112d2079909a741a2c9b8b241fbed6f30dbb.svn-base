package lu.wealins.liability.services.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.liability.services.core.business.CurrencyService;
import lu.wealins.liability.services.ws.rest.CurrencyRESTService;
import lu.wealins.common.dto.liability.services.CurrencyDTO;

@Component
public class CurrencyRESTServiceImpl implements CurrencyRESTService {

	@Autowired
	private CurrencyService currencyService;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.CurrencyRESTService#getActiveCurrencies(javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public List<CurrencyDTO> getActiveCurrencies(SecurityContext context) {

		return currencyService.getActiveCurrencies();
	}

}
