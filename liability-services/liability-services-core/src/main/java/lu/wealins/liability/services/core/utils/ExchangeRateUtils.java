package lu.wealins.liability.services.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.liability.services.core.persistence.entity.ExchangeRateEntity;
import lu.wealins.liability.services.core.persistence.repository.ExchangeRateRepository;

@Component
public class ExchangeRateUtils {

	@Autowired
	ExchangeRateRepository exchangeRateRepository;

	public BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency, Date effectDate) {
		ExchangeRateEntity exchangeRate = exchangeRateRepository.findlatestExchangeRate(effectDate, fromCurrency,
				toCurrency);
		BigDecimal convertedAmount = null;
		if (exchangeRate == null) {
			exchangeRate = exchangeRateRepository.findlatestExchangeRate(effectDate, toCurrency, fromCurrency);
		}
		if (exchangeRate != null) {
			if (exchangeRate.getMidRate() != null && new Integer(0).equals(exchangeRate.getReciprocal())) {
				convertedAmount = amount.multiply(exchangeRate.getMidRate()).setScale(6, RoundingMode.HALF_UP);
			} else if (new Integer(0).compareTo(exchangeRate.getReciprocal()) != 0
					&& BigDecimal.ZERO.compareTo(exchangeRate.getMidRate()) != 0) {
				convertedAmount = amount.divide(exchangeRate.getMidRate(), 6, RoundingMode.HALF_UP).setScale(6,
						RoundingMode.HALF_UP);
			}
		}

		return convertedAmount;
	}

}
