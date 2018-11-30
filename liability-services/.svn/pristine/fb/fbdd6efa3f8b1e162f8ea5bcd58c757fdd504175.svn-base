package lu.wealins.liability.services.ws.rest.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lu.wealins.liability.services.core.business.ExchangeRateService;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.ws.rest.ExchangeRateRESTService;
import lu.wealins.common.dto.liability.services.ExchangeRateDTO;

@Component
public class ExchangeRateRESTServiceImpl implements ExchangeRateRESTService {

	@Autowired
	private ExchangeRateService exchangeRateService;
	@Autowired
	private CalendarUtils calendarUtils;

	@Override
	public ExchangeRateDTO getExchangeRate(SecurityContext context, String fromCurrency, String toCurrency, String date) {
		Date dt = DateUtils.truncate(new Date(), Calendar.DATE);

		if (StringUtils.hasText(date)) {
			dt = calendarUtils.createDate(date);
		}

		return exchangeRateService.getExchangeRate(fromCurrency, toCurrency, dt);
	}

	@Override
	public BigDecimal convert(SecurityContext context, BigDecimal amount, String fromCurrency, String toCurrency, String date) {
		Date dt = DateUtils.truncate(new Date(), Calendar.DATE);

		if (StringUtils.hasText(date)) {
			dt = calendarUtils.createDate(date);
		}

		return exchangeRateService.convert(amount, fromCurrency, toCurrency, dt);
	}
}
