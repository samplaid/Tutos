package lu.wealins.liability.services.core.business;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lu.wealins.liability.services.core.business.exceptions.FundPriceException;
import lu.wealins.liability.services.core.persistence.entity.FundEntity;

/**
 * @author xqv66
 *
 */
public interface PriceInjectionService {

	void checkDuplicatePrice(String fundId, Date priceDate) throws FundPriceException;

	void checkPricingDay(String fundId, Date priceDate) throws FundPriceException;

	void checkPriceVariation(String fundId, Date priceDate, BigDecimal price) throws FundPriceException;

	void checkPriceDateVariation(String fundId, Date priceDate) throws FundPriceException;

	void checkCurrency(String fundId, String currency) throws FundPriceException;

	void checkPriceNotNull(BigDecimal price) throws FundPriceException;

	void checkPendingTransactions(String fundId, Date priceDate) throws FundPriceException;

	void checkHoliday(Date date) throws FundPriceException;

	List<FundEntity> loadFundsByIsinAndCurrency(String isinCode, String currency) throws FundPriceException;
}
