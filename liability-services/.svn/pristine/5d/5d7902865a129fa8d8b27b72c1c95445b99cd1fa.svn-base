package lu.wealins.liability.services.core.business.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.liability.services.core.business.FundService;
import lu.wealins.liability.services.core.business.PriceInjectionService;
import lu.wealins.liability.services.core.business.exceptions.FundPriceException;
import lu.wealins.liability.services.core.business.exceptions.FundPriceExceptionCode;
import lu.wealins.liability.services.core.mapper.FundMapper;
import lu.wealins.liability.services.core.persistence.entity.FundEntity;
import lu.wealins.liability.services.core.persistence.entity.FundPriceEntity;
import lu.wealins.liability.services.core.persistence.entity.FundTransactionEntity;
import lu.wealins.liability.services.core.persistence.repository.FundPriceRepository;
import lu.wealins.liability.services.core.persistence.repository.FundRepository;
import lu.wealins.liability.services.core.persistence.repository.FundTransactionRepository;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.core.utils.MessageUtils;
import lu.wealins.common.dto.liability.services.FundDTO;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED) // NOTICE
public class PriceInjectionServiceImpl implements PriceInjectionService {

	private static final int HUNDRED = 100;
	private static final int TWENTY_ONE = 21;
	private static final int PRICE_VARIATION_THRESHOLD = 10;
	private static final String FUND_PRICE_ALREADY_EXIST = "fundPriceAlreadyExist";
	private static final String WRONG_PRICING_DAY = "wrongPricingDay";
	private static final String PRICE_VARIATION_TOO_HIGH = "priceVariationTooHigh";
	private static final String PRICE_DATE_VARIATION_TOO_HIGH = "priceDateVariationTooHigh";
	private static final String WRONG_PRICE_CURRENCY = "wrongPriceCurrency";
	private static final String PRICE_EQUALS_TO_ZERO = "priceEqualsToZero";
	private static final String PENDING_TRANSACTIONS_IN_WITH_INPUT_FEES = "pendingTransactionsInWithInputFees";
	private static final String PENDING_TRANSACTIONS_OUT_WITH_OUTPUT_FEES = "pendingTransactionsOutWithOutputFees";
	private static final String BLOCKED_TRANSACTIONS = "blockedTransactions";
	private static final String PRICE_INSURANCE_HOLIDAY = "priceInsuranceHoliday";

	@Autowired
	private FundPriceRepository fundPriceRepository;

	@Autowired
	private FundRepository fundRepository;

	@Autowired
	private FundService fundService;

	@Autowired
	private FundTransactionRepository fundTransactionRepository;

	@Autowired
	private MessageUtils messageUtils;

	@Autowired
	private CalendarUtils calendarUtils;

	@Autowired
	private FundMapper fundMapper;

	private Map<String, FundDTO> fundsCache = new HashMap<String, FundDTO>();

	@Override
	public List<FundEntity> loadFundsByIsinAndCurrency(String isinCode, String currency) throws FundPriceException {
		List<FundEntity> funds = new ArrayList<>();
		if (StringUtils.isNotEmpty(isinCode) && StringUtils.isNotEmpty(currency)) {
			funds = fundRepository.findByIsinAndCurrency(isinCode, currency);
		}

		if (funds.isEmpty()) {
			throw new FundPriceException(FundPriceExceptionCode.ISIN_DOES_NOT_EXIST, "The isin " + isinCode + " does not exist.");

		}

		return funds;
	}

	@Override
	public void checkDuplicatePrice(String fundId, Date priceDate) throws FundPriceException {
		Collection<FundPriceEntity> fundPrices = fundPriceRepository.findActiveByFundIdAndPriceDate(fundId, priceDate);

		if (CollectionUtils.isNotEmpty(fundPrices)) {
			throwFundPriceException(FundPriceExceptionCode.DUPLICATE_FUND_PRICE, FUND_PRICE_ALREADY_EXIST, fundId, priceDate.toString());
		}
	}

	@Override
	public void checkPricingDay(String fundId, Date priceDate) throws FundPriceException {
		FundDTO fund = retrieveFund(fundId);

		Integer pricingDay = fund.getPricingDay();
		// The frequency is not dayly and monthly
		if (pricingDay != null && pricingDay.intValue() != 0) {
			int dayOfWeek = (calendarUtils.getDayOfWeek(priceDate) - 1) % 7;
			if (pricingDay.intValue() != dayOfWeek) {
				throwFundPriceException(FundPriceExceptionCode.WRONG_PRICING_DAY, WRONG_PRICING_DAY, pricingDay.toString(), fundId, dayOfWeek + "");
			}
		}
	}

	@Override
	public void checkPriceVariation(String fundId, Date priceDate, BigDecimal price) throws FundPriceException {
		List<FundPriceEntity> previousFundPrices = fundPriceRepository.findPreviousFundPrices(fundId, priceDate);

		if (previousFundPrices != null && previousFundPrices.get(0) != null) {
			FundPriceEntity previousFundPrice = previousFundPrices.get(0);
			BigDecimal previousPrice = previousFundPrice.getPrice();
			if (previousPrice != null && previousPrice.compareTo(BigDecimal.ZERO) != 0) {
				BigDecimal variation = price.subtract(previousPrice).divide(previousPrice, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(HUNDRED)).abs();
				if (variation.compareTo(new BigDecimal(PRICE_VARIATION_THRESHOLD)) > 0) {
					throwFundPriceException(FundPriceExceptionCode.PRICE_VARIATION_TOO_HIGH, PRICE_VARIATION_TOO_HIGH, PRICE_VARIATION_THRESHOLD + "", fundId,
							priceDate.toString(), price + "");
				}
			}
		}

	}

