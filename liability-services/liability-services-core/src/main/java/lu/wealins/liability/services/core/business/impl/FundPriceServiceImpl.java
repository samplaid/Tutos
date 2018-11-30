package lu.wealins.liability.services.core.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.FundPriceDTO;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.liability.services.core.business.FundPriceService;
import lu.wealins.liability.services.core.business.FundService;
import lu.wealins.liability.services.core.mapper.FundMapper;
import lu.wealins.liability.services.core.mapper.FundPriceMapper;
import lu.wealins.liability.services.core.persistence.entity.FundPriceEntity;
import lu.wealins.liability.services.core.persistence.repository.FundPriceRepository;
import lu.wealins.liability.services.core.persistence.repository.FundRepository;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.liability.services.ws.rest.impl.FundRESTServiceImpl;

@Service
public class FundPriceServiceImpl implements FundPriceService {

	private static final Logger logger = LoggerFactory.getLogger(FundRESTServiceImpl.class);
	
	@Autowired
	private FundPriceRepository fundPriceRepository;

	@Autowired
	private FundRepository fundRepository;

	@Autowired
	private FundService fundService;

	@Autowired
	private FundPriceMapper fundPriceMapper;

	@Autowired
	private FundMapper fundMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundPriceService#countFundPrice(java.lang.String)
	 */
	@Override
	public int countFundPrice(String fundId) {
		return fundPriceRepository.countFundPrice(fundId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundPriceService#getFundPrices(java.lang.String, java.util.Date)
	 */
	@Override
	public List<FundPriceDTO> getFundPrices(String fundId, Date priceDate) {
		List<FundPriceEntity> activeFundPrices = fundPriceRepository.findActiveByFundIdAndPriceDate(fundId, priceDate);

		return fundPriceMapper.asFundPriceDTOs(activeFundPrices);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundPriceService#getMinFundPrice(java.lang.String)
	 */
	@Override
	public FundPriceDTO getMinFundPrice(String fundId) {
		logger.info("##################FUND ID "+fundId+" #####################################");
		return fundPriceMapper.asFundPriceDTO(fundPriceRepository.findMinFundPrice(fundId));
	}

	@Override
	public FundPriceDTO getLastFundPricesBefore(String fundId, Date date, int type) {
		
		List<Integer> types = Stream.of(type)
							.collect(Collectors.toCollection(ArrayList::new));
		SearchResult<FundPriceDTO> lastFundPrices = searchLastFundPricesBefore(fundId, types, 0, 1, date);

		if (CollectionUtils.isEmpty(lastFundPrices.getContent())) {
			return null;
		}

		return CollectionUtils.extractSingleton(lastFundPrices.getContent());
	}

	@Override
	public SearchResult<FundPriceDTO> searchLastFundPricesBefore(String fundId, List<Integer> types, int page, int size, Date date) {
		if (date == null) {
			date = new Date();
		}

		List<Integer> typesWithFEPriceType = updateFEPriceType(fundId, types);
		Pageable pageable = new PageRequest(page, size);
		Page<FundPriceEntity> pageResult = fundPriceRepository.findLastFundPricesBefore(fundId, typesWithFEPriceType, date, pageable);

		SearchResult<FundPriceDTO> r = new SearchResult<FundPriceDTO>();

		r.setPageSize(size);
		r.setTotalPageCount(pageResult.getTotalPages());
		r.setTotalRecordCount(pageResult.getTotalElements());
		r.setCurrentPage(pageResult.hasContent() ? pageResult.getNumber() + 1 : 1);
		if (pageResult.getContent() != null) {
			r.setContent(fundPriceMapper.asFundPriceDTOs(pageResult.getContent()));
		}
		return r;
	}

	@Override
	public Boolean isExistVniOfOneHundred(String fdsId, List<Integer> types, Date paymentDate) {
		int vniOfOneHundred = fundPriceRepository.countVniByDate(fdsId, types, paymentDate);
		return vniOfOneHundred > 0;
	}

	@Override
	public Boolean isExistVniBefore(String fdsId, List<Integer> types, Date paymentDate) {
		int vniBefore =  fundPriceRepository.countVniBeforeDate(fdsId, types, paymentDate);
		return vniBefore > 0;
	}

	private List<Integer> updateFEPriceType(String fundId, List<Integer> types) {
		List<Integer> typesWithFEPriceType = new ArrayList<>(types);
		if (fundService.isFe(fundId)) {
			typesWithFEPriceType.add(5);
			logger.info("UPDATE FE PRICE_TYPE add 5 ##################FUND ID " + fundId
				+ " #####################################");
		}
		return typesWithFEPriceType;
	}

	@Override
	public boolean existsForFund(String fundId) {
		return countFundPrice(fundId) > 0;
	}

}
