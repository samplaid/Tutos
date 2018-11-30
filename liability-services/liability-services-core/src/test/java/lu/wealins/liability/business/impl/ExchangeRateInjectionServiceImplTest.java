package lu.wealins.liability.business.impl;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lu.wealins.liability.services.core.business.ExchangeRateInjectionService;
import lu.wealins.liability.services.core.business.exceptions.ExchangeRateException;
import lu.wealins.liability.services.core.persistence.entity.CurrencyEntity;
import lu.wealins.liability.services.core.persistence.entity.ExchangeRateEntity;
import lu.wealins.liability.services.core.persistence.repository.CurrencyRepository;
import lu.wealins.liability.services.core.persistence.repository.ExchangeRateRepository;
import lu.wealins.liability.services.core.utils.CalendarUtils;

@ActiveProfiles(value = "unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mock/dev/test/exchangeRateInjectionServiceTestContext.xml", "classpath:mock/dev/mockProperties.xml" })
public class ExchangeRateInjectionServiceImplTest {

	private static final String LUF = "LUF";
	private static final String GBP = "GBP";
	private static final String EUR = "EUR";
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;
	@Autowired
	private ExchangeRateInjectionService exchangeRateInjectionService;
	@Autowired
	private CurrencyRepository currencyRepository;
	@Autowired
	private CalendarUtils calendarUtils;

	private Date _2017_01_01;
	private Date _2017_01_17;
	private Date _2017_02_17;
	private Date _2017_03_31;

	@Before
	public void setup() {
		_2017_01_01 = calendarUtils.createDate(2017, 01, 01);
		_2017_01_17 = calendarUtils.createDate(2017, 01, 17);
		_2017_02_17 = calendarUtils.createDate(2017, 02, 17);
		_2017_03_31 = calendarUtils.createDate(2017, 03, 31);
	}

	@Test(expected = ExchangeRateException.class)
	public void checkDuplicateExchangeRate() throws ExchangeRateException {
		List<ExchangeRateEntity> exchangeRates = Arrays.asList(new ExchangeRateEntity());

		when(exchangeRateRepository.findActiveByDateAndFromCcyToCcy(eq(_2017_01_01), eq(EUR), eq(GBP))).thenReturn(exchangeRates);

		exchangeRateInjectionService.checkDuplicateExchangeRate(_2017_01_01, EUR, GBP);
	}

	@Test
	public void checkDuplicateExchangeRateOK() throws ExchangeRateException {
		when(exchangeRateRepository.findActiveByDateAndFromCcyToCcy(eq(_2017_01_01), eq(EUR), eq(GBP))).thenReturn(new ArrayList<ExchangeRateEntity>());

		exchangeRateInjectionService.checkDuplicateExchangeRate(_2017_01_01, EUR, GBP);
	}

	@Test(expected = ExchangeRateException.class)
	public void checkExchangeRateVariation() throws ExchangeRateException {
		ExchangeRateEntity exchangeRate = mockExchangeRateEntity(_2017_01_01, BigDecimal.ONE, EUR, GBP);

		when(exchangeRateRepository.findPreviousExchangeRate(eq(_2017_01_01), eq(EUR), eq(GBP))).thenReturn(exchangeRate);

		exchangeRateInjectionService.checkExchangeRateVariation(_2017_01_01, BigDecimal.TEN, EUR, GBP);
	}

	@Test
	public void checkExchangeRateVariationOK() throws ExchangeRateException {
		ExchangeRateEntity exchangeRate = mockExchangeRateEntity(_2017_01_01, BigDecimal.ONE, EUR, GBP);

		when(exchangeRateRepository.findPreviousExchangeRate(eq(_2017_01_01), eq(EUR), eq(GBP))).thenReturn(exchangeRate);

		exchangeRateInjectionService.checkExchangeRateVariation(_2017_01_01, new BigDecimal("1.05"), EUR, GBP);
	}

	@Test(expected = ExchangeRateException.class)
	public void checkExchangeRateDateVariation() throws ExchangeRateException {
		ExchangeRateEntity exchangeRate = mockExchangeRateEntity(_2017_01_01, BigDecimal.ONE, EUR, GBP);

		when(exchangeRateRepository.findPreviousExchangeRate(eq(_2017_02_17), eq(EUR), eq(GBP))).thenReturn(exchangeRate);

		exchangeRateInjectionService.checkExchangeRateDateVariation(_2017_02_17, EUR, GBP);
	}

	@Test
	public void checkExchangeRateDateVariationOK() throws ExchangeRateException {
		ExchangeRateEntity exchangeRate = mockExchangeRateEntity(_2017_01_01, BigDecimal.ONE, EUR, GBP);

		when(exchangeRateRepository.findPreviousExchangeRate(eq(_2017_01_17), eq(EUR), eq(GBP))).thenReturn(exchangeRate);

		exchangeRateInjectionService.checkExchangeRateDateVariation(_2017_01_17, EUR, GBP);
	}

	@Test(expected = ExchangeRateException.class)
	public void checkCurrencyExist() throws ExchangeRateException {
		exchangeRateInjectionService.checkCurrencyExist(LUF);
	}

	@Test
	public void checkCurrencyExistOK() throws ExchangeRateException {
		when(currencyRepository.findActiveCurrency(eq(EUR))).thenReturn(new CurrencyEntity());

		exchangeRateInjectionService.checkCurrencyExist(EUR);
	}

	@Test(expected = ExchangeRateException.class)
	public void checkExchangeRateNotNull() throws ExchangeRateException {
		exchangeRateInjectionService.checkExchangeRateNotNull(BigDecimal.ZERO);
	}

	@Test
	public void checkExchangeRateNotNullOK() throws ExchangeRateException {
		exchangeRateInjectionService.checkExchangeRateNotNull(BigDecimal.ONE);
	}

	private ExchangeRateEntity mockExchangeRateEntity(Date date, BigDecimal exchangeRate, String fromCurrency, String toCurrency) {
		ExchangeRateEntity exchangeRateEntity = Mockito.spy(new ExchangeRateEntity());

		exchangeRateEntity.setDate0(date);
		exchangeRateEntity.setMidRate(exchangeRate);
		exchangeRateEntity.setFromCurrency(fromCurrency);
		exchangeRateEntity.setToCurrency(toCurrency);

		return exchangeRateEntity;
	}

}