	@Override
	public void checkPriceDateVariation(String fundId, Date priceDate) throws FundPriceException {
		List<FundPriceEntity> previousFundPrices = fundPriceRepository.findPreviousFundPrices(fundId, priceDate);

		if (previousFundPrices != null && previousFundPrices.get(0) != null) {
			FundPriceEntity previousFundPrice = previousFundPrices.get(0);
			Date previousPriceDate = previousFundPrice.getDate0();
			int numberOfDays = Math.abs(calendarUtils.daysBetween(previousPriceDate, priceDate));

			if (numberOfDays > TWENTY_ONE) {
				throwFundPriceException(FundPriceExceptionCode.PRICE_DATE_VARIATION_TOO_HIGH, PRICE_DATE_VARIATION_TOO_HIGH, TWENTY_ONE + "", fundId,
						priceDate.toString());
			}
		}

	}

	@Override
	public void checkCurrency(String fundId, String currency) throws FundPriceException {
		FundDTO fund = retrieveFund(fundId);

		String fundCurrency = fund.getCurrency();

		if (fundCurrency == null || !fundCurrency.trim().equals(currency)) {
			throwFundPriceException(FundPriceExceptionCode.WRONG_PRICE_CURRENCY, WRONG_PRICE_CURRENCY, fundId, currency);
		}

	}

	@Override
	public void checkPriceNotNull(BigDecimal price) throws FundPriceException {
		if (price == null || BigDecimal.ZERO.compareTo(price) == 0) {
			throwFundPriceException(FundPriceExceptionCode.PRICE_EQUALS_TO_ZERO, PRICE_EQUALS_TO_ZERO);
		}

	}

	@Override
	public void checkHoliday(Date date) throws FundPriceException {
		if (calendarUtils.isAnHoliday(date)) {
			throwFundPriceException(FundPriceExceptionCode.PRICE_INSURANCE_HOLIDAY, PRICE_INSURANCE_HOLIDAY, date.toString());
		}
	}

	@Override
	public void checkPendingTransactions(String fundId, Date priceDate) throws FundPriceException {
		checkPendingTransactionsInWithInputFees(fundId, priceDate);
		checkPendingTransactionsOutWithOutputFees(fundId, priceDate);
		checkBlockedTransactions(fundId, priceDate);
	}

	private void checkPendingTransactionsInWithInputFees(String fundId, Date priceDate) throws FundPriceException {
		List<FundTransactionEntity> transactionsInWithEntryFees = fundTransactionRepository.findTransactionsInWithInputFees(fundId, priceDate);

		if (CollectionUtils.isNotEmpty(transactionsInWithEntryFees)) {
			throwFundPriceException(FundPriceExceptionCode.PENDING_TRANSACTIONS_IN_WITH_INTPUT_FEES, PENDING_TRANSACTIONS_IN_WITH_INPUT_FEES, fundId);
		}
	}

	private void checkPendingTransactionsOutWithOutputFees(String fundId, Date priceDate) throws FundPriceException {
		List<FundTransactionEntity> transactionsInWithEntryFees = fundTransactionRepository.findTransactionsOutWithOutputFees(fundId, priceDate);

		if (CollectionUtils.isNotEmpty(transactionsInWithEntryFees)) {
			throwFundPriceException(FundPriceExceptionCode.PENDING_TRANSACTIONS_OUT_WITH_OUTPUT_FEES, PENDING_TRANSACTIONS_OUT_WITH_OUTPUT_FEES, fundId);
		}
	}

	private void checkBlockedTransactions(String fundId, Date priceDate) throws FundPriceException {
		List<FundTransactionEntity> blockedTransactions = fundTransactionRepository.findBlockedTransactions(fundId, priceDate);

		if (CollectionUtils.isNotEmpty(blockedTransactions)) {
			throwFundPriceException(FundPriceExceptionCode.PENDING_TRANSACTIONS_IN_WITH_INTPUT_FEES, BLOCKED_TRANSACTIONS, fundId);
		}
	}

	/**
	 * @param fundId
	 * @return
	 */
	private FundDTO retrieveFund(String fundId) {
		FundDTO fund = fundsCache.get(fundId);
		if (fund != null) {
			return fund;
		}

		FundEntity fundEntity = fundService.getFundEntity(fundId);
		if (fundEntity != null) {
			fund = fundMapper.asFundDTO(fundEntity);

			if (fund != null) {
				fundsCache.put(fundId, fund);
				return fund;
			}
		}

		throw new IllegalArgumentException("Fund " + fundId + " does not exist.");
	}

	private void throwFundPriceException(FundPriceExceptionCode exceptionCode, String messageId, String... args) throws FundPriceException {
		String message = messageUtils.getMessage(messageId, args);

		throw new FundPriceException(exceptionCode, message);
	}

}
