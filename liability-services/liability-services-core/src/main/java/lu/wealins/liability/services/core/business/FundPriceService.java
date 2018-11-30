package lu.wealins.liability.services.core.business;

import java.util.Date;
import java.util.List;

import lu.wealins.common.dto.liability.services.FundPriceDTO;
import lu.wealins.common.dto.liability.services.SearchResult;

public interface FundPriceService {

	/**
	 * Return the number of FUND PRICE of the specified fund.
	 * 
	 * @param fundId the fund Id
	 * @return the number of entries
	 */
	int countFundPrice(String fundId);

	boolean existsForFund(String fundId);

	/**
	 * Return the last valuations of a fund before a date
	 * 
	 * @param fundId the fund Id
	 * @param types list of expected types
	 * @param page
	 * @param size
	 * @param maxDate
	 * @return a page of fund price
	 */
	SearchResult<FundPriceDTO> searchLastFundPricesBefore(String fundId, List<Integer> types, int page, int size, Date date);

	FundPriceDTO getMinFundPrice(String fundId);

	/**
	 * Get fund prices according its fund id and the price date.
	 * 
	 * @param fundId The fund id.
	 * @param priceDate The price date.
	 * @return The fund prices.
	 */
	List<FundPriceDTO> getFundPrices(String fundId, Date priceDate);

	FundPriceDTO getLastFundPricesBefore(String fundId, Date date, int type);
	
	/**
	 * Validate if exist VNI of one Hundred for specific Fund.
	 * @param fdsId the fund id
	 * @param date the payment date
	 * @param types the type of price to verified.
	 * @return a boolean value or null.
	 */
	Boolean isExistVniOfOneHundred(String fdsId, List<Integer> types, Date date);
	
	
	/**
	 * Validate if exist VNI before a specific date for specific fund.
	 * @param fdsId the fund id
	 * @param date the payment date
	 * @param types the type of price to verified.
	 * @return a boolean value True if a VNI exists before the specified date. Otherwise, it returns false.
	 */
	Boolean isExistVniBefore(String fdsId, List<Integer> types, Date date);


}
