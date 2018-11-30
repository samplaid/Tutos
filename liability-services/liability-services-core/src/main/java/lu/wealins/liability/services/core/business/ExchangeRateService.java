package lu.wealins.liability.services.core.business;

import java.math.BigDecimal;
import java.util.Date;

import lu.wealins.common.dto.liability.services.ExchangeRateDTO;

public interface ExchangeRateService {

	/**
	 * return the last known exchange rate from a currency to another at a valuation date
	 * 
	 * @param fromCurrency
	 * @param toCurrency
	 * @param date
	 * @return
	 */
	ExchangeRateDTO getExchangeRate(String fromCurrency, String toCurrency, Date date);
  
	BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency, Date date);
}