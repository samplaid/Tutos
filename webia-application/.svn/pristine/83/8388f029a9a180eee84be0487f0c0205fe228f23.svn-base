package lu.wealins.webia.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.FundPriceDTO;
import lu.wealins.webia.core.service.LiabilityFundPriceService;
import lu.wealins.webia.core.utils.Constantes;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityFundPriceServiceImpl implements LiabilityFundPriceService {

	private static final String LIABILITY_LOAD_FUND_PRICES = "liability/fundPrice/";

	@Autowired
	private RestClientUtils restClientUtils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityFundPriceService#getFundPrices(java.lang.String, java.util.Date)
	 */
	@Override
	public Collection<FundPriceDTO> getFundPrices(String fundId, Date priceDate) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();

		params.add("fundId", fundId);
		params.add("priceDate", new SimpleDateFormat(Constantes.YYYYMMDD).format(priceDate));

		return restClientUtils.get(LIABILITY_LOAD_FUND_PRICES, "fundPrices", params, new GenericType<Collection<FundPriceDTO>>() {
			// nothing to do.
		});
	}

	@Override
	public Boolean existsForFund(String fundId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();

		params.add("fundId", fundId);

		return restClientUtils.get(LIABILITY_LOAD_FUND_PRICES, "exists", params, Boolean.class);
	}

}
