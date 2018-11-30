package lu.wealins.liability.services.core.business.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.ExchangeRateDTO;
import lu.wealins.liability.services.core.business.ExchangeRateService;
import lu.wealins.liability.services.core.mapper.ExchangeRateMapper;
import lu.wealins.liability.services.core.persistence.entity.ExchangeRateEntity;
import lu.wealins.liability.services.core.persistence.repository.ExchangeRateRepository;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

	@Autowired
	private ExchangeRateRepository repository;
	@Autowired
	private ExchangeRateMapper exchangeRateMapper;

	@Override
	public ExchangeRateDTO getExchangeRate(String fromCurrency, String toCurrency, Date date) {
		Assert.notNull(fromCurrency);
		Assert.notNull(toCurrency);
		Assert.notNull(date);

		PageRequest page = new PageRequest(0, 1);
		
		Page<ExchangeRateEntity> list = 
				repository.findAllBeforeOrEqualDate0(fromCurrency, toCurrency, date, page);
		
		ExchangeRateEntity firstExchangeRate = (list.hasContent() ? list.getContent().get(0) : null);

		return exchangeRateMapper.asExchangeRateDTO(firstExchangeRate);
	}

	@Override
	public BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency, Date date) {
		Assert.notNull(amount);

		if (fromCurrency.equals(toCurrency)) {
			return amount;
		}

		ExchangeRateDTO exchangeRate = getExchangeRate(fromCurrency, toCurrency, date);

		if (exchangeRate == null) {
			throw new IllegalStateException("Cannot find exchange rate for the currency " + fromCurrency + " to currency " + toCurrency + ".");
		}

		if (BooleanUtils.toBooleanObject(exchangeRate.getReciprocal())) {
			return amount.divide(exchangeRate.getMidRate(), 2, RoundingMode.HALF_UP);
		}

		return amount.multiply(exchangeRate.getMidRate()).setScale(2, RoundingMode.HALF_UP);
	}
}
