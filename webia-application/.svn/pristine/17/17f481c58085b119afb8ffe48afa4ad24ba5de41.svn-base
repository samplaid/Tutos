package lu.wealins.webia.core.service;

import java.util.Collection;
import java.util.Date;

import lu.wealins.common.dto.liability.services.FundPriceDTO;

public interface LiabilityFundPriceService {

	/**
	 * Get fund prices according its fund id and the price date.
	 * 
	 * @param fundId The fund id.
	 * @param priceDate The price date.
	 * @return The fund prices.
	 */
	Collection<FundPriceDTO> getFundPrices(String fundId, Date priceDate);
	
	Boolean existsForFund(String fundId);
}
