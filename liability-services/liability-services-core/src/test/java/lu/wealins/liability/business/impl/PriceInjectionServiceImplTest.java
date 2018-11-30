/**
 * 
 */
package lu.wealins.liability.business.impl;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import lu.wealins.liability.services.core.business.FundService;
import lu.wealins.liability.services.core.business.PriceInjectionService;
import lu.wealins.liability.services.core.business.exceptions.FundPriceException;
import lu.wealins.liability.services.core.persistence.entity.FundEntity;
import lu.wealins.liability.services.core.persistence.entity.FundPriceEntity;
import lu.wealins.liability.services.core.persistence.entity.FundTransactionEntity;
import lu.wealins.liability.services.core.persistence.entity.HolidayEntity;
import lu.wealins.liability.services.core.persistence.repository.FundPriceRepository;
import lu.wealins.liability.services.core.persistence.repository.FundRepository;
import lu.wealins.liability.services.core.persistence.repository.FundTransactionRepository;
import lu.wealins.liability.services.core.persistence.repository.HolidayRepository;
import lu.wealins.liability.services.core.utils.CalendarUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/coreContext.xml" })
@ActiveProfiles("dev-test")
public class PriceInjectionServiceImplTest {

	private static final String EUR = "Eur";
	private static final String GSP = "Gsp";
	private static final String FUND_ID01 = "FUND_ID01";
	private static final String FUND_ID02 = "FUND_ID02";

	@Autowired
	private PriceInjectionService priceInjectionService;

	@Autowired
	private CalendarUtils calendarUtils;

	@Mock
	private FundPriceRepository fundPriceRepository;

	@Mock
	private FundRepository fundRepository;

	@Mock
	private FundService fundService;


	@Mock
	private FundTransactionRepository fundTransactionRepository;

	@Mock
	private HolidayRepository holidayRepository;

	private Date _2017_01_01;
	private Date _2017_01_17;
	private Date _2017_02_17;
	private Date _2017_02_14;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		ReflectionTestUtils.setField(priceInjectionService, "fundTransactionRepository", fundTransactionRepository);
		ReflectionTestUtils.setField(priceInjectionService, "fundRepository", fundRepository);
		ReflectionTestUtils.setField(priceInjectionService, "fundPriceRepository", fundPriceRepository);
		ReflectionTestUtils.setField(priceInjectionService, "calendarUtils", calendarUtils);
		ReflectionTestUtils.setField(priceInjectionService, "fundService", fundService);

		ReflectionTestUtils.setField(calendarUtils, "holidayRepository", holidayRepository);

