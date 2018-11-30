package lu.wealins.liability.services.core.business.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.liability.services.core.business.ExchangeRateInjectionService;
import lu.wealins.liability.services.core.business.exceptions.ExchangeRateException;
import lu.wealins.liability.services.core.business.exceptions.ExchangeRateExceptionCode;
import lu.wealins.liability.services.core.persistence.entity.CurrencyEntity;
import lu.wealins.liability.services.core.persistence.entity.ExchangeRateEntity;
import lu.wealins.liability.services.core.persistence.repository.CurrencyRepository;
import lu.wealins.liability.services.core.persistence.repository.ExchangeRateRepository;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.core.utils.MessageUtils;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class ExchangeRateInjectionServiceImpl implements ExchangeRateInjectionService {

	private static final String EXCHANGE_RATE_ALREADY_EXIST = "exchangeRateAlreadyExist";
	private static final String EXCHANGE_RATE_IS_RECIPROCAL = "exchangeRateDuplicateIntoRequest";

	private static final int HUNDRED = 100;
	private static final String EXCHANGE_RATE_VARIATION_TOO_HIGH = "exchangeRateVariationTooHigh";
	private static final String EXCHANGE_RATE_DATE_VARIATION_TOO_HIGH = "exchangeRateDateVariationTooHigh";
	private static final String EXCHANGE_RATE_WRONG_CURRENCY = "exchangeRateWrongCurrency";
	private static final String EXCHANGE_RATE_EQUALS_TO_ZERO = "exchangeRateEqualsToZero";
	private static final String EXCHANGE_RATE_SAME_AS_PREVIOUS = "exchangeRateSameAsPrevious";
	private static final String EXCHANGE_RATE_WRONG_DATE = "exchangeRateWrongDate";
	private static final String EXCHANGE_RATE_QUARTER_DATE = "exchangeRateQuarterDate";

	@Value("${exchangeRateInjectionVariationThreshold:}")
	private Integer exchangeRateInjectionVariationThreshold;
	@Value("${exchangeRateInjectionDelayVariation:}")
	private Integer exchangeRateInjectionDelayVariation;

	@Autowired
	private ExchangeRateRepository exchangeRateRepository;
	@Autowired
	private CurrencyRepository currencyRepository;
	@Autowired
	private MessageUtils messageUtils;
	@Autowired
	private CalendarUtils calendarUtils;

	@Override
	public void checkReciprocalSens(Date date, String fromCurrency, String toCurrency) throws ExchangeRateException {
		if (StringUtils.isNotEmpty(fromCurrency) && StringUtils.isNotEmpty(toCurrency)) {
			ExchangeRateEntity previousExchangeRate = exchangeRateRepository.findPreviousExchangeRate(date, fromCurrency, toCurrency);

			if (previousExchangeRate != null) {
				if (previousExchangeRate.getReciprocal() == 1)
					throwExchangeRateException(ExchangeRateExceptionCode.EXISTS_EXCHANGE_RATE_RECIPRICAL, EXCHANGE_RATE_IS_RECIPROCAL, fromCurrency, toCurrency);
			}
		}

	}

	@Override
	public void checkDuplicateExchangeRate(Date date, String fromCurrency, String toCurrency) throws ExchangeRateException {
		Collection<ExchangeRateEntity> exchangeRates = exchangeRateRepository.findActiveByDateAndFromCcyToCcy(date, fromCurrency, toCurrency);

		if (CollectionUtils.isNotEmpty(exchangeRates)) {
			throwExchangeRateException(ExchangeRateExceptionCode.DUPLICATE_EXCHANGE_RATE, EXCHANGE_RATE_ALREADY_EXIST, fromCurrency, toCurrency, date.toString());
		}
	}

	@Override
	public void checkExchangeRateVariation(Date date, BigDecimal newExchangeRate, String fromCurrency, String toCurrency) throws ExchangeRateException {
		ExchangeRateEntity previousExchangeRate = exchangeRateRepository.findPreviousExchangeRate(date, fromCurrency, toCurrency);

		if (previousExchangeRate != null) {
			BigDecimal previousRate = previousExchangeRate.getMidRate();
			if (previousRate != null && previousRate.compareTo(BigDecimal.ZERO) != 0) {
				BigDecimal variation = newExchangeRate.subtract(previousRate).divide(previousRate, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(HUNDRED)).abs();
				if (variation.compareTo(new BigDecimal(exchangeRateInjectionVariationThreshold.intValue())) > 0) {
					throwExchangeRateException(ExchangeRateExceptionCode.EXCHANGE_RATE_VARIATION_TOO_HIGH, EXCHANGE_RATE_VARIATION_TOO_HIGH, exchangeRateInjectionVariationThreshold.toString(),
							previousRate + "");
				}
			}
		}

	}

	@Override
	public void checkExchangeRateDateVariation(Date date, String fromCurrency, String toCurrency) throws ExchangeRateException {
		ExchangeRateEntity previousExchangeRate = exchangeRateRepository.findPreviousExchangeRate(date, fromCurrency, toCurrency);

		if (previousExchangeRate != null) {

			Date previousExchangeRateDate = previousExchangeRate.getDate0();
			int numberOfDays = Math.abs(calendarUtils.daysBetween(previousExchangeRateDate, date));

			if (numberOfDays > exchangeRateInjectionDelayVariation.intValue()) {
				throwExchangeRateException(ExchangeRateExceptionCode.EXCHANGE_RATE_DATE_VARIATION_TOO_HIGH, EXCHANGE_RATE_DATE_VARIATION_TOO_HIGH, exchangeRateInjectionDelayVariation + "",
						previousExchangeRateDate.toString());
			}
		}
	}

	@Override
	public BigDecimal getOldExchangeRate(Date date, String fromCurrency, String toCurrency) {
		ExchangeRateEntity previousExchangeRate = exchangeRateRepository.findPreviousExchangeRate(date, fromCurrency, toCurrency);

		if (previousExchangeRate != null) {
			return previousExchangeRate.getMidRate();
		}
		return null;

	}

	@Override
	public void checkCurrencyExist(String currency) throws ExchangeRateException {
		CurrencyEntity activeCurrency = currencyRepository.findActiveCurrency(currency);

		if (activeCurrency == null) {
			throwExchangeRateException(ExchangeRateExceptionCode.EXCHANGE_RATE_WRONG_CURRENCY, EXCHANGE_RATE_WRONG_CURRENCY, currency);
		}

	}

	@Override
	public void checkExchangeRateNotNull(BigDecimal exchangeRate) throws ExchangeRateException {
		if (exchangeRate == null || BigDecimal.ZERO.compareTo(exchangeRate) == 0) {
			throwExchangeRateException(ExchangeRateExceptionCode.EXCHANGE_RATE_EQUALS_TO_ZERO, EXCHANGE_RATE_EQUALS_TO_ZERO);
		}

	}

	private void throwExchangeRateException(ExchangeRateExceptionCode exceptionCode, String messageId, String... args) throws ExchangeRateException {
		String message = messageUtils.getMessage(messageId, args);

		throw new ExchangeRateException(exceptionCode, message);
	}

}
