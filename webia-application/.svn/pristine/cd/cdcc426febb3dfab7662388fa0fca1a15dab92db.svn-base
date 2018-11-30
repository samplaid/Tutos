package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.webia.core.service.LiabilityExchangeRateService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityExchangeRateServiceImpl implements LiabilityExchangeRateService {

	private static final String LIABILITY_EXCHANGE_RATE = "liability/exchangeRate/";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency, Date date) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<String, Object>();
		queryParams.add("amount", amount);
		queryParams.add("fromCurrency", fromCurrency);
		queryParams.add("toCurrency", toCurrency);
		queryParams.add("date", DATE_FORMAT.format(date));

		return restClientUtils.get(LIABILITY_EXCHANGE_RATE, "convert", queryParams, BigDecimal.class);
	}

}
