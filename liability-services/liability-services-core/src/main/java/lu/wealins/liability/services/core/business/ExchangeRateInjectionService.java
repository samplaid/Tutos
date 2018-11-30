package lu.wealins.liability.services.core.business;

import java.math.BigDecimal;
import java.util.Date;

import lu.wealins.liability.services.core.business.exceptions.ExchangeRateException;

public interface ExchangeRateInjectionService {

	void checkDuplicateExchangeRate(Date date, String fromCurrency, String toCurrency) throws ExchangeRateException;

	void checkExchangeRateVariation(Date date, BigDecimal exchangeRate, String fromCurrency, String toCurrency) throws ExchangeRateException;

	void checkExchangeRateDateVariation(Date date, String fromCurrency, String toCurrency) throws ExchangeRateException;

	void checkCurrencyExist(String currency) throws ExchangeRateException;

	void checkExchangeRateNotNull(BigDecimal exchangeRate) throws ExchangeRateException;

	BigDecimal getOldExchangeRate(Date date, String fromCurrency, String toCurrency);

	/**
	 * Check that the previous combination <fromCurrency> <toCurrency> has the reciprocal to 0
	 * 
	 * @param exchangeRateDate
	 * @param fromCurrency
	 * @param toCurrency
	 * @throws ExchangeRateException
	 */
	void checkReciprocalSens(Date exchangeRateDate, String fromCurrency, String toCurrency) throws ExchangeRateException;
}