		_2017_01_01 = calendarUtils.createDate(2017, 01, 01);
		_2017_01_17 = calendarUtils.createDate(2017, 01, 17);
		_2017_02_17 = calendarUtils.createDate(2017, 02, 17);
		_2017_02_14 = calendarUtils.createDate(2017, 02, 14);
	}

	@Test(expected = FundPriceException.class)
	public void checkDuplicatePrice() throws FundPriceException {
		List<FundPriceEntity> prices = Arrays.asList(new FundPriceEntity());

		when(fundPriceRepository.findActiveByFundIdAndPriceDate(eq(FUND_ID01), eq(_2017_02_17))).thenReturn(prices);

		priceInjectionService.checkDuplicatePrice(FUND_ID01, _2017_02_17);
	}

	@Test
	public void checkNoDuplicatePrice() throws FundPriceException {
		when(fundPriceRepository.findActiveByFundIdAndPriceDate(eq(FUND_ID01), eq(_2017_02_17))).thenReturn(new ArrayList<FundPriceEntity>());
		priceInjectionService.checkDuplicatePrice(FUND_ID01, _2017_02_17);
		Assert.assertTrue(true);
	}

	@Test
	public void checkCurrency() throws FundPriceException {
		FundEntity fundEntity = mockFundEntity(FUND_ID01, Integer.valueOf(2), EUR);
		Mockito.doReturn(fundEntity).when(fundService).getFundEntity(eq(FUND_ID01));
		priceInjectionService.checkCurrency(FUND_ID01, EUR);
		Assert.assertTrue(true);
	}

	@Test(expected = FundPriceException.class)
	public void checkWrongCurrency() throws FundPriceException {
		FundEntity fundEntity = mockFundEntity(FUND_ID02, Integer.valueOf(2), GSP);
		when(fundService.getFundEntity(eq(FUND_ID02))).thenReturn(fundEntity);

		priceInjectionService.checkCurrency(FUND_ID02, EUR);
	}

	@Test(expected = FundPriceException.class)
	public void checkWrongPriceNotNull() throws FundPriceException {
		priceInjectionService.checkPriceNotNull(BigDecimal.ZERO);
	}

	@Test
	public void checkPriceNotNull() throws FundPriceException {
		priceInjectionService.checkPriceNotNull(BigDecimal.ONE);
		Assert.assertTrue(true);
	}

	@Test(expected = FundPriceException.class)
	public void checkWrongPricingDay() throws FundPriceException {
		FundEntity fundEntity = mockFundEntity(FUND_ID01, Integer.valueOf(2), EUR);
		when(fundRepository.findOne(eq(FUND_ID01))).thenReturn(fundEntity);

		priceInjectionService.checkPricingDay(FUND_ID01, _2017_02_17);
	}

	@Test
	public void checkPricingDay() throws FundPriceException {
		FundEntity fundEntity = mockFundEntity(FUND_ID01, Integer.valueOf(2), EUR);
		when(fundRepository.findOne(eq(FUND_ID01))).thenReturn(fundEntity);

		priceInjectionService.checkPricingDay(FUND_ID01, _2017_02_14);
		Assert.assertTrue(true);
	}

	@Test
	public void checkPriceVariation() throws FundPriceException {
		List<FundPriceEntity> fundPriceEntities = mockFundPriceEntity(_2017_02_14, BigDecimal.ONE, EUR);
		when(fundPriceRepository.findPreviousFundPrices(eq(FUND_ID01), eq(_2017_02_17))).thenReturn(fundPriceEntities);
		priceInjectionService.checkPriceVariation(FUND_ID01, _2017_02_17, new BigDecimal("1.01"));
		Assert.assertTrue(true);
	}

	@Test(expected = FundPriceException.class)
	public void checkWrongPriceVariation() throws FundPriceException {
		List<FundPriceEntity> fundPriceEntities = mockFundPriceEntity(_2017_02_14, BigDecimal.ONE, EUR);
		when(fundPriceRepository.findPreviousFundPrices(eq(FUND_ID01), eq(_2017_02_17))).thenReturn(fundPriceEntities);

		priceInjectionService.checkPriceVariation(FUND_ID01, _2017_02_17, BigDecimal.TEN);
	}

	@Test
	public void checkPriceDateVariation() throws FundPriceException {
		List<FundPriceEntity> fundPriceEntities = mockFundPriceEntity(_2017_02_14, BigDecimal.ONE, EUR);
		when(fundPriceRepository.findPreviousFundPrices(eq(FUND_ID01), eq(_2017_02_17))).thenReturn(fundPriceEntities);

		priceInjectionService.checkPriceDateVariation(FUND_ID01, _2017_02_17);
		Assert.assertTrue(true);
	}

	@Test(expected = FundPriceException.class)
	public void checkWrongPriceDateVariation() throws FundPriceException {
		List<FundPriceEntity> fundPriceEntities = mockFundPriceEntity(_2017_01_17, BigDecimal.ONE, EUR);
		when(fundPriceRepository.findPreviousFundPrices(eq(FUND_ID01), eq(_2017_02_17))).thenReturn(fundPriceEntities);

		priceInjectionService.checkPriceDateVariation(FUND_ID01, _2017_02_17);
	}

	@Test(expected = FundPriceException.class)
	public void checkWrongPendingTransactionsInWithInputFees() throws FundPriceException {
		FundTransactionEntity fundTransactionEntity = mockFundTransactionEntity(FUND_ID01);
		when(fundTransactionRepository.findTransactionsInWithInputFees(eq(FUND_ID01), eq(_2017_02_17))).thenReturn(Arrays.asList(fundTransactionEntity));

		priceInjectionService.checkPendingTransactions(FUND_ID01, _2017_02_17);
	}

	@Test(expected = FundPriceException.class)
	public void checkWrongPendingTransactionsOutWithOutputFees() throws FundPriceException {
		FundTransactionEntity fundTransactionEntity = mockFundTransactionEntity(FUND_ID01);
		when(fundTransactionRepository.findTransactionsOutWithOutputFees(eq(FUND_ID01), eq(_2017_02_17))).thenReturn(Arrays.asList(fundTransactionEntity));

		priceInjectionService.checkPendingTransactions(FUND_ID01, _2017_02_17);
	}

	@Test(expected = FundPriceException.class)
	public void checkWrongBlockedTransactions() throws FundPriceException {
		FundTransactionEntity fundTransactionEntity = mockFundTransactionEntity(FUND_ID01);
		when(fundTransactionRepository.findBlockedTransactions(eq(FUND_ID01), eq(_2017_02_17))).thenReturn(Arrays.asList(fundTransactionEntity));

		priceInjectionService.checkPendingTransactions(FUND_ID01, _2017_02_17);
	}

	@Test
	public void checkPendingTransactions() throws FundPriceException {
		when(fundTransactionRepository.findTransactionsInWithInputFees(eq(FUND_ID01), eq(_2017_02_17))).thenReturn(new ArrayList<FundTransactionEntity>());
		when(fundTransactionRepository.findTransactionsOutWithOutputFees(eq(FUND_ID01), eq(_2017_02_17))).thenReturn(new ArrayList<FundTransactionEntity>());
		when(fundTransactionRepository.findBlockedTransactions(eq(FUND_ID01), eq(_2017_02_17))).thenReturn(new ArrayList<>());

		priceInjectionService.checkPendingTransactions(FUND_ID01, _2017_02_17);
		Assert.assertTrue(true);
	}

	@Test(expected = FundPriceException.class)
	public void checkWrongHoliday() throws FundPriceException {
		when(holidayRepository.findOne(eq(_2017_01_01))).thenReturn(Mockito.spy(new HolidayEntity()));

		priceInjectionService.checkHoliday(_2017_01_01);
	}

	@Test
	public void checkHoliday() throws FundPriceException {
		priceInjectionService.checkHoliday(_2017_01_17);
	}

	private FundEntity mockFundEntity(String fundId, Integer pricingDay, String currency) {
		FundEntity fundEntity = Mockito.spy(new FundEntity());

		fundEntity.setFdsId(fundId);
		fundEntity.setPricingDay(pricingDay);
		fundEntity.setCurrency(currency);

		return fundEntity;
	}

	private List<FundPriceEntity> mockFundPriceEntity(Date priceDate, BigDecimal price, String currency) {
		FundPriceEntity fundPriceEntity = Mockito.spy(new FundPriceEntity());

		List<FundPriceEntity> fundPriceEntities = new ArrayList<FundPriceEntity>();
		fundPriceEntity.setDate0(priceDate);
		fundPriceEntity.setPrice(price);
		fundPriceEntity.setCurrency(currency);

		fundPriceEntities.add(fundPriceEntity);
		return fundPriceEntities;
	}

	private FundTransactionEntity mockFundTransactionEntity(String fundId) {
		FundTransactionEntity fundTransactionEntity = new FundTransactionEntity();

		fundTransactionEntity.setFund(fundId);

		return fundTransactionEntity;
	}

}
