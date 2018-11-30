package lu.wealins.webia.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.ws.rest.LiabilityFundRESTService;

@Component
public class LiabilityFundRESTServiceImpl implements LiabilityFundRESTService {
	@Autowired
	private LiabilityFundService fundService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityFundRESTService#getFund(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public FundDTO getFund(SecurityContext context, String id) {
		return fundService.getFund(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityFundRESTService#update(javax.ws.rs.core.SecurityContext, lu.wealins.webia.ws.rest.dto.FundDTO)
	 */
	@Override
	public FundDTO update(SecurityContext context, FundDTO fund) {
		return fundService.update(fund);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityFundRESTService#create(javax.ws.rs.core.SecurityContext, lu.wealins.webia.ws.rest.dto.FundDTO)
	 */
	@Override
	public FundDTO create(SecurityContext context, FundDTO fund) {
		return fundService.create(fund);
	}

}
