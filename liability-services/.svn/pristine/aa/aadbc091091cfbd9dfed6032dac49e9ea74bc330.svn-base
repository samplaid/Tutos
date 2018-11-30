package lu.wealins.liability.services.ws.rest.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.liability.services.core.business.ExchangeRateInjectionService;
import lu.wealins.liability.services.core.business.exceptions.ExchangeRateException;
import lu.wealins.liability.services.core.business.exceptions.ExchangeRateExceptionCode;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.ws.rest.ExchangeRateInjectionRESTService;
import lu.wealins.common.dto.liability.services.BatchInjectionControlResponse;
import lu.wealins.common.dto.liability.services.ErrorDTO;
import lu.wealins.common.dto.liability.services.ExchangeRateInjectionControlRequest;

@Component
public class ExchangeRateInjectionRESTServiceImpl implements ExchangeRateInjectionRESTService {

	private static final String PREFIX = "XRS";

	private static final String SUFFIX = "Z";

	private static final String COMA = ",";

	@Autowired
	private ExchangeRateInjectionService exchangeRateInjectionService;

	@Autowired
	private CalendarUtils calendarUtils;

	@Override
	public BatchInjectionControlResponse checkExchangeRateInjection(SecurityContext context, ExchangeRateInjectionControlRequest request) {
		BatchInjectionControlResponse response = new BatchInjectionControlResponse();
		response.setSuccess(Boolean.TRUE);

		BigDecimal exchangeRate = request.getExchangeRate();
		String fromCurrency = request.getFromCurrency();
		String toCurrency = request.getToCurrency();

		try {
			Date exchangeRateDate = calendarUtils.createDate(request.getDate());
			BigDecimal oldExchangeRate = exchangeRateInjectionService.getOldExchangeRate(exchangeRateDate, fromCurrency, toCurrency);
			String currentLine = buildLine(exchangeRateDate, fromCurrency, toCurrency, exchangeRate, oldExchangeRate);

			exchangeRateInjectionService.checkExchangeRateNotNull(exchangeRate);
			exchangeRateInjectionService.checkCurrencyExist(fromCurrency);
			exchangeRateInjectionService.checkCurrencyExist(toCurrency);
			exchangeRateInjectionService.checkReciprocalSens(exchangeRateDate, fromCurrency, toCurrency);
			exchangeRateInjectionService.checkDuplicateExchangeRate(exchangeRateDate, fromCurrency, toCurrency);
			exchangeRateInjectionService.checkExchangeRateVariation(exchangeRateDate, exchangeRate, fromCurrency, toCurrency);
			exchangeRateInjectionService.checkExchangeRateDateVariation(exchangeRateDate, fromCurrency, toCurrency);
			// If everything is right, we can add the line
			response.getLines().add(currentLine);
		} catch (ExchangeRateException e) {
			ErrorDTO error = new ErrorDTO();

			ExchangeRateExceptionCode errorCode = e.getErrorCode();
			if (errorCode != null) {
				error.setCode(e.getErrorCode().name());
			}

			error.setMessage(
					" From currency " + fromCurrency + ", To currency " + toCurrency + ", exchange rate " + exchangeRate + ", exchange rate date " + request.getDate() + " : "
							+ e.getMessage());
			response.getErrors().add(error);
			response.setSuccess(Boolean.FALSE);
		}

		return response;
	}

	/**
	 * Build line for the price injection
	 * 
	 * Example of line : XRS,,31.08.2017,CAD,CHF,0.7645160,0.7645160,0,0,1,,,,,,Z
	 * 
	 * @param priceDate
	 * @param price
	 * @param fundEntity
	 * @return
	 */
	private String buildLine(Date exchangeRateDate, String fromCurrency, String toCurrency, BigDecimal exchangeRate, BigDecimal oldExchangeRate) {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		StringBuilder line = new StringBuilder();

		line.append(PREFIX + COMA + COMA).append(format.format(exchangeRateDate)).append(COMA).append(fromCurrency).append(COMA).append(toCurrency).append(COMA)
				.append(exchangeRate).append(COMA).append(oldExchangeRate).append(",0,0,1,,,,,,").append(SUFFIX);

		return line.toString();
	}

}
