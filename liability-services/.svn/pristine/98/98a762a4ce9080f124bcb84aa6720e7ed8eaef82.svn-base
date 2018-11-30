package lu.wealins.liability.services.ws.rest.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.FundSearchRequest;
import lu.wealins.common.dto.liability.services.FundSearcherRequest;
import lu.wealins.common.dto.liability.services.FundValuationDTO;
import lu.wealins.common.dto.liability.services.FundValuationRequest;
import lu.wealins.common.dto.liability.services.FundValuationSearchRequest;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.liability.services.core.business.FundService;
import lu.wealins.liability.services.core.business.FundValuationService;
import lu.wealins.liability.services.core.mapper.FundMapper;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.ws.rest.FundRESTService;
import lu.wealins.liability.services.ws.rest.exception.WssUpdateFundException;

@Component
public class FundRESTServiceImpl implements FundRESTService {

	private static final String FUND_SEARCH_REQUEST_CANNOT_BE_NULL = "Fund search request cannot be null.";
	private static final Logger logger = LoggerFactory.getLogger(FundRESTServiceImpl.class);
	private static final String POLICY_ID_CANT_BE_NULL = "The policy id can't be null";

	@Autowired
	private FundService fundService;

	@Autowired
	private FundValuationService valuationService;

	@Autowired
	private FundMapper fundMapper;

	@Autowired
	private CalendarUtils calendarUtils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.FundRESTService#getFund(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public FundDTO getFund(SecurityContext context, String fundId) {
		return fundService.getFund(fundId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.FundRESTService#getFunds(javax.ws.rs.core.SecurityContext, java.util.List)
	 */
	@Override
	public Collection<FundLiteDTO> getFunds(SecurityContext context, List<String> ids) {
		return fundService.getFunds(ids);
	}

	@Override
	public Collection<String> getFundIds(SecurityContext context, String agentId) {
		return fundService.getFundIds(agentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.FundRESTService#update(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.FundDTO)
	 */
	@Override
	public FundDTO update(SecurityContext context, FundDTO fund) {

		Assert.notNull(context);
		Assert.notNull(context.getUserPrincipal());
		Assert.notNull(fund);

		if (StringUtils.trimToEmpty(fund.getFdsId()).isEmpty()) {
			throw new WssUpdateFundException("RCFDS004 FundId must not be empty.", "RCFDS004");
		}

		return fundService.update(fund);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.FundRESTService#create(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.FundDTO)
	 */
	@Override
	public FundDTO create(SecurityContext context, FundDTO fund) {

		Assert.notNull(context);
		Assert.notNull(context.getUserPrincipal());
		Assert.notNull(fund);

		return fundService.create(fund);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.FundRESTService#search(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.FundSearchRequest)
	 */
	@Override
	public SearchResult<FundLiteDTO> search(SecurityContext context, FundSearchRequest request) {

		Assert.notNull(request);
		Assert.notNull(request.getType());

		int pageNum = request.getPageNum() == null || request.getPageNum().intValue() < 1 ? 1 : request.getPageNum().intValue();
		int pageSize = request.getPageSize() == null || request.getPageSize().intValue() <= 1 || request.getPageSize().intValue() > SearchResult.MAX_PAGE_SIZE ? SearchResult.DEFAULT_PAGE_SIZE
				: request.getPageSize().intValue();
		return fundService.search(request.getFilter(),
				request.getType(), request.getBrokerId(), request.isOnlyBroker(), pageNum - 1, pageSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.FundRESTService#search(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.FundSearcherRequest)
	 */
	@Override
	public SearchResult<FundLiteDTO> search(SecurityContext context, FundSearcherRequest fundSearcherRequest) {

		Assert.notNull(fundSearcherRequest, FUND_SEARCH_REQUEST_CANNOT_BE_NULL);

		return fundService.search(fundSearcherRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.FundRESTService#getValuation(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.FundValuationSearchRequest)
	 */
	@Override
	public SearchResult<FundValuationDTO> getValuation(SecurityContext context, FundValuationSearchRequest request) {
		return valuationService.getFundValuations(request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.FundRESTService#initValorization(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.FundInitValuationRequest)
	 */
	@Override
	public boolean initValorization(SecurityContext context, FundValuationRequest request) {
		return fundService.initValorization(request.getFdsId(), request.getDate());
	}

	@Override
	public boolean performFundValuation(SecurityContext context, FundValuationRequest request) {
		return fundService.performFundValuation(request.getFdsId(), request.getDate(), request.getPrice(), request.getPriceType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.FundRESTService#extractFidFund(javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public Response extractFidFund(SecurityContext context) {
		try {
			List<FundDTO> response = new ArrayList<>();
			fundService.extractFidFund().forEach(x -> response.add(fundMapper.asFundDTO(x)));
			logger.info("Funds found number :" + (response == null ? 0 : response.size()));
			return Response.ok(response).build();
		} catch (Exception e) {
			logger.error("Error during extract fid fund " + e);
			return Response.serverError().build();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.FundRESTService#updateLissiaFidFlag(javax.ws.rs.core.SecurityContext, java.util.List)
	 */
	@Override
	public Response updateLissiaFidFlag(SecurityContext context, List<String> fids) {
		try {
			Long response = fundService.updatetFidFundFlag(fids);
			return Response.ok(response).build();
		} catch (Exception e) {
			logger.error("Error during update lissia fid fund flag " + e);
			return Response.serverError().build();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.FundRESTService#validateFund(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.FundDTO)
	 */
	@Override
	public boolean validateFund(SecurityContext context, FundDTO fund) {
		return fundService.validateFund(fund);
	}

	@Override
	public boolean validateFunds(SecurityContext context, List<String> fdsIds) {
		return fundService.validateFunds(fdsIds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.FundRESTService#validateActiveFunds(javax.ws.rs.core.SecurityContext, java.util.List)
	 */
	@Override
	public List<String> validateActiveFunds(SecurityContext context, List<String> fundIds) {
		return fundService.validateActiveFunds(fundIds);
	}

	
	@Override
	public Boolean canAddFIDorFASFundValuationAmount(SecurityContext context, String fdsId, String valuationDate,short priceType) {	
		 return fundService.canAddFIDorFASValuationAmount(fdsId, calendarUtils.createDate(valuationDate),(int) priceType);
	}

	@Override
	public Collection<FundLiteDTO> getInvestedFunds(SecurityContext context, String policyId) {
		Assert.notNull(policyId, POLICY_ID_CANT_BE_NULL);

		return fundService.getInvestedFunds(policyId);
	}
}
