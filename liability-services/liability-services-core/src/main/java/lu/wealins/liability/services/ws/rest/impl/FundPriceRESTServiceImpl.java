package lu.wealins.liability.services.ws.rest.impl;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.liability.services.FundPriceDTO;
import lu.wealins.common.dto.liability.services.FundPriceSearchRequest;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.liability.services.core.business.FundPriceService;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.ws.rest.FundPriceRESTService;

@Component
public class FundPriceRESTServiceImpl implements FundPriceRESTService {

	private static final String FUND_ID_CANNOT_BE_NULL = "Fund id cannot be null.";

	private static final Logger logger = LoggerFactory.getLogger(FundPriceRESTServiceImpl.class);

	@Autowired
	private FundPriceService fundPriceService;

	@Autowired
	private CalendarUtils calendarUtils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.FundPriceRESTService#countFundPrice(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public int countFundPrice(SecurityContext context, String fundId) {
		return fundPriceService.countFundPrice(fundId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.FundPriceRESTService#getFundPrices(javax.ws.rs.core.SecurityContext, java.lang.String, java.lang.String)
	 */
	@Override
	public List<FundPriceDTO> getFundPrices(SecurityContext context, String fundId, String priceDate) {
		Date dt = null;
		if (StringUtils.hasText(priceDate)) {
			dt = calendarUtils.createDate(priceDate);
		}

		return fundPriceService.getFundPrices(fundId, dt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.FundPriceRESTService#search(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.FundPriceSearchRequest)
	 */
	@Override
	public SearchResult<FundPriceDTO> search(SecurityContext context, FundPriceSearchRequest request) {

		Assert.notNull(request);
		Assert.notNull(request.getFdsId());

		int pageNum = request.getPageNum() == null || request.getPageNum().intValue() < 1 ? 1 : request.getPageNum().intValue();
		int pageSize = request.getPageSize() == null || request.getPageSize().intValue() <= 1 || request.getPageSize().intValue() > SearchResult.MAX_PAGE_SIZE ? SearchResult.DEFAULT_PAGE_SIZE
				: request.getPageSize().intValue();

		logger.info("Search fund prices for fund " + request.getFdsId() + " before the date '" + request.getDate() + "'");
		return fundPriceService.searchLastFundPricesBefore(request.getFdsId(), request.getTypes(), pageNum - 1, pageSize, request.getDate());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.FundPriceRESTService#getMinFundPrice(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public FundPriceDTO getMinFundPrice(SecurityContext context, String fundId) {
		Assert.notNull(FUND_ID_CANNOT_BE_NULL, fundId);

		return fundPriceService.getMinFundPrice(fundId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.FundPriceRESTService#getLastFundPricesBefore(javax.ws.rs.core.SecurityContext, java.lang.String, java.util.Date, int)
	 */
	@Override
	public FundPriceDTO getLastFundPricesBefore(SecurityContext context, String fundId, String valuationDate, int type) {
		Assert.notNull(FUND_ID_CANNOT_BE_NULL, fundId);
		return fundPriceService.getLastFundPricesBefore(fundId, calendarUtils.createDate(valuationDate), type);
	}

	@Override
	public Boolean existsForFund(SecurityContext context, String fundId) {
		Assert.notNull(FUND_ID_CANNOT_BE_NULL, fundId);

		return fundPriceService.existsForFund(fundId);
	}
}
