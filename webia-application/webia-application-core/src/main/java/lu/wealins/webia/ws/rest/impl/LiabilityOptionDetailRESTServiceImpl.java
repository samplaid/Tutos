package lu.wealins.webia.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.OptionDetailDTO;
import lu.wealins.webia.core.service.LiabilityOptionDetailService;
import lu.wealins.webia.ws.rest.LiabilityOptionDetailRESTService;

@Component
public class LiabilityOptionDetailRESTServiceImpl implements LiabilityOptionDetailRESTService {

	@Autowired
	private LiabilityOptionDetailService optionDetailService;


	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityAgentRESTService#getSendingRoles(javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public List<OptionDetailDTO> getCPRRoles(@Context SecurityContext context) {	
		return optionDetailService.getCPRRoles();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityOptionDetailRESTService#getCPRRoles(java.lang.String)
	 */
	@Override
	public List<OptionDetailDTO> getCPRRoles(@Context SecurityContext context, String productId, boolean productCapi, boolean yearTerm) {
		return optionDetailService.getCPRRoles(productId, productCapi, yearTerm);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityOptionDetailRESTService#getContextualizedPricingFrequencies(javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public List<OptionDetailDTO> getContextualizedPricingFrequencies(SecurityContext ctx) {
		return optionDetailService.getContextualizedPricingFrequencies();
	}

}
